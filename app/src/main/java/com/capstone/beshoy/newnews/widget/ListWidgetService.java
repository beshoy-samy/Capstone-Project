package com.capstone.beshoy.newnews.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.capstone.beshoy.newnews.R;
import com.capstone.beshoy.newnews.classes.Article;
import com.capstone.beshoy.newnews.data.ArticleContract.ArticleEntry;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by bisho on 13-May-17.
 */

public class ListWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;
    private String[] projection = {ArticleEntry.COLUMN_ARTICLE_SOURCE
            , ArticleEntry.COLUMN_ARTICLE_AUTHOR
            , ArticleEntry.COLUMN_ARTICLE_TITLE
            , ArticleEntry.COLUMN_ARTICLE_DESCRIPTION
            , ArticleEntry.COLUMN_ARTICLE_URL
            , ArticleEntry.COLUMN_ARTICLE_IMAGE
            , ArticleEntry.COLUMN_ARTICLE_DATE};

    public ListRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if(mCursor != null)
            mCursor.close();
        mCursor = mContext.getContentResolver()
                .query(ArticleEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if(mCursor == null)
            return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(mCursor == null)
            return null;
        mCursor.moveToPosition(position);
        String source = mCursor.getString(ArticleEntry.SOURCE_COLUMN_INDEX);
        String author = mCursor.getString(ArticleEntry.AUTHOR_COLUMN_INDEX);
        String title = mCursor.getString(ArticleEntry.TITLE_COLUMN_INDEX);
        String description = mCursor.getString(ArticleEntry.DESCRIPTION_COLUMN_INDEX);
        String url = mCursor.getString(ArticleEntry.URL_COLUMN_INDEX);
        String imageURL = mCursor.getString(ArticleEntry.IMAGE_COLUMN_INDEX);
        String date = mCursor.getString(ArticleEntry.DATE_COLUMN_INDEX);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.article_item);

        try {
            Bitmap bitmap = Picasso.with(mContext)
                    .load(imageURL)
                    .error(R.drawable.image_error)
                    .get();
            views.setImageViewBitmap(R.id.article_image, bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        views.setTextViewText(R.id.article_title, title);
        if(!date.equals("null")){
            date = date.substring(0, date.indexOf("T"));
            views.setTextViewText(R.id.article_date, date);
        }

        Intent fillInIntent = new Intent();
        Article article = new Article(source, author, title, description, url, imageURL, date);
        fillInIntent.putExtra("article",article);
        views.setOnClickFillInIntent(R.id.article_item, fillInIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
