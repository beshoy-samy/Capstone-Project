package com.capstone.beshoy.newnews.background;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.capstone.beshoy.newnews.R;
import com.capstone.beshoy.newnews.classes.Article;
import com.capstone.beshoy.newnews.data.ArticleContract.ArticleEntry;
import com.capstone.beshoy.newnews.interfaces.FetchingCallBack;

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
    // add your api key here for this api https://newsapi.org/
    private final String APIKEY = "&APIKEY=0f8a2d21de334596aeaf0de769e108ec";
    private String[] sources_ids;
    private final String ARTICLES = "articles";
    private final String ARTICLESOURCE = "source";
    private final String ARTICLEAUTHOR = "author";
    private final String ARICLETITLE = "title";
    private final String ARTICLEDESCRIPTION = "description";
    private final String ARTICLEURL = "url";
    private final String ARTICLEIMAGE = "urlToImage";
    private final String PUBLISHEDAT = "publishedAt";
    private FetchingCallBack callBack;

    public FetchNews(Context context, FetchingCallBack fetchingCallBack) {
        this.mContext = context;
        this.callBack = fetchingCallBack;
        sources_ids = mContext.getResources().getStringArray(R.array.news_sources_ids);
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
            ArrayList<Article> articles = parseMyJson(response);
            if(articles.size() != 0)
                insertArticles(articles, source);
            return articles;
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
                if(!publishedAT.equals("null"))
                    publishedAT = publishedAT.substring(0,publishedAT.indexOf("T"));
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
        super.onPostExecute(articles);
        callBack.onFetchingFinished();
    }

    private void insertArticles(ArrayList<Article> articles, String source){
        deleteOldArticles(source);
        for(Article article : articles){
            ContentValues contentValues = new ContentValues();
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_SOURCE, article.getSource());
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_AUTHOR, article.getAuthor());
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_TITLE, article.getTitle());
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_DESCRIPTION, article.getDescription());
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_URL, article.getArticleURL());
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_IMAGE, article.getImageURL());
            contentValues.put(ArticleEntry.COLUMN_ARTICLE_DATE, article.getPublishedAt());

            mContext.getContentResolver().insert(ArticleEntry.CONTENT_URI, contentValues);
        }

    }

    private void deleteOldArticles(String source){
        String[] args = {""+source};
        mContext.getContentResolver().delete(ArticleEntry.CONTENT_URI
                , ArticleEntry.COLUMN_ARTICLE_SOURCE + " = ?", args);
    }


}
