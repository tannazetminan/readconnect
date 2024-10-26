import { createWebHistory, createRouter } from "vue-router";
import HomePage from "../components/HomePage.vue";
import SignIn from "../components/SignIn.vue";
import SingUp from "../components/SignUp.vue";
import UserDetails from "../components/UserDetails.vue";
import UserDisplay from "@/components/UserDisplay.vue";
import BookSearch from "../components/BookSearch.vue"
import BooksDisplay from "../components/BooksDisplay.vue";
import BookDetails from "@/components/BookDetails.vue";
import MessagesDisplay from "@/components/MessagesDisplay.vue";
import MessagesList from "@/components/MessagesList.vue";
import MessageDetails from "@/components/MessageDetails.vue";

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
    name: "BooksDisplay",
    component: BooksDisplay,
    path: "/books"
  }, 
  {
    name: "BookDetails",
    component: BookDetails,
    path: '/books/:bookId',
  }, 
  {
    name: "MessagesDisplay",
    component: MessagesDisplay,
    path: '/messagesDisplay/:receiverId',
  }, 
  {
    name: "MessagesList",
    component: MessagesList,
    path: '/messagesList',
  }, 
  {
    name: "MessageDetails",
    component: MessageDetails,
    path: '/messageDetails',
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
