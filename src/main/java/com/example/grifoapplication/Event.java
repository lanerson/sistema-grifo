package com.example.grifoapplication;

public class Event {
    private String title;
    private int imageResId;

    private String description;

    public Event(String title, int imageResId, String description) {
        this.title = title;
        this.imageResId = imageResId;
        this.description = description;
    }

    public Event(String title, int imageResId) {
        this.title = title;
        this.imageResId = imageResId;
        this.description = "Sem descrição";
    }

    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }
}
