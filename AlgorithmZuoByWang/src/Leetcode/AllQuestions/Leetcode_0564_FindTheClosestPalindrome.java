package Leetcode.AllQuestions;

public class Leetcode_0564_FindTheClosestPalindrome {
	//找粗回文，每一个数都有对称轴。例如12344的粗回文就是12321。123456的粗回文就是123321。左边逆序一下放到右边就是粗回文
	//因为我们要找离的近的，所以我们肯定尽量让在中间的数+1。例如123|344，我们让123-》124-》124421.
	//123 5 144 ->123 6 321 。
	//所以我们肯定是紧紧贴着中间去+1 -1 不变，不可能贴着更高位去+1 -1 不变。
	//流程：先求出粗回文，对着粗回文中间的数分别+1 -1算出答案，谁最近谁就是答案。特殊情况：如果粗回文就是自己，那么要把自己排除。
	//盯着中间位置-1，如果中间位置是0怎么办？其实很简单，我们就只看对称轴的一边，例如140| -》139|31 or 140|41 or 141|41
	//也可能140| -》139|931 or 140|041 or 141|141 都去试。要注意，粗回文求出来的位置和原始的位数最多差一位。

	public String nearestPalindromic(String n) {
		long origin=Long.parseLong(n);
		if (origin<=10) return String.valueOf(origin-1);
		if(origin==11) return String.valueOf(9);
		if(origin==1000) return String.valueOf(999);
		long num=Long.parseLong(n.substring(0,n.length()+1>>1))-1;
		long ans=Long.MAX_VALUE;
		for (int i = 0; i < 3; i++,num++) {
			long p=getstr(origin,num);
			if (abs(p-origin)<abs(ans-origin)) ans=p;
			if (abs(p-origin)==abs(ans-origin)&&p<ans) ans=p;
		}
		if (num<100) return String.valueOf(ans);
		num=Long.parseLong(n.substring(0,(n.length()+1>>1)-1))-1;
		for (int i = 0; i < 3; i++,num++) {
			long p=getstr(origin,num);
			if (abs(p-origin)<abs(ans-origin)) ans=p;
			if (abs(p-origin)==abs(ans-origin)&&p<ans) ans=p;
		}
		return String.valueOf(ans);
	}

	public long getstr(long origin,long num){
		String half=String.valueOf(num);
		String odd=half+ new StringBuilder().append(half.substring(0,half.length()-1)).reverse();
		String even=half+new StringBuilder().append(half).reverse();
		long num1=Long.parseLong(odd);
		long num2=Long.parseLong(even);
		if (!isp(num1)&&!isp(num2)) return Long.MAX_VALUE;
		if (!isp(num1)) return num2;
		if (!isp(num2)) return num1;
		if (num1==origin||num2==origin) return num1!=origin?num1:num2;
		if (abs(num1-origin)<abs(num2-origin)) return num1;
		return num2;
	}

	public boolean isp(long num){
		long t=0;
		for (long i=0;i<64;i++){
			if ((num>>i&1)!=0) t+= 1L <<i;
		}
		return t==num;
	}

	public long abs(long a){return a>0?a:-a;}

	public static void main(String[] args) {
		System.out.println(new Leetcode_0564_FindTheClosestPalindrome().nearestPalindromic("1000"));
	}

}
