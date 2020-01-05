package com.ignited.webtoon.translator;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;

public class HTMLJsonMultiTranslator extends HTMLTranslator {

    private JsonNode object;
    private int size;
    private int start; // include
    private int end; // exclude

    /**
     * Instantiates a new HTML Json translator.
     *
     * @param object     the comic json object.
     * @param writeOn    the directory translated files will be located at
     * @param background the html background
     */
    public HTMLJsonMultiTranslator(JsonNode object, File writeOn, String background) {
        super(writeOn, object.get("title").asText(), background);
        this.object = object;
        size = object.get("webtoons").size();
        start = 0;
        end = size;
    }

    /**
     * Instantiates a new Html Json translator.
     * Background will be black
     *
     * @param object     the comic json object.
     * @param writeOn the directory translated file will be located at
     */
    public HTMLJsonMultiTranslator(JsonNode object, File writeOn) {
        this(object, writeOn,"000000");
    }

    public void setStart(int start){
        if(start < 0 || start >= size) throw new IllegalArgumentException("Invalid start number {start=" + start + ", size=" + size + "} ");
        this.start = start;
    }
    public void setEnd(int end){
        if(end <= 0 || end > size) throw new IllegalArgumentException("Invalid start number {start=" + start + ", size=" + size + "} ");
        this.end = end;
    }

    @Override
    protected void appendImage(StringBuilder htmlBuilder) {

        JsonNode arr = object.get("webtoons");

        for (JsonNode item : arr){
            JsonNode imgs = item.get("imgs");

            for (JsonNode img : imgs){
                htmlBuilder.append("<img ; src=\"data:")
                        .append(img.get("type").asText())
                        .append(";base64, ")
                        .append(img.get("data").asText())
                        .append("\" >");
            }
        }
    }
}
