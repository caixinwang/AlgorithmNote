package BasicLearning.Class06_Graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Code09_ShortestPath {//单元最短路径问题
    static final int MAX=999999;

    static class Node {//邻接点
        public int idx;//结点下标,不重复
        public int weight;//邻接表中指向它的权重
        public Node next;//指向下一个邻接点

        public Node(int idx, int weight, Node next) {
            this.idx = idx;
            this.weight = weight;
            this.next = next;
        }

        public Node(int idx, int weight) {
            this.idx = idx;
            this.weight = weight;
        }
    }

    static class Edge{//边
        public int weight;
        public int from;
        public int to;

        public Edge(int weight, int from, int to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }

    }

    static class TNode{//邻接表
        public Node firstNode;
//        Data

        public TNode(Node firstNode) {
            this.firstNode = firstNode;
        }
    }
    static class Graph{
        public int nodeNum;
        public int edgeNum;
        public List<TNode> tNodeList;

        public Graph(int nodeNum) {
            this.nodeNum=nodeNum;
            edgeNum=0;
            tNodeList=new LinkedList<>();
            for (int i = 0; i < nodeNum+1; i++) {//从1开始记
                tNodeList.add(new TNode(null));
            }
        }

        public void insert(Edge edge){
            Node node=new Node(edge.to, edge.weight);
            node.next=tNodeList.get(edge.from).firstNode;
            tNodeList.get(edge.from).firstNode=node;

        }
    }

    public static int shortestPath(Graph graph){
        int[] cost=new int[graph.nodeNum+1];//cost[i]代表从s(1号结点)到i结点的的最小距离
        Arrays.fill(cost, MAX);
        cost[1]=0;
        int[] path=new int[graph.nodeNum+1];//从1开始记
        for (int i = 1; i <= graph.nodeNum; i++) {
            Node head=graph.tNodeList.get(i).firstNode;
            while(head!=null){
                if (cost[i]+head.weight<cost[head.idx]){
                    cost[head.idx]=cost[i]+head.weight;
                    path[head.idx]=i;
                }
                head= head.next;
            }
        }
        return cost[12];
    }


    public static void main(String[] args) {
        Graph graph=new Graph(12);
        graph.insert(new Edge(9,1,2));
        graph.insert(new Edge(7,1,3));
        graph.insert(new Edge(3,1,4));
        graph.insert(new Edge(2,1,5));

        graph.insert(new Edge(4,2,6));
        graph.insert(new Edge(2,2,7));
        graph.insert(new Edge(1,2,8));

        graph.insert(new Edge(2,3,6));
        graph.insert(new Edge(7,3,7));

        graph.insert(new Edge(11,4,8));

        graph.insert(new Edge(11,5,7));
        graph.insert(new Edge(8,5,8));

        graph.insert(new Edge(6,6,9));
        graph.insert(new Edge(5,6,10));

        graph.insert(new Edge(4,7,10));
        graph.insert(new Edge(3,7,9));

        graph.insert(new Edge(5,8,10));
        graph.insert(new Edge(6,8,11));

        graph.insert(new Edge(4,9,12));
        graph.insert(new Edge(2,10,12));
        graph.insert(new Edge(5,11,12));

        System.out.println(shortestPath(graph));

    }


}
