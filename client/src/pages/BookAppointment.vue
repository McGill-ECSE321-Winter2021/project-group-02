<template>
  <div id="book-appointment">
    <div id="book-appointment-container">

      <H1 id="book-appointment-title">Create Appointment</H1>

      <form id="appointment-form"
            @submit.prevent="bookAppointment()"
      >
        <div id="form-splitter">
          <div class="form-container">
            <div id="form-appointment-type">
              <label class="form-text">Select a service:</label>
              <select id="appointment-type" class="form-text"
                      v-model="selectAppointmentType"
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

            <input id="form-service" class="form-text"
                   v-if="this.selectAppointmentType === 'other'"
                   type="text"
                   placeholder="service"
                   v-model="inputService"
            >

            <div id="form-customer"
                 v-if="assistantBooking === true"
            >
              <label class="form-text">Customer e-mail:</label>
              <input id="customer-email" class="form-text"
                     type="text"
                     placeholder="customer@mail.com"
                     v-model="inputEmail"
                     @focusout="getCustomer()"
              >
            </div>

            <label class="form-text">Appointment message:</label>
            <textarea id="form-note" class="form-text"
                      placeholder="Write notes to the mechanic..."
                      v-model="inputNote"
            />
          </div>

          <div class="form-container">
            <label class="form-text">Timeslot:</label>
            <div id="timeslot-container">
              <div class="timeslot"
                   v-for="(timeslot, index) in timeslots"
                   :key="index"
                   :id="timeslot.timeslotId"
                   @click="timeslotSelect(timeslot)"
              >
                <label class="form-text">Date: {{timeslot.startDate}}</label>
                <label class="form-text">Time: {{timeslot.startTime.slice(0, 5)}} to {{timeslot.endTime.slice(0, 5)}}</label>
              </div>
            </div>
          </div>

        </div>

        <label id="form-error" class="form-text"
               v-if="errorMsg !== ''"
        >{{this.errorMsg}}</label>

        <div id="form-buttons">
          <button class="form-button" @click="backViewDash()">Cancel</button>
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

    data() { return {
      selectAppointmentType: "",
      inputService: undefined,
      inputNote: undefined,
      assistantBooking: false,
      inputEmail: undefined,
      customerId: this.$store.state.user,
      timeslots: [],
      selectTimeslotId: [],
      errorMsg: undefined
    };},

    methods: {
      backViewDash() {
        let t = this;
        document.getElementById("book-appointment-title").style.opacity = 0;
        document.getElementById("appointment-form").style.opacity = 0;
        setTimeout(function() {
          t.$router.push("/dashboard");
        }, 300);
      },

      async getCustomer() {
        if (this.inputEmail === undefined || this.inputEmail === "") {
          this.customerId = -1;
          this.errorMsg = "Customer e-mail is not filled";
          return;
        }
        try {
          let customerResponse = await axios.get(proxy.proxy + `/api/customer/getByEmail/${this.inputEmail}`)

          if (customerResponse.status === 200) {
            this.customerId = customerResponse.data.customerId;
            this.errorMsg = "";
          }
        } catch (error) {
          console.error(error);
          this.customerId = -1;
          this.errorMsg = "Customer account with this e-mail could not be found";
        }
      },

      timeslotSelect(timeslot) {
        if (this.selectTimeslotId.length !== 0) {
          let previousTimeslot = document.getElementById(this.selectTimeslotId[0]);
          previousTimeslot.style.backgroundColor = "";
          previousTimeslot.style.color = "";
          previousTimeslot.style.borderColor = "";
        }
        this.selectTimeslotId = [timeslot.timeslotId];
        let timeslotComponent = document.getElementById(this.selectTimeslotId[0]);
        timeslotComponent.style.backgroundColor = "rgb(175, 122, 65)"
        timeslotComponent.style.color = "whitesmoke";
        timeslotComponent.style.borderColor = "rgb(75, 75, 75)";
      },

      async bookAppointment() {
        if (this.selectAppointmentType === "") {
          this.errorMsg = "Appointment service is not selected"
          return;
        }
        if (this.selectAppointmentType === "other" && (this.inputService === undefined || this.inputService === "")) {
          this.errorMsg = "Please specify a service"
          return;
        }
        if (this.customerId === -1) {
          this.errorMsg = "Invalid customer for the appointment"
          return;
        }
        if (this.selectTimeslotId.length === 0) {
          this.errorMsg = "Select at least one availability for the appointment"
          return;
        }
        try {
          let bookAppointmentData = {
            appointmentType: this.selectAppointmentType,
            service: this.inputService,
            note: this.inputNote,
            customerId: this.customerId,
            timeslotsId: this.selectTimeslotId,
          };

          let bookingResult = await axios.post(
              proxy.proxy + "/api/appointment/book",
              bookAppointmentData
          );

          if(bookingResult.status === 200) {
            this.backViewDash();
          }
        } catch (error) {
          console.error(`${error}`)
          this.errorMsg = "Something went wrong during the booking process. Please try again later"
        }
      }
    },

    async mounted() {
      /*if (this.$store.state.user !== "assistant" && this.$store.state.user !== "customer") this.$router.push("/dashboard");

      if (this.$store.state.user === "assistant") {
        this.customerId = -1;
        this.assistantBooking = true;
      }

      try {
        let today = new Date();
        today = `${today.getFullYear()}-${today.getMonth() + 1}-${today.getDate()}`

        let response = await axios.get(proxy.proxy + "/api/timeslot/available/" + today);
        this.timeslots = response.data;
        console.log(`${this.timeslots}`)
      } catch (error) {
        console.error(error);
      }
      */

      this.timeslots = [
          {timeslotId: 1, startDate: "2021-04-02", endDate: "2021-04-02", startTime: "14:55:48", endTime: "14:55:48", workspaceId: 1},
          {timeslotId: 2, startDate: "2021-04-03", endDate: "2021-04-03", startTime: "14:55:48", endTime: "14:55:48", workspaceId: 2}
      ];
    }
  }
</script>

<style scoped>
  #book-appointment,
  #book-appointment-container,
  #appointment-form,
  #timeslot-container {
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
    transition: 0.3s;
  }

  #form-splitter {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: row;
    transition: 0.3s;
  }

  .form-container,
  #form-customer {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    animation: changeOpacity 0.3s;
    transition: 0.3s;
  }

  .form-text {
    all: unset;
    margin-right: 1vw;
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-size: 3vh;
    font-weight: 500;
    color: rgb(59, 58, 58);
    animation: changeOpacity, 0.3s;
    transition: 0.3s;
  }
  #form-error {
    color: rgb(255, 0, 0);
  }

  #appointment-type {
    background-color: rgb(212, 211, 211);
    width: 10vw;
    padding: 1vh;
    border-radius: 2vh;
  }

  #timeslot-container {
    background-color: rgb(225, 225, 225);
    width: 30vw;
    height: 40vh;
    overflow-y: scroll;
    border-radius: 2vh
  }

  #form-service,
  #customer-email {
    background-color: rgb(212, 211, 211);
    width: 30vw;
    margin-top: 1vh;
    margin-bottom: 1vh;
    padding: 1vh;
    border-radius: 2vh;
  }

  #form-note {
    background-color: rgb(212, 211, 211);
    width: 30vw;
    height: 15vh;
    padding: 1vh;
    border-radius: 2vh
  }

  .timeslot {
    all: unset;
    width: 27vw;
    margin-bottom: 1vh;
    border-radius: 2vh;
    background-color: rgb(235, 164, 89);
    border: 0.5vh solid rgb(235, 164, 89);
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-size: 3vh;
    font-weight: 600;
    color: rgb(75, 75, 75);
    text-align: center;
    animation: changeOpacity, 0.3s;
    transition: 0.3s;
  }
  .timeslot:hover {
    background-color: rgb(175, 122, 65);
    color: whitesmoke;
    border-color: rgb(75, 75, 75);
  }

  .form-button {
    all: unset;
    height: 5vh;
    width: 12vw;
    margin: 3vh 1vh 1vh;
    padding: 1vh;
    border-radius: 2vh;
    background-color: rgb(235, 164, 89);
    border: 0.5vh solid rgb(235, 164, 89);
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-size: 3vh;
    font-weight: 600;
    color: rgb(75, 75, 75);
    text-align: center;
    animation: changeOpacity, 0.3s;
    transition: 0.3s;
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