package com.capstone.beshoy.newnews.data;

/**
 * Created by bisho on 11-May-17.
 */

public class ArticleContract {

    // To prevent someone from accidentally instantiating the contract class,
    private ArticleContract() {}

    public static final class ArticleEntry  {

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

        /**
         * Author of the Article.
         *
         * Type: VARCHAR(255)
         */
        public final static String COLUMN_ARTICLE_AUTHOR = "author";

        /**
         * Title of the Article.
         *
         * Type: VARCHAR(255)
         */
        public final static String COLUMN_ARTICLE_TITLE = "title";

        /**
         * Description of the Article.
         *
         * Type: TEXT
         */
        public final static String COLUMN_ARTICLE_DESCRIPTION = "description";

        /**
         * URL of the Article.
         *
         * Type: VARCHAR(255)
         */
        public final static String COLUMN_ARTICLE_URL = "article_url";

        /**
         * IMAGE HEAD of the Article.
         *
         * Type: VARCHAR(255)
         */
        public final static String COLUMN_ARTICLE_IMAGE = "image_url";

        /**
         * DATE of the Article.
         *
         * Type: VARCHAR(255)
         */
        public final static String COLUMN_ARTICLE_DATE = "date";
    }


}
