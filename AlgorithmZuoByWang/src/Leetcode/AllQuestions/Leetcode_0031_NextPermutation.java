package Leetcode.AllQuestions;

/**
 * 我们知道一个序列，如果是全降序的，那么它一定是最大的，例如54321,，它肯定是最大的。
 * 那么以21开头后面跟着543，那么21543中如果21固定，21543就是最大的序列。
 * 所以我们知道我们如果想要下一个序列，那么就要找1,和后面尽量远的一个地方做交换。因为21固定543已经最大，只能变前面的了。
 * 1.先从后往前找，找到第一个降序的地方，这个例子就是找到1。因为1后面的543是降序的，也就是最大的。
 * 2.找到的这个第一个降序的数和543中第一个大于自己的数交换。也就是这个3做交换。由于我们找的是第一个大于自己的数做交换，
 * 所以交换完成以后541也是降序的。这个时候我们需要翻转一个就可以变为升序了，也就是以23开头最小的序列。23145
 */
public class Leetcode_0031_NextPermutation {
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) return;
        int i = nums.length - 1;
        while (--i >= 0 && nums[i] >= nums[i + 1]) ;//找到第一个降序的下标[i]<[i+1]
        if (i == -1) {
            reverse(nums, 0, nums.length - 1);//说明当前是最大排列
            return;
        }
        int j = nums.length;
        while (--j >= 0 && nums[j] <= nums[i]) ;//从后往前找到第一个大于i的数
        swap(nums, i, j);
        reverse(nums, i + 1, nums.length - 1);
    }

    public void reverse(int[] arr, int l, int r) {//逆序一下
        for (; l < r; swap(arr, l, r), l++, r--) ;
    }

    public void swap(int[] arr, int l, int r) {
        int t = arr[l];
        arr[l] = arr[r];
        arr[r] = t;
    }
}
