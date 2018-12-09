package com.ignited.webtoon.extract.comic;

import com.ignited.webtoon.extract.comic.e.ComicDownloadException;

import java.util.List;

public class ComicByteSaver extends ComicSaver {

    StringBuilder builder;

    public ComicByteSaver(String path) {
        super(path);
        builder = new StringBuilder();
    }

    @Override
    public void save(List<String> src, String title) throws ComicDownloadException {
        int index = 0;
        boolean ex = false;
    }
}
