import axios from "axios";

const testTimeslot = async () => {
    const backend_address = "http://localhost:8080";
    let scoreCounter = 0;
    const numberOfTests = 4;
    let timeslotIdToCheck = -1;
    let workspaceIdToCheck = -1;

    let wipeDatabaseResponse = await axios.delete(
        backend_address + "/api/database/wipe"
    );

    //Set up for Test 1
    try {
        let createWorkspaceData = "name=foo";

        let createWorkspaceResponse = await axios.post(
            backend_address + "/api/workspace/create",
            createWorkspaceData);

        workspaceIdToCheck = createWorkspaceResponse.data.workspaceId;

    } catch (error) {
        console.log("failed to setup test 1 (timeslot)")
    }

    //Create a timeslot (Test 1)
    try {
        let createTimeslotData = {
            startDate: Date.now(),
            endDate: Date.now(),
            startTime: +new Date(),
            endTime: +new Date(),
            workspaceId: workspaceIdToCheck,
            techniciansId: []
        };

        let createTimeslotResponse = await axios.post(
            backend_address + "/api/timeslot/create",
            createTimeslotData);

        timeslotIdToCheck = createTimeslotResponse.data.timeslotId;
        let statusCode = createTimeslotResponse.status;

        if (createTimeslotResponse.status === 200 && createTimeslotResponse.data.workspaceId === createTimeslotData.workspaceId) {
            scoreCounter++;
            timeslotIdToCheck = createTimeslotResponse.data.timeslotId;
        } else {
            console.log("Test 1 unsuccessful:");
            if (createTimeslotResponse.workspaceId !== createTimeslotData.workspaceId)
                console.log(`returned data is erronous: ${createTimeslotResponse}`);
            if (statusCode !== 200)
                console.log(`status code not as expected: ${statusCode}`);
        }
    } catch (error) {
        console.log("Failed to create timeslot in test 1");
        console.log(error);
    }
    //Create a timeslot with an incorrect workspaceId (Test 2)
    try {
        let createTimeslotData = {
            startDate: Date.now(),
            endDate: Date.now(),
            startTime: +new Date(),
            endTime: +new Date(),
            workspaceId: "11111",
            techniciansId: []
        };

        let createTimeslotResponse = await axios.post(
            backend_address + "/api/timeslot/create",
            createTimeslotData);

        timeslotIdToCheck = createTimeslotResponse.data.timeslotId;
        let statusCode = createTimeslotResponse.status;

        if (createTimeslotResponse.status === 200 && createTimeslotResponse.data.workspaceId === workspaceIdToCheck) {
            timeslotIdToCheck = createTimeslotResponse.data.timeslotId;
        } else {
            scoreCounter++;
            console.log("Test 2 successful, Did not create timeslot:");
            if (createTimeslotResponse.workspaceId !== createTimeslotData.workspaceId)
                console.log(`returned data is erronous: ${createTimeslotResponse}`);
            if (statusCode !== 200)
                console.log(`status code not as expected: ${statusCode}`);
        }
    } catch (error) {
        scoreCounter++;
        console.log("Failed to create timeslot in test 1");
        console.log(error);
    }

    //Delete timeslots (Test 3)
    try {
        let deleteTimeslotResponse = await axios.delete(
            backend_address + `/api/timeslot/delete/${timeslotIdToCheck}`);

        let statusCode = deleteTimeslotResponse.status;
        if (statusCode === 200 && deleteTimeslotResponse.data.timeslotId === timeslotIdToCheck) {
            scoreCounter++;
        }

    } catch (error) {
        console.log("Failed to delete timeslot in test 3");
    }

    //Deleting an invalid timeslot (Test 4)
    try {
        let deleteTimeslotResponse = await axios.delete(
            backend_address + "/api/timeslot/delete/-1");

        console.log("Test 4 failed: ")
        console.log(`\tUnexpected status code returned ${deleteTimeslotResponse.status}`);
        console.log(`\tWorkspace returned ${deleteTimeslotResponse.data.timeslotId}`);
    } catch (error) {
        scoreCounter++;
    }

    // ========== Compiling Results ==========
    if (scoreCounter === numberOfTests)
        console.log("All the timeslot tests were successful!");
    else
        console.log(
            `${scoreCounter}/${numberOfTests} timeslots tests were successful.`
        );
    console.log("");

};

export default testTimeslot;