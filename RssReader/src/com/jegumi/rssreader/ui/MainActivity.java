package com.jegumi.rssreader.ui;

import java.util.List;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;

import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.inject.Inject;
import com.jegumi.rssreader.R;
import com.jegumi.rssreader.RssPreferences;
import com.jegumi.rssreader.adapters.FeedCursorAdapter;
import com.jegumi.rssreader.adapters.FeedCursorAdapter.ViewHolder;
import com.jegumi.rssreader.db.DataHelper;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getName();

    @Inject
    private DataHelper dataHelper;
    @Inject
    private RssPreferences prefs;

    @InjectView(R.id.feed_list_view)
    private ListView listView;
    private FeedCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFields();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void initFields() {
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
                ViewHolder holder = (ViewHolder) view.getTag();
                Intent intent = new Intent(MainActivity.this, ShowInBrowserActivity.class);
                intent.putExtra(ShowInBrowserActivity.INTENT_EXTRA_URL, holder.getLink());
                intent.putExtra(ShowInBrowserActivity.INTENT_EXTRA_TITLE, holder.getTitle());
                startActivity(intent);
            }
        });
        refreshAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    private void refreshList() {
        new LoadFeeds().execute();
    }

    private void refreshAdapter() {
        Cursor feedCursor = dataHelper.getFeedCursor();
        adapter = new FeedCursorAdapter(MainActivity.this, feedCursor, true);
        listView.setAdapter(adapter);
    }

    private class LoadFeeds extends AsyncTask<Object, Void, Integer> {

        private RSSFeed feed;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            if (adapter.isEmpty()) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage(getString(R.string.cache_empty));
                progressDialog.show();
            }
        }

        @Override
        protected Integer doInBackground(Object... params) {
            try {
                RSSReader reader = new RSSReader();
                feed = reader.load(prefs.getFeedUrl());

                List<RSSItem> rssItems = feed.getItems();
                if (rssItems.isEmpty()) {
                    return 0;
                }
                dataHelper.cleanOldFeeds();

                for (RSSItem rssItem : rssItems) {
                    Log.i("RSS Reader", rssItem.getTitle());
                    rssItem.getThumbnails().get(0).getUrl();
                    dataHelper.insertFeedItem(rssItem);
                }

            } catch (Exception e) {
                Log.i(TAG, e.getMessage());
                return 0;
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            if (result == 1) {
                refreshAdapter();
            } else {
                showError();
            }
        }

        private void showError() {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.no_network).setTitle(R.string.info).setCancelable(false)
                    .setNeutralButton(R.string.accept, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
