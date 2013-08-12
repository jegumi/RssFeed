package com.jegumi.rssreader.ui;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.EditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.inject.Inject;
import com.jegumi.rssreader.R;
import com.jegumi.rssreader.RssPreferences;

public class SettingsActivity extends BaseActivity {

    @InjectView(R.id.settings_feed)
    private EditText settingsUrlEditText;

    @Inject
    private RssPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        initActionBar();
        iniFields();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void iniFields() {
        settingsUrlEditText.setText(prefs.getFeedUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.settings, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        case R.id.action_save:
            prefs.setFeedUrl(settingsUrlEditText.getText().toString());
            return true;
        default:

        }
        return super.onOptionsItemSelected(item);
    }
}
