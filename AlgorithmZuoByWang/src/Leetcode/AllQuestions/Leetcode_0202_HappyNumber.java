package Leetcode.AllQuestions;

import java.util.HashSet;

public class Leetcode_0202_HappyNumber {

	/**
	 * 快乐数一定会是收敛或者循环
	 * @param n 返回n是不是快乐数
	 * @return -
	 */
	public static boolean isHappy(int n) {//通过set判断有环
		HashSet<Integer> set=new HashSet<>();
		while(n!=1&&!set.contains(n)){
			set.add(n);
			n=next(n);
		}
		return n==1;
	}
	public static boolean isHappy2(int n) {//通过快慢指针判断有环
		int s=n,f=n;
		do {
			s=next(s);
			f=next(next(f));
		}while (s!=f&&f!=1);
		return f==1;
	}

	public static int next(int n){
		int ans=0;
		while(n!=0){//从低位取
			int digit=n%10;
			ans+=digit*digit;
			n/=10;
		}
		return ans;
	}

	public static void main(String[] args) {
		System.out.println(next(19));
	}
}
