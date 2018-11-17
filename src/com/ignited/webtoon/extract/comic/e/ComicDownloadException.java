package com.ignited.webtoon.extract.comic.e;

/**
 * The Comic download exception
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.e.ComicException
 */
public class ComicDownloadException extends ComicException{

    /**
     * Instantiates a new Comic download exception.
     */
    public ComicDownloadException() {
    }

    /**
     * Instantiates a new Comic download exception.
     *
     * @param message the message
     */
    public ComicDownloadException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Comic download exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ComicDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Comic download exception.
     *
     * @param cause the cause
     */
    public ComicDownloadException(Throwable cause) {
        super(cause);
    }
}
