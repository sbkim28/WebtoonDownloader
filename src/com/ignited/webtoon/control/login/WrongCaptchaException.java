package com.ignited.webtoon.control.login;

public class WrongCaptchaException extends LoginFailException{

    private String chptchakey;


    public WrongCaptchaException(String message, String chptchakey) {
        super(message);
        this.chptchakey = chptchakey;
    }

    public String getChptchakey() {
        return chptchakey;
    }
}
