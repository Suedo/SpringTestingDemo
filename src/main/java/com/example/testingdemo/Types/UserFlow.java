package com.example.testingdemo.Types;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/* Designing User Business Model using Algebraic Data Types */
public class UserFlow {

    // building blocks
    public record Name(@NotBlank String firstName, String middleName, @NotBlank String lastName) {}
    public record Email(@NotNull @Pattern(regexp = "^(.+)@(.+)$", message = "invalid Email") String string) {}
    public record PhoneNumber(@NotNull @Pattern(regexp = "^\\d{10}$", message = "invalid PhoneNumber") String string) {}
    public record Password(
            @NotNull @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "invalid Password")
            String string
    ) {}
    public record Profile(
            @Min(1) int id, @Valid Name name, @Valid Email email,
            @Valid PhoneNumber phoneNumber, @Valid Password password
    ) {}

    // requests, i.e. incoming DTO
    public record SignupRequest(
            @NotBlank String firstName, String middleName, @NotBlank String lastName,
            @NotBlank String email, @NotBlank String phoneNumber,
            @NotBlank String password
    ) {}

    public sealed interface LoginRequest permits DefaultLogin, LoginWithEmail, LoginWithPhone {}
    public record DefaultLogin(@Min(1) int userId, @NotBlank String password) implements LoginRequest {}
    public record LoginWithEmail(@NotBlank String email) implements LoginRequest {}
    public record LoginWithPhone(@NotBlank String phoneNumber) implements LoginRequest {}

    // internal types and models
    public sealed interface ProfileFetcher permits ById, ByEmail, ByPhoneNumber {}
    public record ById(Integer value) implements ProfileFetcher{}
    public record ByEmail(Email value) implements ProfileFetcher{}
    public record ByPhoneNumber(PhoneNumber value) implements ProfileFetcher{}

    // Unlike Traditional Design, there is no boolean 'flag' that determines authentication
    // thus, nothing to validate, or 'if'-check. Fully diff type, and won't work interchangebly
    // no need for runtime checks, given compile time error if used incorrectly!
    public sealed interface User permits Unauthenticated, Authenticated {}
    public record Unauthenticated() implements User {}
    public record Authenticated(@Valid Profile profile) implements User {}


    /* Resources a user can access */
    public sealed interface AppResource permits Dashboard, ProfilePage, LoginPage {}
    public record Dashboard() implements AppResource {}
    public record ProfilePage() implements AppResource {}
    public record LoginPage() implements AppResource {}

}
