package com.ignited.webtoon.extract.comic;


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
     * @throws IOException when it failed to instantiate the new Finder
     */
    Finder finder() throws IOException;

    /**
     * Instantiate and return the new Downloader
     *
     * @param info the information about the webtoon
     * @return the downloader
     */
    Downloader downloader(ComicInfo info);

}
