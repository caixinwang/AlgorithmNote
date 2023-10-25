package Leetcode.AllQuestions;

public class Leetcode_0004_MedianOfTwoSortedArrays {//寻找两个正序数组的中位数--有序
    //IC 12

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1==null||nums2==null) return 0;
        if (nums1.length==0&&nums2.length==0) return 0;
        int all=nums1.length+nums2.length;
        int upmid=findKthMinNumber(nums1,nums2,1+all>>1);
        int downmid=findKthMinNumber(nums1,nums2,(2+all>>1));
        return (upmid+downmid)/2f;//奇数的话会返回两个相同值
    }

    /**
     * 分类讨论，本质上就是为了凑两边留下来的一样多，怎么一样多呢？我们知道长的和短的差了l-s，我们需要补上这个差距。
     * 1.如果k<=s 那么很显然，直接可以凑一样多，取前s个
     * 2.如果k<=l 那么，我们需要让长的变短。长的前面需要去掉k-s个，后面需要去掉l-k个，那么总的就去掉了l-s，刚好填平了差距
     *   但是长的第k-s个可能是答案，所以我们手工比较之后就可以去掉了
     * 3.如果k>l 那么，我们需要让长的变短，短的也变短，但是长的变短的多就行了。长的前面去掉k-s个，短的去掉k-l个，
     *   总的就去掉了l-s个，正好填平了差距。但是由于长的第k-s个以及短的第k-l个都可以是答案，所以需要手动比较一下
     */
    public static int findKthMinNumber(int[] arr1,int[] arr2,int kth){
        if (arr1 == null || arr2 == null) return -1;
        if (kth < 1 || kth > arr1.length + arr2.length) return -1;
        if (arr1.length==0) return arr2[kth-1];
        if (arr2.length==0) return arr1[kth-1];
        if (arr1.length>arr2.length){//arr1做短的
            int[] tmp=arr1;
            arr1=arr2;
            arr2=tmp;
        }
        int s= arr1.length,l=arr2.length;
        if (kth<=s){
            return getMiddle(arr1,0,kth-1,arr2,0,kth-1);
        } else if (kth <= l) {
            if (arr2[kth-s-1]>=arr1[s-1]) return arr2[kth-s-1];
            return getMiddle(arr1,0,s-1,arr2,kth-s,kth-1);
        }
        if (arr1[kth-l-1]>=arr2[l-1])return arr1[kth-l-1];
        if (arr2[kth-s-1]>=arr1[s-1])return arr2[kth-s-1];
        return getMiddle(arr1,kth-l,s-1,arr2,kth-s,l-1);
    }

    public static int getMiddle(int[] A,int s1,int e1,int[] B,int s2,int e2){
        if (s1==e1) return Math.min(A[s1],B[s2]);
        int mid1=s1+e1>>1;
        int mid2=s2+e2>>1;
        if (A[mid1]==B[mid2]) return A[mid1];
        if ((e1-s1+1&1)==1){
            if (A[mid1]>B[mid2]) return getMiddle(A,s1,mid1,B,mid2,e2);
            else return getMiddle(A,mid1,e1,B,s2,mid2);
        }else {
            if (A[mid1]>B[mid2]) return getMiddle(A,s1,mid1,B,mid2+1,e2);
            else return getMiddle(A,mid1+1,e1,B,s2,mid2);
        }
    }

    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int n1=nums1.length,n2=nums2.length;
        if(n1==0&&n2==0) return -1;
        return (getKthMin(nums1,0,n1-1,nums2,0,n2-1,n1+n2+1>>1)+getKthMin(nums1,0,n1-1,nums2,0,n2-1,n1+n2+2>>1))/2d;
    }

    //为什么淘汰小的？因为小的肯定小于自己后面的，并且还小于下面的，凑不到第k个
    public int getKthMin(int[] arr1,int l1,int r1, int[] arr2,int l2,int r2,int k){
        int len1=r1-l1+1,len2=r2-l2+1;//先考虑长度，让arr1永远作短的那个
        if (len1>len2) return getKthMin(arr2,l2,r2,arr1,l1,r1,k);
        if (len1==0) return arr2[l2+k-1];//下面的情况中，最短的arr1的个数都大于等于1
        if (k==1) return Math.min(arr1[l1],arr2[l2]);//现有上面的保证arr1个数至少有一个，这句话才不会报错
        int i=Math.min(l1+k/2-1,r1),j=l2+k/2-1;//取k/2个元素，可能不够，和r取最小
        if (arr1[i]<arr2[j]) return getKthMin(arr1,i+1,r1,arr2,l2,r2,k-(i-l1+1));//< 的不可能作为第k大了
        return getKthMin(arr1,l1,r1,arr2,j+1,r2,k-(j-l2+1));
    }


}
