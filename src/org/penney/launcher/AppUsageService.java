package org.penney.launcher;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import org.penney.launcher.db.UsageStatDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by penneyd on 8/19/13.
 */
public class AppUsageService extends Service {

    private static final String TAG = "org.penney.launcher.AppUsageService";
    private Timer t;
    private HashMap<String, Long> logs = new HashMap<String, Long>();
    private UsageStatDAO db;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service start called");
        return  Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {

        db = new UsageStatDAO(getApplicationContext());
        db.open();

        final Handler handler = new Handler();
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            String previousPackage = "THEREWASONCEALITTLEFISH";
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        previousPackage = logRunningApp(previousPackage);
                    }
                });
            }
        }, 5000, 5000);
        Log.d(TAG, "service created");
    }

    private String logRunningApp(String previousPackage) {
        ActivityManager localActivityManager = (ActivityManager)getSystemService("activity");
        List<ActivityManager.RunningTaskInfo> running = localActivityManager.getRunningTasks(1);

        String packageName = running.get(0).baseActivity.getPackageName();
        if(packageName != null && !packageName.equals(previousPackage))
        {
            //Just started app
            long now = System.currentTimeMillis();
            logs.put(packageName, now);
            Long previousStart = logs.get(previousPackage);
            if(previousStart != null)
            {
                long runTime = now - previousStart;
                db.saveLog(previousStart, now, runTime, previousPackage);
            }
        }
        Log.d(TAG, "tick " + packageName + ":" + previousPackage);
        return packageName;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "ouchy doodles");
        t.cancel();
        db.close();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
