package com.cooksystems.GroupProject1.services.Impl;

import com.cooksystems.GroupProject1.dtos.CredentialsDto;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
	private final HashtagRepository hashtagRepository;
	private final UserRepository userRepository;
    private final TweetMapper tweetMapper;
	private final UserMapper userMapper;
	
	
    public User findUser(String username) {
		Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);

        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new NotFoundException(("No user found with username: " + username));
        }
        if (user.isDeleted()) {
            throw new NotFoundException("User with username: " + username + " has been deleted");
        }
        return user;
	}
    
    public Tweet findTweet(Long id) {
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

         return tweet;
	}

    @Override
    public TweetResponseDto getTweetByID(Long id) {
        Tweet tweet = findTweet(id);
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
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
		if (tweetRequestDto == null) {
			throw new NotFoundException("Request body must not be empty.");
		} else if (tweetRequestDto.getContent() == null) {
			throw new NotFoundException("Request included no content");
		} else if (tweetRequestDto.getCredentials() == null) {
			throw new NotFoundException("Request included no credentials");
		} else if (tweetRequestDto.getCredentials().getPassword() == null) {
			throw new NotFoundException("Requested credentials did not contain a password");
		}
		User user = findUser(tweetRequestDto.getCredentials().getUsername());
		Tweet tweet = tweetMapper.DtoToEntity(tweetRequestDto);
		String content = tweetRequestDto.getContent();
		tweet.setAuthor(user);
		tweet.setContent(content);
		tweet.setInReplyTo(null);
		tweet.setLikedByUsers(null);
		tweet.setDeleted(false);
		
		List<User> mentions = null;
		List<Hashtag> hashtags = null;
		tweetRepository.saveAndFlush(tweet);
		if (content.contains("#")) {
			if (hashtags == null) {
				hashtags = new ArrayList<>();
			}
			String[] tagSplit = content.split("#");
			for (int i = 1; i < tagSplit.length; i++) {
				
				Hashtag hashtag = null;
				String label = "";
				if (tagSplit[i].contains(" ")) {
					label = "#" + tagSplit[i].split(" ")[0];
				} else {
					label = "#" + tagSplit[i];
				}
				
				if (!hashtagRepository.existsByLabel(label)) {
					hashtag = new Hashtag();
					hashtag.setLabel(label);
					List<Tweet> tweetArray = new ArrayList<>();
					tweetArray.add(tweet);
					hashtag.setTweets(tweetArray);
					
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
		
		if (content.contains("@")) {
			if (mentions == null) {
				mentions = new ArrayList<>();
			}
			String[] mentionSplit = content.split("@");
			
			for (int i = 1; i < mentionSplit.length; i++) {
				
				String mention = "";
				if (mentionSplit[i].contains(" ")) {
					mention = mentionSplit[i].split(" ")[0];
				} else {
					mention = mentionSplit[i];
				}
				User mentionedUser = findUser(mention);
		        mentions.add(mentionedUser);
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

	@Override
	public void likeTweet(long id, CredentialsDto credRequestDto) {
		User likedUser = findUser(credRequestDto.getUsername());
		Tweet tweet = findTweet(id);
        Set<User> temp = tweet.getLikedByUsers();
        temp.add(likedUser);
        tweet.setLikedByUsers(temp);
        tweetRepository.saveAndFlush(tweet);
        
        List<Tweet> tempTweets = likedUser.getLikedTweets();
        tempTweets.add(tweet);
        likedUser.setLikedTweets(tempTweets);
        userRepository.saveAndFlush(likedUser);
	}

	@Override
	public TweetResponseDto createRepost(long id, CredentialsDto credRequestDto) {
		if (credRequestDto == null) {
			throw new NotFoundException("Request body must not be empty.");
		} else if (credRequestDto.getPassword() == null) {
			throw new NotFoundException("Requested credentials did not contain a password");
		}
		Tweet tweet = findTweet(id);
		
		Tweet repost = new Tweet();
		repost.setAuthor(tweet.getAuthor());
		repost.setContent(null);
		repost.setInReplyTo(null);
		repost.setLikedByUsers(null);
		repost.setDeleted(false);
		repost.setRepostOf(tweet);
		tweetRepository.saveAndFlush(repost);
		
		List<Tweet> temp = tweet.getReposts();
		temp.add(repost);
		tweet.setReposts(temp);
		tweetRepository.saveAndFlush(tweet);
		
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(repost));
	}

}
