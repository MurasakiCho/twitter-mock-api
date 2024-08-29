package com.cooksystems.GroupProject1.services.Impl;

import com.cooksystems.GroupProject1.dtos.ProfileDto;
import com.cooksystems.GroupProject1.dtos.UserResponseDto;
import com.cooksystems.GroupProject1.entities.Tweet;
import com.cooksystems.GroupProject1.entities.User;
import com.cooksystems.GroupProject1.exceptions.NotFoundException;
import com.cooksystems.GroupProject1.mappers.ProfileMapper;
import com.cooksystems.GroupProject1.mappers.UserMapper;
import com.cooksystems.GroupProject1.repositories.UserRepository;
import com.cooksystems.GroupProject1.services.UserService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProfileMapper profileMapper;

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();

        for (User user : users){
            if(!user.isDeleted()){
//                ProfileDto profileDto = profileMapper.entityToDto(user.getProfile());
//                UserResponseDto userResponseDto = userMapper.entityToDto(user);
//                userResponseDto.setProfileDto(profileDto);
//                userResponseDtos.add(userResponseDto);
                userResponseDtos.add(userMapper.entityToDto(user).setProfileDto(profileMapper.entityToDto(user.getProfile())));
            }
        }
        return userResponseDtos;
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
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
        return userMapper.entityToDto(user).setProfileDto(profileMapper.entityToDto(user.getProfile()));
    }

//        ProfileDto profileDto = profileMapper.entityToDto(user.getProfile());
//        UserResponseDto userResponseDto = userMapper.entityToDto(user);
//        userResponseDto.setProfileDto(profileDto);
//        return userResponseDto;
}

