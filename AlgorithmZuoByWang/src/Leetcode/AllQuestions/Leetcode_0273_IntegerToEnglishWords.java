package Leetcode.AllQuestions;

import TestUtils.ArrayUtil;
import TestUtils.StringUtil;

public class Leetcode_0273_IntegerToEnglishWords {
	//思路：英文中，  xxx,xxx,xxx 是三个一波。我们把3个3个怎么读搞定，接下来就是往后面加单位。
	//xxx怎么搞定？百位数比较简单，有百位的话后面加一个hundred就可以搞定。麻烦的是0~99怎么搞定？
	//0~99中，0~19都是不同的单词。20~99中，20、30、40、50这些又都是不同的单词。
	static String[] reads0_20=new String[]{
			"","One ","Two ","Three ","Four ","Five ","Six ","Seven ","Eight ","Nine ","Ten ",
			"Eleven ","Twelve ","Thirteen ","Fourteen ","Fifteen ","Sixteen ","Seventeen ","Eighteen ","Nineteen ","Twenty "
	};
	static String[] reads10_90=new String[]{
			"","Ten ","Twenty ","Thirty ","Forty ","Fifty ","Sixty ","Seventy ","Eighty ","Ninety "
	};
	static String[] reads_danwei=new String[]{
			"","","Thousand ","Million ","Billion "
	};
	public static String numberToWords(int num) {
		if (num==0) return "Zero";
		String s=String.valueOf(num);
		int n=s.length(),parts=(n+2)/3;//总共分parts波，向上取整
		String[] reads=new String[parts];
		for (int i = parts-1,j=0; i >= 0; i--,j++) {
			reads[i]=s.substring(Math.max(0,n-3*j-3),n-3*j);
		}
		StringBuilder sb=new StringBuilder();
		for (int i = 0,j=parts; i < reads.length; i++,j--) {
			if (Integer.parseInt(reads[i])==0) continue;
			sb.append(read0_999(reads[i])).append(reads_danwei[j]);
		}
		return sb.toString().trim();//去掉最后的空格
	}

	public static String read0_999(String s){//下标012
		if (s.length()<3) s=new StringBuilder(s).insert(0,"0".repeat(3-s.length())).toString();
		StringBuilder sb=new StringBuilder();
		if (s.charAt(0)!='0'){
			sb.append(reads0_20[s.charAt(0)-'0']).append("Hundred ");
		}
		int low_tow=Integer.parseInt(s.substring(1,3));
		if (low_tow<=20){
			sb.append(reads0_20[low_tow]);
		}else{
			sb.append(reads10_90[low_tow/10]).append(reads0_20[low_tow%10]);
		}
		return sb.toString();
	}

	static ArrayUtil au=new ArrayUtil();
	public static void main(String[] args) {
		numberToWords(1234567895);
		for (int i = 0; i <= 1_999_999_999 ; i+= au.ran(1,1000000)) {
			System.out.printf("数值%d读作:%s\n",i,numberToWords(i));
		}
	}

}
