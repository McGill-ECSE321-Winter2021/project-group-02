<template>
  <div class="view-technician-schedule"></div>
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
  methods: {},
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
      console.log(this.appointments);
    } catch (error) {
      console.log(error);
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

.view-technician-schedule {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
</style>
