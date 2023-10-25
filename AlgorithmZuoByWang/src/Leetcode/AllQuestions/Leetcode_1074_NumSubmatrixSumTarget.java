package Leetcode.AllQuestions;

import java.util.HashMap;

public class Leetcode_1074_NumSubmatrixSumTarget {
    public int numSubmatrixSumTarget(int[][] matrix, int k) {
        int n=matrix.length,m=matrix[0].length,ans=0;
        for(int r1=0;r1<n;r1++){
            int[] arr=new int[m];
            for(int r2=r1;r2<n;r2++){
                for(int i=0;i<m;i++) arr[i]+=matrix[r2][i];
                ans+=f(arr,k);
            }
        }
        return ans;
    }

    public static int f(int[] arr,int k){// 子数组累加和==k的个数
        HashMap<Integer,Integer> map=new HashMap<>();//关心个数，所以需要记录个数
        map.put(0,1);
        int ans=0,sum=0;
        for(int num:arr){
            sum+=num;
            if(map.containsKey(sum-k)) ans+=map.get(sum-k);
            if(!map.containsKey(sum)) map.put(sum,1);
            else map.put(sum,1+map.get(sum));
        }
        return ans;
    }

}
