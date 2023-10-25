package TestUtils;

public class RandomUtil {

    //以1/10的概率产出一个true
    public boolean oneInTen(){
        int random=0;//利用01发生器等概率随机产出一个[0000~1111]0~15的数
        do {
            random=0;
            for (int i = 0; i < 4; i++) {//对i位进行操作，最低位是第0位
                random+=oneOrZero()?1<<i:0;
            }
        }while (random>=10);//[0,9]刚好十个数字我才要
        if (random==0) return true;//1/10的概率产生true
        else return false;
    }

    //01发生器
    public boolean oneOrZero(){
        return Math.random()>=0.5;
    }

    //随机生成[0,max]的数字,max为一个正整数
    public int ran(int max){
        return (int) (Math.random()*(max+1));
    }

    //

    /**
     * [l,r] <==> l+[0,r-l]
     * @param l 范围
     * @param r 范围
     * @return 随机返回[l,r]的数字,max为一个正整数
     */
    public int ran(int l,int r){
        return l+ran(r-l);
    }


}
