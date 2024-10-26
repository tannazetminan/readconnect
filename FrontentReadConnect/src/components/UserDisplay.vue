<template>
  <div v-if="user.mode == false" class="container-cards">

    <!--about user section-->
    <div class="cards-description">

      <!--profile section-->
      <div class="personal-data">
        <img src="../../public/images/people.png" class="profile" />
        <p class="data-description">{{ user.username }}</p>
        <p class="data-description">{{ user.email }}</p>
      </div>

      <!--edit profile section-->
      <div class="setting">
        <div class="edit-preferences">
          <h2>Profile</h2>
        </div>
        <div>
          <p><strong>Username:</strong> {{ user.username }}</p>
          <!-- <p><strong>Email:</strong> {{ user.email }}</p> -->
          <p><strong>Phone:</strong> {{ user.phone }}</p>
          <p><strong>Age:</strong> {{ user.age }}</p>
          <p><strong>Country:</strong> {{ user.country }}</p>
          <p><strong>Interest:</strong> {{ user.intrests }}</p>
          <p><strong>Profile Mode:</strong> <span v-if="user.mode == false">Visible</span><span v-else>Hidden</span></p>
          <button>
            <span @click="sendData(user.id)">Send Message</span>
            </button>

        </div>
      </div>
    </div>


    <!--showin books section-->
    <div class="card-books">
      <div class="book-container">Books Created By user:</div>
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
          <img v-else src="../../public/images/book.jpg" alt="Book Image" class="bookImg" />
        </div>
        <p class="desc-book">
          <star-rating v-model:rating="book.rating" star-size="35" show-rating=False animate=true
            @update:rating="setRating(book.id, $event)">
          </star-rating>
        </p>
      </div>
    </div>

  </div>

  <!-- if hidden profile-->
  <dv v-else>
    <h2>This User Is hidden!</h2>
  </dv>
</template>

<script>
import FetchDataServices from '../services/FetchDataService'
import StarRating from 'vue-star-rating';

export default {
  name: "UserDisplay",

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
      books: [],
      errorMessage: "",
      rating: "Rating not available",
    }

  },

  methods: {
    //showing user data
    retrieveUser(DisplayuserId) {
      let id = localStorage.getItem('userId')
      let fullName;
      this.fullName = localStorage.getItem('fullName')
      console.log(fullName);
      console.log("SID:" + id)

      FetchDataServices.getUserById(DisplayuserId)
        .then(response => {
          this.user = response.data;
          console.log(this.user.mode)
          this.fetchBooks(DisplayuserId);
        })
        .catch(error => {
          console.log(error)
        })
    },

    //cheking to make sure user is logged in
    checklogin() {
      console.log("")
      localStorage.setItem('newLogin', false);
      this.newLogin = localStorage.getItem('newLogin');
      console.log(this.newLogin)
      window.location.reload();
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

    //going to message page carring reciverId
    sendData(receiverId) {
      this.$router.push({ name: "MessagesDisplay", params: { receiverId: receiverId } })
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
    },



  },
  mounted() {
    const DisplayuserId = this.$route.params.userId;
    if (DisplayuserId) {
      this.retrieveUser(DisplayuserId);
    } else {
      console.error('DisplayuserId is undefined');
      this.message = "NA"
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
  width: 20%;
  height: 850px;
  align-items: flex-start;
  border-radius: 0.5rem;
}

.book-container {
  font-size: large;
  text-align: left;
  margin-left: 20px;
  margin-bottom: 20px;
  color: rgb(21, 80, 29);
}

.card-books {
  width: 70%;
  text-align: center;
  margin-left: 100px;
  margin-top: 20px;
}

.book {
  margin-left: 20px;
  margin-bottom: 20px;
  width: 80%;
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
  height: 350px;
  margin-bottom: 50px;
  background-color: rgb(230, 239, 247);
  border-radius: 0.5rem;
  margin-top: 15px;
  padding: 5px;
  text-align: left;
}

button {
  padding: 0.5rem 1rem;
  background-color: #e27713;
  color: black;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin: 10px auto 0 auto;
  font-weight: bold;
  margin-left: auto;
  margin-right: auto;
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


.error-message {
  color: white;
}
</style>