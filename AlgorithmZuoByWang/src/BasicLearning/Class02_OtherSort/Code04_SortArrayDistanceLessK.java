package BasicLearning.Class02_OtherSort;

import java.util.PriorityQueue;

/**
 * 已知一个几乎有序的数组，几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离可以不超过k，
 * 并且k相对于数组来说比较小。请选择一个合适的 排序算法针对这个数据进行排序。
 *
 */
public class Code04_SortArrayDistanceLessK {

    private static void sort(int[]arr ,int k){

        PriorityQueue<Integer>heap=new PriorityQueue<>();//在java中优先级队列就是最小堆---PriorityQueue
        int index=0;//用来放进堆
        for (;index<Math.min(arr.length,k+1);index++){
            heap.add(arr[index]);
        }
        int i=0;//用来指示排序的进度
        for (;index<arr.length;i++,index++){
            arr[i]=heap.poll();
            heap.add(arr[index]);
        }
        while(i<arr.length){
            arr[i++]=heap.poll();
        }

    }
}
