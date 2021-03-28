<template>
  <div class="myaccount">
    <div id="myaccount-container">
      <H1 id="myaccount-generic-title" class="myaccount-title"
        >Edit Account Settings</H1
      >
      <div id="myaccount-edit">
        <form
          class="myaccount-form"
          id="myaccount-edit-form"
          onsubmit="return false"
          @change="change()"
          v-on:submit.prevent="submitEdit()"
        >
          <input
            v-if="this.$store.state.userType === 'assistant'"
            class="myaccount-input"
            id="myaccount-edit-id"
            v-model="idEdit"
            type="number"
            min="0"
            placeholder="User ID to edit"
          />
          <input
            class="myaccount-input"
            id="myaccount-edit-name"
            v-model="nameEdit"
            type="text"
            value="{this.name}"
          />
          <input
            class="myaccount-input"
            id="myaccount-edit-email"
            v-model="emailEdit"
            type="email"
            value="{this.email}"
          />
          <input
            class="myaccount-input"
            id="myaccount-edit-phone"
            v-model="phoneEdit"
            type="tel"
            value="{this.phone}"
          />
          <input
            class="myaccount-input"
            id="myaccount-edit-password"
            v-model="passwordEdit"
            type="password"
            placeholder="password"
          />
          <input
            class="myaccount-input"
            id="myaccount-edit-repeat-password"
            v-model="repeatPasswordEdit"
            type="password"
            placeholder="repeat password"
          />
          <p id="myaccount-edit-error">Invalid information</p>
          <div class="myaccount-button-container">
            <input
              class="myaccount-button"
              type="button"
              value="Back"
              v-on:click="backViewDash()"
            />
            <div
              class="myaccount-spacer"
              v-if="this.$store.state.userType === 'assistant'"
            ></div>
            <input
              class="myaccount-button"
              type="button"
              value="Fetch User Data"
              v-on:click="fetchUserData()"
              v-if="this.$store.state.userType === 'assistant'"
            />
            <div class="myaccount-spacer"></div>
            <input type="submit" class="myaccount-button" value="Submit" />
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import proxy from "../constants.js";

export default {
  name: "MyAccount",
  props: {
    msg: String,
  },
  data() {
    return {
      userID: "",
      userType: "",
      nameEdit: "",
      emailEdit: "",
      phoneEdit: "",
      passwordEdit: "",
      repeatPasswordEdit: "",
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
    change: function() {
      document.getElementById("myaccount-edit-error").style.opacity = 0;
    },
    submitEdit: async function() {
      try {
        let sentData = {
          scrsUserId: this.userID,
          name: this.nameEdit,
          password: this.passwordEdit,
          email: this.emailEdit,
          phone: this.phoneEdit,
        };

        if (
          sentData.name == "" ||
          sentData.password == "" ||
          sentData.email == "" ||
          sentData.phone == ""
        ) {
          document.getElementById("myaccount-edit-error").innerHTML =
            "Missing Value(s)";
          document.getElementById("myaccount-edit-error").style.opacity = 1;
          return;
        }

        if (sentData.password !== this.repeatPasswordEdit) {
          document.getElementById("myaccount-edit-error").innerHTML =
            "Passwords do not match";
          document.getElementById("myaccount-edit-error").style.opacity = 1;
          return;
        }

        let postResult = await axios.post(
          proxy.proxy + `/api/${this.userType}/update`,
          sentData
        );

        if (postResult.status === 200) {
          //success message
          document.getElementById("myaccount-edit-error").innerHTML =
            "Successfully updated account info";
          document.getElementById("myaccount-edit-error").style.opacity = 1;
          setTimeout(function() {
            document.getElementById("myaccount-edit-form").reset();
          }, 300);
          setTimeout(function() {
            document.getElementById("myaccount-edit-error").style.opacity = 0;
          }, 3000);
        } else {
          document.getElementById("myaccount-edit-error").innerHTML =
            "An error occured. Please try again later";
          document.getElementById("myaccount-edit-error").style.opacity = 1;
        }
      } catch (error) {
        document.getElementById("myaccount-edit-error").innerHTML =
          "An error occured. Please try again later";
        document.getElementById("myaccount-edit-error").style.opacity = 1;
        console.log(`${error}`);
      }
    },
  },
  mounted() {
    let user = this.$store.state.user;
    //if (user === -1) this.$router.push("/"); TODO enable once login is working.
    console.log(user);
    /*
			try {
	       		let response = await axios.get(proxy.proxy + `/api/`);
	       	if (response.data == null || response.status !== 200) return;
	       		this.setUser({
	         	user: parseInt(document.cookie),
	         	userType: response.data,
	       		});
	       this.$router.push("/dashboard");
	     } catch (error) {
	       console.log(`${error}`);
	     }
			*/
  },
};
</script>

<style scoped>
.myaccount {
  height: 100vh;
  width: 100vw;
  background-image: radial-gradient(whitesmoke 20%, transparent 20%),
    radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
  background-position: 0 0, 7.5vh 7.5vh;
  background-size: 15vh 15vh;
  background-color: rgb(238, 207, 173);
}

#myaccount-container {
  height: 80vh;
  width: 80vw;
  border-radius: 5vh;
  background-color: whitesmoke;
}

.myaccount,
#myaccount-container,
.myaccount-form,
.myaccount-button-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  transition: 0.3s;
}

.myaccount-button {
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

.myaccount-button:hover {
  background-color: rgb(175, 122, 65);
  color: whitesmoke;
  border-color: rgb(75, 75, 75);
}

.myaccount-input {
  all: unset;
  background-color: rgb(212, 211, 211);
  font-size: 3vh;
  width: 30vw;
  margin-top: 1vh;
  margin-bottom: 1vh;
  padding: 2vh;
  border-radius: 2vh;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  font-weight: 600;
  color: rgb(59, 58, 58);
  transition: 0.3s;
}

.myaccount-input:focus {
  background-color: rgb(175, 175, 175);
}

.myaccount-button-container {
  flex-direction: row;
}

.myaccount-spacer {
  width: 3vw;
}

#myaccount-edit-error {
  opacity: 0;
  font-size: 3vh;
  font-weight: 600;
  color: rgb(59, 58, 58);
  transition: 0.3s;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
}

#myaccount-edit {
  animation: changeOpacity 0.3s;
  transition: 0.3s;
}

.myaccount-title {
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
