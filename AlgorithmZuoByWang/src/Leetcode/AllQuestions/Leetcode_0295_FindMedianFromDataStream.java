package Leetcode.AllQuestions;


import java.util.Comparator;
import java.util.PriorityQueue;
/**
 * 用堆来实现，这个结构允许接收重复值。用两个堆来组成这个结构，一个大根堆一个小根堆
 * 给你一堆数字。假设是645189，如果排序那么就是145689，我们要中位数，其实只关心5和6，别的我不关心
 * 所以我们可以把前半部分145进大根堆，后面部分678进小根堆。那么分别弹出的堆顶就是5和6，这就是我们想要的
 * 小根堆和大根堆的个数的差距要么是1要么是0，如果是1，那么个数多的那个堆的堆顶就直接是中位数，否则就是取平均。
 * 所以我们知道大根堆放小的元素，小根堆放大的元素
 */
public class Leetcode_0295_FindMedianFromDataStream {
	class MyComparator implements Comparator<Integer> {
		public int compare(Integer o1,Integer o2){
			return o2-o1;//大根堆，大的排前面
		}
	}
	class MedianFinder {
		PriorityQueue<Integer> minq;
		PriorityQueue<Integer> maxq;

		public MedianFinder() {
			minq=new PriorityQueue<>();//大的数放到小根堆。小根堆卡大数的瓶颈
			maxq=new PriorityQueue<>(new MyComparator());//小的数放到大根堆
		}

		public void addNum(int num) {
			if (minq.isEmpty()||num>=minq.peek()){//大的数放到小根堆
				minq.add(num);
			}else {
				maxq.add(num);
			}
			balance();
		}

		private void balance(){
			int sizeMinq=minq.size();
			int sizeMaxq=maxq.size();
			if (Math.abs(sizeMaxq-sizeMinq)>=2){//个数差值不能超过两个
				if (sizeMaxq>sizeMinq){
					minq.add(maxq.poll());
				}else {
					maxq.add(minq.poll());
				}
			}
		}

		public double findMedian() {
			int sizeMinq=minq.size();
			int sizeMaxq=maxq.size();
			if (sizeMinq==sizeMaxq){
				return ((double)maxq.peek()+(double)minq.peek())/2D;
			}else {
				return sizeMaxq>sizeMinq?maxq.peek():minq.peek();
			}
		}
	}
}