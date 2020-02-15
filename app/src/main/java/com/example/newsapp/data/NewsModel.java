package com.example.newsapp.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.newsapp.logic.NewsFetchCompletedListener;
import com.example.newsapp.logic.NewsPresenter;

import java.util.List;

public class NewsModel {

    private NewsPresenter mPresenter;

    private NewsFetchCompletedListener listener = new NewsFetchCompletedListener() {
        @Override
        public void onNewsFetchCompleted(List<NewsItem> newsItems) {
            Log.i("Test","onNewsFetchCompleted newsItems: " + newsItems.size());
            mPresenter.onNewsDataAvailable(newsItems);
        }
    };

    public NewsModel(NewsPresenter presenter) {
        mPresenter = presenter;
    }

    public void fetchNewsData(String url) {
        Log.i("Test","fetchNewsData: " + url);
        NewsLoader newsLoader = new NewsLoader(listener);
        newsLoader.execute(url);
    }
}
