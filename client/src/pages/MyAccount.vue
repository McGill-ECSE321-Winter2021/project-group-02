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
						id="myaccount-edit-email"
						v-model="emailEdit"
						type="email"
						placeholder="email"
						@change="fetchUserByEmail()"
					/>
					<input
						v-else
						class="myaccount-input"
						id="myaccount-edit-email"
						v-model="emailEdit"
						type="email"
						placeholder="email"
					/>
					<input
						class="myaccount-input"
						id="myaccount-edit-name"
						v-model="nameEdit"
						type="text"
						placeholder="name"
					/>
					<input
						class="myaccount-input"
						id="myaccount-edit-phone"
						v-model="phoneEdit"
						type="tel"
						placeholder="phone"
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
						<div class="myaccount-spacer"></div>
						<input
							class="myaccount-button"
							type="button"
							value="Delete Account"
							v-on:click="deleteCurAccount()"
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
				idEdit: "",
				nameEdit: "",
				emailEdit: "",
				phoneEdit: "",
				passwordEdit: "",
				repeatPasswordEdit: "",
				userType: "",
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
						scrsUserId: this.idEdit,
						name: this.nameEdit,
						password: this.passwordEdit,
						email: this.emailEdit,
						phone: this.phoneEdit,
					};

					if (
						sentData.name == "" ||
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

					let putResult = await axios.put(
						proxy.proxy + `/api/${this.userType}/update`,
						sentData
					);
					if (putResult.status === 200) {
						//success message
						document.getElementById("myaccount-edit-error").innerHTML =
							"Successfully updated account info";
						document.getElementById("myaccount-edit-error").style.opacity = 1;
						setTimeout(function() {
							document.getElementById("myaccount-edit-error").style.opacity = 0;
						}, 1500);
					} else {
						document.getElementById("myaccount-edit-error").innerHTML =
							"An error occured. Please try again later";
						document.getElementById("myaccount-edit-error").style.opacity = 1;
						console.log(putResult.status);
					}
				} catch (error) {
					document.getElementById("myaccount-edit-error").innerHTML =
						"An error occured. Please try again later";
					document.getElementById("myaccount-edit-error").style.opacity = 1;
					console.log(`${error}`);
				}
			},
			fetchUserByID: async function() {
				try {
					// fetch data of user with given ID
					let response = null;
					switch (this.$store.state.userType) {
						case "customer":
							response = await axios.get(
								proxy.proxy + `/api/customer/getByID/${this.idEdit}`
							);
							this.nameEdit = response.data.customerName;
							this.emailEdit = response.data.customerEmail;
							this.phoneEdit = response.data.customerPhone;
							break;
						case "technician":
							response = await axios.get(
								proxy.proxy + `/api/technician/getByID/${this.idEdit}`
							);
							this.nameEdit = response.data.technicianName;
							this.emailEdit = response.data.technicianEmail;
							this.phoneEdit = response.data.technicianPhone;
							break;
						case "assistant":
							response = await axios.get(
								proxy.proxy + `/api/assistant/getByID/${this.idEdit}`
							);
							this.nameEdit = response.data.assistantName;
							this.emailEdit = response.data.assistantEmail;
							this.phoneEdit = response.data.assistantPhone;
							break;
						default:
							document.getElementById("myaccount-edit-error").innerHTML =
								"User not identified, please try again";
							document.getElementById("myaccount-edit-error").style.opacity = 1;
							document.getElementById("myaccount-edit-form").reset();
							this.nameEdit = "";
							this.emailEdit = "";
							this.phoneEdit = "";
							return;
					}
				} catch (error) {
					console.error(`${error}`);
					document.getElementById("myaccount-edit-error").innerHTML =
						"Unknown error, please try again later";
					document.getElementById("myaccount-edit-error").style.opacity = 1;
				}
			},
			fetchUserByEmail: async function() {
				try {
					// find type of user with given ID
					let response = await axios.get(
						proxy.proxy + `/api/login/typeByEmail/${this.emailEdit}`
					);
					if (response.data == null || response.status !== 200) {
						document.getElementById("myaccount-edit-error").innerHTML =
							"No such user exists";
						document.getElementById("myaccount-edit-error").style.opacity = 1;
						return;
					}
					this.userType = response.data;
					// fetch data of user with given email
					switch (response.data) {
						case "customer":
							response = await axios.get(
								proxy.proxy + `/api/customer/getByEmail/${this.emailEdit}`
							);
							this.idEdit = response.data.customerId;
							this.nameEdit = response.data.customerName;
							this.emailEdit = response.data.customerEmail;
							this.phoneEdit = response.data.customerPhone;
							break;
						case "technician":
							response = await axios.get(
								proxy.proxy + `/api/technician/getByEmail/${this.emailEdit}`
							);
							this.idEdit = response.data.customerId;
							this.nameEdit = response.data.technicianName;
							this.emailEdit = response.data.technicianEmail;
							this.phoneEdit = response.data.technicianPhone;
							break;
						case "assistant":
							response = await axios.get(
								proxy.proxy + `/api/assistant/getByEmail/${this.emailEdit}`
							);
							this.idEdit = response.data.customerId;
							this.nameEdit = response.data.assistantName;
							this.emailEdit = response.data.assistantEmail;
							this.phoneEdit = response.data.assistantPhone;
							break;
						default:
							document.getElementById("myaccount-edit-error").innerHTML =
								"User not identified, please try again";
							document.getElementById("myaccount-edit-error").style.opacity = 1;
							document.getElementById("myaccount-edit-form").reset();
							this.nameEdit = "";
							this.emailEdit = "";
							this.phoneEdit = "";
							return;
					}
				} catch (error) {
					console.error(`${error}`);
					document.getElementById("myaccount-edit-error").innerHTML =
						"Unknown error, please try again later";
					document.getElementById("myaccount-edit-error").style.opacity = 1;
				}
			},
			deleteCurAccount: async function() {
				try {
					let deleteResult = await axios.delete(
						proxy.proxy + `/api/${this.userType}/delete/${this.idEdit}`
					);
					if (deleteResult.status === 200) {
						//success message
						document.getElementById("myaccount-edit-error").innerHTML =
							"Successfully deleted account";
						document.getElementById("myaccount-edit-error").style.opacity = 1;
						setTimeout(function() {
							document.getElementById("myaccount-edit-error").style.opacity = 0;
						}, 1500);
						let temp = this;
						setTimeout(function() {
							if (temp.$store.state.userType !== "assistant")
								temp.$router.push("/");
						}, 300);
					} else {
						document.getElementById("myaccount-edit-error").innerHTML =
							"An error occured. Please try again later";
						document.getElementById("myaccount-edit-error").style.opacity = 1;
						console.log(deleteResult.status);
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
			if (user === -1) this.$router.push("/");
			this.idEdit = user;
			this.userType = this.$store.state.userType;
			this.fetchUserByID();
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
