package Leetcode.AllQuestions;

import java.util.Stack;

public class Leetcode_0084_LargestRectangleInHistogram {//单调栈--求直方图中最大矩阵面积
    
    /**
     * 任意一个i位置，如果想参与构成矩形，那么这个位置的旁边的那些格子的高度一定要大于等于自己的高度
     * 换言之，我们要找到小于i位置高度的最近的格子位置l和r。l和r中间的就都是大于等于自己的高度。
     * 虽然arr中如果有重复的值，在一个比它小的值来到之后被弹出，如果在栈中它的前一个也是和它一样大小的元素，
     * 不用担心答案会不正确，最后一个等于它的那个位置会抓出正确的答案
     *
     * @param heights 直方图
     * @return 返回矩形的最大面积
     */
    public int largestRectangleArea(int[] heights) {//arr是一个直方图，返回这个直方图中最大的矩形面积
        Stack<Integer> stack = new Stack<>();
        int res=Integer.MIN_VALUE;
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[i]<heights[stack.peek()]){
                Integer index = stack.pop();
                int left=stack.isEmpty()?-1:stack.peek();
                int right=i;
                res = Math.max(res, heights[index]*(right-left-1));
            }
            stack.push(i);
        }
        while(!stack.isEmpty()){
            Integer index = stack.pop();
            int right=heights.length;
            int left=stack.isEmpty()?-1:stack.peek();
            res = Math.max(res, heights[index]*(right-left-1));
        }
        return res;
    }

    public int largestRectangleArea2(int[] heights) {
        int ans=0,top =-1,n=heights.length;
        int[] stack=new int[n];
        for(int i=0;i<n;i++){
            while(top>=0&&heights[i]<heights[stack[top]]){
                int left=top>=1?stack[top-1]:-1;//left的含义是最靠近自己的<自己的柱子的下标
                ans=Math.max(ans,(i-left-1)*(heights[stack[top--]]));//right是最靠近自己的<自己的柱子的下标，这里就是i
            }
            stack[++top]=i;
        }
        while(top!=-1){
            int left=top>=1?stack[top-1]:-1;
            ans=Math.max(ans,(n-left-1)*(heights[stack[top--]]));//这里right变为了n
        }
        return ans;
    }


}
