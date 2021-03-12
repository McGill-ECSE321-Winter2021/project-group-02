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
};

export default testingAccounts;
