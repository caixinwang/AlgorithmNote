package BasicLearning.Class07_RecursionToDP;

public class Code12_LongestCommonSubsequence {

    private static int longestCommonSubsequence(String a, String b) {
        if (a == null || b == null || a.length() == 0 || b.length() == 0) {
            return 0;
        }
        char[] string1 = a.toCharArray();
        char[] string2 = b.toCharArray();
        return process1(string1, a.length() - 1, string2, b.length() - 1);
    }

    /**
     * @param a:固定参数
     * @param index1:0~index1 的字符串
     * @param b:固定参数
     * @param index2:0~index2 的字符串
     * @return :返回a的0~index1的字符串和b的0~index2的字符串的最大公共子序列长度
     */
    private static int process1(char[] a, int index1, char[] b, int index2) {
        if (index1 == 0 && index2 == 0) {//二维递归的base case
            return a[index1] == b[index2] ? 1 : 0;
        } else if (index1 == 0) {//base case
            return a[index1] == b[index2] ? 1 : process1(a, index1, b, index2 - 1);
        } else if (index2 == 0) {//base case
            return a[index1] == b[index2] ? 1 : process1(a, index1 - 1, b, index2);
        } else {//至少都有两个字符
            //p1这里一定有一个前提条件就是a[index1]==b[index2]
            int p1 = a[index1] == b[index2] ? process1(a, index1 - 1, b, index2 - 1) + 1 : 0;//最长公共子序列的最后一个字符都在a和b的最后
            int p2 = process1(a, index1, b, index2 - 1);//最长公共子序列的最后一个字符在a不在b
            int p3 = process1(a, index1 - 1, b, index2);//最长公共子序列的最后一个字符在b不在a
            //p4这种情况可以不存在，因为p4一定小于p1,p2,p3.p2/p3的子串范围都大于p4，或者说p4已经包含在p2或者p3的决策中了。
//            int p4=process1(a,index1-1,b,index2-1);
            return Math.max(Math.max(p2, p3), p1);
        }
    }

    private static int longestCommonSubsequence2(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] a = str1.toCharArray();
        char[] b = str2.toCharArray();
        int[][] dp = new int[str1.length()][str2.length()];
        dp[0][0] = a[0] == b[0] ? 1 : 0;
        for (int index2 = 1; index2 < b.length; index2++) {
            dp[0][index2] = a[0] == b[index2] ? 1 : dp[0][index2 - 1];
        }
        for (int index1 = 1; index1 < a.length; index1++) {
            dp[index1][0] = a[index1] == b[0] ? 1 : dp[index1 - 1][0];
        }
        for (int index1 = 1; index1 < a.length; index1++) {
            for (int index2 = 1; index2 < b.length; index2++) {
//                int p1 = a[index1] == b[index2] ? dp[index1 - 1][index2 - 1] + 1 : 0;
//                int p2 = dp[index1][index2 - 1];
//                int p3 = dp[index1 - 1][index2];
//                dp[index1][index2]= Math.max(Math.max(p2, p3), p1);
                dp[index1][index2]=a[index1] == b[index2] ?
                        dp[index1 - 1][index2 - 1] + 1:Math.max(dp[index1][index2 - 1],dp[index1 - 1][index2]);
            }
        }
        return dp[str1.length() - 1][str2.length() - 1];
    }

    public static int longestCommonSubsequence3(String s1, String s2) {//空间压缩
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int row = str1.length;
        int colum = str2.length;
        int[] dp=new int[colum+1];//向下滚.colum+1是因为要归一化，不需要单独初始化
        int[] help=new int[colum+1];
        for (int i = 1; i <= row ; i++) {//迭代到i行
            for (int j = 1; j < dp.length; j++) {//dp[0]一直是0
                if (str1[j-1]==str2[i-1]) dp[j]=help[j-1]+1;
                else dp[j]=Math.max(dp[j],dp[j-1]);
            }
            for (int k = 0; k < dp.length; k++) {
                help[k]=dp[k];
            }
        }
        return dp[colum];
    }

    //给出具体的那个子序列是什么
    public static String lcse(String str1, String str2) {
        if (str1 == null || str2 == null || str1.equals("") || str2.equals("")) {
            return "";
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int[][] dp = getdp(chs1, chs2);
        int m = chs1.length - 1;
        int n = chs2.length - 1;
        char[] res = new char[dp[m][n]];
        int index = res.length - 1;
        while (index >= 0) {
            if (n > 0 && dp[m][n] == dp[m][n - 1]) {
                n--;
            } else if (m > 0 && dp[m][n] == dp[m - 1][n]) {
                m--;
            } else {
                res[index--] = chs1[m];
                m--;
                n--;
            }
        }
        return String.valueOf(res);
    }

    public static int[][] getdp(char[] str1, char[] str2) {
        int[][] dp = new int[str1.length][str2.length];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int i = 1; i < str1.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
        }
        for (int j = 1; j < str2.length; j++) {
            dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
        }
        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (str1[i] == str2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp;
    }

    public static void main(String[] args) {
        String a = "123dsafKSFKJSDA";
        String b = "ab123sadjADHF";
        System.out.println(longestCommonSubsequence(a, b));
        System.out.println(longestCommonSubsequence2(a, b));
        System.out.println();
    }
}
