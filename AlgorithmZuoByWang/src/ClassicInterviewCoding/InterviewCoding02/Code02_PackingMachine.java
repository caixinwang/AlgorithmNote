package ClassicInterviewCoding.InterviewCoding02;
/**
 * 有个打包机器从左到右一字排开，上方有一个自动装置会抓取一批放物品到每个打包机上
 * 放到每个机器上的这些物品数量有多有少，由于物品数量不相同，需要工人将每个机器上的
 * 物品进行移动从而到达物品数量相等才能打包。每个物品重量太大、每次只能搬一个物品进
 * 行移动，为了省力，只在相邻的机器上移动。请计算在搬动最小轮数的前提下，使每个机器
 * 上的物品数量相等。如果不能使每个机器上的物品相同，返回-1。
 * 每一轮指的是每一台机器都可以选择往左或者往右扔包裹。
 * 例如[1,0,5]表示有3个机器，
 * 每个机器上分别有1、0、5个物品，[1,0,5]，经过这些轮后：
 * 第一轮：[1,1,4]；第三台机器往左扔包裹，其它机器不动。
 * 第二轮：[2,1,3]；第三台和第二台机器都往左扔包裹。
 * 第三轮：[2,2,2];  第三台机器往左扔包裹。
 * 移动了3轮，每个机器上的物品相等，所以返回3
 * 例如[2,2,3]表示有3个机器，每个机器上分别有2、2、3个物品，这些物品不管怎么移动，都
 * 不能使三个机器上物品数量相等，返回-1
 */
public class Code02_PackingMachine {

	public static int minOps(int[] arr) {//左老师版本
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int size = arr.length;
		int sum = 0;
		for (int i = 0; i < size; i++) {//求出衣服总量
			sum += arr[i];
		}
		if (sum % size != 0) {//不可能配平
			return -1;
		}
		int avg = sum / size;//最终配平每台机器有这么avg多的衣服
		int leftSum = 0;//利用一个变量知道左边的衣服总量是多少，一直迭代保证它的含义
		int ans = 0;
		// 每个位置都求各自的
		for (int i = 0; i < arr.length; i++) {
			// i号机器，是中间机器，左(0~i-1) i 右(i+1~N-1)
			// 负 需要输入     正需要输出 
			int leftRest = leftSum - i * avg; // a-b
			// 负 需要输入     正需要输出 
			// c - d
			int rightRest =  (sum - leftSum - arr[i]) -  (size - i - 1) * avg; 
			if (leftRest < 0 && rightRest < 0) {
				ans = Math.max(ans, Math.abs(leftRest) + Math.abs(rightRest));
			} else {
				ans = Math.max(ans, Math.max(Math.abs(leftRest), Math.abs(rightRest)));
			}
			leftSum += arr[i];
		}
		return ans;
	}

	public static int minOps2(int[] arr) {// cx's version
		if (arr==null||arr.length==0) return -1;
		int sum=0,N=arr.length,average=0,leftSum=0,leftRest=0,rightRest=0,ans=0;
		for (int i = 0; i < arr.length; i++) {
			sum+=arr[i];//sum用来配合求出平均个数、以及用于求出rightSum
		}
		if (sum%N!=0) return -1;//不可能配平，返回-1
		average=sum/N;//算出每台机器最后应该是average个衣服
		for (int i = 0; i < arr.length; i++) {//对于每一个位置求出它的指标,leftSum为arr(0...i-1),rightSum为arr(i+1...N-1)
			//进来的时候leftSum为0~i-1 的sum,leftRest=leftSum-(i-1-0+1)*average
			leftRest=leftSum-i*average;
			//leftSum+arr[i]+rightSum=sum,可知rightSum(i+1...N-1),进而求出rightRest=rightSum-( N-1 - (i+1) + 1 )*average
			rightRest=sum-arr[i]-leftSum-(N-1-i)*average;
			if (leftRest<0&&rightRest<0) ans = Math.max(ans, Math.abs(leftRest)+Math.abs(rightRest));
			else ans = Math.max(ans, Math.max(Math.abs(leftRest),Math.abs(rightRest)));
			leftSum+=arr[i];
		}
		return ans;
	}

	public static void main(String[] args) {
		int [] arr=new int[]{1,2,3,11,5,6,11,4,2};
		System.out.println(minOps(arr));
		System.out.println(minOps2(arr));
	}

}
