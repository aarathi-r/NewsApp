package com.example.newsapp.data;

import android.provider.BaseColumns;

public class NewsDBSchema {

    public static class NewsTableColumns implements BaseColumns {
        public static final String TABLE_NAME = "offline_news";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_PUBLISHED_AT = "published_at";
    }
}
