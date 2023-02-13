package com.example.foodapp.models;

public class Food {
    private int id;
    private String thumbnail;
    private String title,content;

    public Food(int id, String thumbnail, String title, String content, int price) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.price = price;
    }

    private int price;

    public Food(String thumbnail, String title, String content, int price) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
