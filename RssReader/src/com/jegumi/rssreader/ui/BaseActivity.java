package com.jegumi.rssreader.ui;

import android.content.Intent;

import com.actionbarsherlock.app.RoboSherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.jegumi.rssreader.R;

public class BaseActivity extends RoboSherlockFragmentActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_settings:
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        default:

        }
        return super.onOptionsItemSelected(item);
    }
}
