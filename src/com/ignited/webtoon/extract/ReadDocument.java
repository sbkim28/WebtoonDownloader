package com.ignited.webtoon.extract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ReadDocument {

    private Document doc;

    public ReadDocument() { }

    public void read(String url) throws IOException {
        doc = Jsoup.connect(url).get();
    }

    public Document getDoc() {
        return doc;
    }
}
