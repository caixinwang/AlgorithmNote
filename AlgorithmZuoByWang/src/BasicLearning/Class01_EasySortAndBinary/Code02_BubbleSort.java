package BasicLearning.Class01_EasySortAndBinary;

import static BasicLearning.Class01_EasySortAndBinary.Code01_SelectSort.*;

public class Code02_BubbleSort {

    public static void bubbleSort(int[] arr){
        for(int i = arr.length-1;i>0;i--){
            boolean flag=false;
            for (int j =0;j<i;j++){
                if(arr[j]>arr[j+1]){
                    swap(arr,j,j+1);
                    flag=true;
                }
            }
            if(!flag) return;
        }
    }

    public static void main(String[] args) {
        int testTimes=10000;
        int maxSize=30;
        int maxValue=200;
        boolean isSuccess=true;

        for (int i=0;i<testTimes;i++){
            int [] arr1=generateArray(maxSize,maxValue);
            int [] arr2=copyArray(arr1);
            bubbleSort(arr1);
            comparator(arr2);
            if(!isEqual(arr1,arr2)){
                isSuccess=false;
                //失败了！打印一个正确的和一个错误的给你看
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(isSuccess?"Nice!":"Fucked!");
        int [] arr =generateArray(maxSize,maxValue);
        printArray(arr);
        bubbleSort(arr);
        printArray(arr);
    }
}
