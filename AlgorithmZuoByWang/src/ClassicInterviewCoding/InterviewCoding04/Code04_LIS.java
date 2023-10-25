package ClassicInterviewCoding.InterviewCoding04;

public class Code04_LIS {

	public static int[] lis1(int[] arr) {
		if (arr == null || arr.length == 0) return null;
		int[] dp = getdp1(arr);
		int[] res = generateLIS(arr,dp);
		return res;
	}

	public static int[] getdp1(int[] arr) {
		int N = arr.length;
		int[] dp = new int[N];//dp[i]：以i结尾的最长递增子序列的长度
		dp[0] = 1;//以0位置结尾的最长递增子序列显然长度为1
		for (int i = 1; i < N; i++) {//找到arr[k]小于arr[i]的下标k1,...kn,dp[i]=max{dp[k1]+1,...,dp[kn]+1}
			for (int k = i - 1; k >= 0; k--) {
				if (arr[k] < arr[i]) dp[i] = Math.max(dp[i], dp[k] + 1);
			}
		}
		return dp;
	}

	public static int[] generateLIS(int[] arr, int[] dp) {//利用dp进行加工得到LIS序列具体是什么
		//先找到dp中最大值max以及其下标index，然后顺着dp的index往前找max-1,max-2...直到1，假设dp[k]=max-1，那么res[i]=arr[k]
		int max = 0, index = 0;
		for (int i = 0; i < dp.length; i++) {
			if (dp[i] > max) {
				max = dp[i];
				index = i;
			}
		}
		int[] res = new int[max];
		for (int k = index,i=max-1; k >= 0; k--) {
			if (max == dp[k]) {
				res[i--] = arr[k];
				max--;
			}
		}
		return res;
	}

	public static int[] lis2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[] dp = getdp2(arr);
		return generateLIS(arr, dp);
	}

	/**
	 * dp[i]怎么求？更新dp[i]的每一个，都要同步更新ends数组。在ends数组中找大于等于arr[i]的最左位置k，
	 * 然后更新ends[k]=arr[i]，然后dp[i]=k+1。
	 * @param arr 找arr的最长递增子序列
	 * @return 返回dp数组
	 */
	public static int[] getdp2(int[] arr) {//在方法一中做了加速
		int N=arr.length,l=0,r=0,mid=0,endIndex=0;
		int[] dp=new int[N];//含义与方法1的含义相同
		int[] ends=new int[N];//ends[i]代表长度为i+1的最长递增子序列的结尾的大小
		dp[0]=1;
		ends[0]=arr[0];
		for (int i=1;i<N;i++){
			l=0;
			r=endIndex;
			while(l<=r){
				mid=l+(r-l>>1);
				if (ends[mid]>=arr[i]){
					r=mid-1;//找最左，所以r动。最终l会是答案
				}else {
					l=mid+1;
				}
			}
			endIndex=Math.max(endIndex,l);//看看是否ends中不存在大于等于arr[i]的数，是的话就扩招有效区
			ends[l]=arr[i];//扩招和覆盖都包含在这一句语句中
			dp[i]=l+1;
		}
		return dp;
	}



	public static int lis3(int[] arr) {//如果只要长度，那么只需一个ends数组即可
		if (arr==null||arr.length==0) return -1;
		int N=arr.length;
		int[] ends=new int[N];
		int len=0;//有效区长度
		for (int num:arr){
			int l=0,r=len-1;
			while(l<=r){
				int mid=l+(r-l>>1);
				if (ends[mid]>=num) r=mid-1;
				else l=mid+1;
			}
			if (l==len) len++;
			ends[l]=num;
		}
		return len;
	}

	public static int[] lis4(int[] arr) {//如果要具体的最长递增子序列，那么也只需要一个ends数组即可
		if (arr==null||arr.length==0) return null;
		int N=arr.length;
		int[] ends=new int[N];
		int len=0;//有效区长度
		for (int num:arr){
			int l=0,r=len-1;
			while(l<=r){
				int mid=l+(r-l>>1);
				if (ends[mid]>=num) r=mid-1;
				else l=mid+1;
			}
			if (l==len) len++;
			ends[l]=num;
		}
		int[] ans=new int[len];
		System.arraycopy(ends,0,ans,0,len);
		return ans;
	}

	// for test
	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] arr = { 2, 1, 5, 3, 6, 4, 8, 9, 7 };
		printArray(arr);
		printArray(getdp2(arr));
		printArray(lis1(arr));
		printArray(lis2(arr));
		printArray(lis4(arr));
		System.out.println(lis3(arr));

	}
}