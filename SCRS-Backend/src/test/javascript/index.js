import testLogin from "./login.js";
import testCustomer from "./customer.js";

console.log("");

testLogin().then(() => {
  testCustomer();
});
