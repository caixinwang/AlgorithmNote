package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0666_PathSum {
    public int pathSum(int[] nums) {
        int[] nodes=new int[31+1];//算上空节点最多五层满
        Arrays.fill(nodes,-1);
        for(int node:nums){
            int idx=(1<<(node/100 - 1))+(node/10%10 - 1);
            nodes[idx]=node%10;
        }
        return f(nodes,1,0);
    }
    public int f(int[] nodes,int root,int pre){
        if(nodes[root]==-1) return 0;
        int sum=pre+nodes[root];
        if(nodes[root<<1]==-1&&nodes[root<<1|1]==-1) return sum;
        return f(nodes,root<<1,sum)+f(nodes,root<<1|1,sum);
    }
}
