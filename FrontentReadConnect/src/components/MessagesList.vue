<template>
  <div class="container">
    <h2 class="title">Your Conversations</h2>
    <div v-if="conversations.length > 0" class="conversation-list">
      <div v-for="conversation in conversations" :key="conversation.userId" 
           class="conversation-item" @click="openConversation(conversation.userId)">
        <img src="../../public/images/people.png" class="avatar" alt="User Avatar">
        <div class="conversation-info">
          <p class="username">{{ conversation.username }}</p>
          <p class="last-message">{{ conversation.lastMessage }}</p>
        </div>
        <span class="timestamp">{{ formatDate(conversation.timestamp) }}</span>
      </div>
    </div>
    <div v-else>
      <p>No conversations to display.</p>
    </div>
  </div>
</template>

<script>
import MessageService from "../services/MessageService";


export default {
  name: "MessagesList",

  data() {
    return {
      conversations: [], // Stores each conversation with latest message
      senderId: parseInt(localStorage.getItem("userId")),
    };
  },

  methods: {
    // Fetch latest messages from each conversation
    async fetchConversations() {
    try {
      const response = await MessageService.getUserConversations(this.senderId);
      
      if (!response.data || !Array.isArray(response.data)) {
        throw new Error("Unexpected response structure");
      }

      this.conversations = response.data.map(conv => ({
        userId: conv.otherUserId,
        username: conv.otherUsername,
        lastMessage: conv.lastMessageContent || "No messages yet",
        timestamp: conv.lastMessageTimestamp || new Date().toISOString()
      }));
    } catch (error) {
      console.error("Error fetching conversations:", error);
    }
  },

    // Navigate to messageChat with the selected user
    openConversation(receiverId) {
      this.$router.push({ name: "MessageChat", params: { receiverId } });
    },

    // Format timestamp
    formatDate(timestamp) {
      return new Date(timestamp).toLocaleString();
    }
  },

  mounted() {
    this.fetchConversations();
  }
};
</script>

<style scoped>
.container {
  width: 80%;
  margin: auto;
  padding: 20px;
}

.title {
  text-align: center;
  font-size: 1.8rem;
  color: #3a3a3a;
}

.conversation-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.conversation-item {
  display: flex;
  align-items: center;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.conversation-item:hover {
  background-color: #ececec;
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 15px;
}

.conversation-info {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.username {
  font-weight: bold;
  color: #333;
  font-size: 1.1rem;
}

.last-message {
  color: #555;
  font-size: 0.9rem;
}

.timestamp {
  color: #888;
  font-size: 0.8rem;
  white-space: nowrap;
}
</style>
