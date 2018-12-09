package com.ignited.webtoon.control.login;

public class WrongIdPasswordException extends LoginFailException{

    private String id;
    private String password;

    public WrongIdPasswordException(String message, String id, String password) {
        super(message);
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }


}
