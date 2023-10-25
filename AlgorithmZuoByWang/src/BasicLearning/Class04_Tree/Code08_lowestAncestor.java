package BasicLearning.Class04_Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 最低公共祖先问题。在以head为头的树上，返回树上两个结点o1和o2的最低公共祖先。
 */
public class Code08_lowestAncestor {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 把o1结点到头节点经过的所有祖先结点都放到一个集合里面，然后对o2到头节点的每一个结点都判断一下在不在集合里面。
     * 如果在集合里面那么就说明当前这个结点就是o1和o2的公共祖先。那么这里的问题就是如何有一个机制可以使得孩子结点
     * 找到父亲结点？方法就是先遍历一遍二叉树，把树里面的所有的结点的父亲结点都放到一个带附加值的hashtable里面。
     * 我们可以专门写一个函数fillParentMap来做这件事情。
     */
    public static Node lowestAncestor1(Node head, Node o1, Node o2) {
        if (head == null) return null;
        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap(head, parentMap);
        HashSet<Node> set = new HashSet<>();
        while (o1 != null) {
            set.add(o1);
            o1 = parentMap.get(o1);//往上翻
        }
        while (o2 != null && !set.contains(o2)) {
            o2 = parentMap.get(o2);
        }
        return o2;
    }

    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head == null) return;
        if (head.left != null) parentMap.put(head.left, head);
        if (head.right != null) parentMap.put(head.right, head);
        fillParentMap(head.left, parentMap);
        fillParentMap(head.right, parentMap);
    }

    /**
     * 利用递归套路来做。
     */
    public static Node lowestAncestor2(Node head, Node a, Node b) {
        return process(head, a, b).ancestor;
    }

    public static class Info {
        public boolean findA;
        public boolean findB;
        public Node ancestor;

        //info是给某一个结点用的信息，代表这个结点是不是最低公共祖先，以及以这个结点为头的树能不能找到a和b
        public Info(boolean findA, boolean findB, Node ancestor) {
            this.ancestor = ancestor;
            this.findA = findA;
            this.findB = findB;
        }
    }

    public static Info process(Node head, Node a, Node b) {
        if (head == null) return new Info(false, false, null);
        Info left = process(head.left, a, b);
        Info right = process(head.right, a, b);
        Node ancestor = null;
        boolean findA = false;
        boolean findB = false;
        //如果从head结点走下去能够同时找到a和b，并且左子树和右子树中无法同时找到a和b，那么head就是最低公共祖先
        findA = left.findA || right.findA || head == a;
        findB = left.findB || right.findB || head == b;
        if (left.ancestor != null) {//如果左子树中也能同时找到a和b，说明当前的head结点不是最低公共祖先。
            ancestor = left.ancestor;
        } else if (right.ancestor != null) {
            ancestor = right.ancestor;
        } else if (findA && findB) {//左右子树都找不到a和b，但是在head同时找到了，说明head就是最低公共祖先
            ancestor = head;
        }
        return new Info(findA, findB, ancestor);
    }

    /**
     * 最精简的代码。如果遇到了null或者a或者b就会一路往上扔。扔到上面进行处理。如果在某一个结点发现它的左右两边都有返回非空
     * 也就是说左右两边分别找到了a和b了，那么就直接返回当前这个结点，继续往上扔，这种情况是返回当前结点的，也就是a和b分别在
     * 两侧的情况。还有另外一种情况是a和b在同一边，这种情况就只返回a或者b。这种情况也会把非空的其中一边一路往上扔，也就是a和b
     * 的其中一个一直往上扔。
     * <p>
     * 思想：利用递归的返回来模拟往上找父亲的过程！！
     */
    public static Node lowestAncestor3(Node head, Node a, Node b) {
        if (head == null || head == a || head == b) return head;//走到空了或者找到了a或者b就返回
        Node left = lowestAncestor3(head.left, a, b);
        Node right = lowestAncestor3(head.right, a, b);
        if (left != null && right != null) return head;//说明两边都找到了
        return left != null ? left : right;
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // for test
    public static Node pickRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    // for test
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            if (lowestAncestor1(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }


}
