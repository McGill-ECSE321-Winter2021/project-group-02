<template>
  <div class="mainpage">
    <button
      @click="contactInfo()"
      class="mainpage-button"
      id="mainpage-contact-info"
    >
      Contact info
    </button>
    <div id="mainpage-container">
      <div id="mainpage-select-user">
        <img id="mainpage-image" src="../assets/logo3.png" alt="logo" />
        <p class="mainpage-title">Who are you?</p>
        <button class="mainpage-button" @click="customer()">Customer</button>
        <button class="mainpage-button" @click="technician()">
          Technician
        </button>
        <button class="mainpage-button" @click="assistant()">Assistant</button>
      </div>
      <div id="mainpage-login-signup">
        <p class="mainpage-title">What would you like to do?</p>
        <button class="mainpage-button" @click="login()">Login</button>
        <button class="mainpage-button" @click="signup()">
          Sign Up
        </button>
        <button class="mainpage-button" @click="backUserSelect()">Back</button>
      </div>
      <div id="mainpage-login">
        <form
          class="mainpage-form"
          onsubmit="return false"
          v-on:submit.prevent="submitLogin()"
          @change="change()"
        >
          <input
            class="mainpage-input"
            id="mainpage-login-email"
            v-model="emailLogin"
            type="email"
            placeholder="email"
          />
          <input
            class="mainpage-input"
            id="mainpage-login-password"
            v-model="passwordLogin"
            type="password"
            placeholder="password"
          />
          <p id="mainpage-login-error">Wrong username or password</p>
          <div class="mainpage-button-container">
            <input
              class="mainpage-button"
              type="button"
              value="Back"
              v-on:click="backLoginSignup()"
            />
            <div class="mainpage-spacer"></div>
            <input class="mainpage-button" type="submit" value="Submit" />
          </div>
        </form>
      </div>
      <div id="mainpage-signup">
        <form
          class="mainpage-form"
          id="mainpage-signup-form"
          onsubmit="return false"
          @change="change()"
          v-on:submit.prevent="submitSignup()"
        >
          <input
            class="mainpage-input"
            id="mainpage-signup-name"
            v-model="nameSignup"
            type="text"
            placeholder="name"
          />
          <input
            class="mainpage-input"
            id="mainpage-signup-email"
            v-model="emailSignup"
            type="email"
            placeholder="email"
          />
          <input
            class="mainpage-input"
            id="mainpage-signup-phone"
            v-model="phoneSignup"
            type="tel"
            placeholder="phone number (format: 000-000-0000)"
          />
          <input
            class="mainpage-input"
            id="mainpage-signup-password"
            v-model="passwordSignup"
            type="password"
            placeholder="password"
          />
          <input
            class="mainpage-input"
            id="mainpage-signup-repeat-password"
            v-model="repeatPasswordSignup"
            type="password"
            placeholder="repeat password"
          />
          <p id="mainpage-signup-error">Wrong username or password</p>
          <div class="mainpage-button-container">
            <input
              class="mainpage-button"
              type="button"
              value="Back"
              v-on:click="backLoginSignup()"
            />
            <div class="mainpage-spacer"></div>
            <input type="submit" class="mainpage-button" value="Submit" />
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import proxy from "../constants.js";
import { mapActions } from "vuex";

export default {
  name: "Mainpage",
  props: {
    msg: String,
  },
  data() {
    return {
      userType: "",
      emailLogin: "",
      passwordLogin: "",
      nameSignup: "",
      emailSignup: "",
      phoneSignup: "",
      passwordSignup: "",
      repeatPasswordSignup: "",
    };
  },
  methods: {
    ...mapActions(["setUser"]),
    contactInfo: function() {
      alert("Hey this is none of you're business!!!");
    },
    customer: function() {
      this.userType = "customer";
      document.getElementById("mainpage-select-user").style.opacity = 0;
      setTimeout(function() {
        document.getElementById("mainpage-select-user").style.zIndex = -1;
        document.getElementById("mainpage-login-signup").style.zIndex = 5;
        document.getElementById("mainpage-login-signup").style.opacity = 1;
      }, 300);
    },
    technician: function() {
      this.userType = "technician";
      document.getElementById("mainpage-select-user").style.opacity = 0;
      setTimeout(function() {
        document.getElementById("mainpage-select-user").style.zIndex = -1;
        document.getElementById("mainpage-login").style.zIndex = 5;
        document.getElementById("mainpage-login").style.opacity = 1;
      }, 300);
    },
    assistant: function() {
      this.userType = "assistant";
      document.getElementById("mainpage-select-user").style.opacity = 0;
      setTimeout(function() {
        document.getElementById("mainpage-select-user").style.zIndex = -1;
        document.getElementById("mainpage-login").style.zIndex = 5;
        document.getElementById("mainpage-login").style.opacity = 1;
      }, 300);
    },
    backUserSelect: function() {
      this.userType = "";
      document.getElementById("mainpage-login-signup").style.opacity = 0;
      setTimeout(function() {
        document.getElementById("mainpage-select-user").style.zIndex = 5;
        document.getElementById("mainpage-login-signup").style.zIndex = -1;
        document.getElementById("mainpage-select-user").style.opacity = 1;
      }, 300);
    },
    login: function() {
      document.getElementById("mainpage-login-signup").style.opacity = 0;
      setTimeout(function() {
        document.getElementById("mainpage-login").style.zIndex = 5;
        document.getElementById("mainpage-login-signup").style.zIndex = -1;
        document.getElementById("mainpage-login").style.opacity = 1;
      }, 300);
    },
    signup: function() {
      document.getElementById("mainpage-login-signup").style.opacity = 0;
      document.getElementById("mainpage-container").style.height = "70vh";
      setTimeout(function() {
        document.getElementById("mainpage-signup").style.zIndex = 5;
        document.getElementById("mainpage-login-signup").style.zIndex = -1;
        document.getElementById("mainpage-signup").style.opacity = 1;
      }, 300);
    },
    backLoginSignup: function() {
      if (this.userType == "customer") {
        document.getElementById("mainpage-signup").style.opacity = 0;
        document.getElementById("mainpage-login").style.opacity = 0;
        document.getElementById("mainpage-container").style.height = "50vh";
        document.getElementById("mainpage-login-error").style.opacity = 0;
        setTimeout(function() {
          document.getElementById("mainpage-signup").style.zIndex = -1;
          document.getElementById("mainpage-login").style.zIndex = -1;
          document.getElementById("mainpage-login-signup").style.zIndex = 5;
          document.getElementById("mainpage-login-signup").style.opacity = 1;
        }, 300);
      } else if (
        this.userType == "technician" ||
        this.userType == "assistant"
      ) {
        document.getElementById("mainpage-signup").style.opacity = 0;
        document.getElementById("mainpage-login").style.opacity = 0;
        document.getElementById("mainpage-container").style.height = "50vh";
        document.getElementById("mainpage-login-error").style.opacity = 0;
        setTimeout(function() {
          document.getElementById("mainpage-signup").style.zIndex = -1;
          document.getElementById("mainpage-login").style.zIndex = -1;
          document.getElementById("mainpage-select-user").style.zIndex = 5;
          document.getElementById("mainpage-select-user").style.opacity = 1;
        }, 300);
      }
    },
    change: function() {
      document.getElementById("mainpage-login-error").style.opacity = 0;
      document.getElementById("mainpage-signup-error").style.opacity = 0;
    },
    submitLogin: async function() {
      let email = this.emailLogin;
      let password = this.passwordLogin;

      try {
        let sentData = `email=${email}&password=${password}`;

        let postTest = await axios.post(
          proxy.proxy + `/api/login/${this.userType}`,
          sentData
        );

        let responseDataPost = postTest.data; //do something with this for tests
        let statusCode = postTest.status;
        if (responseDataPost !== -1 && statusCode === 200) {
          this.setUser({ user: responseDataPost, userType: this.userType }); //setting the user in vuex
          document.getElementById("mainpage-container").style.height = "80vh";
          document.getElementById("mainpage-container").style.width = "80vw";
          document.getElementById("mainpage-login").style.opacity = 0;
          document.getElementById("mmainpage-contact-info").style.opacity = 0;
          const vm = this;
          setTimeout(function() {
            vm.$router.push("/dashboard");
          }, 300);
        } else {
          document.getElementById("mainpage-login-error").style.opacity = 1;
        }
      } catch (error) {
        document.getElementById("mainpage-login-error").style.opacity = 1;
        console.log(`${error}`);
      }
    },
    submitSignup: async function() {
      if (this.userType !== "customer") return;
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
          sentData.phone == ""
        ) {
          document.getElementById("mainpage-signup-error").innerHTML =
            "Missing Value(s)";
          document.getElementById("mainpage-signup-error").style.opacity = 1;
          return;
        }

        if (sentData.password !== this.repeatPasswordSignup) {
          document.getElementById("mainpage-signup-error").innerHTML =
            "Passwords do not match";
          document.getElementById("mainpage-signup-error").style.opacity = 1;
          return;
        }

        let postTest = await axios.post(
          proxy.proxy + `/api/${this.userType}/create`,
          sentData
        );

        let statusCode = postTest.status;
        if (statusCode === 200) {
          document.getElementById("mainpage-signup").style.opacity = 0;
          setTimeout(function() {
            document.getElementById("mainpage-signup-form").reset();
            document.getElementById("mainpage-signup").style.zIndex = -1;
            document.getElementById("mainpage-login").style.zIndex = 5;
            document.getElementById("mainpage-login").style.opacity = 1;
          }, 300);
        } else {
          document.getElementById("mainpage-signup-error").innerHTML =
            "An error occured. Please try again later";
          document.getElementById("mainpage-signup-error").style.opacity = 1;
        }
      } catch (error) {
        document.getElementById("mainpage-signup-error").innerHTML =
          "An error occured. Please try again later";
        document.getElementById("mainpage-signup-error").style.opacity = 1;
        console.log(`${error}`);
      }
    },
  },
  async mounted() {
    if (
      document.cookie !== undefined &&
      document.cookie !== -1 &&
      document.cookie !== ""
    ) {
      try {
        let response = await axios.get(
          proxy.proxy + `/api/login/type/${document.cookie}`
        );
        if (response.data == null || response.status !== 200) return;
        this.setUser({
          user: parseInt(document.cookie),
          userType: response.data,
        });
        this.$router.push("/dashboard");
      } catch (error) {
        console.log(`${error}`);
      }
    }
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.mainpage {
  height: 100vh;
  width: 100vw;
  background-image: radial-gradient(whitesmoke 20%, transparent 20%),
    radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
  background-position: 0 0, 7.5vh 7.5vh;
  background-size: 15vh 15vh;
  background-color: rgb(238, 207, 173);
}

#mainpage-container {
  position: absolute;
  height: 50vh;
  width: 50vw;
  border-radius: 5vh;
  background-color: whitesmoke;
}

.mainpage,
#mainpage-container,
#mainpage-select-user,
#mainpage-login-signup,
#mainpage-login,
#mainpage-signup,
.mainpage-form,
.mainpage-button-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  transition: 0.3s;
}

#mainpage-select-user,
#mainpage-login-signup,
#mainpage-login,
#mainpage-signup {
  position: absolute;
  height: 100%;
  width: 100%;
  border-radius: 5vh;
}

#mainpage-select-user {
  z-index: 5;
  animation: changeOpacity 0.3s;
}

@keyframes changeOpacity {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}

#mainpage-login-signup,
#mainpage-login,
#mainpage-signup {
  opacity: 0;
  z-index: -1;
}

.mainpage-button {
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

.mainpage-button:hover {
  background-color: rgb(136, 93, 48);
  color: whitesmoke;
  border-color: rgb(75, 75, 75);
}

.mainpage-input {
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

.mainpage-input:focus {
  background-color: rgb(175, 175, 175);
}

.mainpage-button-container {
  flex-direction: row;
}

.mainpage-spacer {
  width: 3vw;
}

#mainpage-login-error,
#mainpage-signup-error {
  opacity: 0;
  font-size: 3vh;
  font-weight: 600;
  color: rgb(59, 58, 58);
  transition: 0.3s;
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
}

.mainpage-title {
  font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
  font-size: 3vh;
  font-weight: 600;
  color: rgb(59, 58, 58);
  padding-bottom: 3vh;
}

#mainpage-image {
  height: 10vh;
  width: auto;
  margin-bottom: 1vh;
}

#mainpage-contact-info {
  position: absolute;
  top: 2vh;
  right: 2vw;
  background-color: grey;
  color: whitesmoke;
  border-color: rgb(75, 75, 75);
  opacity: 1;
  transition: 0.3s;
}

#mainpage-contact-info:hover {
  background-color: rgb(66, 66, 66);
}
</style>
