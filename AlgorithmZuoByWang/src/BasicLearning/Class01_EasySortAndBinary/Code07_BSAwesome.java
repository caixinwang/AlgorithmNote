package BasicLearning.Class01_EasySortAndBinary;


public class Code07_BSAwesome {
    /**
     * 这里的局部最小值是严格的，两边的数都要比自己大才行，等于也不行
     * @param arr：arr中一定至少存在一个局部最小值并且数组不要求是有序数组但是要求相邻不相等
     * @return 返回arr数组的局部最小值的下标
     */
    private static int getLessIndex(int[] arr) {
        if (arr == null || arr.length < 2) return -1;
        if (arr[0] < arr[1]) return 0;
        if (arr[arr.length - 1] < arr[arr.length - 2]) return arr.length - 1;
        int left = 1, right = arr.length - 2, mid = 0;//0位置和len-1位置单独判断过
        while (left <= right) {
            mid = ((right - left) >> 1) + left;//mid如果是局部最小值，那么从mid-1或者mid+1到mid不可能是增的
            if (arr[mid] >= arr[mid + 1])
                left = mid + 1;//如果斜率小于0，则迭代left
            else if (arr[mid] >= arr[mid - 1])
                right = mid - 1;//如果斜率大于0，则迭代right
            else
                return mid;//此时mid就是局部最小值
        }
        return -1;//不可能在这里返回-1，一个数组一定有局部最小值
    }

    private static boolean isLessIndex(int[] arr, int lessIndex) {
        if (arr == null ||arr.length < 2 )
            return true;
        if (lessIndex == 0)
            return arr[lessIndex] < arr[lessIndex + 1];
        if (lessIndex == arr.length - 1)
            return arr[lessIndex] < arr[lessIndex - 1];
        return arr[lessIndex] < arr[lessIndex + 1] && arr[lessIndex] < arr[lessIndex - 1];
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {//相邻不相等
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        arr[0] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
        for (int i = 1; i < arr.length; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
            } while (arr[i] == arr[i - 1]);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTimes = 100000;
        int maxSize = 30;
        int maxValue = 200;
        boolean isSuccess = true;

        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int[] arr2={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
//            Arrays.sort(arr);//加了sort之后不行的原因是相邻不相等，sort之后不相邻的相等的数排在一起了
//            int checkNum=(int)((maxValue+1)*Math.random());
            int a=getLessIndex(arr);
            if (!isLessIndex(arr,a)) {//只要有一次测试不成功就退出
                isSuccess = false;
                break;
            }
        }
        System.out.println(isSuccess ? "Nice!" : "Fucked!");
    }
}
