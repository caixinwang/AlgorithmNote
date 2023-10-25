package Leetcode.AllQuestions;

public class Leetcode_2374_NodeWithHighestEdgeScore {
    public int edgeScore(int[] edges) {
        int n=edges.length;
        long[] scores=new long[n];//i号结点的积分,用long！
        for (int node=0;node<n;node++){
            scores[edges[node]]+=node;
        }
        int maxidx=0;
        for (int i = 1; i < scores.length; i++) {
            if (scores[i]>scores[maxidx])maxidx=i;
        }
        return maxidx;
    }

}
