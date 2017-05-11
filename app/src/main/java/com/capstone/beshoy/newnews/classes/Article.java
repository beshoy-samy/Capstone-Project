package com.capstone.beshoy.newnews.classes;

/**
 * Created by bisho on 08-May-17.
 */

public class Article {

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
