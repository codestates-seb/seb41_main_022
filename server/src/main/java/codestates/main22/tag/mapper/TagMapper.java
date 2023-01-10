package codestates.main22.tag.mapper;

import codestates.main22.tag.dto.TagRequestDto;
import codestates.main22.tag.dto.TagResponseDto;
import codestates.main22.tag.entity.Tag;
import codestates.main22.tag.dto.TagResponseDto;
import codestates.main22.tag.dto.TagRequestDto;
import codestates.main22.tag.entity.Tag;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag tagReqPostDtoToTag(TagRequestDto.Post post);
    Tag tagReqPatchDtoToTag(TagRequestDto.Patch patch);
    TagResponseDto.Post tagToTagResPostDto(Tag tag);
    List<TagResponseDto.Post> tagsToTagResPostDtos(List<Tag> tags);
    default TagResponseDto.Get tagsToTagResGetDtos(List<Tag> tags) {
        List<String> tagList = tags.stream().map(
                tag -> tag.getName()
        ).collect(Collectors.toList());

        TagResponseDto.Get gets = new TagResponseDto.Get(tagList);

        return gets;
    }
}