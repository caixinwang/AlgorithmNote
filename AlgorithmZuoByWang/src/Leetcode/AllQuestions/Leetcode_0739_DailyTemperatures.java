package Leetcode.AllQuestions;

import java.util.Stack;

public class Leetcode_0739_DailyTemperatures {

	public static int[] dailyTemperatures(int[] arr) {
		int N=arr.length;
		int[] ans=new int[N];
		Stack<Integer> stack=new Stack<>();//找右边离你最近的比你大的,
		for (int i=0;i<N;i++){
			while(!stack.isEmpty()&&arr[i]>arr[stack.peek()]){
				Integer index = stack.pop();
				ans[index]=i-index;
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) ans[stack.pop()]=0;
		return ans;
	}

	public static int[] dailyTemperatures2(int[] arr) {//无语。。系统的Stack是真的慢，这个秒掉99%
		int N=arr.length;
		int[] ans=new int[N];
		int[] stack=new int[N];
		int top=-1;
		for (int i=0;i<N;i++){
			while(top!=-1&&arr[i]>arr[stack[top]]){
				int index = stack[top--];
				ans[index]=i-index;
			}
			stack[++top]=i;
		}
		while (top!=-1) ans[stack[top--]]=0;
		return ans;
	}

	public int[] dailyTemperatures3(int[] tmp) {
		int n=tmp.length,h=0,t=-1;
		int[] dq=new int[n];
		int[] ans=new int[n];
		for(int i=0;i<n;i++){
			while(h<=t&&tmp[i]>tmp[dq[t]]){
				ans[dq[t]]=i-dq[t];
				t--;
			}
			dq[++t]=i;
		}
		return ans;
	}

}
