package Leetcode.AllQuestions;

import java.util.LinkedList;
import java.util.List;

public class Leetcode_0017_LetterCombinationsOfAPhoneNumber {

	public List<String> letterCombinations(String digits) {
		if (digits==null||digits.length()==0) return new LinkedList<>();
		List<String> res=new LinkedList<>();
		char[][] map=new char[][]{//[10][] 只会按下2~9所以0~1弃而不用
			{},//0
			{},//1
			{'a','b','c'},//2
			{'d','e','f'},//3
			{'g','h','i'},//4
			{'j','k','l'},//5
			{'m','n','o'},//6
			{'p','q','r','s'},//7
			{'t','u','v'},//8
			{'w','x','y','z'},//9
		};
		char[] str = digits.toCharArray();
		char[] path=new char[str.length];
		process(str,0,path,res,map);
		return res;
	}

	/**
	 *
	 * @param tail 拨号字符数组，表示拨了哪些数字
	 * @param index 当前还有tail[index....]往后都需要你去拨号
	 * @param path 之前0~index-1拨出了哪些字母，路径留着path中
	 * @param res index到达了tail.len的时候把path加入res
	 * @param map 拨号数字和对应的字母的映射关系
	 */
	private void process(char[] tail, int index, char[] path, List<String> res, char[][] map) {
		if (index==tail.length) {
			res.add(String.valueOf(path));
			return;
		}
		char c=tail[index];//当前拨了哪个数字
		char[] chars = map[c - '0'];//这个数字能对应的字符集拿出来
		for (char t : chars) {//当前位置index要放什么字符，每个都去试
			path[index] = t;//不需要恢复现场，因为赋值行为本身就会覆盖，等价于恢复现场
			process(tail, index + 1, path, res, map);
		}
	}
}
