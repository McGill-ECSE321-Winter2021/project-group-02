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

    backViewDash: function() {
      let t = this;
      setTimeout(function() {
        t.$router.push("/dashboard");
      }, 300);
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


#back-button {
  all: unset;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 3vh;
  width: 8vw;
  border-radius: 2vh;
  padding: 1vh;
  background-color: rgb(182, 182, 182);
  margin-top: 1vh;
  margin-bottom: 1vh;
  transition: 0.3s;
  font-size: 3vh;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  font-weight: 600;
  color: rgb(75, 75, 75);
  border: 0.5vh solid rgb(182, 182, 182);
  text-align: center;
  transition: 0.3s;
  position: absolute;
  top: 1vh;
  right: 1vw;
  animation: changeOpacity 0.3s;
}

#back-button:hover {
  background-color: rgb(139, 139, 139);
  color: whitesmoke;
  border-color: rgb(75, 75, 75);
}

</style>