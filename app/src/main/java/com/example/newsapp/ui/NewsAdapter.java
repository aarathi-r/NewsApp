package com.example.newsapp.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.data.DateComparator;
import com.example.newsapp.data.NewsItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsItemViewHolder> {

    Context mContext;
    List<NewsItem> mNewsItems;

    public NewsAdapter(Context context) {
        mContext = context;
    }

    public void updateNews(List<NewsItem> newsItems) {
        Log.i("Test","updateNews newsItems: " + newsItems.size());
        mNewsItems = newsItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_layout, parent, false);
        NewsItemViewHolder holder = new NewsItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        NewsItem newsItem = mNewsItems.get(position);

        holder.getTitleTile().setText(newsItem.getTitle());
        holder.getDescriptionTile().setText(newsItem.getDescription());
        holder.getAuthorTile().setText(newsItem.getAuthor());
        holder.getmPublishedAtTile().setText(newsItem.getPublishedAt());
    }

    @Override
    public int getItemCount() {
        if (mNewsItems != null) {
            return mNewsItems.size();
        }
        return 0;
    }

    public void sortAndShow() {
        mNewsItems.sort(new DateComparator().getOldToNewComparator());
        notifyDataSetChanged();
    }
}
