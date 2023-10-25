package Leetcode.AllQuestions;

import java.util.*;
public class Leetcode_0493_ReversePairs {
    class IndexTree {
        int N;
        int[] sum;//个数

        public IndexTree(int n) {
            N = n;
            sum = new int[N + 1];
        }

        private int lowBit(int index){return index&-index;}

        public int query(int index) {
            int ans = 0;
            for (; index > 0; index -= lowBit(index)) {
                ans += sum[index];
            }
            return ans;
        }

        public void add(int index, int d) {
            for (; index <= N; sum[index] += d,index += lowBit(index));
        }


    }

    class SegmentTree {//单点才这样写
        class Node{
            int sum;
            Node left;
            Node right;
            public Node(){
                sum=0;
                left=null;
                right=null;
            }
        }

        Node root;
        int N;

        public SegmentTree(int N){
            this.N=N;
            root=new Node();
        }

        public int merge(int l,int r){
            return l+r;
        }
        public int merge(Node l,Node r){
            return merge(l==null?0:l.sum,r==null?0:r.sum);
        }

        private int query(int L,int R,int l,int r,Node cur){
            if (cur==null) return 0;//cur代表的了、l~r这范围都是0，返回0不会影响最终的答案。
            if (L<=l&&r<=R) return cur.sum;
            int mid=l+(r-l>>1),p1=0,p2=0;
            if (mid>=L) p1=query(L,R,l,mid,cur.left);
            if (mid<R) p2=query(L,R,mid+1,r,cur.right);
            return merge(p1,p2);
        }

        private void add(int L,int R,int A,int l,int r,Node cur){
            if (l==r) {//一定递归到底
                cur.sum+=A;
                return;
            }
            int mid=l+(r-l>>1);
            if (mid>=L) {
                if (cur.left==null) cur.left=new Node();
                add(L, R, A, l, mid, cur.left);
            }
            if (mid<R){
                if (cur.right==null) cur.right=new Node();
                add(L, R, A, mid+1, r, cur.right);
            }
            cur.sum=merge(cur.left,cur.right);
        }

        public int query(int index) {
            return query(1,index,1,N,root);
        }

        public void add(int index, int d) {
            add(index,index,d,1,N,root);
        }
    }

    /**
     * 题目说，翻转对（i，j）满足num[i]>2sum[j]。我们的思路是每到一个位置，去前面找大于两倍自己的有多少点。
     * 所以我们可以做一个数组，数组里面的值表示每一个数出现的次数，一开始初始值都为0。我们还要能够方便的得到
     * 某一个范围上的数总共出现了几次，并且有单点修改，因为我们每遍历到一个元素，都要把出现的次数+1。
     * 所以我们映射关系为，先做离散化，num-->{1,2,3,...} -> count , 用一个IndexTree来统计这个假想的
     * count数组的前缀和。
     * 每到一个位置要的是之前出现的i，有多少大于两倍的自己，所以要先累计ans，再去更新tree。
     */
    public int reversePairs(int[] nums) {
        TreeSet<Long> set = new TreeSet<Long>();
        for (int num : nums) {
            set.add((long) num);
            set.add((long) 2 * num);
        }
        HashMap<Long, Integer> map = new HashMap<Long, Integer>();
        int no = 1;
        for (Long num : set) map.put(num, no++);
        int N = map.size();
        SegmentTree indexTree = new SegmentTree(N);
        int ans = 0;
        for (int num : nums) {
            int index = map.get((long) num);
            int index2 = map.get((long) num * 2);
            ans += indexTree.query(N) - indexTree.query(index2);
            indexTree.add(index, 1);//个数+1
        }
        return ans;
    }

    public static int reversePairs2(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    public static int mergeSort(int[] arr, int l, int r) {
        if (l >= r) return 0;
        int mid = l + (r - l >> 1);
        return mergeSort(arr, l, mid) + mergeSort(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    /**
     * 在正式的merge之前，在前面加入一段自己统计的逻辑。
     */
    public static int merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int p1 = l, p2 = mid + 1;
        int index = 0;
        int ans = count(arr,l,mid,r);
        while (p1 <= mid && p2 <= r) {
            if (arr[p1] <= arr[p2]) {
                help[index++] = arr[p1++];
            } else {
                help[index++] = arr[p2++];
            }
        }
        while (p1 <= mid) {
            help[index++] = arr[p1++];
        }
        while (p2 <= r) {
            help[index++] = arr[p2++];
        }
        System.arraycopy(help, 0, arr, l, help.length);
        return ans;
    }

    /**
     * 统计的方法，p1移动了就统计，本质上就是算出以每一个p1开头的答案。
     * p1每次走到第一个成功的位置。在这个基础上p2每次都要走到第一个失败的位置，也就是让p2走的尽可能远
     */
    public static int count(int[] arr,int l,int mid,int r){
        int ans = 0;
        int p1 = l, p2 = mid + 1;
        while(p1<=mid&&p2<=r){
            while(p1<=mid&&!((long)arr[p1]>(long)2*arr[p2])) {//走到把p2包括进来
                ans+=p2-mid-1;//p2是第一个不合法的位置
                p1++;
            }
            while(p2<=r&&!((long)arr[p1]<=(long)2*arr[p2])) p2++;//走到第一个失败的位置
        }
        while(p1<=mid){
            ans+=p2-mid-1;//p2是第一个不合法的位置
            p1++;
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr=new int[]{1,3,2,3,1};
        System.out.println(merge(arr, 3, 3, 4));
        int[] arr2=new int[]{1,2,3,1,3};
        System.out.println(merge(arr2,0,2,4));
        System.out.println("mege:"+mergeSort(arr, 0, arr.length - 1));
        for (int i : arr) {
            System.out.println(i);
        }

    }
}
