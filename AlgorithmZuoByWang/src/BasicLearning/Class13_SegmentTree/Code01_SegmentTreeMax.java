package BasicLearning.Class13_SegmentTree;

public class Code01_SegmentTreeMax {//max

    class SegmentTree{//无懒惰&区间修改&单点修改
        int[] mx;
        int N;

        public  SegmentTree(int N){
            this.N=N;
            mx =new int[N<<2];
        }

        public int merge(int left,int right){
            return Math.max(left,right);
        }

        public int query(int L,int R){
            return query(L,R,1,N,1);
        }

        private int query(int L, int R, int l, int r, int rt) {
            if (L<=l&&r<=R){
                return mx[rt];
            }
            int mid=l+(r-l>>1);
            int p1=Integer.MIN_VALUE,p2=Integer.MIN_VALUE;
            if (mid>=L) p1=query(L,R,l,mid,rt<<1);
            if (mid+1<=R) p2=query(L,R,mid+1,r,rt<<1|1);
            return merge(p1,p2);
        }

        public void update(int L,int R,int U){//区间修改
            update(L,R,U,1,N,1);
        }

        public void update(int index,int U){//单点修改
            update(index,index,U,1,N,1);
        }


        //递归到底的去修改
        private void update(int L, int R, int U, int l, int r, int rt) {
            if(l==r){//递归到底
                mx[rt]=U;
                return;
            }
            int mid=l+(r-l>>1);
            if (mid>=L) update(L,R,U,l,mid,rt<<1);
            if (mid+1<=R) update(L,R,U,mid+1,r,rt<<1|1);
            mx[rt]=merge(mx[rt<<1], mx[rt<<1|1]);
        }
    }


    class SegmentTree2{//懒惰
        int[] mx;
        boolean[] lzupdate;
        int[] update;
        int N;

        public  SegmentTree2(int N){
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


}
