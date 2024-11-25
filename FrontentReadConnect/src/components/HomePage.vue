<template>

  <div class="container-img">
    <!-- <img src="images/2.jpg" class="img_back" /> -->
     <!-- Forum Header -->
    <header class="forum-header">
      <!-- <h1 class="forum-title">Book Forum</h1> -->
      <p class="forum-subtitle">Explore, discuss, and share your favorite books.</p>
    </header>

    <!-- Search and Categories -->
    <div class="forum-controls" style="background: none;">
      <input
        type="text"
        v-model="searchQuery"
        @input="searchBooks"
        placeholder="Search books by title, author, or category..."
        class="search-bar"
      />
      <div class="forum-categories">
        <span
          v-for="category in categories"
          :key="category"
          class="category-tag"
          :class="{ active: selectedCategory === category }"
          @click="filterByCategory(category)"
        >
          {{ category }}
        </span>
      </div>
    </div>

  </div>

  <div class="forum-container">
    
    <!-- Forum Threads Section -->
    <section class="forum-threads">
      <h2 class="section-title2">Latest Posts</h2>
      <div v-for="book in filteredBooks.slice(0, 5)" :key="book.id" class="thread">
        <div class="thread-details">
          <div class="thread-title" @click="goToBookDetails(book.id)">
            <strong>{{ book.title }}</strong>
          </div>
          <div class="thread-meta">
            <p><span>Author:</span> {{ book.author }}</p>
            <p><span>Category:</span> {{ book.category || 'Uncategorized' }}</p>                  
          </div>
        </div>
         <div class="thread-stats">
          <p><span>Posted by:</span> <span @click="sendData(book.writer.id)" class="postCreator">{{ book.writer.username }}</span></p>
          

          <p><span>Posted On:</span> {{formatTimestamp(book.createDate)  }}</p>
          <!--<p><span>Last Engagement:</span> {{ formatDate(book.lastEngagement) }}</p>
          <p><span>Comments:</span> {{ book.commentsCount || 0 }}</p> 
          <img
            :src="book.image ? `data:image/jpeg;base64,${book.image}` : 'images/book.jpg'"
            alt="Book cover"
            class="book-cover"
          /> -->
        </div>
      </div>
    </section>

        <!-- Recommended Books Section -->
        <section class="recommended-section">
      <h2 class="section-title">Recommended Books</h2>
      <div class="recommended-books">
        <div v-for="book in recommendedBooks" :key="book.id" class="recommended-book">
          <img
            :src="book.image ? `data:image/jpeg;base64,${book.image}` : getRandomBookImage()"
            alt="Book cover"
            class="book-cover"
          />

          <div class="book-details">
            <h3 class="book-title" @click="goToBookDetails(book.id)">
              {{ book.title }}
            </h3>
            <p class="book-meta">
              <span>Author:</span> {{ book.author }}
            </p>
            <p class="book-meta">
              <span>Category:</span> {{ book.category || 'Uncategorized' }}
            </p>
            <p class="book-meta">
              <span v-for="star in 5" :key="star" 
                  :class="['star', star <= (  book.totalRatingScore /   book.ratingCount  ) ? 'filled' : '']" 
                 >
                ★
              </span>
            </p> 
          </div>
        </div>
      </div>
    </section>
  </div>

</template>

<script>
import BookService from "../services/BookService";

export default {
  name: "BooksList",
  data() {
    return {
      books: [],
      recommendedBooks: [],
      categories: ["General", "Science", "Historical", "Psychology", "Literature"],
      filteredBooks: [],
      selectedCategory: "General",
      searchQuery: "",
    };
  },
  methods: {
    // Fetch all books from the server
    fetchBooks() {
      BookService.getAllBooks()
        .then((response) => {
          this.books = response.data;
          this.books.sort((a, b) => new Date(b.createDate) - new Date(a.createDate));       
          this.filteredBooks = this.books; // Initially show all books 
          this.books.sort((a, b) => new Date(b.createDate) - new Date(a.createDate));       
          this.fetchRecommendedBooks(); // Populate recommended books
        })
        .catch((error) => {
          console.error(error);
        });
    },

    // Fetch recommended books (highest-rated or newest)
    fetchRecommendedBooks() {
      this.recommendedBooks = this.books
      .filter((book) => book.ratingCount > 0) // Exclude books with 0 ratings
        .sort((a, b) => b.totalRatingScore / b.ratingCount - a.totalRatingScore / a.ratingCount)
        .slice(0, 10); // Top 5 highest-rated books
    },

    // Filter books by category
    filterByCategory(category) {
      this.selectedCategory = category;
      this.filteredBooks = this.books
      .sort((a, b) => new Date(b.createDate) - new Date(a.createDate)) 
      .filter((book) => book.category === category);
    },

    // Search books by query
    searchBooks() {
      const query = this.searchQuery.toLowerCase();
      this.filteredBooks = this.books.filter((book) => {
        return (
          book.title.toLowerCase().includes(query) ||
          book.author.toLowerCase().includes(query) ||
          book.category.toLowerCase().includes(query)
        );
      });
    },

    // Navigate to book details
    goToBookDetails(bookId) {
      this.$router.push({ name: "BookDetails", params: { bookId: bookId } });
    },

    // Format dates for "Last Engagement"
    formatDate(date) {
      if (!date) return "No engagement yet";
      const engagementDate = new Date(date);
      return engagementDate.toLocaleString(); // Adjust format based on locale
    },
    formatTimestamp(timestamp) {
        const date = new Date(timestamp);
        return date.toLocaleString(); 
    },
    sendData(userId) {
      this.$router.push({ name: "UserDisplay", params: { userId } });
    },
    getRandomBookImage() {
      const randomIndex = Math.floor(Math.random() * 6) + 1; // Generate a number between 1 and 6
      return `images/book${randomIndex}.jpg`; // Construct the image path
    },
  },
  mounted() {
    this.fetchBooks();
    this.userId = localStorage.getItem("userId");

  },
};
</script>

<style scoped>
/* General Forum Styles */
.container-img{
  background: url(../../public/images/2.jpg) no-repeat center center;
  background-size: cover;
  width: 100%;
  height: 600px;
  max-height: 600px;
  padding-top: 150px;
}

.forum-container {
  max-width: 1400px;
  padding: 20px;
  background-blend-mode: darken;
  background-color: #e0e0e059;
  background: blur(4px);
  margin-top: 20px;
  border-radius: 10px;
}

.forum-header {
  text-align: center;
  margin-bottom: 20px;
}

.forum-title {
  font-size: 3rem;
  color: #1f8eaf;
}

.forum-subtitle {
  font-size: x-large;
  color: #ff781e;
}

.forum-controls {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
}

.search-bar {
  width: 60%;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 10px;
  margin-bottom: 10px;
  font-size: 1rem;
}

.forum-categories {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-top: 10px;
}

input{
  border-radius: 10px;
}

.category-tag {
  background-color: #e8f4fa;
  border: 1px solid #1f8eaf;
  padding: 12px 15px;
  margin: 0 10px;
  border-radius: 10px;
  cursor: pointer;
  color: #1f8eaf;
  font-weight: bold;
  transition: background-color 0.3s, color 0.3s;
}

.category-tag:hover {
  background-color: #1f8eaf;
  color: white;
}

.category-tag.active {
  background-color: #1f8eaf;
  color: white;
}

/* Recommended Books Section */
.recommended-section {
  margin-top: 20px;
}

.section-title {
  font-size: 1.8rem;
  color: #1f8eaf;
  margin-bottom: 10px;
  margin-top: 100px;
}
.section-title2{
  color: #ff781e;
  font-size: 1.8rem;
  margin-bottom: 20px;
  margin-top: 0px;
}

.recommended-books {
  display: flex;
  gap: 20px;
  overflow-x: scroll;
  padding: 20px 0;
}

.recommended-book {
  flex: 0 0 200px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background-color: #fff;
  padding: 10px;
  text-align: center;
  width: 250px;
  min-width: 255px;
}

.book-cover {
  width: 100%;
  height: 200px;
  object-fit: cover;
  margin-bottom: 10px;
  border-radius: 5px;
}

.book-title {
  font-size: 1rem;
  color: #1f8eaf;
  margin-bottom: 5px;
  cursor: pointer;
}

.book-meta {
  font-size: 0.8rem;
  color: #555;
}

/* Forum Threads */
.thread {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background-color: #fff;
  margin-bottom: 10px;
}

.thread:hover {
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
}

.thread-details {
  flex: 3;
  margin-right: 20px;
}

.thread-title {
  font-size: 1.2rem;
  color: #1f8eaf;
  cursor: pointer;
}

.thread-title:hover {
  text-decoration: underline;
}

.thread-meta {
  font-size: 0.9rem;
  color: #555;
}

.thread-stats {
  flex: 1;
  text-align: left;
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
.postCreator{
  cursor: pointer;
  font-weight: bold;
  color: #ff781e;
}
</style>
