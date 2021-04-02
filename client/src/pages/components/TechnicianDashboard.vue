<template>
  <div class="technician-dashboard" id="technician-dashboard">
    <div class="technician-dashboard-container" id="a">
      <p class="dashboard-title">Appointments</p>
      <button class="dashboard-button" @click="viewSchedule()">
        View Technician Schedule
      </button>
    </div>
    <div class="technician-dashboard-container" id="b">
      <p class="dashboard-title">Accounts</p>
      <button class="dashboard-button" @click="modifyAccount()">
        Modify Account
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "TechnicianDashboard",
  props: {
    msg: String,
  },
  methods: {
    viewSchedule: function() {
      document.getElementById("technician-dashboard").style.opacity = 0;
      document.getElementById("logout-button").style.opacity = 0;
      let vm = this;
      setTimeout(function() {
        vm.$router.push("/viewtechnicianschedule");
      }, 300);
    },
    modifyAccount: function() {
      document.getElementById("technician-dashboard").style.opacity = 0;
      document.getElementById("logout-button").style.opacity = 0;
      let vm = this;
      setTimeout(function() {
        vm.$router.push("/myaccount");
      }, 300);
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.technician-dashboard {
  display: grid;
  align-items: center;
  justify-content: center;
  grid-template-columns: 40vw 40vw;
  height: 80vh;
  width: 80vw;
  border-radius: 5vh;
  background-color: whitesmoke;
}

#technician-dashboard {
  opacity: 1;
  transition: 0.3s;
}

.technician-dashboard-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}

.technician-dashboard-inside-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: row;
}

#a,
#b {
  border-radius: 5vh;
  height: 40vh;
  width: 40vw;
}

#a {
  grid-row: 1/2;
  grid-column: 1/2;
}

#b {
  grid-row: 1/2;
  grid-column: 2/3;
}

.dashboard-button {
  all: unset;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 5vh;
  width: 12vw;
  border-radius: 2vh;
  padding: 1vh;
  background-color: rgb(235, 164, 89);
  margin-top: 1vh;
  margin-bottom: 1vh;
  transition: 0.3s;
  font-size: 3vh;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  font-weight: 600;
  color: rgb(75, 75, 75);
  border: 0.5vh solid rgb(235, 164, 89);
  text-align: center;
}

.dashboard-button:hover {
  background-color: rgb(136, 93, 48);
  color: whitesmoke;
  border-color: rgb(75, 75, 75);
}

.dashboard-title {
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  font-size: 3vh;
  font-weight: 600;
  color: rgb(59, 58, 58);
  padding-bottom: 5vh;
}

.dashboard-spacer {
  width: 3vw;
}
</style>
