package com.ignited.webtoon.translator;

import com.ignited.webtoon.util.Compatamizer;

import java.io.File;

/**
 * JsonTranslator
 *
 * Translate Image Files into Json File
 *
 * @author Ignited
 * @see com.ignited.webtoon.translator.FileTranslator
 */
public class JsonTranslator extends FileTranslator implements Runnable{

    private String arrTag;

    /**
     * Instantiates a new Json translator.
     *
     * @param files   the image files
     * @param writeOn the directory translated files will be located at
     */
    public JsonTranslator(File[] files, File writeOn) {
        this(files, writeOn, "imgs");
    }


    /**
     * Instantiates a new Json translator.
     *
     * @param files   the image files
     * @param writeOn the directory translated files will be located at
     * @param arrTag  the arr tag
     */
    public JsonTranslator(File[] files, File writeOn, String arrTag) {
        super(files, writeOn);
        this.arrTag = arrTag;
    }

    /**
     * Sets arr tag.
     *
     * @param arrTag the arr tag
     */
    public void setArrTag(String arrTag) {
        this.arrTag = arrTag;
    }

    @Override
    public String translate() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ \"");
        builder.append(arrTag);
        builder.append(": [ ");
        for(File file : files){
            builder.append("\"");
            builder.append(Compatamizer.toHTML(file.getPath()));
            builder.append("\",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("] }");
        return builder.toString();
    }
}
