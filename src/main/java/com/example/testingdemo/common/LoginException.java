package com.example.testingdemo.common;

import lombok.Getter;

import java.util.List;

@Getter
public class LoginException extends RuntimeException {

    public LoginException(String message) {
        super(message);
    }

}
