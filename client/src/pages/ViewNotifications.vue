<template>
  <div class="view-notifications">
<div id="notifications-container">

<div class="myaccount-spacer"></div>

      <div id="notifications-containter">
        <div class="notification"
          v-for="(notification,index) in notifications"
          :key="index"
          :id="notification.appointmentId"
        >
        <label class="form-text">Appointment Time: {{timeslots.startDate}}-{{timeslots.endDate}} </label><br>
        <label class="form-text">From {{timeslots.startTime}} to {{timeslots.endTime}}</label><br>
        <label class="form-text">Appointment Type: {{convertForDisplay(notification.appointmentType)}}</label><br>
          
    <!-- <div class="myaccount-spacer"></div> -->

        </div>

        </div>

      </div>

      <div id="dashboard-change-opacity">
        
			<input
				id="back-button"
				type="button"
				value="Back"
				v-on:click="backViewDash()"
				/>

      </div>
    </div>


</template>

<script>
	import axios from "axios";
	import proxy from "../constants.js";

export default {
    name: "ViewNotifications",
    props: {
    msg: String,
    },
  components: {},
  data() {
    return {
      notifications: [],
      timeslots:[],
    };
  },
    methods: {},
  async mounted() {
    try {
      
      let notificationResponse = await axios.get(
        proxy.proxy+`api/appointment/notifications/${this.$store.state.user}`
      );
      if (notificationResponse.status !== 200) return;
      this.notifications = notificationResponse.data;
    } catch (error) {
      console.log(`${error}`);
    }
  },

convertForDisplay: function(type){
      switch (type) {
          case "CarWash":
            return "Car Wash";
          case "Maintenance":
            return "Maintenance";
          case "OilChange":
            return "Oil Change";
          case "TireChange":
            return "Tire Change";
          case "Towing":
            return "Towing";
          case "Inspection":
            return "Inspection";
          case "RoadsideAssistance":
            return "Roadside Assistance";
          case "Checkup":
            return "Checkup";
          default:
            return "Other";
      }
    },

};
</script>

<style scoped>
.view-notifications {
  height: 110vh;
  width: 100vw;
  background-image: radial-gradient(whitesmoke 20%, transparent 20%),
    radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
  background-position: 0 0, 7.5vh 7.5vh;
  background-size: 15vh 15vh;
  background-color: rgb(238, 207, 173);

  display: flex;
/* align-items: center; */
/* justify-content: center; */
flex-direction: column;
}

</style>