package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Leetcode_0131_PalindromePartitioning {

    //枚举所有的是回文的前缀，从左到右的尝试。
    public static List<List<String>> partition(String s) {
        boolean[][] isp = isPalindrome(s);
        List<List<String>> ans = new ArrayList<>();
        LinkedList<String> path = new LinkedList<>();
        f(s.toCharArray(), 0, path, ans, isp);
        return ans;
    }

    private static void f(char[] str, int index, LinkedList<String> path, List<List<String>> ans, boolean[][] isp) {
        if (index == str.length) {
            ans.add(new ArrayList<>(path));
            return;
        }
        int r = index;
        String s = "";
        while (r < str.length) {
            s += str[r];
			if (isp[index][r]){
				path.addLast(s);
				f(str, r+1, path, ans, isp);
				path.pollLast();
			}
			r++;
        }
    }

    //预处理结构，告诉你L~R的子串是不是回文串
    //一定要填好两个对角线，一个对角线不够
    public static boolean[][] isPalindrome(String s) {
        char[] str = s.toCharArray();
        int N = str.length;
        boolean[][] res = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            res[i][i] = true;
        }
		for (int i=0;i+1<N;i++){
			res[i][i+1]=str[i]==str[i+1];
		}
        for (int j = 2; j < N; j++) {
            for (int i = 0; i + j < N; i++) {
                int l = i, r = i + j;
                if (str[l] == str[r] && res[l + 1][r - 1]) res[l][r] = true;
            }
        }
        return res;
    }

	public static void main(String[] args) {
		boolean[][] p = isPalindrome("cdd");
		List<List<String>> cdd = partition("cdd");
	}


}
