package Leetcode.AllQuestions;

import java.util.HashMap;

public class Leetcode_0166_FractionToRecurringDecimal {

	public static String fractionToDecimal(int numerator, int denominator) {
		long n1=numerator,n2=denominator;//转成long来做，否则需要考虑边界条件
		if (n1%n2==0) return String.valueOf(n1/n2);
		boolean neg=(n1<0^n2<0);//符号不同，结果为负
		n1=n1<0?-n1:n1;
		n2=n2<0?-n2:n2;
		long part1=n1/n2;//整数部分
		long remainder=n1%n2;//余数
		StringBuilder part2=new StringBuilder();//小数部分
		HashMap<Long,Integer> map=new HashMap<>();//确定remainder重复的位置,用余数做key，而不是用商做key
		int i=0;
		while(remainder!=0&&!map.containsKey(remainder)){
			map.put(remainder,i++);
			remainder*=10;
			long digit=remainder/n2;
			part2.append(digit);
			remainder%=n2;
		}
		if (map.containsKey(remainder)){//无限循环小数
			part2.insert(map.get(remainder),"(");
			part2.append(")");
		}
		return (neg?"-":"")+part1+"."+ part2;
	}

	public static void main(String[] args) {
		System.out.println(fractionToDecimal(4,3333));
		System.out.println(fractionToDecimal(-50,8));
		System.out.println(fractionToDecimal(-1,Integer.MIN_VALUE));
		System.out.println(4d/3333d);
	}

}
