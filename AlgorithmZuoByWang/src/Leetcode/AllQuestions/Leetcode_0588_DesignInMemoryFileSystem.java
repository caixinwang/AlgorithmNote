package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Leetcode_0588_DesignInMemoryFileSystem {
    //根据题目需要，我们其实需要建立一个前缀树。
    //1.由于文件系统中一条路是一个字符串，所以nexts是一个map
    //并且因为题目要求ls命令调用的时候需要按照字典序排列，所以使用TreeMap
    //2.题目需要区分一个Node是文件还是目录，如果是文件，就需要有向文件添加内容的功能
    //我们使用一个StringBuiler来区分目录还是文件，如果是目录，builder为空。文件才会建起builder
    //3.题目要求，创建目录以及向文件添加内容的时候，如果没有的话就一路新建。
    //4.Node需要:name nexts content
    //5.对于splits数组，从下标1开始,因为对于splits[0]是空串
    class FileSystem {
        class Node {
            String name;
            TreeMap<String, Node> nexts;
            StringBuilder content;

            public Node(String name, TreeMap<String, Node> nexts, StringBuilder content) {
                this.name = name;
                this.content = content;
                this.nexts = nexts;
            }

            public Node(String name, String content) {//文件的构造器
                this(name, null, new StringBuilder().append(content));
            }

            public Node(String name) {//目录的构造器
                this(name, new TreeMap<>(), null);
            }
        }

        Node root;

        public FileSystem() {
            root = new Node("");//根节点是空串，不是"/"，因为我们会把路径按照"/"分解
        }

        public List<String> ls(String path) {
            Node cur = root;
            String[] splits = path.split("/");
            int n = splits.length;
            List<String> ans = new ArrayList<>();
            for (int i = 1; i < n; i++) {
                String name = splits[i];//下一步的路径名
                if (!cur.nexts.containsKey(name)) return ans;//由于是ls，没有的路就直接返回空列表
                cur = cur.nexts.get(name);
            }
            if (cur.content == null) {//是目录
                ans.addAll(cur.nexts.keySet());
            } else {
                ans.add(cur.name);
            }
            return ans;
        }

        public void mkdir(String path) {
            Node cur = root;
            String[] splits = path.split("/");
            int n = splits.length;
            for (int i = 1; i < n; i++) {
                String name = splits[i];//下一步的路径名
                if (!cur.nexts.containsKey(name)) cur.nexts.put(name, new Node(name));//没有这个目录就创建
                cur = cur.nexts.get(name);
            }
        }

        public void addContentToFile(String filePath, String content) {
            Node cur = root;
            String[] splits = filePath.split("/");
            int n = splits.length;
            String name;
            for (int i = 1; i < n - 1; i++) {//走到倒数第二个结点，因为我们最后一个结点需要创建文件而不是目录
                name = splits[i];//下一步的路径名
                if (!cur.nexts.containsKey(name)) cur.nexts.put(name, new Node(name));//没有这个目录就创建
                cur = cur.nexts.get(name);
            }
            name = splits[n - 1];
            if (!cur.nexts.containsKey(name)) cur.nexts.put(name, new Node(name, ""));
            cur = cur.nexts.get(name);//最后这个结点创建的是文件
            cur.content.append(content);
        }

        public String readContentFromFile(String filePath) {
            Node cur = root;
            String[] splits = filePath.split("/");
            int n = splits.length;
            for (int i = 1; i < n; i++) {
                String name = splits[i];//下一步的路径名
                if (!cur.nexts.containsKey(name)) return "";//没有这个文件就直接返回空串
                cur = cur.nexts.get(name);
            }
            if (cur.content == null) {//是目录
                return "";
            }
            return cur.content.toString();
        }
    }

    /**
     * Your FileSystem object will be instantiated and called as such:
     * FileSystem obj = new FileSystem();
     * List<String> param_1 = obj.ls(path);
     * obj.mkdir(path);
     * obj.addContentToFile(filePath,content);
     * String param_4 = obj.readContentFromFile(filePath);
     */

    public static void main(String[] args) {
	    String path="/123/456/789/";
        String[] splits = path.split("/");
        System.out.println("长度为:"+splits.length);
        for (String s:splits) System.out.println(s);
    }

}
