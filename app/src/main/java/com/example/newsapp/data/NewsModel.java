package com.example.newsapp.data;

import android.util.Log;

import com.example.newsapp.logic.NewsFetchCompletedListener;
import com.example.newsapp.logic.NewsPresenter;

import java.util.List;

public class NewsModel {

    private NewsPresenter mPresenter;
    private NewsDBHelper mDBHelper;

    private NewsFetchCompletedListener listener = new NewsFetchCompletedListener() {
        @Override
        public void onNewsFetchCompleted(List<NewsItem> newsItems) {
            Log.i("Test","onNewsFetchCompleted newsItems: " + newsItems.size());
            mPresenter.onNewsDataAvailable(newsItems);
        }
    };

    public NewsModel(NewsPresenter presenter) {
        mPresenter = presenter;
        mDBHelper = new NewsDBHelper(mPresenter.getView().getContext());
    }

    public void fetchNewsData(String url) {
        Log.i("Test","fetchNewsData: " + url);
        NewsLoader newsLoader = new NewsLoader(listener);
        newsLoader.execute(url);
    }

    public void saveDataToDb(final List<NewsItem> newsItems) {
        Log.i("Test","saveDataToDb newsItems.size: " + newsItems.size());
//        WebPageCacheLoader webCache = new WebPageCacheLoader(mPresenter);
//        webCache.execute(newsItems);
        mDBHelper.insertMutlipleNewsItem(newsItems);
    }

    public void fetchOfflineNews() {
        List<NewsItem> newsItems = mDBHelper.readNewsItems();
        Log.i("Test","fetchOfflineNews newsItems.size: " + newsItems.size());
        mPresenter.onNewsDataAvailable(newsItems);
    }
}
