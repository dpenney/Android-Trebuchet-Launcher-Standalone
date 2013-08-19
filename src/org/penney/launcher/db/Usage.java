package org.penney.launcher.db;

/**
 * Created by penneyd on 8/19/13.
 */
public class Usage {
    private long id;
    private String package_name;
    private long access_time;

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

    public long getAccess_time() {
        return access_time;
    }

    public void setAccess_time(long access_time) {
        this.access_time = access_time;
    }
}
