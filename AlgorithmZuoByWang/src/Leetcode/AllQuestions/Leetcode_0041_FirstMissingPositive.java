package Leetcode.AllQuestions;

public class Leetcode_0041_FirstMissingPositive {
    /**
     * 值为x，应该放在下标x-1的位置
     */
    public static int firstMissingPositive(int[] arr) {
        int l = 0, r = arr.length;//l代表下标[0,l-1]上的值是连续的,即nums[l-1]=l。r代表当前最好从[1...r]是连续的
        while (l < r) {//为什么撞了就停，因为r表示最好到r，但是l表示下一个数需要l+1，是矛盾的
            if (arr[l] == l + 1) {
                l++;
            } else if (arr[l] <= l || arr[l] > r || arr[arr[l] - 1] == arr[l]) {//这个if和前面的不能对调
                swap(arr, l, --r);//和r前面的那个交换
            } else {
                swap(arr, l, arr[l] - 1);
            }
        }
        return l + 1;
    }

    public static int firstMissingPositive2(int[] arr) {//这种写法你随便调
        int n=arr.length;
        int l=0;//[0,l)的数从1开始是连续的。假设起始值为k，那么0~L-1偏移量为L-1，也就是集齐了k~k+l-1，还差k+l
        int r=n;//[r,N-1]是垃圾区。换言之最多从0~r-1 是连续的，也就是最大的期望值是k+r-1
        while(l<r){
            if (arr[l]==l+1){//点挺正，直接动
                l++;
            }else if (l+1<arr[l]&&arr[l]<=1+r-1&&arr[arr[l]-1]!=arr[l]){//确定这个值在范围内，并且之前没有出现过这个值
                swap(arr,l,arr[l]-1);
            }else {//垃圾
                swap(arr,l,--r);
            }
        }
        return l+1;//l+1就是期待的那个数,[1...l]都已经收集齐了
    }

    //这是code268
    //借用第41题的思想，找到第一个缺失的正数。改为这题，这题问的是从0开始缺失最小是啥。
    //可以随便改，下次换成从k开始，缺失的最小的数是啥，也能做！！！从k开始连续，那么一个值x就应该是x-k位置
    public int missingNumberFrom0(int[] arr) {
        int n=arr.length;
        int l=0;//[0,l)的数从0开始是连续的,总共l个数连续，0...l-1是连续的，所以下一个点正的数就是l.
        int r=n;//[r,N-1]是垃圾区。换言之最多从0~r-1 是连续的，也就是最大的期望值是k+r-1
        while(l<r){
            if (arr[l]==l){//点挺正，直接动
                l++;
            }else if (l<arr[l]&&arr[l]<=r-1&&arr[arr[l]]!=arr[l]){//确定这个值在范围内，并且之前没有出现过这个值
                swap(arr,l,arr[l]);
            }else {//垃圾
                swap(arr,l,--r);
            }
        }
        return l;//[0...l)连续,总共l个数,凑齐了0,1...,l-1，所以缺l
    }

    /**
     * 值为x应该去x-k的下标,因为从k开始连续，k,...,k+(index),index下标对应的值为k+index,k+index=x得到index=x-k
     * l代表从[0,l)是从k开始连续的，所以k,...,k+(l-1)凑齐了，缺k+l
     * r代表范围[r,N-1]的数全部丢了，这个范围一共N-r个数，所以还剩下r个数没被舍弃，这r个数从k开始,k,...k+(r-1)，所以最高期望为k+(r-1)
     * 用上面三个指标，改写出最终版本。
     * 使用偏移量来解释原理：l初始化为0，r初始化为n。arr的总的偏移量为n-1，所以值最高为k+n-1=k+r-1，期望的值为k+l，下标i，应该放i+k
     * @param arr 无序数组，正负随意
     * @param k 返回从k开始，缺失的最小数。
     * @return -
     */
    public int missingNumberFromK(int[] arr,int k) {
        int N=arr.length;
        int l=0;//[0,l)的数从k开始是连续的,总共l个数连续，((0...l-1)+k)是连续的，所以下一个点正的数就是l+k.
        int r=N;//[r,N-1]是垃圾区。换言之最多从0~r-1 是连续的，也就是最大的期望值是k+r-1
        while(l<r){
            if (arr[l]==l+k){//点挺正，直接动
                l++;
            }else if (l+k<arr[l]&&arr[l]<=r-1+k&&arr[arr[l]-k]!=arr[l]){//确定这个值在范围内，并且之前没有出现过这个值
                swap(arr,l,arr[l]);
            }else {//垃圾
                swap(arr,l,--r);
            }
        }
        return l+k;//([0...l)+k)连续,总共l个数,凑齐了(0,1...,l-1)+k，所以缺l
    }

    public int missingNumber5(int[] arr) {
        int N=arr.length;
        int l=0;//[0,l)的数从0开始是连续的,总共l个数连续，0...l-1是连续的，所以下一个点正的数就是l.
        int r=N;//[r,N-1]是垃圾区。换言之最多从0~r-1 是连续的，也就是最大的期望值是k+r-1
        while(l<r){
            if (arr[l]==l){//点挺正，直接动
                l++;
            }else if (l<arr[l]&&arr[l]<=r-1&&arr[arr[l]]!=arr[l]){//确定这个值在范围内，并且之前没有出现过这个值
                swap(arr,l,arr[l]);
            }else {//垃圾
                swap(arr,l,--r);
            }
        }
        return l;//[0...l)连续,总共l个数,凑齐了0,1...,l-1，所以缺l
    }



    private static void swap(int[] arr, int a, int b) {
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }


}
