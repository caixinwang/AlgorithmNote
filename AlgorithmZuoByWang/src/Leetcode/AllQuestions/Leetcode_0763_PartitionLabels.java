package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0763_PartitionLabels {

	public static List<Integer> partitionLabels(String s) {
		char[] str=s.toCharArray();
		int[] far=new int[128];
		for (int i=0;i<str.length;i++){
			far[str[i]]=i;
		}
		int start=0,end=0;
		List<Integer> ans=new ArrayList<>();
		for (int i=0;i<str.length;i++){
			end=Math.max(far[str[i]],end);
			if (i==end){
				ans.add(end-start+1);
				start=end+1;
			}
		}
		return ans;
	}

}
