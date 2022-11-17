package com.steve.tutorials.springsecurityexample.registration;

import com.steve.tutorials.springsecurityexample.appuser.AppUser;
import com.steve.tutorials.springsecurityexample.appuser.AppUserRole;
import com.steve.tutorials.springsecurityexample.appuser.AppUserService;
import com.steve.tutorials.springsecurityexample.utils.validators.EmailValidatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidatorService emailValidatorService;
    private final AppUserService appUserService;

    public String register(RegistrationRequest request) throws IllegalStateException{
        boolean isValidEmail = emailValidatorService.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException(
                    String.format("Email: [%s] is a valid email", request.getEmail())
            );
        }
        return appUserService.signupUser(
                new AppUser(
                     request.getFirstName(),
                     request.getLastName(),
                     request.getEmail(),
                     request.getPassword(),
                     AppUserRole.USER
                )
        );
    }
}
