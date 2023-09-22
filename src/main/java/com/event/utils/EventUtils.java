package com.event.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EventUtils {
    private EventUtils(){

    }
    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<>("{\"message\":\""+responseMessage+"\"}", httpStatus);
    }
}
