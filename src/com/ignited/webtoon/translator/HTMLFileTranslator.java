package com.ignited.webtoon.translator;


import com.ignited.webtoon.util.Compatamizer;

import java.io.*;

/**
 * HTMLFileTranslator
 *
 * Translate Image Files into HTML Files
 * @author Ignited
 * @see HTMLTranslator
 */
public class HTMLFileTranslator extends HTMLTranslator {

    private File[] files;

    /**
     * Instantiates a new Html translator.
     *
     * @param files      the image files
     * @param writeOn    the directory translated file will be located at
     * @param title      the html title
     * @param background the html background
     */
    public HTMLFileTranslator(File[] files, File writeOn, String title, String background) {
        super(writeOn, title, background);
        this.files = files;
    }

    /**
     * Instantiates a new Html translator.
     * Background will be black
     *
     * @param files   the image files
     * @param writeOn the directory translated file will be located at
     * @param title   the html title
     */
    public HTMLFileTranslator(File[] files, File writeOn, String title) {
        this(files,writeOn,title,"000000");
    }

    protected void appendImage(StringBuilder sb){
        for (File file : files){
            sb.append("<img ; src=\"")
                    .append(Compatamizer.toHTML(file.getPath()))
                    .append("\" >");
        }
    }

}
