import axios from "axios";

const testAssignTimeslotToTechnician = async () => {
  const backendAddress = "https://scrs-backend-1.herokuapp.com";
  let scoreCounter = 0;
  let technicianIdToCheck = 3; // will update this value with the created technician
  let timeslotIdToAssign = -1;

  /*

  try {
    let sentData = {
      name: "Bababooey",
      password: "password",
      email: "admin@scrs.ca",
      phone: "111-111-1111",
    };

    let postTest = await axios.post(
      backendAddress + "/api/assistant/create",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    id = responseDataPost.assistantId;
    if (responseDataPost.assistantName === sentData.name && statusCode === 200)
      scoreCounter++;
    else {
      console.log("Test 1 unsuccessful:");
      if (responseDataPost.name === sentData.name)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 1 unsuccessful:");
    console.log(`${error}`);
  }
  */

  // Set up a workspace and corresponding timeslot for test
  try {
    let createWorkspaceData = "name=foo";

    let createWorkspaceResponse = await axios.post(
      backendAddress + "/api/workspace/create",
      createWorkspaceData
    );

    if (createWorkspaceResponse.status !== 200) {
      throw "Error: Failed to create workspace";
    }

    let createTimeslotData = {
      startDate: Date.now(),
      endDate: Date.now(),
      startTime: +new Date(),
      endTime: +new Date(),
      workspaceId: createWorkspaceResponse.data.workspaceId,
      techniciansId: [],
    };

    let createTimeslotResponse = await axios.post(
      backendAddress + "/api/timeslot/create",
      createTimeslotData
    );

    if (createTimeslotResponse.status !== 200) {
      throw "Error: Failed to create timeslot for test 'assignTechnicianToTimeslot'";
    }

    timeslotIdToAssign = createTimeslotResponse.data.timeslotId;
  } catch (e) {
    console.log(
      "Error: Failed to create timeslot for test 'assignTechnicianToTimeslot'"
    );
    console.log(e);
  }

  // test assign timeslot to technician
  try {
    let assignTimeslotData = `timeslotId=${timeslotIdToAssign}&technicianId=${technicianIdToCheck}`;

    let assignTimeslotResponse = await axios.put(
      backendAddress + "/api/timeslot/assignTech",
      assignTimeslotData
    );

    if (assignTimeslotResponse.status !== 200) {
      throw "Error: failed to assign timeslot to technician";
    }

    scoreCounter++;
  } catch (e) {
    console.log(
      "Error: Failed to assign timeslot to technician for test 'assignTechnicianToTimeslot'"
    );
    console.log(e);
  }
};

testAssignTimeslotToTechnician();
