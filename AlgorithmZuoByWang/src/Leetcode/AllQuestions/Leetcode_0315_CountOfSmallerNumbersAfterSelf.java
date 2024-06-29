package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0315_CountOfSmallerNumbersAfterSelf {
    class IndexTree{
        int N;
        int[] sum;
        public IndexTree(int N){
            this.N=N;
            sum=new int[N+1];
        }

        public void add(int index,int d){
            for(;index<=N;sum[index]+=d,index+=index&-index);
        }

        public int query(int index){
            int ans=0;
            for (;index>0;ans+=sum[index],index-=-index&index);
            return ans;
        }
    }

    /**
     * 一个位置i的答案，依赖于它后面的，比它小的元素的数量，所以从后往前填。
     * 其实就是查询位于[-00,num[i]-1]范围有多少数，就是一个区间查询，单点修改，直接树状数组。
     */
    public List<Integer> countSmaller(int[] nums) {
        int N=nums.length;
        int[] ans=new int[N];
        int index=N-1;
        Set<Long> set=new TreeSet<>();
        for (int num:nums){
            set.add((long)num);
            set.add((long)num-1);
        }
        int no=1;
        HashMap<Long,Integer> map=new HashMap<>();
        for (long num:set){
            map.put(num,no++);
        }
        IndexTree indexTree=new IndexTree(map.size());
        for (int i=N-1;i>=0;i--){
            int num=nums[i];
            int idx=map.get((long)num);
            int idx1=map.get((long) num -1);
            ans[index--]=indexTree.query(idx1);
            indexTree.add(idx,1);
        }
        List<Integer> list=new ArrayList<>();
        for (int num:ans){
            list.add(num);
        }
        return list;
    }

    public List<Integer> countSmaller4(int[] nums) {
        LinkedList<Integer> ans=new LinkedList<>();
        SegmentTree st=new SegmentTree(3*10000);
        int n=nums.length;
        for(int i=0;i<n;i++) nums[i]+=10001;
        ans.addFirst(0);
        st.add(nums[n-1],1);
        for(int i=n-2;i>=0;i--){
            ans.addFirst(st.query(1,nums[i]-1));
            st.add(nums[i],1);
        }
        return ans;
    }

    class SegmentTree{
        class Node{
            Node left,right;
            int sum=0;
        }

        Node root;
        int size;

        public SegmentTree(int size){//1~size的表示范围
            this.size=size;
            root=new Node();
        }

        public void add (int i,int v){add(i,v,root,1,size);};

        public void add(int i,int v,Node c,int l,int r){
            if(l==r&&l==i){//递归到底
                c.sum+=v;
                return;
            }
            int m=l+(r-l>>1);
            if(i<=m) {
                if(c.left==null) c.left=new Node();
                add(i,v,c.left,l,m);
            }else {
                if(c.right==null) c.right=new Node();
                add(i,v,c.right,m+1,r);
            }
            c.sum=(c.left==null?0:c.left.sum)+(c.right==null?0:c.right.sum);
        }

        public int query(int L,int R){return query(L,R,root,1,size);}

        public int query(int L,int R,Node c,int l,int r){
            if(c==null||R<l||L>r) return 0;
            if(L<=l&&R>=r) return c.sum;
            int m=l+(r-l>>1);
            return query(L,R,c.left,l,m)+query(L,R,c.right,m+1,r);
        }

    }

    /**
     * 归并排序的思想，在merge的过程中产生答案。需要解决的问题是arr在排序的过程中，已经不是原来的样子了，
     * 所以我们需要建立一个映射机制，让我们可以直接从排序过的下标映射到没排序之前的下标。
     * 方法就是实现建立一个idx数组，里面放着[0,1,2,...,N-1]。让这个数组跟着arr数组同步merge，这样就可以
     * 从以后打乱的下标找到之前的下标了。
     * 或者也可以不用多搞一个idx，我们直接搞一个Node，里面封装好index和val，对这个Node手写归并排序。
     */
    public List<Integer> countSmaller2(int[] nums) {
        int[] ans=new int[nums.length];
        int[] idx=new int[nums.length];//下标也一起merge
        for (int i = 0; i < idx.length; i++) {
            idx[i]=i;
        }
        mergeSort(nums,0,nums.length-1,ans,idx);
        List<Integer> list=new ArrayList<>();
        for (int num:ans){
            list.add(num);
        }
        return list;
    }

    public void mergeSort(int[] arr,int l,int r,int[] ans,int[] idx){
        if (l>=r) return;
        int mid=l+(r-l>>1);
        mergeSort(arr,l,mid,ans,idx);
        mergeSort(arr,mid+1,r,ans,idx);
        merge(arr,l,mid,r,ans,idx);
    }

    //不同的是idx数组要和arr数组同步merge！
    //这种题的做法就是：看看你要统计什么，你如果要统计一个数右边有多少比自己大或者比自己小的，那么就p1指针移动的时候结算答案。
    //如果要统计一个数左边有多少大或者比自己小的，那么就是p2指针移动的时候结算答案。
    //移动的时机：例如你要统计一个数右边有多少比自己大的，那么你希望[p1]<[p2]的时候，让p2去移动，等p2第一次失败的时候，也就是[p1]>=[p2]
    //的时候去移动p1。这样的话我们发现就是谁大就移动谁，所以相当于从后往前merge
    //而这题，我们希望要得到一个数右边比自己小的，我们就需要[p1]>[p2]的时候让p2去移动，当p2第一次失败的时候，也就是[p1]<=[p2]的时候结算，
    //这种时候就是从左往右去merge，因为谁小移动谁。
    //总结：统计一个数右边的情况，就是在p1移动的时候结算p1。总是让p2在达标的时候继续移动！
    public void merge(int[] arr,int l,int mid,int r,int[] ans,int[] idx){
        int[] help=new int[r-l+1];
        int[] help2=new int[r-l+1];
        int p1=l,p2=mid+1,index=0;
        while(p1<=mid&&p2<=r){
            if (arr[p1]<=arr[p2]){//在p1移动的时候产生答案，因为之前肯定都是[p2]<[p1],p2当前处于第一个失败的位置
                ans[idx[p1]]+=p2-mid-1;
                help2[index]=idx[p1];
                help[index++]=arr[p1++];
            }else {
                help2[index]=idx[p2];
                help[index++]=arr[p2++];
            }
        }
        while(p1<=mid){
            ans[idx[p1]]+=p2-mid-1;
            help2[index]=idx[p1];
            help[index++]=arr[p1++];
        }
        while (p2<=r){
            help2[index]=idx[p2];
            help[index++]=arr[p2++];
        }
        System.arraycopy(help,0,arr,l,help.length);
        System.arraycopy(help2,0,idx,l,help2.length);
    }

    /**
     * 将统计的逻辑挪出去了，因为有时候归并排序里面逻辑不能和题目需要的逻辑揉在一起，只要在求类似：大于、小于时候才可以揉在一起。
     * 如果问的是大于2arr[i]这样的，就需要将统计逻辑独立出去。
     */
    public List<Integer> countSmaller3(int[] nums) {
        int[] ans=new int[nums.length];
        int[] idx=new int[nums.length];//下标也一起merge
        for (int i = 0; i < idx.length; i++) {
            idx[i]=i;
        }
        mergeSort2(nums,0,nums.length-1,ans,idx);
        List<Integer> list=new ArrayList<>();
        for (int num:ans){
            list.add(num);
        }
        return list;
    }

    public void mergeSort2(int[] arr,int l,int r,int[] ans,int[] idx){
        if (l>=r) return;
        int mid=l+(r-l>>1);
        mergeSort2(arr,l,mid,ans,idx);
        mergeSort2(arr,mid+1,r,ans,idx);
        merge2(arr,l,mid,r,ans,idx);
    }

    public void merge2(int[] arr,int l,int mid,int r,int[] ans,int[] idx){
        int[] help=new int[r-l+1];
        int[] help2=new int[r-l+1];
        int p1=l,p2=mid+1,index=0;
        count(arr,l,mid,r,ans,idx);
        while(p1<=mid&&p2<=r){
            if (arr[p1]<=arr[p2]){//在p1移动的时候产生答案，因为之前肯定都是[p2]<[p1],p2当前处于第一个失败的位置
                help2[index]=idx[p1];
                help[index++]=arr[p1++];
            }else {
                help2[index]=idx[p2];
                help[index++]=arr[p2++];
            }
        }
        while(p1<=mid){
            help2[index]=idx[p1];
            help[index++]=arr[p1++];
        }
        while (p2<=r){
            help2[index]=idx[p2];
            help[index++]=arr[p2++];
        }
        System.arraycopy(help,0,arr,l,help.length);
        System.arraycopy(help2,0,idx,l,help2.length);
    }

    private void count(int[] arr, int l, int mid, int r, int[] ans, int[] idx) {
        int p1=l,p2=mid+1;
        while(p1<=mid&&p2<=r){
            while(p1<=mid&&!(arr[p1]>arr[p2])) {//试图将p2囊括进来
                ans[idx[p1]]+=p2-mid-1;//p2是第一个不合法的位置
                p1++;//老规矩，p1来到第一个达标的位置
            }
            while(p2<=r&&arr[p1]>arr[p2]) p2++;//p2来到第一个不达标的位置
        }
        while(p1<=mid){
            ans[idx[p1]]+=p2-mid-1;//p2是第一个不合法的位置
            p1++;//老规矩，p1来到第一个达标的位置
        }
    }

}
