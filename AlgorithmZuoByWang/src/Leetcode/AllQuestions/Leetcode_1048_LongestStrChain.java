package Leetcode.AllQuestions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Leetcode_1048_LongestStrChain {
    HashMap<String,Integer> map=new HashMap<>();
    public int longestStrChain(String[] words) {
        for(String w:words) map.put(w,0);
        int ans=0;
        for(String w:words) ans=Math.max(ans,dfs(w));
        return ans;
    }

    //e字符串一定在words中，上游保证;dfs定义为:以e字符串作为链的结尾，最长能有多长。
    //假设e去掉一个字符之后变为了s，那么总体的最长就是以s结尾的最长再+1
    //我们枚举s中的每一个可能去掉的位置，剩下的字符串只有还在words里面的，我们才进递归
    //所以一件重要的事情就是快速的确认一个字符串是否在words中，那么我们可能需要一个Set
    //但是由于后面需要记忆化搜索，所以我们使用一个HashMap来起到Set和记忆化的作用
    //这样一来原始的words就可以不用了
    public int dfs(String e){
        int max=map.getOrDefault(e,0),n=e.length();
        if(max!=0) return max;
        for(int i=0;i<n;i++){
            var s=e.substring(0,i)+e.substring(i+1,n);
            if(map.containsKey(s)){
                max=Math.max(max,dfs(s));
            }
        }
        map.put(e,max+1);
        return max+1;
    }

    //通过递归方法的启发，直接自底向上进行动态规划，从长度为1的开始，需要进行长度从小到大排序
    public int longestStrChain2(String[] words) {
        Arrays.sort(words,(a,b)->{return a.length()-b.length();});
        HashMap<String,Integer> map=new HashMap<>();
        int n=words.length,ans=1;
        for(int i=0;i<n;i++) map.put(words[i],i);
        int[] dp=new int[n];//dp[i]:以i位置的字符串结尾的最长链长度.去掉一个格子，从前面转移而来
        Arrays.fill(dp,1);
        for (int i=0;i<n;i++){//从前面转移而来，所以先填前面的
            for (int j=0;j<words[i].length();j++){//枚举去掉每一个位置
                var s=words[i].substring(0,j)+words[i].substring(j+1);
                if (map.containsKey(s)) dp[i] = Math.max(dp[i],1 + dp[map.get(s)]);
                ans = Math.max(ans, dp[i]);
            }
        }
        return ans;
    }
}
