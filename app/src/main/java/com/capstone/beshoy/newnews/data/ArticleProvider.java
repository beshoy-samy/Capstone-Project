package com.capstone.beshoy.newnews.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

/**
 * Created by bisho on 11-May-17.
 */

public class ArticleProvider extends ContentProvider {

    private ArticleDbHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        dbHelper = new ArticleDbHelper(getContext());
        database = dbHelper.getReadableDatabase();
        return database != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        return database.query(ArticleContract.ArticleEntry.TABLE_NAME, projection,selection,
                selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "Articles";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long rowID = database.insert(ArticleContract.ArticleEntry.TABLE_NAME, null, values);
        if (rowID == -1) {
            Log.e("Project Error", "Failed to insert row for " + uri);
            FirebaseCrash.log("Failed to insert row for " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri,rowID);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return  database.delete(ArticleContract.ArticleEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return  database.update(ArticleContract.ArticleEntry.TABLE_NAME, values, selection, selectionArgs);
    }
}
