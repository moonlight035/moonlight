package leetcode;

import java.util.*;

public class Leetcode199 {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if(root!=null){
            result.add(root.val);
            List<Integer> leftResult = rightSideView(root.left);
            List<Integer> rightResult = rightSideView(root.right);
            for(int i=0 ; i<Math.max(leftResult.size(),rightResult.size()) ; i++){
                if(i<rightResult.size())
                    result.add(rightResult.get(i));
                else
                    result.add(leftResult.get(i));
            }
        }
        return result;
    }

    public List<Integer> rightSideViewGood(TreeNode root) {
        if(root==null)
            return new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        List<TreeNode> now = new ArrayList<>();
        now.add(root);
        while(now.size()!=0) {
            List<TreeNode> next = new ArrayList<>();
            for (int i = 0; i < now.size(); i++) {
                TreeNode node = now.get(i);
                if(i==now.size()-1)
                    result.add(node.val);
                if(node.left!=null)
                    next.add(node.left);
                if(node.right!=null)
                    next.add(node.right);
            }
            now = next;
        }
        return result;
    }

}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
