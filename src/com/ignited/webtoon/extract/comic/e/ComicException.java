package com.ignited.webtoon.extract.comic.e;


/**
 * The Comic exception.
 *
 * @author Ignited
 */
public class ComicException extends Exception {

    /**
     * Instantiates a new Comic exception.
     */
    public ComicException() {
    }

    /**
     * Instantiates a new Comic exception.
     *
     * @param message the message
     */
    public ComicException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Comic exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ComicException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Comic exception.
     *
     * @param cause the cause
     */
    public ComicException(Throwable cause) {
        super(cause);
    }
}
