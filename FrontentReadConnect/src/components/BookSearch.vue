<template>
  <div class="search-container">
    <h1>Advanced Book Search</h1>

    <!-- Search Form -->
    <div class="search-form">
      <input v-model="query" placeholder="Book title or keywords" />
      <input v-model="author" placeholder="Author" />
      <input v-model="category" placeholder="Category" />
      <input v-model="publisher" placeholder="Publisher" />
      <select v-model="orderBy" class="sort-options">
        <option value="">Sort By</option>
        <option value="relevance">Relevance</option>
        <option value="newest">Newest</option>
      </select>
      <button @click="fetchBooks">Search</button>
      <!-- Category Filtering -->
      <div class="topics">
        <button v-for="topic in topics" @click="fetchByTopic(topic)" :key="topic">{{ topic }}</button>
      </div>
    </div>

    <!-- Book Grid Layout -->
    <div v-if="books && books.items && books.items.length" class="book-grid">
      <div class="book-card" v-for="book in books.items" :key="book.id">
        <!-- Book Cover -->
        <img :src="book.volumeInfo.imageLinks?.thumbnail" alt="Book cover" />
        <!-- Book Title -->
        <h3>{{ book.volumeInfo.title }}</h3>
        <!-- Book Authors -->
        <p><b>Author:</b> {{ book.volumeInfo.authors?.join(', ') }}</p>
        <!-- Publisher -->
        <p><b>Publisher:</b> {{ book.volumeInfo.publisher }}</p>
        <!-- Publish Date -->
        <p><b>Published Date:</b> {{ book.volumeInfo.publishedDate }}</p>
        <!-- Price -->
        <p>{{ book.saleInfo?.retailPrice?.amount }} {{ book.saleInfo?.retailPrice?.currencyCode }}</p>
        <!-- Buy Link -->
        <a :href="book.saleInfo.buyLink" target="_blank"><b>Buy</b></a>
        <!-- Book Summary -->
        <p class="description">{{ book.volumeInfo.description }}</p>
      </div>
        <!-- Pagination -->
        <div class="pagination" v-if="books">
        <button @click="prevPage" :disabled="startIndex === 0">Previous</button>
        <button @click="nextPage">Next</button>
      </div>
    </div>
  </div>




</template>

<script>
import FetchDataServices from '../services/FetchDataService';

export default {
  data() {
    return {
      query: '',
      author: '',
      category: '',
      publisher: '',
      books: null,
      startIndex: 0, // For pagination
      maxResults: 12, // Max books per page
      orderBy: '',
      topics: ['General', 'Science', 'Historical', 'Psychology', 'Literature'],
      intrest: '',
      user: {
        username: '',
        email: '',
        credit: 0,
        fullName: '',
        intrests: [],
      },
    };
  },
  methods: {
    retrieveUser() {
      let id = localStorage.getItem('userId');
      let fullName;
      this.fullName = localStorage.getItem('fullName');
      console.log(fullName);

      console.log('SID:' + id);
      FetchDataServices.getUserById(id)
        .then((response) => {
          this.user = response.data;
          console.log(this.user.intrests[0].name);
          this.intrest = this.user.intrests[0].name;
          this.fetchByTopic(this.intrest);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    // Fetch books based on search query and advanced parameters
    async fetchBooks() {
      let searchQuery = this.query;
      if (this.author) searchQuery += `+inauthor:${this.author}`;
      if (this.category) searchQuery += `+subject:${this.category}`;
      if (this.publisher) searchQuery += `+inpublisher:${this.publisher}`;

      const apiKey = process.env.VUE_APP_API_KEY;


      let queryString = `q=${searchQuery}&maxResults=${this.maxResults}&startIndex=${this.startIndex}&key=${apiKey}`;

      // Append orderBy if selected
      if (this.orderBy) {
        queryString += `&orderBy=${this.orderBy}`;
      }

      try {
        // Make the API request with pagination and sorting
        const response = await fetch(`https://www.googleapis.com/books/v1/volumes?${queryString}`);
        if (!response.ok) {
          throw new Error(`${response.status} ${response.statusText}`);
        }
        const data = await response.json();

        if (data && data.items) {
          this.books = data;
        } else {
          this.books = null;
        }
      } catch (error) {
        if (error.message.includes('429')) {
          alert('You have reached the API request limit. Please try again later.');
        } else {
          console.error('Error fetching books:', error);
        }
      }
    },

    // Fetch books based on predefined topic/category
    fetchByTopic(topic) {
      this.category = topic;
      this.startIndex = 0; // Reset to the first page when changing the topic
      this.fetchBooks();
    },

    // Pagination methods
    nextPage() {
      this.startIndex += this.maxResults;
      this.fetchBooks();
    },
    prevPage() {
      if (this.startIndex > 0) {
        this.startIndex -= this.maxResults;
        this.fetchBooks();
      }
    },
  },

  mounted() {
    this.retrieveUser();
    let newLogin = localStorage.getItem('newLogin');
    if (newLogin === 'true') {
      localStorage.setItem('newLogin', false);
      this.checklogin();
    } else {
      this.fetchByTopic("general");
    }
  },
};
</script>

<style scoped>
/* Main container styling */
.search-container {
  padding: 20px;
  width: 95%;
  margin: 0 auto;
  text-align: center;
}

.search-container h1 {
  text-align: left;
  margin-left: 20px;
  font-size: 2.5rem;
}

/* Search Form styling */
.search-form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 25%;
  max-width: 450px;
  min-width: 350px;
  margin: 20px 20px;
  float: left;
}

/* Input and Button Styling */
.search-form input,
.search-form select {
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.search-form button {
  padding: 10px;
  background-color: #3498db;
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
}

.search-form button:hover {
  background-color: #2980b9;
}

/* Category Buttons */
.topics {
  margin: 20px 0;
}

.topics button {
  margin: 5px;
  padding: 10px 15px;
  background-color: #2ecc71;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  width: auto;
}

.topics button:hover {
  background-color: #27ae60;
}

/* Grid Layout for Books */
.book-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  margin-top: 20px;
  float: left;
  width: 70%;
}

/* Book Card styling */
.book-card {
  background-color: white;
  border-radius: 10px;
  padding: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease-in-out;
  max-height: 400px;
  overflow-y: scroll;
  overflow-x: hidden;
  text-align: center;
}

.book-card:hover {
  transform: scale(1.05);
}

/* Book Cover Styling */
.book-card img {
  max-width: 100%;
  height: auto;
  margin-bottom: 10px;
  border-radius: 5px;
}

/* Title, Author, and Other Info Styling */
.book-card h3 {
  font-size: 1.3rem;
  margin: 10px 0;
}

.book-card p {
  font-size: 0.9rem;
  color: #555;
  margin: 5px 0;
}

/* Buy Link Styling */
.book-card a {
  display: block;
  margin-top: 10px;
  text-decoration: none;
  color: #e74c3c;
}

.book-card a:hover {
  text-decoration: underline;
}

/* Summary Description Styling */
.description {
  margin-top: 15px;
  font-size: 0.8rem;
  color: #777;
  text-align: justify;
}

/* Pagination */
.pagination {
  display: block;
  text-align: center;
  align-items: center;
  width: 300px;
}

.pagination button {
  padding: 10px 20px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 45%;
  margin: 5px;
}

.pagination button:disabled {
  background-color: #ddd;
  cursor: not-allowed;
}

.pagination button:hover:not(:disabled) {
  background-color: #2980b9;
}
</style>
