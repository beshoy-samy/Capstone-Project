package com.capstone.beshoy.newnews.activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.capstone.beshoy.newnews.R;
import com.capstone.beshoy.newnews.background.FetchNews;
import com.capstone.beshoy.newnews.data.ArticleContract.ArticleEntry;
import com.capstone.beshoy.newnews.interfaces.FetchingCallBack;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
                                    , LoaderManager.LoaderCallbacks<Cursor>, FetchingCallBack{

    private String[] news_sources_titles;
    private String[] news_sources_ids;
    private TypedArray news_sources_images;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context mContext;
    private Toolbar toolbar;
    private Drawer result;
    private FetchNews fetchNews;
    private static final int ARTICLE_LOADER = 1;
    private LoaderManager.LoaderCallbacks<Cursor> callbacks;
    private FetchingCallBack fetchingCallBack;
    private boolean initializeLoader = true;
    private int source_position = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mContext = this;
        news_sources_titles = getResources().getStringArray(R.array.news_sources_names);
        news_sources_images = getResources().obtainTypedArray(R.array.news_sources_drawables);
        news_sources_ids    = getResources().getStringArray(R.array.news_sources_ids);
        callbacks = this; fetchingCallBack = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.colorPrimary)
                , ContextCompat.getColor(mContext, R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(this);
        fetchNews = new FetchNews(mContext, fetchingCallBack);

        ArrayList<IDrawerItem> drawerItems = new ArrayList<>();
        for (int i=0;i<news_sources_titles.length;i++) {
            String title = news_sources_titles[i];
            if(!title.equals("World") && !title.equals("Sports")
                    && !title.equals("Business") && !title.equals("Tech")
                    && !title.equals("Science") && !title.equals("Other")){
                Drawable image  = news_sources_images.getDrawable(i);
                drawerItems.add(new SecondaryDrawerItem().withName(title).withIcon(image));
            }
            else drawerItems.add(new PrimaryDrawerItem().withName(title).withSelectable(false));

        }
        news_sources_images.recycle();
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDrawerItems(drawerItems)
                .withSliderBackgroundColor(Color.WHITE)
                .withTranslucentStatusBar(false)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if(!news_sources_ids[position].equals("0")){
                            if(fetchNews != null){
                                fetchNews = null;
                                fetchNews = new FetchNews(mContext, fetchingCallBack);
                            }
                            if(isNetworkAvailable()){
                                swipeRefreshLayout.setRefreshing(true);
                                source_position = position;
                                fetchNews.execute(news_sources_ids[position]);
                            }
                            else{
                                Bundle bundle = new Bundle();
                                bundle.putInt("source_position", position);
                                if(initializeLoader){
                                    getLoaderManager().initLoader(ARTICLE_LOADER, bundle, callbacks);
                                    initializeLoader = false;
                                }
                                else
                                    getLoaderManager().restartLoader(ARTICLE_LOADER, bundle, callbacks);
                                Toast.makeText(mContext, getString(R.string.no_internet_error), Toast.LENGTH_SHORT).show();
                            }
                            result.closeDrawer();
                        }

                        return true;
                    }
                })
                .build();
        result.setSelectionAtPosition(1);


    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }




    public boolean isNetworkAvailable() {
        ConnectivityManager networkManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkState = networkManager.getActiveNetworkInfo();

        return (networkState != null && networkState.isConnected());
    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        int position = args.getInt("source_position");
        String source = news_sources_ids[position];
        String[] projection = {ArticleEntry.COLUMN_ARTICLE_SOURCE
        , ArticleEntry.COLUMN_ARTICLE_AUTHOR
        , ArticleEntry.COLUMN_ARTICLE_TITLE
        , ArticleEntry.COLUMN_ARTICLE_DESCRIPTION
        , ArticleEntry.COLUMN_ARTICLE_URL
        , ArticleEntry.COLUMN_ARTICLE_IMAGE
        , ArticleEntry.COLUMN_ARTICLE_DATE};
        String[] selectionArgs = {""+source};
        return new CursorLoader(mContext
        , ArticleEntry.CONTENT_URI
        , projection
        , ArticleEntry.COLUMN_ARTICLE_SOURCE + " = ?"
        , selectionArgs
        , null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        Log.d("beshoy",data.getCount()+" retrieved");
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }

    @Override
    public void onFetchingFinished() {
        Bundle bundle = new Bundle();
        bundle.putInt("source_position", source_position);
        if(initializeLoader){
            getLoaderManager().initLoader(ARTICLE_LOADER, bundle, callbacks);
            initializeLoader = false;
        }
        else
            getLoaderManager().restartLoader(ARTICLE_LOADER, bundle, callbacks);
        swipeRefreshLayout.setRefreshing(false);
    }
}
