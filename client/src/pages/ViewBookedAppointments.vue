<template>
  <div class="view-booked-appointments">
    <div id="bookedappointments-container">

<button id="rate-button" @click="rate()">Rate</button>
<button id="pay-button" @click="pay()">Pay</button>
<button id="change-button" @click="change()">Change</button>

    </div>
  </div>
		
</template>

<style scoped>
.view-booked-appointments {
  height: 100vh;
  width: 100vw;
  background-image: radial-gradient(whitesmoke 20%, transparent 20%),
    radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
  background-position: 0 0, 7.5vh 7.5vh;
  background-size: 15vh 15vh;
  background-color: rgb(238, 207, 173);
}

.view-booked-appointments {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
</style>


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
    };
  },

    methods: {
    rate: function() {



    },
    pay: function() {


    },
    change: function() {

        
    }
    },
  async mounted() {
    try {
      
      let appointmentResponse = axios.get(
        proxy.proxy`api/appointment/getall/${this.$store.state.user}`
      );
      if (appointmentResponse.status !== 200) return;
      this.appointments = appointmentResponse.data;
    } catch (error) {
      console.log(`${error}`);
    }
  },
};
</script>