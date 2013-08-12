package com.jegumi.rssreader.db;

import org.mcsoxford.rss.RSSItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.jegumi.rssreader.model.Feed;

public class DataHelper {

    private static final String TAG = DataHelper.class.getName();
    private SQLiteDatabase db;

    public DataHelper(Context context) {
        DbHelper openHelper = new DbHelper(context);
        db = openHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    public long insertFeedItem(RSSItem rssItem) {
        Feed feed = new Feed();
        feed.setTitle(rssItem.getTitle());
        feed.setContent(rssItem.getContent());
        feed.setDate(rssItem.getPubDate());
        feed.setUrl(rssItem.getLink().toString());
        feed.setDescription(rssItem.getDescription());
        feed.setUrlThumb(rssItem.getThumbnails().get(0).getUrl().toString());

        return insertFeed(feed);
    }

    public void cleanOldFeeds() {
        db.delete(DbHelper.FEED_TABLE, null, null);
    }

    private long insertFeed(Feed feed) {
        Log.i(TAG, "insertFeed");
        ContentValues values = getFeedValues(feed);
        return db.insert(DbHelper.FEED_TABLE, null, values);
    }

    private ContentValues getFeedValues(Feed feed) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.FEED_CONTENT, feed.getContent());
        values.put(DbHelper.FEED_TITLE, feed.getTitle());
        values.put(DbHelper.FEED_URL, feed.getUrl());
        values.put(DbHelper.FEED_DATE, feed.getDate().toString());
        values.put(DbHelper.FEED_DESCRIPTION, feed.getDescription());
        values.put(DbHelper.FEED_THUMBNAIL, feed.getUrlThumb().toString());

        return values;
    }

    public boolean isTableEmpty(String tableName) {
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        boolean isEmpty = cursor.getCount() == 0;
        cursor.close();
        return isEmpty;
    }

    public Cursor getCursor(String tableName) {
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        return cursor;
    }

    public Cursor getFeedCursor() {
        Cursor cursor = db.query(DbHelper.FEED_TABLE, null, null, null, null, null, DbHelper.FEED_DATE + " ASC");
        return cursor;
    }

    public String createSelection(String name, String value) {
        value = value.replaceAll("'", "");
        return name + " = '" + value + "'";
    }
}
