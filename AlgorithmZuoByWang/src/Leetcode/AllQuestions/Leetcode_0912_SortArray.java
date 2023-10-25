package Leetcode.AllQuestions;

import java.util.HashMap;
import java.util.Map;

public class Leetcode_0912_SortArray {
    public int[] sortArray(int[] nums) {
        quickSort(nums,0,nums.length-1);
        return nums;
    }

    public void quickSort(int[] arr,int l,int r){
        if (l>=r) return;
        int index=partition(arr,l,r);
        quickSort(arr,l,index-1);
        quickSort(arr,index+1,r);
    }

    public int partition(int[] arr,int l,int r){
        swap(arr,l,l+(int)(Math.random()*(r-l+1)));//随机选划分元素
        int pivot=arr[l],p1=l,p2=r+1;
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
