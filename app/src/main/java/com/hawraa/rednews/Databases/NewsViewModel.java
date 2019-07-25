package com.hawraa.rednews.Databases;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.hawraa.rednews.Models.NewsModel;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private LiveData<List<NewsModel>> favorite;
    private AppDatabase database;
    public NewsViewModel(@NonNull Application application) {
        super(application);
        database = AppDatabase.getDatabase(this.getApplication());
        favorite = database.NewsDAO().getAllNews();
    }

    public LiveData<List<NewsModel>> getFavorite() {
        return favorite;
    }

    public NewsModel getMovieById(String mArticleURL) {
        return  database.NewsDAO().getArticleById(mArticleURL);
    }

    public void addFavorite(NewsModel mArticle){
            database.NewsDAO().insertMovie(mArticle);

    }

    public void removeFavorite(String  mArticle){
        database.NewsDAO().deleteArticle(mArticle);
    }
}
