<template>
    <div v-if="book.title" class="container-cards">

        <!-- showing the book img-->
        <div class="cards-description">

            <div class="personal-data">
                <p>
                    <img v-if="book.image" :src="getImageSrc(book.image)" alt="Book Image" class="profile" />
                    <img v-else src="../../public/images/book.jpg" alt="Book Image" class="profile" />
                </p>
                <p class=" data-description">{{ book.title }}</p>
            </div>


            <div class="setting">
                <div class="edit-preferences">
                    <h2>Book Details</h2>
                </div>
                <div>
                    <p>Title: <span style="font-size: large;">{{ book.title }}</span></p>
                    <p>Author: <span style="font-weight: bold;">{{ book.author }}</span></p>
                    <p>Post Creator: {{ book.writer.username }}</p>
                    <p>ISBN: {{ book.isbn }}</p>
                </div>

            </div>
        </div>

        <div class="card-pf">
            <div class=" profile-desc">

                <h4>Description: </h4>

                <div class="desc">
                    <p class="data-description">{{ book.description }}</p>
                </div>
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

.cards-description {
    text-align: center;
    width: 30%;
    height: fit-content;
    align-items: flex-start;
    border-radius: 0.5rem;

}

.card-pf {
    width: 70%;
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
    padding: 5px;
    text-align: left;

}

.fetchData {
    margin-left: 30px;
    margin-bottom: 40px;
    font-size: 18px;
    background-color: transparent;
    border: none;
    color: rgb(60, 172, 15);
    cursor: pointer;
    font-size: inherit;
    padding: 0;
    font-weight: bold;
    font-size: 25px;
}


h2 {
    margin-top: 15px;
    font-size: 25px;
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