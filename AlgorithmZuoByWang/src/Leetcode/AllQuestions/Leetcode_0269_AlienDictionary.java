package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0269_AlienDictionary {//拓扑排序

    public static String alienOrder(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }
        int N = words.length;
        HashMap<Character, Integer> indegree = new HashMap<>();
        for (int i = 0; i < N; i++) {
            for (char c : words[i].toCharArray()) {
                indegree.put(c, 0);
            }
        }
        HashMap<Character, HashSet<Character>> graph = new HashMap<>();
        for (int i = 0; i < N - 1; i++) {
            char[] cur = words[i].toCharArray();
            char[] nex = words[i + 1].toCharArray();
            int len = Math.min(cur.length, nex.length);
            int j = 0;
            for (; j < len; j++) {
                if (cur[j] != nex[j]) {
                    if (!graph.containsKey(cur[j])) {
                        graph.put(cur[j], new HashSet<>());
                    }
                    if (!graph.get(cur[j]).contains(nex[j])) {
                        graph.get(cur[j]).add(nex[j]);
                        indegree.put(nex[j], indegree.get(nex[j]) + 1);
                    }
                    break;
                }
            }
            if (j < cur.length && j == nex.length) {
                return "";
            }
        }
        StringBuilder ans = new StringBuilder();
        Queue<Character> q = new LinkedList<>();
        for (Character key : indegree.keySet()) {
            if (indegree.get(key) == 0) {
                q.offer(key);
            }
        }
        while (!q.isEmpty()) {
            char cur = q.poll();
            ans.append(cur);
            if (graph.containsKey(cur)) {
                for (char next : graph.get(cur)) {
                    indegree.put(next, indegree.get(next) - 1);
                    if (indegree.get(next) == 0) {
                        q.offer(next);
                    }
                }
            }
        }
        return ans.length() == indegree.size() ? ans.toString() : "";
    }

    public static String alienOrder2(String[] words) {
        HashMap<Character, Set<Character>> nodes = new HashMap<>();
        int[] in = new int[26];//图中存在的结点，in对应的值不是-1.
        Arrays.fill(in, -1);
        for (String s : words) for (char c : s.toCharArray()) in[c - 'a'] = 0;//表示存在这个结点
        for (int i = 1; i < words.length; i++) {
            char[] edge = getEdge(words[i - 1], words[i]);
            if (edge == null) continue;
            if (edge[0] == edge[1]) return "";//abc < ab 这种情况直接没有答案
            char from = edge[0], to = edge[1];
            if (!nodes.containsKey(from)) nodes.put(from, new HashSet<>());
            if (!nodes.containsKey(to)) nodes.put(to, new HashSet<>());
            if (nodes.get(from).contains(to)) continue;//已经有这条边了
            in[to - 'a']++;//加入度
            nodes.get(from).add(to);
        }
        StringBuilder sb = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        for (int i = 0; i < in.length; i++)
            if (in[i] == 0) {//所有入度为0的点入队，加答案
                queue.add((char) (i + 'a'));
                sb.append((char) (i + 'a'));
            }
        while (!queue.isEmpty()) {
            char cur = queue.poll();
            if (!nodes.containsKey(cur)) continue;//这个结点没边
            for (char next : nodes.get(cur))
                if (--in[next - 'a'] == 0) {//消除影响，消到0了加答案
                    sb.append(next);
                    queue.add(next);
                }
        }
        for (int i : in) if (i != -1 && i != 0) return "";//存在环
        return sb.toString();
    }

    //根据a和b得到一条字符到字符的边。例如 1. "abc" "aba" -> 'c' -> 'a'   2. "abe" "abe" -> null  3."abc" "abcd" -> null
    public static char[] getEdge(String a, String b) {//
        char[] str1 = a.toCharArray(), str2 = b.toCharArray();
        int n = str1.length, m = str2.length, i = -1, j = -1;
        while (++i < n && ++j < m && str1[i] == str2[j]) ;
        if (i != n && j == m) return new char[]{'a', 'a'};//表示直接没有答案
        if (i == n || j == m) return null;
        return new char[]{str1[i], str2[j]};
    }

    //["wrt","wrf","er","ett","rftt"]
    //["z","x"]
    //["z","x","z"]
    //["ac","ab","zc","zb"]
    public static void main(String[] args) {
        String[] words = new String[]{"ac", "ab", "zc", "zb"};
        System.out.println(alienOrder(words));
        System.out.println(alienOrder2(words));

    }


}
