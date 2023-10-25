package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class Leetcode_0699_FallingSquares {

    class SegmentTree{
        int[] mx;
        boolean[] lzupdate;
        int[] update;
        int N;

        public  SegmentTree(int N){
            this.N=N;
            mx =new int[N<<2];
            update=new int[N<<2];
            lzupdate=new boolean[N<<2];
        }

        private int merge(int left,int right){
            return Math.max(left,right);
        }

        private void pushUp(int rt){//把rt位置更新对,等于无懒更新版本的merge
            mx[rt]= merge(mx[rt<<1], mx[rt<<1|1]);
        }

        private void pushDown(int rt,int l,int r){//把rt懒住的任务发下去
            if (lzupdate[rt]){
                lzupdate[rt<<1]=true;
                lzupdate[rt<<1|1]=true;
                update[rt<<1]=update[rt];
                update[rt<<1|1]=update[rt];
                mx[rt<<1]=update[rt];
                mx[rt<<1|1]=update[rt];
                lzupdate[rt]=false;
                update[rt]=0;
            }
        }

        public void update(int L,int R,int U){
            update(L,R,U,1,N,1);
        }

        private void update(int L, int R, int U, int l, int r, int rt){
            if (L<=l&&r<=R){
                mx[rt]=U;
                update[rt]=U;
                lzupdate[rt]=true;
                return;
            }
            pushDown(rt,l,r);
            int mid=l+r>>1;
            if (mid>=L) update(L,R,U,l,mid,rt<<1);
            if (mid<R) update(L,R,U,mid+1,r,rt<<1|1);
            pushUp(rt);
        }

        public int query(int L,int R){
            return query(L,R,1,N,1);
        }

        private int query(int L, int R, int l, int r, int rt){
            if (L<=l&&r<=R){
                return mx[rt];
            }
            pushDown(rt,l,r);
            int mid=l+r>>1;
            int p1=Integer.MIN_VALUE,p2=Integer.MIN_VALUE;
            if (mid>=L) p1=query(L,R,l,mid,rt<<1);
            if (mid<R) p2=query(L,R,mid+1,r,rt<<1|1);
            return merge(p1,p2);
        }

    }

    class SegmentTree2{//动态开点
        class Node{
            int max;
            boolean lzupdate;
            int update;
            Node left;
            Node right;
            public Node(){
                max=0;
                lzupdate=false;
                update=0;
                left=right=null;
            }
        }

        Node root;
        int N;

        public SegmentTree2(int N){
            this.N=N;
            root=new Node();
        }

        private int merge(int l,int r){
            return Math.max(l,r);
        }

        private void pushUp(Node cur){
            cur.max=merge(cur.left==null?0:cur.left.max,cur.right==null?0:cur.right.max);
        }

        private void pushDown(Node cur){//如果是累加和，这里再加参数，求max就不需要了
            if (cur.left==null) cur.left=new Node();
            if (cur.right==null) cur.right=new Node();
            if (cur.lzupdate){
                cur.left.lzupdate=true;
                cur.left.update=cur.update;
                cur.left.max=cur.update;
                cur.right.lzupdate=true;
                cur.right.update=cur.update;
                cur.right.max=cur.update;
                cur.lzupdate=false;
            }
        }

        public void update(int L,int R,int U){
            update(L,R,U,1,N,root);
        }

        private void update(int L,int R,int U,int l,int r,Node cur){
            if (r<L||l>R) return;
            if (L<=l&&r<=R){
                cur.max=U;
                cur.lzupdate=true;
                cur.update=U;
                return;
            }
            pushDown(cur);
            int mid=l+(r-l>>1);
            update(L,R,U,l,mid,cur.left);
            update(L,R,U,mid+1,r,cur.right);
            pushUp(cur);
        }

        public int query(int L,int R){
            return query(L,R,1,N,root);
        }

        public int query(int L,int R,int l,int r,Node cur){
            if (r<L||l>R) return 0;
            if (L<=l&&r<=R) return cur.max;
            pushDown(cur);
            int mid=l+(r-l>>1);
            return merge(query(L,R,l,mid,cur.left),query(L,R,mid+1,r,cur.right));
        }

    }

    public List<Integer> fallingSquares(int[][] positions) {
        List<Integer> ans=new ArrayList<>();
        if (positions==null||positions.length==0) return ans;
        TreeSet<Integer> set=new TreeSet<>();//离散化
        for (int[] pos:positions){
            set.add(pos[0]);
            set.add(pos[0]+pos[1]-1);
        }
        int no=1;
        HashMap<Integer,Integer> idxMap=new HashMap<>();//离散化
        for (int num:set){
            idxMap.put(num,no++);
        }
        SegmentTree2 segmentTree=new SegmentTree2(set.size());
        for (int[] pos:positions){
            int start=idxMap.get(pos[0]);
            int len=pos[1];
            int end=idxMap.get(pos[0]+pos[1]-1);
            int newHeight = segmentTree.query(start, end)+len;
            segmentTree.update(start,end,newHeight);
            ans.add(segmentTree.query(1,segmentTree.N));
        }
        return ans;
    }

    public static void main(String[] args) {
        Leetcode_0699_FallingSquares c=new Leetcode_0699_FallingSquares();
        int[][] pos=new int[][]{
                {1,2},
                {2,3},
                {6,1}
        };
        c.fallingSquares(pos);
    }
}
