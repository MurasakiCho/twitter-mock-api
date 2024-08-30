package com.cooksystems.GroupProject1.controllers;

import com.cooksystems.GroupProject1.dtos.TweetRequestDto;
import com.cooksystems.GroupProject1.dtos.TweetResponseDto;
import com.cooksystems.GroupProject1.dtos.UserResponseDto;
import com.cooksystems.GroupProject1.entities.User;
import com.cooksystems.GroupProject1.services.TweetService;
import com.cooksystems.GroupProject1.services.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//	
//	@GetMapping("/{id}/tags")
//	
//	@GetMapping("/{id}/likes")
//
//	@GetMapping("/{id}/context")
//	
//	@GetMapping("/{id}/reply")

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
		User user = userService.findUser(tweetRequestDto.getCredentials().getUsername());
		return tweetService.createTweet(tweetRequestDto, user);
	}
//	@PostMapping("/{id}/like")
//	
//	@PostMapping("/{id}/reply")
//	
//	@PostMapping("/{id}/repost")
//	
//	@DeleteMapping("/{id}")
	
	
	
	

}
