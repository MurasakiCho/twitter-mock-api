package com.cooksystems.GroupProject1.controllers;

import com.cooksystems.GroupProject1.dtos.*;
import com.cooksystems.GroupProject1.entities.User;
import com.cooksystems.GroupProject1.services.TweetService;
import com.cooksystems.GroupProject1.services.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;
    private final UserService userService;
	
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

	@GetMapping("/{id}/reply")
	public List<TweetResponseDto> getTweetReply(@PathVariable long id) {
		return tweetService.getTweetReplies(id);
	}

	@GetMapping("/{id}/reposts")
	public TweetResponseDto getRepostById(@PathVariable Long id) {
		return tweetService.getRepostById(id);
	}

	@GetMapping("/{id}/mentions")
	public List<UserResponseDto> getMentionedUsersByTweetId(@PathVariable Long id) {
		return tweetService.getMentionedUsersByTweetId(id);
	}

	@PostMapping
	public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.createTweet(tweetRequestDto, userService.findUser(tweetRequestDto.getCredentials().getUsername()));
	}
//	@PostMapping("/{id}/like")
//	
//	@PostMapping("/{id}/reply")
//	
//	@PostMapping("/{id}/repost")
//	
	@DeleteMapping("/{id}")
	public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto){
		return tweetService.deleteTweet(id, credentialsDto);
	}
	
	
	
	

}
