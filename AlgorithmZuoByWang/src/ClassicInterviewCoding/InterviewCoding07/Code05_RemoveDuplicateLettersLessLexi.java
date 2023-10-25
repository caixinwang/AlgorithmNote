package ClassicInterviewCoding.InterviewCoding07;


public class Code05_RemoveDuplicateLettersLessLexi {

	// 在str中，每种字符都要保留一个，让最后的结果，字典序最小 ，并返回
	public static String removeDuplicateLetters1(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		int[] map = new int[256];
		for (int i = 0; i < str.length(); i++) {
			map[str.charAt(i)]++;
		}
		int minACSIndex = 0;
		for (int i = 0; i < str.length(); i++) {
			minACSIndex = str.charAt(minACSIndex) > str.charAt(i) ? i : minACSIndex;
			if (--map[str.charAt(i)] == 0) {
				break;
			}
		}
		return String.valueOf(str.charAt(minACSIndex)) + removeDuplicateLetters1(
				str.substring(minACSIndex + 1).replaceAll(String.valueOf(str.charAt(minACSIndex)), ""));
	}

	/**
	 * 统计字符的字典序只需要一个对应大小的数组即可，例如统计a~z的词频只需要一个26大小的数组。
	 * 如果是统计所有字符的词频就需要128大小的数组
	 * @param s 由26个小写字母组成的字符串
	 * @return 返回每种字符类型都删的只剩一个之后字典序最大的字符串
	 */
	public static String removeDuplicateLetters2(String s) {//我的实现，和左哥的方法不同点在于我的r不回退
		if (s==null||s.length()<2) return s;
		StringBuilder builder=new StringBuilder();
		int[] cMap=new int[26];//统计词频
		char[] str = s.toCharArray();
		int N=str.length;
		for (int i = 0; i < str.length; i++) {
			cMap[str[i]-'a']++;//统计词频
		}
		int r=-1;//从左往右扫描减词频
		int l=0;//从l~r上选出一串当前字典序最小的字符
		while(r<N-1){
			while(r<N-1&&(cMap[str[++r]-'a']==-1||--cMap[str[r]-'a']>0));//找到第一个还没被选择性忽略，且被减到零的字符位置
			while(l<=r){//每次选一个字典序最小字符的出来，缩l，直到把最后一个字符选进去才退出
				char minChar=128;//字符合法是[0,127]
				int minIndex=-1;//后面要加上判断
				for (int i=l;i<=r;i++){
					if (cMap[str[i]-'a']!=-1&&(str[i]<minChar)) {//但凡进了一次这个if minIndex和minChar都是合法的
						minChar = str[i];//如果一次这if都没进，说明区间字符没有能用的了，换区间，需要判断
						minIndex=i;
					}
				}
				if (minChar!=128){//至少进了一次for中的if，说明至少有一个字符能用
					builder.append(minChar);
					cMap[minChar-'a']=-1;
					l=minIndex+1;
					if (minChar==str[r]){
						break;
					}
				} else {//说明这个区间已经没有能用的字符了.例如l后面剩下aaaa,a在前面选过，所以r会跳到最后的a位置，这时候从l~r都不可用
					l = r + 1;//换一个l
				}
			}
		}
		return builder.toString();
	}

	public static String removeDuplicateLetters3(String s) {//回退
		if (s==null||s.length()<2) return s;
		int[] cMap=new int[26];//统计词频
		char[] str = s.toCharArray();
		int N=str.length;
		for (int i = 0; i < str.length; i++) {
			cMap[str[i]-'a']++;//统计词频
		}
		int diff=0;//有多少不同的字符
		for (int i = 0; i < cMap.length; i++) {
			if (cMap[i]!=0) diff++;
		}
		char[] res=new char[diff];
		int index=0;//配合使用
		int r=-1;//从左往右扫描减词频
		int l=0;//从l~r上选出一串当前字典序最小的字符
		while(index<res.length){
			while(r<N-1&&(cMap[str[++r]-'a']==-1||--cMap[str[r]-'a']>0));//找到第一个还没被选择性忽略，且被减到零的字符位置
			int minIndex=-1;
			for (int i=l;i<=r;i++){//找最小值对应下标  找最小下标和找最小值写法有所区别，最小下标这里多了一个或运算
				if (cMap[str[i]-'a']!=-1&&(minIndex==-1||str[i]<str[minIndex])) minIndex=i;
			}
            if (minIndex==-1) break;
			res[index++]=str[minIndex];
			l=minIndex+1;
			for (int i=l;i<=r;i++) {
				if (cMap[str[i]-'a']!=-1) cMap[str[i] - 'a']++;
			}
			cMap[str[minIndex]-'a']=-1;
			r=l-1;
		}
		return new String(res);
	}

	public static String removeDuplicateLetters4(String s) {//左的实现
		char[] str = s.toCharArray();
		// 小写字母ascii码值范围[97~122]，所以用长度为26的数组做次数统计
		// 如果map[i] > -1，则代表ascii码值为i的字符的出现次数
		// 如果map[i] == -1，则代表ascii码值为i的字符不再考虑
		int[] map = new int[26];
		for (int i = 0; i < str.length; i++) {
			map[str[i] - 'a']++;
		}
		char[] res = new char[26];
		int index = 0;
		int L = 0;
		int R = 0;
		while (R != str.length) {
			// 如果当前字符是不再考虑的，直接跳过
			// 如果当前字符的出现次数减1之后，后面还能出现，直接跳过
			if (map[str[R] - 'a'] == -1 || --map[str[R] - 'a'] > 0) {
				R++;
			} else { // 当前字符需要考虑并且之后不会再出现了
				// 在str[L..R]上所有需要考虑的字符中，找到ascii码最小字符的位置
				int pick = -1;
				for (int i = L; i <= R; i++) {
					if (map[str[i] - 'a'] != -1 && (pick == -1 || str[i] < str[pick])) {
						pick = i;
					}
				}
				// 把ascii码最小的字符放到挑选结果中
				res[index++] = str[pick];
				// 在上一个的for循环中，str[L..R]范围上每种字符的出现次数都减少了
				// 需要把str[pick + 1..R]上每种字符的出现次数加回来
				for (int i = pick + 1; i <= R; i++) {
					if (map[str[i] - 'a'] != -1) { // 只增加以后需要考虑字符的次数
						map[str[i] - 'a']++;
					}
				}
				// 选出的ascii码最小的字符，以后不再考虑了
				map[str[pick] - 'a'] = -1;
				// 继续在str[pick + 1......]上重复这个过程
				L = pick + 1;
				R = L;
			}
		}
		return String.valueOf(res, 0, index);
	}



	//for test
	public static String generateRandomString(int size){//生成一个长度在[0~size]随机的字符串
		int realLen=(int) (Math.random()*(size+1));//该随机字符串的长度
		StringBuilder builder= new StringBuilder();
		for (int j = 0; j < realLen; j++) {//用随机字符拼出一个随机字符串
			int ran=(int) (Math.random()*(25+1));//[0,25]随机，共26个字母
//			int ran=(int) (Math.random()*(6));//[0,5]随机，共a,b,c,d,e,f 6个字母
			char c=(char) (ran+'a');//[a,z]
//                int ran=(int) (Math.random()*(127+1));//[0,127]随机，共128个字符
//                char c=(char) ran;
			builder.append(c);
		}
		return builder.toString();
	}

	//for test
	public static void test1(){//测试generateRandomString
		for (int i = 0; i < 10; i++) {
			System.out.println(generateRandomString(20));
		}
	}

	//for test
	public static void test2(){//测试removeDuplicateLetters1和removeDuplicateLetters2的正确性
		int times=100000;
		int size=30;
		boolean isok=true;
		String f=generateRandomString(size);
		for (int i = 0; i < times; i++) {
			String s = generateRandomString(size);
			if (!removeDuplicateLetters3(s).equals(removeDuplicateLetters2(s))){
				isok=false;
				f=s;
				break;
			}
		}
		System.out.println(f);
		System.out.println(removeDuplicateLetters3(f));
		System.out.println(removeDuplicateLetters2(f));
		System.out.println(isok?"success":"fail");
	}

	//for test
	public static void test3(){//测试removeDuplicateLetters2的正确性
		int times=1;
		int size=20;
		String s = generateRandomString(size);
		System.out.println(s);
		System.out.println(removeDuplicateLetters1(s));
	}

	public static void main(String[] args) {
//		test1();
		test2();
//		test3();
	}



}
