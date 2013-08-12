package com.jegumi.rssreader;

import android.content.Context;
import android.content.SharedPreferences;

public class RssPreferences {

    private static final String PREFS_NAME = "IRISHRAIL_PREFS";
    private static final String FEED_URL = "feed_url";
    private static final String FEED_URL_DEFAULT = "http://feeds.bbci.co.uk/news/rss.xml";

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    public RssPreferences(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
    }

    public String getFeedUrl() {
        return settings.getString(FEED_URL, FEED_URL_DEFAULT);
    }

    public void setFeedUrl(String feed_url) {
        editor.putString(FEED_URL, feed_url);
        editor.commit();
    }
}
