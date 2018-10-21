package com.ignited.tools.connect;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Linker extends ConnectHolder {

    public static Linker getInstance(Adapter adapter){
        return getInstance(adapter,null);
    }

    public static Linker getInstance(Adapter adapter, Object key){
        ConnectHolder holder = connectorSpec.get(key);
        Linker ret;
        if(holder == null || holder.closed){
            ret = new Linker(key);
        }else{
            ret = new Linker(key, holder);
        }
        ret.setAdapter(adapter);
        return ret;
    }

    private Linker(Object key){
        super(key);
    }

    private Linker(Object key, ConnectHolder holder){
        super(key, holder);
    }

    public void setAdapter(AbstractAdapter adapter){
        if(closed){
            throw new NullPointerException();
        }
        this.adapter = adapter;
        this.connection = null;
    }

    @Override
    public void close(){
        cookieSet = null;
        connection = null;
        adapter = null;
        closed = true;
    }

    @Override
    public boolean connect() {
        if(closed){
            throw new NullPointerException();
        }
        this.connection = adapter.createConnection();
        boolean ret = true;
        if(connection == null){
            ret = false;
        }else {
            cookieSet.attachCookie(connection);
            try {
                connection.connect();
                initCookies(connection.getHeaderFields());
            } catch (IOException e) {
                ret = false;
            }
        }
        return ret;
    }

    public static abstract class Adapter extends AbstractAdapter{

    }
}

