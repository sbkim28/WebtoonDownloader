package com.ignited.tools.connect;

import com.ignited.tools.connect.cookie.CookieSet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ConnectHolder extends Connector{

    protected static Map<Object, ConnectHolder> connectorSpec;
    protected CookieSet cookieSet;
    protected boolean closed;
    protected AbstractAdapter adapter;

    private Object key;

    static {
        connectorSpec = new HashMap<>();
    }

    protected ConnectHolder(Object key) {
        this.key = key;
        connectorSpec.put(key, this);
        closed = false;
        cookieSet = new CookieSet();
    }

    protected ConnectHolder(Object key, ConnectHolder holder) {
        if(holder.closed) throw new NullPointerException();
        connectorSpec.put(key, this);
        cookieSet = holder.cookieSet;
        closed = false;
    }

    public CookieSet getCookieSet() {
        return cookieSet;
    }

    public void setCookieSet(CookieSet cookieSet) {
        this.cookieSet = cookieSet;
    }

    public Object getKey() {
        return key;
    }

    public abstract void close();

    protected void initCookies(Map<String, List<String>> headers){
        List<String> values = headers.get("Set-Cookie");
        if(values == null) return;
        for (String s : values) {
            cookieSet.addCookies(s);
        }
    }

    public static abstract class AbstractAdapter {

        public final URLConnection createConnection(){

            URLConnection ret = null;
            try {
                ret = getURL().openConnection();
                initialize(ret);
            } catch (IOException e) {
                e.printStackTrace();
                ret = null;
            }
            return ret;
        }

        protected abstract URL getURL() throws MalformedURLException;

        protected abstract void initialize(URLConnection connection);

    }
}
