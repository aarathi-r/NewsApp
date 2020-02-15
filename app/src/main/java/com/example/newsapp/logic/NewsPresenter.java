package com.example.newsapp.logic;

import com.example.newsapp.data.NewsItem;
import com.example.newsapp.data.NewsModel;
import com.example.newsapp.ui.NewsFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class NewsPresenter {

    WeakReference<NewsFragment> fragment;
    NewsModel mModel;

    public NewsPresenter(NewsFragment newsFragment) {
        fragment = new WeakReference<>(newsFragment);
        mModel = new NewsModel(this);
    }

    public void fetchNewsData(String url) {
        mModel.fetchNewsData(url);
    }

    public void onNewsDataAvailable(List<NewsItem> newsItems) {
        fragment.get().newsDataAvailable(newsItems);
    }
}
