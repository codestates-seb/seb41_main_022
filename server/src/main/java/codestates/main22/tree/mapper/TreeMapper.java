package codestates.main22.tree.mapper;

import codestates.main22.tree.dto.TreeDto;
import codestates.main22.tree.entity.Tree;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    Tree treePostDtoToTree(TreeDto.Post requestBody);
    Tree treePatchDtoToTree(TreeDto.Patch requestBody);
    TreeDto.Response treeToTreeResponseDto(Tree tree);
}
