package com.example.testingdemo.service;

import com.example.testingdemo.common.Types;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.annotation.PreDestroy;
import javax.validation.*;
import java.util.List;
import java.util.Set;

@Service
public class ValidationService {
    ValidatorFactory validatorFactory;
    Validator validator;

    public ValidationService() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public boolean validate(Object obj) throws ConstraintViolationException {
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        if (violations.size() > 0) {
            throw new ConstraintViolationException("Service Layer Validation Error", violations);
        }
        return true;
    }

    public List<Types.Errors> getErrors(ConstraintViolationException ex) {
        List<Types.Errors> errors = ex.getConstraintViolations().stream().map(
                violation -> new Types.Errors(violation.getPropertyPath().toString(),
                                              violation.getMessage())
        ).toList();
        return errors;
    }

    public List<Types.Errors> getErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<Types.Errors> errors = fieldErrors.stream().map(fieldError -> new Types.Errors(
                fieldError.getField(),
                fieldError.getDefaultMessage())).toList();
        return errors;
    }

    @PreDestroy
    private void close() {
        this.validatorFactory.close();
    }
}
