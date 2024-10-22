<template>
  <div class="container-singup">
    <img src="images/signup2.jpg" class="img-signup" />

    <div class="container-form">
      <div class="container-grettings">
        <h2 class="grettings">Welcome to Read Connect</h2>
      </div>

      <form @submit.prevent="submitUserForm" class="signup-form">
        <div class="form-row">
          <label for="firstname">First Name:</label>
          <input type="text" id="firstname" v-model="user.firstname" placeholder="Enter your first name" required />
        </div>
        <div class="form-row">
          <label for="lastname">Last Name:</label>
          <input type="text" id="lastname" v-model="user.lastname" placeholder="Enter your last name" required />
        </div>
        <div class="form-row">
          <label for="email">Email:</label>
          <input type="email" id="email" v-model="user.email" placeholder="Enter your email" required />
        </div>
        <div class="form-row">
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="user.password" placeholder="Enter your password" pattern=".{6,}"
            required />
        </div>
        <div class="form-row">
          <label for="country">Country:</label>
          <input type="text" id="country" v-model="user.country" placeholder="Enter your country" required />
        </div>
        <div class="form-row">
          <label for="age">Age:</label>
          <input type="text" id="age" v-model="user.age" placeholder="Enter your age" required />
        </div>
        <div class="form-row">
          <label>Profile Mode:</label>
          <div class="profile-mode-options">
            <button type="button" :class="{ active: user.mode === true }" @click="setProfileMode(true)">
              Hidden Profile
            </button>
            <button type="button" :class="{ active: user.mode === false }" @click="setProfileMode(false)">
              Regular Profile
            </button>
          </div>
        </div>
        <div class="form-row">
          <label for="interests">Interests:</label>
          <select id="interests" v-model="tempIntrest" required>
            <option value="" disabled>Select interests</option>
            <option value="Psychology">Psychology</option>
            <option value="General" selected>General</option>
            <option value="Historical">Historical</option>
            <option value="Science">Science</option>
            <option value="Literature">Literature</option>
          </select>
        </div>
        <div class="form-row">
          <button type="submit">Sign up</button>
        </div>
      </form>

      <div v-if="errorMessage" class="error-message">
        <br /> {{ errorMessage }}
      </div>
    </div>
  </div>
</template>

<script>
import FetchDataService from "../services/FetchDataService";
import SigninSignupService from "@/services/SigninSignupService";
import bcrypt from 'bcryptjs';


// Define the capitalizeFirstLetter function outside of the component
function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1);
}

export default {
  name: "SignUp",
  data() {
    return {
      user: {
        username: "",
        firstname: "",
        lastname: "",
        password: "",
        email: "",
        country: "",
        age: 18,
        mode: false,
        intrests: []
      },
      tempIntrest: "",
      successMessage: null,
      errorMessage: null
    };
  },
  methods: {

    setProfileMode(isHidden) {
      this.user.mode = isHidden;
    },

    async checkEmailExists(email) {
      try {
        const response = await SigninSignupService.checkEmail(email);
        return response.data.exists;
      } catch (error) {
        console.error("Error checking email:", error);
        return false; // Default to false if there's an error
      }
    },

    addIntrests() {
      if (this.tempIntrest && !this.user.intrests.includes(this.tempIntrest)) {
        this.user.intrests.push(this.tempIntrest);
      }
      this.tempIntrest = "";
    },

    async submitUserForm() {
      // Check if the email already exists
      const emailExists = await this.checkEmailExists(this.user.email);
      if (emailExists) {
        this.errorMessage = "Email is already in use. Please use a different email.";
        this.successMessage = null;
        console.log(this.errorMessage);
        return; // Stop further execution
      }

      // Combine firstName and lastName to create the username
      // this.user.username = "user_" + capitalizeFirstLetter(this.user.firstname) + capitalizeFirstLetter(this.user.lastname);
      this.user.username = capitalizeFirstLetter(this.user.firstname) + "." + capitalizeFirstLetter(this.user.lastname);


      // Hash the password using bcrypt
      const hashedPassword = await bcrypt.hash(this.user.password, 10);

      // Set the hashed password to the user object
      this.user.password = hashedPassword;

      //updating the intrest table
      this.addIntrests();

      FetchDataService.createUser(this.user)
        .then(response => {
          console.log("User created successfully:", response.data);
          this.successMessage = "User created successfully!";
          this.errorMessage = null;
          // Redirect to the home page after a successful form submission
          this.$router.push('/login');
        })
        .catch(error => {
          console.error("Error creating user:", error);
          this.errorMessage = "Error creating user. Please try again.";
          this.successMessage = null;
        });
    }
  }
};

</script>

<style scoped>
.container-singup {
  display: flex;
  justify-content: center;
  margin-top: 100px;
}

.img-signup {
  float: left;
  margin-left: 5rem;
  width: 49%;
  height: auto;
  max-height: min-content;
  border-radius: 25px;
  max-height: 700px;
}

.signup-form {
  display: grid;
  grid-template-columns: 1.5fr 5fr;
  gap: 15px;
  align-items: center;
  width: 90%;
  max-width: 800px;
  margin: 30px auto auto 20px;
  border-radius: 0.5rem;
}

.form-row {
  display: contents;
}

label {
  margin-left: 2rem;
  text-align: left;
  margin-top: 0;
  margin-right: 0;
  font-size: 0.9rem;
}

input,
textarea,
select,
option {
  box-sizing: border-box;
  width: 100%;
  padding: 0.7rem;
  border-radius: 0.5rem;
  margin-left: 10px;
  border: 1px solid #272875;
  font-size: 0.9rem;
}

button {
  box-sizing: border-box;
  padding: 0.8rem;
  background-color: #e27713;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
  margin-left: 10px;
  margin-right: 0;
  font-weight: bold;
  grid-column: 2 / span 1;
  width: 100%;
}

.container-form {
  width: 40%;
  text-align: center;
}
.profile-mode-options {
  display: flex;
  justify-content: flex-start;
  gap: 10px;
}

.profile-mode-options button {
  padding: 0.5rem 1rem;
  border: 1px solid #272875;
  background-color: white;
  color: #272875;
  cursor: pointer;
  border-radius: 4px;
  font-weight: bold;
}

.profile-mode-options button.active {
  background-color: #e27713;
  color: white;
  border: none;
}

.profile-mode-options button:hover {
  background-color: #f39c12;
  color: white;
}


.container-grettings {
  margin-top: 10px;
  display: inline-block;
}

.grettings {
  display: inline-block;
  color: #e27713;
  font-size: x-large;
}

.error-message {
  margin: auto;
  font-size: 0.9em;
  color: #e27713;
}
</style>