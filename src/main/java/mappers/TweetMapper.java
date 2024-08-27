package mappers;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { HashtagMapper.class } )
public interface TweetMapper {

}
