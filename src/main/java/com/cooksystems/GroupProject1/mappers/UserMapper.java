package com.cooksystems.GroupProject1.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksystems.GroupProject1.dtos.UserRequestDto;
import com.cooksystems.GroupProject1.dtos.UserResponseDto;
import com.cooksystems.GroupProject1.entities.User;

@Mapper(componentModel = "spring" , uses = {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {

	@Mapping(target = "username", source = "credentials.username")
    UserResponseDto entityToDto(User user);

    User responseDtoToEntity(UserResponseDto userResponseDto);

    User requestDtoToEntity(UserRequestDto userRequestDto);

    List<UserResponseDto> entitiesToDtos(List<User> users);
}
