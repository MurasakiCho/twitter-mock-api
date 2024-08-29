package com.cooksystems.GroupProject1.services;

import java.util.List;

import com.cooksystems.GroupProject1.dtos.TweetRequestDto;
import com.cooksystems.GroupProject1.dtos.TweetResponseDto;

public interface TweetService {

    TweetResponseDto getTweetByID(Long id);

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

}
