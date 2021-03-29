<template>
  <div id="modify-appointment">
    <div id="modify-appointment-container">
      <input id="form-service" type="text">

    </div>
  </div>
</template>

<script>

</script>

<style scoped>

#modify-appointment {
  height: 100vh;
  width: 100vw;
  background-image: radial-gradient(whitesmoke 20%, transparent 20%),
  radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
  background-position: 0 0, 7.5vh 7.5vh;
  background-size: 15vh 15vh;
  background-color: rgb(238, 207, 173);
}

#modify-appointment {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}

#modify-appointment-container {
  height: 80vh;
  width: 80vw;
  border-radius: 5vh;
  background-color: whitesmoke;
  transition: 0.3s;
}

</style>