<template>
  <div class="dashboard">
    <div id="dashboard-container"></div>
  </div>
</template>

<script>
export default {
  name: "Dashboard",
  props: {
    msg: String,
  },
  data() {
    return {};
  },
  methods: {},
  mounted() {
    console.log(this.$store.state.user);
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.dashboard {
  height: 100vh;
  width: 100vw;
  background-image: radial-gradient(whitesmoke 20%, transparent 20%),
    radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
  background-position: 0 0, 7.5vh 7.5vh;
  background-size: 15vh 15vh;
  background-color: rgb(238, 207, 173);
}

#dashboard-container {
  position: absolute;
  height: 80vh;
  width: 80vw;
  border-radius: 5vh;
  background-color: whitesmoke;
}

.dashboard,
#dashboard-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  transition: 0.3s;
}
</style>
