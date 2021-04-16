package ca.mcgill.ecse321.android_scrs;

public class Timeslot {
    private final int timeslotId;
    private final String startDate;
    private final String endDate;
    private final String startTime;
    private final String endTime;
    private final int workspaceId;
    String workspaceName = "";

    private String displayString;

    public Timeslot(int timeslotId, String startDate, String endDate, String startTime, String endTime, int workspaceId) {
        this.timeslotId = timeslotId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workspaceId = workspaceId;
        displayString = String.format("Date: %s\n Time: %s - %s", startDate, startTime.substring(0,5), endTime.substring(0,5));
    }

    public Timeslot(String startDate, String endDate, String startTime, String endTime, int workspaceId) {
        this(-1, startDate, endDate, startTime, endTime, workspaceId);
    }

    public int getTimeslotId() { return timeslotId; }

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

    public int getWorkspaceId() { return workspaceId; }

    public void setWorkspaceName(String workspaceName)
    {
        this.workspaceName = workspaceName;

        displayString = String.format("%s:\t\t %s - %s", workspaceName, startTime, endTime);
    }

    public String toString()
    {
        return displayString;
    }
}
