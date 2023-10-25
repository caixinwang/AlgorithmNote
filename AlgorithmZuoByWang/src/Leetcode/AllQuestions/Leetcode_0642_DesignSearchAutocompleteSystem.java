package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class Leetcode_0642_DesignSearchAutocompleteSystem {
	//依然是前缀树的应用，只不过在每一个点上多存了一张表map，表示路过这个结点的有些字符串，以及他们的次数 [可以可以使用外挂表，不在内部定义]
	//以及需要father指针实现删掉字符的回溯。
	// 因为用户是一个字符一个字符的输入，所以我们需要搞一个历史，只要用户不是input("#")，那么用户之前的历史要一直留着。
	//更新有序表的时候，我们只能先删再加
	class AutocompleteSystem {

		public class TrieNode {
			public TrieNode father;
			public String path;
			public TrieNode[] nexts;

			public TrieNode(TrieNode f, String p) {
				father = f;
				path = p;
				nexts = new TrieNode[27];
			}
		}

		public class WordCount implements Comparable<WordCount> {
			public String word;
			public int count;

			public WordCount(String w, int c) {
				word = w;
				count = c;
			}

			public int compareTo(WordCount o) {
				return count != o.count ? (o.count - count) : word.compareTo(o.word);
			}
		}

		// 题目的要求，只输出排名前3的列表
		public final int top = 3;
		public final TrieNode root = new TrieNode(null, "");
		// 某个前缀树节点，上面的有序表，不在这个节点内部
		// 外挂
		public HashMap<TrieNode, TreeSet<WordCount>> nodeRankMap = new HashMap<>();
		
		// 字符串 "abc"  7次   ->  ("abc", 7)
		public HashMap<String, WordCount> wordCountMap = new HashMap<>();

		
		public String path;
		public TrieNode cur;

		// ' ' -> 0
		// 'a' -> 1
		// 'b' -> 2
		// ...
		// 'z' -> 26 
		//  '`'  a b  .. z
		private int f(char c) {
			return c == ' ' ? 0 : (c - '`');
		}

		public AutocompleteSystem(String[] sentences, int[] times) {
			path = "";
			cur = root;
			for (int i = 0; i < sentences.length; i++) {
				String word = sentences[i];
				int count = times[i];
				WordCount wc = new WordCount(word, count - 1);
				wordCountMap.put(word, wc);
				for (char c : word.toCharArray()) {
					input(c);
				}
				input('#');
			}
		}

		// 之前已经有一些历史了！
		// 当前键入 c 字符
		// 请顺着之前的历史，根据c字符是什么，继续
		// path : 之前键入的字符串整体
		// cur : 当前滑到了前缀树的哪个节点

		public List<String> input(char c) {
			List<String> ans = new ArrayList<>();
			if (c != '#') {
				path += c;
				int i = f(c);
				if (cur.nexts[i] == null) {
					cur.nexts[i] = new TrieNode(cur, path);
				}
				cur = cur.nexts[i];
				if (!nodeRankMap.containsKey(cur)) {
					nodeRankMap.put(cur, new TreeSet<>());
				}
				int k = 0;
				// for循环本身就是根据排序后的顺序来遍历！
				for (WordCount wc : nodeRankMap.get(cur)) {
					if (k == top) {
						break;
					}
					ans.add(wc.word);
					k++;
				}
			}
			// c = #   path = "abcde" 
			// #
			// #
			// #
			// a b .. #
			if (c == '#' && !path.equals("")) {
				// 真的有一个，有效字符串需要加入！path
				if (!wordCountMap.containsKey(path)) {
					wordCountMap.put(path, new WordCount(path, 0));
				}
				// 有序表内部的小对象，该小对象参与排序的指标数据改变
				// 但是有序表并不会自动刷新
				// 所以，删掉老的，加新的！
				WordCount oldOne = wordCountMap.get(path);
				WordCount newOne = new WordCount(path, oldOne.count + 1);
				while (cur != root) {
					nodeRankMap.get(cur).remove(oldOne);
					nodeRankMap.get(cur).add(newOne);
					cur = cur.father;
				}
				wordCountMap.put(path, newOne);
				path = "";
				// cur 回到头了
			}
			return ans;
		}

	}

	static class AutocompleteSystem2 {
		class Node{
			Node father;
			String path;
			Node[] nexts;
			TreeSet<WordCount> rankSet;

			public Node(Node f,String p){
				father=f;
				path=p;
				nexts=new Node[27];
				rankSet=new TreeSet<>((a,b)->a.cnt!=b.cnt?b.cnt-a.cnt:a.path.compareTo(b.path));
			}

		}

		class WordCount{
			String path;
			int cnt;
			public WordCount(String p,int c){
				path=p;
				cnt=c;
			}
		}

		Node root=new Node(null,"");//到root就停，所以根节点不需要指向自己，和并查集不一样
		final int top=3;
		HashMap<String,WordCount> wordsMap=new HashMap<>();
		Node cur=root;
		String path="";

		private int road(char c){return c==' '?26:c-'a';}

		public AutocompleteSystem2(String[] sentences, int[] times) {
			int n=sentences.length;
			for(int i=0;i<n;i++){
				WordCount wc=new WordCount(sentences[i],times[i]);
				wordsMap.put(sentences[i],wc);
				path="";
				cur=root;
				for(char c:sentences[i].toCharArray()){
					int road=road(c);
					path+=c;
					if(cur.nexts[road]==null)cur.nexts[road]=new Node(cur,path);
					cur=cur.nexts[road];
					cur.rankSet.add(wc);
				}
			}
			path="";
			cur=root;
		}

		public List<String> input(char c) {
			List<String> ans=new ArrayList<>();
			if(c=='#'){
				if(!wordsMap.containsKey(path)) wordsMap.put(path,new WordCount(path,0));//统一处理逻辑
				WordCount owc=wordsMap.get(path);//删掉老的
				WordCount nwc=new WordCount(path,owc.cnt+1);//加入新的
				wordsMap.put(path,nwc);
				for(;cur!=root;cur=cur.father){
					cur.rankSet.remove(owc);
					cur.rankSet.add(nwc);
				}
				path="";//此时cur出了for，就是root了
			}
			if(c!='#'){
				path+=c;
				int road=road(c);
				if(cur.nexts[road]==null) cur.nexts[road]=new Node(cur,path);
				cur=cur.nexts[road];
				int n=top;
				for(WordCount wc:cur.rankSet){
					if(n--==0) break;
					ans.add(wc.path);
				}
			}
			return ans;
		}
	}

	public static void main(String[] args) {
		String[] words=new String[]{
				"i love you","island","iroman","i love leetcode"
		};
		int[] times=new int[]{5,3,2,2};
		AutocompleteSystem2 as=new AutocompleteSystem2(words,times);
		as.input('i');
		as.input('b');
		as.input('a');
		as.input('#');
	}
}
