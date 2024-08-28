package com.cooksystems.GroupProject1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {

    @Id
    @GeneratedValue
    private Long Id;

    @NonNull
    @Column(unique = true)
    private String label;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime firstUsed;

    private LocalDateTime lastUsed;

    @ManyToMany(mappedBy = "tweet_hashtags")
    private Set<Tweet> tweets;

}
