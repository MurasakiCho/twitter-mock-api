package com.cooksystems.GroupProject1.services;

import com.cooksystems.GroupProject1.dtos.CredentialsDto;
import com.cooksystems.GroupProject1.dtos.TweetResponseDto;
import com.cooksystems.GroupProject1.dtos.UserRequestDto;
import com.cooksystems.GroupProject1.dtos.UserResponseDto;
import com.cooksystems.GroupProject1.entities.User;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserByUsername(String username);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUserProfile(UserRequestDto userRequestDto);

    UserResponseDto deleteUser(String username, CredentialsDto credentialsDto);

	List<UserResponseDto> getUserFollowing(String username);

	List<UserResponseDto> getUserFollowers(String username);

	List<TweetResponseDto> getUserTweets(String username);

	List<TweetResponseDto> getUserMentions(String username);

	User findUser(String username);
	
	
}
