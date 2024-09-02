package com.cooksystems.GroupProject1.controllers;

import com.cooksystems.GroupProject1.dtos.*;
import com.cooksystems.GroupProject1.services.TweetService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;
	
	@GetMapping
	public List<TweetResponseDto> getAllTweets(){
        return tweetService.getAllTweets();
    }
	
	@GetMapping("/{id}")
    public TweetResponseDto getTweetByID(@PathVariable Long id) {
        return tweetService.getTweetByID(id);
    }

	@GetMapping("/{id}/tags")
	public List<HashtagDto> getTweetHashtags(@PathVariable long id) {
		return tweetService.getTweetHashtags(id);
	}

	@GetMapping("/{id}/likes")
	public List<UserResponseDto> getTweetLikes(@PathVariable long id) {
		return tweetService.getTweetLikes(id);
	}

	@GetMapping("/{id}/context")
	public ContextDto getTweetContext(@PathVariable long id) {
		return tweetService.getTweetContext(id);
	}

	@GetMapping("/{id}/replies")
	public List<TweetResponseDto> getTweetReply(@PathVariable long id) {
		return tweetService.getTweetReplies(id);
	}

	@GetMapping("/{id}/reposts")
	public List<TweetResponseDto> getRepostById(@PathVariable Long id) {
		return tweetService.getRepostById(id);
	}

	@GetMapping("/{id}/mentions")
	public List<UserResponseDto> getMentionedUsersByTweetId(@PathVariable Long id) {
		return tweetService.getMentionedUsersByTweetId(id);
	}

	@PostMapping
	public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.createTweet(tweetRequestDto);
	}
	@PostMapping("/{id}/like")
	public void likeTweet(@PathVariable long id, @RequestBody CredentialsDto credRequestDto) {
		tweetService.likeTweet(id, credRequestDto);
	}

	@PostMapping("/{id}/reply")
	public TweetResponseDto createReplyTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.createReplyTweet(id, tweetRequestDto);
	}

	@DeleteMapping("/{id}")
	public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto){
		return tweetService.deleteTweet(id, credentialsDto);
	}

	@PostMapping("/{id}/repost")
	public TweetResponseDto createRepost(@PathVariable long id, @RequestBody CredentialsDto credRequestDto) {
		return tweetService.createRepost(id, credRequestDto);
	}

}
