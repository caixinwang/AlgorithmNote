package Leetcode.SwordToOffer;

public class SwordToOffer_012_Exist {
    int[] trans=new int[]{0,1,0,-1,0};//右下左上
    public boolean exist(char[][] board, String word) {
        int n= board.length,m=board[0].length;
        for(int r=0;r<n;r++){
            for(int c=0;c<m;c++){
                if(dfs(board,r,c,word.toCharArray(),0)) return true;
            }
        }
        return false;
    }

    //登上去之后检查合法性
    public boolean dfs(char[][] board,int r,int c,char[] str,int index){
        if(index==str.length) return true;
        int n= board.length,m=board[0].length;
        if(r>=0&&r<n&&c>=0&&c<m&&board[r][c]!=' '&&board[r][c]==str[index]){
            char t=board[r][c];
            board[r][c]=' ';
            for(int d=0;d<4;d++) if(dfs(board,r+trans[d],c+trans[d+1],str,index+1)) return true;
            board[r][c]=t;
        }
        return false;
    }
}
