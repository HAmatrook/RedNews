package com.hawraa.rednews.UTILs;

import com.hawraa.rednews.Models.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtil {

    public static final String KEY_ARTICLES = "articles";

    public static final String KEY_SOURCE = "source";
    public static final String KEY_SOURCE_NAME = "name";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE = "publishedAt";
    public static final String KEY_URL = "url";
    public static final String KEY_IMAGE = "urlToImage";
    public static final String KEY_CONTENT = "content";


    public static List<NewsModel> getNews(String searchResults){
        List<NewsModel>  mNewsList = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(searchResults);
            JSONArray results = jsonObject.getJSONArray(KEY_ARTICLES);


            for (int i=0; i<results.length(); i++){
                JSONObject index = results.getJSONObject(i);
                JSONObject source = index.getJSONObject(KEY_SOURCE);
                String sourceName = source.getString(KEY_SOURCE_NAME);
                String author           = index.optString(KEY_AUTHOR);
                String title            = index.optString(KEY_TITLE);
                String description      = index.optString(KEY_DESCRIPTION);
                String articleURL       = index.optString(KEY_URL);
                String imgURL           = index.optString(KEY_IMAGE);
                String publishDate      = index.optString(KEY_DATE);
                String content          = index.optString(KEY_CONTENT);

                NewsModel mNews = new NewsModel(sourceName, author, title, description,
                         articleURL, imgURL, publishDate, content);
                mNewsList.add(mNews);
            }
        }  catch (JSONException e) {
            e.printStackTrace();
        }
        return mNewsList;
    }

}
