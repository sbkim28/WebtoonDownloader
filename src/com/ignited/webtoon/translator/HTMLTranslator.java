package com.ignited.webtoon.translator;


import com.ignited.webtoon.util.Compatamizer;

import java.io.*;

/**
 * HTMLTranslator
 *
 * Translate Image Files into HTML Files
 * @author Ignited
 * @see com.ignited.webtoon.translator.FileTranslator
 */
public class HTMLTranslator extends FileTranslator implements Runnable {

    private String title;
    private String background;

    /**
     * Instantiates a new Html translator.
     *
     * @param files      the image files
     * @param writeOn    the directory translated file will be located at
     * @param title      the html title
     * @param background the html background
     */
    public HTMLTranslator(File[] files, File writeOn, String title, String background) {
        super(files, writeOn);
        this.title = title;
        this.background = background;
    }

    /**
     * Instantiates a new Html translator.
     * Background will be black
     *
     * @param files   the image files
     * @param writeOn the directory translated file will be located at
     * @param title   the html title
     */
    public HTMLTranslator(File[] files, File writeOn, String title) {
        this(files,writeOn,title,"000000");
    }

    /**
     * Sets background color.
     *
     * @param background the background color
     */
    public void setBackground(String background) {
        this.background = background;
    }

    @Override
    public String translate() {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><head><meta charset=\"UTF-8\"><title>")
                .append(Compatamizer.toHTML(title))
                .append("</title><style>.layer{position:absolute;left:50%;\ntransform:translate(-50%, 0%)}</style></head><body style=\"margin: 0px; background: #")
                .append(background)
                .append(";\"><div class=\"layer\">");
        for (File file : files){
            htmlBuilder.append("<img ; src=\"")
                    .append(Compatamizer.toHTML(file.getPath()))
                    .append("\" >");
        }
        htmlBuilder.append("</div></body></html>");
        return htmlBuilder.toString();
    }

}
