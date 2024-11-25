<template>
  <div class="nav-container">
    <nav class="navbar">
      <div class="container">
        <router-link to="/" class="brand">
          <h2 class="text-2xl font-semibold">Read Connect</h2>
        </router-link>

        <ul class="nav-links">
          <li>
            <router-link to="/" class="nav-link">Home</router-link>
          </li>
          <li>
            <router-link to="/booksearch" class="nav-link">Search</router-link>
          </li>
          <li>
            <router-link to="/bookslist" class="nav-link">Books</router-link>
          </li>
          <li v-if="isLoggedIn && userType === 'user'">
            <router-link to="/userdetails" class="nav-link">Profile</router-link>
          </li>
          <li v-if="isLoggedIn">
            <router-link to="/messageslist" class="nav-link">Messages</router-link>
          </li>
          <li v-if="!isLoggedIn">
            <router-link to="/login" class="nav-link">Login</router-link>
          </li>
          <li v-if="!isLoggedIn">
            <router-link to="/signup" class="nav-link">Sign up</router-link>
          </li>
          <li v-if="isLoggedIn">
            <router-link to="/" class="nav-link"><a href="#" @click="logout">Logout</a></router-link>
          </li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: "navBar",

  data() {
    return {
      isLoggedIn: false,
      userType: null,
    };
  },

  methods: {
    logout() {
      localStorage.removeItem('token');
      localStorage.removeItem('userId');
      localStorage.removeItem('workerId');
      this.isLoggedIn = false;
      this.$router.push('/');
    },
    chekUserType() {
      this.userType = localStorage.getItem('userType');
      // console.log("user type" + this.userType)
    }
  },

  mounted() {
    // Check if token exists in local storage
    if (localStorage.getItem('token')) {
      this.isLoggedIn = true;
      this.chekUserType();
    }
  },


};
</script>


<style scoped>
.nav-container {
  /* background-color: #18143a; */
  background-color: #1f1f25;
  margin: 0;
  position: relative;
  width: 100%;
  height: 100px;
  opacity: 100%;
  margin: 0;
}

h2.text-2xl{
  font-size: 2rem;
}

.navbar {
  border-radius: 0.5rem;
  padding: 1rem;
  margin: auto;
  width: 90%;
}

.container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.brand {
  text-decoration: none;
  color: #e27713;
  font-size: xx-large;
  font-weight: bold;
}

.nav-links {
  list-style: none;
  display: flex;
  align-items: center;
}

.nav-link , a {
  text-decoration: none;
  color: #ecebf8;
  margin-left: 1rem;
  font-size: large;
  margin-right: 2rem;
}

.nav-link:hover,
:any-link:hover {
  color: #e27713;
}

.router-link-active , a:active {
  color: #e27713;
}
</style>