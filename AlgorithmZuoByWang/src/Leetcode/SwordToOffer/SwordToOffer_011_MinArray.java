package Leetcode.SwordToOffer;

public class SwordToOffer_011_MinArray {
    public int minArray(int[] arr) {
        int n=arr.length,l=0,r=n-1;
        while(l<r&&arr[l]==arr[n-1]) l++;//数组中有重复元组，避免上半区和下半区的右端点一致
        while(l<=r){
            int mid=l+(r-l>>1);
            if(arr[mid]<=arr[n-1]) r=mid-1;
            else l=mid+1;
        }
        return arr[l];
    }
}
