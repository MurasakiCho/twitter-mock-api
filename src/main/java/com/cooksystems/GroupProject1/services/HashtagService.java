package com.cooksystems.GroupProject1.services;

import com.cooksystems.GroupProject1.dtos.HashtagDto;

import java.util.List;

public interface HashtagService {
    List<HashtagDto> getAllTags();

    HashtagDto getTags(Long id);
}
