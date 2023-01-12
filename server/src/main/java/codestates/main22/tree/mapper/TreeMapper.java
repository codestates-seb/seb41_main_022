package codestates.main22.tree.mapper;

import codestates.main22.tree.dto.TreeDto;
import codestates.main22.tree.entity.Tree;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    Tree treePostDtoToTree(TreeDto.Post requestBody);
    Tree treePatchDtoToTree(TreeDto.Patch requestBody);
    TreeDto.UserResponse treeToTreeResponseDto(Tree tree);
    default List<TreeDto.UserResponse> treesToTreeUserResponseDto(List<Object> treesAndTeamNames) {
        List<TreeDto.UserResponse> responses = new ArrayList<>();

        for(int i=0; i<treesAndTeamNames.size(); i=i+2) {
            Tree tree = (Tree) treesAndTeamNames.get(i);
            String teamName = (String) treesAndTeamNames.get(i+1);

            TreeDto.UserResponse response = new TreeDto.UserResponse(
                    tree.getTreeId(),
                    tree.getTreePoint(),
                    tree.getTreeImage(),
                    tree.getCreatedAt(),
                    tree.getMakeMonth(),
                    teamName
            );
            responses.add(response);
        }

        return responses;
    }
    List<TreeDto.StudyResponse> treesToTreeStudyResponseDto(List<Tree> tree);
}
