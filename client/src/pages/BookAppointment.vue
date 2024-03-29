<template>
	<div id="book-appointment">
		<div id="book-appointment-container">
			<H1 id="book-appointment-title">Create Appointment</H1>

			<form id="appointment-form" @submit.prevent="bookAppointment()">
				<div id="form-splitter">
					<div class="form-container">
						<div id="form-appointment-type">
							<label class="form-text">Select a service:</label>
							<select
								id="appointment-type"
								class="form-text"
								v-model="selectAppointmentType"
							>
								<option value="" selected disabled>Choose service</option>
								<option value="CarWash">Car Wash</option>
								<option value="Maintenance">Maintenance</option>
								<option value="OilChange">Oil Change</option>
								<option value="TireChange">Tire Change</option>
								<option value="Towing">Towing</option>
								<option value="Inspection">Inspection</option>
								<option value="RoadsideAssistance">Roadside Assistance</option>
								<option value="Checkup">Checkup</option>
								<option value="Other">Other</option>
							</select>
						</div>

						<input
							id="form-service"
							class="form-text"
							v-if="this.selectAppointmentType === 'Other'"
							type="text"
							placeholder="service"
							v-model="inputService"
						/>

						<div id="form-customer" v-if="assistantBooking === true">
							<label class="form-text">Customer e-mail:</label>
							<input
								id="customer-email"
								class="form-text"
								type="text"
								placeholder="customer@mail.com"
								v-model="inputEmail"
								@focusout="getCustomer()"
							/>
						</div>

						<label class="form-text">Appointment message:</label>
						<textarea
							id="form-note"
							class="form-text"
							placeholder="Write notes to the mechanic..."
							v-model="inputNote"
						/>
					</div>

					<div class="form-container">
						<label class="form-text">Timeslot:</label>
						<div id="timeslot-container">
							<div
								class="timeslot"
								v-for="(timeslot, index) in timeslots"
								:key="index"
								:id="timeslot.timeslotId"
								@click="timeslotSelect(timeslot)"
							>
								<label class="form-text">Date: {{ timeslot.startDate }}</label>
								<div class="timeslot-spacer"></div>
								<label class="form-text"
									>Time: {{ timeslot.startTime.slice(0, 5) }} to
									{{ timeslot.endTime.slice(0, 5) }}</label
								>
							</div>
						</div>
					</div>
				</div>

				<label id="form-error" class="form-text" v-if="errorMsg !== ''">{{
					this.errorMsg
				}}</label>

				<div id="form-buttons">
					<button class="form-button" @click="backViewDash()">Cancel</button>
					<input type="submit" class="form-button" value="Book" />
				</div>
			</form>
		</div>
	</div>
</template>

<script>
	import axios from "axios";
	import proxy from "../constants.js";

	export default {
		name: "BookAppointment",
		props: {
			msg: String,
		},

		data() {
			return {
				selectAppointmentType: "",
				inputService: "",
				inputNote: "",
				assistantBooking: false,
				inputEmail: "",
				customerId: this.$store.state.user,
				timeslots: [],
				selectTimeslotId: [],
				errorMsg: "",
			};
		},

		methods: {
			backViewDash() {
				let t = this;
				document.getElementById("book-appointment-title").style.opacity = 0;
				document.getElementById("appointment-form").style.opacity = 0;
				setTimeout(function() {
					t.$router.push("/dashboard");
				}, 300);
			},

			async getCustomer() {
				if (this.inputEmail === undefined || this.inputEmail === "") {
					this.customerId = -1;
					this.errorMsg = "Customer e-mail is not filled";
					return;
				}
				try {
					let customerResponse = await axios.get(
						proxy.proxy + `/api/customer/getByEmail/${this.inputEmail}`
					);

					if (customerResponse.status === 200) {
						this.customerId = customerResponse.data.customerId;
						this.errorMsg = "";
					}
				} catch (error) {
					console.error(error);
					this.customerId = -1;
					this.errorMsg =
						"Customer account with this e-mail could not be found";
				}
			},

			timeslotSelect(timeslot) {
				if (this.selectTimeslotId.length !== 0) {
					let previousTimeslot = document.getElementById(
						this.selectTimeslotId[0]
					);
					// passing an empty string will revert the css to its default value
					previousTimeslot.style.backgroundColor = "";
					previousTimeslot.style.color = "";
					previousTimeslot.style.borderColor = "";
				}
				this.selectTimeslotId = [timeslot.timeslotId];
				let timeslotComponent = document.getElementById(
					this.selectTimeslotId[0]
				);
				timeslotComponent.style.backgroundColor = "rgb(175, 122, 65)";
				timeslotComponent.style.color = "whitesmoke";
				timeslotComponent.style.borderColor = "rgb(75, 75, 75)";
			},

			async bookAppointment() {
				if (this.selectAppointmentType === "") {
					this.errorMsg = "Appointment service is not selected";
					return;
				}
				if (
					this.selectAppointmentType === "other" &&
					(this.inputService === undefined || this.inputService === "")
				) {
					this.errorMsg = "Please specify a service";
					return;
				}
				if (this.customerId === -1) {
					this.errorMsg = "Invalid customer for the appointment";
					return;
				}
				if (this.selectTimeslotId.length === 0) {
					this.errorMsg = "Select an availability for the appointment";
					return;
				}
				try {
					let bookAppointmentData = {
						appointmentType: this.selectAppointmentType,
						service: this.inputService,
						note: this.inputNote,
						customerId: this.customerId,
						timeslotsId: this.selectTimeslotId,
					};
					if (this.selectAppointmentType !== "other") {
						bookAppointmentData.service = "";
					}

					let bookingResult = await axios.post(
						proxy.proxy + "/api/appointment/book",
						bookAppointmentData
					);

					if (bookingResult.status === 200) {
						this.backViewDash();
					}
				} catch (error) {
					console.error(`${error}`);
					this.errorMsg =
						"Something went wrong during the booking process. Please try again later";
				}
			},
		},

		async mounted() {
			if (
				this.$store.state.userType !== "assistant" &&
				this.$store.state.userType !== "customer"
			)
				this.$router.push("/dashboard");

			if (this.$store.state.userType === "assistant") {
				this.customerId = -1;
				this.assistantBooking = true;
			}

			try {
				let today = new Date();
				today = `${today.getFullYear()}-${today.getMonth() +
					1}-${today.getDate()}`;

				let response = await axios.get(
					proxy.proxy + "/api/timeslot/available/" + today
				);
				this.timeslots = response.data;

				if (this.timeslots.length === 0) {
					this.errorMsg =
						"There are no availabilities for appointments. Please try again later";
				}
			} catch (error) {
				console.error(error);
				this.errorMsg =
					"Something went wrong fetching available timeslots. Please try again later";
			}
		},
	};
</script>

<style scoped>
	#book-appointment,
	#book-appointment-container,
	#appointment-form,
	#appointment-type {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		transition: 0.3s;
	}

	#book-appointment {
		height: 100vh;
		width: 100vw;
		background-image: radial-gradient(whitesmoke 20%, transparent 20%),
			radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
		background-position: 0 0, 7.5vh 7.5vh;
		background-size: 15vh 15vh;
		background-color: rgb(238, 207, 173);
	}

	#book-appointment-container {
		height: 80vh;
		width: 80vw;
		border-radius: 5vh;
		background-color: whitesmoke;
	}

	#book-appointment-title {
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-size: 4vh;
		font-weight: 600;
		color: rgb(59, 58, 58);
		padding-bottom: 3vh;
		animation: changeOpacity 0.3s;
		transition: 0.3s;
	}

	#form-splitter {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: row;
		transition: 0.3s;
	}

	.form-container,
	#form-customer {
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		animation: changeOpacity 0.3s;
		transition: 0.3s;
	}

	.form-container {
		padding-right: 1vw;
		padding-left: 1vw;
	}

	.form-text {
		all: unset;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-size: 3vh;
		font-weight: 500;
		color: rgb(59, 58, 58);
		animation: changeOpacity, 0.3s;
		transition: 0.3s;
		padding-bottom: 1vh;
		padding-top: 1vh;
	}
	#form-error {
		color: rgb(255, 0, 0);
	}

	#appointment-type {
		background-color: rgb(212, 211, 211);
		padding: 1vh;
		border-radius: 2vh;
		text-align: center;
		margin-bottom: 1vh;
	}

	#timeslot-container {
		background-color: rgb(225, 225, 225);
		width: 35vw;
		height: 36vh;
		overflow-y: scroll;
		border-radius: 2vh;
		padding-top: 2vh;
		padding-bottom: 2vh;
		display: flex;
		align-items: center;
		flex-direction: column;
		transition: 0.3s;
	}

	#form-service,
	#customer-email {
		background-color: rgb(212, 211, 211);
		width: 30vw;
		padding: 1vh;
		border-radius: 2vh;
	}

	#form-note {
		background-color: rgb(212, 211, 211);
		width: 30vw;
		height: 15vh;
		padding: 1vh;
		border-radius: 2vh;
	}

	.timeslot {
		all: unset;
		border-radius: 2vh;
		background-color: rgb(235, 164, 89);
		border: 0.5vh solid rgb(225, 225, 225);
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-size: 3vh;
		font-weight: 600;
		color: rgb(75, 75, 75);
		text-align: center;
		animation: changeOpacity, 0.3s;
		transition: 0.3s;
		text-align: center;
		padding: 2vh;
	}
	.timeslot:hover {
		background-color: rgb(175, 122, 65);
		color: whitesmoke;
		border-color: rgb(75, 75, 75);
	}

	.timeslot-spacer {
		width: 1vw;
	}

	.form-button {
		all: unset;
		height: 5vh;
		width: 12vw;
		margin: 3vh 1vh 1vh;
		padding: 1vh;
		border-radius: 2vh;
		background-color: rgb(235, 164, 89);
		border: 0.5vh solid rgb(235, 164, 89);
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-size: 3vh;
		font-weight: 600;
		color: rgb(75, 75, 75);
		text-align: center;
		animation: changeOpacity, 0.3s;
		transition: 0.3s;
	}
	.form-button:hover {
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
