package Leetcode.AllQuestions;

public class Leetcode_0096_UniqueBinarySearchTrees {//卡特兰数
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static int numTrees(int n) {//C(2n,n)/n+1
        if (n==1) return 1;
        return (int)(c(2*n,n)/(n+1));
    }

    public static long c(long n,long m){
        if (m==1) return n;
        long o1=1,o2=1;
        for (int i = 0; i <m; i++) {//C(n,m)
            o1*=n-i;
            o2*=m-i;
            long gcd=gcd(o1,o2);
            o1/=gcd;
            o2/=gcd;
        }
        return o1;
    }

    public static long gcd(long n,long m){
        return m==0?n:gcd(m,n%m);
    }

    public static void main(String[] args) {
        System.out.println(c(4,2));
    }

}

