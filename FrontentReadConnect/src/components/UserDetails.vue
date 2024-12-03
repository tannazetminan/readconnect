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
          <h2>Profile
          <img src="images/editar.png" class="edit" @click="editPreferences" /></h2>
        </div>
        <div v-if="showForm" class="container-profile">
          <form @submit.prevent="savePreferences">
            <input type="text" id="username" v-model="profile_settings.username" placeholder="Username" style="margin: 0;">
            <!-- <input type="text" id="email" v-model="profile_settings.email" placeholder="Email"> -->
            <input type="text" id="phone" v-model="profile_settings.phone" placeholder="Phone" style="margin: 0;">
            <input type="text" id="age" v-model="profile_settings.age" placeholder="Age" style="margin: 0;">
            <input type="text" id="country" v-model="profile_settings.country" placeholder="Country" style="margin: 0;">
            <input type="text" id="intrests" v-model="profile_settings.intrests" placeholder="psychology" style="margin: 0;">
            <input type="text" id="mode" v-model="profile_settings.mode" placeholder="false" style="margin: 0;">
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
          <h3 class="title">Post a New Book</h3>
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
          <div class="rating-stars">
              <span v-for="star in 5" :key="star" 
                    :class="['star', star <= (  book.totalRatingScore /   book.ratingCount  ) ? 'filled' : '']" >
                  ★
              </span>
          </div>     
        </div>
        <div style="display: block; float: right;">
          <img v-if="book.image" :src="getImageSrc(book.image)" alt="Book Image" class="bookImg" />
          <img v-else :src="getRandomBookImage()" alt="Book Image" class="bookImg" />
        </div>
        <!-- <p class="desc-book">
          <star-rating v-model:rating="book.rating" star-size="35" show-rating=False animate=true
            @update:rating="setRating(book.id, $event)">
          </star-rating>
        </p> -->
      </div>

    </div>
  </div>
  <FooterMenu />

</template>

<script>
import FetchDataServices from '../services/FetchDataService';
import BookService from "../services/BookService";
import FooterMenu from './Footer.vue';

// import StarRating from 'vue-star-rating';

export default {
  name: "userDetails",
  components: {
    FooterMenu
  },
  // components: {
  //   StarRating,
  // },

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
      const createDate = new Date().toISOString().split("Z")[0];
      formData.append("createDate", createDate);

      if (this.newBook.image) {
        formData.append('image', this.newBook.image);
      }

      BookService.postNewBook(userId, formData)
        .then(response => {
          console.log(response);
          this.fetchBooks(userId);
          this.clearForm();
        })
        .catch(error => {
          console.error(error.response?.data || error);
          this.errorMessage = error.response?.data?.message || "";
        });
    },
    onFileChange(event) {
      const file = event.target.files[0];
      this.newBook.image = file;
    },
    formatTimestamp(timestamp) {
        const date = new Date(timestamp);
        return date.toLocaleString(); 
    },

    //showing books written by this user
    fetchBooks(id) {
      BookService.getBookByUserId(id)
        .then(response => {
          this.books = response.data.reverse();
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
    getRandomBookImage() {
      const randomIndex = Math.floor(Math.random() * 6) + 1; // Generate a number between 1 and 6
      return `images/book${randomIndex}.jpg`; // Construct the image path
    },

    setRating(bookId, rating) {
      BookService.setbookRating(bookId, rating)
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
  width: 90%;
  max-width: 1400px;
  margin: auto;
  display: flex;
  flex-wrap: wrap;
  font-size: 1rem;
  gap: 20px;
  margin-top: 100px;
}

/* Profile Section */
.profile {
  display: block;
  margin: 20px auto;
  height: 250px;
  width: 250px;
  border-radius: 50%;
  border: 3px solid #5c7b97;
}

.personal-data {
  background-color: #f8f9fb;
  border-radius: 0.5rem;
  padding: 15px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin: 10px;
  margin-bottom: 25px;
  text-align: center;
}

.container-profile{
  padding: 0;
}

.cards-description {
  width: 30%;
  align-items: flex-start;
  border-radius: 0.5rem;
  min-width: 300px;
}

.data-description {
  margin: 10px 0;
  color: #333;
  text-align: center;
  font-weight: bold;
}

.setting {
  background-color: #f8f9fb;
  border-radius: 0.5rem;
  padding: 15px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin: 10px;
}

.edit {
  width: 35px;
  height: 35px;
  margin-left: auto;
  display: flex;
  margin-top: 0px;
}

h2 {
  display: flex;
  text-align: left;
}

form {
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin: 20px 0;
}

input,
textarea,
select {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #cdd9e3;
  border-radius: 5px;
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
  margin: 5px;
  height: 40px;
}

button {
  padding: 10px;
  background-color: #e27713;
  color: white;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  width: 100%;
}

button:hover {
  background-color: #cf6b0b;
}

/* Book Section */
.card-books {
  flex: 1;
  width: 95%;
  margin-top: 20px;
  padding: 20px;
  background-color: #f4f6f9;
  border-radius: 0.5rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.book {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 15px;
  margin: 15px 0;
  background-color: #e0e0e059;
  border-radius: 0.5rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.desc-book {
  font-size: 0.9rem;
  color: #333;
  line-height: 1.4;
  margin-bottom: 5px;
}

.bookImg {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  width: 200px;
  margin-left: auto;
  height: 230px;
  object-fit: cover;
  margin-bottom: 10px;
  border-radius: 5px;
}

.container-form {
  background-color: #dde6ee;
  border-radius: 0.5rem;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.error-message {
  color: red;
  font-size: 0.85rem;
  text-align: center;
}


.rating-stars {
    display: flex;
    gap: 8px;
    margin-top: 10px;
}

.star {
    font-size: 1.75rem;
    color: #ddd;
    transition: color 0.3s;
}

.star.filled {
    color: #ff9800;
}

#imgInput{
  border: 0;
  margin: 0px;
  height: auto;
}

</style>