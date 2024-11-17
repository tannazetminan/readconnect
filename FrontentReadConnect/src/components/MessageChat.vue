<template>
  <div class="container-cards">

    <!--left menu-->
    <div class="cards-description">

      <!-- personal info-->
      <div class="personal-data">
        <img src="../../public/images/people.png" class="profile" />
        <p class="data-description">Welcome {{this.fullName }}</p>
      </div>
      <div class="container-form">
        <div class="ctn-title">
          <h3 class=" title ">New message</h3>
        </div>

        <!-- Message Form -->
        <form @submit.prevent="sendMessage">
          <!-- <label for="receiverUsername">To (Receiver's Username):</label> -->
          <!-- <input v-model="receiverUsername" placeholder="Enter receiver's username" required /> -->

          <label for="messageContent">Message:</label>
          <textarea v-model="messageContent" placeholder="Type your message" required></textarea>

          <button type="submit">Send Message</button>
        </form>
      </div>
    </div>

    <!-- right content -->
    <!-- Display Messages -->
    <div class="container">
      <div class="message-list">
        <div v-if="messages.length > 0">
          <div v-for="message in messages" :key="message.id" class="message">
            <div class="message-header">
              <img src="../../public/images/people.png" class="avatar" alt="Avatar">
              <div class="sender-info">
                <p class="sender-name">Sender: {{ message.sender.username }} <br /> Receiver: {{
                  message.receiver.username }}</p>
                <span class="timestamp">{{ formatDate(message.timestamp) }}</span>
                <div class="message-content">

                  <p> {{ message.content }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div v-else>
          <p>No messages to display.</p>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import FetchDataService from "../services/FetchDataService";
import MessageService from "../services/MessageService";


export default {
  name: "MessageChat",

  data() {
    return {
      messages: [],
      messages2: [],
      fullName: "",
      senderId: parseInt(localStorage.getItem("userId")),
      receiverUsername: "",  
      receiverId: null,      
      messageContent: "",    
    };
  },
  methods: {
  async fetchMessages() {
    if (!this.receiverId) {
      console.error('Receiver ID not set');
      return;
    }

    try {
      const response = await MessageService.getMessagesBetweenUsers(this.senderId, this.receiverId);
      this.messages = response.data.reverse();
      this.scrollToBottom();
    } catch (error) {
      console.error("Error fetching messages:", error);
    }
  },

  async sendMessage() {
    // if (!this.receiverUsername) {
    //   console.error("Receiver's username is required");
    //   return;
    // }

    try {
     // await this.getReceiverIdByUsername();

      if (!this.receiverId) {
        alert("Receiver not found");
        return;
      }

      const messageData = {
        senderId: this.senderId,
        receiverId: this.receiverId,
        content: this.messageContent,
      };

      const response = await MessageService.sendMessage(messageData);
      this.messages.push(response.data); // Add new message to the list
      this.messages.sort((a, b) => new Date(a.timestamp) - new Date(b.timestamp)); // Sort messages after sending
      this.messageContent = ""; // Clear the input field

    } catch (error) {
      console.error("Error sending message:", error);
    }
  },

   // Format timestamp
    formatDate(timestamp) {
      return new Date(timestamp).toLocaleString();
    },

    // Retrieve the receiver's ID by username
    getReceiverIdByUsername() {
      return FetchDataService.getUserIdByUsername(this.receiverUsername)
        .then(response => {
          this.receiverId = response.data.id;
        })
        .catch(error => {
          console.error("Error fetching receiver ID:", error);
          this.receiverId = null;
        });
    },

    scrollToBottom() {
      const messageList = this.$el.querySelector(".message-list");
      messageList.scrollTop = messageList.scrollHeight;
    },
},

  // methods: {
  //   // Fetch messages between the sender and receiver
  //   fetchMessages(receiverId) {
  //     console.log(this.receiverId);
  //     if (this.receiverId) {
  //       FetchDataService.getMessagesBetweenUsers(this.senderId, this.receiverId)
  //         .then(response => {
  //           this.messages = response.data;
  //           console.log(receiverId);
  //         })
  //         .catch(error => console.error("Error fetching messages:", error));
  //         FetchDataService.getMessagesBetweenUsers(this.receiverId, this.senderId)
  //         .then(response => {
  //           this.messages2 = response.data.reverse();
  //           console.log(receiverId);
  //         })
  //         .catch(error => console.error("Error fetching messages:", error));
  //     }
  //   },
  //   // Format timestamp
  //   formatDate(timestamp) {
  //     return new Date(timestamp).toLocaleString();
  //   },
  //   // Retrieve the receiver's ID by username
  //   getReceiverIdByUsername() {
  //     return FetchDataService.getUserIdByUsername(this.receiverUsername)
  //       .then(response => {
  //         this.receiverId = response.data.id;
  //       })
  //       .catch(error => {
  //         console.error("Error fetching receiver ID:", error);
  //         this.receiverId = null;
  //       });
  //   },
  //   // Send message after retrieving receiver's ID
  //   async sendMessage() {
  //     if (!this.receiverUsername) {
  //       console.error("Receiver's username is required");
  //       return;
  //     }

  //     try {
  //       // Ensure receiver ID is set before sending the message
  //       await this.getReceiverIdByUsername();

  //       if (!this.receiverId) {
  //         alert("Receiver not found");
  //         return;
  //       }

  //       // Message data to be sent
  //       const messageData = {
  //         senderId: this.senderId,
  //         receiverId: this.receiverId,
  //         content: this.messageContent,
  //       };

  //       // Send the message
  //       FetchDataService.sendMessage(messageData)
  //         .then(response => {
  //           // this.messages.push(response.data);  // Append the new message to the list
  //           // this.messageContent = "";  // Clear input field
  //           this.fetchMessages().reverse();
  //           console(response)
  //         })
  //         .catch(error => {
  //           console.error("Error sending message:", error);
  //         });
  //     } catch (error) {
  //       console.error("Error in sending message:", error);
  //     }
  //   }
  // },
  mounted() {
    this.receiverId = this.$route.params.receiverId;
    if (this.receiverId) {
      this.fetchMessages(this.receiverId);
    } else {
      console.error('receiverId is undefined');
      this.message = "NA"
      this.fetchMessages();
    }
    this.fullName = localStorage.getItem('fullName');
    setInterval(this.fetchMessages,1000)
  },
};
</script>



<!-- <script>
  import FetchDataService from "../services/FetchDataService";
  
  export default {
      name: "MessagesDisplay",
  
      data() {
          return {
              messages: [],
              fullName: "",
          };
      },
  
      methods: {
        fetchMessages() {
          let id;
          let sender;
          this.fullName = localStorage.getItem('fullName');
          console.log(this.fullName);
          console.log(sender);
              id = localStorage.getItem('userId');
              FetchDataService.getAllUserMessages(id)
                  .then(response => {
                      this.messages = response.data.reverse(); // Reverse the messages array
                  })
                  .catch(error => {
                      console.error("Error fetching messages:", error);
                  });
        },
        formatDate(timestamp) {
              return new Date(timestamp).toLocaleString();
        },
        submitForm() {
            const recipientId = parseInt(this.recipientId);
            const content = this.messageContent;
            let messageData;
              messageData = {
                userId: parseInt(localStorage.getItem('userId')), 
                workerId: recipientId, 
                content: content
              };
                              
            FetchDataService.sendMessage(messageData)
                .then(response => {
                    console.log("Message sent:", response.data);
                    this.fetchMessages();
                })
                .catch(error => {
                    console.error("Error sending message:", error);
                });
        }
      },
  
      mounted() {
          // Check if token exists in local storage
          if (localStorage.getItem('token')) {
          this.fetchMessages();
          }
      }
  };
  </script> -->


<style scoped>
.container-cards {
  width: 95%;
  margin: auto;
  /*background-color: aqua;*/
  display: flex;
}

.container {
  width: 80%;
  padding: 20px;
  overflow-y: scroll;
  overflow-x: auto;
  max-height: 730px;
}

.message-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.message {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 20px;
  background-color: #f9f9f9;
}

.message-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.sender-info {
  display: flex;
  flex-direction: column;
}

.sender-name {
  font-weight: bold;
  margin: 0;
}

.timestamp {
  color: #888;
  margin-left: 40px;
  margin-right: 0px;
  max-width: auto;
  width: auto;
}

.message-content {
  line-height: 1.6;
}

.profile {
  display: block;
  margin-top: 25px;
  height: 150px;
  width: 150px;
}

.cards-description {
  text-align: center;
  width: 20%;
  align-items: flex-start;
  border-radius: 0.5rem;
}

.personal-data {
  background-color: rgb(230, 239, 247);
  border-radius: 0.5rem;
  padding: 5px;
  font-size: 18px;
  margin-top: 15px;
}

.data-description {
  margin-bottom: 15px;
  padding: 2px;
  font-size: 20px;
}

.container-form {
  background-color: #dde6ee;
  height: 450px;
  border-radius: 0.5rem;
  font-size: 20px;
  margin: auto;
  margin-top: 10px;
}

form {
  padding: 2px;
  width: 80%;
  height: 350px;
  border-radius: 0.5rem;
  margin: auto;
  text-align: center;
  background-color: #205d96;
}

.ctn-title {
  margin-top: 25px;
  padding: 0.5px;
}

.title {
  margin-top: 25px;
  padding: 0.5px;
}

input {
  width: 80%;
  height: 30px;
  border-radius: 0.8rem;
  margin-left: 25px;
  background-size: 20px;
  background-repeat: no-repeat;
  margin-top: 20px;
}

button {
  padding: 0.5rem 1rem;
  background-color: #e27713;
  color: black;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
  margin-right: 5px;
  font-weight: bold;
}

textarea {
  height: 100px;
  width: 90%;
  border-radius: 4px;
  box-sizing: border-box;
  margin-left: 2rem;
  border-color: #233181;
  margin: auto;
  margin-top: 2px;
}

label {
  display: inline-block;
  color: black;
  text-align: left;
  margin: auto;
  margin-top: 2px;
  margin-bottom: 2px;
  color: blakc
}

</style>