package com.ignited.webtoon.translator;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;

public class HTMLJsonTranslator extends HTMLTranslator {

    private JsonNode object;

    /**
     * Instantiates a new HTML Json translator.
     *
     * @param object     the comic json object.
     * @param writeOn    the directory translated files will be located at
     * @param background the html background
     */
    public HTMLJsonTranslator(JsonNode object, File writeOn, String background) {
        super(writeOn, object.get("title").asText(), background);
        this.object = object;
    }

    /**
     * Instantiates a new Html Json translator.
     * Background will be black
     *
     * @param object     the comic json object.
     * @param writeOn the directory translated file will be located at
     */
    public HTMLJsonTranslator(JsonNode object, File writeOn) {
        this(object, writeOn,"000000");
    }

    @Override
    public void write() throws IOException {
        super.write();
    }

    @Override
    protected void appendImage(StringBuilder htmlBuilder) {

        JsonNode arr = object.get("imgs");

        for (JsonNode node : arr){
            htmlBuilder.append("<img ; src=\"data:")
                    .append(node.get("type").asText())
                    .append(";base64, ")
                    .append(node.get("data").asText())
                    .append("\" >");
        }
    }
}
