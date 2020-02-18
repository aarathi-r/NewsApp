package com.example.newsapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.data.NewsItem;
import com.example.newsapp.logic.NewsPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewsFragment extends Fragment {
    private static final String CONNECTION_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private static final String URL_KEY = "news_url";

    private String mNewsUrl;
    private NewsPresenter mPresenter;
    private NewsAdapter mAdapter;
    private RecyclerView mNewsListView;
    private RelativeLayout mAppBarLayout;
    private ImageView mSortButton;
    private ImageView mDownloadButton;
    private RelativeLayout mSelectionIndicatorLayout;
    private TextView mSelectedNumberView;
    private ImageView mTickButton;
    private boolean mSortOlderNewToOld;
    private List<NewsItem> mSelectedNewsItems;

    private BroadcastReceiver connChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CONNECTION_CHANGE_ACTION)) {
                if (isNetworkAvailable()) {
                    mPresenter.fetchNewsData(mNewsUrl);
                    setDownloadSortIconVisibility(View.VISIBLE);
                }
            }
        }
    };

    /**
     * Create new fragment instance
     *
     * @param newsUrl url to fetch data from.
     * @return A new instance of fragment NewsFragment.
     */
    static NewsFragment newInstance(String newsUrl) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(URL_KEY, newsUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNewsUrl = getArguments().getString(URL_KEY);
        }
        mPresenter = new NewsPresenter(this);
        mSortOlderNewToOld = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Test", "onCreateView");
        View view = inflater.inflate(R.layout.news_fragment_layout, container, false);
        initNewsList(view);
        initPageTitleBar(view);
        initSelectionIndicatorLayout(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(CONNECTION_CHANGE_ACTION);
        getActivity().registerReceiver(connChangeReceiver, filter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(connChangeReceiver);
    }

    private void initPageTitleBar(View v) {
        mAppBarLayout = v.findViewById(R.id.app_bar_layout);
        setAppBarLayoutVisibility(View.VISIBLE);

        mSortButton = v.findViewById(R.id.sort_icon);
        mSortButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sortNews();
            }
        });

        mDownloadButton = v.findViewById(R.id.download_icon);
        mDownloadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectionLayoutVisibility(View.VISIBLE);
                setAppBarLayoutVisibility(View.GONE);
            }
        });
        if (!isNetworkAvailable()) {
            setDownloadSortIconVisibility(View.GONE);
        }
    }

    private void initSelectionIndicatorLayout(View v) {
        mSelectionIndicatorLayout = v.findViewById(R.id.selection_indicator_layout);

        mSelectedNumberView = v.findViewById(R.id.selected_number_view);
        updateSelectedCount(0);

        mTickButton = v.findViewById(R.id.tick_icon);
        mTickButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectionLayoutVisibility(View.GONE);
                setAppBarLayoutVisibility(View.VISIBLE);
            }
        });
    }

    private void setDownloadSortIconVisibility(int visibility) {
        if (mSortButton != null && mDownloadButton != null) {
            mSortButton.setVisibility(visibility);
            mDownloadButton.setVisibility(visibility);
        }
    }

    private void setAppBarLayoutVisibility(int visibility) {
        mAppBarLayout.setVisibility(visibility);
    }

    private void setSelectionLayoutVisibility(int visibility) {
        mSelectionIndicatorLayout.setVisibility(visibility);
        if (visibility == View.GONE) {
            mAdapter.setSelectEnabled(false);
        } else {
            mAdapter.setSelectEnabled(true);
        }
        updateSelectedCount(0);
        saveSelectedNewsOffline();
        mAdapter.notifyDataSetChanged();
    }

    public void updateSelectedCount(int count) {
        mSelectedNumberView.setText(String.format(Locale.ENGLISH,
                getResources().getString(R.string.selected_number_text), count));
    }

    private void initNewsList(View v) {
        mNewsListView = v.findViewById(R.id.news_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mNewsListView.setLayoutManager(layoutManager);
        mAdapter = new NewsAdapter(this);
        mNewsListView.setAdapter(mAdapter);
        if (!isNetworkAvailable()) {
            mPresenter.fetchOfflineNews();
        } else if (mPresenter != null && mNewsUrl != null) {
            mPresenter.fetchNewsData(mNewsUrl);
        }
    }

    private void saveSelectedNewsOffline(){
        List<NewsItem> allNews = mAdapter.getNewsData();
        mSelectedNewsItems = new ArrayList<>();
        if (allNews != null && allNews.size() > 0) {
            for (NewsItem newsItem : allNews) {
                if (newsItem.isSelected()) {
                    newsItem.setSelected(false);
                    mSelectedNewsItems.add(newsItem);
                    Log.i("Test","saveSelectedNewsOffline: " + newsItem.getTitle());
                }
            }
        }
        mPresenter.saveDataToDb(mSelectedNewsItems);
    }

    private void sortNews() {
        mSortOlderNewToOld = !mSortOlderNewToOld;
        mAdapter.sortAndShow(mSortOlderNewToOld);
    }

    public void newsDataAvailable(List<NewsItem> newsItems) {
        if(newsItems == null || newsItems.size() == 0) {
            Toast.makeText(getContext(), R.string.no_news_available, Toast.LENGTH_LONG).show();
        } else {
            mAdapter.updateNews(newsItems);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
