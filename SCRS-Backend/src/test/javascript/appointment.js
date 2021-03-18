import axios from "axios";

const testAppointment = async () => {
  const backend_address = "http://localhost:8080";
  let scoreCounter = 0;
  const numberOfTests = 6;

  // getting all appointments test 1
  try {
    let getTest = await axios.get(
        backend_address + "/api/appointment/getall"
    );

    let responseDataGet = getTest.data;
    let statusCode = getTest.status;
    // TODO test respnseData
  } catch (error) {
    console.log("/getall Test 1 unsuccessful:");
    console.log(`${error}`);
  }

  // getting notifications test 1
  try {
    let getTest = await axios.get(
        backend_address + "/api/appointment/notifications"
    );

    let responseDataGet = getTest.data;
    let statusCode = getTest.status;
    // TODO test respnseData
  } catch (error) {
    console.log("/notifications Test 1 unsuccessful:");
    console.log(`${error}`);
  }

  // booking an appointment Test 1
  try {
    let sentData = {
      // TODO write data to send
    };

    let postTest = await axios.post(
      backend_address + "/api/appointment/book",
      sentData
    );

    let responseDataPost = postTest.data;
    let statusCode = postTest.status;
    // TODO test respnseData
    if (responseDataPost.customerName === sentData.name && statusCode === 200)
      scoreCounter++;
    else {
      console.log("Test 1 unsuccessful:");
      if (responseDataPost.name === sentData.name)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("/book Test 1 unsuccessful:");
    console.log(`${error}`);
  }

  // paying an appointment Test 1
  try {
    let sentData = "appointmentId=1" // TODO write data to send

    let putTest = await axios.put(
        backend_address + "/api/appointment/pay",
        sentData
    );

    let responseDataPut = putTest.data;
    let statusCode = putTest.status;
    // TODO test respnseData
  } catch (error) {
    console.log("/pay Test 1 unsuccessful:");
    console.log(`${error}`);
  }

  // rating an appointment Test 1
  try {
    let sentData = "appointmentId=1&rating=10" // TODO write data to send

    let putTest = await axios.put(
        backend_address + "/api/appointment/rate-appointment",
        sentData
    );

    let responseDataPut = putTest.data;
    let statusCode = putTest.status;
    // TODO test respnseData
  } catch (error) {
    console.log("/rate-appointment Test 1 unsuccessful:");
    console.log(`${error}`);
  }

  // modifying an appointment Test 1
  try {
    let sentData = {
      // TODO write data to send
    };

    let putTest = await axios.put(
        backend_address + "/api/appointment/modifyAppointment",
        sentData
    );

    let responseDataPut = putTest.data;
    let statusCode = putTest.status;
    // TODO test respnseData
  } catch (error) {
    console.log("/modifyAppointment Test 1 unsuccessful:");
    console.log(`${error}`);
  }

  //compiling results
  if (scoreCounter === numberOfTests)
    console.log("All the login tests were successful!");
  else
    console.log(
      `${scoreCounter}/${numberOfTests} login tests were successful.`
    );
  console.log("");
};

const testRateAppointment = async () => {
  const backend_address = "http://localhost:8080";
  let scoreCounter = 0;
  const numberOfTests = 4;
  let customerIdToCheck = -1;
  let appointmentIDToCheck = -1;

  let wipeDatabaseResponse = await axios.delete(
      backend_address + "/api/database/wipe"
  );

  // ------------ Begin setting up state for test ---------------
  //creating customer account
  try {
    let createCustomerData = {
      name: "Bababooey",
      password: "got bababooied",
      email: "babaooey@gmail.com",
      phone: "111-111-1111",
    };

    let createCustomerPost = await axios.post(
        backend_address + "/api/customer/create",
        createCustomerData
    );

    if (createCustomerPost.status !== 200)
    {
      throw "Error: Failed to create customer";
    }
    else
    {
      customerIdToCheck = createCustomerPost.data.customerId;
    }

  } catch (error) {
    console.log(error);
  }

  //booking an appointment

  try {
    let createWorkspaceData = "name=foo";

    let createWorkspacePost = await axios.post(
        backend_address + "/api/workspace/create",
        createWorkspaceData);

    if (createWorkspacePost.status !== 200) {
      throw "Error: Failed to create workspace";
    }

    let createTimeslotData = {
      startDate: Date.now(),
      endDate: Date.now(),
      startTime: +new Date(),
      endTime: +new Date(),
      workspaceId: createWorkspacePost.data.workspaceId,
      techniciansId: [ ]
    };

    let createTimeslotPost = await axios.post(
        backend_address + "/api/timeslot/create",
        createTimeslotData);

    if (createTimeslotPost.status !== 200)
    {
      throw "Error: Failed to create timeslot";
    }

    let createAppointmentData = {
      appointmentType: "CarWash",
      service: "beep",
      note: "beep",
      paid: "false",
      customerId: customerIdToCheck,
      timeslotsId: [
        createTimeslotPost.data.timeslotId
      ],
    };

    let createAppointmentPost = await axios.post(
        backend_address + "/api/appointment/book",
        createAppointmentData
    );

    if (createAppointmentPost.status !== 200) throw "Booking appointment failed";
    appointmentIDToCheck = createAppointmentPost.data.appointmentId;

  } catch (error) {
    console.log(error);
  }

  // ------------ End setting up state for test ---------------

  // Rate appointment
  try {
    // give the appointment we just created a rating of 10
    let rateAppointmentData = `appointmentId=${appointmentIDToCheck}&rating=10`;

    let rateAppointmentResponse = await axios.put(
        backend_address + "/api/appointment/rate",
        rateAppointmentData
    );

    if (rateAppointmentResponse.status === 200 && rateAppointmentResponse.data.rating === 10){
      scoreCounter++;
    }
    else {
      console.log("Test FAILED: ");
      if (rateAppointmentResponse.status !== 200)
      {
        console.log("\tUnexpected status code returned");
      }
      else if (rateAppointmentResponse.data.rating !== 10)
      {
        console.log(`\tUnexpected rating returned ${rateAppointmentResponse.data.rating}`);
      }
    }
  } catch (error) {
    console.log(error);
  }

  // -------------- Test Exceptions --------------------

  // Rating appointment with invalid rating value
  try {
    // give the appointment we just created a rating of -1
    let rateAppointmentData = `appointmentId=${appointmentIDToCheck}&rating=-1`;

    let rateAppointmentResponse = await axios.put(
        backend_address + "/api/appointment/rate",
        rateAppointmentData
    );

    if (rateAppointmentResponse.status === 200)
    {
      scoreCounter++;
      console.log("Passed -1 rating test");
    }
    else {
      console.log("Test FAILED:");
      console.log(`\tRating an appointment with invalid rating should not return 200 : ${rateAppointmentResponse.data}`);
    }
  } catch (error) {
    scoreCounter++;
  }

  try {
    // give the appointment we just created a rating of 100
    let rateAppointmentData = `appointmentId=${appointmentIDToCheck}&rating=100`;

    let rateAppointmentResponse = await axios.put(
        backend_address + "/api/appointment/rate",
        rateAppointmentData
    );

    if (rateAppointmentResponse.status === 200)
    {
      console.log("Test FAILED:");
      console.log(`\tRating an appointment with invalid rating should not return 200 : ${rateAppointmentResponse.data}`);
    }

  } catch (e)
  {
    scoreCounter++;
  }

  // Rate invalid appointment
  try {
    // give the appointment we just created a rating of 10
    let rateAppointmentData = `appointmentId=-1&rating=10`;

    let rateAppointmentResponse = await axios.put(
        backend_address + "/api/appointment/rate",
        rateAppointmentData
    );

    if (rateAppointmentResponse.status === 200)
    {
      console.log("Test FAILED:");
      console.log(`\tRating an invalid appointment should not return 200: ${rateAppointmentResponse.data}`);
    }

  } catch (error) {
    scoreCounter++;
  }


  //compiling results
  if (scoreCounter === numberOfTests)
    console.log("All the rate appointment tests were successful!");
  else
    console.log(
        `${scoreCounter}/${numberOfTests} rate appointment tests were successful.`
    );
}


export { testAppointment, testRateAppointment};
