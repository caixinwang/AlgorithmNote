package Leetcode.AllQuestions;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Leetcode_0300_LongestIncreasingSubsequence {
    public int lengthOfLIS(int[] arr) {//用一个ends数组来做就可以了
        if (arr==null||arr.length==0) return -1;
        int n=arr.length;
        int[] ends=new int[n];
        int len=0;//有效区长度
        for (int num:arr){
            int l=0,r=len-1;
            while(l<=r){
                int mid=l+(r-l>>1);
                if (ends[mid]>=num) r=mid-1;//找到大于等于num最左的位置
                else l=mid+1;
            }
            if (l==len) len++;//说明有效区不够长
            ends[l]=num;
        }
        return len;
    }

    class Node{
        int max;
        Node left;
        Node right;
    }

    Node root=new Node();
    int size=(int)(2*1e4+20);

    public void modify(int index,int U,int l,int r,Node cur){
        if (l==r){
            cur.max=U;
            return;
        }
        int mid=l+(r-l>>1);
        if (mid>=index){
            if (cur.left==null) cur.left=new Node();
            modify(index,U,l,mid,cur.left);
        }else {
            if (cur.right==null) cur.right=new Node();
            modify(index,U,mid+1,r,cur.right);
        }
        cur.max=Math.max(cur.left==null?0:cur.left.max,cur.right==null?0:cur.right.max);
    }

    public int query(int L,int R,int l,int r,Node cur){
        if (cur==null) return 0;
        if (L<=l&&r<=R) return cur.max;
        int mid=l+(r-l>>1),p1=0,p2=0;
        if (mid>=L){
            p1=query(L,R,l,mid,cur.left);
        }
        if (mid+1<=R) {
            p2=query(L,R,mid+1,r,cur.right);
        }
        return Math.max(p1,p2);
    }

    public int lengthOfLIS2(int[] arr) {//用一个ends数组来做就可以了
        if (arr==null||arr.length==0) return -1;
        int n=arr.length;
        for (int i = 0; i < n; i++) {
            arr[i]+=(int)(1e4+10);//[1~20010],变到线段树的处理范围
        }
        for (int num:arr){
            modify(num,query(1,num-1,1,size,root)+1,1,size,root);
        }
        return root.max;
    }

}
