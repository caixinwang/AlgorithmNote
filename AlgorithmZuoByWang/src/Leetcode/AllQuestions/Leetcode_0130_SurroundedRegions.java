package Leetcode.AllQuestions;

import TestUtils.ArrayUtil;

public class Leetcode_0130_SurroundedRegions {

	public static void solve(char[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j]=='O'){
					if (infect(board,i,j,'O','.')){
						infect(board,i,j,'.','Y');//Y最后变成O
					}else {//没走出去就提前改了
						infect(board,i,j,'.','X');
					}
				}
			}
		}
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j]=='Y'){
					infect(board,i,j,'Y','O');//Y最后变成O
				}
			}
		}
	}

	/**
	 *
	 * @param board 从board的[i][j]位置出发
	 * @param i -
	 * @param j -
	 * @param meet 遇到meet才能动，沿途的meet都改成change
	 * @param change change不能和meet相等，否则死循环
	 * @return 走出边界才能返回true否则都返回false
	 */
	public static boolean infect(char[][] board,int i,int j,char meet,char change){
		if (i<0||j<0||i>board.length-1||j>board[0].length-1) return true;
		boolean res=false;
		if (board[i][j]==meet){
			board[i][j]=change;
			res = infect(board, i + 1, j, meet, change);
			res|=infect(board,i-1,j,meet,change);
			res|=infect(board,i,j-1,meet,change);
			res|=infect(board,i,j+1,meet,change);
		}
		return res;
	}


	public static void main(String[] args) {
		ArrayUtil arrayUtil=new ArrayUtil();
		char[][] b=new char[][]{
				{'0','0','0','0'},
				{'0','0','1','1'},
				{'0','1','1','0'},
				{'1','0','0','0'},
		};
		System.out.println(infect(b,1,2,'1','0'));
		arrayUtil.printMatrix(b);
	}


}
