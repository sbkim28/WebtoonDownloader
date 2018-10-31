package com.ignited.webtoon.extract.comic;


import com.ignited.webtoon.util.Compatamizer;

import java.io.IOException;


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
     * The ImageLoader
     */
    protected ImageLoader loader;
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
    }

    /**
     * Download a specific chapter of the webtoon.
     *
     * @param index the index of the chapter
     * @throws IOException when it failed to download and save the webtoon
     */
    public void download(int index) throws IOException {
        saver.save(loader.load(), Compatamizer.factor(getTitle(index)));
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
}
