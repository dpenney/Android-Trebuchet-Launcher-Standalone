package org.penney.launcher.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public List<UsageStat> getAllStats() {
        List<UsageStat> stats = new ArrayList<UsageStat>();


//        Cursor cursor = db.query(Usage._id.getTableName(), DBHelper.names(Usage.values())
//                , null, null, null, null, null);
        Cursor cursor = db.rawQuery("select packageName, sum(runTime) as runtime from " + Usage._id.getTableName() + " group by packageName order by runTime", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UsageStat comment = cursorToSummary(cursor);
            stats.add(comment);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return stats;
    }

    private UsageStat cursorToStat(Cursor cursor) {
        UsageStat stat = new UsageStat();
        int i=0;
        stat.setId(cursor.getLong(i++));
        stat.setPackageName(cursor.getString(i++));
        stat.setStartTime(cursor.getLong(i++));
        stat.setEndTime(cursor.getLong(i++));
        stat.setRunTime(cursor.getLong(i));
        return stat;
    }

    private UsageStat cursorToSummary(Cursor cursor) {
        UsageStat stat = new UsageStat();
        int i=0;
        stat.setPackageName(cursor.getString(i++));
        stat.setRunTime(cursor.getLong(i));
        return stat;
    }

}
