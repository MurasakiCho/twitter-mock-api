package com.cooksystems.GroupProject1.services.Impl;

import com.cooksystems.GroupProject1.dtos.CredentialsDto;
import com.cooksystems.GroupProject1.dtos.ProfileDto;
import com.cooksystems.GroupProject1.dtos.TweetResponseDto;
import com.cooksystems.GroupProject1.dtos.UserRequestDto;
import com.cooksystems.GroupProject1.dtos.UserResponseDto;
import com.cooksystems.GroupProject1.entities.Profile;
import com.cooksystems.GroupProject1.entities.Tweet;
import com.cooksystems.GroupProject1.entities.User;
import com.cooksystems.GroupProject1.exceptions.BadRequestException;
import com.cooksystems.GroupProject1.exceptions.NotAuthorizedException;
import com.cooksystems.GroupProject1.exceptions.NotFoundException;
import com.cooksystems.GroupProject1.mappers.ProfileMapper;
import com.cooksystems.GroupProject1.mappers.TweetMapper;
import com.cooksystems.GroupProject1.mappers.UserMapper;
import com.cooksystems.GroupProject1.repositories.TweetRepository;
import com.cooksystems.GroupProject1.repositories.UserRepository;
import com.cooksystems.GroupProject1.services.UserService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final UserMapper userMapper;
    private final TweetMapper tweetMapper;
    private final ProfileMapper profileMapper;

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
    
    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();

        for (User user : users){
            if(!user.isDeleted()){
                userResponseDtos.add(userMapper.entityToDto(user).setProfileDto(profileMapper.entityToDto(user.getProfile())));
            }
        }
        return userResponseDtos;
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
    	User user = findUser(username);
    	
        return userMapper.entityToDto(user).setProfileDto(profileMapper.entityToDto(user.getProfile()));
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRequestDto.getCredentials() == null && userRequestDto.getProfile() == null) {
            throw new BadRequestException("Credentials and Profile are missing");
        }
        if (userRequestDto.getCredentials() == null) {
            throw new BadRequestException("Credentials are missing");
        }
        if (userRequestDto.getProfile() == null) {
            throw new BadRequestException("Profile is missing");
        }
        String username = userRequestDto.getCredentials().getUsername();
        String password = userRequestDto.getCredentials().getPassword();
        String email = userRequestDto.getProfile().getEmail();

        if (username == null || username.trim().isEmpty()) {
            throw new BadRequestException("Username is missing or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new BadRequestException("Password is missing or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new BadRequestException("Email is missing or empty");
        }

        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
        if(optionalUser.isPresent()){
            if(optionalUser.get().isDeleted()){
                optionalUser.get().setDeleted(false);
                userRepository.saveAndFlush(optionalUser.get());
                return userMapper.entityToDto(optionalUser.get());
            }
            throw new BadRequestException("Username already exists");
        }
        if (userRequestDto.getProfile() == null) {
            userRequestDto.setProfile(new ProfileDto());
        }
        User user = userMapper.requestDtoToEntity(userRequestDto);
        userRepository.saveAndFlush(user);
        return userMapper.entityToDto(user);
    }

    @Override
    public UserResponseDto updateUserProfile(UserRequestDto userRequestDto) {
        if (userRequestDto.getCredentials() == null && userRequestDto.getProfile() == null) {
            throw new BadRequestException("Credentials and Profile are missing");
        }
        if (userRequestDto.getCredentials() == null) {
            throw new BadRequestException("Credentials are missing");
        }
        if (userRequestDto.getProfile() == null) {
            throw new BadRequestException("Profile is missing");
        }
        String username = userRequestDto.getCredentials().getUsername();
        String password = userRequestDto.getCredentials().getPassword();
        //String email = userRequestDto.getProfile().getEmail();

        if (username == null || username.trim().isEmpty()) {
            throw new BadRequestException("Username is missing or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new BadRequestException("Password is missing or empty");
        }
//        if (email == null || email.trim().isEmpty()) {
//            throw new BadRequestException("Email is missing or empty");
//        }
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
        if(!optionalUser.isPresent()){
            throw new BadRequestException("Username does not exist");
        }
        User user = optionalUser.get();

        if(user.isDeleted()){
            throw new BadRequestException("Username has been deleted");
        }
        if(!username.equals(user.getCredentials().getUsername()) ||
                !password.equals(user.getCredentials().getPassword())){
            throw new NotAuthorizedException("Invalid Credentials, Username or Password is inccorect");
        }
        Profile profile = user.getProfile();
        ProfileDto profileDto = userRequestDto.getProfile();

        if (profileDto.getFirstName() != null) {
            profile.setFirstName(profileDto.getFirstName());
        }
        if (profileDto.getLastName() != null) {
            profile.setLastName(profileDto.getLastName());
        }
        if (profileDto.getEmail() != null) {
            profile.setEmail(profileDto.getEmail());
        }
        if (profileDto.getPhone() != null) {
            profile.setPhone(profileDto.getPhone());
        }
        return userMapper.entityToDto(userRepository.saveAndFlush(user));
    }

    @Override
    public UserResponseDto deleteUser(String username, CredentialsDto credentialsDto) {
        if (credentialsDto == null){
            throw new BadRequestException("Credentials are missing");
        }
        String password = credentialsDto.getPassword();

        if (username == null || username.trim().isEmpty()) {
            throw new BadRequestException("Username is missing or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new BadRequestException("Password is missing or empty");
        }
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
        if(!optionalUser.isPresent()){
            throw new BadRequestException("Username does not exist");
        }
        User user = optionalUser.get();

        if(!username.equals(user.getCredentials().getUsername()) ||
                !password.equals(user.getCredentials().getPassword())){
            throw new NotAuthorizedException("Invalid Credentials, Username or Password is inccorect");
        }
        user.setDeleted(true);
        return userMapper.entityToDto(userRepository.saveAndFlush(user));
    }

	@Override
	public List<UserResponseDto> getUserFollowing(String username) {
		User user = findUser(username);
		
        List<User> following = user.getFollowing();
        List<UserResponseDto> followingDtos = new ArrayList<>();
        for(User u : following) {
        	if (!u.isDeleted()) {
        		followingDtos.add(userMapper.entityToDto(u).setProfileDto(profileMapper.entityToDto(u.getProfile())));
        	}
        }
        
		return followingDtos;
	}

	@Override
	public List<UserResponseDto> getUserFollowers(String username) {

        User user = findUser(username);
        List<User> followers = user.getFollowers();
        List<UserResponseDto> followerDtos = new ArrayList<>();
        for(User u : followers) {
        	if (!u.isDeleted()) {
        		followerDtos.add(userMapper.entityToDto(u).setProfileDto(profileMapper.entityToDto(u.getProfile())));
        	}
        }
        
		return followerDtos;
	}

	@Override
	public List<TweetResponseDto> getUserTweets(String username) {
		User user = findUser(username);
		List<Tweet> tweets = user.getTweets();
		Collections.sort(tweets);
		Collections.reverse(tweets);
		List<TweetResponseDto> userTweetDtos = new ArrayList<>();
		for(Tweet t : tweets) {
        	if (!t.isDeleted()) {
        		userTweetDtos.add(tweetMapper.entityToDto(t));
        	}
        }
		return userTweetDtos;
	}

	@Override
	public List<TweetResponseDto> getUserMentions(String username) {
		findUser(username);
		List<Tweet> tweets = tweetRepository.findAll();
		List<TweetResponseDto> mentionedTweetDtos = new ArrayList<>();
		Collections.sort(tweets);
		Collections.reverse(tweets);
		for (Tweet t: tweets) {
			List<User> users = t.getMentionedUsers();
			for (User user: users) {
				if (user.getCredentials().getUsername().equals(username) && (!t.isDeleted())) {
					mentionedTweetDtos.add(tweetMapper.entityToDto(t));
				}
			}
		}
		return mentionedTweetDtos;
	}

    @Override
    public List<TweetResponseDto> getUserFeeds(String username){
        if(username == null || username.trim().isEmpty()){
            throw new BadRequestException("Username is missing or empty");
        }

        Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);

        if(optionalUser.isEmpty()){
            throw new BadRequestException("Username does not exist");
        }

        User user = optionalUser.get();

        if(user.isDeleted()){
            throw new BadRequestException("Username has been deleted");
        }

        List<Tweet> tweets = new ArrayList<>();
        tweets.addAll(user.getTweets());

        for(User following : user.getFollowing()){
            tweets.addAll(following.getTweets());
        }

        tweets.sort(Tweet::compareTo);

        List<TweetResponseDto> tweetResponseDtos = new ArrayList<>();

        for(Tweet tweet : tweets){
            tweetResponseDtos.add(tweetMapper.entityToDto(tweet));
        }

        return tweetResponseDtos;
    }

    @Override
    public void followUser (String username, CredentialsDto credentialsDto) {
        //credential checks
        if (credentialsDto == null){
            throw new BadRequestException("Credentials are missing");
        }

        if (credentialsDto.getUsername() == null || credentialsDto.getUsername().trim().isEmpty()){
            throw new BadRequestException("Username is missing or empty");
        }

        if (credentialsDto.getPassword() == null || credentialsDto.getPassword().trim().isEmpty()){
            throw new BadRequestException("Password is missing or empty");
        }

        Optional<User> optionalUser1 = userRepository.findByCredentialsUsername(username);
        Optional<User> optionalUser2 = userRepository.findByCredentialsUsername(credentialsDto.getUsername());

        //User checks
        if(optionalUser1.isEmpty() || optionalUser2.isEmpty()){
            throw new BadRequestException("Username does not exist");
        }
        if(optionalUser1.get().isDeleted() || optionalUser2.get().isDeleted()){
            throw new BadRequestException("Username has been deleted");
        }

        User followedUser = optionalUser1.get();
        User follower = optionalUser2.get();

        if(follower.getFollowing().contains(followedUser)){
            throw new BadRequestException("Username is already being followed");
        }

        followedUser.getFollowers().add(follower);
        follower.getFollowing().add(followedUser);

        userRepository.saveAndFlush(followedUser);
        userRepository.saveAndFlush(follower);
    }

    @Override
    public void unfollowUser (String username, CredentialsDto credentialsDto) {
        //credential checks
        if (credentialsDto == null){
            throw new BadRequestException("Credentials are missing");
        }

        if (credentialsDto.getUsername() == null || credentialsDto.getUsername().trim().isEmpty()){
            throw new BadRequestException("Username is missing or empty");
        }

        if (credentialsDto.getPassword() == null || credentialsDto.getPassword().trim().isEmpty()){
            throw new BadRequestException("Password is missing or empty");
        }

        Optional<User> optionalUser1 = userRepository.findByCredentialsUsername(username);
        Optional<User> optionalUser2 = userRepository.findByCredentialsUsername(credentialsDto.getUsername());

        //User checks
        if(optionalUser1.isEmpty() || optionalUser2.isEmpty()){
            throw new BadRequestException("Username does not exist");
        }
        if(optionalUser1.get().isDeleted() || optionalUser2.get().isDeleted()){
            throw new BadRequestException("Username has been deleted");
        }

        User followedUser = optionalUser1.get();
        User follower = optionalUser2.get();

        if(!follower.getFollowing().contains(followedUser)){
            throw new BadRequestException("Username is not a follower");
        }

        followedUser.getFollowers().remove(follower);
        follower.getFollowing().remove(followedUser);

        userRepository.saveAndFlush(followedUser);
        userRepository.saveAndFlush(follower);
    }
}

