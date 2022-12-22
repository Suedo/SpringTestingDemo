package com.example.testingdemo.service;

import com.example.testingdemo.common.LoginException;
import com.example.testingdemo.Types.UserFlow.*;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * Provides all transforms and functionality wrt a User Business flow
 */
@Service
public class UserService {

    ValidationService validationService;
    TreeMap<Integer, Profile> idProfileMap;

    public UserService(ValidationService validationService) {
        this.validationService = validationService;
        this.idProfileMap = new TreeMap<>();
    }

    // the only place where you get a Profile returned
    // thus, if you have a profile, it must have gone through a signup
    public Profile signup(SignupRequest s) {
        int newId = idProfileMap.isEmpty() ? 1 : idProfileMap.lastKey() + 1;
        Profile profile = new Profile(
                newId,
                new Name(s.firstName(), s.middleName(), s.lastName()),
                new Email(s.email()),
                new PhoneNumber(s.phoneNumber()),
                new Password(s.password())
        );
        // do some db stuff ideally
        idProfileMap.put(newId, profile);
        return profile;
    }

    // the only place you get an Authenticated user. Thus, if you have an
    // authenticated user, login was done
    public Authenticated login(LoginRequest loginRequest)
            throws ConstraintViolationException, LoginException {
        validationService.validate(loginRequest);
        Optional<Profile> profileMaybe = switch (loginRequest) {
            case DefaultLogin d -> getProfile(new ById(d.userId()));
            case LoginWithEmail e -> getProfile(new ByEmail(new Email(e.email())));
            case LoginWithPhone p -> getProfile(new ByPhoneNumber(new PhoneNumber(p.phoneNumber())));
        };
        Profile profile = profileMaybe.orElseThrow(() -> new LoginException("No Profiles found"));
        if (loginRequest instanceof DefaultLogin d) {
            if (!d.password().equals(profile.password().string()))
                throw new LoginException("Incorrect Password");
        }
        return new Authenticated(profile);
    }

    private Optional<Profile> getProfile(ProfileFetcher key) {
        Stream<Profile> stream = idProfileMap.values().stream();
        return switch (key) {
            case ById e -> stream.filter(profile -> profile.id() == e.value()).findFirst();
            case ByEmail e -> stream.filter(profile -> profile.email().equals(e.value())).findFirst();
            case ByPhoneNumber e -> stream.filter(profile -> profile.phoneNumber().equals(e.value())).findFirst();
        };
    }

    public List<AppResource> getResourcesForUser(User userType) {
        ArrayList<AppResource> resources = new ArrayList<>();
        switch (userType) {
            case Unauthenticated u -> resources.add(new LoginPage());
            case Authenticated a -> {
                resources.add(new Dashboard());
                resources.add(new ProfilePage());
            }
        }
        return resources;
    }
}
