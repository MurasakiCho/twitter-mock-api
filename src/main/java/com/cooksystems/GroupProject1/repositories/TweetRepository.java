package com.cooksystems.GroupProject1.repositories;

import com.cooksystems.GroupProject1.entities.Tweet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
