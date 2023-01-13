package codestates.main22.tree.service;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.study.entity.Study;
import codestates.main22.study.repository.StudyRepository;
import codestates.main22.study.service.StudyService;
import codestates.main22.tree.dto.TreeDto;
import codestates.main22.tree.entity.Tree;
import codestates.main22.tree.repository.TreeRepository;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class TreeService {
    private final TreeRepository treeRepository;
    private final StudyRepository studyRepository;
    private final StudyService studyService;
    private final UserRepository userRepository;

    public TreeService(TreeRepository treeRepository,
                       StudyRepository studyRepository,
                       StudyService studyService,
                       UserRepository userRepository) {
        this.treeRepository = treeRepository;
        this.studyRepository = studyRepository;
        this.studyService = studyService;
        this.userRepository = userRepository;
    }

    public Tree createTree(Tree tree) {
        tree.setTreePoint(0);
        return treeRepository.save(tree);
    }

    public Tree updateTree(Tree tree) {
        Tree findTree = VerifiedTree(tree.getTreeId());
        Optional.ofNullable(tree.getTreePoint()).ifPresent(findTree::setTreePoint);
        Optional.ofNullable(tree.getTreeImage()).ifPresent(findTree::setTreeImage);
        return treeRepository.save(tree);
    }

    public Tree findTree(long treeId) {
        Tree tree = VerifiedTree(treeId);
        return tree;
    }

    // userId를 기준으로 tree 조회
    public List<Object> findTreeByUserId(HttpServletRequest request) {
        UserEntity user = userRepository.findByToken(request);
        List<Study> studies = studyRepository.findByUserStudiesUser(user);

        List<Object> treesAndTeamNames = new ArrayList<>();
        studies.stream().forEach(study ->
                study.getTrees().stream().forEach(tree -> {
                            treesAndTeamNames.add(tree);
                            treesAndTeamNames.add(study.getTeamName());
                        }
                )
        );

        return treesAndTeamNames;
    }

    // studyId를 기준으로 tree 조회
    public List<Tree> findTreeByStudyId(long studyId) {
        Study study = studyService.findStudy(studyId);
        List<Tree> trees = new ArrayList<>();

        study.getTrees().stream().forEach(tree ->
                trees.add(tree)
        );

        return trees;
    }

    public void deleteTree(long treeId) {
        treeRepository.deleteById(treeId);
    }


    public Tree VerifiedTree(long treeId) {
        Optional<Tree> optionalTree = treeRepository.findById(treeId);
        Tree findTree = optionalTree.orElseThrow(() -> new BusinessLogicException(ExceptionCode.TREE_NOT_FOUND));
        return findTree;
    }
}
