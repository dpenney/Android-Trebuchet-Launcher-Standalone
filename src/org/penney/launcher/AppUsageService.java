package org.penney.launcher;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by penneyd on 8/19/13.
 */
public class AppUsageService extends Service {

    private static final String TAG = "org.penney.launcher.AppUsageService";
    private Timer t;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service start called");
        return  Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        final Handler handler = new Handler();
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        logRunningApp();
                    }
                });
            }
        }, 5000, 5000);
        Log.d(TAG, "service created");
    }

    private void logRunningApp() {
        ActivityManager localActivityManager = (ActivityManager)getSystemService("activity");
        List<ActivityManager.RunningTaskInfo> running = localActivityManager.getRunningTasks(1);

        Log.d(TAG, "tick " + running.get(0).baseActivity.getPackageName());
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "ouchy doodles");
        t.cancel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
