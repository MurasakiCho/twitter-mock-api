package com.cooksystems.GroupProject1.mappers;

import org.mapstruct.Mapper;



@Mapper(componentModel = "spring", uses = { HashtagMapper.class } )
public interface TweetMapper {
	
//	TweetResponseDto entityToDto(Tweet entity);
//	
//	Tweet DtoToEntity(TweetRequestDto request);
}
