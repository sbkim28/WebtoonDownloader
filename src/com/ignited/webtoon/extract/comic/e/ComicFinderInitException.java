package com.ignited.webtoon.extract.comic.e;

/**
 * The Comic finder init exception.
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.e.ComicException
 */
public class ComicFinderInitException extends ComicException {

    /**
     * Instantiates a new Comic finder init exception.
     */
    public ComicFinderInitException() {
        super();
    }

    /**
     * Instantiates a new Comic finder init exception.
     *
     * @param message the message
     */
    public ComicFinderInitException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Comic finder init exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ComicFinderInitException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Comic finder init exception.
     *
     * @param cause the cause
     */
    public ComicFinderInitException(Throwable cause) {
        super(cause);
    }
}
