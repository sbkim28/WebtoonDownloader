package com.ignited.webtoon.extract.comic.e;

/**
 * The type Comic not found exception.
 */
public class ComicNotFoundException extends ComicException {

    /**
     * Instantiates a new Comic not found exception.
     */
    public ComicNotFoundException() {
    }

    /**
     * Instantiates a new Comic not found exception.
     *
     * @param message the message
     */
    public ComicNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Comic not found exception.
     *
     * @param type the type
     * @param name the name
     */
    public ComicNotFoundException(String type, String name){
        super("Cannot find any comics matching " + name + " : " +  type);
    }

    /**
     * Instantiates a new Comic not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ComicNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Comic not found exception.
     *
     * @param cause the cause
     */
    public ComicNotFoundException(Throwable cause) {
        super(cause);
    }

}
