package com.cooksystems.GroupProject1.mappers;

import org.mapstruct.Mapper;

import com.cooksystems.GroupProject1.dtos.TweetRequestDto;
import com.cooksystems.GroupProject1.dtos.TweetResponseDto;
import com.cooksystems.GroupProject1.entities.Tweet;

import java.util.List;


@Mapper(componentModel = "spring", uses = { UserMapper.class } )
public interface TweetMapper {
	
	TweetResponseDto entityToDto(Tweet entity);
	
	Tweet DtoToEntity(TweetRequestDto request);

	List<TweetResponseDto> entitiesToDto(List<Tweet> tweets);
}
