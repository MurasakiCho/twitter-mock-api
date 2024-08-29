package com.cooksystems.GroupProject1.services;

import com.cooksystems.GroupProject1.dtos.HashtagDto;
import com.cooksystems.GroupProject1.dtos.TweetResponseDto;

import java.util.List;

public interface HashtagService {
    List<HashtagDto> getAllTags();

    List<TweetResponseDto> getTags(String label);
}
