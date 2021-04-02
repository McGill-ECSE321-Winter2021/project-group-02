<template>
  <div class="view-booked-appointments">
    <div id="booked-appointments-container">
        
<!-- Add No Appointments as default -->

        <ul id="list-of-appointments">
        <li v-for="appointment in appointments" :key="appointment.appointmentId">
            
            <div class="myaccount-spacer"></div>

            <div class="assignement-slot">

                <p>Date:</p>
                <br>
                <p>Date:</p> 
                <br>
            <!-- Appointment Type:  -->
            {{ appointment.appointmentType }}
            <br/>
            <p v-if="appointment.paymentStatus">Paid: Yes</p>
            <p v-else>Paid: No</p>

        <div class="myappointments-button-container">
            <div class="vertical-center">
			<input
				class="myappointments-button"
				type="button"
				value="Rate"
				v-on:click="rate(appointmentId)"
				/>
            <input
				class="myappointments-button"
				type="button"
				value="Pay"
				v-on:click="pay(appointmentId)"
				/>
                
            <input
				class="myappointments-button"
				type="button"
				value="Change"
				v-on:click="change(appointmentId)"
				/>
            </div>
</div>
        </div>
    </li>
        </ul>

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
    name: "ViewBookedAppointments",
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
    change: function(appointmentId) {   
        console.log(appointmentId)
   
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
      
      let appointmentResponse = axios.get(
        proxy.proxy+`/api/appointment/getall/${this.$store.state.user}`
      );
    console.log(appointmentResponse);

      if (appointmentResponse.status !== 200) return;
      this.appointments = appointmentResponse.data;

    } catch (error) {
      console.log(`${error}`);
    }




    // try {

    //   let timeslotResponse = axios.get(
    //     proxy.proxy+`/api/appointment/getStartAndEnd/${this.appointments[0].appointmentId}`
    //   );
    //   if (timeslotResponse.status !== 200) return;
    //   this.timeslots = timeslotResponse.data;

    // } catch (error) {
    //   console.log(`${error}`);
    // }
    


  },
};
</script>


<style scoped>

#list-of-appointments{
    background-color: wheat;
}

.assignement-slot{
    width: 800px;
    text-align: left;
    margin-right: auto;
    margin-left: auto;
    font-size: 2.5vh;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-weight: 600;
		color: rgb(75, 75, 75);
        background:whitesmoke;
    padding: 30px;
    border-radius: 5vh;
}

ul {
    list-style-type: none;
}

#booked-appointments-container {
  display: grid;
  grid-template-columns: 40vw 40vw;
  height: 80vh;
  width: 80vw;
  border-radius: 5vh;
  background-color: whitesmoke;
  transition: 0.3s;
}
/* bg */
.view-booked-appointments {
  height: 110vh;
  width: 100vw;
  background-image: radial-gradient(whitesmoke 20%, transparent 20%),
    radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
  background-position: 0 0, 7.5vh 7.5vh;
  background-size: 15vh 15vh;
  background-color: rgb(238, 207, 173);
}

.myappointments-button-container{
    height: 300px;
  position: relative;
  border: 0.5vh solid rgb(235, 164, 89);
  
}

.myaccount-spacer {
		height: 1vw;
	}

.vertical-center {
  margin: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}


    .view-booked-appointments {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    }

    .appointment {
		display: grid;
		grid-template-columns: 40vw 40vw;
		grid-template-rows: 40vh 40vh;
		height: 80vh;
		width: 80vw;
		border-radius: 5vh;
		background-color: whitesmoke;
		transition: 0.3s;
	}

	.appointment-container {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}

	.appointment-inside-container {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: row;
	}

	.myappointments-button {
		all: unset;
        display: flex;
		align-items: center;
		justify-content: center;
		height: 5vh;
		width: 12vw;
		border-radius: 2vh;
		padding: 0.1vh;
		background-color: rgb(235, 164, 89);
		margin-top: 1vh;
		margin-bottom: 1vh;
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


