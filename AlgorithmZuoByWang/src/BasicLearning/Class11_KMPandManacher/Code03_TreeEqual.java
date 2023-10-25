package BasicLearning.Class11_KMPandManacher;

import java.util.ArrayList;
import java.util.Objects;

public class Code03_TreeEqual {//KMP应用

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    // big做头节点的树，其中是否有某棵子树的结构，是和small为头的树，完全一样的
    public static boolean containsTree1(Node big, Node small) {
        if (small == null) return true;//任何一棵树肯定包含空树
        if (big == null) return false;
        if (isSameValueStructure(big, small)) return true;
        else return containsTree1(big.left, small) || containsTree1(big.right, small);
    }

    // head1为头的树，是否在结构对应上，完全和head2一样.递归过程就是两棵树一直走到最底下的叶子结点，如果中间的值都匹配，才会
    // 走到叶子结点并且返回true。
    public static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 == null) return true;//两个都为空
        if (head1 == null || head2 == null) return false;//两个不都为空，但是有其中一个为空
        if (head1.value != head2.value) return false;
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }

    public static boolean containsTree2(Node big, Node small) {
        if (small == null) return true;//任何一棵树肯定包含空树
        if (big == null) return false;
        String[] b = preSerial(big);
        String[] s = preSerial(small);
        return getIndexOf(b, s) != -1;
    }

    public static String[] preSerial(Node head) {//空树也可以序列化
        ArrayList<String> list = new ArrayList<>();
        pres(head, list);
        String[] strings = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strings[i]=list.get(i);
        }
        return strings;
    }

    public static void pres(Node head, ArrayList<String> ans) {
        if (head == null) ans.add(null);
        else {
            ans.add(String.valueOf(head.value));
            pres(head.left, ans);
            pres(head.right, ans);
        }
    }

    //上层调用保证了str1和str2不为空，所以可以省去一些条件判断
    public static int getIndexOf(String[] str, String[] match) {
        if (str == null || match == null || str.length < 1 || match.length > str.length) return -1;
        int p1 = 0;//str的指针
        int p2 = 0;//match的指针
        int[] next = getNextArray(match);
        while (p1 < str.length && p2 < match.length) {//str的指针i不回退
            if (isEqual(str[p1], match[p2])) {
                p1++;
                p2++;
            } else if (p2 > 0) {//p2回退p1不动
                p2 = next[p2];
            } else {//此时p2等于0，回退不了，p1动
                p1++;
            }
        }
        return p2 == match.length ? p1 - p2 : -1;
    }

    public static int[] getNextArray(String[] match) {
        if (match.length == 1) return new int[]{-1};
        int[] next = new int[match.length];
        next[0] = -1;//第一个字符没有前一个字符，所以next没有意义。
        next[1] = 0;//第二个字符的前一个字符所在的字符串只有它自己，前缀和后缀相等，不符合规定，置为0.
        int p = next[1];//match的指针i对应的指标位置。
        int i=2;
        while (i < match.length) {//不能用for循环，因为i在一些情况下不会前进
            if (isEqual(match[p], match[i - 1])) next[i++] = ++p;
            else if (p>0) p = next[p];
            else next[i++] = 0;
        }
        return next;
    }

    public static boolean isEqual(String a, String b) {
//        if (a==b) return true;
//        if (a==null||b==null) return false;//不可能同时为空，一个为空一个不为空一定是false
//        return a.equals(b);
        return Objects.equals(a, b);//上面三句等价于这一句
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
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testTimes = 1000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node big = generateRandomBST(bigTreeLevel, nodeMaxValue);
            Node small = generateRandomBST(smallTreeLevel, nodeMaxValue);
            boolean ans1 = containsTree1(big, small);
            boolean ans2 = containsTree2(big, small);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }
}
