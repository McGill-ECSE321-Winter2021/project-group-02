import testLogin from "./login.js";
import testCustomer from "./customer.js";
import testAssistant from "./assistant.js";
import testTechnician from "./technician.js";

console.log("");

testLogin().then(() => {
  testCustomer().then(() => {
    testAssistant().then(() => {
      testTechnician();
    });
  });
});
