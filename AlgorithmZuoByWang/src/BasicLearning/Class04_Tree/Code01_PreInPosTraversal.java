package BasicLearning.Class04_Tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code01_PreInPosTraversal {//先序后序中序遍历二叉树
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }


    public static void preOrderRecur(Node head) {
        if (head==null) return;
        System.out.print(head.value+" ");
        preOrderRecur(head.left);
        preOrderRecur(head.right);
    }

    public static void inOrderRecur(Node head) {
        if (head==null) return;
        inOrderRecur(head.left);
        System.out.print(head.value+" ");
        inOrderRecur(head.right);
    }

    public static void posOrderRecur(Node head) {
        if (head==null) return;
        posOrderRecur(head.left);
        posOrderRecur(head.right);
        System.out.print(head.value+" ");
    }

    /**
     * 观察一下系统递归如何压栈，先进到左边递归，再进到右边递归。在左边里面又先进到左边递归再进到右边递归
     * 所以整体来说就是右结点先进栈，左节点后进栈然后弹出可以模拟上面的递归行为
     * 思路：和下面这种实现不同，这种实现一个结点，特别是head的左节点，刚进栈就要出来了，也就是说只来到每个结点一次。
     * 没有打印时机的选择问题，只能弹出的时候打印.栈的作用只是维持弹出结点的顺序。保证是按左边界分解的弹出顺序。
     * @param head:先序打印二叉树，非递归实现
     */
    public static void preOrderUnRecur(Node head){
        System.out.print("preorder unrecur:");
        if (head==null) return;
        Stack<Node> stack =new Stack<>();
        stack.push(head);//头先进去
        while(!stack.isEmpty()){
            head=stack.pop();
            System.out.print(head.value+" ");
            if (head.right!=null){
                stack.push(head.right);
            }
            if (head.left!=null){
                stack.push(head.left);
            }
        }
        System.out.println();
    }

    /**
     * 思路：把整棵二叉树按照左边界来分解。实际上就是模拟递归一直往左边走的过程，控制在第一次遇到一个结点的时候打印
     * 这种方法的唯一劣势是无法第三次来到一个结点，也就是这种方法没有办法实现后序遍历
     * @param head:以先序打印以head为头的二叉树
     */
    public static void preOrderUnRecur2(Node head){//浙大的实现
        System.out.print("preorder unrecur:");
        Stack<Node> stack =new Stack<>();
        while(!stack.isEmpty()||head!=null){
            while(head!=null){
                stack.push(head);//第一次来到一个结点
                System.out.print(head.value+" ");//第一次来到一个结点的时候就打印
                head= head.left;//沿着左边界分解一棵树
            }
            head= stack.pop();//第二次来到一个结点
            head=head.right;//往右边走
        }
        System.out.println();
    }

    /**
     * 把整棵二叉树按照左边界来分解。实际上就是模拟递归一直往左边走的过程，控制在第一次遇到一个结点的时候打印
     * @param head:以中序打印以head为头的二叉树
     */
    public static void inOrderUnRecur(Node head) {
        System.out.print("inorder unrecur:");
        Stack<Node> stack=new Stack<>();
        while(head!=null||!stack.isEmpty()){
            while(head!=null){
                stack.push(head);
                head= head.left;
            }
            head= stack.pop();
            System.out.print(head.value+" ");
            head=head.right;
        }
        System.out.println();
    }

    /**
     * @param head:利用先序遍历的根左右，我们可以把先序遍历改成根右左。然后逆序一下就变成了左右根
     */
    public static void posOrderUnRecur1(Node head){
        System.out.print("postorder unrecur:");
        Stack<Node> stack1=new Stack<>();
        Stack<Node> stack2=new Stack<>();
        while(head!=null||!stack1.isEmpty()){
            while(head!=null){
                stack1.push(head);
                stack2.push(head);
                head=head.right;
            }
            head=stack1.pop();
            head=head.left;
        }
        while(!stack2.isEmpty()){
            System.out.print(stack2.pop().value+" ");
        }
        System.out.println();
    }

    /**
     *
     * @param head 头节点
     */
    public static void posOrderUnRecur2(Node head) {
        if (head==null) return;
        System.out.print("postorder unrecur :");
        Stack<Node> stack=new Stack<>();
        stack.push(head);//这种在while外面就先进栈的需要先判空
        Node p=null;
        while(!stack.isEmpty()){//一直沿着最左边的通路往下走，最后一定会来到一个叶子结点，打印叶子结点并且让head指向这个叶子结点。
            p= stack.peek();//瞥一眼栈顶，通过下面的逻辑分支来判断是三种情况的哪一种
            if (p.left!=null&&p.left!=head&&p.right!=head){//说明左子树还没有处理
                stack.push(p.left);//这里是push进入栈中，而不是更新p=p.left
            }else if (p.right!=null&&p.right!=head){ //说明右子树没有处理
                stack.push(p.right);
            }else {//左右子树都处理完了，打印当前栈顶的结点。
                System.out.print(stack.pop().value+" ");
                head=p;//当第一次打印的时候，head才被赋予了它真正的含义
            }
        }
        System.out.println();
    }

    /**
     * 按照层序打印
     */
    private static void printByLay(Node head){
        if (head==null) return;
        System.out.print("by lay:");
        Queue<Node> queue=new LinkedList<>();
        queue.add(head);//要先进入结构，需要在之前判空
        while(!queue.isEmpty()){
            head=queue.poll();
            System.out.print(head.value+" ");
            if (head.left!=null) queue.add(head.left);
            if (head.right!=null) queue.add(head.right);
        }
        System.out.println();
    }



    public static void main(String[] args) {
        Node head = new Node(5);
        head.left = new Node(3);
        head.right = new Node(8);
        head.left.left = new Node(2);
        head.left.right = new Node(4);
        head.left.left.left = new Node(1);
        head.right.left = new Node(7);
        head.right.left.left = new Node(6);
        head.right.right = new Node(10);
        head.right.right.left = new Node(9);
        head.right.right.right = new Node(11);

        // recursive
        System.out.println("==============recursive==============");
        System.out.print("pre-order: ");
        preOrderRecur(head);
        System.out.println();
        System.out.print("in-order: ");
        inOrderRecur(head);
        System.out.println();
        System.out.print("pos-order: ");
        posOrderRecur(head);
        System.out.println();

        // unrecursive
        System.out.println("============unrecursive=============");
        preOrderUnRecur(head);
        preOrderUnRecur2(head);
        inOrderUnRecur(head);
        posOrderUnRecur1(head);
        posOrderUnRecur2(head);

        System.out.println("============print by lay=============");
        printByLay(head);



    }
}
