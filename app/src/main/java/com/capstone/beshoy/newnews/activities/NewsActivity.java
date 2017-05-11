package com.capstone.beshoy.newnews.activities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.capstone.beshoy.newnews.R;
import com.capstone.beshoy.newnews.background.FetchNews;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String[] news_sources_titles;
    private String[] news_sources_ids;
    private TypedArray news_sources_images;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context mContext;
    private Toolbar toolbar;
    private Drawer result;
    private FetchNews fetchNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mContext = this;
        news_sources_titles = getResources().getStringArray(R.array.news_sources_names);
        news_sources_images = getResources().obtainTypedArray(R.array.news_sources_drawables);
        news_sources_ids    = getResources().getStringArray(R.array.news_sources_ids);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.colorPrimary)
                , ContextCompat.getColor(mContext, R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(this);
        fetchNews = new FetchNews(mContext, swipeRefreshLayout);

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
                                fetchNews = new FetchNews(mContext, swipeRefreshLayout);
                            }
                            fetchNews.execute(news_sources_ids[position]);
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

    }
}
