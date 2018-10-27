package com.ignited.webtoon.translator;


import com.ignited.webtoon.util.Compatamizer;

import java.io.*;

public class HTMLTranslator extends FileTranslator {

    private String title;
    private String background;

    public HTMLTranslator(File[] files, File writeOn, String title, String background) {
        super(files, writeOn);
        this.title = title;
        this.background = background;
    }

    public HTMLTranslator(File[] files, File writeOn, String title) {
        this(files,writeOn,title,"000000");
    }

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
