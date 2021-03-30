<template>
	<div class="assignSchedule">
		<div id="assignSchedule-container" class="assignSchedule-container">
			<H1 id="assignSchedule-generic-title" class="assignSchedule-title"
				>Set Technician Schedule</H1
			>
			<div
				id="assignSchedule-select-container"
				class="assignSchedule-select-container"
			>
				<select
					name="technicians"
					id="assignSchedule-technician-select"
					class="assignSchedule-select"
					v-model="curTechnician"
				>
					<option disabled selected>Select a Technician</option>
					<option v-for="(tech, index) in technicians" :key="index">{{
						tech.technicianName
					}}</option>
				</select>
				<div class="assignSchedule-spacer"></div>
				<select
					name="workspace"
					id="assignSchedule-workspace-select"
					class="assignSchedule-select"
					v-model="curWorkspace"
				>
					<option disabled selected>Select a Workspace</option>
					<option v-for="(ws, index) in workspaces" :key="index">{{
						ws.workspaceName
					}}</option>
				</select>
			</div>
			<div
				id="assignSchedule-button-container"
				class="assignSchedule-button-container"
			>
				<input
					class="assignSchedule-button"
					type="button"
					value="Back"
					v-on:click="backViewDash()"
				/>
			</div>
		</div>
	</div>
</template>

<script>
	import axios from "axios";
	import proxy from "../constants.js";
	export default {
		name: "AssignTechnicianSchedule",
		props: {
			msg: String,
		},
		components: {},
		data() {
			return {
				technicians: [],
				workspaces: [],
				curTechnician: "Select a Technician",
				curWorkspace: "Select a Workspace",
			};
		},
		methods: {
			backViewDash: function() {
				let t = this;
				document.getElementById(
					"assignSchedule-generic-title"
				).style.opacity = 0;
				document.getElementById(
					"assignSchedule-select-container"
				).style.opacity = 0;
				document.getElementById(
					"assignSchedule-button-container"
				).style.opacity = 0;
				setTimeout(function() {
					t.$router.push("/dashboard");
				}, 300);
			},
		},
		async mounted() {
			//TODO UNCOMMENT
			//if (this.$store.state.user !== "assistant") this.$router.push("/");

			try {
				let response = await axios.get(proxy.proxy + "/api/technician/getAll");
				this.technicians = response.data;
				response = await axios.get(proxy.proxy + "/api/workspace/getAll");
				this.workspaces = response.data;
			} catch (error) {
				console.error(error);
			}

			//TODO COMMENT OUT BELOW
			this.technicians = [
				{ technicianName: "bob", technicianId: 3 },
				{ technicianName: "john", technicianId: 4 },
				{ technicianName: "alix", technicianId: 6969 },
			];
			this.workspaces = [
				{ workspaceName: "ws1", workspaceId: 4 },
				{ workspaceName: "ws2", workspaceId: 3 },
				{ workspaceName: "wsAlix", workspaceId: 6969 },
			];
		},
	};
</script>

<style scoped>
	.assignSchedule {
		height: 100vh;
		width: 100vw;
		background-image: radial-gradient(whitesmoke 20%, transparent 20%),
			radial-gradient(rgb(235, 164, 89) 20%, transparent 20%);
		background-position: 0 0, 7.5vh 7.5vh;
		background-size: 15vh 15vh;
		background-color: rgb(238, 207, 173);
	}

	.assignSchedule-container {
		height: 80vh;
		width: 80vw;
		border-radius: 5vh;
		background-color: whitesmoke;
	}

	.assignSchedule,
	.assignSchedule-container,
	.assignSchedule-button-container {
		display: flex;
		align-items: center;
		justify-content: center;
		flex-direction: column;
		transition: 0.3s;
	}

	.assignSchedule-button {
		all: unset;
		display: flex;
		align-items: center;
		justify-content: center;
		height: 3vh;
		width: 8vw;
		border-radius: 2vh;
		padding: 1vh;
		background-color: rgb(235, 164, 89);
		margin-top: 1vh;
		margin-bottom: 1vh;
		transition: 0.3s;
		font-size: 2.5vh;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-weight: 600;
		color: rgb(75, 75, 75);
		border: 0.5vh solid rgb(235, 164, 89);
	}

	.assignSchedule-button:hover {
		background-color: rgb(175, 122, 65);
		color: whitesmoke;
		border-color: rgb(75, 75, 75);
	}

	#assignSchedule-button-container {
		flex-direction: row;
	}

	.assignSchedule-spacer {
		width: 3vw;
	}

	.assignSchedule-title {
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-size: 3vh;
		font-weight: 600;
		color: rgb(59, 58, 58);
		padding-bottom: 3vh;
		animation: changeOpacity 0.3s;
		transition: 0.3s;
	}

	.assignSchedule-select {
		all: unset;
		background-color: rgb(212, 211, 211);
		font-size: 2.5vh;
		margin-top: 0.5vh;
		margin-bottom: 0.5vh;
		padding: 0.5vh;
		padding-left: 4vw;
		padding-right: 4vw;
		border-radius: 2vh;
		font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
		font-weight: 600;
		color: rgb(59, 58, 58);
		transition: 0.3s;
	}

	.assignSchedule-select-container {
		display: flex;
		flex-direction: row;
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