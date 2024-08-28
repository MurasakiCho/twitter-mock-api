package com.cooksystems.GroupProject1.services;

import com.cooksystems.GroupProject1.dtos.TweetResponseDto;

public interface TweetService {

    TweetResponseDto getTweetByID(Long id);

}
