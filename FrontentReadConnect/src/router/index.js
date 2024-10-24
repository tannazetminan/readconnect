import { createWebHistory, createRouter } from "vue-router";
import HomePage from "../components/HomePage.vue";
import SignIn from "../components/SignIn.vue";
import SingUp from "../components/SignUp.vue";
import UserDetails from "../components/UserDetails.vue";
import BookSearch from "../components/BookSearch.vue"
import BooksDisplay from "../components/BooksDisplay.vue";
import BookDetails from "@/components/BookDetails.vue";


const routes = [
  {
    name: "homePage",
    component: HomePage,
    path: "/"
  },
 {
    name: "SignIn",
    component: SignIn,
    path: "/login"
  },
  {
    name: "SignUp",
    component: SingUp,
    path: "/signup"
  },
  {
    name: "userDetails",
    component: UserDetails,
    path: "/userdetails"
  },
  {
    name: "BookSearch",
    component: BookSearch,
    path: "/bookSearch"
  },
  {
    name: "BooksDisplay",
    component: BooksDisplay,
    path: "/books"
  }, 
  {
    name: "BookDetails",
    component: BookDetails,
    path: '/books/:bookId',
  }, 
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
