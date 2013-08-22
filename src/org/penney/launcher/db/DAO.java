package org.penney.launcher.db;

/**
 * Created by penneyd on 8/22/13.
 */
public interface DAO {
    public enum Usage implements TableEnum {
        _id("integer primary key autoincrement"),
        packageName("text"),
        startTime("integer"),
        endTime("integer"),
        runTime("integer");

        private final String type;

        private Usage(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        @Override
        public String getTableName() {
            return "usage_log";
        }

    }




    public enum Bucket implements TableEnum {
        _id("integer primary key autoincrement"),
        name("text"),
        dailyLimit("integer");

        private final String type;

        private Bucket(String type) {
            this.type = type;
        }

        public String getType() { return type; }

        @Override
        public String getTableName() { return "bucket";  }
    }

    public interface TableEnum {
        public String getTableName();

        public String getType();

    }

    public class EnumHelper {

    }
}


