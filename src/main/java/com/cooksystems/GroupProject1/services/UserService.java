package com.cooksystems.GroupProject1.services;

import com.cooksystems.GroupProject1.dtos.UserResponseDto;
import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserByUsername(String username);
}
