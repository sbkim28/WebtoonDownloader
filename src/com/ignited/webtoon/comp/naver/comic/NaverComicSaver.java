package com.ignited.webtoon.comp.naver.comic;

import com.ignited.webtoon.extract.ComicSaver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class NaverComicSaver extends ComicSaver {
    public NaverComicSaver(String path) {
        super(path);
    }

    public NaverComicSaver(String path, String name) {
        super(path, name);
    }

    @Override
    protected InputStream build(String url) throws IOException {

        URLConnection conn = new URL(url).openConnection();
        conn.setRequestProperty("referer", "http://m.naver.com");
        return conn.getInputStream();
    }
}
