package ca.mcgill.ecse321.android_scrs;

public class Appointment {

    final private String displayString;

    public Appointment(String appointmentType, String startDate, String endDate, String startTime, String endTime, int appointmentId) {

        if (startDate.equals(endDate)) {
            displayString = String.format("Type: %s \nDate: %s \nTime: %s - %s", appointmentType, startDate, startTime, endTime);
        } else {
            displayString = String.format("Type: %s \nDate: %s - %s \nTime: %s - %s", appointmentType, startDate, endDate, startTime, endTime);
        }
    }

    public String toString()
    {
        return displayString;
    }
}
