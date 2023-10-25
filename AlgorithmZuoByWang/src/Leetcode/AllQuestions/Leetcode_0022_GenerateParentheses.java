package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;

public class Leetcode_0022_GenerateParentheses {

	public static List<String> generateParenthesis(int n) {//生成n对括号
		List<String> res=new ArrayList<>();
		char[] path=new char[n<<1];//长度固定
		process(0,n,n,path,res);
		return res;
	}

	/**
	 *
	 * @param index 当前还需要填index~2n-1的括号
	 * @param left 还剩下多少左括号可以填
	 * @param right 还剩下多少左括号可以填
	 * @param path 前面的决策
	 * @param res index到达2n的时候添加答案,这等价于left==0&&right==0
	 */
	private static void process(int index, int left, int right, char[] path, List<String> res) {
		if (left==0&&right==0) {
			res.add(String.valueOf(path));
			return;
		}
		if (left==right){
			path[index]='(';
			process(index+1,left-1,right,path,res);
		}else {// 0<=left<right  => right>0 ,right的if可以省略
			if (left>0) {
				path[index]='(';
				process(index+1, left - 1, right, path, res);
			}
			if (right>0) {
				path[index]=')';
				process(index+1, left, right - 1, path, res);
			}
		}
	}

	public static void main(String[] args) {
		List<String> list = generateParenthesis(3);
		for (String s : list) {
			System.out.println(s);
		}
	}

}
