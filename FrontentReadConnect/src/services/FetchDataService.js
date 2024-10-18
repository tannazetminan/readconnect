import http from "../http-common.js";

class FetchDataService {

    createUser(userData) {
        return http.post("/users", userData); 
    }

    updateUser(sid, data){
        return  http.put(`/users/${sid}`, data)
    }

    
    getUserById(sid){
        return http.get(`/users/${sid}`)
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
        return http.get(`/books`)

    }
    
}

export default new FetchDataService();
