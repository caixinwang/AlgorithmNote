package Leetcode.AllQuestions;

public class Leetcode_0335_SelfCrossing {
	//到i位置的时候，枚举和前面的i-1,i-2,i-3,...会不会撞。最多枚举到i-5，因为发现i-5和这里的i是对称的情况

	public static boolean isSelfCrossing2(int[] x) {
		int n=x.length;
		for (int i = 3; i < x.length; i++) {//至少前面有三条才有可能相交
			if (x[i-2]<=x[i]&&x[i-3]>=x[i-1]) return true;
			if (i>=4&&x[i-2]>x[i]&&x[i-3]==x[i-1]&&x[i-4]+x[i]>=x[i-2]) return true;
			if (i>=5&&x[i-2]>x[i]&&x[i-3]>x[i-1]&&x[i-4]+x[i]>=x[i-2]&&x[i-4]<=x[i-2]&&x[i-5]+x[i-1]>=x[i-3]) return true;
		}
		return false;
	}


}
