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
          <h3>Profile</h3>
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
          <p class="desc-book">ISBN: {{ book.isbn }}</p>
          <p class="desc-book">Location: {{ book.location }}</p>
          <p class="desc-book">Description: {{ book.description }}</p>
          <p class="desc-book">category: {{ book.category }} </p>
          <p class="desc-book" style="margin-top: -12px; margin-bottom: 10px;">
            <!-- <div class="rating-stars"> -->
              <span v-for="star in 5" :key="star" 
                    :class="['star', star <= (  book.totalRatingScore /   book.ratingCount  ) ? 'filled' : '']" >
                  ★
              </span>
          <!-- </div>  -->
         </p> 
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

  <!-- if hidden profile-->
  <dv v-else>
    <h1 class="hiddenUser">This User's profile Mode Is hidden!</h1>
  </dv>
</template>

<script>
import FetchDataServices from '../services/FetchDataService'
import BookService from '@/services/BookService';
// import StarRating from 'vue-star-rating';

export default {
  name: "UserDisplay",

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
      BookService.getBookByUserId(id)
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
    getRandomBookImage() {
      const randomIndex = Math.floor(Math.random() * 6) + 1; // Generate a number between 1 and 6
      return `../images/book${randomIndex}.jpg`; // Construct the image path
    },

    //going to message page carring reciverId
    sendData(receiverId) {
      this.$router.push({ name: "MessageChat", params: { receiverId: receiverId } })
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
      this.fetchBooks(DisplayuserId);

    } else {
      console.error('DisplayuserId is undefined');
      this.message = "NA"
    }
  },
}
</script>

<style scoped>
.container-cards {
  max-width: 1400px;
  margin: auto;
  display: flex;
  font-size: 1rem;
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

.book-container {
  font-size: large;
  text-align: left;
  margin-left: 20px;
  margin-bottom: 20px;
  color: #ff7b00;
}

.card-books {
  width: 70%;
  text-align: center;
  margin-top: 20px;
}

.book {
  margin-left: 20px;
  margin-bottom: 20px;
  width: 80%;
  border-radius: 0.5rem;
  text-align: left;
  background-color: #e0e0e059;
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

h3 {
  margin-top: 15px;
  width: 90%;
  text-align: center;
}

.bookImg {
  border-radius: 10px;
  width: 200px;
  margin-left: auto;
  height: 230px;
  object-fit: cover;
  margin-bottom: 10px;
  border-radius: 5px;
}

.desc-books1 {
  text-align: right;
  margin-right: 15px;
  color: rgb(85, 80, 76)
}

.error-message {
  color: white;
}
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
button {
  padding: 0.5rem 1rem;
  background-color: #ff8800;
  color: white;
  border-radius: 10px;
  cursor: pointer;
  margin: 10px auto 0 auto;
  border: #5c7b97;
}

.hiddenUser{
  width: 100%;
  text-align: center;
  padding: 50px;
  margin-top: 200px;
  font-size: 2rem;
}
</style>