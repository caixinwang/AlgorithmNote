package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0127_WordLadder {//参考IC5
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        wordList.add(beginWord);
        HashMap<String, List<String>> neighbors = getNeighbors(wordList);
        Queue<String> queue=new LinkedList<>();
        HashSet<String> set=new HashSet<>();
        queue.add(beginWord);
        set.add(beginWord);//不走回头路
        int level=1;
        while (!queue.isEmpty()){
            int nums=queue.size();
            for (int i = 0; i < nums; i++) {
                String s=queue.poll();
                if (s.equals(endWord)) return level;
                if (!neighbors.containsKey(s)) return 0;
                List<String> list = neighbors.get(s);
                if (!list.isEmpty()){
                    for (String s1 : list) {
                        if (!set.contains(s1)){
                            queue.add(s1);
                            set.add(s1);
                        }
                    }
                }
            }
            level++;
        }
        return 0;
    }

    public static HashMap<String, List<String>> getNeighbors(List<String> wordList) {
        HashMap<String, List<String>> neighbors = new HashMap<>();
        HashSet<String> set = new HashSet<>(wordList);
        for (String word : wordList) {//搞定单词的所有邻居
            char[] w = word.toCharArray();
            neighbors.put(word, new ArrayList<>());
            for (int i = 0; i < w.length; i++) {//单词w的每一个位置都去变换26个字母，然后到set里面看看有没有一样的。
                for (char c = 'a'; c <= 'z'; c++) {
                    if (w[i] != c) {//别变成自己了
                        char t = w[i];
                        w[i] = c;
                        if (set.contains(String.valueOf(w))) {
                            neighbors.get(word).add(String.valueOf(w));
                        }
                        w[i] = t;
                    }
                }
            }
        }
        return neighbors;
    }


    public static int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        HashSet<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) {
            return 0;
        }
        HashSet<String> startSet = new HashSet<>();
        HashSet<String> endSet = new HashSet<>();
        HashSet<String> visit = new HashSet<>();
        startSet.add(beginWord);
        endSet.add(endWord);
        for (int len = 2; !startSet.isEmpty(); len++) {
            HashSet<String> nextSet = new HashSet<>();
            for (String w : startSet) {
                for (int j = 0; j < w.length(); j++) {
                    char[] ch = w.toCharArray();
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c != w.charAt(j)) {
                            ch[j] = c;
                            String next = String.valueOf(ch);
                            if (endSet.contains(next)) {
                                return len;
                            }
                            if (dict.contains(next) && !visit.contains(next)) {
                                nextSet.add(next);
                                visit.add(next);
                            }
                        }
                    }
                }
            }
            startSet = (nextSet.size() < endSet.size()) ? nextSet : endSet;
            endSet = (startSet == nextSet) ? endSet : nextSet;
        }
        return 0;
    }

    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("abc");
        words.add("abe");
        words.add("abd");
        words.add("acd");
        System.out.println(ladderLength("abe","acd",words));
    }


}
