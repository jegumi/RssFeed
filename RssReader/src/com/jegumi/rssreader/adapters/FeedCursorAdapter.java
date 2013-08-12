package com.jegumi.rssreader.adapters;

import roboguice.RoboGuice;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jegumi.rssreader.R;
import com.jegumi.rssreader.db.DbHelper;
import com.jegumi.rssreader.utils.Utils;
import com.squareup.picasso.Picasso;

public class FeedCursorAdapter extends CursorAdapter {

    public static class ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private ImageView thumbImageView;
        private String link;

        public String getLink() {
            return link;
        }

        public String getTitle() {
            return titleTextView.getText().toString();
        }
    }

    private LayoutInflater inflater;

    public FeedCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        RoboGuice.getInjector(context).injectMembersWithoutViews(this);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        String title = Utils.getStringColumn(cursor, DbHelper.FEED_TITLE);
        String description = Utils.getStringColumn(cursor, DbHelper.FEED_DESCRIPTION);
        String thumbnail = Utils.getStringColumn(cursor, DbHelper.FEED_THUMBNAIL);
        String link = Utils.getStringColumn(cursor, DbHelper.FEED_URL);

        holder.link = link;
        holder.titleTextView.setText(title);
        holder.descriptionTextView.setText(description);
        Picasso.with(context).load(thumbnail).into(holder.thumbImageView);
    }

    @Override
    public View newView(Context context, Cursor c, ViewGroup parent) {
        View view = inflater.inflate(R.layout.feed_row, parent, false);

        ViewHolder holder = new ViewHolder();
        holder.titleTextView = (TextView) view.findViewById(R.id.feed_title);
        holder.descriptionTextView = (TextView) view.findViewById(R.id.feed_description);
        holder.thumbImageView = (ImageView) view.findViewById(R.id.feed_thumbnail);
        view.setTag(holder);

        return view;
    }
}
