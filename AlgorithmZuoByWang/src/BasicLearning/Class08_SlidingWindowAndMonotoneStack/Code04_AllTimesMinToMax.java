package BasicLearning.Class08_SlidingWindowAndMonotoneStack;

import java.util.Stack;

public class Code04_AllTimesMinToMax {

    /**
     * 从数组的每一个数作为最小值，找出扩展出来的最大子数组，因为这个数要作为最小值，所以数组里面的数都是要>=它。所以我们找出离它最近的
     * 小于它的左右两边的数L和R，(L,R)即是我们要找的最大子数组。我们要找出最近的大于index的数
     * while的判断条件就是只有arr[i]小于栈顶的时候才弹出，因为我们其实是设置栈顶，既然arr[i]<栈顶，那么栈顶就可以设置了
     * @param arr:
     * @return :
     */
    public static int max(int[] arr){
        int [] res=new int[arr.length];
        Stack<Integer> stack =new Stack<>();//存放下标
        int[] sum=new int[arr.length];
        sum[0]=arr[0];
        int max=Integer.MIN_VALUE;
        for (int i=1;i<arr.length;i++){
            sum[i]=sum[i-1]+arr[i];
        }
        for (int i=0;i<arr.length;i++){
            while(!stack.isEmpty()&&arr[i]<arr[stack.peek()]){
                int index =stack.pop();
                //栈为空说明左边没有比arr[index]大的数，换言之左边的全部范围都属于我们的最大子数组
                max=Math.max(max,arr[index]*(stack.isEmpty()?sum[i-1]:sum[i-1]-sum[stack.peek()]));
            }
            stack.push(i);
        }
        while(!stack.isEmpty()){
            int index=stack.pop();
            //栈为空说明右边没有比arr[index]大的数，换言之右边的全部范围都属于我们的最大子数组
            max=Math.max(max,arr[index]*(stack.isEmpty()?sum[arr.length-1]:sum[arr.length-1]-sum[stack.peek()]));
        }
        return max;
    }

    public static int baoli(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int minNum = Integer.MAX_VALUE;
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                    minNum = Math.min(minNum, arr[k]);
                }
                max = Math.max(max, minNum * sum);
            }
        }
        return max;
    }
    public static int[] gerenareRondomArray() {
        int[] arr = new int[(int) (Math.random() * 20) + 10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTimes = 2000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = gerenareRondomArray();
            if (baoli(arr) != max(arr)) {
                System.out.println("FUCK!");
                break;
            }
        }
        System.out.println("test finish");
    }


}
