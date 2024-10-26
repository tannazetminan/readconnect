<template>
    <div class="conversation-list">
      <h2>Conversations</h2>
      <div v-for="user in conversations" :key="user.id" class="conversation-item" @click="openConversation(user.id)">
        <img :src="user.profileImage || defaultImage" class="user-avatar" />
        <div class="conversation-details">
          <p class="username">{{ user.username }}</p>
          <p class="last-message">Last message preview here...</p>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import FetchDataService from "../services/FetchDataService";
  
  export default {
    name: "MessagesList",
    data() {
      return {
        conversations: []
      };
    },
    methods: {
      fetchConversations() {
        const userId = localStorage.getItem("userId");
        FetchDataService.getUserConversations(userId)
          .then(response => {
            this.conversations = response.data;
          })
          .catch(error => console.error("Error fetching conversations:", error));
      },
      openConversation(user2Id) {
        this.$router.push({ name: "MessageDetail", params: { user2Id } });
      }
    },
    mounted() {
      this.fetchConversations();
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