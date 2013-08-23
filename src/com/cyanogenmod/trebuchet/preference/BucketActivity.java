package com.cyanogenmod.trebuchet.preference;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyanogenmod.trebuchet.R;

import org.penney.launcher.db.UsageStat;
import org.penney.launcher.db.UsageStatDAO;

import java.util.List;



/**
 * Created by penneyd on 8/22/13.
 */
public class BucketActivity extends ListActivity implements MenuItem.OnMenuItemClickListener {
    private UsageStatDAO db;
    private static final int MENU_DELETE = 0;
    private PackageManager mPackageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bucket_list);
        mPackageManager = getPackageManager();


        db = new UsageStatDAO(this);
        db.open();

        List<UsageStat> stats = db.getAllStats();
        ArrayAdapter<UsageStat> adapter = new AppsAdapter(this,
                android.R.layout.simple_list_item_1, stats);
        setListAdapter(adapter);

//        mAppsAdapter =
//        mAppsAdapter.setNotifyOnChange(true);
//
//        setListAdapter(mAppsAdapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, MENU_DELETE, 0, R.string.menu_bucket_apps_add)
                .setOnMenuItemClickListener(this)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }

    @Override
    protected void onResume() {
        db.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        db.close();
        super.onPause();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }


    private class AppsAdapter extends ArrayAdapter<UsageStat> {

        private final LayoutInflater mInflater;

        private AppsAdapter(Context context, int textViewResourceId, List<UsageStat> objects) {
            super(context, textViewResourceId, objects);
            mInflater = LayoutInflater.from(context);
        }

//        public AppsAdapter(Context context, int textViewResourceId) {
//            super(context, textViewResourceId);
//
//            mInflater = LayoutInflater.from(context);
//
//        }

        @Override
        public long getItemId(int id) {
            return id;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final UsageStat info = getItem(position);

            if(convertView == null) {
                convertView = mInflater.inflate(R.layout.hidden_apps_list_item, parent, false);
            }

            final View item = convertView;
            ImageView icon = (ImageView) item.findViewById(R.id.icon);
            TextView title = (TextView) item.findViewById(R.id.title);
            TextView summary = (TextView) item.findViewById(R.id.summary);

            try {
                ApplicationInfo appInfo = mPackageManager.getApplicationInfo(info.getPackageName(), 0);
                icon.setImageDrawable(appInfo.loadIcon(mPackageManager));
                title.setText(appInfo.loadLabel(mPackageManager));
                summary.setText(info.toString());
            } catch (Exception ex) {
                title.setText(info.getPackageName() + " unknown");
            }

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getListView().setItemChecked(position, !((Checkable) item).isChecked());
                    //mSaved = false;
                }
            });

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }



}
