package Leetcode.AllQuestions;

import java.util.LinkedList;

public class Leetcode_0683_KEmptySlots {
	//窗口在最大值与最小值的更新结构
	//对原始的数据进行变换！将原始数据变成，每一个灯泡在什么时候亮的。
	//窗口长度是4，代表的是窗口里面的灯都是没亮的。假设窗口是[l,r],l-1位置以及r+1位置中的最大值一定要大于窗口内的最小值，因为只有这样，
	//窗口全灭的含义才能维持住。使用窗口需要O(K+N)的空间，O(N)的复杂度
	//最优解：空间复杂度O(N)。使用的同向的三指针，这三个指针都不回退。在mid前进的时候，也宣告了以mid位置作为left必然是失败的。

	//将bulbs转化为lights,表示每盏灯亮的时机
	//开一个k大小的窗口l~r,如果窗口内的最小值min>max{[l-1],[r+1]},那么max{[l-1],[r+1]}就是其中的答案
	final int MIN=1<<31,MAX=MIN-1;
	public int kEmptySlots(int[] bulbs, int k) {
		int n=bulbs.length;
		int[] lights=new int[n];
		for(int day=0;day<n;day++){
			lights[bulbs[day]-1]=day+1;//天数从1开始编号
		}
		int l=1,r=1,ans=MAX;
		LinkedList<Integer> dq=new LinkedList<>();//最小值的更新结构
		if(k==0){//说明不需要窗口,本质上就是找两两相连中的最大值中的最小值
			for(int i=0;i<n-1;i++){
				ans=min(ans,max(lights[i],lights[i+1]));
			}
			return ans;
		}
		for(int i=0;i<k-1&&r<n;i++){//窗口先进k-1个,以后都是进一个结算一次，然后出一个
			while(!dq.isEmpty()&&lights[r]<dq.peekLast()) dq.pollLast();
			dq.addLast(lights[r++]);//更新结构放的是值，因为值不会重复
		}
		for(;r<n-1;r++){
			while(!dq.isEmpty()&&lights[r]<dq.peekLast()) dq.pollLast();
			dq.addLast(lights[r]);//进一个
			if(dq.peekFirst()>max(lights[l-1],lights[r+1]))
				ans=min(ans,max(lights[l-1],lights[r+1]));//结算答案
			if(dq.peekFirst()==lights[l++]) dq.pollFirst();//出一个
		}
		return ans==MAX?-1:ans;
	}
	public int kEmptySlots2(int[] bulbs, int k) {//有单调性。如果mid失败了，l下一个位置不能是l+1~mid-1,只能是mid
		int n=bulbs.length;
		int[] lights=new int[n];
		for(int day=0;day<n;day++){
			lights[bulbs[day]-1]=day+1;//天数从1开始编号
		}
		int ans=MAX;
		for (int l=0,r=l+k+1,mid=l+1;r<n;mid++){
			if (lights[mid]<=max(lights[l],lights[r])){
				if (mid==r) ans=min(ans,max(lights[l],lights[r]));
				l=mid;
				r=l+k+1;
			}
		}
		return ans;
	}
	public int min(int a,int b){return a<b?a:b;}
	public int max(int a,int b){return a>b?a:b;}

}
