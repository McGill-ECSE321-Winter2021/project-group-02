package ca.mcgill.ecse321.android_scrs;

public class Timeslot {
    private final String startDate;
    private final String endDate;
    private final String startTime;
    private final String endTime;
    private final int workspaceId;
    String workspaceName = "";

    private String displayString;

    public Timeslot(String startDate, String endDate, String startTime, String endTime, int workspaceId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workspaceId = workspaceId;
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
