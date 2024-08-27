package com.cooksystems.GroupProject1.mappers;

import mappers.HashtagMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { HashtagMapper.class } )
public interface TweetMapper {

}
