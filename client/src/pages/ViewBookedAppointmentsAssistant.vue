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
    <div class="myaccount-spacer"></div>

        <div class="form-sub">
            <button 
            class="myappointments-button"
            id="submit-button" type="button" 
            v-on:click=getToApptPage() >Submit</button>
            
            <p id="myaccount-edit-error"></p>

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

    <div class="myaccount-spacer"></div>


        <div class="list-or-not-appointments"
        v-if="appointments.length===0"
        >
        <p>No appointments</p>

        </div>
        <div class="appointment"
        v-else
          v-for="(appointment,index) in appointments"
          :key="index"
          :id="appointment.appointmentId"
        >

        <label 
        class="form-text"
        v-if="timeslots.startDate===timeslots.endDate"
        >Appointment Time: 
        {{timeslots.startDate}} </label>

        <label 
        class="form-text"
        v-if="timeslots.startDate!==timeslots.endDate"
        >Appointment Time: 
        {{timeslots.startDate}}-{{timeslots.endDate}} </label>
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
      customerReceived:[],
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
        this.getUserID(this.customerIDEntry);
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
					// fetch data of user with given ID
					let response = null;
                    response = await axios.get(
                        proxy.proxy + `/api/customer/getByID/${this.customerIDEntry}`
                    );
                    this.customerReceived = response.data;

                    if(this.customerReceived===null){
                        console.log("hoi");
                        document.getElementById("myaccount-edit-error").innerHTML =
                            "User not identified, please try again";
                        document.getElementById("myaccount-edit-error").style.opacity = 1;
                        document.getElementById("customerIdForm").reset();
                        this.customerIDEntry="";
                        this.customer=[];
                        return;
                    }
                    return;

				} catch (error) {
					console.error(`${error}`);
					document.getElementById("myaccount-edit-error").innerHTML =
						"Unknown error, please try again later";
					document.getElementById("myaccount-edit-error").style.opacity = 1;
				}
			},
    
},
};
</script>


<style scoped>

#customer-id-input {
		all: unset;
		background-color: rgb(212, 211, 211);
		font-size: 2vh;
		width: 8vw;
		margin-top: 1vh;
		margin-bottom: 1vh;
		padding: 2vh;
		border-radius: 2vh;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-weight: 600;
		color: rgb(59, 58, 58);
		transition: 0.3s;
	}

.myaccount-spacer{
  height: 1vw;
}
.appointment{
    background-color: whitesmoke;
    border-radius: 5vh;
    padding: 15px;
      width: 30vw;
display: flex;
  /* grid-template-columns: 40vw 40vw; */
  align-items: center;
  justify-content: center;
  flex-direction: column;
  justify-content: center;
    
}
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
