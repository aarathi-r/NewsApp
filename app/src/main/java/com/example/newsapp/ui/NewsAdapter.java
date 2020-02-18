package com.example.newsapp.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.data.DateComparator;
import com.example.newsapp.data.NewsItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsItemViewHolder> implements Filterable  {

    private NewsFragment mFragment;
    private Context mContext;
    private List<NewsItem> mNewsItems;
    private boolean mSelectEnabled;

    private int mCount;

    public NewsAdapter(NewsFragment fragment) {
        mFragment = fragment;
        mContext = fragment.getContext();
        mSelectEnabled = false;
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
    public void onBindViewHolder(@NonNull final NewsItemViewHolder holder, int position) {
        final int pos = position;
        final NewsItem newsItem = mNewsItems.get(pos);

        holder.getRootView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lauchNewsWebView(newsItem);
            }
        });

        holder.getTitleTile().setText(newsItem.getTitle());
        holder.getDescriptionTile().setText(newsItem.getDescription());
        holder.getAuthorTile().setText(newsItem.getAuthor());
        holder.getPublishedAtTile().setText(newsItem.getPublishedAt());

        if (mSelectEnabled) {
            holder.getCheckBox().setVisibility(View.VISIBLE);
        } else {
            holder.getCheckBox().setVisibility(View.GONE);
        }

        holder.getCheckBox().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = holder.getCheckBox().isChecked();
                mNewsItems.get(pos).setSelected(isChecked);
                if(isChecked) {
                    mCount++;
                } else {
                    mCount--;
                }
                mFragment.updateSelectedCount(mCount);
            }
        });

        holder.getCheckBox().setChecked(newsItem.isSelected());
    }

    @Override
    public int getItemCount() {
        if (mNewsItems != null) {
            return mNewsItems.size();
        }
        return 0;
    }

    public void sortAndShow(boolean sortOlderNewToOld) {
        if (sortOlderNewToOld) {
            Toast.makeText(mContext, R.string.sort_ascending, Toast.LENGTH_SHORT).show();
            mNewsItems.sort(new DateComparator().getNewToOldComparator());
            notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, R.string.sort_descending, Toast.LENGTH_SHORT).show();
            mNewsItems.sort(new DateComparator().getOldToNewComparator());
            notifyDataSetChanged();
        }
    }

    private void lauchNewsWebView(NewsItem newsItem) {
        WebView webView = new WebView(mContext);
        webView.getSettings().setAppCachePath(mFragment.getActivity().getApplicationContext().getCacheDir().getAbsolutePath());
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true );
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        if (!isNetworkAvailable()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        Log.i("Test", "cache path: " + mFragment.getActivity().getApplicationContext().getCacheDir().getAbsolutePath());
        webView.loadUrl(newsItem.getUrl());
    }

    public List<NewsItem> getNewsData() {
        return mNewsItems;
    }

    public void setSelectEnabled(boolean value) {
        mSelectEnabled = value;
        mCount = 0;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
