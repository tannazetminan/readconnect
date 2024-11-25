<template>
  <div class="nav-container">
    <nav class="navbar">
      <div class="container">
        <router-link to="/" class="brand">
          <h2 class="text-2xl font-semibold">ReadConnect</h2>
        </router-link>

        <!-- Hamburger Menu for Small Screens -->
        <div class="menu-toggle" @click="toggleMenu">
          ☰
        </div>

        <!-- Navigation Links -->
        <ul class="nav-links" :class="{ active: isMenuOpen }">
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
            <router-link to="/signup" class="nav-link">SignUp</router-link>
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
      isMenuOpen: false, // Toggle menu state
    };
  },

  methods: {
    logout() {
      localStorage.removeItem("token");
      localStorage.removeItem("userId");
      localStorage.removeItem("workerId");
      this.isLoggedIn = false;
      this.$router.push("/");
    },
    chekUserType() {
      this.userType = localStorage.getItem("userType");
    },
    toggleMenu() {
      this.isMenuOpen = !this.isMenuOpen;
    },
  },

  mounted() {
    // Check if token exists in local storage
    if (localStorage.getItem("token")) {
      this.isLoggedIn = true;
      this.chekUserType();
    }
  },
};
</script>



<style scoped>
.nav-container {
  background-color: #1f1f25;
  margin: 0;
  position: fixed;
  width: 100%;
  height: 100px;
}

h2.text-2xl {
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
  transition: max-height 0.3s ease-out;
}

.nav-link,
a {
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

.router-link-active,
a:active {
  color: #e27713;
}

/* Hamburger Menu Icon */
.menu-toggle {
  display: none;
  font-size: 1.5rem;
  color: #e27713;
  cursor: pointer;
}

/* Responsive Design */
@media (max-width: 768px) {
  .nav-links {
    flex-direction: column;
    align-items: flex-start;
    background-color: #1f1f25;
    position: absolute;
    top: 100%;
    left: 0;
    right: 0;
    overflow: hidden;
    max-height: 0;
    padding: 0;
    border-top: 1px solid #ecebf8;
  }

  .nav-links.active {
    max-height: 200px; 
    padding: 1rem;
  }

  .nav-link {
    margin: 0.5rem 0;
    font-size: 1.2rem;
  }

  .menu-toggle {
    display: block;
  }

  .container {
    justify-content: space-between;
  }
}

</style>