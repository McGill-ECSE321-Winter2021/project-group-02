import axios from "axios";

const testLogin = async () => {
  const backend_address = "http://localhost:8080";
  let scoreCounter = 0;
  const numberOfTests = 11;

  //wiping the database
  try {
    let deleteTest = await axios.delete(backend_address + "/api/database/wipe");

    let statusCode = deleteTest.status;
    if (statusCode === 200) scoreCounter++;
    else {
      console.log("Test 1 unsuccessful:");
      console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 1 unsuccessful:");
    console.log(`${error}`);
  }

  //testing customer login with non-existing account
  try {
    let sentData = "email=babaooey@gmail.com&password=got bababooied";

    let postTest = await axios.post(
      backend_address + "/api/login/customer",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    if (responseDataPost === false && statusCode === 200) scoreCounter++;
    else {
      console.log("Test 2 unsuccessful:");
      if (responseDataPost !== false)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 2 unsuccessful:");
    console.log(`${error}`);
  }

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
      console.log("Test 3 unsuccessful:");
      if (responseDataPost.name === sentData.name)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 3 unsuccessful:");
    console.log(`${error}`);
  }

  //creating assistant account
  try {
    let sentData = {
      name: "Bababooey",
      password: "got bababooied",
      email: "babaooey@gmail.com",
      phone: "111-111-1111",
    };

    let postTest = await axios.post(
      backend_address + "/api/assistant/create",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    if (responseDataPost.assistantName === sentData.name && statusCode === 200)
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

  //testing assistant login
  try {
    let sentData = "email=babaooey@gmail.com&password=got bababooied";

    let postTest = await axios.post(
      backend_address + "/api/login/assistant",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    if (responseDataPost === true && statusCode === 200) scoreCounter++;
    else {
      console.log("Test 5 unsuccessful:");
      if (responseDataPost !== true)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 5 unsuccessful:");
    console.log(`${error}`);
  }

  //creating technician account
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
    if (responseDataPost.technicianName === sentData.name && statusCode === 200)
      scoreCounter++;
    else {
      console.log("Test 6 unsuccessful:");
      if (responseDataPost.name === sentData.name)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 6 unsuccessful:");
    console.log(`${error}`);
  }

  //testing customer login
  try {
    let sentData = "email=babaooey@gmail.com&password=got bababooied";

    let postTest = await axios.post(
      backend_address + "/api/login/customer",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    if (responseDataPost === true && statusCode === 200) scoreCounter++;
    else {
      console.log("Test 7 unsuccessful:");
      if (responseDataPost !== true)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 7 unsuccessful:");
    console.log(`${error}`);
  }

  //testing technician login
  try {
    let sentData = "email=babaooey@gmail.com&password=got bababooied";

    let postTest = await axios.post(
      backend_address + "/api/login/technician",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    if (responseDataPost === true && statusCode === 200) scoreCounter++;
    else {
      console.log("Test 8 unsuccessful:");
      if (responseDataPost !== true)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 8 unsuccessful:");
    console.log(`${error}`);
  }

  //testing customer login with wrong password
  try {
    let sentData = "email=babaooey@gmail.com&password=got rick rolled";

    let postTest = await axios.post(
      backend_address + "/api/login/customer",
      sentData
    );

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    if (responseDataPost === false && statusCode === 200) scoreCounter++;
    else {
      console.log("Test 9 unsuccessful:");
      if (responseDataPost !== false)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 9 unsuccessful:");
    console.log(`${error}`);
  }

  //testing customer login with wrong password
  try {
    let postTest = await axios.get(backend_address + "/api/login/logout");

    let responseDataPost = postTest.data; //do something with this for tests
    let statusCode = postTest.status;
    if (responseDataPost === true && statusCode === 200) scoreCounter++;
    else {
      console.log("Test 10 unsuccessful:");
      if (responseDataPost !== false)
        console.log(`returned data is erronous: ${responseDataPost}`);
      if (statusCode !== 200)
        console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 10 unsuccessful:");
    console.log(`${error}`);
  }

  //wiping the database
  try {
    let deleteTest = await axios.delete(backend_address + "/api/database/wipe");

    let statusCode = deleteTest.status;
    if (statusCode === 200) scoreCounter++;
    else {
      console.log("Test 11 unsuccessful:");
      console.log(`status code not as expected: ${statusCode}`);
    }
  } catch (error) {
    console.log("Test 11 unsuccessful:");
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

export default testLogin;
