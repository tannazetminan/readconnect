<template>
  <div class="container-login">
    <img src="images/login.jpg" class="img-login" />

    <div class="container-form">
      <div class="container-grettings">
        <h2 class="grettings">Welcome to Read Connect</h2>
        <!-- <img src="images/waving-hand.png" class="img-hands" /> -->
      </div>
      <form @submit.prevent="login">
        <div>
          <label for="email">EMAIL:</label>
          <input type="email" id="email" placeholder="Enter your email" v-model="userLogin.email" />
        </div>
        <div>
          <label for="password">PASSWORD:</label>
          <input type="password" id="password" placeholder="Enter your password" v-model="userLogin.password" />
        </div>
        <div>
          <button type="submit">Login</button>
        </div>
        <br><router-link to="/signup" class="nav-link">Don't have an account? Sign up</router-link>

        <!-- Display error message if login fails -->
        <div v-if="errorMessage" class="error-message">
          <br />{{ errorMessage }}
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import LoginService from "../services/SigninSignupService";
import router from "../router/index.js";

export default {

  name: "UserLogin",

  data() {
    return {
      userLogin: {
        email: "",
        password: ""
      },
      errorMessage: null,
    };
  },

  methods: {
    login() {
      LoginService.login(this.userLogin)
        .then((response) => {
          console.log(response);
          let userId = response.data.record.id
          //let user = response.data;
          let userType = response.data.type;
          localStorage.setItem('token', response.data.token);

          //for storing if it is user or worker
          localStorage.setItem('userType', userType);

          // //for storing the fullname
          // let parts = response.data.record.username.split("_");
          // if (parts[1] != null || parts[1]!="")
          // localStorage.setItem('fullName', parts[1]); 
          let fullname = response.data.record.username;
          localStorage.setItem('fullName', fullname);

          //for updating navbar
          localStorage.setItem('newLogin', true);
          let newLogin = localStorage.getItem('newLogin');
          console.log("is it newlogin:" + newLogin);

          localStorage.setItem('userId', userId);
          console.log(userId);
          this.isLoggedIn = true;
          router.push("/userdetails");


        })
        .catch((error) => {
          console.error(error);
          if (error.response) {
            console.log(error.response.data);
            console.log(error.response.status);
            this.errorMessage = error.response.data.message || "Error logging in. Please try again.";
          } else {
            this.errorMessage = "An unexpected error occurred. Please try again.";
          }
        });
    }
  },

  mounted() {
    localStorage.setItem('newLogin', false);
    let newLogin;
    newLogin = localStorage.getItem('newLogin');
    console.log("is it newlogin:" + newLogin);
  }

};
</script>

<style scoped>
.container-login {
  display: flex;
  justify-content: center;
  padding-top: 50px;
}

.img-login {
  float: left;
  margin-left: 5rem;
  width: 49%;
  height: auto;
  max-height: min-content;
  border-radius: 25px;
  flex-shrink: -5;
  max-width: 1000px;
}

form {
  border-radius: 0.5rem;
  text-align: left;
}

label {
  margin-left: 2rem;
  display: block;
  color: black;
  margin-top: 15px;
  text-align: left;
}

input {
  margin-top: 5px;
  padding: 10px;
  font-size: 0.9rem;
  border: 1px solid #30206b;
  border-radius: 5px;
  margin-bottom: 10px;
  margin-left: 2rem;
  width: 80%;
}

button {
  padding: 0.8rem;
  background-color: #e27713;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
  margin-left: 2rem;
  font-weight: bold;
  width: 80%;
  padding: 10px;
}

.container-form {
  width: 40%;
  text-align: center;
  float: left;
}

.container-grettings {
  margin-top: 50px;
  display: inline-block;
  margin-right: 5px;
}

.grettings {
  display: inline-block;
  color: #e27713;
  font-size: x-large;
}

.error-message {
  margin: auto;
  margin-left: 2rem;
  margin-left: 0;
  color: #e27713;
  text-align: center;
  margin-top: 10px;
}

.nav-link {
  padding: 0.5rem;
  margin-top: 15px;
  color: #171653;
  font-weight: bold;
  text-align: center;
  margin-left: 2rem;
}

@media (max-width: 1000px) {
  .img-login{
    visibility: hidden;
    width: 0%;
    display: block;
  }
  .login-form {
  display: grid;
  align-items: center;
  width: 90%;
  margin: 0px;
  padding: 0%;
}
.container-form {
  width: 100%;
  text-align: center;
  padding: 0%;
}
.container-login {
  display: block;
  padding: 0;
}
}
</style>