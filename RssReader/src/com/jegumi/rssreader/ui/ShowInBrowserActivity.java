package com.jegumi.rssreader.ui;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.jegumi.rssreader.R;

public class ShowInBrowserActivity extends BaseActivity {

    public static final String INTENT_EXTRA_URL = "es.jegumi.rssreader.url";
    public static final String INTENT_EXTRA_TITLE = "es.jegumi.rssreader.title";

    @InjectView(R.id.web_view) private WebView web;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }

        String title = (String) extras.get(INTENT_EXTRA_TITLE);
        initActionBar(title);
        String url = (String) extras.get(INTENT_EXTRA_URL);
        initWebView(url);
    }

    private void initActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
    }

    private void initWebView(String url) {
        web.setBackgroundColor(0);
        web.setVerticalScrollBarEnabled(false);
        web.setHorizontalScrollBarEnabled(false);
        web.getSettings().setAppCacheEnabled(false);
        web.getSettings().setDomStorageEnabled(true);
        web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        web.loadUrl(url);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        default:

        }
        return super.onOptionsItemSelected(item);
    }
}
