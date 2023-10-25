package BasicLearning.Class13_SegmentTree;

public class Code05_IndexTree {

	public static class IndexTree {//sum
		int N;
		int[] sum;

		public IndexTree(int N){
			this.N=N;
			sum =new int[N+1];
		}

		public int sum(int index){//1~index的累加和
			int ans=0;
			for (;index>0;index-=index&-index){
				ans+= sum[index];
			}
			return ans;
		}

		public void add(int index,int d){
			for (;index<=N;index+=index&-index){
				sum[index]+=d;
			}
		}

	}

	public static class IndexTree2 {//max
		int N;
		int[] max;

		public IndexTree2(int N){
			this.N=N;
			max =new int[N+1];
		}

		public int max(int index){//1~index的max
			int ans=0;
			for (;index>0;index-=index&-index){
				ans = Math.max(ans, max[index]);
			}
			return ans;
		}

		//这里的update不是真正意义上的update，只有d大于等于max[index]原本的值才有效
		//也就是只能越更越大
		public void update(int index, int d){
			for (;index<=N;index+=index&-index){
				max[index]=Math.max(max[index],d);
			}
		}

	}


}
