<template>
    <div v-if="book.title" class="container-cards">
        <!-- Book Info -->
        <div class="cards-description">
            <div class="personal-data">
                <p class="data-description"><strong>{{ book.title }}</strong></p>
                <p>
                    <img v-if="book.image" :src="getImageSrc(book.image)" alt="Book Image" class="bookImg" />
                    <img v-else src="../../public/images/book.jpg" alt="Book Image" class="bookImg" />
                </p>
            </div>
            <div class="setting">
                <h3>Book Details</h3>
                <p><strong>Author: </strong>{{ book.author }}</p>
                <p><strong>ISBN: </strong>{{ book.isbn }}</p>
                <p><strong>Description:</strong></p><p>{{ book.description }}</p>
                <p v-if="book.writer.mode === false">
                    <strong>Post Creator: </strong>
                    <span @click="sendData(book.writer.id)" class="creator">{{ book.writer.username }}</span>
                </p>
                <p v-else><strong>Post Creator: </strong>{{ book.writer.username }}</p>
                <!-- Rating Section -->
                <div>
                    <p><strong>Total Voters: </strong> {{ book.ratingCount }} votes</p>
                    <div class="rating-stars">
                        <span v-for="star in 5" :key="star" 
                              :class="['star', star <= (  book.totalRatingScore /   book.ratingCount  ) ? 'filled' : '']" 
                              @click="rateBook(star)">
                            ★
                        </span>
                    </div>                     
                </div>
            </div>
            <p>Post Creation Date: {{ formatTimestamp(book.createDate)  }}</p>

        </div>
        <!-- Comments Section -->
        <div class="card-pf">
            <div class="profile-desc">
                <!-- <h3>Comments for this Book:</h3>-->
                <div v-if="comments.length === 0" class="no-comments">
                    No comments yet. Be the first to comment!
                </div>
                <div v-else class="comments-list">
                    <div v-for="comment in comments" :key="comment.id" class="comment">
                        <div class="comment-header">
                            <span @click="sendData(comment.user.id)" style="font-weight: bold; cursor: pointer;" >{{ comment.user.username }}</span>
                            <span class="comment-timestamp">{{ formatTimestamp(comment.timestamp) }}</span>
                        </div>
                        <p class="comment-content">{{ comment.content }}</p>
                    </div>
                </div>
                <!-- Add Comment -->
                <div class="add-comment">
                    <textarea v-model="newComment" placeholder="Write your comment here..."></textarea>
                    <button @click="addComment">Submit Comment</button>
                </div>
            </div>
        </div>

    </div>
    <div v-else>
        <h1>This Book Doesn't Exist!!!</h1>
    </div>
</template>

<script>
import BookService from "../services/BookService";

export default {
    name: "BookDetails",
    data() {
        return {
            book: {},
            comments: [],
            newComment: "",
            userRating: 0, // Temporary for user input
        };
    },
    methods: {
        retrieveBook(bookId) {
            BookService.getBookById(bookId)
                .then((response) => {
                    this.book = response.data;
                })
                .catch((error) => {
                    console.error(error);
                });
        },
        fetchComments(bookId) {
            BookService.getComments(bookId)
                .then((response) => {
                    this.comments = response.data;
                })
                .catch((error) => {
                    console.error(error);
                });
        },
        addComment() {
            const bookId = this.$route.params.bookId;
            const userId = localStorage.getItem("userId");
            if (this.newComment.trim()) {
                BookService.addComment(bookId, userId, this.newComment)
                    .then(() => {
                        this.newComment = "";
                        this.fetchComments(bookId);
                    })
                    .catch((error) => {
                        alert("Failed to submit comment: " + error.response?.data?.message || error.message);
                    });
            } else {
                alert("Comment cannot be empty!");
            }
        },
        formatTimestamp(timestamp) {
            const date = new Date(timestamp);
            return date.toLocaleString(); 
        },
        rateBook(rating) {
            const bookId = this.$route.params.bookId;
            const userId = localStorage.getItem("userId");
            BookService.rateBook(bookId, rating, userId)
                .then(() => {
                    this.retrieveBook(bookId); 
                })
                .catch((error) => {
                    alert("Failed to rate book: " + error.response?.data?.message || error.message);
                });
        },
        getImageSrc(image) {
            return `data:image/jpg;base64,${image}`;
        },
        sendData(userId) {
            this.$router.push({ name: "UserDisplay", params: { userId } });
        },
    },
    mounted() {
        const bookId = this.$route.params.bookId;
        this.retrieveBook(bookId);
        this.fetchComments(bookId);
    },
};
</script>


<style scoped>
/* Container for the entire page */
*{
}

.container-cards {
    max-width: 1400px;
    margin: 0 auto;
    display: flex;
    flex-wrap: wrap;
    color: #333;
    padding: 20px;
    font-size: 15px;
    width: 80%;
}

.data-description{
    text-align: center;
    font-size: x-large;
}

/* Profile Image styling */
.bookImg {
    display: block;
    width: 100%;
    max-width: 250px;
    margin: 0 auto 25px;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* Main Book Information */
.cards-description {
    width: 30%;
    padding: 20px;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    margin-right: 20px;
}

.setting {
    background-color: #f8f9fa;
    border-radius: 8px;
    padding: 15px;
    box-shadow: 0 1px 6px rgba(0, 0, 0, 0.1);
}

h3 {
    margin-top: 0;
    color: #333;
    font-size: 1.25rem;
}

/* Author and rating section */
.creator {
    font-weight: bold;
    cursor: pointer;
    color: #fff;
    background-color: #ff9f00;
    padding: 6px 12px;
    border-radius: 8px;
    text-transform: capitalize;
}

.rating-stars {
    display: flex;
    gap: 8px;
    margin-top: 10px;
}

.star {
    font-size: 1.75rem;
    color: #ddd;
    cursor: pointer;
    transition: color 0.3s;
}

.star.filled {
    color: #ff9800;
}

.comment {
    margin-top: 25px;
}

/* Comments Section */
.card-pf {
    width: 65%;
    padding: 20px;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.profile-desc {
    margin-top: 20px;
}

.no-comments {
    text-align: center;
    font-size: 1.2rem;
    color: #888;
}

.comments-list {
    margin-bottom: 20px;
}

.comment-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
}

.comment-header strong {
    font-size: 1.1rem;
    color: #333;
}

.comment-timestamp {
    font-size: 0.9rem;
    color: #777;
}

.comment-content {
    font-size: 1rem;
    color: #555;
    line-height: 1.6;
}

.add-comment {
    margin-top: 20px;
    display: flex;
    flex-direction: column;
}

textarea {
    width: 100%;
    min-height: 100px;
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 12px;
    font-size: 1rem;
    resize: none;
    box-sizing: border-box;
    margin-bottom: 15px;
    background-color: #f9f9f9;
    font-family: Verdana, Geneva, Tahoma, sans-serif;
}

textarea:focus {
    border-color: #007bff;
    background-color: #fff;
    outline: none;
}

button {
    padding: 10px 15px;
    background-color: #ff8800;
    color: #fff;
    border: none;
    border-radius: 8px;
    font-size: 1rem;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

button:hover {
    background-color: #218838;
}

button:focus {
    outline: none;
}

/* Add space between each comment */
.comment {
    background-color: #f9f9f9;
    border: 1px solid #eee;
    padding: 15px;
    margin-bottom: 15px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* Comment formatting */
.comment-content {
    margin-top: 10px;
    line-height: 1.6;
}

/* Media Query for responsiveness */
@media (max-width: 768px) {
    .container-cards {
        flex-direction: column;
        padding: 10px;
    }

    .cards-description, .card-pf {
        width: 100%;
        margin-right: 0;
    }
}
</style>
