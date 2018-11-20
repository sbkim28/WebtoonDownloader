package com.ignited.webtoon.extract.comic.e;

/**
 * The Comic list init exception.
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.e.ComicException
 */
public class ComicListInitException extends ComicException {

    /**
     * Instantiates a new Comic list init exception.
     */
    public ComicListInitException() {
    }

    /**
     * Instantiates a new Comic list init exception.
     *
     * @param message the message
     */
    public ComicListInitException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Comic list init exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ComicListInitException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Comic list init exception.
     *
     * @param cause the cause
     */
    public ComicListInitException(Throwable cause) {
        super(cause);
    }
}
