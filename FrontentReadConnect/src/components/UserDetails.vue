<template>
  <div class="container-cards">

    <div class="cards-description">

      <!--profile section-->
      <div class="personal-data">
        <img src="images/people.png" class="profile" />
        <p class="data-description">{{ this.fullName }}</p>
        <p class="data-description">{{ user.email }}</p>
      </div>

      <!--edit profile section-->
      <div class="setting">
        <div class="edit-preferences" @click="editPreferences">
          <h2>Profile</h2>
          <img src="images/editar.png" class="edit" @click="editPreferences" />
        </div>
        <div v-if="showForm" class="container-profile">
          <form @submit.prevent="savePreferences">
            <input type="text" id="username" v-model="profile_settings.username" placeholder="Username">
            <!-- <input type="text" id="email" v-model="profile_settings.email" placeholder="Email"> -->
            <input type="text" id="phone" v-model="profile_settings.phone" placeholder="Phone">
            <input type="text" id="age" v-model="profile_settings.age" placeholder="Age">
            <input type="text" id="country" v-model="profile_settings.country" placeholder="Country">
            <input type="text" id="intrests" v-model="profile_settings.intrests" placeholder="psychology">
            <input type="text" id="mode" v-model="profile_settings.mode" placeholder="false">
            <button type="submit" @click="savePreferences">Save</button>
            <button type="submit" @click="hideForm">Cancel</button>
          </form>
        </div>
        <div v-else>
          <p><strong>Username:</strong> {{ user.username }}</p>
          <!-- <p><strong>Email:</strong> {{ user.email }}</p> -->
          <p><strong>Phone:</strong> {{ user.phone }}</p>
          <p><strong>Age:</strong> {{ user.age }}</p>
          <p><strong>Country:</strong> {{ user.country }}</p>
          <p><strong>Interest:</strong> {{ user.intrests }}</p>
          <p><strong>Profile Mode:</strong> <span v-if="user.mode == false">Visible</span><span v-else>Hidden</span></p>
        </div>
      </div>

    </div>


    <!--showin books section-->
    <div class="card-books">

      <!-- Post a New Book Section -->
      <div class="container-form">
        <div class="ctn-title">
          <h2 class="title">Post a New Book</h2>
        </div>
        <form @submit.prevent="handleSubmit">
          <div class="container-lbl">
            <select v-model="newBook.category" required>
              <option value="" disabled>Select Category</option>
              <option value="Psychology">Psychology</option>
              <option value="General">General</option>
              <option value="Historical">Historical</option>
              <option value="Science">Science</option>
              <option value="Literature">Literature</option>
            </select>
            <input type="text" v-model="newBook.title" required placeholder="Book Title" />
            <input type="text" v-model="newBook.author" required placeholder="Author" />
            <input type="textArea" v-model="newBook.description" required placeholder="Description" />
            <input type="text" v-model="newBook.isbn" required placeholder="ISBN" />
            <input type="text" v-model="newBook.location" placeholder="Location" />
            <input type="file" @change="onFileChange" accept="image/*" id="imgInput" >
            <button type="submit">Send</button>
            <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
          </div>
        </form>
      </div>


      <!-- Show existing books-->
      <div v-for="book in books" :key="book.id" class="book">
        <div style="display: block; float: left;">
          <p class="desc-book"><span style="font-weight: bold;">Title: </span>{{ book.title }} </p>
          <p class="desc-book">Author: {{ book.author }} </p>
          <p class="desc-book">category: {{ book.category }} </p>
          <p class="desc-book">Description: {{ book.description }}</p>
          <p class="desc-book">ISBN: {{ book.isbn }}</p>
          <p class="desc-book">Location: {{ book.location }}</p>
        </div>
        <div style="display: block; float: right;">
          <img v-if="book.image" :src="getImageSrc(book.image)" alt="Book Image" class="bookImg" />
          <img v-else src="images/book.jpg" alt="Book Image" class="bookImg" />
        </div>
        <p class="desc-book">
          <star-rating v-model:rating="book.rating" star-size="35" show-rating=False animate=true
            @update:rating="setRating(book.id, $event)">
          </star-rating>
        </p>
      </div>

    </div>
  </div>
</template>

<script>
import FetchDataServices from '../services/FetchDataService'
import StarRating from 'vue-star-rating';

export default {
  name: "userDetails",

  components: {
    StarRating,
    //HeaderComponent,
  },

  data() {
    return {
      newLogin: false,
      user: {
        username: "",
        email: "",

      },
      profile_settings: {
        username: "",
        country: "",
        password: "",
        phone: "",
        email: "",
        age: "",
        intrests: "",
        mode: false,
      },
      showForm: false,
      newBook: {
        title: '',
        author: '',
        isbn: '',
        location: '',
        description: '',
        image: null,
      },
      books: [],
      errorMessage: "",
      errorMessage2: "",
      rating: "Rating not available",
    }

  },

  methods: {
    //showing user data
    retrieveUser() {
      let id = localStorage.getItem('userId')
      let fullName;
      this.fullName = localStorage.getItem('fullName')
      console.log(fullName);

      console.log("SID:" + id)
      FetchDataServices.getUserById(id)
        .then(response => {
          this.user = response.data;
          this.fetchBooks(id);
        })
        .catch(error => {
          console.log(error)
        })
    },

    //showing & editing profile
    editPreferences() {
      this.showForm = true;
    },
    hideform() {
      this.showForm = false;
    },
    savePreferences(event) {
      event.preventDefault();
      const updatedProfile = {
        username: this.profile_settings.username || this.user.username,
        country: this.profile_settings.country || this.user.country,
        phone: this.profile_settings.phone || this.user.phone,
        age: this.profile_settings.age || this.user.age,
        intrests: this.profile_settings.intrests || this.user.intrests,
        mode: this.profile_settings.mode || this.user.mode,
      };

      FetchDataServices.updateUser(this.user.id, updatedProfile)
        .then(response => {
          this.user = { ...this.user, ...updatedProfile };
          this.showForm = false;
          this.clearProfileForm();
          console.log(response)
        })
        .catch(error => {
          console.error(error);
        });
    },

    clearProfileForm() {
      this.profile_settings = {
        username: "",
        country: "",
        phone: "",
        email: "",
        age: "",
        mode: false,
      };
    },

    //cheking to make sure user is logged in
    checklogin() {
      console.log("")
      localStorage.setItem('newLogin', false);
      this.newLogin = localStorage.getItem('newLogin');
      console.log(this.newLogin)
      window.location.reload();
    },


    //posting a new book
    handleSubmit() {
      let userId = localStorage.getItem('userId');

      if (this.newBook.title.trim() === '' || this.newBook.author.trim() === '' || this.newBook.isbn.trim() === '') {
        this.errorMessage = "All fields are required";
        return;
      }

      let formData = new FormData();
      formData.append('title', this.newBook.title);
      formData.append('author', this.newBook.author);
      formData.append('isbn', this.newBook.isbn);
      formData.append('location', this.newBook.location);
      formData.append('category', this.newBook.category);
      formData.append('description', this.newBook.description);

      if (this.newBook.image) {
        formData.append('image', this.newBook.image);
      }

      FetchDataServices.postNewBook(userId, formData)
        .then(response => {
          console.log(response);
          this.fetchBooks(userId);
          this.clearForm();
        })
        .catch(error => {
          console.error(error.response?.data || error);
          this.errorMessage = error.response?.data?.message || "An error occurred";
        });
    },
    onFileChange(event) {
      const file = event.target.files[0];
      this.newBook.image = file;
    },


    //showing books written by this user
    fetchBooks(id) {
      FetchDataServices.getBookByUserId(id)
        .then(response => {
          this.books = response.data;
        })
        .catch(error => {
          if (error.response) {
            console.log(error.response.data);
            console.log(error.response.status);
          }
        });
    },
    getImageSrc(image) {
      return `data:image/jpg;base64,${image}`;
    },

    setRating(bookId, rating) {
      FetchDataServices.setbookRating(bookId, rating)
        .then(response => {
          console.log("Book rating updated:", bookId, "Rating:", rating);
          console.log(response)
        })
        .catch(error => {
          console.error("Error updating book rating:", error);
        });
    }

  },
  mounted() {
    this.retrieveUser();
    let newLogin = localStorage.getItem('newLogin');
    console.log("userdetails newlogin:" + newLogin);
    if (newLogin === 'true') { // Check if newLogin is true
      localStorage.setItem('newLogin', false);
      this.checklogin();
    }
  },
}
</script>

<style scoped>
.container-cards {
  width: 95%;
  margin: auto;
  display: flex;
  font-size: 1rem;
}

.profile {
  display: block;
  margin-top: 25px;
  height: 150px;
  width: 150px;
}

.cards-description {
  text-align: center;
  width: 25%;
  align-items: flex-start;
  border-radius: 0.5rem;
}

.card-books {
  width: 69%;
  text-align: center;
  margin-left: auto;
  margin-right: auto;
  margin-top: 20px;
}

.book {
  margin: 20px auto;
  width: 100%;
  border-radius: 0.5rem;
  text-align: left;
  background-color: rgb(210, 219, 224);
  padding: 10px;
  clear: both;
  height: 250px;
}

.desc-book {
  margin-left: 30px;
}

.cards {
  background-color: rgb(121, 168, 194);
  align-items: flex-start;
}

.personal-data {
  background-color: rgb(230, 239, 247);
  border-radius: 0.5rem;
  padding: 5px;
  margin-top: 15px;
}

.data-description {
  margin-bottom: 15px;
  padding: 2px;
}

.setting {
  margin-bottom: 50px;
  background-color: rgb(230, 239, 247);
  border-radius: 0.5rem;
  margin-top: 15px;
  padding: 5px;
  text-align: left;
}

.title-books {
  text-align: left;
}

.fetchData {
  margin-left: 30px;
  margin-bottom: 40px;
  background-color: transparent;
  border: none;
  color: rgb(60, 172, 15);
  cursor: pointer;
  padding: 0;
  font-weight: bold;
}

.edit {
  width: 30px;
  height: 40px;
}

.edit-preferences {
  display: flex
}

h2 {
  margin-top: 15px;
  width: 90%;
  text-align: center;
}

.bookImg {
  border-radius: 10px;
  width: 200px;
  margin-left: auto;
}

.desc-books1 {
  text-align: right;
  margin-right: 15px;
  color: rgb(85, 80, 76)
}

.container-apply-btn {
  text-align: right;
}

.apply-btn {
  margin-left: 30px;
  margin-bottom: 20px;
  background-color: transparent;
  border: none;
  color: rgb(214, 132, 65);
  cursor: pointer;
  padding: 0;
  font-weight: bold;
  margin-right: 15px;
}

.container-form {
  background-color: #dde6ee;
  height: 450px;
  border-radius: 0.5rem;
  margin: auto;
  margin-top: 10px;
}

form {
  padding: 2px;
  width: 80%;
  height: 350px;
  border-radius: 0.5rem;
  margin: auto;
  text-align: center;
  /* background-color: #5c7b97; */
}

.ctn-title {
  margin-top: 25px;
  padding: 0.5px;
}

.title {
  margin-top: 25px;
  padding: 0.5px;
}

.container-lbl {
  text-align: left;
  margin-left: 15px;
  margin-bottom: 2px;
  margin-top: 10px;
}

.error-message {
  color: white;
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
  padding: 0.6rem;
  border-radius: 0.5rem;
  border: 1px solid #272875;
  font-size: 0.9rem;
  margin-bottom: 3px;
}

button {
  padding: 0.5rem 1rem;
  background-color: #e27713;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1.05rem;
  width: 200px;
  color: white;
  margin-bottom: 10px;
}

#imgInput{
  border: 0;
  margin: 0px;
  height: auto;
}


</style>