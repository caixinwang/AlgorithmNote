package Leetcode.AllQuestions;

import java.util.LinkedList;
import java.util.List;

public class Leetcode_0093_RestoreIpAddresses {
    public List<String> restoreIpAddresses(String s) {
        List<String> ans=new LinkedList<>();
        f(s.toCharArray(),0,4,new LinkedList<>(),ans);
        return ans;
    }

    public String pathToStr(LinkedList<String> path){
        StringBuilder sb=new StringBuilder();
        for(String s:path){
            if(sb.length()!=0) sb.append(".");
            sb.append(s);
        }
        return sb.toString();
    }

    public void f(char[] str, int index, int rest, LinkedList<String> path, List<String> ans){
        if(index==str.length) {
            if(rest==0) ans.add(pathToStr(path));
            return;
        }
        for(int i=index,cur=0;i<min(str.length,index+3);i++){
            cur=cur*10+str[i]-'0';
            if(cur>255) break;
            path.addLast(""+cur);
            f(str,i+1,rest-1,path,ans);
            path.pollLast();
            if(cur==0) break;
        }
    }
    public int min(int a,int b){return a<b?a:b;}
}
