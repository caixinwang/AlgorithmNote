package BasicLearning.Class10_ReservoirAndbfprt;

import java.util.PriorityQueue;

public class Code01_FindMinKth {//bfprt,寻找第k小

    // 利用大根堆，时间复杂度O(N*logK)。原理：k大小的大根堆，每次遍历数组都让比较大的出堆，这样到结束大的都出去了，剩下的就是第k小的
    // 因为剩下的k个是arr中前k小的数，他们之中最大的，也就是位于堆顶的数就是第k小的数。
    public static int minKth1(int[] arr, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((Integer o1,Integer o2)->{return o2-o1;});
        for (int i = 0; i < k; i++) maxHeap.add(arr[i]);//保持堆有k的空间即可，这样每次添加或者删除都是常数操作lgk
        for (int i = k; i < arr.length; i++) {
            if (arr[i] < maxHeap.peek()) {//让两者之间比较大的那个进不了堆，或者出堆
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();
    }

    // 改写快排，时间复杂度O(N)
    public static int minKth2(int[] array, int k) {
        int[] arr = copyArray(array);
        return process2(arr, 0, arr.length - 1, k - 1);
    }

    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i != ans.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    /**
     * @param arr:在arr中找在下标[L,R]中排在第index位置的数。arr数组无序，在无序的arr中找出实际上应该排在index位置的数
     * @param L:左边界，包括
     * @param R:右边界，包括
     * @param index:找出在下标[L,R]中排在第index位置的数
     * @return :返回这个在下标[L,R]中排在第index位置的数
     */
    public static int process2(int[] arr, int L, int R, int index) {
        if (L == R)  return arr[L];//base case。此时L=R=index
        int pivot = arr[L + (int) (Math.random() * (R - L + 1))];//pivot ∈ [L,R-L]
        int[] range = partition(arr, L, R, pivot);//三向切分
        if (index >= range[0] && index <= range[1]) {//index在它们之间，数都是相等的说明已经找到了
            return arr[index];
        } else if (index < range[0]) {//到左边去递归
            return process2(arr, L, range[0] - 1, index);
        } else {//到右边去递归
            return process2(arr, range[1] + 1, R, index);
        }
    }

    /**
     * 三向切分
     * @param arr:进行划分的函数
     * @param L:划分的左边界，包括
     * @param R:划分的右边界，包括
     * @param pivot:这里显式指定了一个划分值，我们也可以实现一个默认是最左边的数或者是最右边的数做划分值的partition函数
     *             但是这样的话我们就要在调用函数中从[L,R]中随机选择一个数与最左或右的数做交换，再进行partition，实现随机
     * @return :返回与划分值相等的范围
     */
    public static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more) {//与more撞上就停止，说明中间的不确定区域已经全部搞定
            if (arr[cur] < pivot) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[] { less + 1, more - 1 };
    }

    public static void swap(int[] arr, int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }

    // 利用bfprt算法，时间复杂度O(N)
    public static int minKth3(int[] array, int k) {
        int[] arr = copyArray(array);
        return bfprt(arr, 0, arr.length - 1, k - 1);
    }

    // arr[L..R]  如果排序的话，位于index位置的数，是什么，返回
    public static int bfprt(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }
        int pivot = medianOfMedians(arr, L, R);
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return bfprt(arr, L, range[0] - 1, index);
        } else {
            return bfprt(arr, range[1] + 1, R, index);
        }
    }

    /**
     *
     * @param arr:arr[L,R]中的元素五个一组，返回组中位数们的中位数
     * @param L:包括
     * @param R:包括
     * @return :返回中位数们的中位数
     */
    public static int medianOfMedians(int[] arr, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;//是否有不够五个的组
        int[] mArr = new int[size / 5 + offset];
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = L + team * 5;
            mArr[team] = getMedian(arr, teamFirst, Math.min(R, teamFirst + 4));
        }
        // marr中，找到中位数
        // marr(0, marr.len - 1,  mArr.length / 2 )
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    /**
     * @return :取得arr[L,R]的中位数。偶数返回上中位数
     */
    public static int getMedian(int[] arr, int L, int R) {
        insertionSort(arr, L, R);//排序的个数小，使用插入排序反而快
        return arr[(L + R) / 2];
    }



    public static void insertionSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i; j-1 >= L && arr[j-1] > arr[j]; j--) {
                swap(arr, j, j - 1);
            }
        }
    }

    public static int minKth4(int[] array, int k) {
        int[] arr = copyArray(array);
        return minKth4(arr,0,arr.length-1,k-1);
    }

    private static int minKth4(int[] arr, int l, int r, int k) {
        if (l==r) return arr[l];
        swap(arr,l,l+(int)(Math.random()*(r-l+1)));
        int index = partition4(arr, l, r);
        if (index==k) return arr[index];
        if (index<k)  return minKth4(arr,index+1,r,k);
        return minKth4(arr,l,index-1,k);
    }

    public static int minKth5(int[] array, int k) {
        int[] arr = copyArray(array);
        return minKth5(arr,0,arr.length-1,k-1);
    }
    private static int minKth5(int[] arr, int l, int r, int k) {
        while (l<r){
            swap(arr,l,l+(int)(Math.random()*(r-l+1)));
            int mid=partition4(arr,l,r);
            if (mid==k) return arr[mid];
            else if (mid>k) r=mid-1;
            else l=mid+1;
        }
        return arr[l];
    }

    private static int partition4(int[] arr,int l,int r){
        int p1=l,p2=r+1;
        while (p1<p2){
            while (++p1<=r&&arr[p1]<arr[l]);
            while (--p2>=l-1&&arr[p2]>arr[l]);
            swap(arr,p1<p2?p1:l,p2);
        }
        return p2;
    }



    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 10000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        int t=0;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth5(arr, k);
            int ans3 = minKth4(arr, k);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!"+(++t));
            }
        }
        System.out.println("test finish");
    }
}
