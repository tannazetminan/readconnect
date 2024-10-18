<template>
  <div class="container-home">
    <div class="container-img">
      <img src="images/background_page.jpg" class="img_back" />
    </div>
  </div>
</template>


<script>

export default {

  name: "HomePage",

  data() {
    return {

    };
  },


  methods: {
     },
  mounted() {
  },
};
</script>



<style scoped>
.container-home {
  width: 100%;
  text-align: center;
  margin: 0px;
}

.container-img {
  width: 100%;
}

.img_back {
  width: 100%;
  height: 500px;
  object-fit: cover;
  object-position: bottom;
  margin: 0;
  box-shadow: slategray;
}

</style>