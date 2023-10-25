package BasicLearning.Class06_GreedUnionFind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code06_HwoManyPeople {

    public static class Person {
        public String a;
        public String b;
        public String c;

        public Person(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public static class UnionFind<V> {

        public static class Node<V> {
            V value;

            public Node(V value) {
                this.value = value;
            }
        }

        public HashMap<V, Node> nodes;
        public HashMap<Node, Node> parents;
        public HashMap<Node, Integer> size;

        public UnionFind(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            size = new HashMap<>();

            for (V v : values) {
                Node node = new Node<>(v);
                nodes.put(v, node);
                parents.put(node, node);
                size.put(node, 1);
            }
        }

        public Node findFather(Node node) {
            Stack<Node> stack = new Stack<>();
            while (node != parents.get(node)) {
                stack.add(node);
                node = parents.get(node);
            }
            while (!stack.isEmpty()) {//扁平化
                parents.put(stack.pop(), node);
            }
            return node;
        }

        /**
         * 小挂大，所以第一步是确认不是一个集合后找大小
         *
         * @param a
         * @param b
         */
        public void union(V a, V b) {
            if (!nodes.containsKey(a)|| !nodes.containsKey(b)){
                return ;
            }
            Node head1 = findFather(nodes.get(a));
            Node head2 = findFather(nodes.get(b));
            if (head1 == head2) {
                return;
            }
            int size1 = size.get(head1);
            int size2 = size.get(head2);
            Node big = size1 >= size2 ? head1 : head2;
            Node small = big == head1 ? head2 : head1;
            parents.put(small, big);
            size.put(big, size1 + size2);
            size.remove(small);
        }

        public int sets() {
            return size.size();
        }

    }

    public static int HowManyPeople(List<Person> arr) {
        UnionFind<Person> unionFind = new UnionFind<Person>(arr);
        HashMap<String,Person> mapA=new HashMap<>();
        HashMap<String,Person> mapB=new HashMap<>();
        HashMap<String,Person> mapC=new HashMap<>();
        for (Person p:arr){
            if (mapA.containsKey(p.a)){
                unionFind.union(p,mapA.get(p.a));
            }else {
                mapA.put(p.a,p);
            }
            if (mapB.containsKey(p.b)){
                unionFind.union(p,mapB.get(p.b));
            }else {
                mapB.put(p.b,p);
            }
            if (mapC.containsKey(p.c)){
                unionFind.union(p,mapC.get(p.c));
            }else {
                mapC.put(p.c,p);
            }
        }
        return unionFind.sets();
    }

    public static void main(String[] args) {
        Person p1=new Person("a","b","c");
        Person p2=new Person("a","d","e");
        Person p3=new Person("b","2","3");
        Person p4=new Person("c","3","3");
        Person p5=new Person("789","4","6");
        ArrayList<Person> list=new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        System.out.println(HowManyPeople(list));
    }
}