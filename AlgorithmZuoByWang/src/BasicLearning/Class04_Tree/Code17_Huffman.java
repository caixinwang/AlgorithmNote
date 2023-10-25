package BasicLearning.Class04_Tree;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Code17_Huffman {
    static class Node implements Comparable<Node>{
        private Character c;//代表的字符
        private int weight;//权重--即出现的频率

        private Node parent;
        private Node left;
        private Node right;

        public Node(Character c,int weight) {
            this.c = c;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node node) {
            return weight< node.weight?-1:1;
        }
    }

    /**
     *
     * @param chars 和weights数组配合使用，
     * @param weights weights[i]代表chars[i]字符出现的权重
     * @return 返回一个string[],i位置代表char[i]字符应该采用的编码
     */
    public static String[] huffmanCode(char[] chars,int[] weights){
        Node[] nodes=new Node[chars.length];
        for (int i = 0; i < chars.length; i++) {
            nodes[i]=new Node(chars[i],weights[i]);
        }
        return huffmanCode(nodes);
    }

    private static String[] huffmanCode(Node[] nodes) {
        PriorityQueue<Node> heap=new PriorityQueue<>();//利用小根堆来进行
        heap.addAll(Arrays.asList(nodes));
        String[] res=new String[nodes.length];
        for (int i = 0; i < nodes.length - 1; i++) {
            Node node1=heap.poll();
            Node node2=heap.poll();
            Node merge=new Node(null,node1.weight+ node2.weight);
            merge.left=node1;
            merge.right=node2;
            node1.parent=merge;
            node2.parent=merge;
            heap.add(merge);
        }
        Node head=heap.poll();
        for (int i = 0; i < res.length; i++) {//从叶子节点开始
            Node tmp=nodes[i];
            String s="";
            while(tmp!=head){
                s+=tmp.parent.left==tmp?0:1;
                tmp=tmp.parent;
            }
            res[i]=s;
        }
        for (int i = 0; i < res.length; i++) {//翻转一下次序，上面得到的编码顺序是翻转的
            String[] split = res[i].split("");
            res[i]="";
            for (int j = split.length-1; j>=0; j--) {
                res[i]+=split[j];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        char[] chars={'a','b','c','d','e','f'};
        int[] weights={45,13,12,16,9,5};
        String[] strings = huffmanCode(chars, weights);
        for (int i = 0; i < strings.length; i++) {
            System.out.println(strings[i]);
        }
    }

}
