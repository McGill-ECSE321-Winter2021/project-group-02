<template>
	<div class="myaccount">
		<div id="myaccount-container">
			<div id="myaccount-edit">
				<form
					class="myaccount-form"
					id="myaccount-edit-form"
					onsubmit="return false"
					@change="change()"
					v-on:submit.prevent="submitEdit()"
				>
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
					<p id="myaccount-edit-error">Wrong username or password</p>
					<div class="myaccount-button-container">
						<input
							class="myaccount-button"
							type="button"
							value="Back"
							v-on:click="backViewDash()"
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
			if (
				document.cookie !== undefined &&
				document.cookie !== -1 &&
				document.cookie !== ""
			) {
				console.log(document.cookie);
				this.$router.push("/dashboard");
			}
			//TODO if cookies eventually work, use the current cookie to get the user data to populate the form above
		},
	};
</script>

<style scoped></style>
