package Leetcode.AllQuestions;

import java.util.LinkedList;
import java.util.List;

public class Leetcode_0139_WordBreak {
    /**
     * 使用前缀树来加速
     *
     * @param s        单词
     * @param wordDict 词典
     * @return 返回s能不能用词典分解
     */
    public static boolean wordBreak(String s, List<String> wordDict) {
        Node head = new Node();
        for (String word : wordDict) {
            Node cur = head;
            for (char c : word.toCharArray()) {
                if (cur.nodes[c - 'a'] == null) cur.nodes[c - 'a'] = new Node();
                cur = cur.nodes[c - 'a'];
            }
            cur.end = true;
        }
        return f(s.toCharArray(), 0, head);
    }

    private static boolean f(char[] str, int index, Node head) {
        if (index == str.length) return true;
        Node cur = head;
        //有路就一直往下走，没路就退出。如果是某一个单词的结尾就进递归
        for (int i = index; i < str.length; i++) {
            int road = str[i] - 'a';
            cur = cur.nodes[road];
            if(cur==null) break;
            if (cur.end && f(str, i + 1, head)) return true;
        }
        return false;
    }

    static class Node {
        public boolean end;
        public Node[] nodes;

        public Node() {
            nodes = new Node[26];
            end = false;
        }
    }

    public static boolean wordBreak2(String s, List<String> wordDict) {//暴力递归改动态规划
        Node head = new Node();
        for (String word : wordDict) {
            Node cur = head;
            for (char c : word.toCharArray()) {
                if (cur.nodes[c - 'a'] == null) cur.nodes[c - 'a'] = new Node();
                cur = cur.nodes[c - 'a'];
            }
            cur.end = true;
        }
        char[] str = s.toCharArray();
        boolean[] dp = new boolean[str.length + 1];
        dp[str.length] = true;
        for (int index = str.length - 1; index >= 0; index--) {
            Node cur = head;
            //有路就一直往下走，没路就退出。如果是某一个单词的结尾就进递归
            for (int i = index;i < str.length;  i++) {
                int road = str[i] - 'a';
                cur = cur.nodes[road];
                if(cur==null) break;
                if (cur.end && dp[i+1]) {
                    dp[index] = true;
                }
            }
        }
        return dp[0];
    }

    public static int wordBreak3(String s, List<String> wordDict) {//返回方法数
        Node head = new Node();
        for (String word : wordDict) {
            Node cur = head;
            for (char c : word.toCharArray()) {
                if (cur.nodes[c - 'a'] == null) cur.nodes[c - 'a'] = new Node();
                cur = cur.nodes[c - 'a'];
            }
            cur.end = true;
        }
        char[] str = s.toCharArray();
        int[] dp = new int[str.length + 1];
        dp[str.length] = 1;//收集1
        for (int index = str.length - 1; index >= 0; index--) {
            Node cur = head;
            for (int i = index; i < str.length; i++) {
                int road = str[i] - 'a';
                if (cur.nodes[road] == null) break;
                cur = cur.nodes[road];
                if (cur.end) {
                    dp[index] += dp[i + 1];
                }
            }
        }
        return dp[0];
    }


    public static void main(String[] args) {
        List<String> wordDict = new LinkedList<>();
        wordDict.add("leet");
        wordDict.add("code");
        boolean leetcode = wordBreak("leetcode", wordDict);
    }

}
