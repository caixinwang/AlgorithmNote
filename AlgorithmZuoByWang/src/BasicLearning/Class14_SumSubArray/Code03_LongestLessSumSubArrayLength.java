package BasicLearning.Class14_SumSubArray;

import java.util.Arrays;

/**
 * 给定一个整数数组以及一个数k，所有累加和小于等于k的子数组都是达标的。问所有达标子数组中最长的长度是多少。
 */
public class Code03_LongestLessSumSubArrayLength {

    //暴力枚举
    public static int solution(int[] arr,int k){
        int N=arr.length;
        int[] s=new int[N+1];
        for (int i = 0; i < arr.length; i++) {
            s[i+1]=s[i]+arr[i];
        }
        int res=0;
        for (int l=0;l<N;l++){
            for (int r=l+1;r<=N;r++){
                if (s[r]-s[l]<=k) res = Math.max(res, r-l);
            }
        }
        return res;
    }

    //动态规划+滑动窗口
    public static int solution1(int[] arr, int k) {
        int N = arr.length;
        int[] f = new int[N];//f[i]:以i为左端点的所有子数组所能达到的最小累加和
        int[] t = new int[N];//t[i]:f[i]所代表的子数组的右端点
        f[N - 1] = arr[N - 1];
        t[N - 1] = N-1;
        for (int i = N - 2; i >= 0; i--) {
            f[i] = arr[i];
            t[i] = i;
            //如果f[i+1]可以使得f[i]更小，那么就加上f[i+1]。取不取等号都行
            if (f[i + 1] <= 0) {
                f[i] += f[i + 1];
                t[i] =  t[i + 1];
            }
        }
		int sum = 0 ,res=0;
        for (int l=0,r=0;l<N;l++){//窗口为[l,r)
            while(r<N&&sum+f[r]<=k){//让窗口往右边扩张
                sum += f[r];
                r=t[r]+1;//左闭右开
                if (r-l>res) res=r-l;//有一部分的l不会产生答案，但是不影响最终结果，因为如果扩不了就比之前的答案短
            }
            if (r>l){//窗口内有数
                sum-=arr[l];
            }else {//窗口内无数
                r=l+1;
            }
        }
        return res;
    }

    //固定右端点的二分
    public static int solution2(int[] arr, int k) {
        int n = arr.length, ans = 0, sum = 0;
        int[] up = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            up[i + 1] = sum > up[i] ? sum : up[i];
        }
        sum = 0;
        for (int i = 1; i <= n; i++) {//脑补遍历前缀和数组s,i从1开始 因为右端点需要>0
            sum += arr[i - 1];//s[i]
            int l = 0, r = i - 1, m;
            while (l <= r) {//在一个非递减序列up中找>=sum-k的最左
                m = l + (r - l >> 1);
                if (sum-up[m]<=k) r = m - 1;//如果满足，就往左边继续找，期望找到一个更长的
                else l = m + 1;
            }
            ans = Math.max(ans, i - l);
        }
        return ans;
    }

    //固定右端点的单调栈做法
    public static int solution3(int[] arr, int k) {
        int N = arr.length;
        int[] s = new int[N + 1];
        for (int i = 0; i < N; i++) {
            s[i + 1] = s[i] + arr[i];
        }
        int[] stack = new int[N + 1];
        int top = -1, ans = 0;
        for (int i = 0; i <= N; i++) {
            if (top == -1 || s[i] > s[stack[top]]) stack[++top] = i;//从左往右维持非递减
        }
        for (int i = N; i > 0; i--) {//固定右端点
            while (top >= 0 && s[i] - s[stack[top]] <= k) {
                ans = Math.max(ans, i - stack[top--]);
            }
        }
        return ans;
    }

    //固定左端点的二分
    public static int solution4(int[] arr, int k){
        int N = arr.length;
        int[] s = new int[N + 1];
        for (int i = 1; i <= N; i++){
            s[i]=s[i-1]+arr[i-1];
        }
        int[] up=new int[N+1];//固定左端点
        up[N]=s[N];
        for (int i=N-1;i>=0;i--){//固定左端点，从右边开始填
            up[i]=s[i];
            if (up[i]>up[i+1]) up[i]=up[i+1];
        }
        int res=0;
        for (int i=0;i<N;i++){
            int l=0,r=N,m=0;
            while(l<=r){
                m=l+(r-l>>1);
                if (up[m]-s[i]<=k) l=m+1;
                else r=m-1;
            }
            res = Math.max(res, r-i);
        }
        return res;
    }

    //固定左端点的单调栈做法
    public static int solution5(int[] arr, int k){
        int N = arr.length;
        int[] s = new int[N + 1];
        for (int i = 1; i <= N; i++){
            s[i]=s[i-1]+arr[i-1];
        }
        int[] q=new int[N+1];//固定左端点
        int t=-1;
        for (int i=N;i>=0;i--){//固定左端点，从右边开始填
            if (t==-1||s[i]<s[q[t]]) q[++t]=i;//从右边往左边走，保证是递减的
        }
        int res=0;
        for (int i=0;i<N;i++){
            while(t!=-1&&s[q[t]]-s[i]<=k) {
                res = Math.max(res, q[t--]-i);
            }
        }
        return res;
    }

    //固定左端点的单调队列做法
    public static int solution6(int[] arr, int k){
        int N = arr.length;
        int[] s = new int[N + 1];
        for (int i = 1; i <= N; i++){
            s[i]=s[i-1]+arr[i-1];
        }
        int[] q=new int[N+1];//固定左端点
        int t=-1,h=0;
        for (int i=1;i<=N;i++){
            //右边的更长，并且更容易满足条件，那么之前的更差的位置就可以淘汰了
            while(t>=h&&s[i]<s[q[t]])  t--;
            q[++t]=i;
        }
        int res=0;
        for (int i=0;i<N;i++){
            while(t>=h&&s[q[h]]-s[i]<=k) {
                res = Math.max(res, q[h++]-i);
            }
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        out:
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArray(6, 20);
            int k = (int) (Math.random() * 20) - 5;
            int[] res = new int[]{
                    solution(arr,k),
                    solution1(arr, k),
                    solution2(arr, k),
                    solution3(arr, k),
                    solution4(arr, k),
                    solution5(arr, k),
            };
            for (int j = 0; j < res.length; j++) {
                if (res[j] != res[0]) {
                    System.out.println(Arrays.toString(arr)+" "+k);
                    for (int re : res) {
                        System.out.println(re);
                    }
                    break out;
                }
            }
        }
        System.out.println("test finish");
    }

}
