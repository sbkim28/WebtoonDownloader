package com.ignited.webtoon.extract;


import com.ignited.webtoon.util.Compatamizer;

import java.io.IOException;

public abstract class Downloader {

    protected ComicInfo info;
    protected String path;
    protected ImageLoader loader;
    protected ComicSaver saver;


    public Downloader(ComicInfo info, String path) {
        this.info = info;
        this.path = path;
    }

    public void download(int index) throws IOException {
        saver.save(loader.load(), Compatamizer.factor(getTitle(index)));
    }

    public void setPath(String path) {
        this.path = path;
        saver.setPath(path);
    }


    protected abstract String getTitle(int index);
    public abstract int size();
}
