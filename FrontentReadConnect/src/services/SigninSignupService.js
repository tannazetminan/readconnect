import http from "../http-common.js";

class LoginService{
    login(data){
        return http.post("/users/login", data)
    }

    checkEmail(email) {
        return http.get("/users/check-email", { params: { email } });
    }
}

export default new LoginService();