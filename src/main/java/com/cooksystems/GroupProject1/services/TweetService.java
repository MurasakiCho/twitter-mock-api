package com.cooksystems.GroupProject1.services;

import java.util.List;

import com.cooksystems.GroupProject1.dtos.*;

public interface TweetService {

    TweetResponseDto getTweetByID(Long id);

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	TweetResponseDto getRepostById(Long id);

	List<UserResponseDto> getMentionedUsersByTweetId(Long id);

	TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto);

	List<HashtagDto> getTweetHashtags (Long id);

	List<UserResponseDto> getTweetLikes(Long id);

	ContextDto getTweetContext(Long id);

	List<TweetResponseDto> getTweetReplies(Long id);

	void likeTweet(long id, CredentialsDto credRequestDto);

	TweetResponseDto createReplyTweet(Long id, TweetRequestDto tweetRequestDto);

	TweetResponseDto createRepost(long id, CredentialsDto credRequestDto);
  
}
