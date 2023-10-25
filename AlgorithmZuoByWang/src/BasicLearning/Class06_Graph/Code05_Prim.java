package BasicLearning.Class06_Graph;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Code05_Prim {

    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> Prim(Gragh gragh) {
        HashSet<Edge> res = new HashSet<>();
        PriorityQueue<Edge> heap = new PriorityQueue<>(new EdgeComparator());//存解锁的边
        HashSet<Node> set = new HashSet<>();//存解锁的点
        for (Node node : gragh.nodes.values()) {//for循环是为了防止森林，如果不是森林那么随便选一个就行

            if (!set.contains(node)) {
                set.add(node);
                for (Edge edge : node.edges) {
                    heap.add(edge);
                }
                while(!heap.isEmpty()){
                    Edge edge= heap.poll();
                    if (!set.contains(edge.to)){
                        set.add(edge.to);
                        res.add(edge);
                        for (Edge e:edge.to.edges){
                            heap.add(edge);
                        }
                    }
                }
            }
        }
        return res;
    }

}
