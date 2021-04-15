package ca.mcgill.ecse321.android_scrs;

public class Timeslot {
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;

    final private String displayString;

    public Timeslot(String startDate, String endDate, String startTime, String endTime) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;

        displayString = String.format("%s - %s", startDate, endDate);
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

    public String toString()
    {
        return displayString;
    }
}
