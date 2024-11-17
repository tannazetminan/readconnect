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
    
}

export default new FetchDataService();
