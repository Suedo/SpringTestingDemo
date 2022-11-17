package com.example.testingdemo.controller;

import com.example.testingdemo.common.BankInternalException;
import com.example.testingdemo.common.Types.*;
import com.example.testingdemo.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BankController {

    @Autowired
    BankService bankService;

    @GetMapping("/{id}/systemDate")
    public ResponseEntity<SystemDateResponse> getSystemDate(
            @PathVariable("id") long bankId,
            @RequestBody @Valid SystemDateRequest systemDateRequest) {

        System.out.println(String.format("fetching date for BankId: %d", bankId));
        return ResponseEntity.ok(new SystemDateResponse(bankId, LocalDateTime.now().toString()));
    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponse> makeDeposit(@RequestBody @Valid DepositRequest depositRequest) {
        String status = bankService.makeDeposit(depositRequest);
        System.out.println("Deposit status: " + status);
        return ResponseEntity.accepted().body(new DepositResponse(status));
    }


}


