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
    
}

export default new FetchDataService();
