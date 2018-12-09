package com.ignited.webtoon.extract.comic.e;

/**
 * The Comic access exception.
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.comic.e.ComicDownloadException
 * @see com.ignited.webtoon.extract.comic.e.ComicException
 */
public class ComicAccessException extends ComicDownloadException {

    /**
     * Instantiates a new Comic access exception.
     */
    public ComicAccessException() {
        super();
    }

    /**
     * Instantiates a new Comic access exception.
     *
     * @param url the url
     */
    public ComicAccessException(String url) {
        super("Unable to access " + url);
    }

    /**
     * Instantiates a new Comic access exception.
     *
     * @param url    the url
     * @param status the status
     */
    public ComicAccessException(String url, String status){
        super("Unable to access " + url + ", status " + status);
    }

    /**
     * Instantiates a new Comic access exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ComicAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Comic access exception.
     *
     * @param cause the cause
     */
    public ComicAccessException(Throwable cause) {
        super(cause);
    }
}
