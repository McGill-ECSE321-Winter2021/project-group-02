import testLogin from "./login.js";
import testCustomer from "./customer.js";
import testAssistant from "./assistant.js";
import {testTechnician, testAssignTimeslotToTechnician, testViewTechnicianSchedule} from "./technician.js";
import {testAppointment, testRateAppointment} from "./appointment.js";
import testWorkspace from "./workspace.js";
import testTimeslot from "./timeslot.js";


console.log("");

testLogin().then(() => {
    testCustomer().then(() => {
        testAssistant().then(() => {
            testTechnician().then(() => {
                testRateAppointment().then(() => {
                    testWorkspace().then(() => {
                        testTimeslot().then(() => {
                            testAssignTimeslotToTechnician().then(() => {
                                testViewTechnicianSchedule().then(() => {
                                    testAppointment();
                                });
                            });
                        });
                    });
                });
            });
        });
    });
});

