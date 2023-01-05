package codestates.main22.dm.mapper;

import codestates.main22.dm.dto.DmDto;
import codestates.main22.dm.entity.DmEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DmMapper {
    DmEntity dmPostDtoToDmEntity(DmDto.Post post);
    DmDto.Response dmEntityToResponseCheck(DmEntity dmEntity);
    List<DmDto.Response> dmsToResponse(List<DmEntity> dmEntities);
}
