package com.cooksystems.GroupProject1.services;

import com.cooksystems.GroupProject1.dtos.UserRequestDto;
import com.cooksystems.GroupProject1.dtos.UserResponseDto;
import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserByUsername(String username);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUserProfile(UserRequestDto userRequestDto);

    UserResponseDto deleteUser(String username, UserRequestDto userRequestDto);
}
