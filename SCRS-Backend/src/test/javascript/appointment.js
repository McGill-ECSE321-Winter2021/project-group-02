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

export default testAppointment;
