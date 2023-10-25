package BasicLearning.Class04_Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 题目：返回整棵树的最大距离。
 * 一个结点到另一个结点的最大距离就是中间要经过的节点数（算上自己），可以走曲线，走上走下
 * 也就是左边的高度加上右边的高度+1
 */
public class Code13_MaxDistance {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 思路：枚举任意两个结点，计算他们的距离，然后一直更新max。枚举行为O(N²），计算距离O(log N），所以总的O(N² logN）复杂度
     * 枚举行为利用一个ArrayList来，把树上所有的结点加到List里面。找parent算距离的行为利用一个HashMap。
     * 在计算距离之前，需要把HashMap的parent表填好
     * @param head 计算以head为头的子树中的最大距离，可以跨越左右子树
     * @return 返回最大距离
     */
    public static int maxDistance1(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = getPrelist(head);
        HashMap<Node, Node> parentMap = getParentMap(head);
        int max = 0;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i; j < arr.size(); j++) {
                max = Math.max(max, distance(parentMap, arr.get(i), arr.get(j)));
            }
        }
        return max;
    }

    public static ArrayList<Node> getPrelist(Node head) {//获得先序序列，存进List集合中
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        return arr;
    }

    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static HashMap<Node, Node> getParentMap(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        map.put(head, null);
        fillParentMap(head, map);
        return map;
    }

    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    /**
     * 两个结点o1，o2的距离就是找到公共结点share，share到o1的高度加上share到o2的高度+1就是答案
     * 方法1：先找到公共结点share。分别算出左子树的高度和右子树的高度，两个相加再+1就是距离
     * 从node一直往上蹿，蹿到root就出循环，那么res的累加量就是子树的高度！
     * @param parentMap 有着树所有结点对应的父节点，根节点的父是null
     * @param o1 结点
     * @param o2 结点
     * @return 返回o1和o2直接的距离
     */
    public static int distance(HashMap<Node, Node> parentMap, Node o1, Node o2) {
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        Node lowestAncestor = cur;//先找到最低公共祖先
        cur = o1;
        int left = 0;//左子树高度
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            left++;
        }
        cur = o2;
        int right = 0;//右子树高度
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            right++;
        }
        return left + right + 1;//重复算了最低公共祖先
    }

    /**
     * 两个结点o1，o2的距离就是找到公共结点share，share到o1的高度加上share到o2的高度+1就是答案
     * 方法2：先找到公共结点，在找公共结点的过程中，o1结点往上蹿了一条链顺便累计了长度，o2结点也往上蹿了一条链继续累计长度，
     * 和o1的链进行了相交，找到了公共结点。o2累计了右边子树的高度。但是o1多累计了从公共结点往上的高度，要减掉才是o1和o2
     * 子树的高度和，最后加上1就是两个结点之间的距离了。结点的距离就是左边高度加上右边的高度再+1
     * 从node一直往上蹿，蹿到root就出循环，那么res的累加量就是子树的高度！
     * 从node一直往上蹿，蹿到null就出循环，那么res的累加量就是子树的高度+1，算上了根节点
     * @param parentMap 有着树所有结点对应的父节点，根节点的父是null
     * @param o1 结点
     * @param o2 结点
     * @return 返回o1和o2直接的距离
     */
    public static int distance2(HashMap<Node, Node> parentMap, Node o1, Node o2) {
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        int res=0;
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
            res++;
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
            res++;
        }
        while(parentMap.get(cur)!=null){
            cur = parentMap.get(cur);
            res--;
        }
        return res+1;
    }

    public static int maxDistance2(Node head) {
        return process(head).maxDistance;
    }

    public static class Info {
        public int maxDistance;
        public int height;

        public Info(int m, int h) {
            maxDistance = m;
            height = h;
        }

    }

    /**
     * 可能性分析：1. 如果最大距离经过了x，那么最大距离就是左树的高度和右树的高度之和+1
     * 2. 如果最大距离没有经过x，那么最大距离就是左右子树中最大距离中较大的那个
     * 这样我们就列出了全部的最大距离的可能性,最大距离就是1和2两种情况中最大的那个
     * 高度没有可能性可言，就是左右子树中的高度较大者+1
     * @param x 头节点
     * @return 返回以x为头的树的最大距离
     */
    public static Info process(Node x) {
        if (x==null) return new Info(0,0);
        Info left=process(x.left);
        Info right=process(x.right);
        int maxDistance=0;
        int height=0;
        maxDistance= Math.max(Math.max(left.maxDistance,right.maxDistance), left.height+right.height+1);
        height= Math.max(left.height, right.height)+1;
        return new Info(maxDistance,height);
    }

    /**
     * 不使用递归套路。也是分为与head有关和无关。
     * 如果和head有关，那么最大距离就是左右子树的高度之和再+1
     * 如何无关，那么就是左右子树去递归，选一个最大值
     * @param head 结点
     * @return 返回以head为头的树的最大距离
     */
    public static int maxDistance3(Node head) {
        if (head==null) return 0;
        return Math.max(height(head.left)+height(head.right)+1,Math.max(maxDistance3(head.left),maxDistance3(head.right)));
    }

    public static int height(Node head){
        if (head==null)return 0;
        return Math.max(height(head.left),height(head.right))+1;
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

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxDistance3(head) != maxDistance2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
