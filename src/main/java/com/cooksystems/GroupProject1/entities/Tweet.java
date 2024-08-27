package com.cooksystems.GroupProject1.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime posted;

    private boolean deleted;

    private String content;

    @ManyToMany
    @JoinTable(
            name = "tweet_hashtags",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private Set<Hashtag> hashtags;

    @ManyToMany(mappedBy = "likedTweets")
    private Set<User> likedByUsers;

    @ManyToMany(mappedBy = "mentionedInTweets")
    private Set<User> mentionedUsers;

    @OneToMany(mappedBy = "inReplyTo")
    private Set<Tweet> replies;

    @OneToMany(mappedBy = "repostOf")
    private Set<Tweet> reposts;

    @ManyToOne
    @JoinColumn(name = "in_reply_to_id")
    private Tweet inReplyTo;


    @ManyToOne
    @JoinColumn(name = "repost_of_id")
    private Tweet repostOf;

}
