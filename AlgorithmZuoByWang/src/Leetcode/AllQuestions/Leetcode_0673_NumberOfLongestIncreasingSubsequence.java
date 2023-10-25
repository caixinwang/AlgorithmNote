package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0673_NumberOfLongestIncreasingSubsequence {
    /**
     * ends数组升级：ends[i]里面存TreeMap记录着i+1长度之前的最小的数是多少，以及它及它之前累计了多少个递增子序列。
     * @param arr 无序数组
     * @return arr的最长自增子序列有多少个
     */
    public int findNumberOfLIS(int[] arr) {
        if (arr == null || arr.length == 0) return -1;
        int N = arr.length;
        ArrayList<TreeMap<Integer, Integer>> ends = new ArrayList<>();//index下标存的Map是之前最小值的迭代记录
        for (int num : arr) {
            int l = 0, r = ends.size() - 1;
            while (l <= r) {
                int mid = l + (r - l >> 1);
                if (ends.get(mid).firstKey() >= num) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            int add = 1;//前面的记录至少也有一条你能用的，就是一个空序列，所以初始值设置为1
            if (l - 1 >= 0) {//看看前面的你可以继承多少，也就是在前面虚列的基础上，后面直接填上num，至少有一个可以继承，那就是空串
                TreeMap<Integer, Integer> treeMap = ends.get(l - 1);
                add = treeMap.firstEntry().getValue();//最多继承这么多
                if (treeMap.ceilingKey(num) != null) {//如果有等于等于num的，就减掉
                    add -= treeMap.get(treeMap.ceilingKey(num));
                }
            }
            if (l == ends.size()) {//如果是新怼出来的位置，那么就新加一张表，只能从前面的继承了，那就是add
                TreeMap<Integer, Integer> treeMap = new TreeMap<>();
                treeMap.put(num, add);
                ends.add(treeMap);
            } else {//否则还可以额外记录自己下面的。下面的只是累加上历史信息，这些序列不是以num结尾的
                TreeMap<Integer, Integer> treeMap = ends.get(l);
                treeMap.put(num, treeMap.firstEntry().getValue() + add);
            }
        }
        return ends.get(ends.size() - 1).firstEntry().getValue();
    }

    class Node{
        int max;
        int count;

        public Node() {
            max=0;
            count=0;
        }

        public Node(int max, int count) {
            this.max = max;
            this.count = count;
        }
    }

    class SegmentTree{//单点更新就不要lazy了，如果加上了count，那么就写好merge
        int N;
        Node[] nodes;
        public  SegmentTree(int N){
            this.N=N;
            nodes=new Node[N<<2];
            for (int i = 0; i < nodes.length; i++) {
                nodes[i]=new Node();
            }
        }

        private Node merge(Node node1,Node node2){
            Node node=new Node();
            if (node1.max> node2.max){
                node.max=node1.max;
                node.count=node1.count;
            } else if (node1.max< node2.max) {
                node.max=node2.max;
                node.count=node2.count;
            }else {
                node.max=node1.max;
                node.count=node1.count+node2.count;
            }
            return node;
        }

        private void modify(int index,Node M,int rt,int l,int r){//单点修改
            int mid=l+r>>1;
            if (index<=l&&r<=index){
                nodes[rt]=merge(M,nodes[rt]);
                return;
            }
            if (index<=mid){
                modify(index,M,rt<<1,l,mid);
            }
            if (index>mid) {
                modify(index,M,rt<<1|1,mid+1,r);
            }
            nodes[rt]=merge(nodes[rt<<1],nodes[rt<<1|1]);
        }


        public void modify(int index,Node M){
            modify(index,M,1,1,N);
        }

        private Node query(int L,int R,int rt,int l,int r){
            int mid=l+r>>1;
            if (L<=l&&r<=R) {
                return nodes[rt];
            }else {
                Node resl=new Node();
                Node resr=new Node();
                if (L<=mid){
                    resl=query(L,R,rt<<1,l,mid);
                }
                if (mid + 1 <= R) {
                    resr=query(L,R,rt<<1|1,mid+1,r);
                }
                return merge(resl,resr);
            }
        }

        public Node query(int L,int R){
            return query(L,R,1,1,N);
        }

    }

    public int findNumberOfLIS2(int[] nums) {//这题数组短，离散化处理更好
        // 离散化处理
        Map<Integer, Integer> numDict = new HashMap<>();
        int[] numsSort = Arrays.copyOf(nums, nums.length);
        Arrays.sort(numsSort);
        for (int i = 0; i < numsSort.length; i++) {
            numDict.put(numsSort[i], i+2);//离散到2开始，而不是1，这样就可以避免处理1的特殊情况
        }
        // 构造线段树
        SegmentTree segmentTree = new SegmentTree(numsSort.length+1);//因为离散到2开始，所以多开一个空间
        for (int num : nums) {
            int index = numDict.get(num);
            Node val = segmentTree.query(1, index - 1);
            segmentTree.modify(index, new Node(val.max+1,Math.max(val.count,1)));
        }
        return segmentTree.query(1,segmentTree.N).count;
    }

    class TreeNode {
        int max;
        int cnt;
        TreeNode left;
        TreeNode right;
    }

    TreeNode root=new TreeNode();
    int size=(int)(2*1e6+100);

    public TreeNode merge(TreeNode left,TreeNode right){
        TreeNode res=new TreeNode();
        if (left==null||right==null) return left!=null?left:right!=null?right:res;
        if (left.max>right.max){
            return left;
        }else if (left.max<right.max){
            return right;
        }else {
            res.max= left.max;
            res.cnt= left.cnt+right.cnt;
            return res;
        }
    }

    public void update(int index,int U,int C,int l,int r,TreeNode cur){
        if (l==r){
            cur.cnt=U==cur.max?cur.cnt+C:C;
            cur.max=U;
            return;
        }
        int mid=l+(r-l>>1);
        if (mid>=index){
            if (cur.left==null) cur.left=new TreeNode();
            update(index,U,C,l,mid,cur.left);
        }else {
            if (cur.right==null) cur.right=new TreeNode();
            update(index,U,C,mid+1,r,cur.right);
        }
        TreeNode merge=merge(cur.left,cur.right);
        cur.max= merge.max;cur.cnt= merge.cnt;
    }

    public TreeNode query(int L,int R,int l,int r,TreeNode cur){
        if (cur==null) return new TreeNode();
        if (L<=l&&r<=R) return cur;
        int mid=l+(r-l>>1);
        TreeNode p1=new TreeNode(),p2=new TreeNode();
        if (mid>=L) p1=query(L,R,l,mid,cur.left);
        if (mid+1<=R) p2=query(L,R,mid+1,r,cur.right);
        return merge(p1,p2);
    }

    public int findNumberOfLIS3(int[] arr) {
        if (arr==null||arr.length==0) return -1;
        int n=arr.length;
        for (int i = 0; i < n; i++) {
            arr[i]+=(int)(1e6+10);//变到线段树的处理范围
        }
        for (int num:arr){
            TreeNode query = query(1, num - 1, 1, size, root);
            update(num,query.max+1,Math.max(1, query.cnt),1,size,root);
        }
        return root.cnt;
    }



    public static void main(String[] args) {
        Leetcode_0673_NumberOfLongestIncreasingSubsequence c=new Leetcode_0673_NumberOfLongestIncreasingSubsequence();
        int[] nums=new int[]{1,3,5,4,7};
        c.findNumberOfLIS3(nums);
    }


}
