<template>
  <div id="workspace-page">
    <div id="workspace-container" class="v-container">
      <h1 id="workspace-title">Workspace</h1>
      <div id="workspace-option" class="h-container">
        <select id="workspace-select"
                v-model="curWorkspace"
                @change="dropdown()"
        >
          <option value="Select a Workspace" disabled selected>Select a Workspace</option>
          <option
              v-for="(workspace, index) in workspaces"
              :key="index"
              :value="workspace"
          >
            {{ workspace.workspaceName }}
          </option>
        </select>
        <input id="new-workspace"
               type="button"
               value="New Workspace"
               @click="newButton()"
        >
      </div>

      <div id="new-container" class="v-container"
           v-if="this.new === true"
      >
        <form class="form"
              @submit.prevent="bookAppointment()"
        >
          <input class="text"
                 type="text"
                 placeholder="name"
                 v-model="newWorkspaceName"
          >
          <div>
            <input type="button" class="button" value="Cancel">
            <input type="submit" class="button" value="Create">
          </div>
        </form>
      </div>

      <div id="edit-container" class="v-container"
           v-if="this.sel === true"
      >
        <form class="form"
              @submit.prevent="bookAppointment()"
        >
          <input class="text"
                 type="text"
                 placeholder="new name"
                 v-model="newWorkspaceName"
          >
          <input type="submit" class="button" value="Update">
        </form>
      </div>

      <input id="return" class="button"
             type="button"
             value="Return"
             @click="backViewDash()"
      >
    </div>
  </div>
</template>

<script>
// import axios from "axios";
// import proxy from "../constants.js";

export default {
  name: "WorkspacePage",
  props: {
    msg: String,
  },
  components: {},
  data() {
    return {
      curWorkspace: "Select a Workspace",
      workspaces: [],
      sel: false,
      new: false,
      newWorkspaceName: ""
    };
  },

  methods: {
    backViewDash: function() {
      let t = this;
      document.getElementById("workspace-title").style.opacity = 0;
      document.getElementById("workspace-option").style.opacity = 0;
      document.getElementById("new-container").style.opacity = 0;
      document.getElementById("edit-container").style.opacity = 0;
      document.getElementById("return").style.opacity = 0;
      setTimeout(function() {
        t.$router.push("/dashboard");
      }, 300);
    },

    dropdown: function() {
      if (this.curWorkspace === "Select a Workspace") {
        this.sel = false;
        return;
      }
      this.sel = true;
      this.new = false;
    },
    newButton: function () {
      if (this.new === true) return;
      this.new=true;
      this.sel=false;
      this.curWorkspace='Select a Workspace';
    }
  },
  async mounted() {
    //TODO REMOVE DUMMY CODE BELOW ONCE BACKEND CONTAINS VALUES
    this.workspaces = [
      { workspaceName: "ws1", workspaceId: 4 },
      { workspaceName: "ws2", workspaceId: 3 },
      { workspaceName: "wsAlix", workspaceId: 6969 },
    ];
  }
}
</script>

<style scoped>
  .v-container,
  .form {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    margin-bottom: 1vh;
  }

  .h-container {
    margin-bottom: 1vh;
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

  #workspace-title {
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-size: 3vh;
    font-weight: 600;
    color: rgb(59, 58, 58);
    padding-bottom: 1vh;
    animation: changeOpacity 0.3s;
    transition: 0.3s;
  }

  #workspace-select {
    all: unset;
    background-color: rgb(212, 211, 211);
    font-size: 2.5vh;
    padding: 0.5vh 4vw;
    border-radius: 2vh;
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-weight: 600;
    color: rgb(59, 58, 58);
    transition: 0.3s;
  }
  #new-workspace{
    all: unset;
    height: 3vh;
    width: 10vw;
    margin-left: 1vw;
    border-radius: 2vh;
    background-color: rgb(235, 164, 89);
    transition: 0.3s;
    font-size: 2.5vh;
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-weight: 600;
    text-align: center;
    color: rgb(75, 75, 75);
    border: 0.5vh solid rgb(235, 164, 89);
  }
  #new-workspace:hover {
    background-color: rgb(175, 122, 65);
    color: whitesmoke;
    border-color: rgb(75, 75, 75);
  }

  .text {
    all: unset;
    background-color: rgb(212, 211, 211);
    width: 30vw;
    margin: 1vh;
    padding: 1vh;
    border-radius: 2vh;
    font-size: 3vh;
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-weight: 500;
    color: rgb(59, 58, 58);
    transition: 0.3s;
  }

  .button {
    all: unset;
    height: 3vh;
    width: 10vw;
    border-radius: 2vh;
    padding: 1vh;
    background-color: rgb(235, 164, 89);
    margin: 1vh 1vh 0;
    font-size: 3vh;
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-weight: 600;
    text-align: center;
    color: rgb(75, 75, 75);
    border: 0.5vh solid rgb(235, 164, 89);
  }
  .button:hover {
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