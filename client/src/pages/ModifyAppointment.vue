<template>
  <div id="modify-appointment">
    <div id="modify-appointment-container">
      <form
          class="modify-appointment-form"
          id="modify-appointment-form"
          onsubmit="return false"
          @change="change()"
          v-on:submit.prevent="submitEdit()"
      >
        <h1 id="modify-appointment-title">Modify Appointment</h1>
        <div id="service-container">
          <label class="modify-appointment-label">Select a service:</label>
          <select id="modify-appointment-type"
                  v-model="apptTypeEdit"
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

        <input
            class="modify-appointment-input"
            v-if="this.apptType === 'other'"
            type="text"
            placeholder="service"
        >

        <input
            v-if="this.pastAppointment"
            class="modify-appointment-input"
            id="modify-appointment-edit-rating"
            v-model="ratingEdit"
            type="number"
            min="0"
            max="10"
            placeholder="rating [0-10]"
        />

        <input
            v-if="this.pastAppointment"
            class="modify-appointment-input"
            id="modify-appointment-edit-feedback"
            v-model="feedbackEdit"
            type="text"
            placeholder="feedback"
        />

        <input
            v-if="!this.pastAppointment"
            class="modify-appointment-input"
            id="modify-appointment-edit-time"
            v-model="timeEdit"
            type="text"
            placeholder="time"
        />

        <textarea id="modify-appointment-note" placeholder="Write notes to the mechanic..."></textarea>

        <div id="modify-appointment-button-container">
          <input
              class="modify-appointment-button"
              type="button"
              value="Back"
              v-on:click="backViewDash()"
          />
          <div class="modify-appointment-spacer"></div>
          <input type="submit" class="modify-appointment-button" value="Submit" />
        </div>
      </form>
    </div>
  </div>
</template>

<script>
//import axios from "axios";
//import proxy from "../constants.js";

export default {
  name: "ModifyAppointment",
  props: {
    msg: String,
  },
  data() {
    return {};
  },
  methods: {
    backViewDash: function () {
      let t = this;
      setTimeout(function () {
        t.$router.push("/dashboard");
      }, 300);
    },
  }
}

</script>

<style scoped>

#modify-appointment {
  height: 100vh;
  width: 100vw;
  background-image: radial-gradient(whitesmoke 20%, transparent 20%),
  radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
  background-position: 0 0, 7.5vh 7.5vh;
  background-size: 15vh 15vh;
  background-color: rgb(238, 207, 173);
}

#modify-appointment,
  .modify-appointment-form,
  #modify-appointment-button-container{
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}

#modify-appointment-container {
  height: 80vh;
  width: 80vw;
  border-radius: 5vh;
  background-color: whitesmoke;
  transition: 0.3s;
}

.modify-appointment-input {
  all: unset;
  background-color: rgb(212, 211, 211);
  font-size: 3vh;
  width: 30vw;
  margin-top: 1vh;
  margin-bottom: 1vh;
  padding: 2vh;
  border-radius: 2vh;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  font-weight: 500;
  color: rgb(59, 58, 58);
  transition: 0.3s;
}

.modify-appointment-input:focus,
  #modify-appointment-note:focus {
  background-color: rgb(175, 175, 175);
}

#modify-appointment-button-container {
  flex-direction: row;
}

.modify-appointment-label {
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  font-size: 3vh;
  font-weight: 500;
  color: rgb(59, 58, 58);
  transition: 0.3s;
}

#modify-appointment-type {
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

#modify-appointment-note {
  all: unset;
  background-color: rgb(212, 211, 211);
  width: 30vw;
  height: 15vh;
  padding: 2vh;
  border-radius: 2vh;
  font-size: 3vh;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  font-weight: 500;
  color: rgb(59, 58, 58);
  transition: 0.3s;
  margin-top: 1vh;
  margin-bottom: 1vh;
}

.modify-appointment-button {
  all: unset;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 5vh;
  width: 12vw;
  border-radius: 2vh;
  padding: 1vh;
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

.modify-appointment-button:hover {
  background-color: rgb(175, 122, 65);
  color: whitesmoke;
  border-color: rgb(75, 75, 75);
}

.modify-appointment-spacer {
  width: 3vw;
}

#modify-appointment-title {
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  font-size: 3vh;
  font-weight: 600;
  color: rgb(59, 58, 58);
  padding-bottom: 3vh;
  animation: changeOpacity 0.3s;
  transition: 0.3s;
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