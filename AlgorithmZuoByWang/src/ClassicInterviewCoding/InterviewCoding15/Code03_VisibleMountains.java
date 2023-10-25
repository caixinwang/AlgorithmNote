package ClassicInterviewCoding.InterviewCoding15;

import java.util.HashSet;
import java.util.Stack;

public class Code03_VisibleMountains {

	// 栈中放的记录，
	// value就是值，times是收集的个数
	public static class Record {
		public int value;
		public int times;

		public Record(int value) {
			this.value = value;
			this.times = 1;
		}
	}

	public static int getVisibleNum(int[] arr) {
		if (arr==null||arr.length<2) return 0;
		int N=arr.length;
		int maxIndex=0;//最高山峰对应的下标
		for (int i = 0; i < arr.length; i++) {
			if (arr[i]>arr[maxIndex]) maxIndex=i;
		}
		Stack<Record> stack=new Stack<>();//由于是环形的，第一个山maxIndex开头位置先处理
		stack.push(new Record(arr[maxIndex]));//默认初始化就是一次,用山的高度比较大小
		int p=nextIndex(maxIndex,N);//第二个位置开始
		int res=0;
		for (;p!=maxIndex;p=nextIndex(p,N)){//回到开头就退出，开头位置已经单独处理了
			while(!stack.isEmpty()&&arr[p]>stack.peek().value){
				Record pop = stack.pop();
				res+=pop.times*2+cn2(pop.times);//栈底是最大的，永远不会弹出，所以这个弹出来得的东西下面肯定还有东西
			}
			if (arr[p]==stack.peek().value){//相等
				Record peek = stack.peek();
				peek.times++;
			}else {
				stack.push(new Record(arr[p]));
			}
		}
		while(stack.size()>2){//三座山以上，弹出来的山一定可以看到两座。
			Record pop = stack.pop();
			res+=pop.times*2+cn2(pop.times);
		}
		if (stack.size()==2){//最高的山如果只有1座，那么第二高的山对外就只能看到一个了，如果大于两座，那么对外就能看到两个
			Record pop = stack.pop();
			Record peek = stack.peek();//最高的山
			res+=(peek.times>=2? pop.times*2: pop.times)+cn2(pop.times);
		}
		if (stack.size()==1){//最高的山只能自己内部看，外部由于规定小看大，所以没有外部的
			res+=cn2(stack.pop().times);
		}
		return res;
	}

	// 如果n==1返回0，如果n>1返回C(n,2)
	public static int cn2(int n) {
		return n==1?0:(n*(n-1))/2;
	}

	// 环形数组中当前位置为i，数组长度为N，返回i的下一个位置
	public static int nextIndex(int i, int N) {
		return i<N-1?i+1:0;
	}

	// 环形数组中当前位置为i，数组长度为N，返回i的上一个位置
	public static int lastIndex(int i, int N) {
		return i>0?i-1:N-1;
	}

	// for test, O(N^2)的解法，绝对正确
	public static int rightWay(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int res = 0;
		HashSet<String> equalCounted = new HashSet<>();
		for (int i = 0; i < arr.length; i++) {
			// 枚举从每一个位置出发，根据“小找大”原则能找到多少对儿，并且保证不重复找
			res += getVisibleNumFromIndex(arr, i, equalCounted);
		}
		return res;
	}

	// for test
	// 根据“小找大”的原则返回从index出发能找到多少对
	// 相等情况下，比如arr[1]==3，arr[5]==3
	// 之前如果从位置1找过位置5，那么等到从位置5出发时就不再找位置1（去重）
	// 之前找过的、所有相等情况的山峰对，都保存在了equalCounted中
	public static int getVisibleNumFromIndex(int[] arr, int index,
			HashSet<String> equalCounted) {
		int res = 0;
		for (int i = 0; i < arr.length; i++) {
			if (i != index) { // 不找自己
				if (arr[i] == arr[index]) {
					String key = Math.min(index, i) + "_" + Math.max(index, i);
					// 相等情况下，确保之前没找过这一对
					if (equalCounted.add(key) && isVisible(arr, index, i)) {
						res++;
					}
				} else if (isVisible(arr, index, i)) { // 不相等的情况下直接找
					res++;
				}
			}
		}
		return res;
	}

	// for test
	// 调用该函数的前提是，lowIndex和highIndex一定不是同一个位置
	// 在“小找大”的策略下，从lowIndex位置能不能看到highIndex位置
	// next方向或者last方向有一个能走通，就返回true，否则返回false
	public static boolean isVisible(int[] arr, int lowIndex, int highIndex) {
		if (arr[lowIndex] > arr[highIndex]) { // “大找小”的情况直接返回false
			return false;
		}
		int size = arr.length;
		boolean walkNext = true;
		int mid = nextIndex(lowIndex, size);
		// lowIndex通过next方向走到highIndex，沿途不能出现比arr[lowIndex]大的数
		while (mid != highIndex) {
			if (arr[mid] > arr[lowIndex]) {
				walkNext = false;// next方向失败
				break;
			}
			mid = nextIndex(mid, size);
		}
		boolean walkLast = true;
		mid = lastIndex(lowIndex, size);
		// lowIndex通过last方向走到highIndex，沿途不能出现比arr[lowIndex]大的数
		while (mid != highIndex) {
			if (arr[mid] > arr[lowIndex]) {
				walkLast = false; // last方向失败
				break;
			}
			mid = lastIndex(mid, size);
		}
		return walkNext || walkLast; // 有一个成功就是能相互看见
	}

	// for test
	public static int[] getRandomArray(int size, int max) {
		int[] arr = new int[(int) (Math.random() * size)];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * max);
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int size = 10;
		int max = 10;
		int testTimes = 3000000;
		System.out.println("test begin!");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = getRandomArray(size, max);
			if (rightWay(arr) != getVisibleNum(arr)) {
				printArray(arr);
				System.out.println(rightWay(arr));
				System.out.println(getVisibleNum(arr));
				break;
			}
		}
		System.out.println("test end!");
	}

}