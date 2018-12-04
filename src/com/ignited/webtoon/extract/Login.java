package com.ignited.webtoon.extract;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;

import java.io.*;
import java.util.Map;

public abstract class Login {

    private String id;
    private String password;

    protected Map<String, String> loginCookies;

    public Login(String id, String password){
        this.id = id;
        this.password = password;
    }

    public boolean logout(){
        int res;
        try {
            res = Jsoup.connect(getLogoutURL())
                    .cookies(loginCookies)
                    .execute()
                    .statusCode();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if(res == 200){
            loginCookies = null;
            return true;
        }else {
            return false;
        }
    }

    protected abstract String getLogoutURL();

    public abstract boolean login() throws LoginFailException;

    protected String getId() {
        return id;
    }

    protected String getPassword() {
        return password;
    }

    protected ExclusionStrategy getExclusionStrategy(){
        return null;
    }

    public boolean isLogin(){
        return loginCookies != null && !loginCookies.isEmpty();
    }

    public Map<String, String> getLoginCookies() {
        return loginCookies;
    }



    public static boolean write(String path, Login login){
        if(!login.isLogin()) throw new IllegalStateException("Not Login");
        File file = new File(path);
        try(FileWriter writer = new FileWriter(file)) {
            ExclusionStrategy es = login.getExclusionStrategy();
            GsonBuilder builder = new GsonBuilder();
            if(es != null){
                builder.setExclusionStrategies(es);
            }
            builder.create().toJson(login, writer);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    public static <T extends Login> T read(String path, Class<T> clazz) throws IOException {
        File file = new File(path);
        try (FileReader writer = new FileReader(file)){
            return new Gson().fromJson(writer, clazz);
        }
    }
}
