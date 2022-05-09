package com.mysite.sbb_practice1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
    //DataNotFoundException발생시 -> @ResponseStatus(value = HttpStatus.NOT_FOUND 발생
}
