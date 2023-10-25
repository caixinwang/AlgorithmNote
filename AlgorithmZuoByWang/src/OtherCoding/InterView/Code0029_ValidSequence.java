package OtherCoding.InterView;

// 来自腾讯
// 给定一个长度为n的数组arr，求有多少个子数组满足 : 
// 子数组两端的值，是这个子数组的最小值和次小值，最小值和次小值谁在最左和最右无所谓
// n<=100000（10^5） n*logn  O(N)
public class Code0029_ValidSequence {
	

	public static int nums(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int n = arr.length;
		int[] values = new int[n];
		int[] times = new int[n];
		int size = 0;
		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			while (size != 0 && values[size - 1] > arr[i]) {
				size--;
				ans += times[size] + cn2(times[size]);
			}
			if (size != 0 && values[size - 1] == arr[i]) {
				times[size - 1]++;
			} else {
				values[size] = arr[i];
				times[size++] = 1;
			}
		}
		while (size != 0) {
			ans += cn2(times[--size]);
		}
		for (int i = arr.length - 1; i >= 0; i--) {
			while (size != 0 && values[size - 1] > arr[i]) {
				ans += times[--size];
			}
			if (size != 0 && values[size - 1] == arr[i]) {
				times[size - 1]++;
			} else {
				values[size] = arr[i];
				times[size++] = 1;
			}
		}
		return ans;
	}

	public static int cn2(int n) {
		return (n * (n - 1)) >> 1;
	}

	public static int nums2(int[] arr) {
		if (arr==null||arr.length<2) return 0;
		int N=arr.length;
		int[] stack=new int[N];//放的就是值，不是下标，因为这题不关心下标，不需要求长度
		int[] nums=new int[N];//和stack搭配使用,代表有多少个相同元素
		int top=-1;//size=top+1;
		int ans=0;
		for (int i=0;i<N;i++){//我们一般喜欢弹出的时候结算答案，此时被弹出的元素是次小值，所以我们等价于枚举了所有次小在左侧的情况
			while (top!=-1&&arr[i]<stack[top]){
				ans+=nums[top]+cn2(nums[top]);
				top--;
			}
			if (top!=-1&&arr[i]==stack[top]){
				nums[top]++;
			}else {
				stack[++top]=arr[i];
				nums[top]=1;
			}
		}
		while(top!=-1){//结算组间
			ans+=cn2(nums[top--]);
		}
		int i=N-1;
		while (i>=0){//枚举次小在右侧的情况，不重复结算组内。
			if (top!=-1&&arr[i]<stack[top]){
				ans+=nums[top];
				top--;
			}else if (top!=-1&&arr[i]==stack[top]){
				nums[top]++;
				i--;
			}else {
				stack[++top]=arr[i--];
				nums[top]=1;
			}
		}
		return ans;
	}

	// 为了测试
	// 暴力方法
	public static int test(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int ans = 0;
		for (int s = 0; s < arr.length; s++) {
			for (int e = s + 1; e < arr.length; e++) {
				int max = Math.max(arr[s], arr[e]);
				boolean valid = true;
				for (int i = s + 1; i < e; i++) {
					if (arr[i] < max) {
						valid = false;
						break;
					}
				}
				ans += valid ? 1 : 0;
			}
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * v);
		}
		return arr;
	}

	// 为了测试
	public static void printArray(int[] arr) {
		for (int num : arr) {
			System.out.print(num + " ");
		}
		System.out.println();
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 30;
		int v = 30;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int m = (int) (Math.random() * n);
			int[] arr = randomArray(m, v);
			int ans1 = nums2(arr);
			int ans2 = test(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
				printArray(arr);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}
