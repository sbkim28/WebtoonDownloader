package com.ignited.webtoon.translator;

import com.ignited.webtoon.util.ErrorHandler;
import com.ignited.webtoon.util.StringUtils;

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
    public File translate() {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><head><meta charset=\"UTF-8\"><title>")
                .append(StringUtils.toHTML(title))
                .append("</title><style>.layer{position:absolute;left:50%;\ntransform:translate(-50%, 0%)}</style></head><body style=\"margin: 0px; background: #")
                .append(background)
                .append(";\"><div class=\"layer\">");
        for (File file : files){
            htmlBuilder.append("<img ; src=\"")
                    .append(StringUtils.toHTML(file.getPath()))
                    .append("\" >");
        }
        htmlBuilder.append("</div></body></html>");
        File file = writeOn;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            if(file.isDirectory()){
                throw new IllegalArgumentException("File exists but Directory");
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            writer.write(htmlBuilder.toString());
            writer.close();
        }catch (IOException e) {
            ErrorHandler.writeFail(e);
        }
        return file;
    }


}
