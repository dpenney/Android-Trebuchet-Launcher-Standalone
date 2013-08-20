package org.penney.launcher.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by penneyd on 8/19/13.
 */
public class UsageStatDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    private String[] allCols = {DBHelper.COLUMN_ID, DBHelper.COLUMN_PACKAGE_NAME, DBHelper.COLUMN_START_TIME, DBHelper.COLUMN_END_TIME, DBHelper.COLUMN_RUN_TIME};

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
        values.put(DBHelper.COLUMN_START_TIME, startTime);
        values.put(DBHelper.COLUMN_END_TIME, endTime);
        values.put(DBHelper.COLUMN_RUN_TIME, runTime);
        values.put(DBHelper.COLUMN_PACKAGE_NAME, packageName);

        db.insert(DBHelper.TABLE_LOG, null, values);
        Log.i(UsageStatDAO.class.getName(), "saving log for " + packageName + " ran for " + runTime + "ms");
    }
}
