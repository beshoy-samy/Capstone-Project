package com.capstone.beshoy.newnews.data;

import android.net.Uri;

/**
 * Created by bisho on 11-May-17.
 */

public class ArticleContract {

    // To prevent someone from accidentally instantiating the contract class,
    private ArticleContract() {}

    public static final String CONTENT_AUTHORITY = "com.capstone.beshoy.newnews";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ARTICLES = "articles";



    public static final class ArticleEntry  {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ARTICLES);

        /** Name of database table for pets */
        public final static String TABLE_NAME = "articles";

        /**
         * NO of the Article.
         *
         * Type: INTEGER PRIMARY KEY AUTOINCREMENT
         */
        public static final String ARTICLE_NO = "_No";

        /**
         * Source of the Article.
         *
         * Type: VARCHAR(255)
         */
        public final static String COLUMN_ARTICLE_SOURCE ="source";

        public static final int SOURCE_COLUMN_INDEX = 0;

        /**
         * Author of the Article.
         *
         * Type: VARCHAR(255)
         */
        public final static String COLUMN_ARTICLE_AUTHOR = "author";

        public static final int AUTHOR_COLUMN_INDEX = 1;

        /**
         * Title of the Article.
         *
         * Type: VARCHAR(255)
         */
        public final static String COLUMN_ARTICLE_TITLE = "title";

        public static final int TITLE_COLUMN_INDEX = 2;

        /**
         * Description of the Article.
         *
         * Type: TEXT
         */
        public final static String COLUMN_ARTICLE_DESCRIPTION = "description";

        public static final int DESCRIPTION_COLUMN_INDEX = 3;

        /**
         * URL of the Article.
         *
         * Type: VARCHAR(255)
         */
        public final static String COLUMN_ARTICLE_URL = "article_url";

        public static final int URL_COLUMN_INDEX = 4;

        /**
         * IMAGE HEAD of the Article.
         *
         * Type: VARCHAR(255)
         */
        public final static String COLUMN_ARTICLE_IMAGE = "image_url";

        public static final int IMAGE_COLUMN_INDEX = 5;

        /**
         * DATE of the Article.
         *
         * Type: VARCHAR(255)
         */
        public final static String COLUMN_ARTICLE_DATE = "date";

        public static final int DATE_COLUMN_INDEX = 6;
    }


}
