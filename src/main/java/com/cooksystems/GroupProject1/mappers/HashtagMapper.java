package com.cooksystems.GroupProject1.mappers;

import org.mapstruct.Mapper;

import com.cooksystems.GroupProject1.dtos.HashtagDto;
import com.cooksystems.GroupProject1.entities.Hashtag;

import java.util.List;


@Mapper(componentModel = "spring" )
public interface HashtagMapper {

	HashtagDto entityToDto(Hashtag entity);

	List<HashtagDto> entitiesToDtos(List<Hashtag> users);

}
