package Leetcode.AllQuestions;

public class Leetcode_0875_KokoEatingBananas {
	//二份答案。定一个速度看看能不能吃完。对速度二分。

	final int MIN=1<<31,MAX=MIN-1;
	public int minEatingSpeed(int[] piles, int h) {
		int n=piles.length;
		if(h<n) return MAX;//就算吃的再快，也至少要n小时。
		int max=MIN;
		for(int num:piles) max=max(max,num);
		int l=1,r=max,mid;//最快的速度就是最大的香蕉堆
		while(l<=r){
			mid=l+(r-l>>1);
			if(can(piles,mid,h)){
				r=mid-1;
			}else{
				l=mid+1;
			}
		}
		return l;
	}

	public boolean can(int[] piles,int k,int h){//按照k的速度吃，能不能在h小时之内吃完
		long ans=0;//防止溢出！！！长个心眼
		for(int num:piles) ans+=(k-1+num)/k;
		return ans<=h;
	}

	public int max(int a,int b){return a>b?a:b;}

}
