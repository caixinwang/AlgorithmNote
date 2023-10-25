package Leetcode.AllQuestions;

import java.util.Arrays;

//灵神的讲解：倒着想。最后一步的情况一定是某一个人多了钱，一个人少了钱，最后一步的操作就是多的人给少的人。
//并且我们知道所有的人的cnt累加和是0。那么我们如果可以找到一些子集cnt累加和也是0，那么我们就隐约找到了一个子问题。
//所以我们似乎可以用动态规划来做。我们我们就原问题一顿拆，到达了base case可能就是{5,-5} {3,2,-5}类似这样的子集。
//那么我们知道对于{5,-5}这样的子集我们需要1次交易，对于{3,2,-5}这样的子集我们需要2次交易。
//这样我们就可以定义我们dp[i]的含义为：集合i通过交易之后，使得所有元素值均为0，至少需要几次调配。
//由于题目的元素的个数最多是12个。那么根据集合论，一串长为12的二进制数就可以代表这个集合。所以我们可以用int来表示集合
//假设只有5个元素，那么00101，就表示0号元素以及2号元素在当前的集合，然后我们把0和2这个下标对应回原来的欠账数组就能知道
//这个集合的cnt之和是不是为0了。假设原始的集合全集为c=01110 拆分为a=01100 那么a的补集就是b=c^a=00010
//如果sum[i]==0&&sum[j]==0那么说明我们划分出了两个和都为0的子集了。
//初始化dp[i]，最极端的情况是一个大其它都是小，那么就需要操作len-1次。
//转移：dp[i]=min{dp[i],dp[j]+dp[i^j]},条件：sum[i]==0；如果sum[i]!=0，那么dp[i]=MAX/2 OR len-1
//     dp[0]=0,空集用二进制表示就是0。最大的就是就是(1<<len)-1,也就是全1
//如何求集合的大小？其实就是求1的个数。java的Integer.bitCount可以做。
//如何判断第k号元素在不在集合里面？两种做法
//	1. (i&(1<<k))!=0  注意，这种写法不能写成(i&(i<<k))==1 但是可以写成(i&(i<<k))==(i<<k)
//	2. ((i>>k)&1)==1  这种写法也可以写成上面的((i>>k)&1)!=0
//如何枚举子集？
//	假设集合为a=110,我们不断的把a减1，a的所有子集必定在其中。a不断减1会得到{*101,100,*011,010,*001,000}，
//	其中打上*的不是a的子集，因为包含了a集合没有的元素。那么如何从中筛查出a的子集呢？我们把所有带有*号的都和a与一下
//	会得到{*100,100,*010,010,*000,000},你会发现，如果我们把a不断减1得到的东西与上a自己，那么我们就能去除掉原本不是a子集的集合
//  所以我们通过&a的操作来从一个不合法的子集变到一个合法的子集。所以原始集合为i,j初始化为(i-1)&i,变到下一个合法子集j=(j-1)&i
//	准确的说，j所能到达
//具体代码的编写：为了dp[i]=min{dp[i],dp[j]+dp[i^j]}不判断j对应的sum是不是为0，我们dp[i]=MAX/2,这样可以保证相加直接为无穷大
public class Leetcode_0465_OptimalAccountBalancing {

	final int MIN=1<<31,MAX=MIN-1;
	public int minTransfers(int[][] transactions) {
		int n=12,all=1<<n;
		int[] cnt=new int[n];
		for (int[] trans:transactions){
			cnt[trans[0]]-=trans[2];
			cnt[trans[1]]+=trans[2];
		}
		int[] dp=new int[all];
		Arrays.fill(dp,MAX>>1);//初始为MIN>>1是因为状态转移最多有两个dp相加，这样可以避免讨论
		dp[0]=0;
		for(int i=1;i<all;i++){
			int sum=0;
			for (int k=0;k<n;k++){
				if ((i>>k&1)==1){
					sum+=cnt[k];
				}
			}
			if (sum!=0) continue;//只有i集合累加和为0，才是原问题的子问题。
			dp[i]=Integer.bitCount(i)-1;//sum==0才有答案，最平凡的答案就是集合元素的个数-1
			for (int j=i-1&i;j>0;j=j-1&i){//枚举非空真子集
				dp[i]=min(dp[i],dp[j]+dp[i^j]);//这里没有验证j集合累加和为0，因为假设其中一个不为0，那么dp的值就是MAX/2，不影响
			}
		}
		return dp[all-1];
	}
	public int min(int a,int b){return a<b?a:b;}

}
