import axios from "axios";

const testAppointmentBookingAndPayment = async () => {
  const backend_address = "http://localhost:8080";
  let scoreCounter = 0;
  const numberOfTests = 8;
  let customerIdToCheck = -1;
  let timeslotIdToCheck = -1;
  let appointmentDataToCheck = null;

  // ========== test setups ==========
  try {
    let wipeDatabaseResponse = await axios.delete(
      backend_address + "/api/database/wipe"
    );

    // create customer
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
    customerIdToCheck = createCustomerPost.data.customerId;

    // create workspace
    let createWorkspaceData = "name=foo";
    let createWorkspacePost = await axios.post(
      backend_address + "/api/workspace/create",
      createWorkspaceData
    );

    // create timeslot
    let createTimeslotData = {
      startDate: Date.now(),
      endDate: Date.now(),
      startTime: +new Date(),
      endTime: +new Date(),
      workspaceId: createWorkspacePost.data.workspaceId,
      techniciansId: [],
    };
    let createTimeslotPost = await axios.post(
      backend_address + "/api/timeslot/create",
      createTimeslotData
    );
    timeslotIdToCheck = createTimeslotPost.data.timeslotId;
  } catch (error) {
    console.log("Setup for testAppointment FAILED");
  }

  // ========== booking an appointment ==========
  try {
    let bookAppointmentData = {
      appointmentType: "CarWash",
      service: "Car wash",
      note: "Hello world",
      customerId: customerIdToCheck,
      timeslotsId: [timeslotIdToCheck],
    };

    let bookAppointmentResponse = await axios.post(
      backend_address + "/api/appointment/book",
      bookAppointmentData
    );

    if (bookAppointmentResponse.status === 200) {
      appointmentDataToCheck = bookAppointmentResponse.data;
      if (
        appointmentDataToCheck.appointmentType ===
          bookAppointmentData.appointmentType &&
        appointmentDataToCheck.customerId === bookAppointmentData.customerId
      ) {
        scoreCounter++;
      } else {
        console.log("Test booking an appointment unsuccessful:");
        console.log(`returned data is erronous: ${appointmentDataToCheck}`);
      }
    } else {
      console.log("Test booking an appointment unsuccessful:");
      console.log(
        `status code not as expected: ${bookAppointmentResponse.status}`
      );
    }
  } catch (error) {
    console.log("Test booking an appointment unsuccessful:");
    console.log(error);
  }

  // ========== getting all appointments ==========

  try {
    let getAppointmentResponse = await axios.get(
      backend_address + `/api/appointment/getall/${customerIdToCheck}`
    );
    if (
      getAppointmentResponse.data.length === 1 &&
      getAppointmentResponse.status === 200
    )
      scoreCounter++;
    else {
      console.log("Test get all appointments unsuccessful:");
      if (getAppointmentResponse.length !== 1)
        console.log(
          `The number of appointment is wrong: ${getAppointmentResponse.data.length}`
        );
      if (getAppointmentResponse.status !== 200)
        console.log(
          `status code not as expected: ${getAppointmentResponse.status}`
        );
    }
  } catch (error) {
    console.log("Test get all appointments unsuccessful:");
    console.log(error);
  }

  // ========== getting all appointments with an invalid id ==========

  try {
    let getAppointmentResponse = await axios.get(
      backend_address + `/api/appointment/getall/${-1}`
    );
  } catch (error) {
    if (error.response.status === 406) scoreCounter++;
    else {
      console.log("Test get all appointments with invalid id unsuccessful:");
      console.log(`${error}`);
    }
  }

  // ========== getting appointments in the next week ==========

  try {
    let getNotificationsResponse = await axios.get(
      backend_address + `/api/appointment/notifications/${customerIdToCheck}`
    );
    if (
      getNotificationsResponse.data.length === 0 &&
      getNotificationsResponse.status === 200
    )
      scoreCounter++;
    else {
      console.log("Test get all appointments unsuccessful:");
      if (getNotificationsResponse.length !== 0)
        console.log(
          `The number of appointment is wrong: ${getNotificationsResponse.data.length}`
        );
      if (getNotificationsResponse.status !== 200)
        console.log(
          `status code not as expected: ${getNotificationsResponse.status}`
        );
    }
  } catch (error) {
    console.log("Test get all appointments unsuccessful:");
    console.log(error);
  }

  // ========== getting all notifications with an invalid id ==========

  try {
    let getAppointmentResponse = await axios.get(
      backend_address + `/api/appointment/notifications/${-1}`
    );
  } catch (error) {
    if (error.response.status === 406) scoreCounter++;
    else {
      console.log("Test get all notifications with invalid id unsuccessful:");
      console.log(`${error}`);
    }
  }

  // ========== booking an appointment with invalid timeslot  ==========
  try {
    let bookAppointmentData = {
      appointmentType: "CarWash",
      service: "Car wash",
      note: "Hello world",
      customerId: customerIdToCheck,
      timeslotsId: [-1],
    };

    let bookAppointmentResponse = await axios.post(
      backend_address + "/api/appointment/book",
      bookAppointmentData
    );
    if (bookAppointmentResponse.status === 200) {
      console.log(
        "Error: booking an appointment with an invalid timeslot should be impossible"
      );
    }
  } catch (e) {
    scoreCounter++;
  }

  // ========== booking an appointment with invalid customer  ==========
  try {
    // create timeslot
    let createTimeslotData = {
      startDate: Date.now(),
      endDate: Date.now(),
      startTime: +new Date(),
      endTime: +new Date(),
      workspaceId: createWorkspacePost.data.workspaceId,
      techniciansId: [],
    };

    let createTimeslotPost = await axios.post(
      backend_address + "/api/timeslot/create",
      createTimeslotData
    );

    let bookAppointmentData = {
      appointmentType: "CarWash",
      service: "Car wash",
      note: "Hello world",
      customerId: -1,
      timeslotsId: [createTimeslotPost.data.timeslotId],
    };

    let bookAppointmentResponse = await axios.post(
      backend_address + "/api/appointment/book",
      bookAppointmentData
    );
    if (bookAppointmentResponse.status === 200) {
      console.log(
        "Error: booking an appointment with an invalid timeslot should be impossible"
      );
    }
  } catch (e) {
    scoreCounter++;
  }

  // ========== paying an appointment ==========
  try {
    if (appointmentDataToCheck.paymentStatus) {
      console.log("Test paying an appointment unsuccessful:");
      console.log("Appointment is already payed");
    } else {
      let paymentData = `appointmentId=${appointmentDataToCheck.appointmentId}`;

      let paymentResponse = await axios.put(
        backend_address + "/api/appointment/pay",
        paymentData
      );

      if (
        paymentResponse.status === 200 &&
        paymentResponse.data.paymentStatus
      ) {
        scoreCounter++;
      } else {
        console.log("Test paying an appointment FAILED: ");
        if (paymentResponse.status !== 200) {
          console.log("\tUnexpected status code returned");
        } else if (!paymentResponse.data.paymentStatus) {
          console.log(
            `\tAppointment was not payed returned ${paymentResponse}`
          );
        }
      }
    }
  } catch (error) {
    console.log("Test paying an appointment unsuccessful:");
    console.log(error);
  }

  // ========== compiling results ==========
  if (scoreCounter === numberOfTests)
    console.log("All the booking tests were successful!");
  else
    console.log(
      `${scoreCounter}/${numberOfTests} booking tests were successful.`
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

    if (createCustomerPost.status !== 200) {
      throw "Error: Failed to create customer";
    } else {
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
      createWorkspaceData
    );

    if (createWorkspacePost.status !== 200) {
      throw "Error: Failed to create workspace";
    }

    let createTimeslotData = {
      startDate: Date.now(),
      endDate: Date.now(),
      startTime: +new Date(),
      endTime: +new Date(),
      workspaceId: createWorkspacePost.data.workspaceId,
      techniciansId: [],
    };

    let createTimeslotPost = await axios.post(
      backend_address + "/api/timeslot/create",
      createTimeslotData
    );

    if (createTimeslotPost.status !== 200) {
      throw "Error: Failed to create timeslot";
    }

    let createAppointmentData = {
      appointmentType: "CarWash",
      service: "beep",
      note: "beep",
      paid: "false",
      customerId: customerIdToCheck,
      timeslotsId: [createTimeslotPost.data.timeslotId],
    };

    let createAppointmentPost = await axios.post(
      backend_address + "/api/appointment/book",
      createAppointmentData
    );

    if (createAppointmentPost.status !== 200)
      throw "Booking appointment failed";
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

    if (
      rateAppointmentResponse.status === 200 &&
      rateAppointmentResponse.data.rating === 10
    ) {
      scoreCounter++;
    } else {
      console.log("Test FAILED: ");
      if (rateAppointmentResponse.status !== 200) {
        console.log("\tUnexpected status code returned");
      } else if (rateAppointmentResponse.data.rating !== 10) {
        console.log(
          `\tUnexpected rating returned ${rateAppointmentResponse.data.rating}`
        );
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

    if (rateAppointmentResponse.status === 200) {
      scoreCounter++;
      console.log("Passed -1 rating test");
    } else {
      console.log("Test FAILED:");
      console.log(
        `\tRating an appointment with invalid rating should not return 200 : ${rateAppointmentResponse.data}`
      );
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

    if (rateAppointmentResponse.status === 200) {
      console.log("Test FAILED:");
      console.log(
        `\tRating an appointment with invalid rating should not return 200 : ${rateAppointmentResponse.data}`
      );
    }
  } catch (e) {
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

    if (rateAppointmentResponse.status === 200) {
      console.log("Test FAILED:");
      console.log(
        `\tRating an invalid appointment should not return 200: ${rateAppointmentResponse.data}`
      );
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
  console.log("");
};

const testModifyAppointment = async () => {
  const backend_address = "http://localhost:8080";
  let scoreCounter = 0;
  const numberOfTests = 2;
  let customerIdToCheck = -1;
  let appointmentToModify = {};

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

    if (createCustomerPost.status !== 200) {
      throw "Error: Failed to create customer";
    } else {
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
      createWorkspaceData
    );

    if (createWorkspacePost.status !== 200) {
      throw "Error: Failed to create workspace";
    }

    let createTimeslotData = {
      startDate: Date.now(),
      endDate: Date.now(),
      startTime: +new Date(),
      endTime: +new Date(),
      workspaceId: createWorkspacePost.data.workspaceId,
      techniciansId: [],
    };

    let createTimeslotPost = await axios.post(
      backend_address + "/api/timeslot/create",
      createTimeslotData
    );

    if (createTimeslotPost.status !== 200) {
      throw "Error: Failed to create timeslot";
    }

    let createAppointmentData = {
      appointmentType: "CarWash",
      service: "beep",
      note: "beep",
      paid: "false",
      customerId: customerIdToCheck,
      timeslotsId: [createTimeslotPost.data.timeslotId],
    };

    let createAppointmentPost = await axios.post(
      backend_address + "/api/appointment/book",
      createAppointmentData
    );

    if (createAppointmentPost.status !== 200)
      throw "Booking appointment failed";
    appointmentToModify = createAppointmentPost.data;
  } catch (error) {
    console.log(error);
  }

  // ------------ End setting up state for test ---------------

  // Modify appointment
  try {
    // give the appointment we just created a rating of 10
    let modifyAppointmentData = appointmentToModify;
    modifyAppointmentData.rating = 10;
    modifyAppointmentData.note = "Modified appointment note";

    let modifyAppointmentResponse = await axios.put(
      backend_address + "/api/appointment/modifyAppointment",
      modifyAppointmentData
    );

    if (
      modifyAppointmentResponse.status === 200 &&
      modifyAppointmentResponse.data.note === "Modified appointment note"
    ) {
      scoreCounter++;
    } else {
      console.log("Test FAILED: ");
      if (modifyAppointmentResponse.status !== 200) {
        console.log("\tUnexpected status code returned");
      }
      console.log(
        `\tUnexpected note returned ${modifyAppointmentResponse.data.note}`
      );
    }
  } catch (error) {
    console.log(error);
  }

  try {
    let modifyAppointmentData = appointmentToModify;
    modifyAppointmentData.rating = 5;
    modifyAppointmentData.appointmentId = -1;

    let modifyAppointmentResponse = await axios.put(
      backend_address + "/api/appointment/modifyAppointment",
      modifyAppointmentData
    );

    if (modifyAppointmentResponse.status === 200)
      console.log("Error: Modified an invalid appointment");
  } catch (error) {
    scoreCounter++;
  }

  //compiling results
  if (scoreCounter === numberOfTests)
    console.log("All the modify appointment tests were successful!");
  else
    console.log(
      `${scoreCounter}/${numberOfTests} modify appointment tests were successful.`
    );
  console.log("");
};

export {
  testAppointmentBookingAndPayment,
  testRateAppointment,
  testModifyAppointment,
};
