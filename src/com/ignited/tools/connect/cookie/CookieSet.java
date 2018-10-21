package com.ignited.tools.connect.cookie;

import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class CookieSet {

    private List<Cookie> cookieList;

    public CookieSet() {
        cookieList = new ArrayList<>();
    }

    public List<Cookie> getCookieList() {
        return new ArrayList<>(cookieList);
    }

    public void addCookies(String cookieData){
        addCookies(new Cookie(cookieData));
    }

    public void addCookies(Cookie cookie){
        boolean flag = false;

        for(Cookie c : cookieList){
            if(c.getName().equals(cookie.getName())){
                c.setValue(cookie.getValue());
                flag = true;
            }
        }
        if(!flag) {
            cookieList.add(cookie);
        }
    }

    public void autoDeleteExpired(){
        Iterator<Cookie> iCookie = cookieList.iterator();
        while(iCookie.hasNext()){
            Cookie cookie = iCookie.next();
            long s = cookie.getExpires();
            if(s < System.currentTimeMillis()){
                iCookie.remove();
            }
        }
    }

    public void attachCookie(URLConnection connection){
        autoDeleteExpired();
        StringBuilder builder = null;
        URL url = connection.getURL();
        for (Cookie cookie : cookieList){
            if(cookie.getDomain() != null && !url.getAuthority().contains(cookie.getDomain())){
                continue;
            }
            if(cookie.getPath() != null && !url.getPath().contains(cookie.getPath()) && !cookie.getPath().equals("/")){
                continue;
            }
            if(builder == null){
                builder = new StringBuilder();
            }else {
                builder.append("; ");
            }
            builder.append(cookie.getCookie());
        }
        if(builder == null) return;
        connection.setRequestProperty("cookie", builder.toString());
    }

    public void clean(){
        cookieList.clear();
    }
}
