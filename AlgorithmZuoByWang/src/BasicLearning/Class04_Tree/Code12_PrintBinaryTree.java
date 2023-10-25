package BasicLearning.Class04_Tree;

public class Code12_PrintBinaryTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    /**
     *我们将树逆时针旋转90°，这样一来就变成了右根左的递归序。然后我们要根据高度打印前面的空格，为了保持美观，我们将输出的值固定为
     * len长度的String，这样避免结点的值大小差距悬殊的时候破坏的了树的打印结构
     * @param head:打印以head为头的树
     * @param height:根据高度来决定行前打印的空格数
     * @param to:父亲结点调用传下来的参数，调用右子树传递"v"，调用左子树传递"^"
     * @param len:除了前面的空格，后面的打印的部分我固定为长度为len长度的String
     */
    public static void printInOrder(Node head, int height, String to, int len) {
        if (head==null){
            return;
        }
        printInOrder(head.right, height+1, "v", len);

        String value=new String(to+head.value+to);
        int len1=(len-value.length())>>1;//左边的空格
        int len2=len-len1-value.length();//右边的空格
        String space1=getSpace(len1);//左边的空格
        String space2=getSpace(len2);//右边的空格
        String space3=getSpace(10*height);//和高度有关的空格
        System.out.println(space3+space1+value+space2);

        printInOrder(head.left, height+1, "^", len);

    }

    /**
     *
     * @param num:生成一个有num个空格的字符串
     * @return
     */
    public static String getSpace(int num) {
        String space=new String(" ");
        StringBuffer buf=new StringBuffer();
        for (int i=0;i<num;i++){
            buf.append(space);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(-222222222);
        head.right = new Node(3);
        head.left.left = new Node(Integer.MIN_VALUE);
        head.right.left = new Node(55555555);
        head.right.right = new Node(66);
        head.left.left.right = new Node(777);
        printTree(head);

        head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.right.left = new Node(5);
        head.right.right = new Node(6);
        head.left.left.right = new Node(7);
        printTree(head);

        head = new Node(1);
        head.left = new Node(1);
        head.right = new Node(1);
        head.left.left = new Node(1);
        head.right.left = new Node(1);
        head.right.right = new Node(1);
        head.left.left.right = new Node(1);
        printTree(head);

    }

}
