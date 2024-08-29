package com.cooksystems.GroupProject1.controllers;


import com.cooksystems.GroupProject1.dtos.HashtagDto;
import com.cooksystems.GroupProject1.dtos.TweetResponseDto;
import com.cooksystems.GroupProject1.services.HashtagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {

    private final HashtagService hashtagService;
	
	@GetMapping
    public List<HashtagDto> getAllTags() {
        return hashtagService.getAllTags();
    }

    @GetMapping("/{label}")
    public List<TweetResponseDto> getTags(@PathVariable String label) {
        return hashtagService.getTags(label);
    }

}
