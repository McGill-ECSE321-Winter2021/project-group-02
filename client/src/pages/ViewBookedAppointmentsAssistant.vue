<template>
  <div class="view-booked-appointments-assistant">
    <div id="booked-appointments-container">
        <form id="customerIdForm">
        <div class="form-customer-id">
        <p>Customer for who to get the appointments of: </p>
        <input
			id="customer-id-input"
			v-model="customerIDEntry"
			type="text"
			placeholder="customer ID"
		/>
        </div>

        <div class="form-sub">
            <button id="submit-button" type="button" 
            v-on:click=getToApptPage() >Submit</button>
        </div>

        </form>

        <div>
        <p id='result'></p>
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

<div id="appointment-containter"
    v-if= "this.enteredValue==true" >
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

        <br>
        

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
    customer:[],
      appointments: [],
      timeslots:[],
      customerIDEntry: "",
      enteredValue:false,
    };
  },

    methods: {

//     getUserName:function() {
//     var customerId = document.getElementById('customer-id-input').value;
//     var result = document.getElementById('result');

// if (customerId.length < 1) {
//     result.textContent = 'Customer ID must contain at least 1 number';
//     //alert('Username must contain at least 3 characters');
// } else {
//     result.textContent = 'The entered customer ID is: ' + customerId;
//     //alert(nameField);
// }
// },
    getToApptPage: function(){
        console.log("hello");
        this.getAppointments(this.customerIDEntry);
    },

    getAppointments: async function(customerId){
        try {
      
      let appointmentResponse = await axios.get(
        proxy.proxy+`/api/appointment/getall/${customerId}`
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

    this.enteredValue=true;
    },

    rate: function(appointmentId) {
        console.log(appointmentId)
    },
    pay: function(appointmentId) {
        console.log(appointmentId) //not reactive??

    },
    modify: function(appointmentId) {   
        console.log(appointmentId)
   
    },

//CarWash, Maintenance, OilChange, TireChange, Towing, Inspection, RoadsideAssistance, Checkup, Other
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


    getUserID: async function(){
        try {
			let response = await axios.get(
				proxy.proxy+`/api/customer/getByID/${this.customerIDEntry}` //need to pass the variable but how customerIDEntry
			);
            if (response.data == null || response.status !== 200){
                
                
                
                return;
            }
  
            this.customer = this.customer.data;
            } catch (error) {
      console.log(`${error}`);
    }

    },
    

  
},
};
</script>


<style scoped>

.view-booked-appointments-assistant {
  height: 110vh;
  width: 100vw;
  background-image: radial-gradient(whitesmoke 20%, transparent 20%),
    radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
  background-position: 0 0, 7.5vh 7.5vh;
  background-size: 15vh 15vh;
  background-color: rgb(238, 207, 173);

  display: flex;
align-items: center; 
 justify-content: center; 
flex-direction: column;
}

#booked-appointments-container {
  display: flex;
  /* grid-template-columns: 40vw 40vw; */
  align-items: center;
  justify-content: center;
  flex-direction: column;
  justify-content: center;
  height: 20vh;
  width: 30vw;
  border-radius: 5vh;
  background-color: whitesmoke;
  transition: 0.3s;

  padding:15px;
} 

.view-booked-appointments-assistant{
  font-size: 2vh;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
	font-weight: 600;
	color: rgb(75, 75, 75);
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
