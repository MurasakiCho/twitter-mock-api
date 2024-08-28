package com.cooksystems.GroupProject1.services.Impl;

import com.cooksystems.GroupProject1.dtos.TweetResponseDto;
import com.cooksystems.GroupProject1.entities.Tweet;
import com.cooksystems.GroupProject1.exceptions.NotFoundException;
import com.cooksystems.GroupProject1.mappers.TweetMapper;
import com.cooksystems.GroupProject1.repositories.TweetRepository;
import com.cooksystems.GroupProject1.services.TweetService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    @Override
    public TweetResponseDto getTweetByID(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        Tweet tweet;
        //checking if tweet exist in database
        if (optionalTweet.isPresent()) {
            tweet = optionalTweet.get();
        } else {
            throw new NotFoundException("No Tweet found with id: " + id);
        }
        //checking if that tweet has been deleted
        if (tweet.isDeleted()) {
            throw new NotFoundException("Tweet with id: " + id + " has been deleted.");
        }
        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
    }

}
