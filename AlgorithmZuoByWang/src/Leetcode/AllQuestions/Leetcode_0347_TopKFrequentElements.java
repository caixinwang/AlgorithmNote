package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0347_TopKFrequentElements {

	public static class Node {
		public int num;
		public int count;

		public Node(int val) {
			num = val;
			count = 1;
		}
	}

	public static class CountComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.count - o2.count;
		}

	}

	//使用一个k大小的小根堆，不要看他是小根堆，其实它里面放的都是大的值，只不过成为这些大的值的门槛是小根堆的堆顶。NlogK
	//如果你用大根堆，你就不知道一个数进不进，你只能让他们全进来，最后依次弹出k个，这样复杂度就是NlogN。
	public static int[] topKFrequent(int[] nums, int k) {
		HashMap<Integer, Node> map = new HashMap<>();
		for (int num : nums) {
			if (!map.containsKey(num)) {
				map.put(num, new Node(num));
			} else {
				map.get(num).count++;
			}
		}
		PriorityQueue<Node> heap = new PriorityQueue<>(new CountComparator());
		for (Node node : map.values()) {
			if (heap.size() < k || (heap.size() == k && node.count > heap.peek().count)) {
				heap.add(node);
			}
			if (heap.size() > k) {
				heap.poll();
			}
		}
		int[] ans = new int[k];
		int index = 0;
		while (!heap.isEmpty()) {
			ans[index++] = heap.poll().num;
		}
		return ans;
	}

	//快排，理论上最快，O(N)
	public static int[] topKFrequent2(int[] nums, int k) {
		HashMap<Integer,Node> map=new HashMap<>();
		for (int num:nums){
			if (!map.containsKey(num)){
				map.put(num,new Node(num));
			}else {
				map.get(num).count++;
			}
		}
		Node[] nodes=new Node[map.size()];
		int index=0;
		for (Integer num : map.keySet()) {
			nodes[index++]=map.get(num);
		}
		Node topK = findKthMin(nodes, 0, nodes.length-1, nodes.length-k);
		int count=topK.count;
		index=0;
		int[] ans=new int[k];
		for (int i = 0; i < nodes.length&&index<k; i++) {
			if (nodes[i].count>=count) ans[index++]=nodes[i].num;
		}
		return ans;
	}

	public static Node findKthMin(Node[] arr,int l,int r,int k){
		while(l<r){
			swap(arr,l,l+(int)(Math.random()*(r-l+1)));
			int mid=partition(arr,l,r);
			if (mid==k) return arr[mid];
			else if (mid>k) r=mid-1;
			else l=mid+1;
		}
		return arr[l];
	}

	public static int partition(Node[] arr,int l,int r){
		int p1=l,p2=r+1,pivot=arr[l].count;
		while(p1<p2){
			while(++p1<=r&&arr[p1].count<pivot);
			while(--p2>=l+1&&arr[p2].count>pivot);
			swap(arr,p1<p2?p1:l,p2);
		}
		return p2;
	}

	public static void swap(Node[] arr,int a,int b){
		Node t=arr[a];
		arr[a]=arr[b];
		arr[b]=t;
	}


}
