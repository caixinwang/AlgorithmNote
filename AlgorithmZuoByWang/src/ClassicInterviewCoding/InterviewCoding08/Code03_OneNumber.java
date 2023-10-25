package ClassicInterviewCoding.InterviewCoding08;

public class Code03_OneNumber {

	public static int solution1(int num) {
		if (num < 1) {
			return 0;
		}
		int count = 0;
		for (int i = 1; i != num + 1; i++) {
			count += get1Nums(i);
		}
		return count;
	}

	public static int get1Nums(int num) {
		int res = 0;
		while (num != 0) {
			if (num % 10 == 1) {
				res++;
			}
			num /= 10;
		}
		return res;
	}

	public static int solution2(int num) {
		if (num<1) return 0;
		if (num<10) return 1;
		//618943  -> len=5
		int len = getLenOfNum(num);
		//6
		int high=getKDigit(num,len);//获取最高位数字
		int partHigh=high==1?num/10+1:powerBaseOf10(len-1);
		//去掉最高位再固定一位，总的只有10^len-2，并且由除了最高位都可以固定，所以乘以len-1
		int partOther=high*powerBaseOf10(len-2)*(len-1);
		//num/10是拿掉第一位，num%powerBaseOf10(len-1)是拿掉最高位
		return partOther+partHigh+solution2(num%powerBaseOf10(len-1));

	}

	public static int getLenOfNum(int num) {
		int len = 0;
		while (num != 0) {
			len++;
			num /= 10;
		}
		return len;
	}

	public static int getKDigit(int num,int k){//得到num的第k位，k从1开始记，个位是1
		if (k<1) return -1;
		if (k==1) return num%10;
		while(k!=1) {
			num /= 10;
			k--;
		}
		return num%10;
	}

	public static int powerBaseOf10(int base) {
		return (int) Math.pow(10, base);
	}

	public static void main(String[] args) {
		int num = 50000000;
		long start1 = System.currentTimeMillis();
		System.out.println(solution1(num));
		long end1 = System.currentTimeMillis();
		System.out.println("cost time: " + (end1 - start1) + " ms");

		long start2 = System.currentTimeMillis();
		System.out.println(solution2(num));
		long end2 = System.currentTimeMillis();
		System.out.println("cost time: " + (end2 - start2) + " ms");

		System.out.println(getKDigit(587697,6));
	}
}
