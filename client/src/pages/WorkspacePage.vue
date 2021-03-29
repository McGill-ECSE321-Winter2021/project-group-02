<template>
  <div id="workspace-page">
    <div id="workspace-container">
      
    </div>
  </div>
</template>

<script>
export default {
  name: "WorkspacePage"
}
</script>

<style scoped>
  #workspace-page,
  #workspace-container {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
  }

  #workspace-page {
    height: 100vh;
    width: 100vw;
    background-image: radial-gradient(whitesmoke 20%, transparent 20%),
    radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
    background-position: 0 0, 7.5vh 7.5vh;
    background-size: 15vh 15vh;
    background-color: rgb(238, 207, 173);
  }

  #workspace-container {
    height: 80vh;
    width: 80vw;
    border-radius: 5vh;
    background-color: whitesmoke;
    transition: 0.3s;
  }
</style>