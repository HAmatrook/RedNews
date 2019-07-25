package com.hawraa.rednews.UTILs;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.hawraa.rednews.MainActivity;
import com.hawraa.rednews.Models.NewsModel;
import com.hawraa.rednews.R;
import com.hawraa.rednews.WelcomeUI;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.net.URL;

import static com.hawraa.rednews.WelcomeUI.mRecentNewsList;
import static com.hawraa.rednews.Widgets.NewsWidgetProvider.mAppWidgetManager;
import static com.hawraa.rednews.Widgets.NewsWidgetProvider.mWidget;
import static com.hawraa.rednews.Widgets.NewsWidgetProvider.views;

public  class NetworkTasks extends AsyncTask<Void, Void, String> {
    private ProgressBar mProgressBar;
    private Context mContext;
    public static NewsModel item;
    public NetworkTasks(ProgressBar progressBar, Context context) {
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
            if (mContext instanceof WelcomeUI) {
                Intent intent = new Intent(mContext, MainActivity.class);
                // intent.putExtra(NEWS_RESULTS, (Parcelable) mNewsList);
                mContext.startActivity(intent);
            }else {
                if( mRecentNewsList !=null) {
                    item = mRecentNewsList.get(0);
                    Picasso.get().load(item.getImgURL())
                    .into(new Target() {
                              @Override
                              public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                  views.setImageViewBitmap(R.id.news_img, bitmap);
                              }

                              @Override
                              public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                              }

                              @Override
                              public void onPrepareLoad(Drawable placeHolderDrawable) {

                              }
                          }
                        );

                    views.setTextViewText(R.id.news_title_tv, item.getTitle());
                    views.setTextViewText(R.id.news_article_tv, item.getContent());

                }

                       else {
                    views.setTextViewText(R.id.news_title_tv,"No news to view");
                }
                mAppWidgetManager.updateAppWidget(mWidget, views);

            }
        }
    }
    public static NewsModel getItem(){
        return item;
    }
}