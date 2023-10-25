package Leetcode.AllQuestions;

public class Leetcode_0483_SmallestGoodBase {

	//二分求答案，二分什么？二分位数。2进制全2的位数一定是最高的。
	public static String smallestGoodBase(String n) {
		long num = Long.parseLong(n);
		// n这个数，需要从m位开始试，固定位数，一定要有m位！
		for (int m = (int) (Math.log(num + 1) / Math.log(2)); m > 2; m--) {
			// num开m次方
			long l = (long) (Math.pow(num, 1.0 / m));
			long r = (long) (Math.pow(num, 1.0 / (m - 1))) + 1L;
			while (l <= r) {
				long k = l + ((r - l) >> 1);
				long sum = 0L;
				long base = 1L;
				for (int i = 0; i < m && sum <= num; i++) {
					sum += base;
					base *= k;
				}
				if (sum < num) {
					l = k + 1;
				} else if (sum > num) {
					r = k - 1;
				} else {
					return String.valueOf(k);
				}
			}
		}
		return String.valueOf(num - 1);
	}

	//1+x+x^2+...+x^n = (x^n+1 - 1)/(x-1)
	//log10(x)上取整就是位数，下取整就是最大的base
	//log(a,b)=lnb/lna
	//如果规定位数为k位，那么要求进制r, r^k-1<=num  r^k>num
	public static String smallestGoodBase2(String n) {//long最大是9*10^18
		long num=Long.parseLong(n);
		for (long bits=(long)(Math.log(num+1)/Math.log(2));bits>=2;bits--){
			long l=(long)Math.pow(num,1d/(bits)),r=(long)Math.pow(num,1d/(bits-1))+1,x;//进制的范围
			while(l<=r){//x进制
				x=l+(r-l>>1);//x进制
				long compute=0;
				for (long base=1,cnt=1;cnt<=bits&&compute<=num;cnt++,base*=x){
					compute+=base;
				}
				if (compute==num) {
					return String.valueOf(x);
				}else if (compute>num){
					r=x-1;
				}else {
					l=x+1;
				}
			}
		}
		return String.valueOf(num-1);
	}

	public static void main(String[] args) {
		for (long i=1;i<10000;i+=1){
			String ans1=smallestGoodBase(String.valueOf(i));
			String ans2=smallestGoodBase2(String.valueOf(i));
			if (!ans1.equals(ans2)){
				System.out.println(i);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("========");
			}
		}
	}

}
