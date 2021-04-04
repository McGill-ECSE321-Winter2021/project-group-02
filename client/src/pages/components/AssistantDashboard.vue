<template>
  <div class="assistant-dashboard" id="assistant-dashboard">
    <div class="assistant-dashboard-container" id="a">
      <p class="dashboard-title">Appointments</p>
      <div class="assistant-dashboard-inside-container">
        <button class="dashboard-button" @click="bookAppointment()">
          Create Appointment
        </button>
        <div class="dashboard-spacer"></div>
        <button class="dashboard-button" v-on:click="viewBookedAppt()">
          View Appointments
        </button>
      </div>
    </div>
    <div class="assistant-dashboard-container" id="b">
      <p class="dashboard-title">Accounts</p>
      <button class="dashboard-button" @click="createAccount()">
        Create Account
      </button>
      <button class="dashboard-button" @click="modifyAccount()">
        Modify Account
      </button>
    </div>
    <div class="assistant-dashboard-container" id="c">
      <p class="dashboard-title">Technician</p>
      <button class="dashboard-button" @click="viewTechnicianSchedule()">
        View Technician Schedule
      </button>
      <button class="dashboard-button" @click="assignSchedule()">
        Assign Schedule to Technician
      </button>
    </div>
    <div class="assistant-dashboard-container" id="d">
      <p class="dashboard-title">Worspaces</p>
      <button class="dashboard-button" @click="workspace()">
        View Spaces and Availabilities
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "AssistantDashboard",
  props: {
    msg: String,
  },
  data() {
    return {
      userType: "",
    };
  },
  methods: {
    modifyAccount: function() {
      document.getElementById("assistant-dashboard").style.opacity = 0;
      document.getElementById("logout-button").style.opacity = 0;
      let vm = this;
      setTimeout(function() {
        vm.$router.push("/myaccount");
      }, 300);
    },
    assignSchedule: function() {
      document.getElementById("assistant-dashboard").style.opacity = 0;
      document.getElementById("logout-button").style.opacity = 0;
      let vm = this;
      setTimeout(function() {
        vm.$router.push("/assigntechnicianschedule");
      }, 300);
    },
    viewTechnicianSchedule: function() {
      document.getElementById("assistant-dashboard").style.opacity = 0;
      document.getElementById("logout-button").style.opacity = 0;
      let vm = this;
      setTimeout(function() {
        vm.$router.push("/viewtechnicianschedule2");
      }, 300);
    },
    createAccount: function() {
      document.getElementById("assistant-dashboard").style.opacity = 0;
      document.getElementById("logout-button").style.opacity = 0;
      let vm = this;
      setTimeout(function() {
        vm.$router.push("/admincreateaccount");
      }, 300);
    },
    bookAppointment: function() {
      document.getElementById("assistant-dashboard").style.opacity = 0;
      document.getElementById("logout-button").style.opacity = 0;
      let vm = this;
      setTimeout(function() {
        vm.$router.push("/bookappointment");
      }, 300);
    },
    workspace: function() {
      document.getElementById("assistant-dashboard").style.opacity = 0;
      document.getElementById("logout-button").style.opacity = 0;
      let vm = this;
      setTimeout(function() {
        vm.$router.push("/workspace");
      }, 300);
    },
    viewBookedAppt: function() {
      let t = this;

      setTimeout(function() {
        t.$router.push("/viewbookedappointmentsassistant");
      }, 300);
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.assistant-dashboard {
  display: grid;
  grid-template-columns: 40vw 40vw;
  grid-template-rows: 40vh 40vh;
  height: 80vh;
  width: 80vw;
  border-radius: 5vh;
  background-color: whitesmoke;
  transition: 0.3s;
}

.assistant-dashboard-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}

.assistant-dashboard-inside-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: row;
}

#a,
#b,
#c,
#d {
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

#c {
  grid-row: 2/3;
  grid-column: 1/2;
}

#d {
  grid-row: 2/3;
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
