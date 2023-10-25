package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0049_GroupAnagrams {
	public List<List<String>> groupAnagrams(String[] strs) {// O（k² * N）
		List<List<String>> ans=new LinkedList<>();
		if (strs==null||strs.length==0) return ans;
		HashMap<String,Integer> rest=new HashMap<>();
		for (String str : strs) {
			if (!rest.containsKey(str)){
				rest.put(str,0);
			}
			rest.put(str,rest.get(str)+1);
		}
		for (String str : strs) {
			if (rest.size()==0) break;
			List<String> permutations = getPermutations(str);
			List<String> list=new LinkedList<>();
			for (String permutation : permutations) {
				if (rest.containsKey(permutation)){
					int times=rest.get(permutation);
					rest.remove(permutation);
					for (int i = 0; i < times; i++) {
						list.add(permutation);
					}
				}
			}
			if (list.size()!=0) ans.add(list);
		}
		return ans;
	}
	
	public List<String> getPermutations(String str){//返回全排列
		List<String> res=new LinkedList<>();
		char[] s = str.toCharArray();
		f(s,0,res);
		return res;
	}

	private void f(char[] s, int index, List<String> res) {
		if (index==s.length){
			res.add(String.valueOf(s));
			return;
		}
		HashSet<Character> set=new HashSet<>();
		for (int i=index;i<s.length;i++){
			if (!set.contains(s[i])){
				set.add(s[i]);
				swap(s,index,i);
				f(s,index+1,res);
				swap(s,index,i);
			}
		}
	}

	public void swap(char[]arr,int a,int b){
		char t=arr[a];
		arr[a]=arr[b];
		arr[b]=t;
	}

	public List<List<String>> groupAnagrams2(String[] strs) {// O（N² * k）
		List<List<String>> ans=new LinkedList<>();
		HashMap<String,Integer> rest=new HashMap<>();//统计词频
		for (String str : strs) {
			if (!rest.containsKey(str)){
				rest.put(str,0);
			}
			rest.put(str,rest.get(str)+1);
		}
		for (int i = 0; i < strs.length; i++) {//O(N*N)
			if (rest.get(strs[i])>0){//strs[i]还没有被拿完
				List<String> list=new ArrayList<>();
				list.add(strs[i]);
				rest.put(strs[i],rest.get(strs[i])-1);
				for (int j=i+1;j<strs.length;j++){//往后去找strs[i]的字母异位词
					if (isok(strs[i],strs[j])){ //O(k)
						list.add(strs[j]);
						rest.put(strs[j],rest.get(strs[j])-1);
					}
				}
				ans.add(list);
			}
		}
		return ans;
	}

	public boolean isok(String str1,String str2){
		if (str1.length()!=str2.length()) return false;
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int[] count1=new int[128];
		int[] count2=new int[128];
		for (int i = 0; i < s1.length; i++) {
			count1[s1[i]]++;
			count2[s2[i]]++;
		}
		for (int i = 0; i < count1.length; i++) {
			if (count1[i]!=count2[i]) return false;
		}
		return true;
	}

	/**
	 * 方法2对于遍历到的每一个字符串都要往后面去找一遍，时间复杂度N²，太慢了。
	 * 实际上这题只要是字母异位词，那么生成出来的字符-次数表一定是一样的，我们可以把这个表变成一个cstr，作为HashMap的key
	 * 那么这样我们只需要遍历一遍，生成对应的cstr然后把str加入到对应HashMap中，HashMap的value是一个List，放的就是
	 * 字母异位词的集合
	 */
	public List<List<String>> groupAnagrams3(String[] strs) {// O（N * k）
		HashMap<String,List<String>> map=new HashMap<>();
		for (String str : strs) {
			String cstr = getCstr(str);
			if (!map.containsKey(cstr)){
				map.put(cstr,new LinkedList<>());
			}
			map.get(cstr).add(str);
		}
		List<List<String>> ans=new LinkedList<>();
		for (String s : map.keySet()) {
			ans.add(map.get(s));
		}
		return ans;
	}

	public String getCstr(String s){//题目说仅包含小写字母
		char[] chars = s.toCharArray();
		int[] count=new int[26];
		for (char aChar : chars) {
			count[aChar-'a']++;
		}
		StringBuilder builder=new StringBuilder();
		for (int i = 0; i < count.length; i++) {
			builder.append(count[i]).append((char)('a'+i));
		}
		return builder.toString();
	}

	/**
	 * 将方法3的getCstr用排序来代替。
	 * 总之都是得到一个类别的代表串，然后用这个来把集合分到HashMap里面。
	 */
	public List<List<String>> groupAnagrams4(String[] strs) {// O（N * k）
		HashMap<String,List<String>> map=new HashMap<>();
		for (String str : strs) {
			char[] chars = str.toCharArray();
			Arrays.sort(chars);
			String cstr = String.valueOf(chars);
			if (!map.containsKey(cstr)){
				map.put(cstr,new ArrayList<>());
			}
			map.get(cstr).add(str);
		}
		List<List<String>> ans=new ArrayList<>();
		for (String s : map.keySet()) {
			ans.add(map.get(s));
		}
		return ans;
	}


}
