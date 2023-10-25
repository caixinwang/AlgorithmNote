package Leetcode.AllQuestions;

import java.util.HashMap;

public class Leetcode_0904_TotalFruit {
    int MIN=1<<31,MAX=MIN-1;
    public int totalFruit(int[] fruits) {
        HashMap<Integer,Integer> map=new HashMap<>();
        int all=0;//不同的种类数
        int n=fruits.length;
        int ans=MIN;
        for(int l=0,r=0;r<n;r++){
            while(all==2&&!map.containsKey(fruits[r])) {
                if(map.get(fruits[l])==1) {
                    map.remove(fruits[l]);
                    all--;
                }else{
                    map.put(fruits[l],-1+map.get(fruits[l]));
                }
                l++;
            }
            if(!map.containsKey(fruits[r])){
                all++;
                map.put(fruits[r],1);
            }else{
                map.put(fruits[r],1+map.get(fruits[r]));
            }
            ans=Math.max(ans,r-l+1);
        }
        return ans;
    }
}
