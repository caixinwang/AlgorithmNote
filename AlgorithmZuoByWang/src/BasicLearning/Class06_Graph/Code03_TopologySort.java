package BasicLearning.Class06_Graph;

import java.util.*;

public class Code03_TopologySort {

    public static List<Node> sortedTopology(Gragh gragh){
        HashMap<Node,Integer> inMap=new HashMap<>();
        Queue<Node> zeroQueue=new LinkedList<>();
        for (Node node:gragh.nodes.values()){
            inMap.put(node,node.in);
            if (node.in==0){
                zeroQueue.add(node);
            }
        }
        List<Node> result=new ArrayList<>();
        while(!zeroQueue.isEmpty()){
            Node cur=zeroQueue.poll();
            result.add(cur);
            for (Node next:cur.nexts){
                inMap.put(next,inMap.get(next)-1);
                if (inMap.get(next)==0){
                    zeroQueue.add(next);
                }
            }
        }
        return result;

    }

}
