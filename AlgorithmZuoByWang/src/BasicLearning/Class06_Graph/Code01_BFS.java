package BasicLearning.Class06_Graph;

import java.util.HashSet;
import java.util.LinkedList;

public class Code01_BFS {

    public static void bfs(Node node){
        if (node==null){
            return;
        }
        LinkedList<Node> queue=new LinkedList<>();//实现层序遍历的功能
        HashSet<Node> set=new HashSet<>();//保证结点不重复入队
        queue.add(node);
        set.add(node);//入队和入集合一定是绑定在一起发生
        while(!queue.isEmpty()){
            node=queue.poll();
            System.out.print(node.value+" ");
            for (Node p:node.nexts){//让node周围一圈没有入过队的结点入队
                if (!set.contains(p)){
                    queue.add(p);
                    set.add(p);
                }
            }
        }
    }





}
