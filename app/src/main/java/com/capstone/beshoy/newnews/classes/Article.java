package com.capstone.beshoy.newnews.classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bisho on 08-May-17.
 */

public class Article implements Parcelable{

    private String source;
    private String author;
    private String title;
    private String description;
    private String articleURL;
    private String imageURL;
    private String publishedAt;

    public Article(String source, String author, String title, String description, String articleURL, String imageURL, String publishedAt) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.articleURL = articleURL;
        this.imageURL = imageURL;
        this.publishedAt = publishedAt;
        this.source = source;
    }

    private Article(Parcel in){
        source = in.readString();
        author = in.readString();
        title = in.readString();
        description = in.readString();
        articleURL = in.readString();
        imageURL = in.readString();
        publishedAt = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(source);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(articleURL);
        dest.writeString(imageURL);
        dest.writeString(publishedAt);
    }


}
