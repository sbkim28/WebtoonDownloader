package com.ignited.webtoon.translator;

import com.ignited.webtoon.util.Compatamizer;

import java.io.File;

public class JsonTranslator extends FileTranslator{

    private String arrTag;

    public JsonTranslator(File[] files, File writeOn) {
        this(files, writeOn, "imgs");
    }



    public JsonTranslator(File[] files, File writeOn, String arrTag) {
        super(files, writeOn);
        this.arrTag = arrTag;
    }

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
