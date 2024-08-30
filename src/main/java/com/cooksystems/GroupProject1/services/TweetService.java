package com.cooksystems.GroupProject1.services;

import java.util.List;

import com.cooksystems.GroupProject1.dtos.TweetRequestDto;
import com.cooksystems.GroupProject1.dtos.TweetResponseDto;
import com.cooksystems.GroupProject1.dtos.UserResponseDto;
import com.cooksystems.GroupProject1.entities.User;

public interface TweetService {

    TweetResponseDto getTweetByID(Long id);

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto, User user);

	TweetResponseDto getRepostById(Long id);

	List<UserResponseDto> getMentionedUsersByTweetId(Long id);
}
