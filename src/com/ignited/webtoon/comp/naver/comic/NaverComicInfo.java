package com.ignited.webtoon.comp.naver.comic;

import com.ignited.webtoon.extract.ComicInfo;

public class NaverComicInfo extends ComicInfo {

    private String image;

    public NaverComicInfo(String id, String title) {
        this(id, title, null);
    }

    public NaverComicInfo(String id, String title, String image) {
        super(id, title);
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
