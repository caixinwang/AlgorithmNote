package BasicLearning.Class06_GreedUnionFind;

import java.util.PriorityQueue;

public class Code03_LessMoneySplitGold {

    public static int lessMoney1(int[]arr){
        if (arr==null||arr.length==0){
            return 0;
        }
        return process1(arr,0);
    }

    /**
     *暴力的思路就是不断的去更新这个less。和安排会议思路差不多
     * @param arr:现在需要合并的金条，逆向思维寻求最小金额。
     * @param preMoney:因为是递归函数，子调用需要从父调用中得知之前已经累计了多少金额
     * @return
     */
    public static int process1(int []arr,int preMoney){
        if (arr.length==1){
            return preMoney;
        }
        int less=Integer.MAX_VALUE;
        for (int i=0;i<arr.length;i++){
            for (int j=i+1;j<arr.length;j++){
                int []p=mergeTwoNum(arr,i,j);
                less=Math.min(less,process1(p,preMoney+arr[i]+arr[j]));
            }
        }
        return less;
    }

    public static int[] mergeTwoNum(int[]arr,int i,int j){
        int[] p=new int[arr.length-1];
        int index=0;//p
        for (int k=0;k<arr.length;k++){
            if (k!=i&&k!=j){
                p[index++]=arr[k];
            }
        }
        p[index]=arr[i]+arr[j];
        return p;
    }

    public static int lessMoney2(int[] arr){
        PriorityQueue<Integer> heap=new PriorityQueue<>();
        for (int a:arr){
            heap.add(a);
        }
        int sum=0;
        int cur=0;
        while (heap.size()>=2){
            cur=heap.poll()+heap.poll();//产生这次要付的钱
            sum+=cur;
            heap.add(cur);
        }
        return sum;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (lessMoney1(arr) != lessMoney2(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
