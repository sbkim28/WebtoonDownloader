package com.ignited.webtoon.extract;

public class WrongIdPasswordException extends LoginFailException{

    private String id;
    private String password;

    public WrongIdPasswordException(String id, String password) {
        super("Wrong id or Password.\n" + "ID : \"" + id + "\", Password : \"" + password + "\"");
        this.id= id;
        this.password = password;
    }

    public WrongIdPasswordException(String message, String id, String password) {
        super(message + " ID : \"" + id + "\", Password : \"" + password + "\"");
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
