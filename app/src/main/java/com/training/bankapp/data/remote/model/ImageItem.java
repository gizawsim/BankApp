package com.training.bankapp.data.remote.model;

public class ImageItem {
    private String url;
    private String title;
    private int likes;

    public ImageItem(String url, String title, int likes) {
        this.url = url;
        this.title = title;
        this.likes = likes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
