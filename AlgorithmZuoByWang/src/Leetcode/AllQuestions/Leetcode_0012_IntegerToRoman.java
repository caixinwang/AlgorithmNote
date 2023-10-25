package Leetcode.AllQuestions;

public class Leetcode_0012_IntegerToRoman {

	public static String intToRoman(int num) {
		//map[2][3]的值代表2000,2的含义是位上的数值，3代表是千位
		String[][] map=new String[][]{
				{"","","",""},//0就是空
				{"I","X","C","M"},//1,10,100,100
				{"II","XX","CC","MM"},
				{"III","XXX","CCC","MMM"},
				{"IV","XL","CD"},//不会超过4000
				{"V","L","D"},
				{"VI","LX","DC"},
				{"VII","LXX","DCC"},
				{"VIII","LXXX","DCCC"},
				{"IX","XC","CM"},//9,90,900
		};
		return map[num / 1000 % 10][3] +
				map[num / 100 % 10][2] +
				map[num / 10 % 10][1] +
				map[num % 10][0];
	}

}
