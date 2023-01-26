package codestates.main22.tree.service;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.study.entity.Study;
import codestates.main22.study.repository.StudyRepository;
import codestates.main22.tree.entity.Tree;
import codestates.main22.tree.repository.TreeRepository;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.repository.UserRepository;
import codestates.main22.utils.Init;
import codestates.main22.utils.Token;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;

@Service
public class TreeService {
    private final TreeRepository treeRepository;
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final Token token;

    public TreeService(TreeRepository treeRepository,
                       StudyRepository studyRepository,
                       UserRepository userRepository,
                       Token token) {
        this.treeRepository = treeRepository;
        this.studyRepository = studyRepository;
        this.userRepository = userRepository;
        this.token = token;
    }

    public Tree createTree(Tree tree) {
        tree.setTreePoint(0);
        return treeRepository.save(tree);
    }

    // 새 트리 생성
    public Tree createTree(Study study) {
        Tree tree = new Tree();
        tree.setTreeImage(Init.treeFirstImage);
        tree.setMakeMonth(LocalDate.now().getMonthValue());
//        tree.setMakeMonth(12); // createTreesAllStudies 테스트
        tree.setStudy(study);

        return treeRepository.save(tree);
    }

    // 마지막에 생성된 트리 가져오기
    public Tree findfinalCreatedTree(Study study) {
        return study.getTrees().get(study.getTrees().size() - 1);
    }

    public Tree updateTree(Tree tree) {
        Tree findTree = VerifiedTree(tree.getTreeId());
        Optional.ofNullable(tree.getTreePoint()).ifPresent(findTree::setTreePoint);
        Optional.ofNullable(tree.getTreeImage()).ifPresent(findTree::setTreeImage);
        return treeRepository.save(findTree);
    }

    // 트리 포인트 업데이트
    public Tree updateTreePoint(Tree tree, int point) {
        point += tree.getTreePoint();
        tree.setTreePoint(point);

        // 특정 기준점 초과시 이미지 업데이트
        if(point >= Init.treeFinalUpgradeScore)
            tree.setTreeImage(Init.tree2023Image.get(LocalDate.now().getMonthValue()-1));
        else if(point >= Init.treeSecondUpgradeScore)
            tree.setTreeImage(Init.treeThirdImage);
        else if(point >= Init.treeFirstUpgradeScore)
            tree.setTreeImage(Init.treeSecondImage);

        return treeRepository.save(tree);
    }

    // 트리 포인트 업데이트
    public Tree updateTreePoint(Study study, int point) {
        Tree tree = findfinalCreatedTree(study);
        point += tree.getTreePoint();
        tree.setTreePoint(point);

        // 특정 기준점 초과시 이미지 업데이트
        if(point >= Init.treeFinalUpgradeScore)
            tree.setTreeImage(Init.tree2023Image.get(LocalDate.now().getMonthValue()-1));
        else if(point >= Init.treeSecondUpgradeScore)
            tree.setTreeImage(Init.treeThirdImage);
        else if(point >= Init.treeFirstUpgradeScore)
            tree.setTreeImage(Init.treeSecondImage);

        return treeRepository.save(tree);
    }

    public Tree findTree(long treeId) {
        Tree tree = VerifiedTree(treeId);
        return tree;
    }

    // userId를 기준으로 tree 조회
    public List<Object> findTreeByUserId(HttpServletRequest request) {
        UserEntity user = token.findByToken(request);
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
        Optional<Study> optionalStudy = studyRepository.findById(studyId);
        Study study = optionalStudy.orElseThrow(() -> new BusinessLogicException(ExceptionCode.STUDY_NOT_FOUND));
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
