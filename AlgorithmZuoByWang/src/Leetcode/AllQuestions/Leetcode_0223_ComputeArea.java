package Leetcode.AllQuestions;

public class Leetcode_0223_ComputeArea {
    public int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        int x = Math.max(0, Math.min(ax2, bx2) - Math.max(ax1, bx1));//右边取小，左边取大
        int y = Math.max(0, Math.min(ay2, by2) - Math.max(ay1, by1));//上面取小，下面取大
        return (ax2 - ax1) * (ay2 - ay1) + (bx2 - bx1) * (by2 - by1) - (x * y);
    }
}
