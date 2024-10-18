<template>
  <div id="app">
    <div>
      <NavBar />
      <router-view />
    </div>

  </div>
</template>

<script>
import NavBar from './components/NavBar.vue';

export default {
  name: 'App',
  components: {
    NavBar
  }


}
</script>

<style>
#app {
  margin: 0;
}
body{
  font-family: Verdana, Geneva, Tahoma, sans-serif;
  font-size: 13px;
}
*{
  box-sizing: border-box;
}

</style>