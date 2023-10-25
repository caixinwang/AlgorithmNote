package BasicLearning.Class17_ACAutomaton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Code01_AC {
    static class Node{
        public Node[] nexts;
        public Node fail;//失败之后直接到下一个匹配最长的地方
        public String end;//辅助的字段，end不为空说明这个结点是之前添加的某个字符串的最后一个结点
        public Boolean endUsed;//辅助字段，防止重复添加答案
        public int times;//times和endUsed都可以完成功能

        public Node(){
            fail=null;
            end=null;
            endUsed=false;
            nexts=new Node[26];//假定文章只有小写字母
        }
    }

    static class AcMachine{
        private Node root;

        public AcMachine(){
            root=new Node();
        }

        /**
         *
         * @param word 要在文中查找的关键词
         */
        public void insert(String word){
            if (word==null||word.length()==0) return;
            char[] str = word.toCharArray();
            Node cur=root;//从root开始走
            for (int i = 0; i < str.length; i++) {
                int path=str[i]-'a';
                if (cur.nexts[path] == null) {
                    cur.nexts[path] = new Node();
                }
                cur=cur.nexts[path];
            }
            cur.end=word;//最后一个结点的end带上word
        }

        /**
         * 通过层序遍历填充好fail指针。
         */
        public void build(){//将前缀树中Node的fail指针填充
            Queue<Node> queue=new LinkedList<>();
            queue.add(root);
            Node cur=null;
            while(!queue.isEmpty()){
                cur=queue.poll();
                for (int i = 0; i < cur.nexts.length; i++) {//cur结点的所有路都走一遍，帮它的孩子们设置fail指针
                    if (cur.nexts[i]!=null){//接下来要帮cur.nexts[i]设置fail
                        queue.add(cur.nexts[i]);
                        Node tmp=cur.fail;//cur有i路能走向孩子，如果cur的fail向上也能找到一个有i路的结点，就能作为孩子的fail
                        //如果不能完成匹配就一直往上沿着fail走，直到走到null了，只有root的fail才为null
                        while(tmp!=null&&tmp.nexts[i]==null) tmp=tmp.fail;
                        cur.nexts[i].fail=tmp!=null?tmp.nexts[i]:root;//只有root的fail才指向空
                    }
                }
            }
        }

        public List<String> containWords(String content){
            if (content==null||content.length()==0) return null;
            List<String> ans=new ArrayList<>();
            char[] str = content.toCharArray();
            Node cur=root,up=null;
            for (int i = 0; i < str.length; i++) {
                while(cur!=null&&cur.nexts[str[i]-'a']==null){//出while就是找到第一个成功匹配的位置或者压根没有匹配
                    cur=cur.fail;
                }
                cur=cur==null?root:cur.nexts[str[i]-'a'];
                up=cur;//up开始往上走，收入部分答案
                while (up != null&& !up.endUsed) {
                    if (up.end!=null){
                        ans.add(up.end);
                        up.endUsed=true;
                    }
                    up=up.fail;
                }
            }
            return ans;
        }

        public List<String> containWords2(String content){
            if (content==null||content.length()==0) return null;
            List<String> ans=new ArrayList<>();
            char[] str = content.toCharArray();
            Node cur=root;
            for (int i = 0; i < str.length; i++) {
                while(cur!=null&&cur.nexts[str[i]-'a']==null){//出while就是找到第一个成功匹配的位置或者压根没有匹配
                    cur=cur.fail;
                }
                if (cur==null) {
                    cur = root;
                } else {
                    cur = cur.nexts[str[i] - 'a'];
                    cur.times++;
                }
            }
            add(ans);
            return ans;
        }

        public List<String> containWords3(String content) {//和1版本一样
            char[] str = content.toCharArray();
            Node cur = root, follow = null;
            int path = 0, i = 0;
            List<String> ans = new ArrayList<>();
            while (i != str.length) {
                path = str[i] - 'a';
                while (cur != null && cur.nexts[path] != null) {//匹配成功
                    cur = cur.nexts[path];
                    i++;
                    path = str[i] - 'a';
                    follow = cur;
                    while (follow != null && !follow.endUsed) {
                        if (follow.end != null) {
                            ans.add(follow.end);
                            follow.endUsed = true;
                        }
                        follow = follow.fail;
                    }
                    //出while说明 follow走到空 或者 follow位置已经被添加过答案了
                }
                //出while说明 cur为空 或者 目前的i位置下，在线段树中找不到通路了，通过fail指针继续失败
                if (cur == null) {
                    cur = root;
                    i++;
                } else {
                    cur = cur.fail;
                }
            }
            return ans;
        }

        private void add(List<String> list){
            Queue<Node> queue=new LinkedList<>();
            queue.add(root);
            Node cur=null;
            while(!queue.isEmpty()){
                cur=queue.poll();
                for (int i = 0; i < cur.nexts.length; i++) {//cur结点的所有路都走一遍
                    if (cur.nexts[i]!=null){
                        if (cur.nexts[i].end!=null) list.add(cur.nexts[i].end);
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        AcMachine ac = new AcMachine();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("abcdheks");
        // 设置fail指针
        ac.build();

        List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruv");
        for (String word : contains) {
            System.out.println(word);
        }
    }


}
