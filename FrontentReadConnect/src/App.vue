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
*{
  box-sizing: border-box;
  font-size: 15px;
  font-family: Verdana, Geneva, Tahoma, sans-serif;
}

</style>