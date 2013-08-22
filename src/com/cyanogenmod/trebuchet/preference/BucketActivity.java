package com.cyanogenmod.trebuchet.preference;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.cyanogenmod.trebuchet.R;

import org.penney.launcher.db.UsageStat;
import org.penney.launcher.db.UsageStatDAO;

import java.util.List;

/**
 * Created by penneyd on 8/22/13.
 */
public class BucketActivity extends ListActivity {
    private UsageStatDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bucket_list);

        db = new UsageStatDAO(this);
        db.open();

        List<UsageStat> stats = db.getAllStats();
        ArrayAdapter<UsageStat> adapter = new ArrayAdapter<UsageStat>(this,
                android.R.layout.simple_list_item_1, stats);
        setListAdapter(adapter);

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

}
