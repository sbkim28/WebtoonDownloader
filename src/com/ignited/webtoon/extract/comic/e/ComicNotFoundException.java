package com.ignited.webtoon.extract.comic.e;

import com.ignited.webtoon.extract.comic.Finder;

public class ComicNotFoundException extends ComicException {

    public ComicNotFoundException() {
    }

    public ComicNotFoundException(String message) {
        super(message);
    }

    public ComicNotFoundException(Finder finder, String name){
        super("Cannot find any comics matching " + name + " : " +  finder.getClass().getName());
    }

    public ComicNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComicNotFoundException(Throwable cause) {
        super(cause);
    }

}
