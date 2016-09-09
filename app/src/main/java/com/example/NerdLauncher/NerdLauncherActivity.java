package com.example.NerdLauncher;

import android.support.v4.app.Fragment;

/**
 * Created by sjha3 on 9/5/16.
 */
public class NerdLauncherActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new NerdLauncherFragment();
    }
}
