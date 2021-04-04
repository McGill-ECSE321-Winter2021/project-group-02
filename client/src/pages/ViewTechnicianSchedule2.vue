<template>
  <div class="view-technician-schedule2">
    <div class="view-technician-schedule2-container">
      <div id="view-technician-schedule2-opacity-container">
        <select
          class="view-technician-schedule2-input"
          v-model="selectedTechnician"
          @change="technicianChange()"
        >
          <option value="" selected disabled>Choose Technician</option>
          <option
            v-for="(technician, indexT) in this.technicians"
            :key="indexT"
            :value="technician.technicianId"
            >{{ technician.technicianName }}</option
          >
        </select>
        <p
          v-if="this.appointments.length === 0"
          class="view-technician-schedule2-no-appointment"
        >
          No Schedule!
        </p>
        <div v-for="(appointment, index) in this.appointments" :key="index">
          <div class="view-technician-schedule2-appointement-container">
            <p class="view-technician-schedule2-text">
              Start date: {{ appointment.startDate }}
            </p>
            <p class="view-technician-schedule2-text">
              End date: {{ appointment.endDate }}
            </p>
            <p class="view-technician-schedule2-text">
              Start Time: {{ appointment.startTime }}
            </p>
            <p class="view-technician-schedule2-text">
              End Time: {{ appointment.endTime }}
            </p>
            <p class="view-technician-schedule2-text">
              Workspace: {{ appointment.workspaceId }}
            </p>
          </div>
          <div class="view-technician-schedule2-spacer"></div>
        </div>
        <button class="view-technician-schedule2-button" v-on:click="back()">
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
  name: "ViewTechnicianSchedule2",
  props: {
    msg: String,
  },
  components: {},
  data() {
    return {
      appointments: [],
      technicians: [],
      selectedTechnician: -1,
    };
  },
  methods: {
    back: function() {
      document.getElementById(
        "view-technician-schedule2-opacity-container"
      ).style.opacity = 0;
      let vm = this;
      setTimeout(function() {
        vm.$router.push("/dashboard");
      }, 300);
    },
    technicianChange: async function() {
      if (this.selectedTechnician === -1) return;
      try {
        let now = new Date(Date.now());
        let nextWeek = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000);
        let nowString = `${now.getFullYear()}-${now.getMonth() +
          1}-${now.getDate()}`;
        let nextWeekString = `${nextWeek.getFullYear()}-${nextWeek.getMonth() +
          1}-${nextWeek.getDate()}`;
        let scheduleResponse = await axios.get(
          proxy.proxy +
            `api/technician/viewschedule/${this.selectedTechnician}/${nowString}/${nextWeekString}`
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
              if (
                this.appointments[i].workspaceId === workspaces[j].workspaceId
              )
                this.appointments[i].workspaceId = workspaces[i].spaceName;
            }
          }
          document.getElementById(
            "view-technician-schedule2-opacity-container"
          ).style.opacity = 1;
          console.log(this.appointments);
        } catch (error) {
          console.log(error);
        }
      } else
        document.getElementById(
          "view-technician-schedule2-opacity-container"
        ).style.opacity = 1;
    },
  },
  async mounted() {
    try {
      let technicianResponse = await axios.get(
        proxy.proxy + `api/technician/getAll`
      );
      if (technicianResponse.status !== 200) return;
      this.technicians = technicianResponse.data;
    } catch (error) {
      console.log(error);
    }
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.view-technician-schedule2 {
  height: 100vh;
  width: 100vw;
  background-image: radial-gradient(whitesmoke 20%, transparent 20%),
    radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
  background-position: 0 0, 7.5vh 7.5vh;
  background-size: 15vh 15vh;
  background-color: rgb(238, 207, 173);
}

.view-technician-schedule2,
.view-technician-schedule2-container,
.view-technician-schedule2-button,
.view-technician-schedule2-appointement-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}

.view-technician-schedule2-container {
  height: 80vh;
  width: 80vw;
  border-radius: 5vh;
  background-color: whitesmoke;
}

#view-technician-schedule2-opacity-container {
  height: 100%;
  width: 100%;
  border-radius: 5vh;
  transition: 0.3s;
  overflow-y: scroll;
  padding-top: 5vh;
  padding-bottom: 5vh;
  display: flex;
  align-items: center;
  flex-direction: column;
}

.view-technician-schedule2-button {
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

.view-technician-schedule2-button:hover {
  background-color: rgb(136, 93, 48);
  color: whitesmoke;
  border-color: rgb(75, 75, 75);
}

.view-technician-schedule2-no-appointment {
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

.view-technician-schedule2-text {
  font-size: 2vh;
  font-weight: 500;
  color: whitesmoke;
  transition: 0.3s;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
}

.view-technician-schedule2-appointement-container {
  height: 15vh;
  width: 30vw;
  background-color: grey;
  border-radius: 3vh;
  margin: auto;
}

.view-technician-schedule2-spacer {
  height: 2vh;
}

.view-technician-schedule2-input {
  all: unset;
  background-color: rgb(212, 211, 211);
  font-size: 3vh;
  margin-top: 1vh;
  margin-bottom: 1vh;
  padding: 2vh;
  border-radius: 2vh;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  font-weight: 600;
  color: rgb(59, 58, 58);
  transition: 0.3s;
  margin: auto;
}
</style>
