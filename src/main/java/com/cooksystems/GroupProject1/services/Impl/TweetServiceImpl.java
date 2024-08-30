package com.cooksystems.GroupProject1.services.Impl;

import com.cooksystems.GroupProject1.dtos.TweetRequestDto;
import com.cooksystems.GroupProject1.dtos.TweetResponseDto;
import com.cooksystems.GroupProject1.dtos.UserResponseDto;
import com.cooksystems.GroupProject1.entities.Hashtag;
import com.cooksystems.GroupProject1.entities.Tweet;
import com.cooksystems.GroupProject1.entities.User;
import com.cooksystems.GroupProject1.exceptions.NotFoundException;
import com.cooksystems.GroupProject1.mappers.TweetMapper;
import com.cooksystems.GroupProject1.mappers.UserMapper;
import com.cooksystems.GroupProject1.repositories.HashtagRepository;
import com.cooksystems.GroupProject1.repositories.TweetRepository;
import com.cooksystems.GroupProject1.repositories.UserRepository;
import com.cooksystems.GroupProject1.services.TweetService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
	private final HashtagRepository hashtagRepository;
	private final UserRepository userRepository;
    private final TweetMapper tweetMapper;
	private final UserMapper userMapper;

    @Override
    public TweetResponseDto getTweetByID(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        Tweet tweet;

        //checking if tweet exist in database
        if (optionalTweet.isPresent()) {
            tweet = optionalTweet.get();
        } else {
            throw new NotFoundException("No Tweet found with id:" + id);
        }
        //checking if that tweet has been deleted
        if (tweet.isDeleted()) {
            throw new NotFoundException("The Tweet with id:" + id + " has been deleted");
        }

        return tweetMapper.entityToDto(tweet);
    }

	@Override
	public List<TweetResponseDto> getAllTweets() {
		List<Tweet> tweets = tweetRepository.findAll();
		//sort tweets in reverse-chronological order.
		Collections.sort(tweets);
		Collections.reverse(tweets);
		
	    List<TweetResponseDto> tweetResponseDtos = new ArrayList<>();
	    for(Tweet tweet: tweets) {
			if(!tweet.isDeleted()) {
				tweetResponseDtos.add(tweetMapper.entityToDto(tweet));
	        }
		}
		return tweetResponseDtos;
	}

	@Override
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto, User user) {
		Tweet tweet = tweetMapper.DtoToEntity(tweetRequestDto);
		String content = tweetRequestDto.getContent();
		tweet.setAuthor(user);
		tweet.setContent(content);
		tweet.setInReplyTo(null);
		tweet.setLikedByUsers(null);
		tweet.setDeleted(false);
		int index = 0;
		List<User> mentions = null;
		List<Hashtag> hashtags = null;
		
		while (content.contains("@") || content.contains("#")) {
			if (content.contains("@")) {
				if (mentions == null) {
					mentions = new ArrayList<>();
				}
				index = content.indexOf('@', 0);
				int end = content.indexOf(' ', index);
				content.replaceFirst("@", " ");
				String mention = content.substring(index + 1, end - 1);
				Optional<User> optionalUser = userRepository.findByCredentialsUsername(mention);

		        User mentionedUser;
		        if (optionalUser.isPresent()) {
		            mentionedUser = optionalUser.get();
		        } else {
		            throw new NotFoundException(("No user found with username: " + mention));
		        }
		        
		        mentions.add(mentionedUser);
				
			} else if (content.contains("#")) {
				if (hashtags == null) {
					hashtags = new ArrayList<>();
				}
				Hashtag hashtag = null;
				index = content.indexOf('#', 0);
				int end = content.indexOf(' ', index);
				content.replaceFirst("#", " ");
				
				String label = content.substring(index + 1, end - 1);
				if (!hashtagRepository.existsByLabel(label)) {
					hashtag = new Hashtag();
					hashtag.setLabel(label);
					hashtag.setTweets(Arrays.asList(tweet));
					
				} else {
					hashtag = hashtagRepository.findByLabel(label);
					List<Tweet> tweets = hashtag.getTweets();
					tweets.add(tweet);
					hashtag.setTweets(tweets);
				}
				hashtags.add(hashtag);
				hashtagRepository.saveAndFlush(hashtag);
			}
			
		}
		tweet.setHashtags(hashtags);
		tweet.setMentionedUsers(mentions);
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
	}

	@Override
	public TweetResponseDto getRepostById(Long id) {
		//repost tweet = a tweet that reposted another tweet
		//reposted tweet = the original tweet that was reposted
		Optional<Tweet> optionalTweet = tweetRepository.findById(id);
		Tweet tweet;

		//checking if tweet exist in database
		if (optionalTweet.isPresent()) {
			tweet = optionalTweet.get();
		} else {
			throw new NotFoundException("No Tweet found with id:" + id);
		}
		//checking if the repost tweet has been deleted
		if (tweet.isDeleted()) {
			throw new NotFoundException("The Tweet with id:" + id + " has been deleted");
		}
		//checking if the tweet is a repost tweet
		if (tweet.getRepostOf() == null) {
			throw new NotFoundException("The Tweet with id:" + id + " has no reposted Tweet");
		}
		//checking if the reposted tweet has been deleted
		if (tweet.getRepostOf().isDeleted()) {
			throw new NotFoundException("The Tweet that was reposted has been deleted");
		}

		return tweetMapper.entityToDto(tweet);
	}

	@Override
	public List<UserResponseDto> getMentionedUsersByTweetId(Long id) {
		Optional<Tweet> optionalTweet = tweetRepository.findById(id);
		Tweet tweet;
		//checking if tweet exist in database
		if (optionalTweet.isPresent()) {
			tweet = optionalTweet.get();
		} else {
			throw new NotFoundException("No Tweet found with id:" + id);
		}
		//checking if that tweet has been deleted
		if (tweet.isDeleted()) {
			throw new NotFoundException("The Tweet with id:" + id + " has been deleted");
		}
		//checking if the tweet has any mentioned users
		if (tweet.getMentionedUsers().isEmpty()) {
			throw new NotFoundException("The Tweet with id:" + id + " has no mentioned Users");
		}

		//removing any deleted users that is mentioned in the list
		List<User> mentionedUsers = tweet.getMentionedUsers();
		mentionedUsers.removeIf(User::isDeleted);

		//list is empty after removing deleted users
		if (mentionedUsers.isEmpty()) {
			throw new NotFoundException("The Tweet with id:" + id + " mentions no active Users");
		}

		//parsing returned users to only their usernames
		List<UserResponseDto> userResponseDtos = userMapper.entitiesToDtos(mentionedUsers);
		for (UserResponseDto u: userResponseDtos) {
			u.setProfileDto(null);
			u.setJoined(null);
		}
		return userResponseDtos;

//		return userMapper.entitiesToDtos(mentionedUsers);
	}

}
