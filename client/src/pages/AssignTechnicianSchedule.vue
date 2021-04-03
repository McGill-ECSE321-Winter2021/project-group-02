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
					id="assignSchedule-technician-select"
					class="assignSchedule-select"
					v-model="curTechnician"
					@change="dropdown()"
				>
					<option disabled selected>Select a Technician</option>
					<option
						v-for="(technician, index) in technicians"
						:key="index"
						:value="technician"
						>{{ technician.technicianName }}</option
					>
				</select>
				<div class="assignSchedule-spacer"></div>
				<select
					id="assignSchedule-workspace-select"
					class="assignSchedule-select"
					v-model="curWorkspace"
					@change="dropdown()"
				>
					<option disabled selected>Select a Workspace</option>
					<option
						v-for="(workspace, index) in workspaces"
						:key="index"
						:value="workspace"
						>{{ workspace.spaceName }}</option
					>
				</select>
			</div>

      <div id="assignSchedule-timeslot-container"
            v-if="sel">
        <label class="assignSchedule-label">Timeslot</label>
        <div id="timeslot-container">
          <div
              class="timeslot"
              v-for="(timeslot, index) in timeslots"
              :key="index"
              :id="timeslot.timeslotId"
              @click="timeslotSelect(timeslot)"
          >
            <label class="modify-appointment-label">Date: {{ timeslot.startDate }}</label>
            <div class="timeslot-spacer"></div>
            <label class="modify-appointment-label"
            >Time: {{ timeslot.startTime.slice(0, 5) }} to
              {{ timeslot.endTime.slice(0, 5) }}</label
            >
          </div>
        </div>
      </div>

			<div
				id="assignSchedule-button-container"
				class="assignSchedule-button-container"
			>
				<button class="assignSchedule-button" v-on:click="backViewDash()">
					Back
				</button>
				<div class="assignSchedule-spacer" v-if="sel"></div>
				<button
					class="assignSchedule-button"
					v-if="sel && selectTimeslotId.length !== 0"
					v-on:click="assignTimeslots()"
				>
					Submit
				</button>
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
				sel: false,
				timeslots: [],
        selectTimeslotId: [],
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
        document.getElementById(
            "assignSchedule-timeslot-container"
        ).style.opacity = 0;
				setTimeout(function() {
					t.$router.push("/dashboard");
				}, 300);
			},
			fetchTimeslots: async function() {
				try {
					let response = await axios.get(
						proxy.proxy +
							`/api/workspace/availabilities/${this.curWorkspace.workspaceId}`
					);
					this.timeslots = response.data;
				} catch (error) {
					console.error(error);
				}
			},
			dropdown: function() {
				if (
					this.curTechnician === "Select a Technician" ||
					this.curWorkspace === "Select a Workspace"
				) {
					this.sel = false;
					return;
				}
				this.fetchTimeslots();
				this.sel = true;
			},
			assignTimeslots: async function() {
        let assignTimeslotData = `timeslotId=${this.selectTimeslotId[0]}&technicianId=${this.curTechnician.technicianId}`;

        try {
          await axios.put(
              proxy.proxy + "/api/timeslot/assignTech",
              assignTimeslotData
          );
        } catch (error) {
          console.log(error);
        }

        this.backViewDash();
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
        let timeslotComponent = document.getElementById(this.selectTimeslotId[0]);
        timeslotComponent.style.backgroundColor = "rgb(175, 122, 65)";
        timeslotComponent.style.color = "whitesmoke";
        timeslotComponent.style.borderColor = "rgb(75, 75, 75)";
      },
		},
		async mounted() {
			if (this.$store.state.userType !== "assistant") this.$router.push("/dashboard");

			try {
				let response = await axios.get(proxy.proxy + "/api/technician/getAll");
				this.technicians = response.data;

				response = await axios.get(proxy.proxy + "/api/workspace/getall");
				this.workspaces = response.data;
			} catch (error) {
				console.error(error);
			}
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

  .assignSchedule-label {
    font-family: Cambria, Cochin, Georgia, Times, "Times New Roman", serif;
    font-size: 3vh;
    font-weight: 500;
    color: rgb(59, 58, 58);
    transition: 0.3s;
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

  #assignSchedule-timeslot-container {
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

	@keyframes changeheight {
		from {
			height: 0vh;
		}

		to {
			height: 55vh;
		}
	}
</style>
