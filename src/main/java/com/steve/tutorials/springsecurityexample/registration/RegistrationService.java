package com.steve.tutorials.springsecurityexample.registration;

import com.steve.tutorials.springsecurityexample.appuser.AppUser;
import com.steve.tutorials.springsecurityexample.appuser.AppUserRole;
import com.steve.tutorials.springsecurityexample.appuser.AppUserService;
import com.steve.tutorials.springsecurityexample.email.EmailService;
import com.steve.tutorials.springsecurityexample.email.IEmailSender;
import com.steve.tutorials.springsecurityexample.registration.token.ConfirmationToken;
import com.steve.tutorials.springsecurityexample.registration.token.ConfirmationTokenService;
import com.steve.tutorials.springsecurityexample.utils.validators.EmailValidatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidatorService emailValidatorService;
    private final EmailService emailService;
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) throws IllegalStateException {
        boolean isValidEmail = emailValidatorService.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException(
                    String.format("Email: [%s] is a valid email", request.getEmail())
            );
        }
        String token = appUserService.signupUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );

        emailService.send(
                request.getEmail(),
                emailService.getEmailTemplate(
                        request.getFirstName(),
                        "http://localhost:8080/api/v1/registration/confirm?token=" + token));

        return token;
    }

    @Transactional
    public String confirmToken(String token) throws IllegalStateException {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));
        if (confirmationToken.getTokenUserConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed!");
        }

        LocalDateTime expiredAt = confirmationToken.getTokenExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token has expired");
        }
        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }
}
