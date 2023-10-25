package ClassicInterviewCoding.InterviewCoding03;

import java.util.*;

// 本文件是Code07_TopKTimesRealTime的线上测试
// 测试链接：https://www.lintcode.com/problem/top-k-frequent-words-ii/description
// 把以下的代码粘贴进java环境编辑器
// 以上的代码不要粘贴
// 可以直接通过
public class TopK {

	public static class Node {
		public String str;
		public int times;

		public Node(String s, int t) {
			str = s;
			times = t;
		}
	}

	private String[] heap;
	private int size;

	private int topk;
	//在不影响swap的情况下去更新额外的结构
	private HashMap<String, Integer> timesMap;//string ---> times
	private HashMap<String, Integer> indexMap;//快速找到string所在的位置

	public TopK(int K) {
		heap = new String[K + 2];//k+1 +1 ,多一个位置是因为add的过程需要多一个空间
		topk =K;
		size = 0;
		timesMap = new HashMap<String, Integer>();
		indexMap = new HashMap<String, Integer>();
	}

	private boolean less(int a, int b) {//times小才是真的小,time一样的话
		String str1=heap[a];
		String str2=heap[b];
		int times1=timesMap.get(str1);
		int times2=timesMap.get(str2);
		boolean directory= str1.compareTo(str2) > 0;//字典序大的让他上去（等着删除），小的留在下面
		return times1==times2?directory:times1<times2;
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
				indexMap.put(str,size+1);
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


	/*
	 * @return: the current top k frequent words.
	 */
	public List<String> topk() {
		List<String> list = new ArrayList<>();
		for (int i=1;i<=size;i++) list.add(heap[i]);
		list.sort((String a,String b)-> timesMap.get(b)!=timesMap.get(a)?timesMap.get(b)-timesMap.get(a):b.compareTo(a));
		return list;
	}

	public static void main(String[] args) {
		TopK topK=new TopK(1);
//        topK.add("lint");
//        topK.add("code");
//        topK.add("code");
		topK.add("aa");
		topK.add("ab");
		List<String> topk1 = topK.topk();
		for (String s : topk1) {
			System.out.println(s);
		}
	}
}