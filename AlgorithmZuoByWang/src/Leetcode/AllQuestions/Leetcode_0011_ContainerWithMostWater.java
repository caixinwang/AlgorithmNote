package Leetcode.AllQuestions;

public class Leetcode_0011_ContainerWithMostWater {

	/**
	 * 思路：贪心，从中间往两边靠，结算矮的柱子--瓶颈的柱子
	 * 必须以i位置作为瓶颈（是小的）所能达到的最大值。i位置作为瓶颈，其实就是到左右两边找大于等于它最远的杆。看看左右两边哪个最大。
	 * 从最左的杆子和最右的杆子往中间靠，如果a杆子比较小，那么b杆子肯定是a作为瓶颈时候选的最优解，因为离得最远。
	 * 也就是说，我们不是按照0~i这样的顺序结算每个杆作为瓶颈时候的最大值，而是按照从两边往中间缩的顺序去结算最大值，
	 * 这是一个贪心，因为我们知道肯定最远的杆子是最好的选择，我们舍弃了一些可能性。
	 * @param h 柱子高度
	 * @return 选两根柱子，返回最大容积
	 */
	public static int maxArea(int[] h) {
		int res=0;
		int l=0,r=h.length-1;
		while(l<r){
			if (h[l]<=h[r]){//谁小结算谁
				res = Math.max(res, h[l]*(r-l));
				l++;
			}else {
				res = Math.max(res, h[r]*(r-l));
				r--;
			}
		}
		return res;
	}

}
