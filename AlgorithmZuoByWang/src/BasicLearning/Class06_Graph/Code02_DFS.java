package BasicLearning.Class06_Graph;

import java.util.HashSet;
import java.util.Stack;

public class Code02_DFS {

    public static void dfs(Node start){
        if (start == null) {
            return;
        }
        Stack<Node> stack=new Stack<>();
        HashSet<Node> set=new HashSet<>();
        stack.push(start);
        set.add(start);
        System.out.print(start.value+" ");//入栈就打印

        while (!stack.isEmpty()) {
            Node cur=stack.pop();
            for (Node node:cur.nexts){
                if (!set.contains(node)){
                    stack.push(cur);//cur压回去
                    stack.push(node);
                    set.add(node);
                    System.out.print(node.value+" ");//入栈就打印
                    break;//回到while循环从刚刚加进去的这个最深的结点node重新开始
                }
            }
        }
    }


}
