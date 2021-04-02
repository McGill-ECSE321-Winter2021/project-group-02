<template>
  <div class="view-technician-schedule">
    <div class="view-technician-schedule-container">
      <div id="view-technician-schedule-opacity-container">
        <p
          v-if="this.appointments.length === 0"
          class="view-technician-schedule-no-appointment"
        >
          No Schedule!
        </p>
        <div v-for="(appointment, index) in this.appointments" :key="index">
          <div class="view-technician-schedule-appointement-container">
            <p class="view-technician-schedule-text">
              Start date: {{ appointment.startDate }}
            </p>
            <p class="view-technician-schedule-text">
              End date: {{ appointment.endDate }}
            </p>
            <p class="view-technician-schedule-text">
              Start Time: {{ appointment.startTime }}
            </p>
            <p class="view-technician-schedule-text">
              End Time: {{ appointment.endTime }}
            </p>
            <p class="view-technician-schedule-text">
              Workspace: {{ appointment.workspaceId }}
            </p>
          </div>
          <div class="view-technician-schedule-spacer"></div>
        </div>
        <button class="view-technician-schedule-button" v-on:click="back()">
          Back
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import proxy from "../constants.js";

export default {
  name: "ViewTechnicianSchedule",
  props: {
    msg: String,
  },
  components: {},
  data() {
    return {
      appointments: [],
    };
  },
  methods: {
    back: function() {
      document.getElementById(
        "view-technician-schedule-opacity-container"
      ).style.opacity = 0;
      let vm = this;
      setTimeout(function() {
        vm.$router.push("/dashboard");
      }, 300);
    },
  },
  async mounted() {
    try {
      let now = new Date(Date.now());
      let nextWeek = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000);
      let nowString = `${now.getFullYear()}-${now.getMonth() +
        1}-${now.getDate()}`;
      let nextWeekString = `${nextWeek.getFullYear()}-${nextWeek.getMonth() +
        1}-${nextWeek.getDate()}`;
      let scheduleResponse = await axios.get(
        proxy.proxy +
          `api/technician/viewschedule/${this.$store.state.user}/${nowString}/${nextWeekString}`
      );
      if (scheduleResponse.status !== 200) return;
      this.appointments = scheduleResponse.data;
    } catch (error) {
      console.log(error);
    }

    if (this.appointments.length !== 0) {
      try {
        let workspaceResponse = await axios.get(
          proxy.proxy + `api/workspace/getall/`
        );
        if (workspaceResponse.status !== 200) return;
        let workspaces = workspaceResponse.data;
        for (let i = 0; i < this.appointments.length; i++) {
          for (let j = 0; j < workspaces.length; j++) {
            if (this.appointments[i].workspaceId === workspaces[j].workspaceId)
              this.appointments[i].workspaceId = workspaces[i].spaceName;
          }
        }
        document.getElementById(
          "view-technician-schedule-opacity-container"
        ).style.opacity = 1;
        console.log(this.appointments);
      } catch (error) {
        console.log(error);
      }
    }
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.view-technician-schedule {
  height: 100vh;
  width: 100vw;
  background-image: radial-gradient(whitesmoke 20%, transparent 20%),
    radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
  background-position: 0 0, 7.5vh 7.5vh;
  background-size: 15vh 15vh;
  background-color: rgb(238, 207, 173);
}

.view-technician-schedule,
.view-technician-schedule-container,
.view-technician-schedule-button,
.view-technician-schedule-appointement-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}

.view-technician-schedule-container {
  height: 80vh;
  width: 80vw;
  border-radius: 5vh;
  background-color: whitesmoke;
}

#view-technician-schedule-opacity-container {
  opacity: 0;
  height: 100%;
  width: 100%;
  border-radius: 5vh;
  transition: 0.3s;
  overflow-y: scroll;
  padding-top: 5vh;
  padding-bottom: 5vh;
}

.view-technician-schedule-button {
  all: unset;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 5vh;
  width: 12vw;
  border-radius: 2vh;
  padding: 1vh;
  background-color: rgb(235, 164, 89);
  transition: 0.3s;
  font-size: 3vh;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  font-weight: 600;
  color: rgb(75, 75, 75);
  border: 0.5vh solid rgb(235, 164, 89);
  margin: auto;
}

.view-technician-schedule-button:hover {
  background-color: rgb(136, 93, 48);
  color: whitesmoke;
  border-color: rgb(75, 75, 75);
}

.view-technician-schedule-no-appointment {
  font-size: 3vh;
  font-weight: 600;
  color: rgb(59, 58, 58);
  transition: 0.3s;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  padding-top: 3vh;
  padding-bottom: 3vh;
  margin: auto;
  text-align: center;
}

.view-technician-schedule-text {
  font-size: 2vh;
  font-weight: 500;
  color: whitesmoke;
  transition: 0.3s;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
}

.view-technician-schedule-appointement-container {
  height: 15vh;
  width: 30vw;
  background-color: grey;
  border-radius: 3vh;
  margin: auto;
}

.view-technician-schedule-spacer {
  height: 2vh;
}
</style>
