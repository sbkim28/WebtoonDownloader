package com.ignited.webtoon.extract.comic.e;

/**
 * The Comic init exception.
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.e.ComicException
 */
public class ComicInitException extends ComicException {

    /**
     * Instantiates a new Comic init exception.
     */
    public ComicInitException() {
        super();
    }

    /**
     * Instantiates a new Comic init exception.
     *
     * @param message the message
     */
    public ComicInitException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Comic init exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ComicInitException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Comic init exception.
     *
     * @param cause the cause
     */
    public ComicInitException(Throwable cause) {
        super(cause);
    }

}
