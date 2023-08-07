package com.wanted.preonboarding.user.infra;

import com.wanted.preonboarding.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    User save(User user);
}
