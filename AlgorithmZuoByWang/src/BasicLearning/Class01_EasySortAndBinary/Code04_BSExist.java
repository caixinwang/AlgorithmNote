package BasicLearning.Class01_EasySortAndBinary;

import java.util.Arrays;

import static BasicLearning.Class01_EasySortAndBinary.Code01_SelectSort.*;

public class Code04_BSExist {
    public static boolean exist(int [] sortedArr,int num){
        if (sortedArr.length==0||sortedArr==null)
            return false;
        int left=0,right= sortedArr.length-1,mid;
        while(left <= right){
            mid=((right-left)>>1)+left;
            if (sortedArr[mid]>num)
                right=mid-1;
            else if (sortedArr[mid] < num)
                left=mid+1;
            else
                return true;
        }
        return false;
    }

    private static boolean Test(int [] sortedArr,int num ){
        if (sortedArr==null||sortedArr.length==0)
            return false;
        for (int a:sortedArr){
            if (a==num)
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int testTimes=1000;
        int maxSize=30;
        int maxValue=200;
        boolean isSuccess=true;

        for (int i=0;i<testTimes;i++){
            int [] arr1=generateArray(maxSize,maxValue);
            Arrays.sort(arr1);
            int checkNum=(int)((maxValue+1)*Math.random());
            if (Test(arr1,checkNum)!=exist(arr1,checkNum)){//只要有一次测试不成功就退出
                isSuccess=false;
                break;
            }
        }
        System.out.println(isSuccess?"Nice!":"Fucked!");
    }
}
