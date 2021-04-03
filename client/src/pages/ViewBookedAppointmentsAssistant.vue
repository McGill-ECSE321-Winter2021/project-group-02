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
            <button id="submit-button" type="button">Submit</button>
        </div>
        </form>

        <div>
        <p id='result'></p>
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
    };
  },

    methods: {

    getUserName:function() {
    var customerId = document.getElementById('customer-id-input').value;
    var result = document.getElementById('result');

if (customerId.length < 1) {
    result.textContent = 'Customer ID must contain at least 1 number';
    //alert('Username must contain at least 3 characters');
} else {
    result.textContent = 'The entered customer ID is: ' + customerId;
    //alert(nameField);
}
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
				proxy.proxy+`/api/customer/getByID/${this.$store.state.user}` //need to pass the variable but how customerIDEntry
			);
            if (response.data == null || response.status !== 200){
                // document.getElementById("myaccount-edit-error").innerHTML =
				// 			"No such user exists";
				// 		document.getElementById("myaccount-edit-error").style.opacity = 1;
				// 		return;
                
                
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
/* align-items: center; */
/* justify-content: center; */
flex-direction: column;
}

#booked-appointments-container {
  display: grid;
  grid-template-columns: 40vw 40vw;
  height: 30vh;
  width: 30vw;
  border-radius: 5vh;
  background-color: whitesmoke;
  transition: 0.3s;
} 


</style>
