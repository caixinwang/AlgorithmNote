package Leetcode.AllQuestions;

import TestUtils.StringUtil;

/**
 * 和395题很像，一起来看。题目如下
 * 给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串， 要求该子串中的每一字符出现次数都不少于 k 。返回这一子串的长度。
 * 可以发现395中，你窗口增大到你阔不动了，但是右边仍有可能是你的答案，所以并没有r阔不动就可以移动L的单调性。
 * 因此这题我们采取的方法是固定字符的种类，反正只有小写字母，我最多算26次。如果固定了字符的种类，那么就有单调性了。
 * 因为固定了字符的种类，你扩大窗口扩到你阔不动了，你可以排除右边所有的答案，因为右边只有可能增加总数，所以单调性成立
 *
 * 而340题：给定一个字符串s，找出最多包含k个不同字符的最长子串的长度。
 * 这题的指标本身就是符合滑动窗口的，因为窗口增大到你阔不动，你也可以果断抛弃右边所有，因为右边再进来，字符种类数只可能重复更多。
 * 并且这题是要求最长的，所以是扩的时候更新答案
 */
public class Leetcode_0340_LongestSubstringWithAtMostKDistinctCharacters {

	public static int lengthOfLongestSubstringKDistinct(String s, int k) {
		if (s == null || s.length() == 0 || k < 1) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int[] count = new int[256];
		int diff = 0;
		int R = 0;
		int ans = 0;
		for (int i = 0; i < N; i++) {
			// R 窗口的右边界
			while (R < N && (diff < k || (diff == k && count[str[R]] > 0))) {
				diff += count[str[R]] == 0 ? 1 : 0;
				count[str[R++]]++;
			}
			// R 来到违规的第一个位置
			ans = Math.max(ans, R - i);
			diff -= count[str[i]] == 1 ? 1 : 0;
			count[str[i]]--;
		}
		return ans;
	}

	public static int lengthOfLongestSubstringKDistinct2(String s, int k) {
		if (s==null||s.length()==0) return 0;
		int ans=0;
		int l=0,r=0;//[l,r)
		int diff=0;//种类数
		int[] map=new int[128];//词频统计
		char[] str = s.toCharArray();
		while(l<str.length){
			while (r<str.length&&(diff<k||(diff==k&&map[str[r]]!=0))){//扩到不符合条件
				if (map[str[r]]++==0) diff++;
				r++;
				ans = Math.max(ans, r-l);
			}
//			if(!(r<str.length&&(diff<k||(diff==k&&map[str[r]]!=0)))){//缩到符合条件
				if (--map[str[l]]==0) diff--;
				l++;
//			}
		}
		return ans;
	}
	public int lengthOfLongestSubstringKDistinct3(String s, int k) {
		char[] str=s.toCharArray();
		int[] cnt=new int[128];
		int diff=0,ans=0,n=str.length;
		for(int l=0,r=0;r<n;r++){
			if(cnt[str[r]]++==0) diff++;
			while(diff>k) if(--cnt[str[l++]]==0) diff--;
			if(r-l+1>ans) ans=r-l+1;
		}
		return ans;
	}
	public static void testForString() {//参数为String
		StringUtil stringUtil = new StringUtil();
		int times = 100000;//测试次数
		long time1 = 0, time2 = 0;
		boolean isok = true;
		int maxSize = 10;//String长度在[0~maxSize]随机

        int parameter1=0;//测试函数的参数
        int maxParameter1=26;//参数1在[0,maxParameter1]随机

		String t1 = null;
//        String t2=null;

		int res1 = 0, res2 = 0;
		for (int i = 0; i < times; i++) {
            parameter1=stringUtil.ran(maxParameter1);
			t1 = stringUtil.generateRandom_a_z_String(stringUtil.ran(maxSize));
//        t2= stringUtil.generateRandom_a_z_String(stringUtil.ran(maxSize));

//        t1= stringUtil.generateRandom_all_String(stringUtil.ran(maxSize));
//        t2= stringUtil.generateRandom_all_String(stringUtil.ran(maxSize));

			long l = System.currentTimeMillis();
			res1 = lengthOfLongestSubstringKDistinct(t1,parameter1);
			time1 += System.currentTimeMillis() - l;
			l = System.currentTimeMillis();
			res2 = lengthOfLongestSubstringKDistinct(t1,parameter1);
			time2 += System.currentTimeMillis() - l;


			if (res1 != res2) {
				isok = false;
				break;
			}
		}
		System.out.println("t1:" + t1);
//        System.out.println("t2:"+t2);
//        System.out.println("parameter1:"+parameter1);//打印参数
		if (isok) System.out.println("m1 cost " + time1 + "ms");
		System.out.println(res1);//针对返回值的操作
		if (isok) System.out.println("m2 cost " + time2 + "ms");
		System.out.println(res2);//针对返回值的操作
		System.out.println(isok ? "success" : "fail");
	}

	public static void main(String[] args) {
		testForString();
	}

}
