import axios from "axios";

const testTechnician = async () => {
  const backend_address = "http://localhost:8080";
  let scoreCounter = 0;
  const numberOfTests = 7;
  let id = 0;

  //creating technician account test 1
  try {
    let sentData = {
      name: "Bababooey",
      password: "got bababooied",
      email: "babaooey@gmail.com",
      phone: "111-111-1111",
    };

    let postTest = await axios.post(
      backend_address + "/api/technician/create",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    id = responseDataPost.technicianId;
    if (responseDataPost.technicianName === sentData.name && statusCode === 200)
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

  //creating technician account test 2
  try {
    let sentData = {
      name: "Bababooey",
      password: "got bababooied",
      email: "babaooey@gmail.com",
      phone: "111-111-1111",
    };

    let postTest = await axios.post(
      backend_address + "/api/technician/create",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    if (responseDataPost.technicianName !== sentData.name && statusCode === 208)
      scoreCounter++;
    else {
      console.log("Test 2 unsuccessful:");
      if (responseDataPost.name !== sentData.name)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 208)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 2 unsuccessful:");
    console.log(`${error}`);
  }

  //creating technician account test 3
  try {
    let sentData = null;

    let postTest = await axios.post(
      backend_address + "/api/technician/create",
      sentData
    );
  } catch (error) {
    if (error.response.status === 415) scoreCounter++;
    else {
      console.log("Test 3 unsuccessful:");
      console.log(`${error}`);
    }
  }

  //updating technician account test 1
  try {
    let sentData = {
      scrsUserId: id,
      name: "Bababooey",
      password: "got bababooied",
      email: "babaooey@gmail.com",
      phone: "111-111-1111",
    };

    let postTest = await axios.put(
      backend_address + "/api/technician/update",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    if (responseDataPost.technicianName === sentData.name && statusCode === 200)
      scoreCounter++;
    else {
      console.log("Test 4 unsuccessful:");
      if (responseDataPost.name === sentData.name)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 4 unsuccessful:");
    console.log(`${error}`);
  }

  //updating technician account test 2
  try {
    let sentData = {
      scrsUserId: 0,
      name: "Bababooey",
      password: "got bababooied",
      email: "babaooey@gmail.com",
      phone: "111-111-1111",
    };

    let postTest = await axios.put(
      backend_address + "/api/technician/update",
      sentData
    );
  } catch (error) {
    if (error.response.status === 406) scoreCounter++;
    else {
      console.log("Test 6 unsuccessful:");
      console.log(`${error}`);
    }
  }

  //updating technician account test 3
  try {
    let sentData = null;

    let postTest = await axios.put(
      backend_address + "/api/technician/update",
      sentData
    );
  } catch (error) {
    if (error.response.status === 415) scoreCounter++;
    else {
      console.log("Test 6 unsuccessful:");
      console.log(`${error}`);
    }
  }

  /*
  //deleting account test 1
  try {
    let sentData = `id=-1`;

    let deleteTest = await axios.delete(
      backend_address + "/api/technician/delete",
      sentData
    );
  } catch (error) {
    console.log(error);
    if (error.response.status === 406) scoreCounter++;
    else {
      console.log("Test 8 unsuccessful:");
      console.log(`${error}`);
    }
  }

  //deleting account test 2
  try {
    let sentData = `id=${id}`;

    let deleteTest = await axios.delete(
      backend_address + "/api/technician/delete",
      sentData
    );

    console.log(deleteTest);

    let statusCode = deleteTest.status;
    if (statusCode === 200) scoreCounter++;
    else {
      console.log("Test 7 unsuccessful:");
      console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 7 unsuccessful:");
    console.log(`${error}`);
  }
  */

  //wiping the database
  try {
    let deleteTest = await axios.delete(backend_address + "/api/database/wipe");

    let statusCode = deleteTest.status;
    if (statusCode === 200) scoreCounter++;
    else {
      console.log("Test 9 unsuccessful:");
      console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 9 unsuccessful:");
    console.log(`${error}`);
  }

  //compiling results
  if (scoreCounter === numberOfTests)
    console.log("All the technician tests were successful!");
  else
    console.log(
      `${scoreCounter}/${numberOfTests} technician tests were successful.`
    );
  console.log("");
};

const testAssignTimeslotToTechnician = async () => {
  const backendAddress = "http://localhost:8080";
  let scoreCounter = 0;
  const numberOfTests = 4;
  let technicianIdToCheck = -1; // will update this value with the created technician
  let timeslotIdToAssign = -1;

  console.log("");

  let wipeDatabaseResponse = await axios.delete(
      backendAddress + "/api/database/wipe"
  );

  // Set up a technician for test
  try {
    let createTechnicianData = {
      name: "Foo",
      email: "foo@bar.com",
      phone: "111 111-1111",
    };

    let createTechnicianResponse = await axios.post(
        backendAddress + "/api/technician/create",
        createTechnicianData);

    if (createTechnicianResponse.status !== 200)
      throw "Error: Failed to create technician for test 'assignTechnicianToTimeslot'";

    technicianIdToCheck = createTechnicianResponse.data.technicianId;

    console.log(`CreatedTechnician with id ${createTechnicianResponse.data.technicianId}`);  // todo

  } catch (e)
  {
    console.log("Error: Failed to create technician for test 'assignTechnicianToTimeslot'");
    console.log(e);
  }

  // Set up a workspace and corresponding timeslot for test
  try {
    let createWorkspaceData = "name=foo";

    let createWorkspaceResponse = await axios.post(
        backendAddress + "/api/workspace/create",
        createWorkspaceData);

    if (createWorkspaceResponse.status !== 200) {
      throw "Error: Failed to create workspace";
    }

    let createTimeslotData = {
      startDate: Date.now(),
      endDate: Date.now(),
      startTime: +new Date(),
      endTime: +new Date(),
      workspaceId: createWorkspaceResponse.data.workspaceId,
      techniciansId: [ ]
    };

    let createTimeslotResponse = await axios.post(
        backendAddress + "/api/timeslot/create",
        createTimeslotData);

    if (createTimeslotResponse.status !== 200)
    {
      throw "Error: Failed to create timeslot for test 'assignTechnicianToTimeslot'";
    }


    timeslotIdToAssign = createTimeslotResponse.data.timeslotId;

    console.log(`Created Timeslot with id ${createTimeslotResponse.data.timeslotId}`); // todo

  } catch (e)
  {
    console.log("Error: Failed to create timeslot for test 'assignTechnicianToTimeslot'");
    console.log(e);
  }

  // test assign timeslot to technician
  try
  {
    let assignTimeslotData = `timeslotId=${timeslotIdToAssign}&technicianId=${technicianIdToCheck}`;

    let assignTimeslotResponse = await axios.put(
        backendAddress + "/api/timeslot/assignTech",
        assignTimeslotData);

    if (assignTimeslotResponse.status !== 200)
    {
      throw "Error: failed to assign timeslot to technician";
    }

    scoreCounter++;
  } catch (e)
  {
    console.log("Error: Failed to assign timeslot to technician for test 'assignTechnicianToTimeslot'");
    console.log(e);
  }

  // test assign timeslot to technician who already has that timeslot assigned to them
  try
  {
    let assignTimeslotData = `timeslotId=${timeslotIdToAssign}&technicianId=${technicianIdToCheck}`;

    let assignTimeslotResponse = await axios.put(
        backendAddress + "/api/timeslot/assignTech",
        assignTimeslotData);

    if (assignTimeslotResponse.status === 200)
    {
      console.log("Error: duplicate timeslot assigned to technician");
    }

  } catch (e)
  {
    scoreCounter++;
  }

  // test assign timeslot to technician who doesn't exist
  try
  {
    let assignTimeslotData = `timeslotId=${timeslotIdToAssign}&technicianId=-1`;

    let assignTimeslotResponse = await axios.put(
        backendAddress + "/api/timeslot/assignTech",
        assignTimeslotData);

    if (assignTimeslotResponse.status === 200)
    {
      console.log("Error: assigned timeslot to invalid technician");
    }

  } catch (e)
  {
    scoreCounter++;
  }

  // test assign timeslot which doesn't exist to technician
  try
  {
    let assignTimeslotData = `timeslotId=-1&technicianId=${technicianIdToCheck}`;

    let assignTimeslotResponse = await axios.put(
        backendAddress + "/api/timeslot/assignTech",
        assignTimeslotData);

    if (assignTimeslotResponse.status === 200)
    {
      console.log("Error: assigned invalid timeslot to technician");
    }

  } catch (e)
  {
    scoreCounter++;
  }


  //compiling results
  if (scoreCounter === numberOfTests)
    console.log("All the assign technician to timeslot tests were successful!");
  else
    console.log(
        `${scoreCounter}/${numberOfTests} assign technician to timeslot tests were successful.`
    );
  console.log("");
};

export {testTechnician, testAssignTimeslotToTechnician};
