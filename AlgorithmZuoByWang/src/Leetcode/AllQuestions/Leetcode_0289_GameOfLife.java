package Leetcode.AllQuestions;

// 有关这个游戏更有意思、更完整的内容：
// https://www.bilibili.com/video/BV1rJ411n7ri
// 也推荐这个up主
public class Leetcode_0289_GameOfLife {

	/**
	 * 使用辅助矩阵比较简单，每个位置去数邻居就行。但是如果不使用额外空间，那么和矩阵消0那题就很像了，原地修改会影响后序决策
	 * 和那题一样，我们可以利用原始矩阵一些浪费的位置。这题我们可以使用原始矩阵浪费的地方，也就是一个int只是0和1，我们可以赋予
	 * 其它值一些含义。我们可以利用低位第二位的0和1来表示下一代的信息，这样更新下一代的时候，右移一位即可。
	 * 矩阵置零那题是实现得到第一行第一列的状态，先记录下来，然后把第一行第一列当做额外的空间，而这题是利用没有用完的值当做辅助空间。
	 * @param board 细胞状态
	 */
	public static void gameOfLife(int[][] board){
		int N= board.length,M=board[0].length;
		for (int i=0;i<N;i++){
			for (int j=0;j<M;j++){
				int neighbors=getNeighbors(board,i,j);
				if (neighbors==3||(neighbors==2&&(board[i][j]&1)==1)){//用board[i][j]的第一位代表细胞现在的死活
					board[i][j]|=0b10;//第二位为1表示下一轮是活的
				}
			}
		}
		for (int i=0;i<N;i++){
			for (int j=0;j<M;j++){
				board[i][j]>>=1;
			}
		}
	}

	public static int getNeighbors(int[][]m,int i,int j){
		int ans=0;
		for (int r=i-1;r<=i+1;r++){
			for (int c=j-1;c<=j+1;c++){
				if (r==i&&c==j) continue;
				if (isok(m,r,c)) ans++;
			}
		}
		return ans;
	}

	public static boolean isok(int[][]m,int i,int j){
		return i>=0&&j>=0&&i<m.length&&j<m[0].length&&(m[i][j]&1)==1;
	}

}
