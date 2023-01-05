package codestates.main22.personalDm.mapper;

import codestates.main22.personalDm.dto.PersonalDmDto;
import codestates.main22.personalDm.entity.PersonalDmEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonalDmMapper {
    PersonalDmEntity personalDmDtoToPersonalDmEntity(PersonalDmDto.Post post);
    PersonalDmDto.Response personalDmEntityToResponseCheck(PersonalDmEntity personalDm);
    List<PersonalDmDto.Response> personalDmsToResponse(List<PersonalDmEntity> personalDms);
}
