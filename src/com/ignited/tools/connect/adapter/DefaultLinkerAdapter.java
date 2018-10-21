package com.ignited.tools.connect.adapter;

import com.ignited.tools.connect.Linker;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DefaultLinkerAdapter extends Linker.Adapter {

    private String url;

    public DefaultLinkerAdapter(String url) {
        this.url = url;
    }

    @Override
    protected URL getURL() throws MalformedURLException {
        return new URL(url);
    }

    @Override
    protected void initialize(URLConnection connection) { }
}
