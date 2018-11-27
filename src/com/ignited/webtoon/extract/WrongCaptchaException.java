package com.ignited.webtoon.extract;

public class WrongCaptchaException extends LoginFailException{

    private String chptchakey;

    public WrongCaptchaException(String chptchakey) {
        super("Wrong chptchakey: (" + chptchakey + ")");
        this.chptchakey = chptchakey;

    }

    public WrongCaptchaException(String message, String chptchakey) {
        super(message + " (" + chptchakey + ")");
        this.chptchakey = chptchakey;
    }

    public String getChptchakey() {
        return chptchakey;
    }
}
