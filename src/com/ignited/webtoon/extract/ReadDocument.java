package com.ignited.webtoon.extract;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;


/**
 * ReadDocument
 * Read HTML Document
 *
 * @author Ignited
 */
public class ReadDocument {

    /**
     * The Html document.
     */
    protected Document doc;
    private Map<String, String> cookies;

    /**
     * Instantiates a new Read cookie document.
     */
    public ReadDocument() { }


    /**
     * Instantiates a new Read cookie document.
     *
     * @param cookies the cookies
     */
    public ReadDocument(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    /**
     * Sets cookies.
     *
     * @param cookies the cookies
     */
    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public void read(String url) throws IOException {
        Connection c = Jsoup.connect(url);
        if(cookies != null){
            c = c.cookies(cookies);
        }
        doc = c.get();
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
