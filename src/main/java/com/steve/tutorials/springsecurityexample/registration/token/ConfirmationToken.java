package com.steve.tutorials.springsecurityexample.registration.token;

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
    @Column(nullable = false)
    private LocalDateTime tokenUserConfirmedAt;

    public ConfirmationToken(String token,
                             LocalDateTime tokenCreatedAt,
                             LocalDateTime tokenExpiresAt,
                             LocalDateTime tokenUserConfirmedAt) {
        this.token = token;
        this.tokenCreatedAt = tokenCreatedAt;
        this.tokenExpiresAt = tokenExpiresAt;
        this.tokenUserConfirmedAt = tokenUserConfirmedAt;
    }
}
