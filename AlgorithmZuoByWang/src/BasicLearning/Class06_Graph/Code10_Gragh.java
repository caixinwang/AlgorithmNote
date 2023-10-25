package BasicLearning.Class06_Graph;

import java.util.LinkedList;

public class Code10_Gragh {//邻接表实现图

    static class Node {//邻接点
        public int idx;//结点下标,不重复
        public int weight;//邻接表中指向它的权重

        public Node(int idx, int weight) {
            this.idx = idx;
            this.weight = weight;
        }
    }

    static class Edge {//边
        public int weight;
        public int from;
        public int to;

        public Edge(int weight, int from, int to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }

    }

    static class Graph {
        public int nodeNum;
        public int edgeNum;
        public LinkedList<Node>[] tNodeList;

        public Graph(int nodeNum) {
            this.nodeNum = nodeNum;
            edgeNum = 0;
            tNodeList = new LinkedList[nodeNum+1];
            for (int i = 1; i <= nodeNum; i++) {//从1开始记
                tNodeList[i]=new LinkedList<>();//这个链表放的是i结点的邻居
            }
        }

        public void insert(Edge edge) {
            tNodeList[edge.from].add(new Node(edge.to, edge.weight));
        }
    }

}
