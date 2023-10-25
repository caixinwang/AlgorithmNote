package ClassicInterviewCoding.InterviewCoding06;

public class Code02_ExpressionNumber {

	/**
	 * 几层过滤
	 * 1.捣乱的空串和null过滤
	 * 2.长度不是奇数的字符串过滤。也就是过滤长度为偶数的字符串
	 * 3.偶数下标都是0和1。奇数下标都是&^|,才合格，反之不合格，过滤。
	 *
	 * @param express 表达式
	 * @return 返回表达式合不合格
	 */
	public static boolean isValid(String express) {//大过滤器
		if (express == null || express.length() == 0) return false;
		if ((express.length() & 1) == 0) return false;//长度为偶数，过滤！
		char[] str = express.toCharArray();
		for (int i = 0; i < str.length; i += 2) {
			if (str[i] != '0' && str[i] != '1') return false;
		}
		for (int i = 1; i < str.length; i += 2) {
			if (str[i] != '&' && str[i] != '|' && str[i] != '^') return false;
		}
		return true;
	}


	public static int num1(String express, boolean desired) {
		if (!isValid(express)) return 0;
		return f(express.toCharArray(), desired, 0, express.length() - 1);
	}

	/**
	 * l和r的位置一定是压中数字的
	 *
	 * @param str     表达式
	 * @param desired 期望的值
	 * @param l       子表达式左边界
	 * @param r       子表达式右边界
	 * @return 返回子表达式有多少组合可以达到desired
	 */
	private static int f(char[] str, boolean desired, int l, int r) {
		if (l == r) {
			if (desired) {//1
				return str[l] == '1' ? 1 : 0;
			} else {//0
				return str[l] == '0' ? 1 : 0;
			}
		}
		int res = 0;
		if (desired) {//1
			for (int i = l + 1; i < r; i += 2) {//枚举每一个操作符位置
				switch (str[i]) {
					case '&':
						res += f(str, true, l, i - 1) * f(str, true, i + 1, r);
						break;
					case '|':
						res += f(str, true, l, i - 1) * f(str, true, i + 1, r)
								+ f(str, true, l, i - 1) * f(str, false, i + 1, r)
								+ f(str, false, l, i - 1) * f(str, true, i + 1, r);
						break;
					case '^':
						res += f(str, true, l, i - 1) * f(str, false, i + 1, r)
								+ f(str, false, l, i - 1) * f(str, true, i + 1, r);
						break;
				}
			}
		} else {//0
			for (int i = l + 1; i < r; i += 2) {//
				switch (str[i]) {
					case '&':
						res += f(str, false, l, i - 1) * f(str, false, i + 1, r)
								+ f(str, false, l, i - 1) * f(str, true, i + 1, r)
								+ f(str, true, l, i - 1) * f(str, false, i + 1, r);
						break;
					case '|':
						res += f(str, false, l, i - 1) * f(str, false, i + 1, r);
						break;
					case '^':
						res += f(str, false, l, i - 1) * f(str, false, i + 1, r)
								+ f(str, true, l, i - 1) * f(str, true, i + 1, r);
						break;
				}
			}
		}
		return res;
	}

	/**
	 * 通过暴力递归改的动态规划。观察暴力递归的f方法，发现有三个可变参数，但是有一个是布尔类型，所以我们准备两个二维表即可
	 * f函数中，带有布尔类型判断的就可以直接去掉判断，直接用tmap和fmap填表。f函数的其它部分都是填tmap[i][j]和fmap[i][j]的过程
	 * 观察f函数中的递归一来关系，发现map[i][j]位置依赖自己左边的和下边的。这样一来填map有三种方式：（依赖那边就从哪边开始）
	 * 第一种：从下往上（第一层for控制i--），从左往右（第二层for控制j++）
	 * 第二种：从左往右（第一层for控制j++），从下往上（第二层for控制i--）
	 * 第三种：沿着对角线填，第一层for控制起点(0,j)，第二层for控制往斜下走，(i,j+i)才是我们操作的点，而不是(i,j)
	 * 下面的方法采用的是第三种填法,尤其注意第二层for的判断条件是 i+j < N,第三层for判断条件是k < j+i
	 * @param express 表达式
	 * @param desired 期待的结果
	 * @return 返回子表达式有多少组合可以达到desired
	 */
	private static int dp(String express, boolean desired) {
		if (!isValid(express)) return 0;
		char[] str = express.toCharArray();
		int N = str.length;
		int[][] tmap = new int[N][N];
		int[][] fmap = new int[N][N];
		for (int i = 0; i < N; i+=2) {
			tmap[i][i] = str[i] == '1' ? 1 : 0;
			fmap[i][i] = str[i] == '0' ? 1 : 0;
		}
		for (int j = 2; j < N; j += 2) {//(0,j)作为斜线的起点，沿着斜线填写,起点每次跳两格
			for (int i = 0; i+j < N; i += 2) {//(i,j+i)是斜线上的点，每次往斜下跳两格因为i和j都必须是偶数
				for (int k = i + 1; k < j+i; k += 2) {//枚举每一个操作符位置
					switch (str[k]) {
						case '&':
							tmap[i][j+i] += tmap[i][k - 1] * tmap[k + 1][j+i];
							fmap[i][j+i] += fmap[i][k - 1] * tmap[k + 1][j+i]
									+tmap[i][k - 1] * fmap[k + 1][j+i]
									+fmap[i][k - 1] * fmap[k + 1][j+i];
							break;
						case '|':
							tmap[i][j+i] += tmap[i][k - 1] * tmap[k + 1][j+i]
									+ tmap[i][k - 1] * fmap[k + 1][j+i]
									+ fmap[i][k - 1] * tmap[k + 1][j+i];
							fmap[i][j+i] += fmap[i][k - 1] * fmap[k + 1][j+i];
							break;
						case '^':
							tmap[i][j+i] += tmap[i][k - 1] * fmap[k + 1][j+i]
									+ fmap[i][k - 1] * tmap[k + 1][j+i];
							fmap[i][j+i] += tmap[i][k - 1] * tmap[k + 1][j+i]
									+fmap[i][k - 1] * fmap[k + 1][j+i];
							break;
					}
				}
			}
		}
		return desired?tmap[0][N-1]:fmap[0][N-1];
	}

	/**
	 * 通过暴力递归改的动态规划。观察暴力递归的f方法，发现有三个可变参数，但是有一个是布尔类型，所以我们准备两个二维表即可
	 * f函数中，带有布尔类型判断的就可以直接去掉判断，直接用tmap和fmap填表。f函数的其它部分都是填tmap[i][j]和fmap[i][j]的过程
	 * 观察f函数中的递归一来关系，发现map[i][j]位置依赖自己左边的和下边的。这样一来填map有三种方式：（依赖那边就从哪边开始）
	 * 第一种：从下往上（第一层for控制i--），从左往右（第二层for控制j++）
	 * 第二种：从左往右（第一层for控制j++），从下往上（第二层for控制i--）
	 * 第三种：沿着对角线填，第一层for控制起点(0,j)，第二层for控制往斜下走，(i,j+i)才是我们操作的点，而不是(i,j)
	 * 下面的方法采用的是第二种填法
	 * @param express 表达式
	 * @param desired 期待的结果
	 * @return 返回子表达式有多少组合可以达到desired
	 */
	private static int dp2(String express, boolean desired) {
		if (!isValid(express)) return 0;
		char[] str = express.toCharArray();
		int N = str.length;
		int[][] tmap = new int[N][N];
		int[][] fmap = new int[N][N];
		for (int i = 0; i < N; i+=2) {
			tmap[i][i] = str[i] == '1' ? 1 : 0;
			fmap[i][i] = str[i] == '0' ? 1 : 0;
		}
		for (int j = 2; j < N; j += 2) {//从左往右固定列号，j起始为0+2=2
			for (int i = N-3; i>=0; i -= 2) {//从下往上依次填写，i起始位N-1-2=N-3
				for (int k = i + 1; k < j; k += 2) {//枚举每一个操作符位置
					switch (str[k]) {
						case '&':
							tmap[i][j] += tmap[i][k - 1] * tmap[k + 1][j];
							fmap[i][j] += fmap[i][k - 1] * tmap[k + 1][j]
									+tmap[i][k - 1] * fmap[k + 1][j]
									+fmap[i][k - 1] * fmap[k + 1][j];
							break;
						case '|':
							tmap[i][j] += tmap[i][k - 1] * tmap[k + 1][j]
									+ tmap[i][k - 1] * fmap[k + 1][j]
									+ fmap[i][k - 1] * tmap[k + 1][j];
							fmap[i][j] += fmap[i][k - 1] * fmap[k + 1][j];
							break;
						case '^':
							tmap[i][j] += tmap[i][k - 1] * fmap[k + 1][j]
									+ fmap[i][k - 1] * tmap[k + 1][j];
							fmap[i][j] += tmap[i][k - 1] * tmap[k + 1][j]
									+fmap[i][k - 1] * fmap[k + 1][j];
							break;
					}
				}
			}
		}
		return desired?tmap[0][N-1]:fmap[0][N-1];
	}


	public static void main(String[] args) {
		String express = "1^0&0|1&1^0&0^1|0|1&1";
		boolean desired = true;
		System.out.println(num1(express, desired));
		System.out.println(dp(express, desired));
		System.out.println(dp2(express, desired));
	}
}
