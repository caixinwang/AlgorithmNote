package Leetcode.AllQuestions;

public class Leetcode_2712_MinimumCostToMakeAllCharactersEqual {
    //翻转的前缀和后缀中的字符一定是连续相同的，因为如果有不同了，那么就白翻转了
    //每次选择前缀和后缀中代价小的翻转。把0~l全部变为[l+1]
    public static long minimumCost(String s) {//用leetcode的测试用例去验证自己的贪心。
        char[] str = s.toCharArray();
        int n = str.length;
        if (n == 1) return 0;
        int l = 0, r = n - 1;
        long ans = 0;
        while (l < r) {//首尾指针，贪心
            if (str[l + 1] == str[l]) l++;
            else if (str[r - 1] == str[r]) r--;
            else if (l + 1 > n - r) {
                ans += n - r;
                r--;
            } else {
                ans+=l+1;
                l++;
            }
        }
        return ans;
    }

    public static long minimumCost2(String s) {
        char[] str = s.toCharArray();
        int n = str.length;
        if (n == 1) return 0;
        long f0=0,f1=0;//f0代表把0~i的前缀全部变为0的最低开销，f1为变为1的最低开销
        for (int i=1;i<n;i++){
            long new_f0=f0;
            if (str[i]=='1') new_f0=Math.min(f0+n-i,f1+i+1);
            if (str[i]=='0') f1=Math.min(f1+n-i,f0+i+1);
            f0=new_f0;
        }
        return Math.min(f0,f1);
    }


}
