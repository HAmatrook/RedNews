package com.hawraa.rednews.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class NewsModel  implements Parcelable{
    @PrimaryKey
    @NonNull
    private String title;
    private String articleURL;
    private String sourceName;
    private String author;
    private String description;
    private String imgURL;
    private String publishDate;
    private String content;

    public NewsModel(String sourceName, String author, String title, String description,
                     String articleURL, String imgURL, String publishDate, String content) {
        this.sourceName = sourceName;
        this.author = author;
        this.title = title;
        this.description = description;
        this.articleURL = articleURL;
        this.imgURL = imgURL;
        this.publishDate = publishDate;
        this.content = content;
    }


    protected NewsModel(Parcel in) {
        sourceName = in.readString();
        author = in.readString();
        title = in.readString();
        description = in.readString();
        articleURL = in.readString();
        imgURL = in.readString();
        publishDate = in.readString();
        content = in.readString();
    }

    public static final Creator<NewsModel> CREATOR = new Creator<NewsModel>() {
        @Override
        public NewsModel createFromParcel(Parcel in) {
            return new NewsModel(in);
        }

        @Override
        public NewsModel[] newArray(int size) {
            return new NewsModel[size];
        }
    };

    public String getSourceName() {
        return sourceName;
    }

    public String getAuthor() {
        if (author.contains("www."))
            return "-";
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getContent() {
        if(content.isEmpty())
            return description;
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sourceName);
        parcel.writeString(author);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(articleURL);
        parcel.writeString(imgURL);
        parcel.writeString(publishDate);
        parcel.writeString(content);
    }
}
