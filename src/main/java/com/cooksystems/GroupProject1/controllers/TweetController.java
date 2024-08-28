package com.cooksystems.GroupProject1.controllers;

import com.cooksystems.GroupProject1.dtos.TweetResponseDto;
import com.cooksystems.GroupProject1.services.TweetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;
	
//	@GetMapping
//	
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
//	
//	@GetMapping("/{id}/repost")
//	
//	@GetMapping("/{id}/mentions")
//	
//	@PostMapping
//	
//	@PostMapping("/{id}/like")
//	
//	@PostMapping("/{id}/reply")
//	
//	@PostMapping("/{id}/repost")
//	
//	@DeleteMapping("/{id}")
	
	
	
	

}
