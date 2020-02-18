package com.example.newsapp.data;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DateComparator {
    DateOldToNew oldToNew;
    DateNewToOld newToOld;

    public DateComparator() {
        oldToNew = new DateOldToNew();
        newToOld = new DateNewToOld();
    }

    public DateOldToNew getOldToNewComparator() {
        return oldToNew;
    }

    public DateNewToOld getNewToOldComparator() {
        return newToOld;
    }

    class DateOldToNew implements Comparator<NewsItem> {

        @Override
        public int compare(NewsItem o1, NewsItem o2) {
            Log.i("Test", "compare");
            String date1 = o1.getPublishedAt();
            String date2 = o2.getPublishedAt();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                Date first = sdf.parse(date1);
                Date second = sdf.parse(date2);
                return (-1*first.compareTo(second));
            } catch (ParseException e) {
                Log.i("Test", "ParseException");
                e.printStackTrace();
            }
            return 0;
        }
    }

    class DateNewToOld implements Comparator<NewsItem> {

        @Override
        public int compare(NewsItem o1, NewsItem o2) {
            Log.i("Test", "compare");
            String date1 = o1.getPublishedAt();
            String date2 = o2.getPublishedAt();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                Date first = sdf.parse(date1);
                Date second = sdf.parse(date2);
                return first.compareTo(second);
            } catch (ParseException e) {
                Log.i("Test", "ParseException");
                e.printStackTrace();
            }
            return 0;
        }
    }

}