<template>
	<div class="notifications">
		<div id="notifications-container">
			<H1 id="notifications-title" class="notifications-title"
				>Upcoming Appointments</H1
			>
			<div
				class="notification"
				v-for="(appointment, index) in this.appointments"
				:key="index"
				:id="appointment.appointmentId"
			>
				<label class="form-text">
					{{
						convertForDisplay(appointment.appointment.appointmentType)
					}}</label
				><br />
				<label
					class="form-text"
					v-if="appointment.timeslot.startDate === appointment.timeslot.endDate"
				>
					{{ appointment.timeslot.startDate }}
				</label>
				<label class="form-text" v-else>
					{{ appointment.timeslot.startDate }}-{{
						appointment.timeslot.endDate
					}}
				</label>
				<br />
				<label class="form-text"
					>{{ appointment.timeslot.startTime }} to
					{{ appointment.timeslot.endTime }}</label
				><br />
			</div>
			<span class="no-notifications" v-if="appointments.length === 0">
				No new notifications
			</span>
			<button id="back-button" v-on:click="backViewDash()">Back</button>
		</div>
	</div>
</template>

<script>
	import axios from "axios";
	import proxy from "../constants.js";

	export default {
		name: "ViewNotifications",
		props: {
			msg: String,
		},
		components: {},

		data() {
			return {
				appointments: [],
			};
		},
		methods: {
			convertForDisplay: function(type) {
				switch (type) {
					case "CarWash":
						return "Car Wash";
					case "Maintenance":
						return "Maintenance";
					case "OilChange":
						return "Oil Change";
					case "TireChange":
						return "Tire Change";
					case "Towing":
						return "Towing";
					case "Inspection":
						return "Inspection";
					case "RoadsideAssistance":
						return "Roadside Assistance";
					case "Checkup":
						return "Checkup";
					default:
						return "Other";
				}
			},

			backViewDash: function() {
				let t = this;
				setTimeout(function() {
					t.$router.push("/dashboard");
				}, 300);
			},
		},
		async mounted() {
			let tempArray1 = [];
			try {
				let notificationResponse = await axios.get(
					proxy.proxy +
						`/api/appointment/notifications/${this.$store.state.user}`
				);

				if (notificationResponse.status !== 200) return;
				tempArray1 = notificationResponse.data;
			} catch (error) {
				console.log(`${error}`);
			}
			let tempArray = [];

			for (let i = 0; i < tempArray1.length; i++) {
				try {
					let timeslotResponse = await axios.get(
						proxy.proxy +
							`/api/appointment/getStartAndEnd/${tempArray1[i].appointmentId}`
					);
					let appt = {
						appointment: tempArray1[i],
						timeslot: timeslotResponse.data,
					};
					tempArray.push(appt);
				} catch (error) {
					console.log(`${error}`);
				}
			}
			this.appointments = tempArray;
		},
	};
</script>

<style scoped>
	.notifications {
		height: 100vh;
		width: 100vw;
		background-image: radial-gradient(whitesmoke 20%, transparent 20%),
			radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
		background-position: 0 0, 7.5vh 7.5vh;
		background-size: 15vh 15vh;
		background-color: rgb(238, 207, 173);
	}

	#notifications-container {
		height: 70vh;
		width: 50vw;
		border-radius: 5vh;
		background-color: whitesmoke;
	}

	.notifications,
	#notifications-container {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		transition: 0.3s;
	}

	.notification,
	.no-notifications {
		background-color: rgb(235, 164, 89);
		margin: 1vh;
		border-radius: 1vh;
		padding: 1vh;
		width: 30vw;
		text-align: center;

		font-size: 3vh;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-weight: 600;
		color: rgb(75, 75, 75);
	}

	.no-notifications {
		margin-top: 8vh;
	}

	#back-button {
		all: unset;
		display: flex;
		align-items: center;
		justify-content: center;
		height: 3vh;
		width: 8vw;
		border-radius: 2vh;
		padding: 1vh;
		background-color: rgb(182, 182, 182);
		margin-top: 1vh;
		margin-bottom: 1vh;
		transition: 0.3s;
		font-size: 3vh;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-weight: 600;
		color: rgb(75, 75, 75);
		border: 0.5vh solid rgb(182, 182, 182);
		text-align: center;
		transition: 0.3s;
		position: absolute;
		top: 1vh;
		right: 1vw;
		animation: changeOpacity 0.3s;
	}

	#back-button:hover {
		background-color: rgb(139, 139, 139);
		color: whitesmoke;
		border-color: rgb(75, 75, 75);
	}

	.myaccount-spacer {
		width: 3vw;
	}

	.notifications-title {
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-size: 4.5vh;
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
