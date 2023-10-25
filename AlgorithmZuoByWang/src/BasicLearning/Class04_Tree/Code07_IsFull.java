package BasicLearning.Class04_Tree;

public class Code07_IsFull {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 第一种方法
    // 收集整棵树的高度h，和节点数n
    // 只有满二叉树满足 : 2 ^ h - 1 == n
    public static boolean isFull1(Node head) {
        Info1 res=process1(head);
        return (1<<res.height)-1== res.nodeNum;
    }

    public static class Info1 {
        public int nodeNum;
        public int height;

        public Info1(int nodeNum,int height){
            this.height=height;
            this.nodeNum=nodeNum;
        }
    }

    public static Info1 process1(Node head) {
        if (head==null) return new Info1(0,0);
        int height=0;//这里得到高度是指总共有几层，1~n层。如果是从叶子结点返回0的话得到的就是边的条数，本题是从空节点得到返回0.
        int nodeNUm=0;
        Info1 left=process1(head.left);
        Info1 right=process1(head.right);
        height= Math.max(left.height, right.height)+1;
        nodeNUm= left.nodeNum+ right.nodeNum+1;
        return new Info1(nodeNUm,height);
    }

    // 第二种方法
    // 收集子树是否是满二叉树
    // 收集子树的高度
    // 左树满 && 右树满 && 左右树高度一样 -> 整棵树是满的
    public static boolean isFull2(Node head) {
        return process2(head).isFull;
    }

    public static class Info2{
        boolean isFull;
        int height;

        public Info2(boolean isFull,int height){
            this.height=height;
            this.isFull=isFull;
        }
    }

    public static Info2 process2(Node head) {
        if (head==null) return new Info2(true,0);
        boolean isFull=true;
        int height=0;
        Info2 left=process2(head.left);
        Info2 right=process2(head.right);
        height=Math.max(left.height, right.height)+1;
        isFull= left.isFull&& right.isFull&& left.height== right.height;
        return new Info2(isFull,height);
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
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isFull1(head) != isFull2(head)) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }
}
