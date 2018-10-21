package com.ignited.webtoon.translator;

import com.ignited.webtoon.util.ErrorHandler;
import com.ignited.webtoon.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    public File translate() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ \"");
        builder.append(arrTag);
        builder.append(": [ ");
        for(File file : files){
            builder.append("\"");
            builder.append(StringUtils.toHTML(file.getPath()));
            builder.append("\",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("] }");

        File file = writeOn;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            if(file.isDirectory()){
                throw new IllegalArgumentException("File exists but Directory");
            }
            FileWriter writer = new FileWriter(file);
            writer.write(builder.toString());
            writer.close();
        }catch (IOException e) {
            ErrorHandler.writeFail(e);
        }
        return file;
    }
}
