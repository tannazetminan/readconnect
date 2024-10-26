import http from "../http-common.js";

class FetchDataService {

    createUser(userData) {
        return http.post("/users", userData); 
    }

    updateUser(id, data){
        return  http.put(`/users/${id}`, data)
    }

    
    getUserById(id){
        return http.get(`/users/${id}`)
    }
    
    postNewBook(writerId, data) {
        return http.post(`/books/create?writerId=${writerId}`, data, {
            headers: {
                'Content-Type': 'multipart/form-data' 
            }
        });
    }
    // postNewBook(writerId, data){
    //     return http.post(`/books/create?writerId=${writerId}`, data)
    // }

    getBookByUserId(userId){
        return http.get(`/books/user/${userId}`);
    }

    getAllBooks(){
        return http.get(`/books`);
    }

    getBookById(id){
        return http.get(`/books/${id}`);
    }

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
    
}

export default new FetchDataService();
