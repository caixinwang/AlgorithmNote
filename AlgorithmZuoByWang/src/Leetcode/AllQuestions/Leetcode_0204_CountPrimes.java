package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0204_CountPrimes {

    public static int countPrimes(int n) {//暴力,会超时，即使做了优化
        if (n <= 2) return 0;
        int ans = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime(i)) ans++;
        }
        return ans;
    }

    public static boolean isPrime(int n) {
        if (n == 1) return false;
        if (n <= 3) return true;
        for (int i = 2; i * i <= n; i++) {//遍历到根号n就行
            if (n % i == 0) return false;
        }
        return true;
    }

    /**
     * 埃氏。用一个布尔类型的数组，一开始默认所有数都是质数，如果一个数是质数了，那么它的倍数一定不是质数，
     * 把它后面的倍数全部变为false。
     * 优化1：倍数不用从2开始，前面都已经帮你标记好了，从i开始就行
     * 优化2：我们知道因子只需要找个根号n以前的，因子x和对应的因子y，x<y，y在遍历到x的时候就标记了。
     */
    public static int countPrimes2(int n) {//埃氏筛选
        if (n <= 2) return 0;
        int ans = 0;
        boolean[] isPrime = new boolean[n];//2~n-1，够用了
        Arrays.fill(isPrime, true);
        for (int i = 2; i * i <= n; i++) {//根号n后面的在前面就已经标记了，假设a*b=n,a<b,在遍历到a的时候b就被标记了
            if (isPrime[i]) {//arr[i]还没有被标记为非质数，再去isPrime验证是否为指数
                for (int j = i * i; j < n; j += i) {
                    isPrime[j] = false;//合数，不是质数
                }
            }
        }
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) ans++;
        }
        return ans;
    }

    public static int countPrimes3(int n) {//再快些
        if (n <= 2) return 0;
        int ans = n / 2;//上来从3开始验证，直接砍掉n/2，自己列出来找规律
        boolean[] noPrime = new boolean[n];//不是质数为true
        for (int i = 3; i * i <= n; i += 2) {//步长为2，因为上来砍一半，偶数不用你去验了
            if (noPrime[i]) continue;
            for (int j = i * i; j < n; j += 2 * i) {//步长为2i，因为别的在2的时候标记了
                if (!noPrime[j]) {//要加判断，不然会重复减
                    ans--;
                    noPrime[j] = true;//合数，不是质数
                }
            }
        }
        return ans;
    }

}
