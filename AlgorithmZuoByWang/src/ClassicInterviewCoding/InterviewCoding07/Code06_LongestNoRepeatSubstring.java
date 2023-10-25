package ClassicInterviewCoding.InterviewCoding07;

import java.util.Arrays;

public class Code06_LongestNoRepeatSubstring {

	public static int maxUnique(String str) {//左的实现
		if (str == null || str.equals("")) {
			return 0;
		}
		char[] chas = str.toCharArray();
		// map 替代了哈希表   假设字符的码是0~255
		int[] map = new int[256];
		for (int i = 0; i < 256; i++) {
			map[i] = -1;
		}
		int len = 0;
		int pre = -1;
		int cur = 0;
		for (int i = 0; i != chas.length; i++) {
			pre = Math.max(pre, map[chas[i]]);
			cur = i - pre;
			len = Math.max(len, cur);
			map[chas[i]] = i;
		}
		return len;
	}

	/**
	 * dp[i]：必须以i位置结尾的子串有多长
	 * dp[i]：依赖dp[i-1]以及上一次出现s[i]字符的位置
	 * 注意：不是直接返回dp[N-1],需要遍历dp数组找答案
	 */
	public static int maxUnique2(String str) {
		if (str==null||str.equals("")) return 0;
		char[] s = str.toCharArray();
		int N=s.length;
		int[] map=new int[128];//记录此时每个字符出现的最晚的位置
		Arrays.fill(map, -1);
		int[] dp=new int[N];
		dp[0]=1;
		map[s[0]]=0;
		for (int i=1;i<N;i++){//上一次出现的地方要小于dp[i-1]能推到的地方
			dp[i]=map[s[i]]==-1||map[s[i]]<i-dp[i-1]?dp[i-1]+1:i-map[s[i]];
			map[s[i]]=i;
		}
		int res=Integer.MIN_VALUE;
		for (int i = 0; i < dp.length; i++) {
			res = Math.max(res, dp[i]);
		}
		return res;
	}

	public static int maxUnique3(String str) {//滑动窗口，我的实现
		if (str==null||str.equals("")) return 0;
		char[] s = str.toCharArray();
		int l=0;
		int r=0;//[l,r)，r代表下一个要进去的
		int max=0;
		boolean[] isExist=new boolean[128];
		while(r<str.length()){
			if (!isExist[s[r]]){//不是子串的一部分
				isExist[s[r]]=true;
				r++;
				max=Math.max(max,r-l);
			}else {//是子串的一部分,窗口左边界移动，窗口缩小
				isExist[s[l]]=false;
				l++;
			}
		}
		return max;
	}
	// for test
	public static String getRandomString(int len) {
		char[] str = new char[len];
		int base = 'a';
		int range = 'z' - 'a' + 1;
		for (int i = 0; i != len; i++) {
			str[i] = (char) ((int) (Math.random() * range) + base);
		}
		return String.valueOf(str);
	}

	// for test
	public static String maxUniqueString(String str) {
		if (str == null || str.equals("")) {
			return str;
		}
		char[] chas = str.toCharArray();
		int[] map = new int[256];
		for (int i = 0; i < 256; i++) {
			map[i] = -1;
		}
		int len = -1;
		int pre = -1;
		int cur = 0;
		int end = -1;
		for (int i = 0; i != chas.length; i++) {
			pre = Math.max(pre, map[chas[i]]);
			cur = i - pre;
			if (cur > len) {
				len = cur;
				end = i;
			}
			map[chas[i]] = i;
		}
		return str.substring(end - len + 1, end + 1);
	}

	//for test
	public static String generateRandomString(int size){//生成一个长度在[0~size]随机的字符串
		int realLen=(int) (Math.random()*(size+1));//该随机字符串的长度
		StringBuilder builder= new StringBuilder();
		for (int j = 0; j < realLen; j++) {//用随机字符拼出一个随机字符串
//			int ran=(int) (Math.random()*(25+1));//[0,25]随机，共26个字母
			int ran=(int) (Math.random()*(6));//[0,5]随机，共a,b,c,d,e,f 6个字母
			char c=(char) (ran+'a');//[a,z]
//                int ran=(int) (Math.random()*(127+1));//[0,127]随机，共128个字符
//                char c=(char) ran;
			builder.append(c);
		}
		return builder.toString();
	}

	//for test
	public static void test1(){//测试removeDuplicateLetters1和removeDuplicateLetters2的正确性
		int times=100000;
		int size=30;
		boolean isok=true;
		String f=generateRandomString(size);
		for (int i = 0; i < times; i++) {
			String s = generateRandomString(size);
			if (maxUnique3(s)!=maxUniqueString(s).length()){
				isok=false;
				f=s;
				break;
			}
		}
		System.out.println(f);
		System.out.println(maxUnique3(f));
		System.out.println(maxUniqueString(f).length());
		System.out.println(isok?"success":"fail");
	}

	public static void main(String[] args) {
		test1();
	}
}
