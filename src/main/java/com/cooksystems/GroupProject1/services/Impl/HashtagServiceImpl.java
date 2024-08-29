package com.cooksystems.GroupProject1.services.Impl;

import com.cooksystems.GroupProject1.dtos.HashtagDto;
import com.cooksystems.GroupProject1.dtos.TweetResponseDto;
import com.cooksystems.GroupProject1.entities.Hashtag;
import com.cooksystems.GroupProject1.entities.Tweet;
import com.cooksystems.GroupProject1.exceptions.NotFoundException;
import com.cooksystems.GroupProject1.mappers.HashtagMapper;
import com.cooksystems.GroupProject1.mappers.TweetMapper;
import com.cooksystems.GroupProject1.repositories.HashtagRepository;
import com.cooksystems.GroupProject1.services.HashtagService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;
    private final TweetMapper tweetMapper;

    @Override
    public List<HashtagDto> getAllTags() {
        return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
    }

    @Override
    public List<TweetResponseDto> getTags(String label) {
        //checks if the hashtag label exists in database
        String tagLabel = "#" + label;
        if (!hashtagRepository.existsByLabel(tagLabel)) {
            throw new NotFoundException("Hashtag label:" + label + " does not exits");
        }
        //get all the tweets that contains the hashtag label
        Hashtag hashtag = hashtagRepository.findByLabel(tagLabel);
        List<Tweet> tweetList = new ArrayList<>(hashtag.getTweets());

        //removing any tweets from the list if it's been deleted
        tweetList.removeIf(Tweet::isDeleted);
        //checking if after the removing the list is empty
        if (tweetList.isEmpty()) {
            throw new NotFoundException("The Tweet with the Hashtag label:" + label + " has been deleted");
        }

        //sort the list of tweets by timestamp and
        // put it in reverse-chronological order
        tweetList.sort(Comparator.comparing(Tweet::getPosted).reversed());

        return tweetMapper.entitiesToDto(tweetList);
    }
}
