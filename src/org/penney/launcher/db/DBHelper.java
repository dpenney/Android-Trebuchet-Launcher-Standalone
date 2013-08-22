package org.penney.launcher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by penneyd on 8/19/13.
 */
public class DBHelper extends SQLiteOpenHelper implements DAO{

    private static final String TAG = "org.penney.launcher.db.DBHelper";

    public List<String> tableNames = new ArrayList<String>();

    private static final String DATABASE_NAME = "appusage.db";
    private static final int DATABASE_VERSION = 6;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder script = new StringBuilder();
        script.append(toTableCreate(Usage.values()));
        script.append(toTableCreate(Bucket.values()));
        Log.d(TAG, script.toString());

        sqLiteDatabase.execSQL(script.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + i + " to "
                        + i2 + ", which will destroy all old data");

            db.execSQL("DROP TABLE IF EXISTS usage_log");
            db.execSQL("DROP TABLE IF EXISTS bucket");

        onCreate(db);
    }

    private String toTableCreate(TableEnum[] vals) {
        StringBuilder script = new StringBuilder();
        String tableName = vals[0].getTableName();
        tableNames.add(tableName);
        script.append("create table ").append(tableName).append("(");
        for (int i = 0; i < vals.length; i++) {
            TableEnum col = vals[i];
            script.append(col.toString()).append(" ").append(col.getType());
            if (i < vals.length - 1) {
                script.append(", ");
            }
        }
        script.append(");");
        return script.toString();
    }

    public static String[] names(TableEnum[] vals) {
        //todo this can be cached and could even be created when we create the table
        String[] colNames = new String[vals.length];
        for (int i = 0; i < vals.length; i++) {
            colNames[i] = vals[i].toString();
        }
        return colNames;
    }



}
