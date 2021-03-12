import axios from "axios";

const testingAccounts = async () => {
  const backend_address = "http://localhost:8000";

  //example of a get request from front-end

  try {
    let getTest = await axios.get(backend_address + "/api/somegetroute");
  } catch (err) {
    console.log(err.response.data.error); //or any other message you would want
  }

  let responseDataGet = getTest.data; //do something with this for tests

  //example of a post request from front-end

  let sentData = {
    name: "bababooey",
    you: "got bababooied",
  };

  try {
    let postTest = await axios.post(
      backend_address + "/api/somepostroute",
      sentData
    );
  } catch (err) {
    console.log(err.response.data.error); //or any other message you would want
  }

  let responseDataPost = postTest.data; //do something with this for tests

  //example of an update request from the front-end

  const id = 1;

  try {
    let updateTest = await axios.put(
      backend_address + `/api/someupdateroute/${id}`,
      sentData
    );
  } catch (err) {
    console.log(err.response.data.error); //or any other message you would want
  }

  let responseDataUpdate = updateTest.data; //do something with this for tests

  //example of a delete request from the front-end

  try {
    let deleteTest = await axios.delete(
      backend_address + `/api/somedeleteroute/${id}`
    );
  } catch (err) {
    console.log(err.response.data.error); //or any other message you would want
  }

  let responseDataDelete = deleteTest.data; //do something with this for tests
};

export default testingAccounts;
