package com.example.testingdemo.common;

import lombok.Getter;

import java.util.List;

@Getter
public class BankInternalException extends RuntimeException{

    public List<Types.Errors> applicationErrorList;

    public BankInternalException(String message, Throwable cause, List<Types.Errors> errors) {
        super(message, cause);
        this.applicationErrorList = errors;
    }

    public BankInternalException(String message, List<Types.Errors> applicationErrorList) {
        super(message);
        this.applicationErrorList = applicationErrorList;
    }
}
