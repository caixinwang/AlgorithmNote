package ClassicInterviewCoding.InterviewCoding11;

// 这个代码废掉了，因为我写了一个更猛的
// 更猛的链接：https://github.com/algorithmzuo/algorithmbasic2020/blob/master/src/class02/Code03_KM.java
// 更猛的原因，因为上面链接里的代码解决了以下这个问题：
// 一个数组中，只有一个数出现k次，其他所有数出现m次，怎么找到这个出现了k次的数，已知k<m，要求时间复杂度O(N)
// 可以看到，本题就是更猛问题的一个特例，而且都是O(N)就很好了
// 讲解在“体系学习班第2节”
public class Code04_KTimesOneTime {//k进制异或

	public static int onceNum(int[] arr, int k) {
		if (arr==null||arr.length==0) return -1;
		int[] digit=new int[32];//用k进制异或，把arr中的数全部k进制异或了，最后把digit用k进制转为十进制答案
		for (int i = 0; i < arr.length; i++) {
			eor(arr[i],digit,k);//把arr[i]这个数k进制异或进digit中
		}
		return convert(digit,k);
	}

	private static void eor(int num, int[] digit, int k) {//把num，k进制异或到digit中
		int index=0;
		while(num!=0){
			int t=num%k;
			digit[index]=(digit[index]+t)%k;
			num/=k;
			index++;
		}
	}

	private static int convert(int[] digit, int k) {
		int res=0;
		for (int i = digit.length-1; i >=0; i--) {//从高位开始
			res=res*k+digit[i];//乘法次数最少
		}
		return res;
	}


	public static void main(String[] args) {
		int[] test1 = { 1, 1, 1, 2, 6, 6, 2, 2, 10, 10, 10, 12, 12, 12, 6, 9 };
		System.out.println(onceNum(test1, 3));

		int[] test2 = { -1, -1, -1, -1, -1, 2, 2, 2, 4, 2, 2 };
		System.out.println(onceNum(test2, 5));

	}

}