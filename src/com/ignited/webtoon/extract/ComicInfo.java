package com.ignited.webtoon.extract;

public class ComicInfo {
    private String id;
    private String title;

    public ComicInfo(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "ComicInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
