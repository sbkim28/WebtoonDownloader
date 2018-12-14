package com.ignited.webtoon.extract.comic;


import com.ignited.webtoon.extract.comic.e.ComicListInitException;

/**
 * ComicFactory
 * <p>
 * Comic Factory creating finder and donwloader
 *
 * @author Ignited
 */
public interface ComicFactory {
    /**
     * Instantiate and return the new Downloader
     *
     * @param info the information about the webtoon
     * @return the downloader
     */
    Downloader downloader(ComicInfo info, String path) throws ComicListInitException;

    Downloader downloader(ComicInfo info, String path, ComicSaver saver) throws ComicListInitException;

    /**
     * Instantiate and return the new Cataloger
     *
     * @return the cataloger
     */
    Cataloger cataloger();

}
