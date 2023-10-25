package ClassicInterviewCoding.InterviewCoding19;

import java.util.HashMap;
import java.util.HashSet;

public class Code02_BricksFallingWhenHit1 {


	/**
	 * 你用什么来映射集合里面的元素，你就建立什么样的nodes。例如你用String来映射集合里面的Node，那么你就用一个
     * HashMap<String,Node>。这题使用(x,y)来映射Node，那么毫无疑问，直接用一个矩阵来映射，所以nodes是一个矩阵
     * 细节:无论何时何地，你new了一个Node，你就要去更新好nodes、size、count、cellingSet这几个结构
	 */
	static class UnionFind {

        public static class Node {
            public Node parent;
            public Node(){
                parent=this;
            }
        }

        private Node[][] nodes;//用(x,y)来映射一个Node,不同题目的nodes是什么结构要你自己去决定。
        private HashMap<Node, Integer> size;//只有代表结点才有size值，一个代表结点代表一个集合
        //count和cellingSet的更新依赖于size，所以要放在size结构更新前去更新count和cellingSet--你用谁，就把谁放在后面更新
        //插前 更删后 指的是size这样的初始结构要放在count、cellingSet额外结构的后面更新，因为格外结构需要用到初始结构
        private int count;//和题目有关的，连在天花板上的砖块的数量
        private HashSet<Node> cellingSet;//添加的时机为创建了一个行为0的Node。更新时机为两个结合union的时候

        /**
         * 把影响后的矩阵传进来，然后把所有为1的位置建立一个单独的集合。然后上下左右去连1。
         * 每次new一个Node的时候，如果对应的点的x为0，那么把这个点加到cellingSet里面
         * @param em effected matrix
         */
        public UnionFind(int[][] em) {
            count=0;
            cellingSet=new HashSet<>();
            nodes=new Node[em.length][em[0].length];
            size=new HashMap<>();
            int N=em.length,M=em[0].length;
            for (int i = 0; i < em.length; i++) {
                for (int j = 0; j < em[0].length; j++) {
                    if (em[i][j] == 1) {
                        nodes[i][j] = new Node();
                        size.put(nodes[i][j], 1);
                        if (i == 0) {
                            cellingSet.add(nodes[i][j]);
                            count++;
                        }
                    }
                }
            }
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (em[i][j] == 1) {
                        if (i-1>=0&&em[i-1][j]==1) union(i,j,i-1,j);
                        if (i+1<N&&em[i+1][j]==1) union(i,j,i+1,j);
                        if (j-1>=0&&em[i][j-1]==1) union(i,j,i,j-1);
                        if (j+1<M&&em[i][j+1]==1) union(i,j,i,j+1);
                    }
                }
            }
        }

        /**
         *
         * @param node 如果node不在nodes表里面，那么会返回自己
         * @return 返回node所在集合的代表结点
         */
        private Node findFather(Node node) {
            if (node==node.parent) return node;
            else return node.parent=findFather(node.parent);//打扁平
        }

        public void union(int x1, int y1,int x2,int y2){
            Node head1 = findFather(nodes[x1][y1]);
            Node head2 = findFather(nodes[x2][y2]);
            if (head1 == head2) {//确认不是一个集合
                return;
            }
            if (size.get(head1)<size.get(head2)){//让head1指针永远指向大的
                Node tmp=head1;
                head1=head2;
                head2=tmp;
            }
            head2.parent=head1;//小挂大
            if (cellingSet.contains(head1)^cellingSet.contains(head2)){//有且仅有其中一个不包含
                count+=!cellingSet.contains(head1)?size.get(head1):size.get(head2);//谁不包含在内就把谁加进去,这句在前
                cellingSet.add(head1);//head2如果在，冗余就冗余了，以后肯定用不到。你也可以把head2移除掉
                cellingSet.remove(head2);//head1才是代表结点，这里不移出head2以后也用不到了
            }
            size.put(head1,size.get(head1)+size.get(head2) );//更新大集合的size
            size.remove(head2);//小集合的size移除
        }

        public int finger(int[][] em,int i,int j){
            int pre=count;
            int after=count;
            int N=em.length,M=em[0].length;
            if (em[i][j]==2){
                em[i][j]=1;//为1就新建node
                nodes[i][j]=new Node();
                size.put(nodes[i][j], 1);
                if (i == 0) {//新建node就要看看这个node是不是在天花板上
                    cellingSet.add(nodes[i][j]);
                    count++;
                }
                if (i-1>=0&&em[i-1][j]==1) union(i,j,i-1,j);
                if (i+1<N&&em[i+1][j]==1) union(i,j,i+1,j);
                if (j-1>=0&&em[i][j-1]==1) union(i,j,i,j-1);
                if (j+1<M&&em[i][j+1]==1) union(i,j,i,j+1);
                after=count;
            }
            return after>pre?after-pre-1:0;
        }
    }

    public static int[] hitBricks(int[][] grid, int[][] hits) {
        // 把炮弹影响加上，grid会怎么变
        for (int i = 0; i < hits.length; i++) {
            if (grid[hits[i][0]][hits[i][1]] == 1) {
                grid[hits[i][0]][hits[i][1]] = 2;
            }
        }

        UnionFind unionFind = new UnionFind(grid);
        int[] ans = new int[hits.length];
        for (int i = hits.length - 1; i >= 0; i--) {
            ans[i]= unionFind.finger(grid,hits[i][0], hits[i][1]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] grid = { { 1, 0, 1 }, { 1, 1, 1 },{1,0,0} };
        int[][] hits = { { 0, 0 }, { 0, 2 }, { 1, 1 } ,{2,1}};
        int[] ans = hitBricks(grid, hits);
        for (int i = 0; i < ans.length; i++) {
            System.out.println(ans[i]);
        }
    }
}
