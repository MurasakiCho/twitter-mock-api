package com.cooksystems.GroupProject1.repositories;

import com.cooksystems.GroupProject1.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
