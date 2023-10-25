package BasicLearning.Class14_SumSubArray;

public class Code03_LongestLessSumSubArrayLength {

	public static int maxLengthAwesome(int[] arr, int k) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[] minSums = new int[arr.length];
		int[] minSumEnds = new int[arr.length];
		minSums[arr.length - 1] = arr[arr.length - 1];
		minSumEnds[arr.length - 1] = arr.length - 1;
		for (int i = arr.length - 2; i >= 0; i--) {
			if (minSums[i + 1] < 0) {
				minSums[i] = arr[i] + minSums[i + 1];
				minSumEnds[i] = minSumEnds[i + 1];
			} else {
				minSums[i] = arr[i];
				minSumEnds[i] = i;
			}
		}
		int end = 0;
		int sum = 0;
		int res = 0;
		// i是窗口的最左的位置，end扩出来的最右有效块儿的最后一个位置的，再下一个位置
		// end也是下一块儿的开始位置
		// 窗口：[i~end)
		for (int i = 0; i < arr.length; i++) {
			// while循环结束之后：
			// 1) 如果以i开头的情况下，累加和<=k的最长子数组是arr[i..end-1]，看看这个子数组长度能不能更新res；
			// 2) 如果以i开头的情况下，累加和<=k的最长子数组比arr[i..end-1]短，更新还是不更新res都不会影响最终结果；
			while (end < arr.length && sum + minSums[end] <= k) {
				sum += minSums[end];
				end = minSumEnds[end] + 1;
			}
			res = Math.max(res, end - i);
			if (end > i) { // 窗口内还有数 [i~end) [4,4)
				sum -= arr[i];
			} else { // 窗口内已经没有数了，说明从i开头的所有子数组累加和都不可能<=k
				end = i + 1;
			}
		}
		return res;
	}

	// <=k 的最长等价于找x>=sum-k,维持一个只升不降的序列up
	//为什么维持一个up而不是down？因为我们希望维持最希望的左侧，由于x>=? 所以肯定是维持一个升序。
	public static int maxLengthAwesome2(int[] arr, int k) {
		int n=arr.length,ans=0,sum=0;
		int[] up=new int[n+1];
		for (int i = 0; i < n; i++) {
			sum+=arr[i];
			up[i+1]=sum>up[i]?sum:up[i];
		}
		sum=0;
		for (int i = 1; i <= n; i++) {//脑补遍历前缀和数组s
			sum+=arr[i-1];//s[i]
			int l=0,r=i-1,m;
			while(l<=r){//在一个非递减序列up中找>=sum-k的最左
				m=l+(r-l>>1);
				if (up[m]>=sum-k) r=m-1;
				else l=m+1;
			}
			ans = Math.max(ans, i-l);
		}
		return ans;
	}

	public static int maxLengthAwesome3(int[] arr, int k) {//使用单调栈，由于要<=k,所以栈顶要放大的，这样我们固定的尾部成功的概率大
		int n=arr.length;
		int[] s=new int[n+1];
		for(int i=0;i<n;i++) s[i+1]=s[i]+arr[i];//分数的前缀和数组
		int[] stack=new int[n+1];
		int top=-1,ans=0;
		for(int i=0;i<=n;i++){
			if(top==-1||s[i]>s[stack[top]]) stack[++top]=i;//不踢人，如果比栈顶大就直接放进来，小的就直接无视
		}
		for(int i=n;i>0;i--){
			while(top>=0&&s[i]-s[stack[top]]<=k) ans=Math.max(ans,i-stack[top--]);//这里是while不是if
		}
		return ans;
	}

	// for test
	public static int[] generateRandomArray(int len, int maxValue) {
		int[] res = new int[len];
		for (int i = 0; i != res.length; i++) {
			res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println("test begin");
		for (int i = 0; i < 1000000; i++) {
			int[] arr = generateRandomArray(10, 20);
			int k = (int) (Math.random() * 20) - 5;
			int ans1=maxLengthAwesome2(arr, k);
			int ans2=maxLengthAwesome3(arr, k);
			if (ans1 != ans2) {
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("==================");
			}
		}
		System.out.println("test finish");
	}

}
