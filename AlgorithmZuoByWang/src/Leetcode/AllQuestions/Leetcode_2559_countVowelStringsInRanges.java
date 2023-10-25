package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_2559_countVowelStringsInRanges {
    //前缀和数组其实就是没有修改功能的线段树，这题用前缀和数组做即可
    public static int[] vowelStrings(String[] words, int[][] queries) {
        int[] ans=new int[queries.length];
        SegmentTree segmentTree=new SegmentTree(words.length);
        segmentTree.build(words);
        for (int i = 0; i < queries.length; i++) {
            int[] query=queries[i];
            ans[i]=segmentTree.query(query[0],query[1]);
        }
        return ans;
    }

    public static boolean isVowel(char c){
        return c=='a'||c=='e'||c=='i'||c=='o'||c=='u';
    }

    static class SegmentTree {
        int N;
        int[] sum;

        public SegmentTree(int N){
            this.N=N;
            sum=new int[N<<2];
        }

        private int merge(int l,int r){
            return l+r;
        }

        private void pushUp(int rt){
            sum[rt]=merge(sum[rt<<1],sum[rt<<1|1]);
        }

        public void build(String[] words){
            build(words,1, words.length, 1);
        }

        public void build(String[] words,int l,int r,int rt){
            if (l==r){
                String s=words[l-1];
                boolean isVowel = isVowel(s.charAt(0))&&isVowel(s.charAt(s.length()-1));
                sum[rt]=isVowel?1:0;
                return;
            }
            int mid=l+(r-l>>1);
            build(words,l,mid,rt<<1);
            build(words,mid+1,r,rt<<1|1);
            pushUp(rt);
        }

        public int query(int L,int R){
            return query(L+1,R+1,1,N,1);
        }

        public int query(int L,int R,int l,int r,int rt){
            if (L<=l&&r<=R){
                return sum[rt];
            }
            int mid=l+(r-l>>1);
            int p1=0,p2=0;
            if(mid>=L)p1=query(L,R,l,mid,rt<<1);
            if(mid<R)p2=query(L,R,mid+1,r,rt<<1|1);
            return merge(p1,p2);
        }
    }

    public static void main(String[] args) {
        String[] words=new String[]{"bzmxvzjxfddcuznspdcbwiojiqf","mwguoaskvramwgiweogzulcinycosovozppl","uigevazgbrddbcsvrvnngfrvkhmqszjicpieahs","uivcdsboxnraqpokjzaayedf","yalc","bbhlbmpskgxmxosft","vigplemkoni","krdrlctodtmprpxwditvcps","gqjwokkskrb","bslxxpabivbvzkozzvdaykaatzrpe","qwhzcwkchluwdnqjwhabroyyxbtsrsxqjnfpadi","siqbezhkohmgbenbkikcxmvz","ddmaireeouzcvffkcohxus","kjzguljbwsxlrd","gqzuqcljvcpmoqlnrxvzqwoyas","vadguvpsubcwbfbaviedr","nxnorutztxfnpvmukpwuraen","imgvujjeygsiymdxp","rdzkpk","cuap","qcojjumwp","pyqzshwykhtyzdwzakjejqyxbganow","cvxuskhcloxykcu","ul","axzscbjajazvbxffrydajapweci"};
        int[][] queries=new int[][]{
                {0,2},
                {1,4},
                {1,1},
                {4,4},
                {6,17},
                {10,17},
        };
        System.out.println(words.length);
        int[] ints = vowelStrings(words, queries);
        System.out.println(Arrays.toString(ints));
    }
}
