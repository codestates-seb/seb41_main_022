package codestates.main22.hashtag.mapper;

import codestates.main22.hashtag.dto.HashtagDto;
import codestates.main22.hashtag.entity.HashtagEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
    HashtagEntity hashtagPostDtoToHashtagEntity(HashtagDto.Post post);
    HashtagDto.Response hashtagEntityToResponseCheck(HashtagEntity hashtag);
    List<HashtagDto.Response> hashtagsToResponse(List<HashtagEntity> hashtags);
}