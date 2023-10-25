package Leetcode.AllQuestions;

import java.util.Stack;

public class Leetcode_0085_MaximalRectangle {//单调栈--最大矩形面积

    public int maximalRectangle(char[][] matrix) {//暴力。利用数组问题、压缩技巧解决
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return -1;
        int res = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] arr = new int[matrix[0].length];
            for (int j = i; j < matrix.length; j++) {
                for (int index = 0; index < arr.length; index++) {
                    arr[index] += matrix[j][index];
                }
                int maxContinuousK = maxContinuousK(arr, (j - i + 1) * '1');
                res = Math.max(res, (j - i + 1) * maxContinuousK);
            }
        }
        return res;
    }

    public int maxContinuousK(int[] arr, int k) {//求arr中连续为k的最大长度
        int res = 0, cur = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == k) {
                cur++;
                res = Math.max(res, cur);
            } else {
                cur = 0;
            }
        }
        return res;
    }

    /**
     * 任意一个i位置，如果想参与构成矩形，那么这个位置的旁边的那些格子的高度一定要大于等于自己的高度
     * 换言之，我们要找到小于i位置高度的最近的格子位置l和r。l和r中间的就都是大于等于自己的高度。
     * 虽然arr中如果有重复的值，在一个比它小的值来到之后被弹出，如果在栈中它的前一个也是和它一样大小的元素，
     * 不用担心答案会不正确，最后一个等于它的那个位置会抓出正确的答案
     *
     * @param arr 直方图
     * @return 返回矩形的最大面积
     */
    public int maxRectangleArea(int[] arr) {//arr是一个直方图，返回这个直方图中最大的矩形面积
        Stack<Integer> stack = new Stack<>();
        int res=Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[i]<arr[stack.peek()]){
                Integer index = stack.pop();
                int left=stack.isEmpty()?-1:stack.peek();//如果栈空了，说明pop左边没有比它小的
                int right=i;
                res = Math.max(res, arr[index]*(right-left-1));//高度乘以宽度
            }
            stack.push(i);
        }
        while(!stack.isEmpty()){
            Integer index = stack.pop();
            int right=arr.length;//右边没有比自己小的
            int left=stack.isEmpty()?-1:stack.peek();
            res = Math.max(res, arr[index]*(right-left-1));
        }
        return res;
    }

    public int maximalRectangle2(char[][] matrix) {//单调栈
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) return -1;
        int res = Integer.MIN_VALUE;
        int[] arr = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {//第i行作为直方图的底
            for (int j = 0; j < arr.length; j++) {//更新直方图
                if (matrix[i][j]=='0'){
                    arr[j]=0;
                }else {
                    arr[j]+=1;
                }
            }
            res = Math.max(res, maxRectangleArea(arr));
        }
        return res;
    }

    public int maximalRectangle3(char[][] matrix) {
        int n=matrix.length,m=matrix[0].length;
        int[] arr=new int[m];
        int ans=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++) {
                arr[j]=matrix[i][j]=='1'?(arr[j]+1):0;
            }
            ans=Math.max(ans,largestRectangleArea3(arr));
        }
        return ans;
    }

    public int largestRectangleArea3(int[] heights) {
        int ans=0,top =-1,n=heights.length;
        int[] stack=new int[n];
        for(int i=0;i<n;i++){
            while(top>=0&&heights[i]<heights[stack[top]]){
                int left=top>=1?stack[top-1]:-1;
                ans=Math.max(ans,(i-left-1)*(heights[stack[top--]]));
            }
            stack[++top]=i;
        }
        while(top!=-1){
            int left=top>=1?stack[top-1]:-1;
            ans=Math.max(ans,(n-left-1)*(heights[stack[top--]]));
        }
        return ans;
    }

}
