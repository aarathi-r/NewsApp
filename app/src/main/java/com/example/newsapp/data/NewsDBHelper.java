package com.example.newsapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.newsapp.data.NewsDBSchema.NewsTableColumns;

import java.util.ArrayList;
import java.util.List;

public class NewsDBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "News.db";
    private final static int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + NewsTableColumns.TABLE_NAME + " (" +
                    NewsTableColumns._ID + " INTEGER PRIMARY KEY," +
                    NewsTableColumns.COLUMN_TITLE + " TEXT," +
                    NewsTableColumns.COLUMN_DESCRIPTION + " TEXT," +
                    NewsTableColumns.COLUMN_AUTHOR + " TEXT," +
                    NewsTableColumns.COLUMN_PUBLISHED_AT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NewsTableColumns.TABLE_NAME;

    NewsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insertMutlipleNewsItem(List<NewsItem> newsItems){
        SQLiteDatabase db = getWritableDatabase();

        for (NewsItem newsItem : newsItems) {
            ContentValues values = new ContentValues();
            values.put(NewsTableColumns.COLUMN_TITLE, newsItem.getTitle());
            values.put(NewsTableColumns.COLUMN_DESCRIPTION, newsItem.getDescription());
            values.put(NewsTableColumns.COLUMN_AUTHOR, newsItem.getAuthor());
            values.put(NewsTableColumns.COLUMN_PUBLISHED_AT, newsItem.getPublishedAt());

            db.insert(NewsTableColumns.TABLE_NAME, null, values);
        }
        db.close();
    }

    public void insertNewsItem(NewsItem newsItem){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NewsTableColumns.COLUMN_TITLE, newsItem.getTitle());
        values.put(NewsTableColumns.COLUMN_AUTHOR, newsItem.getAuthor());
        values.put(NewsTableColumns.COLUMN_PUBLISHED_AT, newsItem.getPublishedAt());

        db.insert(NewsTableColumns.TABLE_NAME, null, values);

        db.close();
    }

    public List<NewsItem> readNewsItems() {
        Log.i("Test", "readNewsItems: ");
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(NewsTableColumns.TABLE_NAME, null, null,
                null, null, null, null);
        return getListFromCursor(c);
    }

    private List<NewsItem> getListFromCursor(Cursor cursor) {
        Log.i("Test", "getListFromCursor cursor.count: " + cursor.getCount());
        List<NewsItem> newsItems = new ArrayList<>();

        if (cursor == null || cursor.getCount() <= 0) {
            return newsItems;
        }
        cursor.moveToFirst();
        do {
            NewsItem newsItem = new NewsItem();
            newsItem.setTitle(cursor.getString(cursor.getColumnIndex(NewsTableColumns.COLUMN_TITLE)));
            newsItem.setDescription(cursor.getString(cursor.getColumnIndex(NewsTableColumns.COLUMN_DESCRIPTION)));
            newsItem.setAuthor(cursor.getString(cursor.getColumnIndex(NewsTableColumns.COLUMN_AUTHOR)));
            newsItem.setPublishedAt(cursor.getString(cursor.getColumnIndex(NewsTableColumns.COLUMN_PUBLISHED_AT)));

            newsItems.add(newsItem);
        } while (cursor.moveToNext());
        return newsItems;
    }
}
