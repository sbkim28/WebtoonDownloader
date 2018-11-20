package com.ignited.webtoon.extract.comic.e;

/**
 * The Comic Catalog Exception
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.e.ComicException
 */
public class ComicCatalogException extends ComicException {

    /**
     * Instantiates a new Comic catalog exception.
     */
    public ComicCatalogException() {
        super();
    }

    /**
     * Instantiates a new Comic catalog exception.
     *
     * @param message the message
     */
    public ComicCatalogException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Comic catalog exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ComicCatalogException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Comic catalog exception.
     *
     * @param cause the cause
     */
    public ComicCatalogException(Throwable cause) {
        super(cause);
    }
}
