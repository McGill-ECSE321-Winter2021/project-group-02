import axios from "axios";

const testAppointment = async () => {
  const backend_address = "http://localhost:8080";
  let scoreCounter = 0;
  const numberOfTests = 9;

  console.log("");

  //creating customer account
  try {
    let sentData = {
      name: "Bababooey",
      password: "got bababooied",
      email: "babaooey@gmail.com",
      phone: "111-111-1111",
    };

    let postTest = await axios.post(
      backend_address + "/api/customer/create",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
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
    console.log(error);
  }

  //booking an appointment
  try {
    let sentData = {
      appointmentType: "CarWash",
      service: "beep",
      note: "beep",
      paid: "false",
      timeslots: [
        {
          startDate: Date.now(),
          endDate: Date.now(),
          startTime: Date.now().getTime(),
          endTime: Date.now().getTime(),
          workspace: {
            spaceType: "beep",
          },
        },
      ],
    };

    let postTest = await axios.post(
      backend_address + "/api/appointment/book",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
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
    console.log(error);
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

export default testAppointment;
