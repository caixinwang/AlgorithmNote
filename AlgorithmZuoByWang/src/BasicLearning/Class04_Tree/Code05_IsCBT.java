package BasicLearning.Class04_Tree;

import java.util.LinkedList;
import java.util.Queue;

public class Code05_IsCBT {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 对层序遍历进行修改，如果发现某一个入队结点不是左右孩子健全的，那么接下来入队的结点都是叶子结点。
     * 入队的结点不能有左没右，否则就不是完全二叉树
     * @param head
     * @return
     */
    public static boolean isCBT1(Node head) {
        if (head == null) {
            return true;
        }
        Queue<Node> queue=new LinkedList<>();
        queue.add(head);
        boolean flag=false;
        while(!queue.isEmpty()){
            head= queue.poll();
            if (flag&&(head.left!=null||head.right!=null)) return false;//出现过左右孩子不双全的情况之后的结点必须为叶子结点
            if (head.right!=null&&head.left==null) return false;//有右无左必定不是完全二叉树
            if (head.left==null||head.right==null) flag=true;//是否出现过左右孩子不双全的情况
            if (head.left != null) {
                queue.add(head.left);
            }
            if (head.right != null) {
                queue.add(head.right);
            }
        }
        return true;
    }

    public static boolean isCBT2(Node head) {
        return  process(head).isCom;
    }

    // 对每一棵子树，是否是满二叉树、是否是完全二叉树、高度
    public static class Info {
        public boolean isFull;
        public boolean isCom;
        public int height;

        public Info(boolean isFull, boolean isCom, int height) {
            this.isFull = isFull;
            this.isCom = isCom;
            this.height = height;
        }
    }

    public static Info process(Node head) {
        if (head==null) return new Info(true,true,0);
        Info left=process(head.left);
        Info right=process(head.right);
        boolean isFull= left.isFull&& right.isFull&& left.height== right.height;
        boolean isCom= isFull||
                left.isFull&& right.isFull&&left.height-1==right.height||
                left.isCom&& right.isFull&&left.height-1==right.height||
                left.isFull&&right.isCom&& left.height== right.height;
        int height=Math.max(left.height, right.height)+1;
        return new Info(isFull,isCom,height);
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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isCBT1(head) != isCBT2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
