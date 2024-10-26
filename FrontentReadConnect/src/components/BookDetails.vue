<template>
    <div v-if="book.title" class="container-cards">

        <!-- book info-->
        <div class="cards-description">

            <!-- book img & tile-->
            <div class="personal-data">
                <p>
                    <img v-if="book.image" :src="getImageSrc(book.image)" alt="Book Image" class="profile" />
                    <img v-else src="../../public/images/book.jpg" alt="Book Image" class="profile" />
                </p>
                <p class=" data-description"><strong>{{ book.title }}</strong></p>
            </div>

            <!-- book details-->
            <div class="setting">
                <div class="edit-preferences">
                    <h3>Book Details</h3>
                </div>
                <div>
                    <p><span style="font-weight: bold;">Author: </span>{{ book.author }}</p>
                    <p v-if="book.writer.mode == false">
                        <span style="font-weight: bold;">Post Creator: </span>
                        <span @click="sendData(book.writer.id)" class="creator">{{ book.writer.username }}</span>
                    </p>
                    <p v-else> <span style="font-weight: bold;">Post Creator: </span>{{ book.writer.username }} </p>
                    <p><span style="font-weight: bold;">ISBN: </span>{{ book.isbn }}</p>
                    <p>
                        <span style="font-weight: bold;">Description:<br/></span> 
                        <span class="data-description">{{book.description }}</span> 
                    </p>
                </div>

            </div>
        </div>

        <!-- book comments-->
        <div class="card-pf">
            <div class=" profile-desc">

                <h3>Comments for this book:</h3>
                no comment yet

                <!-- <h3> Technical Skills</h3>
                <ul>
                    <li v-for="(skill, index) in book.skills" :key="index">{{ skill.name }}</li>
                </ul>
                <h3> Soft Skills</h3>
                <ul>
                    <li>Customer service skills</li>
                    <li>Effective communication with clients and colleagues</li>
                    <li>Teamwork</li>
                    <li>Adaptability to different work environments</li>
                    <li>Problem-solving</li>
                    <li>Time management and organization</li>
                    
                </ul> -->



            </div>


        </div>
    </div>
    <div v-else>
        <h1>This Book Doesn't Excist!!!</h1>
    </div>
</template>

<script>

import FetchDataServices from '../services/FetchDataService'

export default {
    name: "BookDetails",

    data() {
        return {
            newLogin: false,
            book: {
                title: "",
                description: "",
                isbn: "",
                author: "",
            },
            date: "",
            random_number: 0,
        }
    },

    methods: {
        retrievebook(bookId) {
            FetchDataServices.getBookById(bookId)
                .then(response => {
                    this.book = response.data
                })
                .catch(error => {
                    console.log(error)
                })
        },
        getImageSrc(image) {
            return `data:image/jpg;base64,${image}`;
        },

        //going to creator page
        sendData(userId) {
            this.$router.push({ name: "UserDisplay", params: { userId: userId } })
        }
    },

    mounted() {
        const bookId = this.$route.params.bookId;
        if (bookId) {
            this.retrievebook(bookId);
        } else {
            console.error('bookId is undefined');
            this.message = "NA"
        }
    }
}


</script>

<style scoped>
.container-cards {
    width: 90%;
    margin: auto;
    overflow: auto;
    display: flex;
    font-size: 1rem;
    font-weight: normal;
}

.profile {
    display: block;
    margin-top: 25px;
    height: 350px;
    width: 250px;
}

.creator {
    font-weight: bold;
    cursor: cell;
    color: white;
    animation: backwards;
    background-color: #e27713;
    padding: 6px 10px;
    border-radius: 10%;
}

.cards-description {
    text-align: center;
    width: 35%;
    height: fit-content;
    align-items: flex-start;
    border-radius: 0.5rem;
}

.card-pf {
    width: 64%;
    text-align: left;
    margin-left: 100px;
}

.profile-desc {
    margin-left: 20px;
    margin-bottom: 20px;
    width: 69%;
    border-radius: 0.5rem;
    text-align: left;
    background-color: white;
    padding: 5px;
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
    padding: 20px;
    text-align: left;
    font-size: 0.93rem;
}

h3 {
    margin-top: 15px;
    text-align: center;
}

.title {
    margin-top: 25px;
    padding: 0.5px;
}

.title-pr {
    text-align: left;
}

.desc {
    text-align: justify;
}
</style>