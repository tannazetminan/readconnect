<template>
    <div class="forum-container">
      <!-- <div class="categories">
      <span>General</span>
      <span>Science</span>
      <span>Electrical </span>
      <span>Historical</span>
      <span>Psychology </span>
    </div> -->

     <!-- Recommended Books Section -->
     <section class="recommended-section">
      <h2 class="section-title">Highest-Rated Books</h2>
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
            <p  style="text-align: center;">
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


    <!-- Search and Categories -->
    <div class="forum-controls">
      <!-- Forum Header -->
      <header class="forum-header">
        <!-- <h1 class="forum-title">Book Forum</h1> -->
        <p class="forum-subtitle">Search Your Favorit Book In ReadConnect Platform</p>
      </header>
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

   

    <!-- Forum Threads Section -->
    <section class="forum-threads">
      <h2 class="section-title2">Related Books</h2>
      <div v-for="book in filteredBooks" :key="book.id" class="thread">
        <div class="thread-details">
          <div class="thread-title" @click="goToBookDetails(book.id)">
            <strong>{{ book.title }}</strong>
          </div>
          <div class="thread-meta">
            <p><span>Author:</span> {{ book.author }}</p>
            <p><span>Posted by:</span> {{ book.writer.username }}</p>
            <p><span>Category:</span> {{ book.category || 'Uncategorized' }}</p>
            <div class="rating-stars">
               <span v-for="star in 5" :key="star" 
                  :class="['star', star <= (  book.totalRatingScore /   book.ratingCount  ) ? 'filled' : '']" 
                  >
                    ★
                </span>
            </div>                     
          </div>
        </div>
        <div class="thread-stats">
          <!-- <p><span>Last Engagement:</span> {{ formatDate(book.lastEngagement) }}</p>
          <p><span>Comments:</span> {{ book.commentsCount || 0 }}</p> -->
          <img
            :src="book.image ? `data:image/jpeg;base64,${book.image}` : getRandomBookImage()"
            alt="Book cover"
            class="book-cover"
          />
        </div>
      </div>
    </section>
  </div>
  <FooterMenu />
</template>



<script>
import BookService from "../services/BookService";
import FooterMenu from './Footer.vue';

export default {
  name: "BooksList",
  components: {
    FooterMenu
  },

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
          this.filteredBooks = this.books; // Initially show all books
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
        .slice(0, 20); // Top 20 highest-rated books
    },

    // Filter books by category
    filterByCategory(category) {
      this.selectedCategory = category;
      this.filteredBooks = this.books.filter((book) => book.category === category);
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
    getRandomBookImage() {
      const randomIndex = Math.floor(Math.random() * 6) + 1; // Generate a number between 1 and 6
      return `images/book${randomIndex}.jpg`; // Construct the image path
    },

  },
  mounted() {
    this.fetchBooks();
  },

}
</script>

<style>
.img_back{
  width: 100%;
  height: 840px;
}
.forum-container {
  max-width: 1400px;
  margin: auto;
  padding: 20px;
  font-family: Arial, sans-serif;
  background-blend-mode: darken;
  background-color: #e0e0e059;
  background: blur(4px);
  margin-top: 100px;
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
  font-size: large;
  color: #ff781e;
  font-weight: bold;
}

.forum-controls {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 50px;
  margin-top: 50px;
  background-color: #1f1f25f8;
  border-radius: 10px;
  padding: 50px;
}

.search-bar {
  width: 80%;
  padding: 10px;
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

.category-tag {
  background-color: #e8f4fa;
  border: 1px solid #ff781e;
  padding: 10px 15px;
  margin: 0 10px;
  border-radius: 10px;
  cursor: pointer;
  color: #ff781e;
  font-weight: bold;
  transition: background-color 0.3s, color 0.3s;
}

.category-tag:hover {
  background-color: #ff781e;
  color: white;
}

.category-tag.active {
  background-color: #ff781e;
  color: white;
}

/* Recommended Books Section */
.recommended-section {
  margin-top: 20px;
}

.section-title {
  font-size: 1.8rem;
  color: #ff781e;
  margin-bottom: 0px;
  margin-top: 50px;
}
.section-title2{
  /* color: #1f8eaf; */
  color: #ff781e;
  font-size: 1.8rem;
  margin-bottom: 30px;
  margin-top: 70px;
}
.recommended-books {
  display: flex;
  gap: 20px;
  overflow-x: scroll;
  padding: 30px 0;
}

.recommended-book {
  flex: 0 0 200px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background-color: #1f1f25f8;
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
  color: #ddd;
}

/* Forum Threads */
.forum-threads {
  margin-top: 50px;
}

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
  text-align: right;
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
</style>

