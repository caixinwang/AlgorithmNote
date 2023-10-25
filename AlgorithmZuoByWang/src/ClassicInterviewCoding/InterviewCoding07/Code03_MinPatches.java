package ClassicInterviewCoding.InterviewCoding07;

public class Code03_MinPatches {

	/**
	 *
	 * @param arr 有序的正数数组,无序的话请排个序
	 * @param aim 要完成1~aim的加和全覆盖需要补充几个数
	 * @return 返回最少需要补充几个数字完成加和全覆盖
	 */
	public static int minPatches(int[] arr, int aim) {
		if (arr==null) return -1;//允许arr里面没有任何元素
		long range=0;//当前达到了0~0的全覆盖，怕越界，用long
		int patch=0;//需要补充的数有几个
		int N=arr.length;
		int i=0;
		while (i<N){
			if (range+1>=arr[i]){//只有成功利用上了arr[i]的值，i才++
				range+=arr[i++];
			}else {//还没利用上arr[i]的值，i不能++，看看下次进来能不能利用上
				patch++;//补充上range+1这个数，一定最经济且最省
				range+=range+1;
			}
		}
		while(range<aim){//把数组里面的数都利用完了还不能全覆盖，那么还需要额外补充
			patch++;
			range+=range+1;
		}
		return patch;
	}

	public static int minPatches2(int[] arr, int K) {//不用long类型，手动判断越界情况
		int patches = 0; // 缺多少个数字
		int range = 0; // 已经完成了1 ~ range的目标
		for (int i = 0; i != arr.length; i++) {
			// 1~range
			// 1 ~ arr[i]-1
			while (arr[i] > range + 1) { // arr[i] 1 ~ arr[i]-1

				if (range > Integer.MAX_VALUE - range - 1) {
					return patches + 1;
				}

				range += range + 1; // range + 1 是缺的数字
				patches++;
				if (range >= K) {
					return patches;
				}
			}
			if (range > Integer.MAX_VALUE - arr[i]) {
				return patches;
			}
			range += arr[i];
			if (range >= K) {
				return patches;
			}
		}
		while (K >= range + 1) {
			if (K == range && K == Integer.MAX_VALUE) {
				return patches;
			}
			if (range > Integer.MAX_VALUE - range - 1) {
				return patches + 1;
			}
			range += range + 1;
			patches++;
		}
		return patches;
	}

	public static void main(String[] args) {
		int[] test = { 1, 2, 31, 33 };
		int n = 2147483647;
		System.out.println(minPatches(test, n));

	}

}
