package com.cooksystems.GroupProject1.repositories;

import com.cooksystems.GroupProject1.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByCredentialsUsername(String username);

    Optional<User> findByCredentialsUsername(String username);
}
