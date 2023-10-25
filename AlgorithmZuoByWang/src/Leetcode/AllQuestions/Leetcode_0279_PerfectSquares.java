package Leetcode.AllQuestions;

public class Leetcode_0279_PerfectSquares {

	public static int numSquares1(int n) {//打表找规律
		int res=n;
		for (int i = 2; i*i <=n; i++) {
			int p1=n/(i*i);
			int remainder=n%(i*i);
			res=Math.min(res,p1+numSquares1(remainder));
		}
		return res;
	}

	// 1 : 1, 4, 9, 16, 25, 36, ...
	// 4 : 7, 15, 23, 28, 31, 39, 47, 55, 60, 63, 71, ...
	// 规律解
	// 规律一：个数不超过4
	// 规律二：出现1个的时候，显而易见
	// 规律三：任何数 % 8 == 7，一定是4个
	// 规律四：任何数消去4的因子之后，剩下rest，rest % 8 == 7，一定是4个
	public static int numSquares2(int n) {
		int rest = n;
		while (rest % 4 == 0) {//消去4的因子
			rest /= 4;
		}
		if (rest % 8 == 7) {//4的情况是消去4的因子之后，%8为7
			return 4;
		}
		int f = (int) Math.sqrt(n);
		if (f * f == n) {//1的情况显然
			return 1;
		}
		for (int first = 1; first * first <= n; first++) {//2的情况去枚举
			int second = (int) Math.sqrt(n - first * first);
			if (first * first + second * second == n) {
				return 2;
			}
		}
		return 3;//3的情况通过排除
	}

	// 数学解
	// 1）四平方和定理
	// 2）任何数消掉4的因子，结论不变
	public static int numSquares3(int n) {
		while (n % 4 == 0) {//消掉4的因子
			n /= 4;
		}
		if (n % 8 == 7) {
			return 4;
		}
		for (int a = 0; a * a <= n; ++a) {
			int b = (int) Math.sqrt(n - a * a);
			if (a * a + b * b == n) {
				return (a > 0 && b > 0) ? 2 : 1;
			}
		}
		return 3;
	}

	public static void main(String[] args) {
		for (int i = 1; i < 1000; i++) {
			System.out.println(i + " , " + numSquares1(i));
		}
	}

}
