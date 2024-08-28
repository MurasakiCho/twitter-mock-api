package com.cooksystems.GroupProject1.mappers;

import org.mapstruct.Mapper;

import com.cooksystems.GroupProject1.dtos.TweetRequestDto;
import com.cooksystems.GroupProject1.dtos.TweetResponseDto;
import com.cooksystems.GroupProject1.entities.Tweet;



@Mapper(componentModel = "spring", uses = { UserMapper.class } )
public interface TweetMapper {
	
	TweetResponseDto entityToDto(Tweet entity);
	
	Tweet DtoToEntity(TweetRequestDto request);
}
