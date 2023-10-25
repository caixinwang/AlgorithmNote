package Leetcode.AllQuestions;

import TestUtils.ArrayUtil;
import TestUtils.StringUtil;

import java.util.Arrays;
import java.util.HashMap;

public class Leetcode_0003_LongestSubstringWithoutRepeatingCharacters {//无重复字符的最长子串

    /**
     * dp[i]为必须以i结尾不含重复字符的最长子串
     * 分两种情况，第一种i这个字符出现在dp[i-1]的子串内部--比不过i不参与。第二种出现在子串外部，dp[i]=dp[i-1]+1
     */
    public static int lengthOfLongestSubstring1(String s) {//动态规划
        if (s==null||s.length()==0) return 0;
        int res=0;
        char[] str = s.toCharArray();
        int N=str.length;
        int[]dp=new int[N];
        HashMap<Character,Integer> map=new HashMap<>();//记录一个字符最早出现的位置
        dp[0]=1;
        map.put(str[0],0);
        for (int i=1;i<N;i++){
            if (!map.containsKey(str[i])){
                dp[i]=dp[i-1]+1;
            }else {
                int recent=map.get(str[i]);
                if (recent<=i-1-dp[i-1]){//最近出现的位置在dp[i-1]外面
                    dp[i] = dp[i-1]+1;
                }else {//出现在dp[i-1]那个子串的内部
                    dp[i]=i-recent;//recent是我不要的
                }
            }
            map.put(str[i],i);
        }
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    public static int lengthOfLongestSubstring2(String s) {//空间压缩的动态规划
        if (s==null||s.length()==0) return 0;
        char[] str = s.toCharArray();
        int N=str.length;
        int[] map=new int[128];//映射字符出现的下标位置
        Arrays.fill(map,-1);
        //这里不初始化含义也可以维持住
        int max=1;//初始化，从第二个位置开始。max是长度
        int pre=-1;//i-1位置的答案第一个不达标的位置
        map[str[0]]=0;
        for (int i=1;i<N;i++){
            int index=Math.max(pre,map[str[i]]);//找瓶颈，即找i位置结尾阔出去第一个不达标的位置
            pre=index;
            max = Math.max(max, i-index);//当前位置减去第一个阔不动的位置就是长度
            map[str[i]]=i;
        }
        return max;
    }

    public static int lengthOfLongestSubstring3(String s) {//滑动窗口
        if (s==null||s.length()==0) return 0;
        int res=0;
        char[] str = s.toCharArray();
        int N=str.length;
        boolean[] isExist=new boolean[128];
        int l=0;
        int r=0;
        while(r<N){
            if (!isExist[str[r]]){//子串还没有包含r位置的字符，扩大窗口
                isExist[str[r++]]=true;
                res = Math.max(res, r-l);
            }else {//子串已经包含了r位置的字符，缩小窗口
                isExist[str[l++]]=false;
            }
        }
        return res;
    }

    public static int lengthOfLongestSubstring4(String s) {
        char[] str=s.toCharArray();
        boolean[] exist=new boolean[128];
        int n=str.length,ans=0;
        for(int r=0,l=0;r<n;r++){//固定r，发现即将不达标了就缩
            while(exist[str[r]]) exist[str[l++]]=false;
            exist[str[r]]=true;
            ans=Math.max(ans,r-l+1);
        }
        return ans;
    }

    public static int lengthOfLongestSubstring5(String s) {
        int[] cnt=new int[128];
        char[] str=s.toCharArray();
        int n=str.length,ans=0;
        for(int r=0,l=0;r<n;r++){//固定r，已经不达标了才缩
            cnt[str[r]]++;
            while(cnt[str[r]]>1) cnt[str[l++]]--;
            ans=Math.max(ans,r-l+1);
        }
        return ans;
    }

    public static int lengthOfLongestSubstring6(String s) {
        int n=s.length(),ans=0;
        char[] str=s.toCharArray();
        int[] cnt=new int[128];
        for(int l=0,r=0;r<n;){
            while(l<n&&cnt[str[r]]==1){
                cnt[str[l++]]--;
            }
            cnt[str[r++]]++;
            if(r-l>ans) ans=r-l;
        }
        return ans;
    }

    public static int lengthOfLongestSubstring7(String s) {
        int n=s.length(),ans=0;
        boolean[] exist=new boolean[128];
        char[] str=s.toCharArray();
        for(int l=0,r=0;l<n;){
            while(r<n&&!exist[str[r]]) exist[str[r++]]=true;
            if (r-l>ans) ans=r-l;
            exist[str[l++]]=false;
        }
        return ans;
    }

    static StringUtil su=new StringUtil();
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            String s=su.generateRandom_all_String(su.ran(1,50));
            int ans1=lengthOfLongestSubstring7(s);
            int ans2=lengthOfLongestSubstring2(s);
            if (ans1!=ans2) {
                System.out.println("oops!");
                break;
            }
        }
    }


}
