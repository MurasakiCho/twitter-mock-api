package com.cooksystems.GroupProject1.mappers;

import org.mapstruct.Mapper;


import com.cooksystems.GroupProject1.dtos.UserRequestDto;
import com.cooksystems.GroupProject1.dtos.UserResponseDto;
import com.cooksystems.GroupProject1.entities.User;

@Mapper(componentModel = "spring" , uses = { TweetMapper.class })
public interface UserMapper {

	UserResponseDto entityToDto(User entity);
	
	User DtoToEntity(UserRequestDto request);
}
