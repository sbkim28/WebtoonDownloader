package com.ignited.webtoon.translator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;

public class HTMLJsonTranslator extends HTMLTranslator {

    private JsonObject object;

    /**
     * Instantiates a new HTML Json translator.
     *
     * @param object     the comic json object.
     * @param writeOn    the directory translated files will be located at
     * @param background the html background
     */
    public HTMLJsonTranslator(JsonObject object, File writeOn, String background) {
        super(writeOn, object.get("title").getAsString(), background);
        this.object = object;
    }

    /**
     * Instantiates a new Html Json translator.
     * Background will be black
     *
     * @param object     the comic json object.
     * @param writeOn the directory translated file will be located at
     */
    public HTMLJsonTranslator(JsonObject object, File writeOn) {
        this(object, writeOn,"000000");
    }

    @Override
    public void write() throws IOException {
        super.write();
    }

    @Override
    protected void appendImage(StringBuilder htmlBuilder) {

        JsonArray ja = object.get("imgs").getAsJsonArray();

        for (int i = 0; i<ja.size(); ++i){
            JsonObject jo = ja.get(i).getAsJsonObject();
            htmlBuilder.append("<img ; src=\"data:")
                    .append(jo.get("type").getAsString())
                    .append(";base64, ")
                    .append(jo.get("data").getAsString())
                    .append("\" >");
        }
    }
}
