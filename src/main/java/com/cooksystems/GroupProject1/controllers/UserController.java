package com.cooksystems.GroupProject1.controllers;
import com.cooksystems.GroupProject1.dtos.CredentialsDto;
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
//
//	@GetMapping("/@{username}/feed")
//	
//	@GetMapping("/@{username}/tweets")
//	
//	@GetMapping("/@{username}/mentions")
//	
//	@GetMapping("/@{username}/followers")
//
//	@GetMapping("/@{username}/following")
//	
	@PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto){
        return userService.createUser(userRequestDto);
    }

//	
//	@PostMapping("/@{username}/follow")
//	
//	@PostMapping("/@{username}/unfollow")
//
    @PatchMapping("/@{username}")
    public UserResponseDto updateUserProfile(@RequestBody UserRequestDto userRequestDto){
        return userService.updateUserProfile(userRequestDto);
    }

	@DeleteMapping("/@{username}")
    public UserResponseDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto){
        return userService.deleteUser(username, credentialsDto);
    }
	
	
	
}
