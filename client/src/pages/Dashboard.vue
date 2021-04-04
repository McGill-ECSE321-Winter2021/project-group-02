<template>
	<div class="dashboard">
		<button id="logout-button" @click="logout()">Logout</button>
		<div
			v-if="this.$store.state.userType === 'assistant'"
			id="dashboard-container"
		>
			<div id="dashboard-change-opacity">
				<AssistantDashboard />
			</div>
		</div>
		<div
			v-else-if="this.$store.state.userType === 'technician'"
			id="dashboard-container"
		>
			<div id="dashboard-change-opacity">
				<TechnicianDashboard />
			</div>
		</div>
		<div v-else id="dashboard-container">
			<div id="dashboard-change-opacity">
				<CustomerDashboard />
			</div>
		</div>
	</div>
</template>

<script>
	import AssistantDashboard from "./components/AssistantDashboard";
	import TechnicianDashboard from "./components/TechnicianDashboard";
	import CustomerDashboard from "./components/CustomerDashboard";
	import proxy from "../constants.js";
	import axios from "axios";
	import { mapActions } from "vuex";

	export default {
		name: "Dashboard",
		props: {
			msg: String,
		},
		components: {
			AssistantDashboard,
			TechnicianDashboard,
			CustomerDashboard,
		},
		data() {
			return {};
		},
		methods: {
			...mapActions(["setUser"]),
			logout: function() {
				this.setUser({ user: -1, userType: "" });
				document.getElementById("dashboard-change-opacity").style.opacity = 0;
				document.getElementById("logout-button").style.opacity = 0;
				document.getElementById("dashboard-container").style.height = "50vh";
				document.getElementById("dashboard-container").style.width = "50vw";
				let vm = this;
				setTimeout(function() {
					vm.$router.push("/");
				}, 300);
			},
		},
		mounted() {
			if (this.$store.state.user === -1) this.$router.push("/");
		},
	};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
	.dashboard {
		height: 100vh;
		width: 100vw;
		background-image: radial-gradient(whitesmoke 20%, transparent 20%),
			radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
		background-position: 0 0, 7.5vh 7.5vh;
		background-size: 15vh 15vh;
		background-color: rgb(238, 207, 173);
	}

	#logout-button {
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

	#logout-button:hover {
		background-color: rgb(139, 139, 139);
		color: whitesmoke;
		border-color: rgb(75, 75, 75);
	}

	#dashboard-change-opacity {
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

	#dashboard-container {
		height: 80vh;
		width: 80vw;
		border-radius: 5vh;
		background-color: whitesmoke;
		transition: 0.3s;
	}

	.dashboard,
	#dashboard-container,
	.dashboard-assistant-container,
	.dashboard-technician-container,
	.dashboard-customer-container {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
	}

	#a,
	#b,
	#c,
	#d {
		border-radius: 5vh;
		height: 40vh;
		width: 40vw;
	}

	#a {
		grid-row: 1/2;
		grid-column: 1/2;
	}

	#b {
		grid-row: 1/2;
		grid-column: 2/3;
	}

	#c {
		grid-row: 2/3;
		grid-column: 1/2;
	}

	#d {
		grid-row: 2/3;
		grid-column: 2/3;
	}
</style>
