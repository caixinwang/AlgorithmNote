package ClassicInterviewCoding.InterviewCoding06;

public class Code01_MaxEOR {

	// O(N^2)
	public static int maxXorSubarray1(int[] arr) {
		if (arr==null||arr.length==0) return 0;
		int res=Integer.MIN_VALUE,N=arr.length;
		int[] eor=new int[N];//eor[i]代表arr[0^...^i],前缀异或和数组
		eor[0]=arr[0];
		for (int i=1;i<N;i++){
			eor[i]=eor[i-1]^arr[i];
		}
		for (int i = 0; i < N; i++) {//枚举以i作为子数组结尾的异或和最大值
			for (int j = i; j >= 0; j--) {//arr[j^...^i]
				res = Math.max(res, j==0?eor[i]:eor[i]^eor[j-1]);//j=0时值就是eor[i]
			}
		}
		return res;
	}

	// 前缀树的节点类型，每个节点向下只可能有走向0或1的路
	// node.nexts[0] == null 0方向没路
	// node.nexts[0] != null 0方向有路
	public static class Node {
		public Node[] nexts = new Node[2];
	}

	// 基于本题，定制前缀树的实现
	public static class NumTrie {
		public Node head=new Node();

		public void add (int num){
			Node cur=head;
			for (int i = 31; i >=0 ; i--) {//取出num的第i位，第i位为((num>>i)&1)，i从高位31开始取，一直到0
				int path=((num>>i)&1);//第i位取出
				if (cur.nexts[path]==null) cur.nexts[path]=new Node();//有路新建无路复用
				cur=cur.nexts[path];
			}
		}

		public int maxXor(int num){//帮你根据num这个数字，找最适合的路，然后帮你返回最大值--已经帮你算好了。
			Node cur=head;
			int best=0;
			for (int i = 31; i >= 0; i--) {//从高位开始取
				int bit=((num>>i)&1);//取出num的第i位,0/1
				int expect=i==31?bit:bit^1;//最高位期望最终是0，自己和自己异或就是0.其它位希望最终是1，异或上自己的反最终就是1
				int real=cur.nexts[expect]!=null?expect:expect^1;
				best |= real<<i;//在for循环里面，best就是你选择的路对应的值。
				cur=cur.nexts[real];
			}
			return best^num;//best路径对应的值和num异或就是你想要的那个最大值
		}
	}

	public static int maxXorSubarray2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int eor = 0; // 0..i 异或和
		// 前缀树 -> numTrie
		NumTrie numTrie = new NumTrie();
		numTrie.add(0); // 一个数也没有的时候，异或和是0。初始化，使得i=0开始不会出现空指针异常
		for (int i = 0; i < arr.length; i++) {
			eor ^= arr[i]; // eor -> 0..i异或和
			// X, 0~0 , 0~1, .., 0~i-1
			max = Math.max(max, numTrie.maxXor(eor));
			numTrie.add(eor);
		}
		return max;
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 5000;
		int maxSize = 30;
		int maxValue = 50;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int comp = maxXorSubarray1(arr);
			int res = maxXorSubarray2(arr);
			if (res != comp) {
				succeed = false;
				printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
		//
		// // int[] arr = generateRandomArray(6, maxValue);
		// int[] arr = { 3, -28, -29, 2};
		//
		// for (int i = 0; i < arr.length; i++) {
		// System.out.println(arr[i] + " ");
		// }
		// System.out.println("=========");
		// System.out.println(maxXorSubarray(arr));
		// System.out.println((int) (-28 ^ -29));

	}
}
