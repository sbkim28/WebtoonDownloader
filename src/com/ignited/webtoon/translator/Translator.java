package com.ignited.webtoon.translator;


/**
 * Translator
 *
 * Translate an Image File to an Easily Accessible Form
 *
 * @author Ignited
 */
public interface Translator extends Runnable{

    /**
     * Translate string.
     *
     * @return the string
     */
    String translate();

}
