package com.cooksystems.GroupProject1.mappers;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" , uses = { TweetMapper.class })
public interface UserMapper {

}
