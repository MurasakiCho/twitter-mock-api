package com.cooksystems.GroupProject1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
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
    private Timestamp firstUsed;

    private Timestamp lastUsed;

    @ManyToMany
    @JoinTable(
            name = "tweet_hashtags",
            joinColumns = @JoinColumn(name = "hashtag_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id")
    )
    private Set<Tweet> tweets;

}
