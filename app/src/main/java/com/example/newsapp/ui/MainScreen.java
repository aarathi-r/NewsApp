package com.example.newsapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.newsapp.R;

public class MainScreen extends AppCompatActivity {

    private static final String NEWS_URL = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNewsFragment(NEWS_URL);
    }

    private void initNewsFragment(String url) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.news_fragment, NewsFragment.newInstance(url));
        ft.commit();
    }
}
