import axios from "axios";

const testingAccounts = async () => {
  const backend_address = "http://localhost:8080";
  let scoreCounter = 0;
  const numberOfTests = 3;

  //testing customer login
  try {
    let sentData = {
      name: "bababooey",
      password: "got bababooied",
    };

    let postTest = await axios.post(
      backend_address + "/api/login/customer",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    if (responseDataPost === true && statusCode === 200) scoreCounter++;
    else {
      console.log("Test 1 unsuccessful:");
      if (responseDataPost !== true)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log(error);
  }

  //testing assistant login
  try {
    let sentData = {
      name: "bababooey",
      password: "got bababooied",
    };

    let postTest = await axios.post(
      backend_address + "/api/login/assistant",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    if (responseDataPost === true && statusCode === 200) scoreCounter++;
    else {
      console.log("Test 1 unsuccessful:");
      if (responseDataPost !== true)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log(error);
  }

  //testing technician login
  try {
    let sentData = {
      name: "bababooey",
      password: "got bababooied",
    };

    let postTest = await axios.post(
      backend_address + "/api/login/technician",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    if (responseDataPost === true && statusCode === 200) scoreCounter++;
    else {
      console.log("Test 1 unsuccessful:");
      if (responseDataPost !== true)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log(error);
  }

  if (scoreCounter === numberOfTests)
    console.log("All the login tests were successful!");
  else console.log(`${scoreCounter}/${numberOfTests} tests were successful.`);
};

export default testingAccounts;
