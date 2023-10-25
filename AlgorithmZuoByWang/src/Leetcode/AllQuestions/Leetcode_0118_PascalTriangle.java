package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0118_PascalTriangle {

	public List<List<Integer>> generate(int numRows) {
		int[] arr=new int[30];//观察输入numRows不超过30
		List<List<Integer>> ans=new ArrayList<>();
		for (int i = 0; i < numRows; i++) {
			arr[i]=1;//最后一个位置新来的，都是1
			for (int k = i-1; k>=1; k--) {//最后一个和第一个不需要处理,都是1
				arr[k]=arr[k]+arr[k-1];
			}
			List<Integer> list=new ArrayList<>();
			for (int j = 0; j <= i; j++) {//生成每一行的list
				list.add(arr[j]);
			}
			ans.add(list);
		}
		return ans;
	}

}
