package com.steve.tutorials.springsecurityexample.registration;

import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    public String register(RegistrationRequest request){
        return "Works!";
    }
}
