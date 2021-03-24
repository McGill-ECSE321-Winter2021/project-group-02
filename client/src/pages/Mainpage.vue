<template>
  <div class="mainpage">
    <div id="mainpage-container">
      <div id="mainpage-select-user">
        <button class="mainpage-button" @click="customer()">Customer</button>
        <button class="mainpage-button" @click="technician()">
          Technician
        </button>
        <button class="mainpage-button" @click="assistant()">Assistant</button>
      </div>
      <div id="mainpage-login-signup">
        <button class="mainpage-button" @click="login()">Login</button>
        <button class="mainpage-button" @click="signup()">
          Sign Up
        </button>
        <button class="mainpage-button" @click="backUserSelect()">Back</button>
      </div>
      <div id="mainpage-login">
        <form class="mainpage-form" submit="return false" @change="change()">
          <input
            class="mainpage-input"
            id="mainpage-login-email"
            type="email"
            placeholder="email"
          />
          <input
            class="mainpage-input"
            id="mainpage-login-password"
            type="password"
            placeholder="password"
          />
          <p id="mainpage-login-error">Wrong username or password</p>
          <div class="mainpage-button-container">
            <button class="mainpage-button" @click="backLoginSignup()">
              Back
            </button>
            <div class="mainpage-spacer"></div>
            <button class="mainpage-button" @click="submitLogin()">
              Submit
            </button>
          </div>
        </form>
      </div>
      <div id="mainpage-signup">
        <form
          class="mainpage-form"
          id="mainpage-signup-form"
          onsubmit="return false"
          @change="change()"
        >
          <input
            class="mainpage-input"
            id="mainpage-signup-name"
            type="text"
            placeholder="name"
          />
          <input
            class="mainpage-input"
            id="mainpage-signup-email"
            type="email"
            placeholder="email"
          />
          <input
            class="mainpage-input"
            id="mainpage-signup-phone"
            type="tel"
            placeholder="phone number (format: 000-000-0000)"
          />
          <input
            class="mainpage-input"
            id="mainpage-signup-password"
            type="password"
            placeholder="password"
          />
          <input
            class="mainpage-input"
            id="mainpage-signup-repeat-password"
            type="password"
            placeholder="repeat password"
          />
          <p id="mainpage-signup-error">Wrong username or password</p>
          <div class="mainpage-button-container">
            <button class="mainpage-button" @click="backLoginSignup()">
              Back
            </button>
            <div class="mainpage-spacer"></div>
            <button class="mainpage-button" @click="submitSignup()">
              Submit
            </button>
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
  name: "Mainpage",
  props: {
    msg: String,
  },
  data() {
    return {
      userType: "",
    };
  },
  methods: {
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
        document.getElementById("mainpage-login-signup").style.zIndex = 5;
        document.getElementById("mainpage-login-signup").style.opacity = 1;
      }, 300);
    },
    assistant: function() {
      this.userType = "assistant";
      document.getElementById("mainpage-select-user").style.opacity = 0;
      setTimeout(function() {
        document.getElementById("mainpage-select-user").style.zIndex = -1;
        document.getElementById("mainpage-login-signup").style.zIndex = 5;
        document.getElementById("mainpage-login-signup").style.opacity = 1;
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
      document.getElementById("mainpage-signup").style.opacity = 0;
      document.getElementById("mainpage-login").style.opacity = 0;
      document.getElementById("mainpage-container").style.height = "50vh";
      document.getElementById("mainpage-login-error").style.opacity = 0;
      setTimeout(function() {
        document.getElementById("mainpage-login-signup").style.zIndex = 5;
        document.getElementById("mainpage-signup").style.zIndex = -1;
        document.getElementById("mainpage-login").style.zIndex = -1;
        document.getElementById("mainpage-login-signup").style.opacity = 1;
      }, 300);
    },
    change: function() {
      document.getElementById("mainpage-login-error").style.opacity = 0;
      document.getElementById("mainpage-signup-error").style.opacity = 0;
    },
    submitLogin: async function() {
      let email = document.getElementById("mainpage-login-email").value;
      let password = document.getElementById("mainpage-login-password").value;

      try {
        let sentData = `email=${email}&password=${password}`;

        let postTest = await axios.post(
          proxy.proxy + `/api/login/${this.userType}`,
          sentData
        );

        let responseDataPost = postTest.data; //do something with this for tests
        let statusCode = postTest.status;
        console.log(responseDataPost);
        if (responseDataPost === true && statusCode === 200) {
          document.getElementById("mainpage-container").style.height = "80vh";
          document.getElementById("mainpage-container").style.width = "80vw";
          document.getElementById("mainpage-login").style.opacity = 0;
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
      try {
        let sentData = {
          name: document.getElementById("mainpage-signup-name").value,
          password: document.getElementById("mainpage-signup-password").value,
          email: document.getElementById("mainpage-signup-email").value,
          phone: document.getElementById("mainpage-signup-phone").value,
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

        if (
          sentData.password !==
          document.getElementById("mainpage-signup-repeat-password").value
        ) {
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
  opacity: 1;
  z-index: 5;
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
}

.mainpage-button:hover {
  background-color: rgb(175, 122, 65);
  color: rgb(230, 230, 230);
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
</style>
