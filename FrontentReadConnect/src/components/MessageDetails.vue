<template>
    <div class="message-detail">
      <div class="message-header">
        <h2>Chat with {{ recipientName }}</h2>
      </div>
      <div class="message-container">
        <div v-for="message in messages" :key="message.id" class="message">
          <p :class="{ 'sent': message.senderId === userId, 'received': message.receiverId === userId }">
            {{ message.content }}
          </p>
        </div>
      </div>
      <form @submit.prevent="sendMessage" class="message-input">
        <input v-model="messageContent" placeholder="Type your message..." />
        <button type="submit">Send</button>
      </form>
    </div>
  </template>
  
  <script>
  import FetchDataService from "../services/FetchDataService";
  
  export default {
    name: "MessageDetail",
    data() {
      return {
        userId: localStorage.getItem("userId"),
        messages: [],
        messageContent: "",
        recipientId: this.$route.params.user2Id,
        recipientName: ""
      };
    },
    methods: {
      fetchMessages() {
        FetchDataService.getUserMessages(this.userId, this.recipientId)
          .then(response => (this.messages = response.data))
          .catch(error => console.error("Error fetching messages:", error));
      },
      sendMessage() {
        if (this.messageContent.trim()) {
          const messageData = {
            userId: parseInt(this.userId),
            user2Id: parseInt(this.recipientId),
            content: this.messageContent
          };
          FetchDataService.sendMessage(messageData).then(() => {
            this.messageContent = "";
            this.fetchMessages();
          });
        }
      }
    },
    mounted() {
      this.fetchMessages();
      setInterval(this.fetchMessages, 3000); // Poll every 3 seconds
    }
  };
  </script>
  

  <style scoped> 

  .conversation-list {
    max-width: 400px;
    margin: 0 auto;
  }
  .conversation-item {
    display: flex;
    align-items: center;
    padding: 10px;
    cursor: pointer;
    border-bottom: 1px solid #ddd;
  }
  .user-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 15px;
  }
  .message-container {
    height: 400px;
    overflow-y: scroll;
    padding: 10px;
    background: #f9f9f9;
  }
  .sent {
    text-align: right;
    background: #d1ffd1;
    padding: 5px;
    border-radius: 8px;
  }
  .received {
    text-align: left;
    background: #e1e1e1;
    padding: 5px;
    border-radius: 8px;
  }
  </style>