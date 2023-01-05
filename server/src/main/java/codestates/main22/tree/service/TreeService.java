package codestates.main22.tree.service;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.tree.entity.Tree;
import codestates.main22.tree.repository.TreeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TreeService {
    private final TreeRepository treeRepository;

    public TreeService(TreeRepository treeRepository) {this.treeRepository = treeRepository;}

    public Tree createTree(Tree tree) {
        tree.setTreePoint(0);
        return treeRepository.save(tree);
    }
    public Tree updateTree(Tree tree) {
        Tree findTree =findVerifiedTree(tree.getTreeId());
        Optional.ofNullable(tree.getTreePoint()).ifPresent(findTree::setTreePoint);
        Optional.ofNullable(tree.getTreeImage()).ifPresent(findTree::setTreeImage);
        return treeRepository.save(tree);
    }

    public Tree findTree(long treeId) {
        Tree tree = findVerifiedTree(treeId);
        return tree;
    }

    public void deleteTree(long treeId) {treeRepository.deleteById(treeId);}


    public Tree findVerifiedTree(long treeId) {
        Optional<Tree> optionalTree = treeRepository.findById(treeId);
        Tree findTree = optionalTree.orElseThrow(() -> new BusinessLogicException(ExceptionCode.TREE_NOT_FOUND));
        return findTree;
    }
}
