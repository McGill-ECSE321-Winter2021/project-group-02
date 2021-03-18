import testLogin from "./login.js";
import testCustomer from "./customer.js";
import testAssistant from "./assistant.js";
import {testTechnician, testAssignTimeslotToTechnician} from "./technician.js";
import { testAppointmentBookingAndPayment, testRateAppointment, testModifyAppointment } from  "./appointment.js";
import testWorkspace from "./workspace.js";

console.log("");

testLogin().then(() => {
  testCustomer().then(() => {
    testAssistant().then(() => {
      testTechnician().then(() => {
            testRateAppointment().then(() => {
                testModifyAppointment().then(() => {
                    testWorkspace().then(() => {
                        testAssignTimeslotToTechnician().then(() => {
                            testAppointmentBookingAndPayment();
                        });
                    });
                });
            });
        });
    });
  });
});


