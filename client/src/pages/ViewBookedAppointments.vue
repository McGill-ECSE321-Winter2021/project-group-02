<template>
  <div class="view-booked-appointments">
    <div id="booked-appointments-container">
      <!-- <label class="form-text">Appointments:</label><br> -->

    <!-- <div class="myaccount-spacer"></div> -->

      <div id="appointment-containter">
        <div class="appointment"
          v-for="(appointment,index) in appointments"
          :key="index"
          :id="appointment.appointmentId"
        >

        <label 
        class="form-text"
        v-if="timeslots.startDate===timeslots.endDate"
        >Appointment Time: {{timeslots.startDate}} </label>

        <label 
        class="form-text"
        v-if="timeslots.startDate!==timeslots.endDate"
        >Appointment Time: {{timeslots.startDate}}-{{timeslots.endDate}} </label>
        <br>


        <label class="form-text">From {{timeslots.startTime}} to {{timeslots.endTime}}</label><br>
        <label class="form-text">Appointment Type: {{convertForDisplay(appointment.appointmentType)}}</label>

        <div class="myaccount-spacer"></div>


        <div class="myappointments-button-container">
        
          <input

          v-if="appointment.paymentStatus==false"
          class="myappointments-button"
          type="button"
          value="Pay"
          v-on:click="pay(appointmentId)"
          />  

          <input
          class="myappointments-button"
          type="button"
          value="Modify"
          v-on:click="modify(appointmentId)"
          />

          <input
          class="myappointments-button"
          type="button"
          value="Rate"
          v-on:click="rate(appointmentId)"
          />
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

  </div>

</template>

<script>
	import axios from "axios";
	import proxy from "../constants.js";

export default {
    name: "ViewBookedAppointmentsAssistant",
    props: {
    msg: String,
    },
  components: {},
  data() {
    return {
      appointments: [],
      timeslots:[],
    };
  },

    methods: {
    rate: function(appointmentId) {
        console.log(appointmentId)
    },
    pay: function(appointmentId) {
        console.log(appointmentId) //not reactive??

    },
    modify: function(appointmentId) {   
        console.log(appointmentId)
   
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

    },

  async mounted() {
    try {
      
      let appointmentResponse = await axios.get(
        proxy.proxy+`/api/appointment/getall/${this.$store.state.user}`
      );
    console.log(appointmentResponse);

      if (appointmentResponse.status !== 200) return;
      this.appointments = appointmentResponse.data;

    } catch (error) {
      console.log(`${error}`);
    }


    var i;
    for(i=0;i<this.appointments.length; i++ ){
      try {

      let timeslotResponse = await axios.get(
        proxy.proxy+`/api/appointment/getStartAndEnd/${this.appointments[i].appointmentId}`
      );
      if (timeslotResponse.status !== 200) return;
      this.timeslots = timeslotResponse.data;

    } catch (error) {
      console.log(`${error}`);
    }
    }


  },
};
</script>


<style scoped>

.view-booked-appointments {
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

#booked-appointments-container{
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
 
}

.appointment{


  text-align:center;
}


#appointment-containter{
  display: flex;
  background-color: whitesmoke;
  width:600px;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  justify-content: center;

  border-radius: 5vh;
  padding:10px;
}

.myaccount-spacer{
  height: 1vw;
}

/* Text */

.form-text{
  font-size: 3vh;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
	font-weight: 600;
	color: rgb(75, 75, 75);
}

/* Buttons */

/* .myappointments-button-container{
  align-items: center;
  justify-content: center;
  flex-direction: column;
} */


.myappointments-button{
  all: unset;
  /* display: flex; */
  text-align: center;
	align-items: center;
	justify-content: center;
	height: 4vh;
	width: 8vw;
  border-radius: 2vh;
	padding: 0.1vh;
	background-color: rgb(235, 164, 89);
	margin-bottom: 1vh;
  margin-left: 0.5vh;
  margin-right: 0.5vh;
	transition: 0.3s;
	font-size: 3vh;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
	font-weight: 600;
	color: rgb(75, 75, 75);
	border: 0.5vh solid rgb(235, 164, 89);
}

.myappointments-button:hover {
		background-color: rgb(175, 122, 65);
		color: whitesmoke;
		border-color: rgb(75, 75, 75);
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


