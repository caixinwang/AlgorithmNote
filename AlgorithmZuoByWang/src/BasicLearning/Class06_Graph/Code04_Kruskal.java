package BasicLearning.Class06_Graph;

import java.util.*;

public class Code04_Kruskal {

    public static class UnionFind{

        HashMap<Node,Node> parents=new HashMap<>();//向上找到父亲
        HashMap<Node,Integer> sizeMap=new HashMap<>();//只有是一个集合的代表结点才在这个表里

        public UnionFind() {
            parents=new HashMap<>();
            sizeMap=new HashMap<>();
        }

        public void makeSets(Collection<Node> collection){
            parents.clear();
            sizeMap.clear();//先清空老的并查集
            for (Node cur:collection){
                parents.put(cur,cur);
                sizeMap.put(cur,1);
            }
        }

        public Node findFather(Node node){
            Stack<Node> stack=new Stack<>();
            while(node!=parents.get(node)){//出while的时候node就是代表结点
                stack.push(node);
                node=parents.get(node);
            }
            while(!stack.isEmpty()){
                parents.put(stack.pop(),node);
            }
            return node;
        }

        public boolean isSameSet(Node node1,Node node2){
            return findFather(node1)==findFather(node2);
        }

        public void union(Node node1,Node node2){
            Node head1=findFather(node1);
            Node head2=findFather(node2);
            if (head1!=head2){
                int size1=sizeMap.get(head1);
                int size2=sizeMap.get(head2);
                Node big=size1>=size2?head1:head2;
                Node small=big==head1?head2:head1;
                parents.put(small,big);
                sizeMap.remove(small);
                sizeMap.put(big,size1+size2);
            }
        }
    }

    public static class EdgeComparator implements Comparator<Edge>{//从小到大的比较器
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight-o2.weight;
        }
    }

    public static Set<Edge> Kruskal(Gragh gragh){
        UnionFind unionFind=new UnionFind();
        unionFind.makeSets(gragh.nodes.values());//传来一个容器，初始化并查集
        PriorityQueue<Edge> heap=new PriorityQueue<>(new EdgeComparator());
        HashSet<Edge> res=new HashSet<>();
        for (Edge edge:gragh.edges){
            heap.add(edge);
        }
        while (!heap.isEmpty()) {
            Edge edge= heap.poll();
            if (!unionFind.isSameSet(edge.from,edge.to)){
                unionFind.union(edge.from, edge.to);
                res.add(edge);
            }
        }
        return res;
    }
}
