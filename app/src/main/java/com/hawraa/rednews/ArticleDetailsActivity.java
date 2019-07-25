package com.hawraa.rednews;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hawraa.rednews.Databases.NewsViewModel;
import com.hawraa.rednews.Models.NewsModel;
import com.squareup.picasso.Picasso;

public class ArticleDetailsActivity extends AppCompatActivity {

    public TextView mSourceName;
    public TextView mAuthor;
    public TextView mDate;
    public TextView mContent;
    public ImageView mImage;
    public MenuItem mStar;
    public Toolbar mToolbar;

    public NewsViewModel mViewModel;
    public boolean favorite = false;
    public boolean checkFav = false;
    public String sourceName, author, title, description, articleURL, imgURL, publishDate, content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        mToolbar    = findViewById(R.id.mToolbar);
        mSourceName = findViewById(R.id.source_name_tv);
        mAuthor     = findViewById(R.id.author_tv);
        mDate       = findViewById(R.id.date_tv);
        mContent    = findViewById(R.id.content_tv);
        mImage      = findViewById(R.id.article_img);


        AdView mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        Bundle bundle = getIntent().getExtras();
        NewsModel mNews = bundle.getParcelable(MainActivity.EXTRA_SELECTED_ITEM);

        setViewContents(mNews);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mStar       =  menu.findItem(R.id.favorite_ic);
        new CheckFavoriteTask().execute();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.favorite_ic) {
            new FavoriteTask().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class CheckFavoriteTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (mViewModel.getMovieById(articleURL) != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStar.setIcon(R.drawable.ic_filled_star);
                    }
                });
                      //  setImageResource(R.drawable.ic_filled_star);
                favorite = true;
                checkFav = true;
            }
            return null;
        }
    }
    class FavoriteTask extends AsyncTask<NewsViewModel, Void, NewsViewModel> {
        @Override
        protected NewsViewModel doInBackground(NewsViewModel... newsViewModels) {

            if (favorite) {
                mViewModel.removeFavorite(articleURL);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStar.setIcon(R.drawable.ic_unfilled_star);
                    }
                });
                favorite= false;
            } else {
                NewsModel mNews = new NewsModel(sourceName, author, title, description,
                        articleURL, imgURL, publishDate, content);
                mViewModel.addFavorite(mNews);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStar.setIcon(R.drawable.ic_filled_star);
                    }
                });
                favorite=true;
            }
            return null;
        }
    }

    public void setViewContents(NewsModel mNews){
        mViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        articleURL =  mNews.getArticleURL();
        sourceName = mNews.getSourceName();
        author = mNews.getAuthor();
        title = mNews.getTitle();
        description = mNews.getDescription();
        articleURL =mNews.getArticleURL();
        imgURL = mNews.getImgURL();
        publishDate = mNews.getPublishDate();
        content = mNews.getContent();

        mSourceName.setText(getString(R.string.source_name)+sourceName);
        mAuthor.setText(getString(R.string.author)+author);
        mDate.setText(getString(R.string.publishing_date)+publishDate);
        mContent.setText(content+"\n" +
                "For more: "+ articleURL);
        if(mNews.getImgURL() != null) {
            Picasso.get()
                    .load(mNews.getImgURL())
                    .error(R.drawable.defulte_image)
                    .into(mImage);
        }

        setSupportActionBar(mToolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setTitle(title);
        ab.setDisplayHomeAsUpEnabled(true);
    }



}
