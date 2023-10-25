package ClassicInterviewCoding.InterviewCoding14;

import TestUtils.ArrayUtil;

public class Code01_SplitNumber {

	/**
	 * 暴力递归做法。暴力递归的时候依赖左边裂开到的最大值，所以需要多加一个参数
	 * @param number  求number裂开有几种方法
	 * @return 裂开有几种方法
	 */
	public static int ways1(int number){
		if (number<1) return -1;
		return process1(1,number);
	}

	/**
	 *
	 * @param preMax 之前左边裂开到的最大值
	 * @param rest 还剩下rest要去裂开，裂开的块都不能比prMax小
	 * @return 返回在有preMax的限制下，rest能有几种方法裂开
	 */
	private static int process1(int preMax, int rest) {
		if (rest==0) return 1;
//		if (rest<preMax) return 0;//这句不加也行，包括在下面for循环里面
		//下面就是rest>preMax，去枚举
		int res=0;
		for (int i=preMax;i<=rest;i++){
			res+=process1(i,rest-i);//此时rest选择裂出一个i
		}
		return res;
	}

	//递归依赖自己下面和左边的值，所以从左往右从下往上填
	public static int ways2(int number){//改动态规划
		if (number<1) return -1;
		int[][] dp=new int[number+1][number+1];//[preMax][rest]
		for (int i = 0; i < dp.length; i++) {
			dp[i][0]=1;
		}
		for (int preMax = number; preMax >=1; preMax--) {//preMax>=1,从下往上,第一行无意义不管
			for (int rest=1;rest<=number;rest++){//rest,从左往右
				for (int i=preMax;i<=rest;i++){//枚举行为
					//观察到，rest不动，如果preMax大1，那么就会少加一个
					dp[preMax][rest]+=dp[i][rest-i];
				}
			}
		}
		return dp[1][number];
	}

	/**
	 * 这里需要对for循环的条件做一些些修改，由于我们的暴力递归的没去限制rest和preMax的关系，
	 * 这一层的关系是在for循环控制的。但是现在我们省去枚举行为，这个逻辑就消失了，需要我们自己去for循环里面
	 * 通过我们人工的干预，只填有意义的格子,也就是rest初始值设置为preMax
	 * 总结一下：最好还是在递归就做好条件的过滤，这样你在改动态规划的时候就会先初始化。你就知道从哪里开始填写了
	 */
	public static int ways3(int number){//省枚举
		if (number<1) return -1;
		int[][] dp=new int[number+1][number+1];//[preMax][rest]
		for (int i = 0; i < dp.length; i++) {
			dp[i][0]=1;
		}
		for (int preMax = number; preMax >=1; preMax--) {//preMax>=1,从下往上,第一行无意义不管
			for (int rest=preMax;rest<=number;rest++){//rest,从左往右
				dp[preMax][rest]=(preMax+1<=number?dp[preMax+1][rest]:0)+dp[preMax][rest-preMax];
			}
		}
		return dp[1][number];
	}

	public static void testForArr() {//参数为arr
		ArrayUtil arrayUtil = new ArrayUtil();
		int times = 1000;//测试次数
		long time1=0,time2=0;
		boolean isok = true;
		int maxSize = 100;//数组大小在[0~maxSize]随机
		int maxValue = 500;//数组的值在[0,maxValue]随机
//        int parameter1=0;//测试函数的参数
//        int maxParameter1=100;//参数1在[0,maxParameter1]随机
//		int[] t1 = null, t2 = null;
		int t1=0,r2=0;

		int res1 = 0, res2 = 0;
		for (int i = 0; i < times; i++) {

//            parameter1=arrayUtil.ran(maxParameter1);

			t1 = arrayUtil.ran(1,maxValue);
//            t2=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),maxValue);

//            t1=arrayUtil.generateRandomArr(arrayUtil.ran(maxSize),1,maxValue);//正数数组[1,maxValue]
//            t2 = arrayUtil.generateRandomArr(arrayUtil.ran(maxSize), 1, maxValue);//正数数组[1,maxValue]

			long l = System.currentTimeMillis();
			res1 = ways2(t1);
			time1+=System.currentTimeMillis()-l;
			l=System.currentTimeMillis();
			res2 = ways3(t1);
			time2+=System.currentTimeMillis()-l;
			if (res1 != res2) {
				isok = false;
				break;
			}
		}
//		arrayUtil.printArr(t1);//打印参数
		System.out.println("t1 = " + t1);
//        arrayUtil.printArr(t2);//打印参数
//        System.out.println("parameter1:"+parameter1);//打印参数
		if (isok) System.out.println("m1 cost "+time1+"ms");
		System.out.println(res1);//针对返回值的操作
		if (isok)System.out.println("m2 cost "+time2+"ms");
		System.out.println(res2);//针对返回值的操作
		System.out.println(isok ? "success" : "fail");
	}

	public static void main(String[] args) {
//		System.out.println(ways1(20));
//		System.out.println(ways2(20));
//		System.out.println(ways3(20));
		testForArr();
	}
}
