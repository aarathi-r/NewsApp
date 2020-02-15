package com.example.newsapp.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.newsapp.R;
import com.example.newsapp.data.NewsItem;
import com.example.newsapp.logic.NewsPresenter;

import java.util.List;

public class NewsFragment extends Fragment {
    private static final String URL_KEY = "news_url";

    private String mNewsUrl;
    private NewsPresenter mPresenter;
    NewsAdapter mAdapter;
    RecyclerView mNewsListView;

    /**
     * Create new fragment instance
     *
     * @param newsUrl url to fetch data from.
     * @return A new instance of fragment NewsFragment.
     */
    public static NewsFragment newInstance(String newsUrl) {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment_layout, container, false);
        initNewsList(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initNewsList(View v) {
        mNewsListView = v.findViewById(R.id.news_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mNewsListView.setLayoutManager(layoutManager);
        mAdapter = new NewsAdapter(getContext());
        mNewsListView.setAdapter(mAdapter);
        if (mPresenter != null && mNewsUrl != null) {
            mPresenter.fetchNewsData(mNewsUrl);
        }
    }

    public void newsDataAvailable(List<NewsItem> newsItems) {
        mAdapter.updateNews(newsItems);
    }
}
