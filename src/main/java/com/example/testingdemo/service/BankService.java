package com.example.testingdemo.service;

import com.example.testingdemo.common.BankInternalException;
import com.example.testingdemo.Types.BankFlow.Deposit;
import com.example.testingdemo.Types.BankFlow.DepositRequest;
import com.example.testingdemo.Types.Common.Errors;
import com.example.testingdemo.Types.BankFlow.Person;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankService {

    ValidationService validationService;

    public BankService(ValidationService validationService) {
        this.validationService = validationService;
    }

    private Deposit generateDepositFromRequest(DepositRequest depositRequest) throws DateTimeParseException {
        LocalDate now = LocalDate.now();
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate parsedDate = LocalDate.parse(depositRequest.dob(), formatter);
        int age = now.getYear() - parsedDate.getYear();

        Person person = new Person(depositRequest.firstname(), depositRequest.lastName(), age);
        Deposit deposit = new Deposit(person, depositRequest.depositValue());

        return deposit;
    }

    public String makeDeposit(DepositRequest depositRequest) throws BankInternalException {

        List<Errors> validationErrors = new ArrayList<>();

        try {
            Deposit deposit = generateDepositFromRequest(depositRequest);
            validationService.validate(deposit);
        } catch (DateTimeParseException ex) {
            validationErrors.add(new Errors("dob", ex.getMessage()));
        } catch (ConstraintViolationException ex) {
            validationErrors.addAll(validationService.getErrors(ex));
        }

        if (validationErrors.size() > 0)
            throw new BankInternalException("Error while depositing", validationErrors);
        else
            return "Created deposit successfully";
    }
}
