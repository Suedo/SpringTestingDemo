package com.example.testingdemo.controller;

import com.example.testingdemo.Types.Common.ErrorResponse;
import com.example.testingdemo.Types.Common.Errors;
import com.example.testingdemo.common.BankInternalException;
import com.example.testingdemo.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ErrorResponse handleValidationErrors(MethodArgumentNotValidException ex) {
        System.out.println("Validation failed. Details:");
        System.out.println(ex.getMessage());
        List<Errors> errors = validationService.getErrors(ex);
        return new ErrorResponse(errors.size(),
                                          ex.getParameter().getMethod().getName(),
                                          errors);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleBankAppException(BankInternalException ex) {
        System.out.println("Validation failed. Details:");
        System.out.println(ex.getMessage());
        List<Errors> errors = ex.getApplicationErrorList();
        return new ErrorResponse(errors.size(), "BankInternal", errors);
    }


}
