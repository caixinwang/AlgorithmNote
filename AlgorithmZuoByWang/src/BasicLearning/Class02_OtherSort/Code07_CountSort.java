package BasicLearning.Class02_OtherSort;

public class Code07_CountSort {
    //0~200
    private static void countSort(int[] arr){
        int max=arr[0];
        for (int a:arr){
            max=a>max?a:max;
        }
        int count[]=new int[max+1];
        for (int a:arr)
            count[a]++;

        for (int i=0,j=0;i<count.length;i++){
            while(count[i]!=0){
                arr[j++]=i;
                count[i]--;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr={4,3,2,1,2,3,4,84,46,12,46,8,2,15,46,76};
        countSort(arr);
        for (int a:arr)
            System.out.print(a+" ");
    }
}
