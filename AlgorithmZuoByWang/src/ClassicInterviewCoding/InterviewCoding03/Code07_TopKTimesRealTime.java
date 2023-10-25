package ClassicInterviewCoding.InterviewCoding03;

import java.util.HashMap;

public class Code07_TopKTimesRealTime {

	public static class Node {
		public String str;
		public int times;

		public Node(String s, int t) {
			str = s;
			times = t;
		}
	}

	public static class TopK {
		private String[] heap;
		private int size;

		private int topk;
		//在不影响swap的情况下去更新额外的结构
		private HashMap<String, Integer> timesMap;//string ---> times , 比较的依据
		private HashMap<String, Integer> indexMap;//快速找到string所在的位置 ， 更新位置要放在合理的位置

		public TopK(int K) {
			heap = new String[K + 2];//k+1 +1 ,多一个位置是因为add的过程需要多一个空间
			topk =K;
			size = 0;
			timesMap = new HashMap<String, Integer>();
			indexMap = new HashMap<String, Integer>();
		}

		private boolean less(int a, int b) {//times小才是真的小
			return timesMap.get(heap[a]) < timesMap.get(heap[b]);
		}

		private void swap(int a, int b) {
			indexMap.put(heap[a], b);
			indexMap.put(heap[b], a);
			String tmp = heap[a];
			heap[a] = heap[b];
			heap[b] = tmp;
		}

		private void swim(int k) {
			for (; k > 1 && less(k, k >> 1); k >>= 1) swap(k, k >> 1);
		}

		private void sink(int k) {
			while (k << 1 <= size) {
				int child=k<<1;
				if (child+1<=size&&less(child+1,child)) child++;
				if (less(k,child)) break;
				swap(k,child);
				k=child;
			}
		}

		public void add(String str) {
			if (timesMap.containsKey(str)){
				Integer times = timesMap.get(str);
				timesMap.put(str,times+1);
				if (indexMap.get(str)!=-1){
					sink(indexMap.get(str));
				}else {
					indexMap.put(str,size+1);//插放前，删放后
					heap[++size]=str;
					swim(size);
					if (size> topk){
						swap(1,size--);
						sink(1);
						indexMap.put(heap[size+1],-1);//只能放在不影响swap的地方
					}
				}
			}else {
				timesMap.put(str,1);
				indexMap.put(str,size+1);
				heap[++size]=str;
				swim(size);
				if (size> topk){
					swap(1,size--);
					sink(1);
					indexMap.put(heap[size+1],-1);
				}
			}
		}

		public void printTopK() {
			for (int i = 1; i <= topk; i++) {
				System.out.println(heap[i]+":"+timesMap.get(heap[i]));
			}
		}


	}

	public static String[] generateRandomArray(int len, int max) {
		String[] res = new String[len];
		for (int i = 0; i != len; i++) {
			res[i] = String.valueOf((int) (Math.random() * (max + 1)));
		}
		return res;
	}

	public static void printArray(String[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		TopK record = new TopK(2);
		record.add("zuo");
		record.printTopK();
		System.out.println("=======");
		record.add("cheng");
		record.add("cheng");
		record.printTopK();
		System.out.println("=======");
		record.add("Yun");
		record.add("Yun");
		record.printTopK();
		System.out.println("=======");
		record.add("yes");
		record.add("yes");
		record.add("yes");
		record.printTopK();


	}
}