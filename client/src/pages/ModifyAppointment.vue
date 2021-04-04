<template>
	<div id="modify-appointment">
		<div id="modify-appointment-container">
			<form
				class="modify-appointment-form"
				id="modify-appointment-form"
				onsubmit="return false"
				v-on:submit.prevent="submitEdit()"
			>
				<h1 id="modify-appointment-title">Modify Appointment</h1>

				<div id="modify-appointment-form-splitter">
					<div class="modify-appointment-form-container">
						<div id="service-container">
							<label class="modify-appointment-label">Select a service:</label>
							<select id="modify-appointment-type" v-model="appointmentType">
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
							class="modify-appointment-input"
							v-if="this.appointmentType === 'Other'"
							v-model="service"
							type="text"
							placeholder="service"
						/>

						<div
							id="modify-appointment-rating-label-container"
						>
							<input
								class="modify-appointment-input"
								id="modify-appointment-slider"
								v-model="rating"
								type="range"
								min="0"
								max="10"
							/>
							<label class="modify-appointment-label">
								Rating: {{ this.rating }}
							</label>
						</div>

						<textarea
							class="modify-appointment-input"
							id="modify-appointment-edit-feedback"
							v-model="feedback"
							placeholder="Write feedback about quality of service..."
							wrap="soft"
						/>

						<textarea
							id="modify-appointment-note"
							placeholder="Write notes to the mechanic..."
							v-model="note"
							wrap="soft"
						/>
					</div>

					<div
						class="modify-appointment-form-container"
					>
						<label class="modify-appointment-label">Timeslot:</label>
						<div id="timeslot-container">
							<div
								class="timeslot"
								v-for="(timeslot, index) in timeslots"
								:key="index"
								:id="timeslot.timeslotId"
								@click="timeslotSelect(timeslot)"
							>
								<label class="modify-appointment-label"
									>Date: {{ timeslot.startDate }}</label
								>
								<div class="timeslot-spacer"></div>
								<label class="modify-appointment-label"
									>Time: {{ timeslot.startTime.slice(0, 5) }} to
									{{ timeslot.endTime.slice(0, 5) }}</label
								>
							</div>
						</div>
					</div>
				</div>

				<p id="modify-appointment-error">Invalid information</p>

				<div id="modify-appointment-button-container">
					<input
						class="modify-appointment-button"
						type="button"
						value="Back"
						v-on:click="backViewDash()"
					/>
					<div class="modify-appointment-spacer"></div>
					<input
						type="submit"
						class="modify-appointment-button"
						value="Submit"
					/>
				</div>
			</form>
		</div>
	</div>
</template>

<script>
	import axios from "axios";
	import proxy from "../constants.js";
	import { mapActions } from "vuex";

	export default {
		name: "ModifyAppointment",
		props: {
			msg: String,
		},
		data() {
			return {
				appt: null,
				appointmentType: "",
				service: "",
				rating: 0,
				feedback: "",
				note: "",
				selectTimeslotId: [],
				timeslots: [],
			};
		},
		methods: {
			...mapActions(["setApptIdToModify"]),
			backViewDash: function() {
				let t = this;
				setTimeout(function() {
					t.$router.push("/dashboard");
				}, 300);
			},
			fetchApptById: async function() {
				try {
					// fetch data of appt with given ID
					let response = await axios.get(
						proxy.proxy + `/api/appointment/getById/${this.apptIdToModify}`
					);

					this.appt = response.data;
					this.appointmentType = this.appt.appointmentType;
					this.service = this.appt.service;
					this.rating = this.appt.rating;
					this.feedback = this.appt.feedback;
				} catch (error) {
					console.error(`${error}`);
					document.getElementById("modify-appointment-error").innerHTML =
						"Unknown error, please try again later";
					document.getElementById("modify-appointment-error").style.opacity = 1;
				}

				if (this.appt === null) {
					console.error(
						"Failed to find requested appointment to modify. Returning to dashboard"
					);
					this.$router.push("/dashboard");
				}
			},
			submitEdit: async function() {
				try {
					let postData = this.appt;
					postData.appointmentType = this.appointmentType;
					postData.service = this.service;
					postData.rating = this.rating;
					postData.feedback = this.feedback;
					if (this.selectTimeslotId.length === 0) {
						postData.timeslots = this.selectTimeslotId[0];
					}

					let response = await axios.post(
						proxy.proxy + "/api/appointment/modifyAppointment",
						postData
					);

					if (response.status === 200) {
						document.getElementById("modify-appointment-status").innerHTML =
							"Successfully modified appointment!";
						document.getElementById(
							"modify-appointment-status"
						).style.opacity = 1;
					}
				} catch (error) {
					console.error(`${error}`);
					document.getElementById("modify-appointment-status").innerHTML =
						"Unknown error, please try again later";
					document.getElementById(
						"modify-appointment-status"
					).style.opacity = 1;
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
			fetchTimeslots: async function() {
				try {
					let today = new Date();
					today = `${today.getFullYear()}-${today.getMonth() +
						1}-${today.getDate()}`;

					let response = await axios.get(
						proxy.proxy + "/api/timeslot/available/" + today
					);
					this.timeslots = response.data;

					if (
						this.timeslots.length === 0 &&
						this.appt.timeslotsId.length !== 0
					) {
						document.getElementById("modify-appointment-error").innerHTML =
							"There are no availabilities for appointments. Please try again later";
						document.getElementById(
							"modify-appointment-error"
						).style.opacity = 1;
					}
				} catch (error) {
					console.error(error);
					document.getElementById("modify-appointment-error").innerHTML =
						"Something went wrong fetching available timeslots. Please try again later";
					document.getElementById("modify-appointment-error").style.opacity = 1;
				}
			},
		},
		mounted() {
			this.apptIdToModify = this.$store.state.apptIdToModify;
			this.setApptIdToModify(-1);

			this.fetchApptById();
		},
	};
</script>

<style scoped>
	#modify-appointment {
		height: 100vh;
		width: 100vw;
		background-image: radial-gradient(whitesmoke 20%, transparent 20%),
			radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
		background-position: 0 0, 7.5vh 7.5vh;
		background-size: 15vh 15vh;
		background-color: rgb(238, 207, 173);
	}

	#modify-appointment,
	.modify-appointment-form,
	#modify-appointment-button-container {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}

	#modify-appointment-container {
		height: 80vh;
		width: 80vw;
		border-radius: 5vh;
		background-color: whitesmoke;
		transition: 0.3s;
	}

	.modify-appointment-input {
		all: unset;
		background-color: rgb(212, 211, 211);
		font-size: 3vh;
		width: 30vw;
		margin-top: 1vh;
		margin-bottom: 1vh;
		padding: 2vh;
		border-radius: 2vh;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-weight: 500;
		color: rgb(59, 58, 58);
		transition: 0.3s;
	}

	.modify-appointment-input:focus,
	#modify-appointment-note:focus {
		background-color: rgb(175, 175, 175);
	}

	#modify-appointment-button-container {
		flex-direction: row;
	}

	.modify-appointment-label {
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-size: 3vh;
		font-weight: 500;
		color: rgb(59, 58, 58);
		transition: 0.3s;
	}

	#modify-appointment-type {
		all: unset;
		background-color: rgb(212, 211, 211);
		width: 10vw;
		margin-left: 1vh;
		padding: 1vh;
		border-radius: 2vh;
		font-size: 3vh;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-weight: 500;
		color: rgb(59, 58, 58);
		transition: 0.3s;
	}

	#modify-appointment-note {
		all: unset;
		background-color: rgb(212, 211, 211);
		width: 30vw;
		height: 15vh;
		padding: 2vh;
		border-radius: 2vh;
		font-size: 3vh;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-weight: 500;
		color: rgb(59, 58, 58);
		transition: 0.3s;
		margin-top: 1vh;
		margin-bottom: 1vh;
	}

	.modify-appointment-button {
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

	.modify-appointment-button:hover {
		background-color: rgb(175, 122, 65);
		color: whitesmoke;
		border-color: rgb(75, 75, 75);
	}

	.modify-appointment-spacer {
		width: 3vw;
	}

	#modify-appointment-title {
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-size: 3vh;
		font-weight: 600;
		color: rgb(59, 58, 58);
		padding-bottom: 3vh;
		animation: changeOpacity 0.3s;
		transition: 0.3s;
	}

	#modify-appointment-slider {
		height: 0.1vh;
	}

	#modify-appointment-slider :hover {
		opacity: 1;
	}

	#modify-appointment-slider::-webkit-slider-thumb {
		appearance: none;
		width: 3vh;
		height: 3vh;
		cursor: pointer;
		background: rgb(235, 164, 89);
		border-radius: 50%;
	}

	#modify-appointment-error {
		opacity: 0;
		font-size: 3vh;
		font-weight: 600;
		color: rgb(59, 58, 58);
		transition: 0.3s;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
	}

	#modify-appointment-form-splitter {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: row;
		transition: 0.3s;
	}

	.modify-appointment-form-container {
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		animation: changeOpacity 0.3s;
		transition: 0.3s;
		padding-right: 1vw;
		padding-left: 1vw;
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

	#modify-appointment-rating-label-container {
		display: flex;
		width: 100%;
		justify-content: center;
		align-items: center;
		flex-direction: column;
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

	@keyframes changeOpacity {
		from {
			opacity: 0;
		}

		to {
			opacity: 1;
		}
	}
</style>
