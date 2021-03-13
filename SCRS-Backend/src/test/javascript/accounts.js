import axios from "axios";

const templates = async () => {
  const backend_address = "http://localhost:8080";
  let counter = 0;

  //example of a get request from front-end
  try {
    let getTest = await axios.get(backend_address + "/api/somegetroute");

    let responseDataGet = getTest.data; //do something with this for tests
  } catch (err) {
    console.error(err.response.data.error); //or any other message you would want
  }

  //example of a post request from front-end

  try {
    let sentData = {
      name: "bababooey",
      you: "got bababooied",
    };

    let postTest = await axios.post(
      backend_address + "/api/somepostroute",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
  } catch (err) {
    console.error(err.response.data.error); //or any other message you would want
  }

  //example of an update request from the front-end

  try {
    const id = 1;

    let updateTest = await axios.put(
      backend_address + `/api/someupdateroute/${id}`,
      sentData
    );

    let responseDataUpdate = updateTest.data; //do something with this for tests
  } catch (err) {
    console.error(err.response.data.error); //or any other message you would want
  }

  //example of a delete request from the front-end

  try {
    const id = 1;

    let sentData = {
      name: "bababooey",
      you: "got bababooied",
    };

    let deleteTest = await axios.delete(
      backend_address + `/api/somedeleteroute/${id}`
    );

    let responseDataDelete = deleteTest.data; //do something with this for tests
  } catch (err) {
    console.error(err.response.data.error); //or any other message you would want
  }
};

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
      console.log(`returned data may be erronous: ${responseDataPost}`);
      console.log(`status code may not be as expected: ${statusCode}`);
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
      console.log(`returned data may be erronous: ${responseDataPost}`);
      console.log(`status code may not be as expected: ${statusCode}`);
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
      console.log(`returned data may be erronous: ${responseDataPost}`);
      console.log(`status code may not be as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log(error);
  }

  if (scoreCounter === numberOfTests) console.log("All tests were successful!");
  else console.log(`${scoreCounter}/${numberOfTests} tests were successful.`);
};

export default testingAccounts;
