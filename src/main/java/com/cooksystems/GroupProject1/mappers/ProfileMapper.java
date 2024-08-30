package com.cooksystems.GroupProject1.mappers;

import org.mapstruct.Mapper;

import com.cooksystems.GroupProject1.dtos.ProfileDto;
import com.cooksystems.GroupProject1.entities.Profile;

@Mapper(componentModel = "spring" )
public interface ProfileMapper {

	ProfileDto entityToDto(Profile entity);

	Profile dtoToEntity(ProfileDto profileDto);
}
