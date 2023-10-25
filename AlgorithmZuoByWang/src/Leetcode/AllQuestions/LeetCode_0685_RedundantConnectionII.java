package Leetcode.AllQuestions;

import java.util.Arrays;

public class LeetCode_0685_RedundantConnectionII {
    /**
     * 和684题不一样的地方在于这题是有向边，所以你从左到右找到的第一个导致入环的结点可能不是那个捣蛋的结点。
     * 要找到那个捣蛋的结点你只能找入度为2的结点，和这个结点的入度有关的两条边除掉，哪个去掉之后图没有环了，
     * 哪个就是捣蛋的结点。自己画一个二叉树的有向图，发现只有根节点的入度为0，其它结点入度都为1。
     * 1.如果添加的边指向了根节点，那么就没有入度为2的结点。这个时候用684题的方法从左到右找到第一个成环的结点即可
     * 2.如果添加的边指向了其它结点，那么就有入度为2的结点x，假设有边<a,x><b,x>，捣蛋的边只可能出现在它们中的一个。
     *    我们只需要去掉这两个边的其中一个，判断一下剩下的结点有没有环，如果没有环，说明捣蛋的边就是去掉的这个边。
     */
    static class UnionFind {
        int[] parents;//结点直接映射成数组了，元素为负是代表结点，绝对值是集合中元素的个数。正数为parent的对应的下标

        public UnionFind(int N) {
            parents = new int[N];
            Arrays.fill(parents, -1);//所有结点都是自己的代表结点
        }

        public int findFather(int node) {
            if (parents[node] < 0) return node;
            else return parents[node] = findFather(parents[node]);
        }

        public boolean union(int node1, int node2) {//node1->node2 ,不用考虑大小
            int a = findFather(node1);
            int b = findFather(node2);
            if (a == b) return false;
            parents[a] += parents[b];//更新集合里面元素的个数
            parents[b] = a;//小集合b挂到大集合a下面
            return true;
        }

    }

    public static int[] getFailNode(int[][] edges, int[] except) {//图去掉一条边，如果有环找到最右边的导致有环的结点
        UnionFind uf = new UnionFind(edges.length + 1);
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            if (edge == except) continue;
            if (!uf.union(edge[0], edge[1])) return edge;//切记是反过来到并查集里面union
        }
        return null;//没有环就返回null
    }

    public static int[] findRedundantDirectedConnection(int[][] edges) {
        int N = edges.length;
        int[] in = new int[N+1];
        for (int[] edge : edges) {
            in[edge[1]]++;
        }
        boolean count2 = false;
        int indexc2 = 0;
        for (int i = 0; i < in.length && !count2; i++) {
            if (in[i]==2){
                count2 = true;
                indexc2 = i;//indexc3这个结点有两次入度
            }
        }
        if (count2) {
            for (int i = N - 1; i >= 0; i--) {
                int[] edge = edges[i];
                if (edge[1] == indexc2){
                    int[] failNode = getFailNode(edges, edge);
                    if (failNode==null) return edge;//为空，说明去掉了这个edge之后，图就没有环了，说明edge就是捣蛋的结点
                }
            }
        } else {
            return getFailNode(edges,null);
        }
        return null;
    }

    public static void main(String[] args) {
        int[][] edges =new int[][]{
                {1,5},
                {3,2},
                {2,4},
                {4,5},
                {5,3},
        };
        for (int n:findRedundantDirectedConnection(edges)){
            System.out.println(n);
        }
    }
}
