package com.ignited.webtoon.extract;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;


/**
 * ReadCookieDocument
 * Read Document using cookies
 *
 * @author Ignited
 * @see com.ignited.webtoon.extract.ReadDocument
 */
public class ReadCookieDocument extends ReadDocument {

    private Map<String, String> cookies;

    /**
     * Instantiates a new Read cookie document.
     */
    public ReadCookieDocument() { }


    /**
     * Instantiates a new Read cookie document.
     *
     * @param cookies the cookies
     */
    public ReadCookieDocument(Map<String, String> cookies) {
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

    @Override
    public void read(String url) throws IOException {
        doc = Jsoup.connect(url)
                .cookies(cookies).get();
    }
}
