<template>
  <div class="container-book">
    <!-- <div class="categories">
      <span>General</span>
      <span>Science</span>
      <span>Electrical </span>
      <span>Historical</span>
      <span>Psychology </span>
    </div> -->

    <div>
      <h1 class="title">List of Books</h1>
    </div>
    <div class="cards">
      <div v-for="book in books" :key="book.id" class="card-book">
        <p>
          <img v-if="book.image" :src="getImageSrc(book.image)" alt="Book Image" class="profile" />
          <img v-else src="images/book.jpg" alt="Book Image" class="profile" />
        </p>
        <p>Title: <span style="font-size: large;">{{ book.title }}</span></p>
        <p>Author: <span style="font-weight: bold;">{{ book.author }}</span></p>
        <p>Post Creator: {{ book.writer.username }}</p>
        <p>ISBN: {{ book.isbn }}</p>
        <!-- <p style="text-align: left; margin-left: 10px;">
          <img src="../../public/images/phone.png" width="25px" height="25px" style="margin-bottom: -7px;" /> {{
            book.phone }}<br /><br />
          <img src="../../public/images/email.png" width="30px" height="30px"
            style="margin-bottom: -10px; padding-right: 2px; margin-left: -2px;" />{{ book.email }}
        </p> -->

        <!-- <div class="rating-container">
            <img src="images/star.png" class="rating" /><span>{{ book.averageRating }}</span>
          </div> -->
        <div v-if="book.averageRating === 5">
          <img src="images/5star.png" class="rating" />
        </div>
        <div v-if="book.averageRating === 4">
          <img src="images/4star.png" class="rating" />
        </div>
        <div v-if="book.averageRating === 3">
          <img src="images/3star.png" class="rating" />
        </div>
        <div v-if="book.averageRating === 2">
          <img src="images/2star.png" class="rating" />
        </div>
        <div v-if="book.averageRating === 1">
          <img src="images/1star.png" class="rating" />
        </div>
        <button type="submit" class="button-profile" @click="sendData(book.id)">See more</button>
      </div>
    </div>
  </div>

</template>



<script>
import BookService from "../services/BookService";


export default {
  name: "BooksList",

  data() {
    return {
      books: [],
      id: 1

    }
  },

  methods: {
    fetchBooks() {
      BookService.getAllBooks()
        .then(response => {
          this.books = response.data
          console.log(response)
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

    //going to bookdetails page carring the bookId
    sendData(bookId){         
        console.log("book id", bookId)
        this.$router.push({name:"BookDetails", params: { bookId: bookId }})
    }
  },
  mounted() {
    this.fetchBooks();
  },

}
</script>

<style>
h1 {
  font-size: 50px;
  margin-left: 9rem;
  color: rgb(1, 97, 1);
  margin-top: 70px;
}


.categories {
  display: flex;
  width: 80%;
  margin-left: 10rem;
  font-size: 20px;
  font-weight: bold;
  color: #e27713;
}

.categories span {
  margin-right: 15px;
}

.cards {
  margin: auto;
  text-align: center;
  width: 70%;
  display: grid;
  grid-gap: 10px;
  margin-top: 10rem;
  grid-template-columns: repeat(4, minmax(250px, 1fr));
  text-align: center;


}

.card-book {
  box-sizing: border-box;
  border-radius: 0.5rem;
  display: flex;
  flex-direction: column;
  text-align: center;
  transition: transform 0.3s ease;
  max-width: 98%;
  font-weight: bold;
  background-color: white;
  margin-left: 20px;
  border: 1px solid rgb(31, 142, 175);
  line-height: 1;
}

.profile {
  width: 100px;
  height: 100px;
  margin: auto;
  margin-top: 15px;
}

.rating {
  max-width: 110px;
  height: 25px;
  margin: auto;
  margin-top: 15px;
  margin-bottom: 15px;
  display: inline
}

.rating-container img,
.rating-container span {
  display: inline-block;
  margin-right: 5px;

}

.subtitle-book {
  text-align: left;
  margin-bottom: 20px;
}

.button-profile {
  display: inline-block;
  padding: 0.5rem 1rem;
  background-color: #e27713;
  color: rgb(32, 20, 20);
  width: 120px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin: auto;
  margin-bottom: 10px;
}
p{
  font-weight: normal;
}
</style>