package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Leetcode_0119_PascalTriangleII {
    public List<Integer> getRow(int rowIndex) {
        int[] ans=new int[rowIndex+1];//rowidx+1 -> 行数 ->开的空间
        Arrays.fill(ans,1);
        for (int i=1;i<=rowIndex;i++){//第i行
            for (int k=1,pre=1;k<=i-1;k++){
                int t=ans[k];//覆盖之前保存一下
                ans[k]=pre+ans[k];
                pre=t;
            }
        }
        List<Integer> res=new ArrayList<>();
        for (int n:ans) res.add(n);
        return res;
    }
}
