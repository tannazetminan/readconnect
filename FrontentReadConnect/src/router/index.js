import { createWebHistory, createRouter } from "vue-router";
import HomePage from "../components/HomePage.vue";
import SignIn from "../components/SignIn.vue";
import SingUp from "../components/SignUp.vue";
import BookSearch from "../components/BookSearch.vue"
import DisplayBooks from "../components/DisplayBooks.vue";
import UserDetails from "../components/UserDetails.vue";



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
    name: "BookSearch",
    component: BookSearch,
    path: "/bookSearch"
  },
  {
    name: "userDetails",
    component: UserDetails,
    path: "/userdetails"
  },
  {
    name: "Displaybooks",
    component: DisplayBooks,
    path: "/workers"
  }, 
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
