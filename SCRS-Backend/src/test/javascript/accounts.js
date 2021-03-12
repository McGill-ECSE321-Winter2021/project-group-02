import axios from "axios";

const testingAccounts = async () => {
  const backend_address = "http://localhost:8000";

  //example of a get request from front-end

  let getTest = await axios.get(backend_address + "/api/somegetroute");

  let responseDataGet = getTest.data;

  //example of a post request from front-end

  let sentData = {
    name: "bababooey",
    you: "got bababooied",
  };

  let postTest = await axios.post(
    backend_address + "/api/somepostroute",
    sentData
  );

  let responseDataPost = postTest.data;

  //example of a delete request from the front-end

  const id = 1;

  let updateTest = await axios.put(
    backend_address + `/api/someupdateroute/${id}`,
    sentData
  );

  let responseDataUpdate = updateTest.data;

  //example of a delete request from the front-end

  let deleteTest = await axios.delete(
    backend_address + "/api/somedeleteroute",
    {
      id,
    }
  );

  let responseDataDelete = updateTest.data;
};

export default testingAccounts;
