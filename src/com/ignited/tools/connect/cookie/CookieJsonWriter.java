package com.ignited.tools.connect.cookie;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.List;

public class CookieJsonWriter implements CookieWriter{
    private final String RES = "./src/res/";

    private String name;

    public CookieJsonWriter(String name) {
        this.name = name;
    }

    @Override
    public void write(Cookie cookie) throws IOException {
        JsonObject data = new JsonObject();
        JsonArray dataset = new JsonArray();
        dataset.add(makeObj(cookie));

        data.add("data",dataset);
        write(data);
    }

    @Override
    public void write(CookieSet cookieSet) throws IOException {
        JsonObject data = new JsonObject();
        JsonArray dataset = new JsonArray();
        List<Cookie> cookies = cookieSet.getCookieList();
        for (Cookie cookie : cookies) {
            dataset.add(makeObj(cookie));
        }

        data.add("data",dataset);
        write(data);
    }

    private void write(JsonObject object) throws IOException {
        StringBuilder file = new StringBuilder().append(RES).append(name);
        if(!name.endsWith(".json")){
            file.append(".json");
        }
        OutputStream out = new FileOutputStream(file.toString());
        out.write(object.toString().getBytes());
        out.flush();
        out.close();
    }

    private JsonObject makeObj(Cookie cookie){
        JsonObject object = new JsonObject();
        object.addProperty("domain",cookie.getDomain());
        object.addProperty("expires", cookie.getExpires());
        object.addProperty("name", cookie.getName());
        object.addProperty("path", cookie.getPath());
        object.addProperty("value", cookie.getValue());
        return object;
    }
}
