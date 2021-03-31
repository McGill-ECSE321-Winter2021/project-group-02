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
              @submit.prevent="createButton()"
        >
          <input class="text"
                 type="text"
                 placeholder="name"
                 v-model="newWorkspaceName"
          >
          <div>
            <input type="button" class="button" value="Cancel" @click="cancelButton()">
            <input type="submit" class="button" value="Create">
          </div>
        </form>
      </div>

      <div id="edit-container" class="v-container"
           v-if="this.sel === true"
      >
        <form class="form"
              @submit.prevent="updateButton()"
        >
          <input id="edit-name-input" class="text"
                 type="text"
                 placeholder="new name"
                 v-model="newWorkspaceName"
          >

          <div>
            <input type="button" class="button" value="Delete" @click="deleteButton()">
            <input type="submit" class="button" value="Update">
          </div>
        </form>
      </div>

      <input id="back" class="button"
             type="button"
             value="Back"
             @click="backViewDash()"
      >
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
      sel: false,
      new: false,
      newWorkspaceName: ""
    };
  },

  methods: {
    backViewDash: function() {
      let t = this;
      t.cancelButton();
      document.getElementById("workspace-title").style.opacity = 0;
      document.getElementById("workspace-option").style.opacity = 0;
      document.getElementById("back").style.opacity = 0;
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
      this.newWorkspaceName = this.curWorkspace.workspaceName;
    },

    newButton: function () {
      if (this.new === true) return;
      this.new=true;
      this.sel=false;
      this.curWorkspace='Select a Workspace';
      this.newWorkspaceName="";
    },
    cancelButton: function () {
      this.new=false;
      this.sel=false;
      this.curWorkspace='Select a Workspace';
      this.newWorkspaceName="";
    },
    createButton: async function () {
      try {
        let createWorkspaceData = "name=foo";

        let createWorkspaceResponse = await axios.post(
            proxy.proxy + "/api/workspace/create",
            createWorkspaceData
        );

        if (createWorkspaceResponse.status === 200) {
          this.workspaces.push(createWorkspaceResponse.data);
          this.cancelButton();
        }
      } catch (error) {
        console.error(error);
      }
    },
    updateButton: async function () {
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
          let index = this.workspaces.findIndex(workspace => workspace === this.curWorkspace);
          this.workspaces[index] = updatedWorkspaceResponse.data;
          this.curWorkspace = updatedWorkspaceResponse.data;
        }
      } catch (error) {
        console.error(error)
      }
    },
    deleteButton: async function () {
      try {
        let workspaceId = this.curWorkspace.workspaceId;

        let deleteWorkspaceResponse = await axios.delete(
            proxy.proxy + `/api/workspace/delete/${workspaceId}`
        );

        if (deleteWorkspaceResponse.status === 200) {
          this.cancelButton();
        }
      } catch (error) {
        console.error(error);
      }
    }
  },
  async mounted() {
    //TODO UNCOMMENT ONCE BACKEND WORKS
    /*if (this.$store.state.user !== "assistant") this.$router.push("/");

    try {
      let response = await axios.get(proxy.proxy + "/api/technician/getAll");
      this.technicians = response.data;
      response = await axios.get(proxy.proxy + "/api/workspace/getAll");
      this.workspaces = response.data;
    } catch (error) {
      console.error(error);
    }*/

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
    transition: 0.3s;
  }

  .h-container {
    margin-bottom: 1vh;
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