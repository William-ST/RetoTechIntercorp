package com.sulca.retotechintercorp.data.exception;

/**
 * Created by everis on 25/03/19.
 */
public class ServerExceptionMessage extends Exception {

    private String message;

    public ServerExceptionMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
