import http from "../http-common.js";

class MessageService {

    // Send a new message
    sendMessage(messageData) {
        return http.post(`/messages/send`, messageData);
    }

    getAllUserMessages(userId) {
        return http.get(`/messages/user/${userId}/all`);
    }

    // Get messages between users by sender and receiver IDs
    getMessagesBetweenUsers(senderId, receiverId) {
        return http.get(`/messages/${senderId}/to/${receiverId}`);
    }

    // Fetch user ID based on username
    getUserIdByUsername(username) {
        return http.get(`/users/by-username?username=${username}`);
    }
    
    getUserConversations(userId) {
        return http.get(`/messages/conversations/${userId}`);
    }

}

export default new MessageService();
