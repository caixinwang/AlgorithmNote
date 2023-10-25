package Leetcode.AllQuestions;

import java.util.HashSet;
import java.util.Set;

public class Leetcode_2718_SumOfMatrixAfterQueries {

    //思路：这是一个离线处理，所谓离线，就是它一次性给了你所有的query。并且我们确定了后面的操作会导致前面的操作失效
    //所以我们从后面往前走。那么我们需要用记录每行每列有没有被操作过。为什么使用Set来记录呢？因为我们需要知道已经被
    //操作过的元素的个数，使用Set可以很方便的得到。如果使用数组那么就需要使用两个变量来记录，使用数组也是可以的。
    public long matrixSumQueries(int n, int[][] queries) {
        Set<Integer> rset=new HashSet<>();
        Set<Integer> cset=new HashSet<>();
        int N=queries.length;
        long ans=0;
        for(int i=N-1;i>=0;i--){
            if (rset.size()+cset.size()==n<<1) break;//常数优化
            int[] query=queries[i];
            if(query[0]==0&&!rset.contains(query[1])){
                rset.add(query[1]);
                ans+=query[2]*(n-cset.size());
            }
            if(query[0]==1&&!cset.contains(query[1])){
                cset.add(query[1]);
                ans+=query[2]*(n-rset.size());
            }
        }
        return ans;
    }

    public long matrixSumQueries2(int n, int[][] queries) {//使用数组更快
        boolean[] rset=new boolean[n];
        boolean[] cset=new boolean[n];
        int rsz=0,csz=0;//分别代表有多少行、列已经标记为true了
        int N=queries.length;
        long ans=0;
        for(int i=N-1;i>=0;i--){
            if (rsz+csz==n<<1) break;//常数优化
            int[] query=queries[i];
            if(query[0]==0&&!rset[query[1]]){
                rset[query[1]]=true;
                rsz++;
                ans+=query[2]*(n-csz);
            }
            if(query[0]==1&&!cset[query[1]]){
                cset[query[1]]=true;
                csz++;
                ans+=query[2]*(n-rsz);
            }
        }
        return ans;
    }

    public long matrixSumQueries3(int n, int[][] queries) {//coding合并逻辑
        boolean[][] sets=new boolean[2][n];
        int[] szs=new int[2];//分别代表有多少行、列已经标记为true了
        int N=queries.length;
        long ans=0;
        for(int i=N-1;i>=0;i--){
            int[] query=queries[i];
            if (!sets[query[0]][query[1]]){
                sets[query[0]][query[1]]=true;
                szs[query[0]]++;
                ans+=query[2]*(n-szs[query[0]^1]);
            }
        }
        return ans;
    }
}
