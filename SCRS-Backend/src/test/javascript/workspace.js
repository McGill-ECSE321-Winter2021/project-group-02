import axios from "axios";

const testWorkspace = async () => {
  const backend_address = "http://localhost:8000";
  let scoreCounter = 0;
  const numberOfTests = 7;
  let workspaceIdToCheck = -1;

  let wipeDatabaseResponse = await axios.delete(
    backend_address + "/api/database/wipe"
  );

  // ========== Create a workspace (Test 1) ==========
  try {
    let createWorkspaceData = "name=foo";

    let createWorkspaceResponse = await axios.post(
      backend_address + "/api/workspace/create",
      createWorkspaceData
    );

    if (
      createWorkspaceResponse.status === 200 &&
      createWorkspaceResponse.data.spaceName === "foo"
    ) {
      scoreCounter++;
      workspaceIdToCheck = createWorkspaceResponse.data.workspaceId;
    } else {
      console.log("Test Create a workspace FAILED: ");
      if (createWorkspaceResponse.status !== 200) {
        console.log("\tUnexpected status code returned");
      } else if (createWorkspaceResponse.data.spaceName !== "foo") {
        console.log(
          `\tUnexpected spaceName returned ${createWorkspaceResponse.data.spaceName}`
        );
      }
    }
  } catch (error) {
    console.log("Test Create a workspace FAILED");
    console.log(error);
  }

  // ========== Creating a workspace with an invalid name (Test 2) ==========
  try {
    let createWorkspaceData = null;

    let createWorkspaceResponse = await axios.post(
      backend_address + "/api/workspace/create",
      createWorkspaceData
    );

    console.log("Test Creating a workspace with an invalid name FAILED: ");
    console.log(
      `\tUnexpected status code returned ${createWorkspaceResponse.status}`
    );
    console.log(
      `\tWorkspace returned ${createWorkspaceResponse.data.workspaceId}`
    );
  } catch (error) {
    scoreCounter++;
  }

  // ========== Test 3&4 setups ==========
  let timeslotIdToCheck = -1;
  // create timeslots
  try {
    let createTimeslotData = {
      startDate: Date.now(),
      endDate: Date.now(),
      startTime: +new Date(),
      endTime: +new Date(),
      workspaceId: workspaceIdToCheck,
      techniciansId: [],
    };

    let createTimeslotResponse = await axios.post(
      backend_address + "/api/timeslot/create",
      createTimeslotData
    );

    timeslotIdToCheck = createTimeslotResponse.data.timeslotId;
  } catch (error) {
    console.log("Faild to create timeslot in workspace tests");
  }

  // ========== Get workspace available timeslot (Test 3) ==========
  try {
    let getWorkspaceTimeslotResponse = await axios.get(
      backend_address + `/api/workspace/availabilities/${workspaceIdToCheck}`
    );

    if (
      getWorkspaceTimeslotResponse.status === 200 &&
      getWorkspaceTimeslotResponse.data[0].timeslotId === timeslotIdToCheck
    ) {
      scoreCounter++;
    } else {
      console.log("Test Get workspace available timeslot FAILED: ");
      if (getWorkspaceTimeslotResponse.status !== 200) {
        console.log("\tUnexpected status code returned");
      } else if (
        getWorkspaceTimeslotResponse.data[0].timeslotId === timeslotIdToCheck
      ) {
        console.log(
          `\tUnexpected timeslot returned ${getWorkspaceTimeslotResponse.data[0].timeslotId}`
        );
      }
    }
  } catch (error) {
    console.log("Test Get workspace available timeslot FAILED");
    console.log(error);
  }

  // ========== Get available timeslot of invalid workspace (Test 4) ==========
  try {
    let deleteWorkspaceResponse = await axios.get(
      backend_address + "/api/workspace/availabilities/-1"
    );

    console.log("Test Get available timeslot of invalid workspace FAILED: ");
    console.log(
      `\tUnexpected status code returned ${deleteWorkspaceResponse.status}`
    );
    console.log(
      `\tWorkspace returned ${deleteWorkspaceResponse.data.workspaceId}`
    );
  } catch (error) {
    scoreCounter++;
  }

  // ========== Deleting an workspace with timeslot association (Test 5) ==========
  try {
    let deleteWorkspaceResponse = await axios.delete(
      backend_address + `/api/workspace/delete/${workspaceIdToCheck}`
    );

    console.log(
      "Test Deleting an workspace with timeslot association FAILED: "
    );
    console.log(
      `\tUnexpected status code returned ${deleteWorkspaceResponse.status}`
    );
    console.log(
      `\tWorkspace returned ${deleteWorkspaceResponse.data.workspaceId}`
    );
  } catch (error) {
    scoreCounter++;
  }

  // ========== Test 6&7 setups ==========
  // delete timeslots
  try {
    let deleteTimeslotResponse = await axios.delete(
      backend_address + `/api/timeslot/delete/${timeslotIdToCheck}`
    );
  } catch (error) {
    console.log("Faild to delete timeslot in workspace test");
  }

  // ========== Deleting a workspace (Test 6) ==========
  try {
    let deleteWorkspaceResponse = await axios.delete(
      backend_address + `/api/workspace/delete/${workspaceIdToCheck}`
    );

    if (
      deleteWorkspaceResponse.status === 200 &&
      deleteWorkspaceResponse.data.workspaceId === workspaceIdToCheck
    ) {
      scoreCounter++;
    } else {
      console.log("Test Deleting a workspace FAILED: ");
      if (deleteWorkspaceResponse.status !== 200) {
        console.log("\tUnexpected status code returned");
      } else if (
        deleteWorkspaceResponse.data.workspaceId !== workspaceIdToCheck
      ) {
        console.log(
          `\tUnexpected spaceName returned ${deleteWorkspaceResponse.data.workspaceId}`
        );
      }
    }
  } catch (error) {
    console.log("Test Deleting a workspace FAILED");
    console.log(error);
  }

  // ========== Deleting an invalid workspace (Test 7) ==========
  try {
    let deleteWorkspaceResponse = await axios.delete(
      backend_address + "/api/workspace/delete/-1"
    );

    console.log("Test Deleting an invalid workspace FAILED: ");
    console.log(
      `\tUnexpected status code returned ${deleteWorkspaceResponse.status}`
    );
    console.log(
      `\tWorkspace returned ${deleteWorkspaceResponse.data.workspaceId}`
    );
  } catch (error) {
    scoreCounter++;
  }

  // ========== compiling results ==========
  if (scoreCounter === numberOfTests)
    console.log("All the workspace tests were successful!");
  else
    console.log(
      `${scoreCounter}/${numberOfTests} workspace tests were successful.`
    );
  console.log("");
};

export default testWorkspace;
