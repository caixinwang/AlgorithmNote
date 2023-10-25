package ClassicInterviewCoding.InterviewCoding11;

import java.util.HashMap;
import java.util.Map.Entry;

public class Code05_FindKMajority {

	public static void printHalfMajor(int[] arr) {
		if (arr==null||arr.length==0) return;
		int cand=-1;
		int hp=0;
		for (int i = 0; i < arr.length; i++) {
			if (hp==0) {//没有候选
				cand = arr[i];
				hp++;
			}
			else if (cand==arr[i]){//有候选，但是候选是自己
				hp++;
			}else {//候选不是自己
				hp--;
			}
		}
		if (hp==0) {
			System.out.println("no cand");
			return;
		}
		hp=0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i]==cand)hp++;
		}
		if (hp>arr.length/2) System.out.println("cand:"+cand);
		else System.out.println("no cand");
	}

	public static void printKMajor(int[] arr, int k) {
		if (arr==null||arr.length==0||k<2) return;
		HashMap<Integer,Integer> cand=new HashMap<>();
		for (int i = 0; i < arr.length; i++) {
			if (cand.containsKey(arr[i])){//在里面
				cand.put(arr[i],cand.get(arr[i])+1 );
			}else {//不是候选
				if (cand.size()==k-1){//候选满了
					for (Entry<Integer, Integer> entry : cand.entrySet()) {
						Integer key = entry.getKey();
						Integer value = entry.getValue();
						if (value==1) cand.remove(key);
						else cand.put(key,value-1);
					}
				}else {//候选没满
					cand.put(arr[i],1);
				}
			}
		}
		for (Integer key : cand.keySet()) {//开始验证里面的候选，先把hp都设置为0
			cand.put(key,0);
		}
		for (int i = 0; i < arr.length; i++) {//加血
			if (cand.containsKey(arr[i])) cand.put(arr[i],1+cand.get(arr[i]));
		}
		for (Entry<Integer, Integer> entry : cand.entrySet()) {
			Integer key = entry.getKey();
			Integer value = entry.getValue();
			if (value>arr.length/k) System.out.print(key+" ");//验血并打印
		}
	}


	public static void main(String[] args) {
		int[] arr = { 1, 2, 3, 1, 1, 2, 1 };
		printHalfMajor(arr);
		int K = 4;
		printKMajor(arr, K);
	}

}
