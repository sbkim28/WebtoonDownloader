package com.ignited.tools.connect.adapter;

import com.ignited.tools.connect.Linker;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

public class IndexedLinkerAdapter extends Linker.Adapter implements Iterator<String>{
    protected int index;
    protected List<String> urls;

    public IndexedLinkerAdapter(List<String> urls) {
        index = 0;
        this.urls = urls;
    }

    @Override
    protected URL getURL() throws MalformedURLException {
        URL ret = null;
        ret = new URL(next());
        return ret;
    }

    @Override
    protected void initialize(URLConnection connection) { }

    @Override
    public boolean hasNext() {
        return index < urls.size();
    }

    @Override
    public String next() {
        return urls.get(index++);
    }
}
