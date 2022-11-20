package com.example.testingdemo.service;

import com.example.testingdemo.common.BankInternalException;
import com.example.testingdemo.Types.BankFlow;
import com.example.testingdemo.utils.DepositAggregator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BankServiceTest {

    ValidationService validationService;
    BankService bankService;

    @BeforeEach
    void setup() {
        validationService = new ValidationService();
        bankService = new BankService(validationService); // utilizing constructor injection
    }

    @ParameterizedTest
    @CsvSource({
            "Tom,Hardy,1990-01-01,USA,1,200",
            "Lewis,Hamilton,1991-01-01,UK,2,100"
    })
    void makeDeposit_happy(@AggregateWith(DepositAggregator.class) BankFlow.DepositRequest depositRequest) {
        String status = bankService.makeDeposit(depositRequest);
        assertEquals("Created deposit successfully", status);
    }

    /*
        Test that BankService#makeDeposit method properly validates the 'Deposit' type
        We will pass all valid 'DepositRequest' dtos, but in a way that it would generate invalid 'Deposit' models
        and then assert that the validation throws errors for all the created models
     */
    @ParameterizedTest
    @CsvSource({
            "Tom,Hardy,2021-01-01,USA,1,1",
            "Lewis,Hamilton,2022-01-01,UK,2,500",
            "Kid,Baby,1991-01-01,India,1,5"
    })
    void makeDeposit_error(@AggregateWith(DepositAggregator.class) BankFlow.DepositRequest depositRequest) {

        BankInternalException ex = assertThrows(BankInternalException.class,
                                                () -> bankService.makeDeposit(depositRequest));
        assertEquals("Error while depositing", ex.getMessage());
    }
}