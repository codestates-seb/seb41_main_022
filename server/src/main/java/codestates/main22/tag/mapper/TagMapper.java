package codestates.main22.tag.mapper;

import codestates.main22.tag.dto.TagReponseDto;
import codestates.main22.tag.dto.TagRequestDto;
import codestates.main22.tag.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag tagPostDtoToTag(TagRequestDto.Post post);
    TagReponseDto.Post tagToTagPostDto(Tag tag);
}