package codestates.main22.tag.mapper;

import codestates.main22.tag.dto.TagRequestDto;
import codestates.main22.tag.dto.TagResponseDto;
import codestates.main22.tag.entity.Tag;
import codestates.main22.tag.dto.TagResponseDto;
import codestates.main22.tag.dto.TagRequestDto;
import codestates.main22.tag.entity.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag tagReqPostDtoToTag(TagRequestDto.Post post);
    Tag tagReqPatchDtoToTag(TagRequestDto.Patch patch);
    TagResponseDto.Post tagToTagResPostDto(Tag tag);
    List<TagResponseDto.Post> tagsToTagResPostDtos(List<Tag> tags);
}