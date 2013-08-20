package org.penney.launcher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by penneyd on 8/19/13.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_LOG = "usage_log";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PACKAGE_NAME = "packageName";
    public static final String COLUMN_START_TIME = "startTime";
    public static final String COLUMN_END_TIME = "endTime";
    public static final String COLUMN_RUN_TIME = "runTime";

    private static final String DATABASE_NAME = "appusage.db";
    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_LOG + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_PACKAGE_NAME + " text, "
            + COLUMN_START_TIME + " integer, "
            + COLUMN_END_TIME + " integer, "
            + COLUMN_RUN_TIME + " integer "
            + ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + i + " to "
                        + i2 + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);
        onCreate(db);
    }
}
