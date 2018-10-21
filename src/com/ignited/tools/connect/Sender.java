package com.ignited.tools.connect;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class Sender extends ConnectHolder {

    public static Sender getInstance(Adapter adapter){
        return getInstance(adapter,null);
    }

    public static Sender getInstance(Adapter adapter, Object key){
        ConnectHolder holder = connectorSpec.get(key);
        Sender ret;
        if(holder == null || holder.closed){
            ret = new Sender(key);
        }else{
            ret = new Sender(key, holder);
        }
        ret.setAdapter(adapter);
        return ret;
    }

    private Sender(Object key){
        super(key);
    }

    private Sender(Object key, ConnectHolder holder){
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
    public void close() {
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
                ((HttpURLConnection) connection).setRequestMethod(Method.POST.toString());
                connection.setDoOutput(true);
                OutputStream out = connection.getOutputStream();
                ((Adapter) adapter).write(out);
                connection.connect();
                initCookies(connection.getHeaderFields());
            } catch (IOException e) {
                ret = false;
            }
        }
        return ret;
    }


    public static abstract class Adapter extends AbstractAdapter{
        protected abstract void write(OutputStream out) throws IOException;
    }
}
