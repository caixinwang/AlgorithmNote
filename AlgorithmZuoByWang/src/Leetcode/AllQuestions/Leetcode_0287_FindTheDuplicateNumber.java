package Leetcode.AllQuestions;

public class Leetcode_0287_FindTheDuplicateNumber {

	public static int findDuplicate(int[] next) {//这题其实就是找第一个入环结点，判断一个链表有没有环,快慢指针
		int s=0,f=0;
		do {
			s=next[s];
			f=next[next[f]];
		}while (f!=s);//不需要判断快指针的越界，因为知道一定有环
		for (s=0;f!=s;s=next[s],f=next[f]);
		return s;
	}

}
