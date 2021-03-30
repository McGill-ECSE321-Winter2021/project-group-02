<template>
	<div class="adminTechSignup">
		<div id="adminTechSignup-container">
			<H1 id="adminTechSignup-title" class="adminTechSignup-title"
				>Create New Account</H1
			>
			<div id="adminTechSignup-edit">
				<form
					class="adminTechSignup-form"
					id="adminTechSignup-form"
					onsubmit="return false"
					@change="change()"
					v-on:submit.prevent="createAccount()"
				>
					<select
						id="adminTechSignup-select-type"
						class="adminTechSignup-select"
						v-model="newUserType"
					>
						<option selected value="assistant">Administrative Assistant</option>
						<option value="technician">Technician</option>
					</select>
					<input
						class="adminTechSignup-input"
						id="adminTechSignup-email"
						v-model="emailSignup"
						type="email"
						placeholder="email"
					/>
					<input
						class="adminTechSignup-input"
						id="adminTechSignup-name"
						v-model="nameSignup"
						type="text"
						placeholder="name"
					/>
					<input
						class="adminTechSignup-input"
						id="adminTechSignup-phone"
						v-model="phoneSignup"
						type="tel"
						placeholder="phone"
					/>
					<input
						class="adminTechSignup-input"
						id="adminTechSignup-password"
						v-model="passwordSignup"
						type="password"
						placeholder="password"
					/>
					<input
						class="adminTechSignup-input"
						id="adminTechSignup-repeat-password"
						v-model="repeatPasswordSignup"
						type="password"
						placeholder="repeat password"
					/>
					<p id="adminTechSignup-error">Invalid information</p>
					<div class="adminTechSignup-button-container">
						<input
							class="adminTechSignup-button"
							type="button"
							value="Back"
							v-on:click="backViewDash()"
						/>
						<div class="adminTechSignup-spacer"></div>
						<input
							type="submit"
							class="adminTechSignup-button"
							value="Submit"
						/>
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
		name: "AdminTechSignup",
		props: {
			msg: String,
		},
		data() {
			return {
				newUserType: "assistant",
				nameSignup: "",
				emailSignup: "",
				phoneSignup: "",
				passwordSignup: "",
				repeatPasswordSignup: "",
			};
		},
		methods: {
			change: function() {
				document.getElementById(
					"adminTechSignup-signup-error"
				).style.opacity = 0;
			},
			backViewDash: function() {
				let t = this;
				document.getElementById("adminTechSignup-edit").style.opacity = 0;
				document.getElementById("adminTechSignup-title").style.opacity = 0;
				setTimeout(function() {
					t.$router.push("/dashboard");
				}, 300);
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
						document.getElementById("adminTechSignup-error").innerHTML =
							"Missing Value(s)";
						document.getElementById("adminTechSignup-error").style.opacity = 1;
						return;
					}

					if (sentData.password !== this.repeatPasswordSignup) {
						document.getElementById("adminTechSignup-error").innerHTML =
							"Passwords do not match";
						document.getElementById("adminTechSignup-error").style.opacity = 1;
						return;
					}

					let postTest = await axios.post(
						proxy.proxy + `/api/${this.newUserType}/create`,
						sentData
					);

					if (postTest.status === 200) {
						document.getElementById("adminTechSignup-error").innerHTML =
							"Successfully created new account";
						document.getElementById("adminTechSignup-error").style.opacity = 1;
						document.getElementById("adminTechSignup-form").reset();
						setTimeout(function() {
							document.getElementById(
								"adminTechSignup-error"
							).style.opacity = 0;
						}, 1500);
					} else {
						document.getElementById("adminTechSignup-error").innerHTML =
							"Could not create account. Please try again later";
						document.getElementById("adminTechSignup-error").style.opacity = 1;
					}
				} catch (error) {
					document.getElementById("adminTechSignup-signup-error").innerHTML =
						"An error occured. Please try again later";
					document.getElementById(
						"adminTechSignup-signup-error"
					).style.opacity = 1;
					console.log(`${error}`);
				}
			},
		},
		async mounted() {
			if (this.$store.state.userType !== "assistant") this.$router.push("/");
		},
	};
</script>

<style scoped>
	.adminTechSignup {
		height: 100vh;
		width: 100vw;
		background-image: radial-gradient(whitesmoke 20%, transparent 20%),
			radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
		background-position: 0 0, 7.5vh 7.5vh;
		background-size: 15vh 15vh;
		background-color: rgb(238, 207, 173);
	}

	#adminTechSignup-container {
		height: 80vh;
		width: 80vw;
		border-radius: 5vh;
		background-color: whitesmoke;
	}

	.adminTechSignup,
	#adminTechSignup-container,
	.adminTechSignup-form,
	.adminTechSignup-button-container {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		transition: 0.3s;
	}

	.adminTechSignup-button {
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

	.adminTechSignup-button:hover {
		background-color: rgb(175, 122, 65);
		color: whitesmoke;
		border-color: rgb(75, 75, 75);
	}

	.adminTechSignup-input {
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

	.adminTechSignup-input:focus {
		background-color: rgb(175, 175, 175);
	}

	.adminTechSignup-button-container {
		flex-direction: row;
	}

	.adminTechSignup-spacer {
		width: 3vw;
	}

	#adminTechSignup-error {
		opacity: 0;
		font-size: 3vh;
		font-weight: 600;
		color: rgb(59, 58, 58);
		transition: 0.3s;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
	}

	#adminTechSignup-edit {
		animation: changeOpacity 0.3s;
		transition: 0.3s;
	}

	.adminTechSignup-title {
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-size: 3vh;
		font-weight: 600;
		color: rgb(59, 58, 58);
		padding-bottom: 3vh;
		animation: changeOpacity 0.3s;
		transition: 0.3s;
	}

	.adminTechSignup-select {
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

	@keyframes changeOpacity {
		from {
			opacity: 0;
		}

		to {
			opacity: 1;
		}
	}
</style>
