package com.example.rxnetty.exceptions;

public class RouteNotFoundException extends Exception {
    public RouteNotFoundException() {
        super();
    }

    public RouteNotFoundException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public RouteNotFoundException(String detailMessage) {
        super(detailMessage);
    }

    public RouteNotFoundException(Throwable throwable) {
        super(throwable);
    }

}
