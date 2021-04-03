<template>
	<div class="payment">
		<div id="payment-container">
			<H1 id="payment-title" class="payment-title">Pay for Appointment</H1>
			<div id="payment-form-container">
				<label class="appointment-info-payment">Data</label>
				<!-- TODO EDIT THIS TO DISPLAY APPOINTMENT DATE AND CUSTOMER ETC... ^^^ -->
				<form
					class="payment-form"
					id="payment-form"
					onsubmit="return false"
					@change="change()"
					v-on:submit.prevent="submitPayment()"
				>
					<input
						v-if="this.$store.state.userType === 'customer'"
						class="payment-input"
						id="payment-card"
						v-model="cardNo"
						pattern="\d*"
						maxlength="19"
						placeholder="Card Number"
						type="tel"
					/>
					<input
						v-if="this.$store.state.userType === 'customer'"
						class="payment-input"
						id="payment-expiry"
						v-model="expDate"
						type="tel"
						placeholder="MM / YY"
						pattern="\d*"
						maxlength="7"
					/>
					<input
						v-if="this.$store.state.userType === 'customer'"
						class="payment-input"
						id="payment-cvc"
						v-model="cvc"
						type="tel"
						placeholder="CVC"
						pattern="\d*"
						maxlength="4"
					/>
					<p id="payment-error">Invalid information</p>
					<div class="payment-button-container">
						<input
							class="payment-button"
							type="button"
							value="Back"
							@click="backAppointments()"
						/>
						<div class="payment-spacer"></div>
						<input
							v-if="this.$store.state.userType === 'customer'"
							type="submit"
							class="payment-button"
							value="Submit Payment"
						/>
						<button
							v-if="this.$store.state.userType === 'assistant'"
							class="payment-button"
							@click="submitPayment()"
						>
							Mark as Paid
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
		name: "PayForAppointment",
		props: {
			msg: String,
		},
		data() {
			return {
				cardNo: "",
				expDate: "",
				cvc: "",
			};
		},
		methods: {
			backAppointments: function() {
				let t = this;
				document.getElementById("payment-form-container").style.opacity = 0;
				document.getElementById("payment-title").style.opacity = 0;
				setTimeout(function() {
					t.$router.push("/dashboard"); //TODO CHANGE TO APPOINTMENTS
				}, 300);
			},
			change: function() {
				document.getElementById("payment-error").style.opacity = 0;
			},
			submitPayment: async function() {
				if (
					this.$store.state.userType === "customer" &&
					(this.cardNo === "" || this.expDate === "" || this.cvc === "")
				) {
					document.getElementById("payment-error").innerHTML =
						"Missing Information";
					document.getElementById("payment-error").style.opacity = 1;
					return;
				}
				try {
					let putResult = await axios.put(
						proxy.proxy + `/api/appointment/pay`,
						`appointmentId=${this.$store.state.apptIdToModify}`
					);
					if (putResult.status === 200) {
						//success message
						document.getElementById("payment-error").innerHTML =
							"Payment complete!";
						document.getElementById("payment-error").style.opacity = 1;
						setTimeout(function() {
							document.getElementById("payment-error").style.opacity = 0;
							this.backAppointments();
						}, 1500);
					} else {
						document.getElementById("payment-error").innerHTML =
							"Invalid request. Could not process payment";
						document.getElementById("payment-error").style.opacity = 1;
						console.error(putResult.status);
					}
				} catch (error) {
					document.getElementById("payment-error").innerHTML =
						"Error. Could not process payment. Please try again later";
					document.getElementById("payment-error").style.opacity = 1;
					console.error(error);
				}
			},
		},
		mounted() {
			//if (this.$store.state.user === -1) this.$router.push("/"); TODO UNCOMMENT
			//fetch customer name, appointment date/time TODO
			this.$store.state.userType = "assistant";
		},
	};
</script>

<style scoped>
	.payment {
		height: 100vh;
		width: 100vw;
		background-image: radial-gradient(whitesmoke 20%, transparent 20%),
			radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
		background-position: 0 0, 7.5vh 7.5vh;
		background-size: 15vh 15vh;
		background-color: rgb(238, 207, 173);
	}

	#payment-container {
		height: 80vh;
		width: 80vw;
		border-radius: 5vh;
		background-color: whitesmoke;
	}

	.payment,
	#payment-container,
	.payment-form,
	.payment-button-container {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		transition: 0.3s;
	}

	.payment-button {
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

	.payment-button:hover {
		background-color: rgb(175, 122, 65);
		color: whitesmoke;
		border-color: rgb(75, 75, 75);
	}

	.payment-input {
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

	.payment-input:focus {
		background-color: rgb(175, 175, 175);
	}

	.payment-button-container {
		flex-direction: row;
	}

	.payment-spacer {
		width: 3vw;
	}

	#payment-error {
		opacity: 0;
		font-size: 3vh;
		font-weight: 600;
		color: rgb(59, 58, 58);
		transition: 0.3s;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
	}

	.payment-title {
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
