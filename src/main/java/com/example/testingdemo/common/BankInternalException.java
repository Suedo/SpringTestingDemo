package com.example.testingdemo.common;

import com.example.testingdemo.Types.Common.*;
import lombok.Getter;

import java.util.List;

@Getter
public class BankInternalException extends RuntimeException{

    public List<Errors> applicationErrorList;

    public BankInternalException(String message, Throwable cause, List<Errors> errors) {
        super(message, cause);
        this.applicationErrorList = errors;
    }

    public BankInternalException(String message, List<Errors> applicationErrorList) {
        super(message);
        this.applicationErrorList = applicationErrorList;
    }
}
