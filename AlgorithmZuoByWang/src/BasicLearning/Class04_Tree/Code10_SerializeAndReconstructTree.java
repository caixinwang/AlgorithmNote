package BasicLearning.Class04_Tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 题目：将树结构进行序列化和反序列化
 * 注意一定要补齐空节点，因为如果不这么做，那么如果结点的值有相等的，那么就无法还原。
 */
public class Code10_SerializeAndReconstructTree {
    /*
     * 二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，
     * 以下代码全部实现了。
     * 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
     * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
     * 比如如下两棵树
     *         __2
     *        /
     *       1
     *       和
     *       1__
     *          \
     *           2
     * 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
     *
     * */
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Queue<String> preSerial(Node head) {
        if (head==null) return null;
        Queue<String> answer=new LinkedList<>();
        pres(head,answer);
        return answer;
    }

    public static void pres(Node head, Queue<String> answer) {
        if (head==null){
            answer.add(null);
        }else {
            answer.add(String.valueOf(head.value));
            pres(head.left,answer);
            pres(head.right,answer);
        }
    }

    public static Queue<String> inSerial(Node head){
        if (head==null){
            return null;
        }

        Queue<String> answer=new LinkedList<>();
        ins(head,answer);
        return answer;
    }

    public static void ins(Node head, Queue<String> answer){
        if (head==null){
            answer.add(null);
        }else {
            ins(head.left,answer);
            answer.add(String.valueOf(head.value));
            ins(head.right,answer);
        }
    }

    public static Queue<String> posSerial(Node head){
        if (head==null){
            return null;
        }

        Queue<String> answer=new LinkedList<>();
        poss(head,answer);
        return answer;
    }

    public static void poss(Node head, Queue<String> answer){
        if (head==null){
            answer.add(null);
        }else {
            poss(head.left,answer);
            poss(head.right,answer);
            answer.add(String.valueOf(head.value));
        }
    }

    public static Node buildByPreQueue(Queue<String> prelist){
        if (prelist==null||prelist.isEmpty()){
            return null;
        }
        return preb(prelist);
    }

    public static Node preb(Queue<String> prelist){
        String value=prelist.poll();//Node根据String的值来创建，只有String非空的时候才创建Node，否则Node就是空
        if (value==null){
            return null;
        }
        Node head=new Node(Integer.valueOf(value));
        head.left=preb(prelist);
        head.right=preb(prelist);
        return head;
    }

    public static Node buildByPosQueue(Queue<String> poslist){
        if (poslist==null||poslist.isEmpty()){
            return null;
        }
        Stack<String> stack=new Stack<>();
        while(!poslist.isEmpty()){//从栈中弹出的顺序是头右左
            stack.push(poslist.poll());
        }
        return posb(stack);
    }

    public static Node posb(Stack<String> posstack){
        String value=posstack.pop();
        if (value==null){
            return null;
        }
        Node head=new Node(Integer.valueOf(value));
        head.right=posb(posstack);
        head.left=posb(posstack);
        return head;
    }

    public static Queue<String> levelSerial(Node head){
        Queue<Node>queue=new LinkedList<>();//放结点
        Queue<String>res=new LinkedList<>();//放序列
        if (head==null){
            res.add(null);
        }else{
            res.add(String.valueOf(head.value));
            queue.add(head);
            Node p=null;
            while(!queue.isEmpty()){
                p=queue.poll();
                if (p.left!=null){
                    res.add(String.valueOf(p.left.value));
                    queue.add(p.left);
                }else {
                    res.add(null);
                }
                if (p.right!=null){
                    res.add(String.valueOf(p.right.value));
                    queue.add(p.right);
                }else{
                    res.add(null);
                }
            }
        }
        return res;
    }

    public static Node buildByLevelQueue(Queue<String> levelList){
        if (levelList==null||levelList.isEmpty()){
            return null;
        }
        Queue<Node> queue=new LinkedList<>();//装非空的Node，作用和层序遍历的队列一样
        Node head=generateNode(levelList.poll());
        if (head!=null){
            queue.add(head);
            Node p=null;
            while(!queue.isEmpty()){
                p= queue.poll();
                p.left=generateNode(levelList.poll());
                p.right=generateNode(levelList.poll());
                if (p.left!=null){
                    queue.add(p.left);
                }
                if (p.right!=null){
                    queue.add(p.right);
                }
            }
        }
        return head;
    }

    public static Node generateNode(String val){
        if (val==null)
            return null;
        return new Node(Integer.valueOf(val));
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
    public static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }

    // for test
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Queue<String> pre = preSerial(head);
            Queue<String> pos = posSerial(head);
            Queue<String> level = levelSerial(head);
            Node preBuild = buildByPreQueue(pre);
            Node posBuild = buildByPosQueue(pos);
            Node levelBuild = buildByLevelQueue(level);
            if (!isSameValueStructure(preBuild, posBuild) || !isSameValueStructure(posBuild, levelBuild)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }
}
