<template>
  <div class="nav-container">
    <nav class="navbar">
      <div class="container">
        <router-link to="/" class="brand">
          <span class="text-2xl font-semibold">Read Connect</span>
        </router-link>

        <ul class="nav-links">
          <li>
            <router-link to="/" class="nav-link">Home</router-link>
          </li>
          <li>
            <router-link to="/booksearch" class="nav-link">Search</router-link>
          </li>
          <li>
            <router-link to="/books" class="nav-link">Books</router-link>
          </li>
          <li v-if="isLoggedIn && userType === 'user'">
            <router-link to="/userdetails" class="nav-link">Profile</router-link>
          </li>
          <li v-if="isLoggedIn">
            <router-link to="/messagesdisplay" class="nav-link">Messages</router-link>
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

  // watch: {
  //   if (newLogin) {
  //     console.log("ddddddddddddddddddddddddddd")

  //     localStorage.setItem('newLogin', false);
  //     console.log(newLogin)
  //     window.location.reload();
  //   }
  // }
};
</script>


<style scoped>
.nav-container {
  background-color: #18143ada;
  margin: 0;
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