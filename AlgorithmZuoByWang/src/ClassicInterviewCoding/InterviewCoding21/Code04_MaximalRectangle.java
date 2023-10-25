package ClassicInterviewCoding.InterviewCoding21;

import java.util.Stack;

public class Code04_MaximalRectangle {

	public static int maxRecSize(int[][] map) {
		if (map == null || map.length == 0 || map[0].length == 0) {
			return 0;
		}
		int maxArea = 0;
		int[] height = new int[map[0].length];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				height[j] = map[i][j] == 0 ? 0 : height[j] + 1;
			}
			maxArea = Math.max(maxRecFromBottom(height), maxArea);
		}
		return maxArea;
	}

	// height是直方图数组
	public static int maxRecFromBottom(int[] arr) {
		if (arr==null||arr.length==0) return 0;
		int max=0;
		Stack<Integer> stack=new Stack<>();
		for (int i = 0; i < arr.length; i++) {
			while(!stack.isEmpty()&&arr[i]<arr[stack.peek()]){
				Integer pop = stack.pop();
				int left=stack.isEmpty()?-1:stack.peek();
				int right=i;
				max = Math.max(max, arr[pop]*(right-left-1));
			}
			stack.push(i);
		}
		while(!stack.isEmpty()){
			Integer pop = stack.pop();
			int left=stack.isEmpty()?-1:stack.peek();
			int right=arr.length;
			max = Math.max(max, arr[pop]*(right-left-1));
		}
		return max;
	}

	public static void main(String[] args) {
		int[][] map = { { 1, 0, 1, 1 }, { 1, 1, 1, 1 }, { 1, 1, 1, 0 }, };
		System.out.println(maxRecSize(map));
	}

}
