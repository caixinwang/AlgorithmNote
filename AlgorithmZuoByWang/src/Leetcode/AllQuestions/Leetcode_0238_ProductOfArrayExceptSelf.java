package Leetcode.AllQuestions;

public class Leetcode_0238_ProductOfArrayExceptSelf {
    //使用除法
    public int[] productExceptSelf(int[] arr) {
        int zeros = 0;
        int p = 1;
        for (int n : arr) {
            if (n == 0) zeros++;//统计0的数量
            else p *= n;//所有非零的数乘起来
        }
        int[] ans = new int[arr.length];
        if (zeros >= 2) return ans;
        if (zeros == 1) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == 0) ans[i] = p;//只有0位置不是0
            }
            return ans;
        }
        for (int i = 0; i < arr.length; i++) {
            ans[i] = p / arr[i];
        }
        return ans;
    }

    //使用前缀乘和后缀乘，使用额外空间
    public int[] productExceptSelf2(int[] arr) {
        int[] ans = new int[arr.length];
        int[] prefix = new int[arr.length];
        int[] suffix = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            prefix[i] = i == 0 ? arr[0] : prefix[i - 1] * arr[i];
            suffix[arr.length - 1 - i] = i == 0 ? arr[arr.length - 1] : suffix[arr.length - 1 - (i - 1)] * arr[arr.length - 1 - i];
        }
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (i - 1 >= 0 ? prefix[i - 1] : 1) * (i + 1 < arr.length ? suffix[i + 1] : 1);
        }
        return ans;
    }

    //省掉额外空间，用ans暂时做其中一个前缀数组
    public int[] productExceptSelf3(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = i == 0 ? arr[0] : ans[i - 1] * arr[i];
        }
        int suffix=1;
        for (int i = ans.length - 1; i >= 0; i--) {
            ans[i] = (i - 1 >= 0 ? ans[i - 1] : 1) * suffix;
            suffix*=arr[i];
        }
        return ans;
    }


}
