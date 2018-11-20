package com.ignited.webtoon.extract.comic.e;

public class ComicNotFoundException extends ComicException {

    public ComicNotFoundException() {
    }

    public ComicNotFoundException(String message) {
        super(message);
    }

    public ComicNotFoundException(String type, String name){
        super("Cannot find any comics matching " + name + " : " +  type);
    }

    public ComicNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComicNotFoundException(Throwable cause) {
        super(cause);
    }

}
