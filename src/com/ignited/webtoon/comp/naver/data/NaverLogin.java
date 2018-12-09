package com.ignited.webtoon.comp.naver.data;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.ignited.webtoon.control.login.Login;
import com.ignited.webtoon.control.login.LoginFailException;
import com.ignited.webtoon.control.login.WrongCaptchaException;
import com.ignited.webtoon.control.login.WrongIdPasswordException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NaverLogin extends Login{

    private static final String CAPTCHA = "https://nid.naver.com/login/image/captcha/nhncaptchav4.gif?1&key=";

    private static final String KEYS = "https://nid.naver.com/login/ext/keys.nhn";
    private static final String LOGIN = "https://nid.naver.com/nidlogin.login";

    private String chptchakey;
    private boolean onCaptcha;

    public NaverLogin(String id, String password) {
        super(id, password);
    }

    @Override
    protected String getLogoutURL() {
        return "https://nid.naver.com/nidlogin.logout";
    }

    @Override
    public boolean login() throws LoginFailException {
        return login(null);
    }

    public boolean login(String captcha) throws LoginFailException {

        if(isLogin()) logout();

        NaverRSA module = new NaverRSA();
        String[] keys;
        try {
            keys = getKeys();
        } catch (IOException e){
            throw new LoginFailException("Getting key failed.", e);
        }
        String encnm = keys[1];
        String encpw;
        String enc = getValueWithLength(keys[0]) + getValueWithLength(getId()) + getValueWithLength(getPassword());
        module.setPublic(keys[2], keys[3]);
        try {
            encpw = module.encrypt(enc);
        } catch (GeneralSecurityException e){
            throw new LoginFailException("Login data encryption failed. (n=" + keys[2] + ", e=" + keys[3] + ", msg=" + enc + ")" , e);
        }

        Map<String, String> data = new HashMap<>();
        data.put("enctp", "1");
        data.put("encpw", encpw);
        data.put("encnm", encnm);
        data.put("svctype", "0");
        data.put("viewtype", "0");
        data.put("locale","ko_KR");
        data.put("smart_LEVEL", "-1");
        data.put("url", "http://www.naver.com");
        data.put("nvlong","on");

        if(onCaptcha){
            data.put("chptcha", captcha);
            data.put("chptchakey", chptchakey);
            data.put("captcha_type", "image");
        }

        Document body;
        Connection.Response res;
        try {
            res = Jsoup.connect(LOGIN)
                    .header("Referer", LOGIN)
                    .data(data)
                    .method(Connection.Method.POST)
                    .execute();

            body = res.parse();
        } catch (IOException e) {
            throw new LoginFailException("Connection failed. (url=" + LOGIN + ", data=" + data + ")",e);
        }

        Elements chptchakeys;
        if((chptchakeys = body.select("#chptchakey")).size() == 1){
            String s = chptchakey;
            chptchakey = chptchakeys.first().val();
            if (onCaptcha) throw new WrongCaptchaException("Login failed due to wrong captcha (key=" + s + ")", captcha);
            onCaptcha = true;
            return false;
        }
        onCaptcha = false;
        String strBody = body.toString();
        Pattern p = Pattern.compile("location.replace\\(\"(.*?)\"\\)");
        Matcher m = p.matcher(strBody);
        if(m.find()){
            String redirect = m.group(0);
            redirect = redirect.substring(redirect.indexOf("\"") + 1, redirect.lastIndexOf("\""));

            try {
                loginCookies = Jsoup.connect(redirect).execute().cookies();
            } catch (IOException e) {
                throw new LoginFailException("Getting login data failed. (redirect=" + redirect + ", body=" + strBody + ")" , e);
            }
        }else {
            throw new WrongIdPasswordException("Wrong id or password (id=" + getId()+ ", pw=" + getPassword() + ", data = " + data + ", body=" + strBody + ")", getId(), getPassword());
        }
        return true;
    }



    public String getChptchaURL() {
        return CAPTCHA + chptchakey;
    }

    public boolean isOnCaptcha() {
        return onCaptcha;
    }

    private String getValueWithLength(String str){
        return ((char) str.length()) + str;
    }


    private String[] getKeys() throws IOException {
        return Jsoup.connect(KEYS).get().text().split(",");
    }

    @Override
    protected ExclusionStrategy getExclusionStrategy() {
        return new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getName().equals("chptchakey")
                        || field.getName().equals("onCaptcha");
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        };
    }
}