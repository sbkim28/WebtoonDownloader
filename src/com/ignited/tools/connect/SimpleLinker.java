package com.ignited.tools.connect;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleLinker extends Connector {

    private String url;

    public SimpleLinker(String url) {
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean connect(){
        boolean ret = true;
        try {
            connection = new URL(url).openConnection();
        } catch (IOException e) {
            connection = null;
            ret = false;
        }
        return ret;
    }
}
