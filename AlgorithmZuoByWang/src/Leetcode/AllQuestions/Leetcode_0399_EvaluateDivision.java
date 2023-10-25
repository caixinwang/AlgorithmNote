package Leetcode.AllQuestions;

import java.util.HashMap;
import java.util.List;

public class Leetcode_0399_EvaluateDivision {

    class UnionFind {
        class Node {
            Node parent;
            double weight;//边如果有含义，就加在Node里面，代表parent这条有向边

            public Node() {
                weight = 1;//每个结点初始都是自己的代表结点，代表结点到自己的weight是1,a/a=1,合理！
                parent = this;
            }
        }

        HashMap<String, Node> nodes;//本题是String到Node的映射

        public UnionFind(List<List<String>> equations, double[] values){
            nodes=new HashMap<>();
            int index=0;
            for (List<String> equation:equations){//equation[0]/equation[1]=values[index]
                String a=equation.get(0),b=equation.get(1);
                if (!nodes.containsKey(a))nodes.put(a,new Node());
                if (!nodes.containsKey(b))nodes.put(b,new Node());//String -> Node
                union(a,b,values[index++]);
            }
        }

        public Node findFather(Node node) {
            if (node.parent == node) return node;
            Node f = findFather(node.parent);
            node.weight *= node.parent.weight;//找到父亲之后不着急连，先把weight更新对。代表结点的weight是1，一路往下更新对
            return node.parent = f;
        }

        //这题的union谁挂谁是有讲究的，a的代表结点挂到b的代表结点下。
        //找到f1和f2的过程中，node1和node2都直接连在了代表结点上。a=val*b 我们要算出f1=?f2
        //a=a.w*f1  b=b.w*f2 ==> a.w*f1=val*b.w*f2 => f1/f2=val*b.w/a.w
        public void union(String a, String b, double val) {
            Node node1 = nodes.get(a);
            Node node2 = nodes.get(b);
            Node f1 = findFather(node1);//f1挂到f2的下面，并且f1到f2的权重是weight
            Node f2 = findFather(node2);
            if (f1 == f2) return;
            f1.parent = f2;
            f1.weight = val * node2.weight / node1.weight;

        }

        public double get(String a, String b) {//返回a/b的答案
            if (!nodes.containsKey(a)||!nodes.containsKey(b)) return -1;
            Node node1 = nodes.get(a);
            Node node2 = nodes.get(b);
            Node f1 = findFather(node1);
            Node f2 = findFather(node2);
            if (f1!=f2) return -1;
            return node1.weight/node2.weight;//在找的过程中，node1和node2都挂在了同一个父亲底下，weight自然更新，直接除即可
        }

    }

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        UnionFind unionFind=new UnionFind(equations,values);
        double[] ans=new double[queries.size()];
        int index=0;
        for (List<String> query:queries){
            String a=query.get(0);
            String b=query.get(1);
            ans[index++]= unionFind.get(a,b);
        }
        return ans;
    }

}
