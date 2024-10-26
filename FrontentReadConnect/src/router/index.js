import { createWebHistory, createRouter } from "vue-router";
import HomePage from "../components/HomePage.vue";
import SignIn from "../components/SignIn.vue";
import SingUp from "../components/SignUp.vue";
import UserDetails from "../components/UserDetails.vue";
import UserDisplay from "@/components/UserDisplay.vue";
import BookSearch from "../components/BookSearch.vue"
import BooksList from "../components/BooksList.vue";
import BookDetails from "@/components/BookDetails.vue";
import MessageChat from "@/components/MessageChat.vue";
import MessagesList from "@/components/MessagesList.vue";


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
    name: "UserDisplay",
    component: UserDisplay,
    path: "/userDisplay/:userId"
  },
  {
    name: "BookSearch",
    component: BookSearch,
    path: "/bookSearch"
  },
  {
    name: "BooksList",
    component: BooksList,
    path: "/booksList"
  }, 
  {
    name: "BookDetails",
    component: BookDetails,
    path: '/books/:bookId',
  }, 
  {
    name: "MessageChat",
    component: MessageChat,
    path: '/messageChat/:receiverId',
  }, 
  {
    name: "MessagesList",
    component: MessagesList,
    path: '/messagesList',
  }, 
 

];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
