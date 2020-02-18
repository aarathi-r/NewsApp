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
        initNewsFragment(NEWS_URL);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initNewsFragment(String url) {
        NewsFragment newsFragment = NewsFragment.newInstance(url);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.news_fragment, newsFragment);
        fragmentTransaction.commit();
    }
}
