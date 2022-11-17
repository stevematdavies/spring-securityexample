package com.steve.tutorials.springsecurityexample.appuser;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface IAppUserRepository {
    Optional<AppUser> findByEmail(String email);
}
