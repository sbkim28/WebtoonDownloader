package com.ignited.tools.connect;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class SimpleIndexedLinker extends SimpleLinker implements Iterator<String> {


    protected int index;
    protected List<String> urls;

    public SimpleIndexedLinker(List<String> urls) {
        super("");
        this.urls = urls;
    }

    @Override
    public boolean connect() {
        boolean ret = true;
        try {
            connection = new URL(next()).openConnection();
        } catch (IOException e) {
            connection = null;
            ret = false;
        }
        return ret;
    }

    @Override
    public void setUrl(String url) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasNext() {
        return index < urls.size();
    }

    @Override
    public String next() {
        return urls.get(index++);
    }

}
