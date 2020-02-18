package com.example.newsapp.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.newsapp.logic.NewsPresenter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class WebPageCacheLoader extends AsyncTask<List<NewsItem>,Void,Void> {

    NewsPresenter mPresenter;
    int count;

    public WebPageCacheLoader(NewsPresenter presenter){
        mPresenter = presenter;
        count = 0;
    }

    @Override
    protected Void doInBackground(List<NewsItem>... lists) {
        List<NewsItem> newsItems = lists[0];

        for (NewsItem newsItem : newsItems) {
            URL url;
            try {
                Log.i("Test","newsItem.getUrl: " + newsItem.getUrl());
                url = new URL(newsItem.getUrl());
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    sb.append(line + "\n");
                Log.i("Test", "reponse: " + sb.toString());
                if (sb.toString() != null) {
                    writeHtmlFile(sb.toString(), count++);
                }
                reader.close();
                urlConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void writeHtmlFile(String html, int count) {
        Context applicationContext = mPresenter.getView().getActivity().getApplicationContext();
        File fileHtml = new File(applicationContext.getExternalFilesDir("/com.example.newsapp"), "html_"+count+".html");

        BufferedWriter bufferedWriter = null;
        try {
            fileHtml.createNewFile();

            FileOutputStream out = new FileOutputStream(fileHtml);
            byte[] data = html.getBytes();
            out.write(data);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
