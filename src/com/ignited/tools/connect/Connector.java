package com.ignited.tools.connect;

import com.ignited.webtoon.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;

public abstract class Connector implements IConnector {

    protected URLConnection connection;

    @Override
    public InputStream getInputstream() {
        InputStream ret = null;
        if(connection != null || connect()){
            try {
                ret = connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                ret = null;
            }
        }
        return ret;
    }

    @Override
    public String read() {
        InputStream in = getInputstream();
        String ret = null;
        if(in != null) {
            ret = StringUtils.toString(in);
        }
        return ret;
    }

    @Override
    public String read(String charset){
        InputStream in = getInputstream();
        String ret = null;
        if(in != null) {
            ret = StringUtils.toString(in, charset);
        }
        return ret;
    }
}
