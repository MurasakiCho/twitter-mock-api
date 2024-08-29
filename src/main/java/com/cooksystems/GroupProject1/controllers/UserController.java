package com.cooksystems.GroupProject1.controllers;
import com.cooksystems.GroupProject1.dtos.UserResponseDto;
import com.cooksystems.GroupProject1.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//	@PostMapping
//	
//	@PostMapping("/@{username}/follow")
//	
//	@PostMapping("/@{username}/unfollow")
//	
//	@PatchMapping("/@{username}")
//	
//	@DeleteMapping("/@{username}")
	
	
	
}
