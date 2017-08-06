package org.sjbanerjee.urlshortener.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String s){
        super(s);
    }
}
