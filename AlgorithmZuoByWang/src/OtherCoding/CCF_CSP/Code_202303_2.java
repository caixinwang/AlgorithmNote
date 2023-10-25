package OtherCoding.CCF_CSP;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Code_202303_2 {

    static class MyComparator implements Comparator<Node>{//按照时间来排序，从大到小
        @Override
        public int compare(Node o1, Node o2) {
            return o2.t-o1.t;
        }
    }

    static class Node{
        public int t;//此时的耗时
        public int c;//消耗的资源

        public Node(int t, int c) {
            this.t = t;
            this.c = c;
        }
    }

    /**
     * 这题一开始用堆做，但是会超时
     * 所以我们就想到一次处理一批的方式。
     * @param T [i]代表第i块区域的开垦时间
     * @param C [i]代表第i块区域开垦时间缩短1天所需要多消耗的资源量
     * @param m 手上有m的资源
     * @param k 一块地最少需要的开垦时间不能低于k
     * @return 返回所需要的最短的天数
     */
    public static int solve(int[] T,int[] C,int m,int k) {
        if (T.length==1){
            if (T[0]>k&&m>=C[0]) return Math.max(k,T[0]-(m/C[0]));
        }
        Node[] nodes=new Node[T.length];
        for (int i = 0; i < T.length; i++) {
            nodes[i]=new Node(T[i],C[i]);
        }
        Arrays.sort(nodes,new MyComparator());
        int[] csum=new int[T.length];//csum[i]代表nodes[0~i] c 的累加和
        csum[0]=nodes[0].c;
        for (int i=1;i<C.length;i++) csum[i]=nodes[i].c+csum[i-1];
        //让0~i-1的全部都变为i处的天数
        int i=1;
        for (;i<C.length&&m>=csum[i-1]*(nodes[i-1].t-nodes[i].t);i++){
            m-=csum[i-1]*(nodes[i-1].t-nodes[i].t);
        }
        int minusDay=m/csum[i-1];//变不成i，那么就让0~i-1能减多少减多少
        return Math.max(k,nodes[i-1].t-minusDay);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            int m = in.nextInt();
            int k = in.nextInt();
            int[] T=new int[n];
            int[] C=new int[n];
            for (int i = 0; i < T.length; i++) {
                T[i]=in.nextInt();
                C[i]=in.nextInt();
            }
            System.out.println(solve(T,C,m,k));
        }
        in.close();
    }
}
