package org.penney.launcher.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by penneyd on 8/19/13.
 */
public class UsageStatDAO implements DAO {
    private SQLiteDatabase db;
    private DBHelper helper;

    public UsageStatDAO(Context context) {
        helper = new DBHelper(context);
    }

    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public void saveLog(long startTime, long endTime, long runTime, String packageName) {
        ContentValues values = new ContentValues();
        values.put(Usage.startTime.toString(), startTime);
        values.put(Usage.endTime.toString(), endTime);
        values.put(Usage.runTime.toString(), runTime);
        values.put(Usage.packageName.toString(), packageName);

        db.insert(Usage._id.getTableName(), null, values);
        Log.i(UsageStatDAO.class.getName(), "saving log for " + packageName + " ran for " + runTime + "ms");
    }
}
