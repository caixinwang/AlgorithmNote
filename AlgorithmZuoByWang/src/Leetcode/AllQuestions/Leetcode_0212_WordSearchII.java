package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Leetcode_0212_WordSearchII {
	class Node{
		Node[] nexts;
		boolean end;
		int pass;//剪枝

		public Node(){
			nexts=new Node[26];
			end=false;
			pass=0;
		}
	}
	public List<String> findWords(char[][] board, String[] words) {
		Node head=new Node();
		for(String s:words){
			Node cur=head;
			char[] str=s.toCharArray();
			for (char c:str){
				cur.pass++;
				int road=c-'a';
				if (cur.nexts[road]==null) cur.nexts[road]=new Node();
				cur=cur.nexts[road];
			}
			cur.end=true;
			cur.pass++;
		}
		List<String> ans=new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				dfs(board,i,j,head,new LinkedList<Character>(),ans);
			}
		}
		return ans;
	}

	private int dfs(char[][] board, int r, int c, Node head, LinkedList<Character> path, List<String> ans) {
		if (r<0||c<0||r>board.length-1||c>board[0].length-1||board[r][c]=='0') return 0;
		Node cur=head;
		int road=board[r][c]-'a';
		if (head.nexts[road]==null||head.nexts[road].pass<=0) return 0;//有路，并且走向这个结点有前途我才登上去
		cur=cur.nexts[road];//正式登上这个结点
		char t=board[r][c];
		path.addLast(t);
		board[r][c]='0';//登上了这个结点，建立不走回头路机制
		int fix=0;
		if (cur.end) {
			ans.add(pathToStr(path));
			cur.end=false;//加入过了，标记为false
			fix++;
		}
		fix+=dfs(board,r+1,c,cur,path,ans) +dfs(board,r-1,c,cur,path,ans)
				+dfs(board,r,c+1,cur,path,ans) +dfs(board,r,c-1,cur,path,ans);
		cur.pass-=fix;//减去搞定的单词数
		path.pollLast();
		board[r][c]=t;//恢复现场
		return fix;
	}

	private String pathToStr(LinkedList<Character> path){
		StringBuilder builder=new StringBuilder();
		for (char c:path){
			builder.append(c);
		}
		return builder.toString();
	}
}
