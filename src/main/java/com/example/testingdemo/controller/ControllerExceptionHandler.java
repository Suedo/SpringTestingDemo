package com.example.testingdemo.controller;

import com.example.testingdemo.common.BankInternalException;
import com.example.testingdemo.common.Types;
import com.example.testingdemo.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @Autowired
    ValidationService validationService;


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Types.ErrorResponse handleValidationErrors(MethodArgumentNotValidException ex) {
        System.out.println("Validation failed. Details:");
        System.out.println(ex.getMessage());
        List<Types.Errors> errors = validationService.getErrors(ex);
        return new Types.ErrorResponse(errors.size(),
                                       ex.getParameter().getMethod().getName(),
                                       errors);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Types.ErrorResponse handleBankAppException(BankInternalException ex) {
        System.out.println("Validation failed. Details:");
        System.out.println(ex.getMessage());
        List<Types.Errors> errors = ex.getApplicationErrorList();
        return new Types.ErrorResponse(errors.size(), "BankInternal", errors);
    }


}
