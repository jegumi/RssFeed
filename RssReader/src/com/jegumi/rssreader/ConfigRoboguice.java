package com.jegumi.rssreader;

import android.content.Context;

import com.google.inject.AbstractModule;
import com.jegumi.rssreader.db.DataHelper;

public class ConfigRoboguice extends AbstractModule {

    @Override
    protected void configure() {
        Context context = RssReaderApp.getContext();

        RssPreferences preferences = new RssPreferences(context);
        bind(RssPreferences.class).toInstance(preferences);

        DataHelper dataHelper = new DataHelper(context);
        bind(DataHelper.class).toInstance(dataHelper);
    }
}
