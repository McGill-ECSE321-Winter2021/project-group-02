package ca.mcgill.ecse321.android_scrs;

public class Appointment {
    private String appointmentType;
    private final String startDate;
    private final String endDate;
    private final String startTime;
    private final String endTime;
    private final int appointmentId;

    final private String displayString;

    public Appointment(String appointmentType, String startDate, String endDate, String startTime, String endTime, int appointmentId) {
        this.appointmentType = appointmentType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appointmentId=appointmentId;

        if (startDate == endDate) {
            displayString = String.format("Type: %s \nDate: %s \nTime: %s - %s", appointmentType, startDate, startTime, endTime);
        } else {
            displayString = String.format("Type: %s \nDate: %s - %s \nTime: %s - %s", appointmentType, startDate, endDate, startTime, endTime);
        }
    }

//    public Appointment(String appointmentType, int appointmentId) {
//        this.appointmentType = appointmentType;
//        this.appointmentId=appointmentId;
//
//        displayString = String.format("%s  %s", appointmentType,appointmentId);
//
//    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getAppointmentId() { return appointmentId; }


    public String toString()
    {
        return displayString;
    }
}
