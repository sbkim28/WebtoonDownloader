package com.ignited.webtoon.control.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ignited.webtoon.util.ObjectMapperConfiguration;
import org.jsoup.Jsoup;

import java.io.*;
import java.util.Map;
import java.util.logging.Logger;

public abstract class Login {

    private static final Logger LOGGER = Logger.getLogger(Login.class.getName());

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
            LOGGER.warning("logout failed. (loginCookies=" + loginCookies + ", url="+getLogoutURL()+")");
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

    public boolean isLogin(){
        return loginCookies != null && !loginCookies.isEmpty();
    }

    public Map<String, String> getLoginCookies() {
        return loginCookies;
    }


    public static boolean write(String path, Login login){
        if(!login.isLogin()) throw new IllegalStateException("Not Login");
        File file = new File(path);

        ObjectMapper mapper = ObjectMapperConfiguration.getMapper();
        try {
            mapper.writeValue(file, login);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static <T extends Login> T read(String path, Class<T> clazz) throws IOException {
        File file = new File(path);

        return ObjectMapperConfiguration.getMapper().readValue(file, clazz);

    }
}
