package Leetcode.AllQuestions;

import java.util.LinkedList;

public class Leetcode_2762_ContinuousSubarrays {
    public long continuousSubarrays1(int[] arr) {
        long ans=0;
        int n=arr.length;
        LinkedList<Integer> minq=new LinkedList<>();
        LinkedList<Integer> maxq=new LinkedList<>();
        for(int l=0,r=0;l<n;){
            while(r<n&&!(!minq.isEmpty()&&abs(min(arr[r],arr[minq.peekFirst()])-max(arr[r],arr[maxq.peekFirst()]))>2)){
                while(!minq.isEmpty()&&arr[r]<arr[minq.peekLast()]) minq.pollLast();
                while(!maxq.isEmpty()&&arr[r]>arr[maxq.peekLast()]) maxq.pollLast();
                minq.addLast(r);
                maxq.addLast(r);
                r++;
            }
            ans+=r-l;
            while(!minq.isEmpty()&&l==minq.peekFirst()) minq.pollFirst();
            while(!maxq.isEmpty()&&l==maxq.peekFirst()) maxq.pollFirst();
            l++;
        }
        return ans;
    }

    public long continuousSubarrays(int[] arr) {
        long ans=0;
        int n=arr.length;
        LinkedList<Integer> minq=new LinkedList<>();
        LinkedList<Integer> maxq=new LinkedList<>();
        for(int l=0,r=0;r<n;){
            while(!minq.isEmpty()&&arr[r]<arr[minq.peekLast()]) minq.pollLast();
            while(!maxq.isEmpty()&&arr[r]>arr[maxq.peekLast()]) maxq.pollLast();
            minq.addLast(r);
            maxq.addLast(r);
            r++;
            while(abs(arr[minq.peekFirst()]-arr[maxq.peekFirst()])>2){
                while(!minq.isEmpty()&&l==minq.peekFirst()) minq.pollFirst();
                while(!maxq.isEmpty()&&l==maxq.peekFirst()) maxq.pollFirst();
                l++;
            }
            ans+=r-l;
        }
        return ans;
    }

    public int abs(int a){return a<0?-a:a;};
    public int max(int a,int b){return a>b?a:b;}
    public int min(int a,int b){return a<b?a:b;}
}
