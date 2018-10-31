package com.ignited.webtoon.extract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


/**
 * ReadDocument
 * Read Html Document
 */
public class ReadDocument {

    /**
     * The Html document.
     */
    protected Document doc;

    /**
     * Instantiates a new Read document.
     */
    public ReadDocument() { }

    /**
     * Read.
     *
     * @param url the url
     * @throws IOException the io exception
     */
    public void read(String url) throws IOException {
        doc = Jsoup.connect(url).get();
    }

    /**
     * Gets doc.
     *
     * @return the doc
     */
    public Document getDoc() {
        return doc;
    }
}
