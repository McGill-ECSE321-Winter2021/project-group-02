<template>
  <div id="book-appointment">
    <div id="book-appointment-container">
      <H1 id="book-appointment-title">Create Appointment</H1>

      <form id="appointment-form"
            @submit.prevent="bookAppointment()"
      >
        <div class="form-container">
          <div id="service-container">
            <label class="form-label">Select a service:</label>
            <select id="appointment-type"
                    v-model="apptType"
            >
              <option value="" selected disabled>Choose service</option>
              <option value="carWash">Car Wash</option>
              <option value="maintenance">Maintenance</option>
              <option value="oilChange">Oil Change</option>
              <option value="tireChange">Tire Change</option>
              <option value="towing">Towing</option>
              <option value="inspection">Inspection</option>
              <option value="roadsideAssistance">Roadside Assistance</option>
              <option value="checkup">Checkup</option>
              <option value="other">Other</option>
            </select>
          </div>
          <input id="form-service"
                   v-if="this.apptType === 'other'"
                   type="text"
                   placeholder="service"
            >

          <div id="timeslot-container">
          </div>

          <label class="form-label">Appointment message:</label>
          <textarea id="form-note" placeholder="Write notes to the mechanic..."></textarea>
        </div>

        <div id="button-container">
          <input type="button" class="form-button" value="Cancel" @click="backViewDash()">
          <input type="submit" class="form-button" value="Book">
        </div>
      </form>
    </div>
  </div>
</template>

<script>
  import axios from "axios";
  import proxy from "../constants.js";

  export default {
    name: "BookAppointment",
    props: {
      msg: String,
    },
    data() {
      return {
        apptType:"",
        apptService:"",
        apptNote:"",
        custId:this.$store.state.user,
        timeId:-1
      };
    },
    methods: {
      backViewDash: function() {
        let t = this;
        document.getElementById("myaccount-edit").style.opacity = 0;
        document.getElementById("myaccount-generic-title").style.opacity = 0;
        setTimeout(function() {
          t.$router.push("/dashboard");
        }, 300);
      },

      bookAppointment: async function() {
        try {
          let bookAppointmentData = {
            appointmentType: this.apptType,
            service: this.apptService,
            note: this.apptNote,
            customerId: this.custId,
            timeslotsId: this.timeId,
          };

          let bookingResult = await axios.post(
              proxy.proxy + "/api/appointment/book",
              bookAppointmentData
          );

          console.log(bookingResult.data)
        } catch (error) {
          console.log(`${error}`)
        }
      }
    }
  }
</script>

<style scoped>
  #book-appointment,
  #book-appointment-container,
  #timeslot-container,
  #appointment-form{
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    transition: 0.3s;
  }

  #book-appointment {
    height: 100vh;
    width: 100vw;
    background-image: radial-gradient(whitesmoke 20%, transparent 20%),
    radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
    background-position: 0 0, 7.5vh 7.5vh;
    background-size: 15vh 15vh;
    background-color: rgb(238, 207, 173);
  }

  #book-appointment-container {
    height: 80vh;
    width: 80vw;
    border-radius: 5vh;
    background-color: whitesmoke;
  }

  #book-appointment-title {
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-size: 4vh;
    font-weight: 600;
    color: rgb(59, 58, 58);
    padding-bottom: 3vh;
    animation: changeOpacity 0.3s;
  }

  .form-container {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    animation: changeOpacity 0.3s;
    transition: 0.3s;
  }

  .form-label {
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-size: 3vh;
    font-weight: 500;
    color: rgb(59, 58, 58);
    transition: 0.3s;
  }

  #appointment-type {
    all: unset;
    background-color: rgb(212, 211, 211);
    width: 10vw;
    margin-left: 1vh;
    padding: 1vh;
    border-radius: 2vh;
    font-size: 3vh;
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-weight: 500;
    color: rgb(59, 58, 58);
    transition: 0.3s;
  }

  #form-service {
    all: unset;
    background-color: rgb(212, 211, 211);
    width: 30vw;
    margin-top: 1vh;
    margin-bottom: 1vh;
    padding: 1vh;
    border-radius: 2vh;
    font-size: 3vh;
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-weight: 500;
    color: rgb(59, 58, 58);
    transition: 0.3s;
  }

  #form-note {
    all: unset;
    background-color: rgb(212, 211, 211);
    width: 30vw;
    height: 15vh;
    padding: 1vh;
    border-radius: 2vh;
    font-size: 3vh;
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-weight: 500;
    color: rgb(59, 58, 58);
    transition: 0.3s;
  }

  .form-button {
    all: unset;
    height: 5vh;
    width: 12vw;
    border-radius: 2vh;
    padding: 1vh;
    background-color: rgb(235, 164, 89);
    margin: 3vh 1vh 1vh;
    font-size: 3vh;
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-weight: 600;
    text-align: center;
    color: rgb(75, 75, 75);
    border: 0.5vh solid rgb(235, 164, 89);
  }
  .form-button:hover {
    background-color: rgb(175, 122, 65);
    color: whitesmoke;
    border-color: rgb(75, 75, 75);
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