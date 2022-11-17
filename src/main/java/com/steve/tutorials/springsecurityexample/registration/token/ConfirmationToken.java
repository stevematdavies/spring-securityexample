package com.steve.tutorials.springsecurityexample.registration.token;

import com.steve.tutorials.springsecurityexample.appuser.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime tokenCreatedAt;
    @Column(nullable = false)
    private LocalDateTime tokenExpiresAt;

    private LocalDateTime tokenUserConfirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "app_user_id")
    private AppUser appUser;

    public ConfirmationToken(String token,
                             LocalDateTime tokenCreatedAt,
                             LocalDateTime tokenExpiresAt,
                             AppUser appUser) {
        this.token = token;
        this.tokenCreatedAt = tokenCreatedAt;
        this.tokenExpiresAt = tokenExpiresAt;
        this.appUser = appUser;
    }
}
