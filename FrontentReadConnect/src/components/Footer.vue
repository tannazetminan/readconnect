<template>
    <div class="containerF">
        Copyright <span>©ReadConnect</span><br/>
        Developed by <a href="http://stegroup.ir"><span>Tannaz Etminan</span></a> | 2024-2025. All rights reserve
    </div>
  </template>
  
  
  <script>
  export default {
    name: "footerMenu",
  
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
  .containerF {
    background-color: #1f1f25;
    margin: 0;
    position: relative;
    width: 100%;
    height: 90px;
    text-align: center;
    color: aliceblue;
    padding-top: 20px;
  }
  span {
    color: #ff781e;
    text-decoration: none;
  }
  a {
    text-decoration: none;
  }
  </style>