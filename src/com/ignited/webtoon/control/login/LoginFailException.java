package com.ignited.webtoon.control.login;

public class LoginFailException extends Exception {

    public LoginFailException() {
        super();
    }

    public LoginFailException(String message) {
        super(message);
    }

    public LoginFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailException(Throwable cause) {
        super(cause);
    }
}
