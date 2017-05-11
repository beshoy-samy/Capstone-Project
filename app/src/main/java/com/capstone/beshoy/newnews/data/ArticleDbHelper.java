package com.capstone.beshoy.newnews.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bisho on 11-May-17.
 */

public class ArticleDbHelper extends SQLiteOpenHelper {



    public static final String LOG_TAG = ArticleDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "news.db";

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_ARTICLES_TABLE = "CREATE TABLE "+ArticleContract.ArticleEntry.TABLE_NAME+
            " ("+ArticleContract.ArticleEntry.ARTICLE_NO+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_SOURCE+" VARCHAR(255), "+
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_AUTHOR+" VARCHAR(255), "+
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_TITLE+" VARCHAR(255), "+
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_DESCRIPTION+" TEXT, "+
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_URL+" VARCHAR(255), "+
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_IMAGE+" VARCHAR(255), "+
            ArticleContract.ArticleEntry.COLUMN_ARTICLE_DATE+" VARCHAR(255) );";

    private static final String DROP_TABLE    = "DROP TABLE IF EXISTS "+ArticleContract.ArticleEntry.TABLE_NAME;

    public ArticleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ARTICLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }


}
