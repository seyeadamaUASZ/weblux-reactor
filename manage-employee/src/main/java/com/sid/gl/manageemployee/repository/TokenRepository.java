package com.sid.gl.manageemployee.repository;

import com.sid.gl.manageemployee.models.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Long> {
    Optional<Token> findByRefreshToken(String token);
}
