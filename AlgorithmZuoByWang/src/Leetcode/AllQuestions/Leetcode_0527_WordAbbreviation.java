package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Leetcode_0527_WordAbbreviation {
	//先全部搞成最精简的形式，如果不能区分开，就增加前缀，直到可以区分开。
	//1.首先先生成一个最简的答案，然后再去调整。我们将某一个缩写放到一个map中，并且记录哪些位置的单词拥有这个缩写
	//2.如果有其中一个缩写不止一个下标拥有，那么就将这些下标全部拿出来，增加一个长度的前缀，直到所有的缩写都不重复
	public List<String> wordsAbbreviation(List<String> words) {
		List<String> ans=new ArrayList<>();
		int n=words.size();
		HashMap<String,List<Integer>> map=new HashMap<>();//记录着某一个缩写有哪些位置的单词拥有
		for (String word : words) {
			String s=getstr(word,1);
			ans.add(s);
			if (!map.containsKey(s)) map.put(s,new ArrayList<>());
			map.get(s).add(ans.size()-1);//下标就是此时ans的大小-1
		}
		int[] prefix=new int[n];
		Arrays.fill(prefix,2);//重做的起始标准是2的前缀长度
		for (int i = 0; i < n; i++) {//逐个检查ans的答案，确保这个缩写没有重复，利用map来确定是否重复
			while(map.get(ans.get(i)).size()>1){//有重复，重复的那些单词全部重做！
				List<Integer> list=map.get(ans.get(i));//把缩写重复的单词抓出来
				map.remove(ans.get(i));//移除这个缩写，这个缩写是不合法的
				for (int index:list){
					String s=getstr(words.get(index),prefix[index]++);//重做这个单词
					ans.set(index,s);//覆盖原来的缩写
					if (!map.containsKey(s)) map.put(s,new ArrayList<>());
					map.get(s).add(index);//更新map
				}
			}
		}
		return ans;
	}
	public String getstr(String s,int prefix){//把字符串s按照prefix的前缀长度，1的后缀长度进行缩写。
		if (s.length()<=3||s.length()-1-prefix<=1) return s;//题目要求缩写之后长度与原串一样就保留原串
		return new StringBuilder()
				.append(s.substring(0,prefix))
				.append(s.length()-1-prefix)
				.append(s.charAt(s.length()-1))
				.toString();
	}

}
