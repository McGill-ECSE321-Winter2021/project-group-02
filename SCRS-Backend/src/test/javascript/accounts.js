import axios from "axios";

const testingAccounts = async () => {
  const backend_address = "http://localhost:8000";
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

export default testingAccounts;
