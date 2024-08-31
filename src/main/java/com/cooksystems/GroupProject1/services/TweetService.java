package com.cooksystems.GroupProject1.services;

import java.util.List;

import com.cooksystems.GroupProject1.dtos.CredentialsDto;
import com.cooksystems.GroupProject1.dtos.TweetRequestDto;
import com.cooksystems.GroupProject1.dtos.TweetResponseDto;
import com.cooksystems.GroupProject1.dtos.UserResponseDto;

public interface TweetService {

    TweetResponseDto getTweetByID(Long id);

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	TweetResponseDto getRepostById(Long id);

	List<UserResponseDto> getMentionedUsersByTweetId(Long id);

	void likeTweet(long id, CredentialsDto credRequestDto);

	TweetResponseDto createRepost(long id, CredentialsDto credRequestDto);
}
