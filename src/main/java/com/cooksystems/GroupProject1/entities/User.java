package com.cooksystems.GroupProject1.entities;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "user_table", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    @Id
    @GeneratedValue
    private Long Id;

    @Embedded
    private Credentials credentials;

    @Embedded
    private Profile profile;
    
    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp joined;

    private  boolean deleted = false;

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id")
    )
    private List<Tweet> likedTweets;

    @ManyToMany(mappedBy = "mentionedUsers")
    private List<Tweet> mentionedTweets;

    @ManyToMany
    @JoinTable(name = "followers_following")
    private List<User> followers;

    @ManyToMany(mappedBy = "followers")
    private List<User> following;

}
