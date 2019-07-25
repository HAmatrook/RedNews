package com.hawraa.rednews;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.hawraa.rednews.Models.NewsModel;
import com.hawraa.rednews.UTILs.JSONUtil;
import com.hawraa.rednews.UTILs.NetworkTasks;
import com.hawraa.rednews.UTILs.NetworkUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class WelcomeUI extends AppCompatActivity {
    public static List<NewsModel> mRecentNewsList = new ArrayList<>();
    public static final String NEWS_RESULTS = "news results";

    //ProgressBar progressBar = findViewById(R.id.progressBar);
    ProgressBar progressBar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new NetworkTasks(progressBar,this).execute();
    }


    public static class networkTask extends AsyncTask<Void, Void, String> {
        private ProgressBar mProgressBar;
        private Context mContext;

        public networkTask(ProgressBar progressBar, Context context) {
            this.mProgressBar = progressBar;
            this.mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mProgressBar != null) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            String SearchResults = null;
            try {
                URL News_URL = NetworkUtil.buildUrl();
                SearchResults = NetworkUtil.getResponse(News_URL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mProgressBar != null) {
                mProgressBar.setVisibility(View.GONE);
            }
            return SearchResults;
        }

        @Override
        protected void onPostExecute(String searchResults) {
            if (searchResults != null && !searchResults.equals("")) {
                mRecentNewsList.addAll(JSONUtil.getNews(searchResults));
                Intent intent = new Intent(mContext,MainActivity.class);
               // intent.putExtra(NEWS_RESULTS, (Parcelable) mNewsList);
                mContext.startActivity(intent);
            }
        }
    }
}
