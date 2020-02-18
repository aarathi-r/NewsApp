package com.example.newsapp.data;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.newsapp.logic.NewsFetchCompletedListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTask<String,Void,List<NewsItem>> {

    private static final String JSON_KEY_STATUS = "status";
    private static final String JSON_KEY_ARTICLES = "articles";

    private String mUrl;
    private NewsFetchCompletedListener mListener;

    public NewsLoader(NewsFetchCompletedListener listener) {
        mListener = listener;
    }

    @Override
    protected List<NewsItem> doInBackground(String... strings) {
        List<NewsItem> newsItems = new ArrayList<>();
        mUrl = strings[0];

        Log.i("Test","doInBackground: " + mUrl);

        try {
            URL url = new URL(mUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line + "\n");
            if (sb.length() == 0)
                return newsItems;

            JSONObject response = new JSONObject(sb.toString());
            if (response.has(JSON_KEY_STATUS) && response.getString(JSON_KEY_STATUS).equals("ok")
                    && response.has(JSON_KEY_ARTICLES)) {
                JSONArray articles = response.getJSONArray(JSON_KEY_ARTICLES);
                Log.i("Test","length: " + articles.length());
                for(int i=0; i< articles.length(); i++) {
                    JSONObject article = articles.getJSONObject(i);
                    Gson gson = new Gson();
                    NewsItem newsItem = gson.fromJson(article.toString(), NewsItem.class);
                    Log.i("Test",i + " author: " + newsItem.getAuthor());
                    newsItems.add(newsItem);
                }
            }
            reader.close();
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsItems;
    }

    @Override
    protected void onPostExecute(List<NewsItem> newsItems) {
        super.onPostExecute(newsItems);
        if (mListener != null) {
            mListener.onNewsFetchCompleted(newsItems);
        }
    }
}
