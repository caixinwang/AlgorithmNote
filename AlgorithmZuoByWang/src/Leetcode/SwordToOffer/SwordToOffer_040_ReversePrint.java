package Leetcode.SwordToOffer;

import Leetcode.LeetClass.ListNode;

public class SwordToOffer_040_ReversePrint {
    public int[] getLeastNumbers(int[] arr, int k) {//快排
        int n=arr.length,l=0,r=n-1,mid;
        if(k==0) return new int[]{};
        while(l<=r){
            swap(arr,l,l+(int)(Math.random()*(r-l+1)));//随机打乱数据，平均时间复杂度
            mid=partition(arr,l,r);
            if(mid==k-1) break;
            else if(mid>k-1) r=mid-1;
            else l=mid+1;
        }
        int[] ans=new int[k];
        for(int i=0;i<k;i++){
            ans[i]=arr[i];
        }
        return ans;
    }

    public int partition(int[] arr,int l,int r){
        int p1=l,p2=r+1,pivot=arr[l];
        while(p1<=p2){
            while(++p1<=r&&arr[p1]<pivot);
            while(--p2>=l&&arr[p2]>pivot);
            swap(arr,p2,p1<=p2?p1:l);
        }
        return p2;
    }

    public void swap(int[] arr,int a,int b){
        int t=arr[a];
        arr[a]=arr[b];
        arr[b]=t;
    }
}
