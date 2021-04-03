<template>
<div class="appointmentsassistant">
		<div id="appointmentsassistant-container">
			<H1 id="appointmentsassistant-title" class="appointmentsassistant-title"
				>Booked Appointments</H1>
			
      <div class="list-container">
      <div
				class="appointmentsassistant-list"
				v-for="(appointment, index) in appointments"
				:key="index"
				:id="appointment.appointmentId"
			>

        <div class="appointmentsassistant-text-container">
          <label class="form-text">
            {{ convertForDisplay(appointment.appointmentType) }}</label
          ><br />
          <label
            class="form-text"
            v-if="timeslots.startDate === timeslots.endDate"
          >
            {{ timeslots.startDate }}
          </label>
          <label
            class="form-text"
            v-if="timeslots.startDate !== timeslots.endDate"
          >
            {{ timeslots.startDate }}-{{ timeslots.endDate }}
          </label>
          <br />
          <label class="form-text"
            >{{ timeslots.startTime }} to {{ timeslots.endTime }}</label
          ><br />

        </div>

        <button
          v-if="appointment.paymentStatus==false"
          class="appointmentsassistant-button"
          v-on:click="pay(appointmentId)"
        >Pay</button>

        <button
          class="appointmentsassistant-button"
          v-on:click="modify(appointmentId)"
        >Modify</button>

        <button
          class="appointmentsassistant-button"
          v-on:click="rate(appointmentId)"
        >Rate</button>

			</div>
			<span 
				class="no-appointmentsassistant"
				v-if="appointments.length===0"
			>
				No appointments
			</span>
			<button
				id="back-button"
				v-on:click="backViewDash()"
				> Back</button>

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
        proxy.proxy+`/api/appointment/getallappointments`
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

.appointmentsassistant {
		height: 100vh;
		width: 100vw;
		background-image: radial-gradient(whitesmoke 20%, transparent 20%),
			radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
		background-position: 0 0, 7.5vh 7.5vh;
		background-size: 15vh 15vh;
		background-color: rgb(238, 207, 173);
	}

	#appointmentsassistant-container {
		height: 70vh;
		width: 50vw;
		border-radius: 5vh;
		background-color: whitesmoke;
	}

	.appointmentsassistant,
	#appointmentsassistant-container {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		transition: 0.3s;
		
	}

	.appointmentsassistant-list,
	.no-appointmentsassistant {
		background-color:  rgb(238, 207, 173);
		margin: 1vh;
		border-radius: 1vh;
		padding: 1vh;
		width: 30vw;
		text-align: center;

		font-size: 3vh;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-weight: 600;
		color: rgb(75, 75, 75);
		
	}

	.no-appointmentsassistant {
		margin-top: 8vh;
	}

  .appointmentsassistant-button{
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
	border: 0.5vh solid  rgb(238, 207, 173);
}

.appointmentsassistant-button:hover {
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



	.appointmentsassistant-title {
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-size: 4.5vh;
		font-weight: 600;
		color: rgb(59, 58, 58);
		padding-bottom: 3vh;
		animation: changeOpacity 0.3s;
		transition: 0.3s;
		margin-top:3vh;

	}

  .list-container{
    overflow-y:scroll;
	margin-bottom: 3vh;
  }

	@keyframes changeOpacity {
		from {
			opacity: 0;
		}

		to {
			opacity: 1;
		}
	}
</style>


