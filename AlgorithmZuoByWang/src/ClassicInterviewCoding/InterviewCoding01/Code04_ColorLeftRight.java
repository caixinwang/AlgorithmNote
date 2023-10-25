package ClassicInterviewCoding.InterviewCoding01;

public class Code04_ColorLeftRight {

	// RGRGR -> RRRGG
	/**
	 * 尝试试每一个分界位置,需要涂的就是左边的G + 右边的R
	 */
	public static int minPaint(String s){
		if (s==null||s.length()==0) return -1;
		char[] str = s.toCharArray();
		int rightSideR=0;//当前位置右边有多少个R
		int left=0;//当前位置左边有多少个R 初始位置在0位置的左边
		for (int i = 0; i < str.length; i++) rightSideR+=str[i]=='R'?1:0;
		int res=rightSideR;//初始状态为分界线在0位置的左边，也就是全部都变成G的情况
		for (int i = 0; i < str.length; i++) {//i代表当前分界线在i 和 i+1之间
			rightSideR-=str[i]=='R'?1:0;//来到i位置的右边了，如果i位置是R，要更新
			left+=str[i]=='G'?1:0;
			res = Math.min(res, rightSideR+left);
		}
		return res;
	}

	public static void main(String[] args) {
		String test = "GGGGGR";
		System.out.println(minPaint(test));
	}

}
