package com.ignited.tools.connect.cookie;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ignited.webtoon.util.StringUtils;

import java.io.*;

public class CookieJsonReader implements CookieReader{

    private final String RES = "./src/res/";

    private String name;

    public CookieJsonReader(String name) {
        this.name = name;
    }

    @Override
    public CookieSet read() throws IOException {
        StringBuilder file = new StringBuilder().append(RES).append(name);
        if(!name.endsWith(".json")){
            file.append(".json");
        }
        JsonArray array = new JsonParser().parse(StringUtils.toString(
                new FileInputStream(file.toString())
        )).getAsJsonObject().get("data").getAsJsonArray();
        CookieSet ret = new CookieSet();
        for(int i = 0;i<array.size();++i){
            JsonObject object = array.get(i).getAsJsonObject();
            ret.addCookies(new Cookie(
                    object.get("name").getAsString(),
                    object.get("value").getAsString(),
                    object.get("path").getAsString(),
                    object.get("domain").getAsString(),
                    object.get("expires").getAsLong()));
        }
        return ret;
    }

}
