package BasicLearning.Class13_SegmentTree;

public class Code05_IndexTree {

	public static class IndexTree {
		int[] sum;
		public IndexTree(int size){
			sum=new int[size+1];
		}

		public void add(int i,int v){
			for(;i<=sum.length;i+=i&-i) sum[i]+=v;
		}

		public int query(int i){
			int ans=0;
			for(;i>0;i-=i&-i) ans+=sum[i];
			return ans;
		}

		public int query(int L,int R){return query(R)-query(L-1);}

	}

	public static void main(String[] args) {

	}
}
