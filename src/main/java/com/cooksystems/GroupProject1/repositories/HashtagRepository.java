package com.cooksystems.GroupProject1.repositories;

import com.cooksystems.GroupProject1.entities.Hashtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
