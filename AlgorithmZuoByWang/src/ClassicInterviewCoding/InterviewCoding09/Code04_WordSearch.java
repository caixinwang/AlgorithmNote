package ClassicInterviewCoding.InterviewCoding09;

import TestUtils.ArrayUtil;
import TestUtils.StringUtil;

import java.util.*;

public class Code04_WordSearch {
    public static class TrieNode{
        public TrieNode[] nexts;
        public int pass;
        public int end;

        public TrieNode() {
            nexts=new TrieNode[26];//只有小写字母
        }
    }

    public static void addWord(TrieNode head,String word){
        head.pass++;
        char[] str = word.toCharArray();
        int road=0;
        for (int i = 0; i < str.length; i++) {
            road=str[i]-'a';
            if (head.nexts[road]==null) head.nexts[road]=new TrieNode();
            head=head.nexts[road];
            head.pass++;
        }
        head.end++;
    }


    /**
     * 这题用一个前缀树来帮助我们每一步递归的决策，但是我们不需要一个前缀树结构，我们只需要一个结点跟着走即可
     * 因为前缀树结构不好跟着递归深入，而结点可以。
     * @param board 字符二维数组
     * @param words 单词
     * @return 返回字符二维数组可以凑出多少单词
     */
    public static List<String> findWords(char[][] board, String[] words){
        if (board==null||board.length==0||board[0]==null||board[0].length==0||words==null||words.length==0)
            return null;
        HashSet<String> set=new HashSet<>(List.of(words));//把words去重之后加到前缀树里面
        TrieNode head=new TrieNode();
        for (String s : set) {
            addWord(head,s);
        }
        List<String> res=new LinkedList<>();
        LinkedList<Character> path=new LinkedList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                process(board,i,j,head,path,res);//收集board中每一个位置的答案
            }
        }
        return res;

    }

    private static String pathToString (LinkedList<Character> path){
        StringBuilder res= new StringBuilder();
        for (Character character : path) {
            res.append(character);
        }
        return res.toString();
    }

    private static int process(char[][] board, int i, int j,
                               TrieNode node,
                               LinkedList<Character> path,
                               List<String> res) {
        char c=board[i][j];
        if (c==0) return 0;//之前已经来过这个位置了,不走回头路
        int road=c-'a';
        if (node.nexts[road]==null||node.nexts[road].pass==0) return 0;//两个剪枝条件,node往下包括自己已经收集答案
        node=node.nexts[road];//往下走说明board[i][j]这个位置我要了
        int fix=0;//上面剪枝都通过了，说明下面要进递归了，先看看自己这个结点是不是答案先收集了，然后收集递归的答案
        path.addLast(c);//说明当前这个c我要了,进递归
        if (node.end==1){//所有字符加前缀树的时候我们已经去重，end最大只能是1
            res.add(pathToString(path));
            node.end--;
            fix++;
        }
        board[i][j]=0;
        fix+=i-1>=0?process(board,i-1,j,node, path,res):0;
        fix+=i+1<board.length?process(board,i+1,j,node, path,res):0;
        fix+=j-1>=0?process(board,i,j-1,node, path,res):0;
        fix+=j+1<board[0].length?process(board,i,j+1,node, path,res):0;
        node.pass-=fix;//记得把收集到的答案在node减掉
        board[i][j]=c;//恢复现场
        path.pollLast();//恢复现场
        return fix;
    }

    //=============左============

    public static void fillWord(TrieNode head, String word) {
        head.pass++;
        char[] chs = word.toCharArray();
        int index = 0;
        TrieNode node = head;
        for (int i = 0; i < chs.length; i++) {
            index = chs[i] - 'a';
            if (node.nexts[index] == null) {
                node.nexts[index] = new TrieNode();
            }
            node = node.nexts[index];
            node.pass++;
        }
        node.end++;
    }

    public static String generatePath(LinkedList<Character> path) {
        char[] str = new char[path.size()];
        int index = 0;
        for (Character cha : path) {
            str[index++] = cha;
        }
        return String.valueOf(str);
    }

    public static List<String> findWords2(char[][] board, String[] words) {
        if (board==null||board.length==0||board[0]==null||board[0].length==0||words==null||words.length==0)
            return null;
        TrieNode head = new TrieNode(); // 前缀树最顶端的头
        HashSet<String> set = new HashSet<>();
        for (String word : words) {
            if (!set.contains(word)) {
                fillWord(head, word);
                set.add(word);
            }
        }
        // 答案
        List<String> ans = new ArrayList<>();
        // 沿途走过的字符，收集起来，存在path里
        LinkedList<Character> path = new LinkedList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                // 枚举在board中的所有位置
                // 每一个位置出发的情况下，答案都收集
                process2(board, row, col, path, head, ans);
            }
        }
        return ans;
    }

    // 从board[row][col]位置的字符出发，
    // 之前的路径上，走过的字符，记录在path里
    // cur还没有登上，有待检查能不能登上去的前缀树的节点
    // 如果找到words中的某个str，就记录在 res里
    // 返回值，从row,col 出发，一共找到了多少个str
    public static int process2(
            char[][] board,
            int row, int col,
            LinkedList<Character> path,
            TrieNode cur,
            List<String> res) {
        char cha = board[row][col];
        if (cha == 0) { // 这个row col位置是之前走过的位置
            return 0;
        }
        // (row,col) 不是回头路   cha 有效

        int index = cha - 'a';
        // 如果没路，或者这条路上最终的字符串之前加入过结果里
        if (cur.nexts[index] == null || cur.nexts[index].pass == 0) {
            return 0;
        }
        // 没有走回头路且能登上去
        cur = cur.nexts[index];
        path.addLast(cha);// 当前位置的字符加到路径里去
        int fix = 0; // 从row和col位置出发，后续一共搞定了多少答案
        // 当我来到row col位置，如果决定不往后走了。是不是已经搞定了某个字符串了
        if (cur.end > 0) {
            res.add(generatePath(path));
            cur.end--;
            fix++;
        }
        // 往上、下、左、右，四个方向尝试
        board[row][col] = 0;
        if (row > 0) {
            fix += process2(board, row - 1, col, path, cur, res);
        }
        if (row < board.length - 1) {
            fix += process2(board, row + 1, col, path, cur, res);
        }
        if (col > 0) {
            fix += process2(board, row, col - 1, path, cur, res);
        }
        if (col < board[0].length - 1) {
            fix += process2(board, row, col + 1, path, cur, res);
        }
        board[row][col] = cha;
        path.pollLast();
        cur.pass -= fix;
        return fix;
    }

    public static boolean compare(List<String> list1,List<String> list2){
        if (list1==null&&list2==null) return true;
        if (list1==null||list2==null||list1.size()!=list2.size()) return false;
        if (list1.size()==0&&list2.size()==0) return true;
        HashSet<String> set=new HashSet<>(list1);
        for (String s : list2) {
            if (!set.contains(s)) return false;
        }
        return true;
    }

    public static void testForCharMatrix() {//参数为char[][]
        ArrayUtil arrayUtil = new ArrayUtil();
        StringUtil stringUtil=new StringUtil();
        int times = 10;//测试次数
        boolean isok = true;
        int maxN = 30;//matrix大小在[0~maxN][0~maxM]随机
        int maxM = 30;//matrix大小在[0~maxN][0~maxM]随机
        int maxSize=10;
        int strSize=5;
//        int parameter1=0;//测试函数的参数
//        int maxParameter1=1000;//参数1在[0,maxParameter1]随机
        String[] parameter2=null;
        char[][] t1 = null;
//        char[][] t2 = null;
        List<String> res1 = null, res2 = null;
        for (int i = 0; i < times; i++) {
//            parameter1=arrayUtil.ran(maxParameter1);
            parameter2=stringUtil.generateRandomStringArr(arrayUtil.ran(maxSize),strSize);

            t1 = arrayUtil.generateRandomChar_a_z_Matrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM));
//            t2 = arrayUtil.generateRandomChar_a_z_Matrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM));

//            t1 = arrayUtil.generateRandomChar_all_Matrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM));
//            t2 = arrayUtil.generateRandomChar_all_Matrix(arrayUtil.ran(maxN), arrayUtil.ran(maxM));

            res1 = findWords(t1,parameter2);
            res2 = findWords2(t1,parameter2);
            if (!compare(res1,res2)) {
                isok = false;
                break;
            }
        }
        arrayUtil.printMatrix(t1);//打印参数
//        System.out.println("parameter:"+parameter1);//打印参数
//        System.out.println(res1);//针对返回值的操作
//        System.out.println(res2);//针对返回值的操作
        System.out.print("res1:");
        stringUtil.printStrList(res1);
        System.out.println(res1==null?"res1==null":"no null");
        System.out.println();
        System.out.print("res2:");
        stringUtil.printStrList(res2);
        System.out.println(res2==null?"res2==null":"no null");
        System.out.println();
        System.out.print("words:");
        stringUtil.printStrArr(parameter2);
        System.out.println();
        System.out.println(isok ? "success" : "fail");
    }

    public static void main(String[] args) {
        testForCharMatrix();
    }
}
