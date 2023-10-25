package Leetcode.AllQuestions;
//import Leetcode.LeetClass.*;
import java.util.*;
public class Leetcode_0133_CloneGraph {
    class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
    Node[] nodes=new Node[101];
    public Node cloneGraph(Node node) {
        if(node==null) return null;
        return f(node);
    }

    public Node f(Node node){
        var ans=new Node(node.val,new ArrayList<>());
        nodes[node.val]=ans;
        if(node.neighbors!=null&&!node.neighbors.isEmpty()){
            for(var cur:node.neighbors){
                if(nodes[cur.val]!=null) ans.neighbors.add(nodes[cur.val]);
                else ans.neighbors.add(f(cur));
            }
        }
        return ans;
    }
}
