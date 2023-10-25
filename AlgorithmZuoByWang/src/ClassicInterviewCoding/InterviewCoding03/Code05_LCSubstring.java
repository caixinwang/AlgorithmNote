package ClassicInterviewCoding.InterviewCoding03;

public class Code05_LCSubstring {

	public static String lcst1(String str1, String str2) {//暴力
		if (str1 == null || str2 == null || str1.equals("") || str2.equals("")) {
			return "";
		}
		char[] chs1 = str1.toCharArray();
		char[] chs2 = str2.toCharArray();
		int[][] dp = getdp(chs1, chs2);
		int end = 0;
		int max = 0;
		for (int i = 0; i < chs1.length; i++) {
			for (int j = 0; j < chs2.length; j++) {
				if (dp[i][j] > max) {
					end = i - 1;//dp[i][j]对应的是str1[i-1]结尾和str2[j-1]结尾
					max = dp[i][j];
				}
			}
		}
		return str1.substring(end - max + 1, end + 1);
	}

	//dp [i] [j]的含义是同时以str1[i]位置结尾以及以str2[j]位置结尾，最长公共子串长度
	//不单独初始化，多一行一列进行统一。这种情况下dp[i][j]代表str1[i-1]结尾和str2[j-1]结尾
	public static int[][] getdp(char[] str1, char[] str2) {
		int row = str1.length, colum = str2.length;
		int[][] dp = new int[row + 1][colum + 1];
		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= colum; j++) {
				if (str1[i - 1] == str2[j - 1]) dp[i][j] = dp[i - 1][j - 1] + 1;
			}
		}
		return dp;
	}


	public static String lcst2(String s1, String s2) {//空间压缩
		if (s1 == null || s2 == null || s1.equals("") || s2.equals("")) {
			return "";
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int row = 0, colum = s2.length() - 1;//从右上角开始，沿着上边和左边走到左下角。不归一化了,因为只要一个变量，不需要数组
		int max = 0, end = 0;//max是最长子串长度，end是子串结束位置在str1的哪一个位置（不包含那个位置）
		while (row < str1.length) {//后半程是row在动，所以用row来作为停止的记号
			int len = 0, i = row, j = colum;
			while (i < str1.length && j < str2.length) {
				if (str1[i] == str2[j]) {
					len++;
				} else {
					if (len > max) {
						max = len;
						end = i;//停在了str1的i处，空心（不包含），这样可以直接利用subString函数
					}
					len = 0;
				}
				i++;
				j++;
			}
			if (colum > 0) colum--;//先左动再向下
			else row++;
		}
		return s1.substring(end-max,end);
	}

	public static void main(String[] args) {
		String str1 = "ABC1234567DEFG";
		String str2 = "HIJKL1234567MNOP";
		System.out.println(lcst1(str1, str2));
		System.out.println(lcst2(str1, str2));

	}

}