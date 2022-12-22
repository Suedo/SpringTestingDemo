package com.example.testingdemo.Types;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class BankFlow {

    public record Person(@NotBlank String firstname, @NotBlank String lastName, @Range(min = 18, max = 60) int age) {}

    public record Deposit(@Valid Person depositor, @Min(100) int depositValue) {}

    public record SystemDateRequest(@NotBlank @Size(min = 8, max = 11) String bic, @NotBlank String currency) {}

    public record DepositRequest(@NotBlank String firstname, @NotBlank String lastName, @NotBlank String dob,
                                 @NotBlank String country, @Positive int bankId, @Positive int depositValue) {}

    public record SystemDateResponse(long id, String date) {}

    public record DepositResponse(String details) {}



}
