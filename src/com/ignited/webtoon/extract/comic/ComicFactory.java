package com.ignited.webtoon.extract.comic;


import com.ignited.webtoon.extract.comic.e.ComicFinderInitException;

import java.io.IOException;

/**
 * ComicFactory
 *
 * Comic Factory creating finder and donwloader
 *
 * @author Ignited
 */
public interface ComicFactory {

    /**
     * Return the finder.
     *
     * @return the finder
     * @throws ComicFinderInitException when it failed to instantiate the new Finder
     */
    Finder finder() throws ComicFinderInitException;

    /**
     * Instantiate and return the new Downloader
     *
     * @param info the information about the webtoon
     * @return the downloader
     */
    Downloader downloader(ComicInfo info);

}
