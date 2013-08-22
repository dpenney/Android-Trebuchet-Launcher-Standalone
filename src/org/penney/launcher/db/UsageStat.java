package org.penney.launcher.db;

/**
 * Created by penneyd on 8/19/13.
 */
public class UsageStat {
    private long id;
    private String package_name;
    private long startTime;
    private long endTime;
    private long runTime;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    @Override
    public String toString() {
        return "UsageStat{" +
                "package_name='" + package_name + '\'' +
                ", runTime=" + runTime +
                '}';
    }
}
