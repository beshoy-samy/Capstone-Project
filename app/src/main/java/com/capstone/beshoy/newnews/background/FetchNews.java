package com.capstone.beshoy.newnews.background;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.capstone.beshoy.newnews.R;
import com.capstone.beshoy.newnews.classes.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by bisho on 08-May-17.
 */

public class FetchNews extends AsyncTask<String,Void,ArrayList<Article>> {

    private Context mContext;
    private final String MAINURL = "https://newsapi.org/v1/articles?source=";
    private final String SORTBYTOP = "&sortBy=top";
    private final String APIKEY = "&APIKEY=0f8a2d21de334596aeaf0de769e108ec";
    private String[] sources_ids;
    private SwipeRefreshLayout refreshLayout;
    private final String ARTICLES = "articles";
    private final String ARTICLESOURCE = "source";
    private final String ARTICLEAUTHOR = "author";
    private final String ARICLETITLE = "title";
    private final String ARTICLEDESCRIPTION = "description";
    private final String ARTICLEURL = "url";
    private final String ARTICLEIMAGE = "urlToImage";
    private final String PUBLISHEDAT = "publishedAt";


    public FetchNews(Context context, SwipeRefreshLayout swipeRefreshLayout) {
        this.mContext = context;
        this.refreshLayout = swipeRefreshLayout;
        sources_ids = mContext.getResources().getStringArray(R.array.news_sources_ids);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        refreshLayout.setRefreshing(true);
    }

    @Override
    protected ArrayList<Article> doInBackground(String... params) {
        String source = params[0];
        String address = MAINURL + source + SORTBYTOP + APIKEY;

        try {
            URL url = new URL(address);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            JSONObject response = new JSONObject(builder.toString());
            urlConnection.disconnect();
            return parseMyJson(response);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    private ArrayList<Article> parseMyJson(JSONObject response){

        ArrayList<Article> articles = new ArrayList<>();
        try {
            String source = response.getString(ARTICLESOURCE);
            JSONArray newsArticles = response.getJSONArray(ARTICLES);
            for(int i=0;i<newsArticles.length();i++){
                JSONObject art = newsArticles.getJSONObject(i);
                String author  = art.getString(ARTICLEAUTHOR);
                String title  = art.getString(ARICLETITLE);
                String description  = art.getString(ARTICLEDESCRIPTION);
                String url  = art.getString(ARTICLEURL);
                String imageURL  = art.getString(ARTICLEIMAGE);
                String publishedAT  = art.getString(PUBLISHEDAT);
                Article article = new Article(source, author, title, description, url, imageURL, publishedAT);
                articles.add(article);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        refreshLayout.setRefreshing(false);
        Log.d("beshoy",articles.size()+"");
        super.onPostExecute(articles);
    }
}
