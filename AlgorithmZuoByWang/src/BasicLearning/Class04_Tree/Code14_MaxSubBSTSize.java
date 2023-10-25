package BasicLearning.Class04_Tree;

import java.util.ArrayList;

public class Code14_MaxSubBSTSize {//最大二叉搜索子树

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static int getBSTSize(Node head) {
        if (head == null) return 0;
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) return;
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    public static int maxSubBSTSize1(Node head) {
        if (head == null) return 0;
        int h = getBSTSize(head);
        if (h != 0) return h;
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
    }

    public static int maxSubBSTSize2(Node head) {
        if(head == null) return 0;
        return process(head).maxBSTSubtreeSize;
    }

    //递归套路需要的信息
    public static class Info {
        public int maxBSTSubtreeSize;
        public boolean isAllBst;
        public int max;
        public int min;

        public Info(int maxBSTSubtreeSize, boolean isAllBst, int max, int min) {
            this.maxBSTSubtreeSize = maxBSTSubtreeSize;
            this.isAllBst = isAllBst;
            this.max = max;
            this.min = min;
        }
    }

    /**
     * 在判断isBST的递归套路上多了计算最大搜索子树的大小
     * 可能性1.信息和x结点无关。也就是说明左右子树有至少一个为不是二叉搜索树。此时最大的二叉搜索子树大小就是左右子树的最大二插子树大小中较大的那个
     * 可能性2.信息和x结点有关。这时候就说明左右子树都是二叉搜索树。最大的二叉搜索子树就变成了以x为头的树的大小。
     *
     * @param x:返回以x为头的树的相关信息，包括了以x为头的子树是不是二叉搜索树、x为头的子树的最大值、x为头的子树的最小值、
     *         x为头的最大子搜索树的大小（结点个数）。
     * @return ：
     */
    public static Info process(Node x) {
        if (x==null) return null;//返回空是因为min和max返回什么都不是很合适
        Info left=process(x.left);
        Info right=process(x.right);
        int maxBSTSubtreeSize=0;
        boolean isAllBst=true;
        int max=x.value;
        int min=x.value;
        if (right!=null){
            max=Math.max(right.min,x.value);
        }
        if (left!=null){
            min=Math.min(left.max,x.value);
        }
        isAllBst= left.isAllBst&&right.isAllBst&&x.value> left.max&&x.value< right.min;
        maxBSTSubtreeSize=isAllBst? left.maxBSTSubtreeSize+ right.maxBSTSubtreeSize+1:
                Math.max(left.maxBSTSubtreeSize,right.maxBSTSubtreeSize);
        return new Info(maxBSTSubtreeSize,isAllBst,max,min);
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
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
