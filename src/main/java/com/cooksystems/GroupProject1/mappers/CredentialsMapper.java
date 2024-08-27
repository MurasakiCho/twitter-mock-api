package com.cooksystems.GroupProject1.mappers;

import org.mapstruct.Mapper;

import com.cooksystems.GroupProject1.dtos.CredentialsDto;
import com.cooksystems.GroupProject1.entities.Credentials;

@Mapper(componentModel = "spring" )

public interface CredentialsMapper {

	CredentialsDto entityToDto(Credentials entity);
}
