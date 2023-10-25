package Leetcode.AllQuestions;

import java.util.TreeSet;

public class Leetcode_0363_MaxSumSubmatrix {
    static final int MIN=1<<31,MAX=MIN-1;
    public static int maxSumSubmatrix(int[][] matrix, int k) {
        int n=matrix.length,m=matrix[0].length,ans=MIN;
        for(int r1=0;r1<n;r1++){
            int[] arr=new int[m];
            for(int r2=r1;r2<n;r2++){
                for(int i=0;i<m;i++) arr[i]+=matrix[r2][i];
                ans=max(ans,f(arr,k));
            }
        }
        return ans;
    }

    public static int f(int[] arr,int k){// 子数组<=k最大
        TreeSet<Integer> set=new TreeSet<>();
        set.add(0);
        int ans=MIN,sum=0;
        for(int num:arr){
            sum+=num;
            Integer find=set.ceiling(sum-k);
            if(find!=null){
                ans=max(ans,sum-find);
            }
            set.add(sum);
        }
        return ans;
    }

    public static int max(int a,int b){return a>b?a:b;}

    public static void main(String[] args) {
        System.out.println(MIN==Integer.MIN_VALUE);
        System.out.println(MAX==Integer.MAX_VALUE);
    }
}
