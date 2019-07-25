package com.hawraa.rednews.Databases;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.hawraa.rednews.Models.NewsModel;

import java.util.List;

@Dao
public interface DataAO {

    @Query("Select * from NewsModel")
    public LiveData<List<NewsModel>> getAllNews();

    @Query("Select * from NewsModel where articleURL = :mArticleURL")
    public NewsModel getArticleById(String mArticleURL);

    @Insert
    public void insertMovie(NewsModel movie);

    @Query("DELETE FROM NewsModel WHERE articleURL = :mArticleURL")
    void deleteArticle(String mArticleURL);

}
