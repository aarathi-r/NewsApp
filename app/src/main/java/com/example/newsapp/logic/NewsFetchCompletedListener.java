package com.example.newsapp.logic;

import com.example.newsapp.data.NewsItem;

import java.util.List;

public interface NewsFetchCompletedListener {
    void onNewsFetchCompleted(List<NewsItem> newsItems);
}
