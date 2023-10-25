package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0472_ConcatenatedWords {
	/**
	 * 这题的几个要点：
	 * 1.你在乎某一个前缀有没有，就使用前缀树。
	 * 2.为了避免一开始全部一股脑加前缀树，我们先按照长度进行排序，这是因为长度大的一定是通过长度小的拆解的！
	 * 3.可以分解就不需要加前缀树了，不能分解才去加前缀树。题目只需要知道一个字符串能不能拆解就能加答案了，不需要知道具体是怎么拆解的
	 * 4.canSplit使用了find，属于是从头开始查。canSplit2是边动边查，效率高。属于对canSplit的优化
	 */
	class Node{
		Node[] nexts;
		boolean end;
		public Node(){
			nexts=new Node[26];
			end=false;
		}
	}

	Node root=new Node();

	public void insert(String s){
		int road;
		Node cur=root;
		for(char c:s.toCharArray()){
			road=c-'a';
			if(cur.nexts[road]==null) cur.nexts[road]=new Node();
			cur=cur.nexts[road];
		}
		cur.end=true;
	}

	public boolean find(String s){
		int road;
		Node cur=root;
		for(char c:s.toCharArray()){
			road=c-'a';
			if(cur.nexts[road]==null) return false;
			cur=cur.nexts[road];
		}
		return cur.end;
	}

	 public boolean canSplit(String s,int index){
	     char[] str=s.toCharArray();
	     int n=str.length;
	     if(index==n) return true;
	     String t="";
	     for(int i=index;i<n;i++){
	         t+=str[i];
	         if(find(t)&&canSplit(s,i+1)) return true;
	     }
	     return false;
	 }

	public boolean canSplit2(String s,int index){
		char[] str=s.toCharArray();
		int n=str.length,road;
		if(index==n) return true;
		Node cur=root;
		for(int i=index;i<n;i++){
			road=str[i]-'a';
			if(cur.nexts[road]==null) break;
			cur=cur.nexts[road];
			if(cur.end&&canSplit2(s,i+1)) return true;
		}
		return false;
	}


	public boolean canSplit3(String s,int index,HashMap<Integer,Boolean> map){//记忆化搜索
		if (map.containsKey(index)) return map.get(index);
		char[] str=s.toCharArray();
		int n=str.length,road;
		if(index==n) {
			map.put(index,true);
			return true;
		}
		Node cur=root;
		for(int i=index;i<n;i++){
			road=str[i]-'a';
			if(cur.nexts[road]==null) break;
			cur=cur.nexts[road];
			if(cur.end&&canSplit2(s,i+1)) {
				map.put(index,true);
				return true;
			}
		}
		map.put(index,false);
		return false;
	}

	public List<String> findAllConcatenatedWordsInADict(String[] words) {
		int n=words.length;
		List<String> ans=new LinkedList<>();
		if(n<3) return ans;
		Arrays.sort(words, Comparator.comparingInt(String::length));
		for(String s:words){
//			if(canSplit(s,0)) ans.add(s);
			if(canSplit2(s,0)) ans.add(s);//最快
//			if(canSplit3(s,0,new HashMap<>())) ans.add(s);
			else insert(s);
		}
		return ans;
	}


}
