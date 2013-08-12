package com.jegumi.rssreader.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

public final class Utils {

    public static float getFloatColumn(Cursor cursor, String column) {
        try {
            int index = cursor.getColumnIndex(column);
            return cursor.getFloat(index);
        } catch (Exception e) {
            return -1;
        }
    }

    public static int getIntColumn(Cursor cursor, String column) {
        try {
            int index = cursor.getColumnIndex(column);
            return cursor.getInt(index);
        } catch (Exception e) {
            return -1;
        }
    }

    public static long getLongColumn(Cursor cursor, String column) {
        try {
            int index = cursor.getColumnIndex(column);
            return cursor.getLong(index);
        } catch (Exception e) {
            return -1;
        }
    }

    public static double getDoubleColumn(Cursor cursor, String column) {
        try {
            int index = cursor.getColumnIndex(column);
            return cursor.getDouble(index);
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getStringColumn(Cursor cursor, String column) {
        try {
            int index = cursor.getColumnIndex(column);
            return cursor.getString(index);
        } catch (Exception e) {
            return "";
        }
    }

    public static void openUrlInBrowser(Context context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }
}
