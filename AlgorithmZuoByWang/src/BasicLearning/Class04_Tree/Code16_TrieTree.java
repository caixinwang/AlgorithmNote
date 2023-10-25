package BasicLearning.Class04_Tree;

import java.util.HashMap;

public class Code16_TrieTree {

    public static class Node{
        public int pass;
        public int end;
        public Node[] next;//指路

        public Node(){
            pass=0;
            end=0;
            next=new Node[26];//假设我们只放26个字母的小写，那么我们就有26条路
        }
    }

    public static class Trie1 {
        public Node head;

        public Trie1(){
            head=new Node();
        }

        public void insert(String val){
            if (val==null) return;
            char[] arr=val.toCharArray();
            Node p=head;
            p.pass++;//头节点的pass++，代表有多少个字符串存在我这个结构里
            for (char a:arr){
                if (p.next[a-'a']==null){
                    p.next[a-'a']=new Node();
                }
                p=p.next[a-'a'];
                 p.pass++;
            }
            p.end++;
        }

        /**
         * 找val在前缀树中存入了几次。思路很简单，就是怎么加的就怎么找。
         */
        public int search(String val){
            if (val==null) return 0;
            char[] arr=val.toCharArray();
            Node p=head;
            for(char a:arr){
                if (p.next[a-'a']==null){
                    return 0;
                }
                p=p.next[a-'a'];
            }
            return p.end;
        }

        /**
         * @param val：
         * @return 返回以val作为前缀的字符串有多少
         */
        public int prefixNumber(String val){
            if (val==null)return 0;
            char[] arr=val.toCharArray();
            Node p=head;
            for (char a:arr){
                if (p.next[a-'a']==null){
                    return 0;
                }
                p=p.next[a-'a'];
            }
            return p.pass;
        }

        public void delete(String val){
            if (search(val)==0)return;
            char[] arr=val.toCharArray();
            Node p=head;
            p.pass--;
            for (char a:arr){
                if (--p.next[a-'a'].pass==0){
                    p.next[a-'a']=null;//后面还连着的结点会被GC回收
                    return;
                }
                p=p.next[a-'a'];
            }
            p.end--;
        }
    }

    public static class Node2 {
        public int pass;
        public int end;
        public HashMap<Integer, Node2> nexts;//26个大小的空间不够就用这个，Integer表示这条路的ASCII码值

        public Node2() {
            pass = 0;
            end = 0;
            nexts = new HashMap<>();
        }
    }

    public static class Trie2 {
        private Node2 root;

        public Trie2() {
            root = new Node2();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chs = word.toCharArray();
            Node2 node = root;
            node.pass++;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    node.nexts.put(index, new Node2());
                }
                node = node.nexts.get(index);
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                Node2 node = root;
                node.pass--;
                int index = 0;
                for (int i = 0; i < chs.length; i++) {
                    index = (int) chs[i];
                    if (--node.nexts.get(index).pass == 0) {
                        node.nexts.remove(index);
                        return;
                    }
                    node = node.nexts.get(index);
                }
                node.end--;
            }
        }

        // word这个单词之前加入过几次
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chs = word.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.end;
        }

        // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chs = pre.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.pass;
        }
    }

    public static class Right {

        private HashMap<String, Integer> box;

        public Right() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count += box.get(cur);
                }
            }
            return count;
        }
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                    right.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans2 = trie2.search(arr[j]);
                    int ans3 = right.search(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefixNumber(arr[j]);
                    int ans2 = trie2.prefixNumber(arr[j]);
                    int ans3 = right.prefixNumber(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("finish!");

    }

}
