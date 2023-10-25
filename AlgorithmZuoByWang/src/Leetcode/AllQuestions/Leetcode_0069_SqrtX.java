package Leetcode.AllQuestions;

public class Leetcode_0069_SqrtX {

	// x一定非负，输入可以保证
	public static int mySqrt(int x) {//二分到底，找最右的平方之后小于x的数
		if (x==0) return 0;
		if (x<=3) return 1;
		long l=1;
		long r=x;
		while(l<=r){
			long mid=l+(r-l>>1);
			long v=  mid *mid;
			if (v<=x){
				l=mid+1;
			}else {
				r=mid-1;
			}
		}
		return (int)r;//r最后一次移动来到最佳位置
	}

}
