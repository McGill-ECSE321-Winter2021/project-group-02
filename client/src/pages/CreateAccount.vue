<template>
  <div class="create-account">
    <div id="create-account-container">
      <div id="create-account-signup">
        <form
          class="create-account-form"
          id="create-account-signup-form"
          onsubmit="return false"
          @change="change()"
          v-on:submit.prevent="submitSignup()"
        >
          <select class="create-account-input" v-model="userType">
            <option value="" selected disabled>Choose Account Type</option>
            <option value="customer">Customer</option>
            <option
              v-if="this.$store.state.userType === 'assistant'"
              value="technician"
              >Technician</option
            >
            <option
              v-if="this.$store.state.userType === 'assistant'"
              value="assistant"
              >Assistant</option
            >
          </select>
          <input
            class="create-account-input"
            id="create-account-signup-name"
            v-model="nameSignup"
            type="text"
            placeholder="name"
          />
          <input
            class="create-account-input"
            id="create-account-signup-email"
            v-model="emailSignup"
            type="email"
            placeholder="email"
          />
          <input
            class="create-account-input"
            id="create-account-signup-phone"
            v-model="phoneSignup"
            type="tel"
            placeholder="phone number (format: 000-000-0000)"
          />
          <input
            class="create-account-input"
            id="create-account-signup-password"
            v-model="passwordSignup"
            type="password"
            placeholder="password"
          />
          <input
            class="create-account-input"
            id="create-account-signup-repeat-password"
            v-model="repeatPasswordSignup"
            type="password"
            placeholder="repeat password"
          />
          <p id="create-account-signup-error">Wrong username or password</p>
          <div class="create-account-button-container">
            <input
              class="create-account-button"
              type="button"
              value="Back"
              v-on:click="back()"
            />
            <div class="create-account-spacer"></div>
            <input type="submit" class="create-account-button" value="Submit" />
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
  name: "CreateAccount",
  props: {
    msg: String,
  },
  data() {
    return {
      userType: "",
      nameSignup: "",
      emailSignup: "",
      phoneSignup: "",
      passwordSignup: "",
      repeatPasswordSignup: "",
    };
  },
  methods: {
    back: function() {
      document.getElementById("create-account-signup").style.opacity = 0;
      let vm = this;
      setTimeout(function() {
        vm.$router.push("/dashboard");
      }, 300);
    },
    change: function() {
      document.getElementById("create-account-signup-error").style.opacity = 0;
    },
    submitSignup: async function() {
      try {
        let sentData = {
          name: this.nameSignup,
          password: this.passwordSignup,
          email: this.emailSignup,
          phone: this.phoneSignup,
        };

        if (
          sentData.name == "" ||
          sentData.password == "" ||
          sentData.email == "" ||
          sentData.phone == "" ||
          this.userType == ""
        ) {
          document.getElementById("create-account-signup-error").innerHTML =
            "Missing Value(s)";
          document.getElementById(
            "create-account-signup-error"
          ).style.opacity = 1;
          return;
        }

        if (sentData.password !== this.repeatPasswordSignup) {
          document.getElementById("create-account-signup-error").innerHTML =
            "Passwords do not match";
          document.getElementById(
            "create-account-signup-error"
          ).style.opacity = 1;
          return;
        }

        let postTest = await axios.post(
          proxy.proxy + `/api/${this.userType}/create`,
          sentData
        );

        let statusCode = postTest.status;
        if (statusCode === 200) {
          document.getElementById("create-account-signup-form").reset();
          document.getElementById("create-account-signup-error").innerHTML =
            "Account created successfully";
          document.getElementById(
            "create-account-signup-error"
          ).style.opacity = 1;
        } else {
          document.getElementById("create-account-signup-error").innerHTML =
            "An error occured. Please try again later";
          document.getElementById(
            "create-account-signup-error"
          ).style.opacity = 1;
        }
      } catch (error) {
        document.getElementById("create-account-signup-error").innerHTML =
          "An error occured. Please try again later";
        document.getElementById(
          "create-account-signup-error"
        ).style.opacity = 1;
        console.log(`${error}`);
      }
    },
  },
  async mounted() {
    if (this.$store.state.user === undefined || this.$store.state.user === -1) {
      if (
        document.cookie !== undefined &&
        document.cookie !== -1 &&
        document.cookie !== ""
      ) {
        try {
          let response = await axios.get(
            proxy.proxy + `/api/login/type/${document.cookie}`
          );
          if (response.data == null || response.status !== 200)
            this.$router.push("/");
          this.setUser({
            user: parseInt(document.cookie),
            userType: response.data,
          });
        } catch (error) {
          console.log(`${error}`);
          this.$router.push("/");
        }
      } else this.$router.push("/");
    }
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.create-account {
  height: 100vh;
  width: 100vw;
  background-image: radial-gradient(whitesmoke 20%, transparent 20%),
    radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
  background-position: 0 0, 7.5vh 7.5vh;
  background-size: 15vh 15vh;
  background-color: rgb(238, 207, 173);
}

#create-account-container {
  position: absolute;
  height: 80vh;
  width: 80vw;
  border-radius: 5vh;
  background-color: whitesmoke;
}

.create-account,
#create-account-container,
#create-account-signup,
.create-account-form,
.create-account-button-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  transition: 0.3s;
}

#create-account-signup {
  position: absolute;
  height: 100%;
  width: 100%;
  border-radius: 5vh;
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

.create-account-button {
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

.create-account-button:hover {
  background-color: rgb(136, 93, 48);
  color: whitesmoke;
  border-color: rgb(75, 75, 75);
}

.create-account-input {
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

::placeholder,
:-ms-input-placeholder,
::-ms-input-placeholder {
  /* Chrome, Firefox, Opera, Safari 10.1+ */
  color: rgb(59, 58, 58);
  opacity: 1; /* Firefox */
}

.create-account-input:focus {
  background-color: rgb(175, 175, 175);
}

.create-account-button-container {
  flex-direction: row;
}

.create-account-spacer {
  width: 3vw;
}

#create-account-login-error,
#create-account-signup-error {
  opacity: 0;
  font-size: 3vh;
  font-weight: 600;
  color: rgb(59, 58, 58);
  transition: 0.3s;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
}

.create-account-title {
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  font-size: 3vh;
  font-weight: 600;
  color: rgb(59, 58, 58);
  padding-bottom: 3vh;
}
</style>
