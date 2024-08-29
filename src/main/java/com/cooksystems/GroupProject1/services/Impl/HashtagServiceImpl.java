package com.cooksystems.GroupProject1.services.Impl;

import com.cooksystems.GroupProject1.dtos.HashtagDto;
import com.cooksystems.GroupProject1.mappers.HashtagMapper;
import com.cooksystems.GroupProject1.repositories.HashtagRepository;
import com.cooksystems.GroupProject1.services.HashtagService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;

    @Override
    public List<HashtagDto> getAllTags() {
        return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
    }

    @Override
    public HashtagDto getTags(Long id) {
        return null;
    }
}
