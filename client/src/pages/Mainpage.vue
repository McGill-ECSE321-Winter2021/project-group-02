<template>
  <div class="mainpage">
    <h1>beep</h1>
  </div>
</template>

<script>
export default {
  name: "Mainpage",
  props: {
    msg: String,
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
*,
body,
html {
  padding: 0;
  margin: 0;
  user-select: none;
}

.mainpage {
  height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
