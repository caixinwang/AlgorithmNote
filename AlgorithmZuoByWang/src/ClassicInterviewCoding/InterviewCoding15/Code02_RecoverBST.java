package ClassicInterviewCoding.InterviewCoding15;

import java.util.Stack;

public class Code02_RecoverBST {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int data) {
            this.val = data;
        }
    }

    public static TreeNode[] getTwoErrNodes(TreeNode head) {
        TreeNode[] err=new TreeNode[2];
        Stack<TreeNode> stack=new Stack<>();
        TreeNode pre=null;
        while(head!=null||!stack.isEmpty()){
            while(head!=null){
                stack.push(head);
                head= head.left;
            }
            head = stack.pop();
            if (pre!=null&&pre.val>head.val){//降序了
                if (err[0]==null) err[0]=pre;//第一次降序的第一个结点
                err[1]=head;//抓住降序的最后一个结点
            }
            pre=head;
            head=head.right;
        }
        return err;
    }


    public static TreeNode[] getTwoErrParents(TreeNode head, TreeNode e1, TreeNode e2) {
        TreeNode[] parents = new TreeNode[2];
        if (head == null) {
            return parents;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                if (head.left == e1 || head.right == e1) {
                    parents[0] = head;
                }
                if (head.left == e2 || head.right == e2) {
                    parents[1] = head;
                }
                head = head.right;
            }
        }
        return parents;
    }

    /**
     * 交换其实基本的语句就是
     * 				e1.left = e2L;
     * 				e1.right = e2R;
     * 				e2.right = e1R;
     * 				e2.left = e1L;
     * 	但是需要做相应的修改
     * 	需要讨论e1和e2的位置关系。特殊关系：处于头节点、e1和e2相连
     * 	相连或者处于头节点都会少一句关于父亲结点的处理，如果相连和处于头节点都占了，那么就一句关于父亲的处理都没有了
     * 	相连的话，交换的时候原本作为孩子结点的就要把原本的父亲连上
     * 	由于是中序的关系，e1和e2的位置关系有些是不存在的。例如e1做头的话e2就只能在e1的右子树上，因为是中序依次找到e1和e2
     *  特殊情况先处理，先处理为头，再处理相连，最后处理不为头也不相连
     */
    public static TreeNode recoverTree(TreeNode head) {
        TreeNode[] errs = getTwoErrNodes(head);
        TreeNode[] parents = getTwoErrParents(head, errs[0], errs[1]);
        TreeNode e1 = errs[0];//错误的结点1
        TreeNode e1P = parents[0];//结点1的父节点
        TreeNode e1L = e1.left;//结点1的左孩子
        TreeNode e1R = e1.right;//结点1的右孩子
        TreeNode e2 = errs[1];
        TreeNode e2P = parents[1];
        TreeNode e2L = e2.left;
        TreeNode e2R = e2.right;
        e2.left = e1L;
        e2.right = e1R;
        e1.left = e2L;
        e1.right = e2R;
        if (e1 == head) {//为头
            if (e1 == e2P) {//修改过了e1和指针域，不能用e1.right==e2判断了
                e2.right = e1;
            } else if (e2P.left == e2) {
                e2P.left = e1;
            } else {
                e2P.right = e1;
            }
            head = e2;
        } else if (e2 == head) {//为头
            if (e2 == e1P) {
                e1.left = e2;
            } else if (e1P.left == e1) {
                e1P.left = e2;
            } else {
                e1P.right = e2;
            }
            head = e1;
        } else if (e1 == e2P) {//都不为头但相连
            e2.right = e1;
            if (e1P.left == e1) {
                e1P.left = e2;
            } else {
                e1P.right = e2;
            }
        } else if (e2 == e1P) {//都不为头但相连
            e1.left = e2;
            if (e2P.left == e2) {
                e2P.left = e1;
            } else {
                e2P.right = e1;
            }
        }else {//不为头且不相连
            if (e1P.left==e1) e1P.left=e2;
            else e1P.right=e2;
            if (e2P.left==e2) e2P.left=e1;
            else e2P.right=e1;
        }
        return head;
    }

    // for test -- print tree
    public static void printTree(TreeNode head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(TreeNode head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.val + to;
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

    // for test
    public static boolean isBST(TreeNode head) {
        if (head == null) {
            return false;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode pre = null;
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                if (pre != null && pre.val > head.val) {
                    return false;
                }
                pre = head;
                head = head.right;
            }
        }
        return true;
    }
    public static void testCase1(){
        System.out.println("situation 1");
        TreeNode head1 = new TreeNode(7);
        head1.left = new TreeNode(3);
        head1.right = new TreeNode(5);
        head1.left.left = new TreeNode(2);
        head1.left.right = new TreeNode(4);
        head1.right.left = new TreeNode(6);
        head1.right.right = new TreeNode(8);
        head1.left.left.left = new TreeNode(1);
        printTree(head1);
        System.out.println(isBST(head1));
        TreeNode res1 = recoverTree(head1);
        printTree(res1);
        System.out.println(isBST(res1));
        System.out.println("====================================");
    }
    public static void testCase2(){
        // ���2, 6 -> e1, 5 -> e2
        System.out.println("situation 2");
        TreeNode head2 = new TreeNode(6);
        head2.left = new TreeNode(3);
        head2.right = new TreeNode(7);
        head2.left.left = new TreeNode(2);
        head2.left.right = new TreeNode(4);
        head2.right.left = new TreeNode(5);
        head2.right.right = new TreeNode(8);
        head2.left.left.left = new TreeNode(1);
        printTree(head2);
        System.out.println(isBST(head2));
        TreeNode res2 = recoverTree(head2);
        printTree(res2);
        System.out.println(isBST(res2));
        System.out.println("====================================");
    }

    public static void testCase3(){
        // ���3, 8 -> e1, 5 -> e2
        System.out.println("situation 3");
        TreeNode head3 = new TreeNode(8);
        head3.left = new TreeNode(3);
        head3.right = new TreeNode(7);
        head3.left.left = new TreeNode(2);
        head3.left.right = new TreeNode(4);
        head3.right.left = new TreeNode(6);
        head3.right.right = new TreeNode(5);
        head3.left.left.left = new TreeNode(1);
        printTree(head3);
        System.out.println(isBST(head3));
        TreeNode res3 = recoverTree(head3);
        printTree(res3);
        System.out.println(isBST(res3));
        System.out.println("====================================");
    }
    public static void testCase4(){
        // ���4, 5 -> e1, 3 -> e2
        System.out.println("situation 4");
        TreeNode head4 = new TreeNode(3);
        head4.left = new TreeNode(5);
        head4.right = new TreeNode(7);
        head4.left.left = new TreeNode(2);
        head4.left.right = new TreeNode(4);
        head4.right.left = new TreeNode(6);
        head4.right.right = new TreeNode(8);
        head4.left.left.left = new TreeNode(1);
        printTree(head4);
        System.out.println(isBST(head4));
        TreeNode res4 = recoverTree(head4);
        printTree(res4);
        System.out.println(isBST(res4));
        System.out.println("====================================");
    }
    public static void testCase5(){

        // ���5, 5 -> e1, 2 -> e2
        System.out.println("situation 5");
        TreeNode head5 = new TreeNode(2);
        head5.left = new TreeNode(3);
        head5.right = new TreeNode(7);
        head5.left.left = new TreeNode(5);
        head5.left.right = new TreeNode(4);
        head5.right.left = new TreeNode(6);
        head5.right.right = new TreeNode(8);
        head5.left.left.left = new TreeNode(1);
        printTree(head5);
        System.out.println(isBST(head5));
        TreeNode res5 = recoverTree(head5);
        printTree(res5);
        System.out.println(isBST(res5));
        System.out.println("====================================");
    }
    public static void testCase6(){

        // ���6, 5 -> e1, 4 -> e2
        System.out.println("situation 6");
        TreeNode head6 = new TreeNode(4);
        head6.left = new TreeNode(3);
        head6.right = new TreeNode(7);
        head6.left.left = new TreeNode(2);
        head6.left.right = new TreeNode(5);
        head6.right.left = new TreeNode(6);
        head6.right.right = new TreeNode(8);
        head6.left.left.left = new TreeNode(1);
        printTree(head6);
        System.out.println(isBST(head6));
        TreeNode res6 = recoverTree(head6);
        printTree(res6);
        System.out.println(isBST(res6));
        System.out.println("====================================");
    }
    public static void testCase7(){

        // ���7, 4 -> e1, 3 -> e2
        System.out.println("situation 7");
        TreeNode head7 = new TreeNode(5);
        head7.left = new TreeNode(4);
        head7.right = new TreeNode(7);
        head7.left.left = new TreeNode(2);
        head7.left.right = new TreeNode(3);
        head7.right.left = new TreeNode(6);
        head7.right.right = new TreeNode(8);
        head7.left.left.left = new TreeNode(1);
        printTree(head7);
        System.out.println(isBST(head7));
        TreeNode res7 = recoverTree(head7);
        printTree(res7);
        System.out.println(isBST(res7));
        System.out.println("====================================");
    }
    public static void testCase8(){

        // ���8, 8 -> e1, 7 -> e2
        System.out.println("situation 8");
        TreeNode head8 = new TreeNode(5);
        head8.left = new TreeNode(3);
        head8.right = new TreeNode(8);
        head8.left.left = new TreeNode(2);
        head8.left.right = new TreeNode(4);
        head8.right.left = new TreeNode(6);
        head8.right.right = new TreeNode(7);
        head8.left.left.left = new TreeNode(1);
        printTree(head8);
        System.out.println(isBST(head8));
        TreeNode res8 = recoverTree(head8);
        printTree(res8);
        System.out.println(isBST(res8));
        System.out.println("====================================");
    }
    public static void testCase9(){
        // ���9, 3 -> e1, 2 -> e2
        System.out.println("situation 9");
        TreeNode head9 = new TreeNode(5);
        head9.left = new TreeNode(2);
        head9.right = new TreeNode(7);
        head9.left.left = new TreeNode(3);
        head9.left.right = new TreeNode(4);
        head9.right.left = new TreeNode(6);
        head9.right.right = new TreeNode(8);
        head9.left.left.left = new TreeNode(1);
        printTree(head9);
        System.out.println(isBST(head9));
        TreeNode res9 = recoverTree(head9);
        printTree(res9);
        System.out.println(isBST(res9));
        System.out.println("====================================");
    }
    public static void testCase10(){
        // ���10, 7 -> e1, 6 -> e2
        System.out.println("situation 10");
        TreeNode head10 = new TreeNode(5);
        head10.left = new TreeNode(3);
        head10.right = new TreeNode(6);
        head10.left.left = new TreeNode(2);
        head10.left.right = new TreeNode(4);
        head10.right.left = new TreeNode(7);
        head10.right.right = new TreeNode(8);
        head10.left.left.left = new TreeNode(1);
        printTree(head10);
        System.out.println(isBST(head10));
        TreeNode res10 = recoverTree(head10);
        printTree(res10);
        System.out.println(isBST(res10));
        System.out.println("====================================");
    }
    public static void testCase11(){
        // ���11, 6 -> e1, 2 -> e2
        System.out.println("situation 11");
        TreeNode head11 = new TreeNode(5);
        head11.left = new TreeNode(3);
        head11.right = new TreeNode(7);
        head11.left.left = new TreeNode(6);
        head11.left.right = new TreeNode(4);
        head11.right.left = new TreeNode(2);
        head11.right.right = new TreeNode(8);
        head11.left.left.left = new TreeNode(1);
        printTree(head11);
        System.out.println(isBST(head11));
        TreeNode res11 = recoverTree(head11);
        printTree(res11);
        System.out.println(isBST(res11));
        System.out.println("====================================");
    }
    public static void testCase12(){
        // ���12, 8 -> e1, 2 -> e2
        System.out.println("situation 12");
        TreeNode head12 = new TreeNode(5);
        head12.left = new TreeNode(3);
        head12.right = new TreeNode(7);
        head12.left.left = new TreeNode(8);
        head12.left.right = new TreeNode(4);
        head12.right.left = new TreeNode(6);
        head12.right.right = new TreeNode(2);
        head12.left.left.left = new TreeNode(1);
        printTree(head12);
        System.out.println(isBST(head12));
        TreeNode res12 = recoverTree(head12);
        printTree(res12);
        System.out.println(isBST(res12));
        System.out.println("====================================");
    }
    public static void testCase13(){
        // ���13, 6 -> e1, 4 -> e2
        System.out.println("situation 13");
        TreeNode head13 = new TreeNode(5);
        head13.left = new TreeNode(3);
        head13.right = new TreeNode(7);
        head13.left.left = new TreeNode(2);
        head13.left.right = new TreeNode(6);
        head13.right.left = new TreeNode(4);
        head13.right.right = new TreeNode(8);
        head13.left.left.left = new TreeNode(1);
        printTree(head13);
        System.out.println(isBST(head13));
        TreeNode res13 = recoverTree(head13);
        printTree(res13);
        System.out.println(isBST(res13));
        System.out.println("====================================");
    }
    public static void testCase14(){
        System.out.println("situation 14");
        TreeNode head14 = new TreeNode(5);
        head14.left = new TreeNode(3);
        head14.right = new TreeNode(7);
        head14.left.left = new TreeNode(2);
        head14.left.right = new TreeNode(8);
        head14.right.left = new TreeNode(6);
        head14.right.right = new TreeNode(4);
        head14.left.left.left = new TreeNode(1);
        printTree(head14);
        System.out.println(isBST(head14));
        TreeNode res14 = recoverTree(head14);
        printTree(res14);
        System.out.println(isBST(res14));
        System.out.println("====================================");
    }

    public static void main(String[] args) {
        TreeNode head = new TreeNode(5);
        head.left = new TreeNode(3);
        head.right = new TreeNode(7);
        head.left.left = new TreeNode(2);
        head.left.right = new TreeNode(4);
        head.right.left = new TreeNode(6);
        head.right.right = new TreeNode(8);
        head.left.left.left = new TreeNode(1);
        printTree(head);
        System.out.println(isBST(head));
        System.out.println("====================================");

        testCase1();
		testCase2();
		testCase3();
		testCase4();
		testCase5();
		testCase6();
		testCase7();
		testCase8();
		testCase9();
		testCase10();
		testCase11();
		testCase12();
		testCase13();
		testCase14();

    }

}
