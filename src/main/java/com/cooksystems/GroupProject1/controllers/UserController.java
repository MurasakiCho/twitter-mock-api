package com.cooksystems.GroupProject1.controllers;
import com.cooksystems.GroupProject1.dtos.CredentialsDto;
import com.cooksystems.GroupProject1.dtos.TweetResponseDto;
import com.cooksystems.GroupProject1.dtos.UserRequestDto;
import com.cooksystems.GroupProject1.dtos.UserResponseDto;
import com.cooksystems.GroupProject1.services.UserService;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }

	@GetMapping("/@{username}")
    public UserResponseDto getUser(@PathVariable String username){
        return userService.getUserByUsername(username);
    }
	@GetMapping("/@{username}/feed")
    public List<TweetResponseDto> getUserFeeds(@PathVariable String username){
        return userService.getUserFeeds(username);
    }

	@GetMapping("/@{username}/tweets")
	public List<TweetResponseDto> getUserTweets(@PathVariable String username){
        return userService.getUserTweets(username);
    }
	
	@GetMapping("/@{username}/mentions")
	public List<TweetResponseDto> getUserMentions(@PathVariable String username){
        return userService.getUserMentions(username);
    }
	
	@GetMapping("/@{username}/followers")
	public List<UserResponseDto> getUserFollowers(@PathVariable String username){
        return userService.getUserFollowers(username);
    }
	
	@GetMapping("/@{username}/following")
	public List<UserResponseDto> getUserFollowing(@PathVariable String username){
        return userService.getUserFollowing(username);
    }
	
	@PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto){
        return userService.createUser(userRequestDto);
    }

    @PostMapping("/@{username}/follow")
    public void followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto){ userService.followUser(username, credentialsDto);}

    @PostMapping("/@{username}/unfollow")
    public void unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto){ userService.unfollowUser(username, credentialsDto);}

    @PatchMapping("/@{username}")
    public UserResponseDto updateUserProfile(@RequestBody UserRequestDto userRequestDto){
        return userService.updateUserProfile(userRequestDto);
    }

	@DeleteMapping("/@{username}")
    public UserResponseDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto){
        return userService.deleteUser(username, credentialsDto);
    }
}
