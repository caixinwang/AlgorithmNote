package Leetcode.AllQuestions;

/**
 * 题目：给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串， 要求该子串中的每一字符出现次数都不少于 k 。返回这一子串的长度。
 *
 * 利用题目本身的限制，只可能有小写字母，一种可能的做法就是去枚举包含的字母种类，总的也就26种，每种去算一个答案。算的方法就是滑动窗口。
 * 因为只有把种类限制死 滑动窗口才有单调性。否则不限制死，是没有单调性的！！
 * 假设试到m种已经没答案了，那么m往后你也不用试了，一定没有答案！
 * 固定了种数之后，那么你就有一个欠账表map以及一个欠账数all=types*k。利用76题的解法
 * 或者下面还有一种方法，依然是map，但是用collected表示收集了几种，用satisfy表示收集到的有多少达到k次了。
 *
 * 这题和340题的相似点在于，如果你固定了最多出现m个不同的字符，其实就是340题多加了达到了k次之后才结算答案
 */
public class Leetcode_0395_LongestSubstringWithAtLeastKRepeatingCharacters {//340

	public int longestSubstring(String s, int k) {
		int ans=0,n=s.length();
		char[] str=s.toCharArray();
		for(int types=1;types<=26;types++){//恰好包含types种字符
			int[] cnt=new int[128];
			int diff=0,dubt=types*k;
			for(int l=0,r=0;r<n;){
				while(diff==types&&cnt[str[r]]==0){//种数要超了，就缩
					if(cnt[str[l]]--<=k) {//缩之前如果<=k，那么说明是在还债的范围内，没有多还
						dubt++;
						if(cnt[str[l]]==0) diff--;//如果减到0了种了，说明种类也要减少
					}
					l++;
				}
				if(++cnt[str[r]]<=k) {//加完之后<=k说明是有效还款
					dubt--;
					if(cnt[str[r]]==1) diff++;//加完之后是1说明有新的类型了
				}
				r++;
				if(dubt==0&&r-l>ans) ans=r-l;
			}
		}
		return ans;
	}

	//事实上如果某一次m压根没有收集过答案，那么也没有必要继续下去了，优化常数时间。
	public static int longestSubstring2(String s, int k) {
		if (s==null||s.length()==0) return 0;
		int ans=0;
		char[] str=s.toCharArray();
		for (int m=1;m<=26;m++){//for循环里就是340题的变种,字符种类数量最多到m
			int l=0,r=0;
			int diff=0,satisfy=0;//satisfy就是比340题多的门槛
			int[] map=new int[128];
			while(l<str.length){//r阔的时候结算答案，所以条件按在l上
				while (r<str.length&&(diff<m||(diff==m&&map[str[r]]!=0))){
					if (map[str[r]]==0) diff++;
					if (++map[str[r]]==k) satisfy++;
					r++;
					if (satisfy==diff) ans = Math.max(ans, r-l);//答案的更新变为有门槛了
				}
				if (map[str[l]]==1) diff--;
				if (map[str[l]]--==k) satisfy--;
				l++;
			}
		}
		return ans;
	}

	public static int longestSubstring3(String s, int k) {
		if (s==null||s.length()==0) return 0;
		char[] str=s.toCharArray();
		int ans=0,n=str.length;
		for (int m=1;m<=26;m++){//恰好有m种字符至少重复了k次
			int all=m*k,diff=0;//同时维护欠账和种数两个字段
			int[] cnt=new int[128];
			for (int l=0,r=0;l<n;){//固定l,r能扩就扩
				while(r<n&&diff<m){//先搞定种数
					if (cnt[str[r]]<k) all--;
					if (cnt[str[r]]==0) diff++;
					cnt[str[r++]]++;
				}
				while(r<n&&diff==m&&cnt[str[r]]!=0){//保持种数的情况下，让all尽量的减小
					if (cnt[str[r++]]++<k) all--;
				}
				if (all==0&&r-l>ans) ans=r-l;
				if (cnt[str[l]]==1) diff--;
				if (cnt[str[l]]<=k) all++;
				cnt[str[l++]]--;
			}
		}
		return ans;
	}

	public static int longestSubstring6(String s, int k) {
		if (s==null||s.length()==0) return 0;
		char[] str=s.toCharArray();
		int ans=0,n=str.length;
		for (int m=1;m<=26;m++){//恰好有m种字符至少重复了k次
			int all=m*k,diff=0;//同时维护欠账和种数两个字段
			int[] cnt=new int[128];
			for (int l=0,r=0;l<n;){//固定l,r能扩就扩
				while(r<n&&!(diff==m&&cnt[str[r]]==0)){//先搞定种数,再搞定个数（扩到即将不满足）
					if (cnt[str[r]]<k) all--;
					if (cnt[str[r]]==0) diff++;
					cnt[str[r++]]++;
				}
				if (all==0&&r-l>ans) ans=r-l;
				if (cnt[str[l]]==1) diff--;
				if (cnt[str[l]]<=k) all++;
				cnt[str[l++]]--;
			}
		}
		return ans;
	}

	public static int longestSubstring4(String s, int k) {
		if (s==null||s.length()==0) return 0;
		char[] str=s.toCharArray();
		int ans=0,n=str.length;
		for (int m=1;m<=26;m++){//恰好有m种字符至少重复了k次
			int all=m*k,diff=0;//同时维护欠账和种数两个字段
			int[] cnt=new int[128];
			for (int l=0,r=0;r<n;){//固定r，只有在种数要超了我才缩
				if (cnt[str[r]]==0) diff++;//下面三句是让这次固定r进窗口，维护diff和all
				if (cnt[str[r]]<k) all--;
				cnt[str[r++]]++;
				while(l<n&&diff>m){//种数超,我必须得缩小窗口了
					if (cnt[str[l]]==1) diff--;
					if (cnt[str[l]]<=k) all++;
					cnt[str[l++]]--;
				}
				if (all==0&&r-l>ans) ans=r-l;
			}
		}
		return ans;
	}

	public static int longestSubstring5(String s, int k) {
		if (s==null||s.length()==0) return 0;
		char[] str=s.toCharArray();
		int ans=0,n=str.length;
		for (int m=1;m<=26;m++){//恰好有m种字符至少重复了k次
			int all=m*k,diff=0;//同时维护欠账和种数两个字段
			int[] cnt=new int[128];
			for (int l=0,r=0;r<n;){//固定r，只有在种数要超了我才缩
				while(l<n&&diff==m&&cnt[str[r]]==0){//种数快超就缩
					if (cnt[str[l]]==1) diff--;
					if (cnt[str[l]]<=k) all++;
					cnt[str[l++]]--;
				}
				if (cnt[str[r]]==0) diff++;//下面三句是让这次固定r进窗口，维护diff和all
				if (cnt[str[r]]<k) all--;
				cnt[str[r++]]++;
				if (all==0&&r-l>ans) ans=r-l;
			}
		}
		return ans;
	}


}
