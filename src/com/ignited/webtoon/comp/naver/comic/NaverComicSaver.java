package com.ignited.webtoon.comp.naver.comic;

import com.ignited.webtoon.extract.comic.ComicSaver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * NaverComicSaver
 *
 * Save Naver Webtoons
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.ComicSaver
 */
public class NaverComicSaver extends ComicSaver {

    /**
     * Instantiates a new Naver comic saver.
     *
     * @param path the location where the naver webtoon will be saved
     */
    public NaverComicSaver(String path) {
        super(path);
    }

    /**
     * Instantiates a new Naver comic saver.
     *
     * @param path the location where the naver webtoon will be saved
     * @param name the name
     */
    public NaverComicSaver(String path, String name) {
        super(path, name);
    }

    @Override
    protected URLConnection build(String url) throws IOException {

        URLConnection conn = new URL(url).openConnection();
        conn.setRequestProperty("referer", "http://m.naver.com");
        return conn;
    }
}
