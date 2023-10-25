package Leetcode.SwordToOffer;

import java.util.HashMap;
import java.util.TreeSet;

public class SwordToOffer_051_ReversePairs {

    class Node{
        int sum;
        Node left;
        Node right;
    }
    Node root=new Node();

    public int reversePairs(int[] nums) {
        int ans=0,n=nums.length,max=0,size=n<<1;
        TreeSet<Integer> set=new TreeSet<>();
        for(int num:nums) set.add(num);
        int index=1;
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int num:set) map.put(num,index++);
        for(int i=0;i<n;i++){
            int d=map.get(nums[i]);
            ans+=query(d+1,size,1,size,root);
            add(d,1,1,size,root);
        }
        return ans;
    }

    private void add(int index,int A,int l,int r,Node cur){
        if(l==r){
            cur.sum+=A;
            return;
        }
        int mid=l+(r-l>>1);
        if(mid>=index){
            if(cur.left==null) cur.left=new Node();
            add(index,A,l,mid,cur.left);
        }else{
            if(cur.right==null) cur.right=new Node();
            add(index,A,mid+1,r,cur.right);
        }
        cur.sum=(cur.left==null?0:cur.left.sum)+(cur.right==null?0:cur.right.sum);
    }

    private int query(int L,int R,int l,int r,Node cur){
        if(cur==null) return 0;
        if(L<=l&&r<=R) return cur.sum;
        int mid=l+(r-l>>1),p1=0,p2=0;
        if(mid>=L){
            p1=query(L,R,l,mid,cur.left);
        }
        if(mid+1<=R){
            p2=query(L,R,mid+1,r,cur.right);
        }
        return p1+p2;
    }

    int[] sum;
    public int reversePairs2(int[] nums) {
        int ans=0,n=nums.length,max=0,size=n+1;
        sum=new int[size<<2];
        TreeSet<Integer> set=new TreeSet<>();
        for(int num:nums) set.add(num);
        int index=1;
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int num:set) map.put(num,index++);
        for(int i=0;i<n;i++){
            int d=map.get(nums[i]);
            ans+=query2(d+1,size,1,n+1,1);
            add2(d,1,1,n+1,1);
        }
        return ans;
    }

    private void add2(int index,int A,int l,int r,int cur){
        if(l==r){
            sum[cur]+=A;
            return;
        }
        int mid=l+(r-l>>1);
        if(mid>=index){
            add2(index,A,l,mid,cur<<1);
        }else{
            add2(index,A,mid+1,r,cur<<1|1);
        }
        sum[cur]=sum[cur<<1]+sum[cur<<1|1];
    }

    private int query2(int L,int R,int l,int r,int cur){
        if(L<=l&&r<=R) return sum[cur];
        int mid=l+(r-l>>1),p1=0,p2=0;
        if(mid>=L){
            p1=query2(L,R,l,mid,cur<<1);
        }
        if(mid+1<=R){
            p2=query2(L,R,mid+1,r,cur<<1|1);
        }
        return p1+p2;
    }

    public int reversePairs3(int[] nums) {
        return mergeSort(nums,0,nums.length-1);
    }

    public int mergeSort(int[] nums,int l,int r){
        if(l>=r) return 0;
        int mid=l+(r-l>>1);
        return mergeSort(nums,l,mid)+mergeSort(nums,mid+1,r)+merge(nums,l,mid,r);
    }

    public int merge(int[] nums,int l,int mid,int r){
        int ans=0,len=r-l+1;
        int[] help=new int[len];
        int p1=l,p2=mid+1,index=0;
        while(p1<=mid||p2<=r){
            if(p1>mid) {
                help[index++]=nums[p2++];
            }else if(p2>r) {
                help[index++]=nums[p1++];
            }else if(nums[p1]<=nums[p2]){
                help[index++]=nums[p1++];
            }else{
                help[index++]=nums[p2++];
                ans+=mid+1-p1;
            }
        }
        System.arraycopy(help,0,nums,l,len);
        return ans;
    }


}
