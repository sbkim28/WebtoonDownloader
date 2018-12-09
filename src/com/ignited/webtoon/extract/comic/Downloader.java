package com.ignited.webtoon.extract.comic;


import com.ignited.webtoon.extract.comic.e.ComicDownloadException;
import com.ignited.webtoon.util.Compatamizer;

import java.io.IOException;
import java.util.List;


/**
 * Downloader
 *
 * Download Webtoons
 *
 * @author Ignited
 */
public abstract class Downloader {

    /**
     * The Information about webtoon
     */
    protected ComicInfo info;
    /**
     * The location where the webtoon will be saved
     */
    protected String path;
    /**
     * The ComicSaver
     */
    protected ComicSaver saver;

    /**
     * Instantiates a new downloader.
     *
     * @param info the information about webtoon
     * @param path the location where the webtoon will be saved.
     */
    public Downloader(ComicInfo info, String path) {
        this.info = info;
        this.path = path;
        this.saver = new ComicSaver(path);
    }

    /**
     * Download a specific chapter of the webtoon.
     *
     * @param index the index of the chapter
     * @throws ComicDownloadException when it failed to download and save the webtoon
     */
    public void download(int index) throws ComicDownloadException {
        if(path == null) throw new IllegalStateException("Undefined path");
        List<String> srcs = getImages(index);
        saver.save(srcs, Compatamizer.factor(getTitle(index)));

    }


    /**
     * Sets path.
     *
     * @param path the path
     */
    public void setPath(String path) {
        this.path = path;
        saver.setPath(path);
    }


    /**
     * Get the title of one specific chapter.
     *
     * @param index the index of the chapter
     * @return the title of the chapter
     */
    protected abstract String getTitle(int index);

    /**
     * Get the number of the chapter of the webtoon.
     *
     * @return the number of the chapter.
     */
    public abstract int size();

    protected abstract List<String> getImages(int index) throws ComicDownloadException;
}
