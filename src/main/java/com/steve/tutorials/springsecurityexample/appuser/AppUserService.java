package com.steve.tutorials.springsecurityexample.appuser;

import com.steve.tutorials.springsecurityexample.registration.token.ConfirmationToken;
import com.steve.tutorials.springsecurityexample.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "user with email: <%s>, not found";
    private final IAppUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signupUser(AppUser user) throws IllegalStateException{
        boolean userExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();
        if(userExists){
            throw new IllegalStateException(String.format("User: <%s>, already exists", user.getUsername()));
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // TODO send an email;

        return confirmationToken.getToken();
    }
}