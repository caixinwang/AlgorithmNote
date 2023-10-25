package Leetcode.AllQuestions;

public class Leetcode_0705_MyHashSet {
    class MyHashSet {
        boolean[] nodes = new boolean[1000009];

        public void add(int key) {
            nodes[key] = true;
        }

        public void remove(int key) {
            nodes[key] = false;
        }

        public boolean contains(int key) {
            return nodes[key];
        }
    }
}
