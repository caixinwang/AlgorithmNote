package ClassicInterviewCoding.InterviewCoding21;

import TestUtils.ArrayUtil;
import TestUtils.StringUtil;

import java.util.*;

public class Code01_PalindromePairs {

	//
	public static List<List<Integer>>  palindromePairs(String[] words){
		if (words==null||words.length<2) return null;
		HashMap<String,Integer> indexMap=new HashMap<>();
		for (int i = 0; i < words.length; i++) {
			indexMap.put(words[i],i);//字符串不重复
		}
		List<List<Integer>> ans=new ArrayList<>();
		for (int j = 0; j < words.length; j++) {
			String word=words[j];
			String reword = reverse(word);
			if (word.equals("")){
				for (int i = 0; i < words.length; i++) {
					if (i!=j&&isPalindrome(words[i])){//不能是空串自己
						addAns(ans,i,j);
						addAns(ans,j,i);
					}
				}
				continue;
			}
			if (indexMap.containsKey(reword)){//没有前缀,整体看一下
				addAns(ans,indexMap.get(reword),j);
			}
			int[] parr = manacher(word);//word[0~i]前缀串如果是回文串，那么parr[i+1]=i+1
			int p=0;//0~p的前缀串是回文串我才看
			while(p<word.length()-1){//全是前缀对应找空串，这个我们处理过了
				if (p+1==parr[p+1]){//说明word[0~p]是一个前缀串
					String other = word.substring(p + 1);//后面的那一部分
					String reverse = reverse(other);
					if (indexMap.containsKey(reverse)){
						List<Integer> pair=new ArrayList<>();
						pair.add(indexMap.get(reverse));
						pair.add(j);
						ans.add(pair);
					}
				}
				p++;
			}
			parr=manacher(reword);//parr对应的是后缀串
			p=0;//0~p前缀串代表N-1-p~N-1
			while(p<word.length()-1){//全是后缀对应找空串，这个我们处理过了
				if (p+1==parr[p+1]){//说明reword[0~p]前缀串是一个回文,那么说明word[N-1-p,N-1]后缀串是回文
					String other = word.substring(0,word.length()-1-p);//word[0~N-1-p-1]
					String reverse = reverse(other);
					if (indexMap.containsKey(reverse)){
						List<Integer> pair=new ArrayList<>();
						pair.add(j);
						pair.add(indexMap.get(reverse));
						ans.add(pair);
					}
				}
				p++;
			}
		}
		return ans;
	}

	public static void addAns(List<List<Integer>> ans,int i,int j){
		if (i==j) return;
		List<Integer> pair=new ArrayList<>();
		pair.add(i);
		pair.add(j);
		ans.add(pair);
	}

	public static String reverse(String s){
		if (s==null||s.length()<=1) return s;
		char[] str = s.toCharArray();
		int l=0,r=str.length-1;
		while(l<r){
			char t=str[l];
			str[l]=str[r];
			str[r]=t;
			l++;
			r--;
		}
		return String.valueOf(str);
	}

	public static int[] manacher(String s) {//直接由right2改过来
		if (s == null || s.length() == 0) return null;
		char[] str = manacherString(s);
		int[] parr=new int[str.length];
		int c=-1,r=-1,p=0;//[c-p,r] r=c+p
		for (int i = 0; i < str.length; i++) {
			p=i<=r?Math.min(r-i,parr[2*c-i]):0;
			while(i-p-1>=0&&i+p+1<str.length&&str[i-p-1]==str[i+p+1])  p++;
			if (i+p>r){
				c=i;
				r=i+p;
			}
			parr[i]=p;
		}
		return parr;
	}

	public static char[] manacherString(String str) {
		char[] s = str.toCharArray();
		char[] res=new char[2* s.length+1];
		int index=0;
		for (int i = 0; i < res.length; i++) {
			if ((i&1)==0) res[i]='#';
			else res[i]=s[index++];
		}
		return res;
	}

	public static boolean isPalindrome(String s){
		if (s==null) return false;
		if (s.length()==0||s.length()==1) return true;
		char[] str = s.toCharArray();
		int N=str.length;
		char[] reverse=reverse(s).toCharArray();
		for (int i = 0; i < reverse.length; i++) {
			if (reverse[i]!=str[i]) return false;
		}
		return true;
	}

	public static List<List<Integer>> palindromePairs2(String[] words) {
		if (words==null||words.length<2) return null;
		HashMap<String,List<Pari>> map=new HashMap<>();
		for (int i = 0; i < words.length; i++) {
			for (int j = 0; j < words.length; j++) {
				if (j!=i){
					String s =words[i]+words[j];
					if (!map.containsKey(s))  map.put(s,new ArrayList<>());
					map.get(s).add(new Pari(i,j));
				}
			}
		}
		List<List<Integer>> res=new ArrayList<>();
		for (String s : map.keySet()) {
			if (isPalindrome(s)){
				List<Pari> paris = map.get(s);
				for (Pari pari : paris) {
					List<Integer> list=new ArrayList<>();
					list.add(pari.x);
					list.add(pari.y);
					res.add(list);
				}
			}
		}
		return res;
	}

	static class Pari{
		public int x;
		public int y;

		public Pari(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj==null) return false;
			Pari p= (Pari) obj;
			return x==p.x&&y==p.y;
		}
	}

	static class PairComparator implements Comparator<Pari>{
		@Override
		public int compare(Pari o1, Pari o2) {
			return o1.x!=o2.x?o1.x-o2.x:o1.y-o2.y;
		}
	}

	//for test {[1,2],[2,3],[2,4]}  =>  {Paris}
	public static boolean equals( List<List<Integer>> lists1, List<List<Integer>> lists2){
		if (lists1==null&&lists2==null)  return true;
		if (lists1==null||lists2==null) return false;
		if(lists1.size()!=lists2.size()) return false;
		List<Pari> l1=new ArrayList<>();
		List<Pari> l2=new ArrayList<>();
		for (List<Integer> list : lists1) {
			if (list.size()!=2) return false;
			Pari p=new Pari(list.get(0),list.get(1));
			l1.add(p);
		}
		for (List<Integer> list : lists2) {
			if (list.size()!=2) return false;
			Pari p=new Pari(list.get(0),list.get(1));
			l2.add(p);
		}
		Collections.sort(l1,new PairComparator());
		Collections.sort(l2,new PairComparator());
		for (int i = 0; i < l1.size(); i++) {
			if (!l1.get(i).equals(l2.get(i))) return false;
		}
		return true;
	}

	public static void  test1(){
		List<List<Integer>> lists1=new ArrayList<>();
		List<Integer> list1=new ArrayList<>();
		list1.add(1);
		list1.add(2);
		List<Integer> list2=new ArrayList<>();
		list2.add(2);
		list2.add(4);
		List<Integer> list3=new ArrayList<>();
		list3.add(6);
		list3.add(7);
		lists1.add(list1);
		lists1.add(list2);
		lists1.add(list3);

		List<List<Integer>> lists2=new ArrayList<>();
		List<Integer> listb=new ArrayList<>();
		listb.add(2);
		listb.add(4);
		List<Integer> lista=new ArrayList<>();
		lista.add(1);
		lista.add(2);
		List<Integer> listc=new ArrayList<>();
		listc.add(6);
		listc.add(7);
		lists2.add(lista);
		lists2.add(listb);
		lists2.add(listc);

		System.out.println(equals(lists1,lists2));
	}

	public static void test2(){
		System.out.println(isPalindrome("12321"));
		System.out.println(isPalindrome("abaaba"));
		System.out.println(isPalindrome("123211"));
	}

	public static void testForStringArr() {//参数为String[]
		StringUtil stringUtil = new StringUtil();
		int times = 1000;//测试次数
		long time1 = 0, time2 = 0;
		boolean isok = true;
		int maxSize = 5;//String长度在[0~maxSize]随机
		int arrSize = 100;

//        int parameter1=0;//测试函数的参数
//        int maxParameter1=100;//参数1在[0,maxParameter1]随机

		String[] t1 = null;
//        String[] t2=null;

		List<List<Integer>> res1 = null, res2 = null;
		for (int i = 0; i < times; i++) {
//            parameter1=arrayUtil.ran(maxParameter1);
			t1 = stringUtil.generateRandomStringArrNoRepeat(stringUtil.ran(arrSize), stringUtil.ran(maxSize));

//        t2= stringUtil.generateRandomStringArrNoRepeat(stringUtil.ran(arrSize), stringUtil.ran(maxSize));

//        t1= stringUtil.generateRandomStringArr(stringUtil.ran(arrSize), stringUtil.ran(maxSize));
//        t2= stringUtil.generateRandomStringArr(stringUtil.ran(arrSize), stringUtil.ran(maxSize));
//			System.out.println("====================================");
//			stringUtil.printStrArr(t1);
			long l = System.currentTimeMillis();
			res1 = palindromePairs(t1);
//			System.out.println(res1);//针对返回值的操作
			time1 += System.currentTimeMillis() - l;
			l = System.currentTimeMillis();
			res2 = palindromePairs2(t1);
//			System.out.println(res2);//针对返回值的操作
			time2 += System.currentTimeMillis() - l;
//			System.out.println("====================================");
			if (!equals(res1,res2)) {
				isok = false;
				break;
			}
		}

		System.out.println("================"+(isok?"测试成功":"测试失败")+"===================");
		stringUtil.printStrArr(t1);
//        System.out.println("t2:"+t2);
//        System.out.println("parameter1:"+parameter1);//打印参数
		if (isok) System.out.println("m1 cost " + time1 + "ms");
		System.out.println(res1);//针对返回值的操作
		if (isok) System.out.println("m2 cost " + time2 + "ms");
		System.out.println(res2);//针对返回值的操作
		System.out.println(isok ? "success" : "fail");
		System.out.println("================"+(isok?"测试成功":"测试失败")+"===================");

	}

	public static void test3(){
		ArrayUtil arrayUtil=new ArrayUtil();
	}

	public static void main(String[] args) {
//		test1();
//		test2();
		testForStringArr();
//		test3();
	}

}