package com.example.NerdLauncher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sjha3 on 9/5/16.
 */
public class NerdLauncherFragment extends ListFragment {
    private static final String TAG = "NerdLauncherFragment";

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final PackageManager pm = getActivity().getPackageManager();
        final List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);

        Log.i(TAG, "I have found " + activities.size() + " activities");

        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(final ResolveInfo a, final ResolveInfo b) {
                final PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(a.loadLabel(pm).toString(), b.loadLabel(pm).toString());
            }
        });

        final ArrayAdapter<ResolveInfo> adapter = new ArrayAdapter<ResolveInfo>(getActivity(), android.R.layout.simple_list_item_1, activities) {
            public View getView(final int pos, final View convertView, final ViewGroup parent) {
                final PackageManager pm = getActivity().getPackageManager();
                final View v = super.getView(pos, convertView, parent);
                // Simple_list_item_1 is a textView so we need to cast it so that we can set its text value
                final TextView tv = (TextView) v;
                final ResolveInfo ri = getItem(pos);
                tv.setText(ri.loadLabel(pm));
                return v;
            }
        };

        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        final ResolveInfo resolveInfo = (ResolveInfo) l.getAdapter().getItem(position);
        final ActivityInfo activityInfo = resolveInfo.activityInfo;
        if (activityInfo == null)
            return;
        final Intent i = new Intent(Intent.ACTION_MAIN);
        i.setClassName(activityInfo.applicationInfo.packageName, activityInfo.name);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
