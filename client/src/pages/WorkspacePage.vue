<template>
  <div id="workspace-page">
    <div id="workspace-container" class="v-container">

      <h1 id="workspace-title" class="form-text">Workspace / Timeslot</h1>

      <div id="workspace-option" class="button-container">
        <select
            id="workspace-select"
            class="form-text"
            v-model="curWorkspace"
            @change="workspaceSelect()"
        >
          <option value="Select a Workspace" disabled selected>Select a Workspace</option>
          <option
              v-for="(workspace, index) in workspaces"
              :key="index"
              :value="workspace"
          > {{
              workspace.workspaceName
          }} </option>
        </select>

        <button id="new-workspace" class="text-button" @click="newWorkspace()">New Workspace</button>
      </div>
      <label id="form-error" class="form-text" v-if="errorMsg !== ''">{{
          this.errorMsg
      }}</label>

      <div id="new-container" class="v-container" v-if="this.new === true">
        <form class="v-container" @submit.prevent="createWorkspace()">

          <input class="form-input"
                 type="text"
                 placeholder="name"
                 v-model="newWorkspaceName"
          >

          <div class="button-container">
            <button class="text-button" @click="cancelWorkspace()">Cancel</button>
            <div class="button-spacer"/>
            <input type="submit" class="text-button" value="Create">
          </div>
        </form>
      </div>

      <div id="edit-container" v-if="this.edit === true">
        <div id="form-container" class="form">
          <form class="form" @submit.prevent="updateButton()">
            <div class="button-container">
              <label class="form-text">Workspace:</label>
              <button class="text-button" @click="deleteWorkspace()">Delete</button>
              <div class="button-spacer"/>
              <input type="submit" class="text-button" value="Update">
            </div>
            <input id="edit-name-input"
                   class="form-input"
                   type="text"
                   placeholder="new name"
                   v-model="newWorkspaceName"
            >
          </form>

          <form class="form" @submit.prevent="createTimeslot()">
            <div class="button-container">
              <label class="form-text">New Timeslot:</label>
              <input type="submit" class="text-button" value="Add">
            </div>

            <div class="button-container">
              <label class="timeslot-text">Date:</label>
              <select id="year-select" class="timeslot-select" v-model="timeslotForm.year" @change="setDate()">
                <option value=-1 selected disabled>year</option>
                <option
                    v-for="index in 10"
                    :key="index"
                    :value="index + curYear - 1"
                >{{
                    index + curYear - 1
                }}</option>
              </select>
              <select id="month-select" class="timeslot-select" v-model="timeslotForm.month" @change="setDate()">
                <option value=-1 selected disabled>mm</option>
                <option
                    v-for="index in 12"
                    :key="index"
                    :value="index"
                >{{
                    index
                }}</option>
              </select>
              <select id="date-select" class="timeslot-select" v-model="timeslotForm.day" @change="setDate()">
                <option value=-1 selected disabled>dd</option>
                <option
                    v-for="index in 31"
                    :key="index"
                    :value="index"
                >{{
                    index
                  }}</option>
              </select>
            </div>

            <div class="button-container">
              <label class="timeslot-text">Start Time:</label>
              <select id="start-hour-select" class="timeslot-select" v-model="timeslotForm.startHour" @change="setStartTime()">
                <option value=-1 selected disabled>hh</option>
                <option
                    v-for="index in 24"
                    :key="index"
                    :value="index"
                >{{
                    index
                  }}</option>
              </select>
              <label class="form-text">:</label>
              <select id="start-minute-select" class="timeslot-select" v-model="timeslotForm.startMinute" @change="setStartTime()">
                <option value=-1 selected disabled>mm</option>
                <option value=0>00</option>
                <option
                    v-for="index in 3"
                    :key="index"
                    :value="index * 15"
                >{{
                    index * 15
                  }}</option>
              </select>
            </div>

            <div class="button-container" v-if="timeslotForm.startHour !== -1 && timeslotForm.startMinute !== -1">
              <label class="timeslot-text">End Time:</label>
              <select id="end-hour-select" class="timeslot-select" v-model="timeslotForm.endHour" @change="setEndTime()">
                <option value=-1 selected disabled>hh</option>
                <option
                    v-for="index in 24"
                    :key="index"
                    :value="index"
                >{{
                    index
                  }}</option>
              </select>
              <label class="form-text">:</label>
              <select id="end-minute-select" class="timeslot-select" v-model="timeslotForm.endMinute" @change="setEndTime()">
                <option value=-1 selected disabled>mm</option>
                <option value=0>00</option>
                <option
                    v-for="index in 3"
                    :key="index"
                    :value="(index) * 15"
                >{{
                    index * 15
                  }}</option>
              </select>
            </div>
          </form>
        </div>

        <div id="timeslot-container" class="form">

          <div class="button-container">
            <label class="form-text">Timeslots:</label>
            <button class="text-button" @click="deleteTimeslot()" v-if="selectTimeslotId !== ''">Delete</button>
          </div>
          <div id="timeslot-select">
            <div
                class="timeslot"
                v-for="(timeslot, index) in timeslots"
                :key="index"
                :id="timeslot.timeslotId"
                @click="timeslotSelect(timeslot)"
            >
              <label class="form-text">Date: {{ timeslot.startDate }}</label>
              <label class="form-text"
              >Time: {{ timeslot.startTime.slice(0, 5) }} to
                {{ timeslot.endTime.slice(0, 5) }}</label
              >
            </div>
          </div>

        </div>
      </div>

      <button id="back" class="form-button" @click="backViewDash()">Back</button>
    </div>
  </div>
</template>

<script>
 import axios from "axios";
 import proxy from "../constants.js";

export default {
  name: "WorkspacePage",
  props: {
    msg: String,
  },

  data() {
    return {
      curWorkspace: "Select a Workspace",
      workspaces: [],
      edit: false,
      new: false,
      newWorkspaceName: "",
      timeslots: [],
      selectTimeslotId: "",
      timeslotForm: {
        year: -1,
        month: -1,
        day: -1,
        startHour: -1,
        startMinute: -1,
        endHour: -1,
        endMinute: -1,
      },
      newStartDate: new Date(),
      newEndDate: new Date(),
      curYear: new Date().getFullYear(),
      errorMsg: ""
    };
  },

  methods: {
    backViewDash() {
      let t = this;
      t.cancelButton();
      document.getElementById("workspace-title").style.opacity = 0;
      document.getElementById("workspace-option").style.opacity = 0;
      document.getElementById("back").style.opacity = 0;
      setTimeout(function() {
        t.$router.push("/dashboard");
      }, 300);
    },

    newWorkspace() {
      if (this.new === true) return;
      this.new = true;
      this.edit = false;
      this.curWorkspace = 'Select a Workspace';
      this.newWorkspaceName = "";
      this.errorMsg = "";
    },

    cancelWorkspace() {
      this.new=false;
      this.edit=false;
      this.curWorkspace='Select a Workspace';
      this.newWorkspaceName="";
      this.errorMsg = "";
    },

    async createWorkspace() {
      if (this.newWorkspaceName === "" || this.newWorkspaceName === undefined) {
        this.errorMsg = "The workspace name is not filled"
        return;
      }
      try {
        let createWorkspaceData = `name=${this.newWorkspaceName}`;

        let createWorkspaceResponse = await axios.post(
            proxy.proxy + "/api/workspace/create",
            createWorkspaceData
        );

        if (createWorkspaceResponse.status === 200) {
          this.workspaces.push(createWorkspaceResponse.data);
          this.cancelWorkspace();
        }
      } catch (error) {
        console.error(error);
        this.errorMsg = "Something went wrong creating the workspace. Please try again later";
      }
    },

    async workspaceSelect() {
      if (this.curWorkspace === "Select a Workspace") {
        this.edit = false;
        return;
      }
      this.edit = true;
      this.new = false;
      this.newWorkspaceName = this.curWorkspace.workspaceName;
      this.errorMsg = "";
      this.timeslotForm.year = this.timeslotForm.month = this.timeslotForm.day = -1;
      this.timeslotForm.startHour = this.timeslotForm.startMinute = -1;
      this.timeslotForm.endHour = this.timeslotForm.endMinute = -1;

      try {
        let response = await axios.get(proxy.proxy + "/api/workspace/availabilities/" + this.curWorkspace.workspaceId);
        this.timeslots = response.data;
      } catch (error) {
        console.error(error);
        this.cancelWorkspace();
        this.errorMsg = "Something went wrong loading workspace. Please try again later"
      }
    },

    async updateButton() {
      if (this.newWorkspaceName === "" || this.newWorkspaceName === undefined) {
        this.errorMsg = "The workspace name is empty"
        this.newWorkspaceName = this.curWorkspace.workspaceName;
        return;
      }
      try {
        let updatedWorkspace = {
          workspaceId: this.curWorkspace.workspaceId,
          spaceName: this.newWorkspaceName
        }

        let updatedWorkspaceResponse = await  axios.put(
            proxy.proxy + "/api/workspace/update",
            updatedWorkspace
        );

        if (updatedWorkspaceResponse.status === 200) {
          let response = await axios.get(proxy.proxy + "/api/workspace/getAll");
          this.workspaces = response.data;
        }
      } catch (error) {
        console.error(error)
        this.errorMsg = "Something went wrong updating the workspace name. Please try again later";
      }
    },

    async deleteWorkspace() {
      if (this.timeslots.length !== 0) {
        this.errorMsg = "Please delete all timeslots before deleting workspace"
      }
      try {
        let workspaceId = this.curWorkspace.workspaceId;

        let deleteWorkspaceResponse = await axios.delete(
            proxy.proxy + `/api/workspace/delete/${workspaceId}`
        );

        if (deleteWorkspaceResponse.status === 200) {
          this.cancelWorkspace();
        }
      } catch (error) {
        console.error(error);
        this.errorMsg = "Failed to delete workspace. Please try again later"
      }
    },

    setDate() {
      if (this.timeslotForm.year === -1 || this.timeslotForm.month === -1 || this.timeslotForm.day === -1) return;

      this.newStartDate.setFullYear(this.timeslotForm.year, this.timeslotForm.month - 1, this.timeslotForm.day);
      this.newEndDate.setFullYear(this.timeslotForm.year, this.timeslotForm.month - 1, this.timeslotForm.day);
      this.timeslotForm.month = this.newStartDate.getMonth() + 1;
      this.timeslotForm.day = this.newStartDate.getDate();
    },

    setStartTime() {
      if(this.timeslotForm.startHour === -1 || this.timeslotForm.startMinute === -1) return;
      this.newStartDate.setHours(this.timeslotForm.startHour, this.timeslotForm.startMinute);
    },

    setEndTime() {
      if(this.timeslotForm.endHour === -1 || this.timeslotForm.endMinute === -1) return;

      if(this.timeslotForm.startHour > this.timeslotForm.endHour) {
        this.timeslotForm.endHour = -1;
        this.timeslotForm.endMinute = -1
        return;
      } else if (this.timeslotForm.startHour === this.timeslotForm.endHour && this.timeslotForm.startMinute >= this.timeslotForm.endMinute) {
        this.timeslotForm.endMinute = -1;
        return;
      }

      this.newEndDate.setHours(this.timeslotForm.endHour, this.timeslotForm.endMinute);
    },

    async createTimeslot() {
      if (this.timeslotForm.year === -1 || this.timeslotForm.month === -1 || this.timeslotForm.day === -1) {
        this.errorMsg = "A date for the timeslot is not selected"
        return
      }
      if(this.timeslotForm.startHour === -1 || this.timeslotForm.startMinute === -1) {
        this.errorMsg = "A starting time for the timeslot is not selected"
        return;
      }
      if(this.timeslotForm.endHour === -1 || this.timeslotForm.endMinute === -1) {
        this.errorMsg = "An end time for the timeslot is not selected"
        return;
      }
      try {
        let createTimeslotData = {
          startDate: this.newStartDate,
          endDate: this.newEndDate,
          startTime: this.newStartDate.getTime(),
          endTime: this.newEndDate.getTime(),
          workspaceId: this.curWorkspace.workspaceId
        };

        let createTimeslotResponse = await axios.post(
            proxy.proxy + "/api/workspace/create",
            createTimeslotData
        );

        if (createTimeslotResponse.status === 200) {
          this.timeslotForm.year = this.timeslotForm.month = this.timeslotForm.day = -1;
          this.timeslotForm.startHour = this.timeslotForm.startMinute = -1;
          this.timeslotForm.endHour = this.timeslotForm.endMinute = -1;
          this.timeslots.push(createTimeslotResponse.data);
        }
      } catch (error) {
        console.error(error);
        this.errorMsg = "Something went wrong adding the timeslot to workspace. Please try again later";
      }
    },

    timeslotSelect(timeslot) {
      if (this.selectTimeslotId !== "") {
        let previousTimeslot = document.getElementById(this.selectTimeslotId);
        // passing an empty string will revert the css to its default value
        previousTimeslot.style.backgroundColor = "";
        previousTimeslot.style.color = "";
        previousTimeslot.style.borderColor = "";
      }
      if (this.selectTimeslotId === timeslot.timeslotId) {
        this.selectTimeslotId = "";
        return;
      }
      this.selectTimeslotId = timeslot.timeslotId;
      let timeslotComponent = document.getElementById(this.selectTimeslotId);
      timeslotComponent.style.backgroundColor = "rgb(175, 122, 65)"
      timeslotComponent.style.color = "whitesmoke";
      timeslotComponent.style.borderColor = "rgb(75, 75, 75)";
    },

    async deleteTimeslot() {
      try {
        let deleteWorkspaceResponse = await axios.delete(
            proxy.proxy + `/api/timeslot/delete/${this.selectTimeslotId}`
        );

        if (deleteWorkspaceResponse.status === 200) {
          let response = await axios.get(proxy.proxy + "/api/workspace/availabilities/" + this.curWorkspace.workspaceId);
          this.timeslots = response.data;
        }
      } catch (error) {
        console.error(error);
        this.errorMsg = "Failed to delete timeslot. Please try again later"
      }
    }
  },

  async mounted() {
    if (this.$store.state.userType !== "assistant") this.$router.push("/dashboard");

    try {
      let response = await axios.get(proxy.proxy + "/api/workspace/getAll");
      this.workspaces = response.data;
    } catch (error) {
      console.error(error);
    }

    this.newStartDate.setSeconds(0,0);
    this.newEndDate.setSeconds(0,0);
  }
}
</script>

<style scoped>
  .v-container {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    transition: 0.3s;
  }

  .form {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    animation: changeOpacity 0.3s;
    transition: 0.3s;
  }

  /* display text formatting */
  .form-text,
  .form-input,
  .form-button,
  .text-button,
  .timeslot-select,
  .timeslot-text {
    all: unset;
    margin-right: 1vw;
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-size: 3vh;
    font-weight: 500;
    color: rgb(59, 58, 58);
    animation: changeOpacity, 0.3s;
    transition: 0.3s;
  }

  #workspace-page {
    height: 100vh;
    width: 100vw;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    background-image: radial-gradient(whitesmoke 20%, transparent 20%),
    radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
    background-position: 0 0, 7.5vh 7.5vh;
    background-size: 15vh 15vh;
    background-color: rgb(238, 207, 173);
  }

  #workspace-container {
    height: 80vh;
    width: 80vw;
    border-radius: 5vh;
    background-color: whitesmoke;
    transition: 0.3s;
  }

  #edit-container,
  .button-container {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: row;
    transition: 0.3s;
  }

  #timeslot-select {
    background-color: rgb(225, 225, 225);
    width: 30vw;
    height: 35vh;
    overflow-y: scroll;
    border-radius: 2vh;
    margin-top: 1vh;
  }

  #workspace-title {
    margin-right: 0vw;
    margin-bottom: 1vh;
    font-weight: 600;
  }

  #workspace-option {
    margin-bottom: 0.5vh;
  }

  #form-error {
    color: rgb(255, 0, 0);
  }

  #workspace-select {
    background-color: rgb(212, 211, 211);
    padding: 0.5vh 4vw;
    border-radius: 2vh;
    font-weight: 600;
    font-size: 2.5vh;
  }

  .text-button {
    height: 3vh;
    margin-right: 0vw;
    border-radius: 2vh;
    background-color: rgb(235, 164, 89);
    border: 0.5vh solid rgb(235, 164, 89);
    font-weight: 600;
    font-size: 2.5vh;
    text-align: center;
    padding: 0 0.5vw;
  }

  .form-input {
    background-color: rgb(212, 211, 211);
    width: 30vw;
    margin-top: 1vh;
    margin-bottom: 1vh;
    padding: 1vh;
    border-radius: 2vh;
  }

  .timeslot-text {
    font-size: 2.5vh;
    margin-left: 5vw;
  }

  .timeslot-select {
    height: 3vh;
    font-size: 2.5vh;
    text-align: center;
    background-color: rgb(212, 211, 211);
    margin-top: 1vh;
    border-radius: 2vh;
    padding: 0.5vh 0.5vw;
  }

  .form-button {
    height: 3vh;
    width: 10vw;
    border-radius: 2vh;
    padding: 1vh;
    background-color: rgb(235, 164, 89);
    border: 0.5vh solid rgb(235, 164, 89);
    margin: 1vh;
    font-weight: 600;
    text-align: center;
  }

  .button-spacer {
    width: 1vw;
  }

  /* hover effect format */
  .form-button:hover,
  #new-workspace:hover {
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