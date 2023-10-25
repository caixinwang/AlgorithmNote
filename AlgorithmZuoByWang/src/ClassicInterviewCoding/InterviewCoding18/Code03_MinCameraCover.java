package ClassicInterviewCoding.InterviewCoding18;

public class Code03_MinCameraCover {
    static final int MAX = Integer.MAX_VALUE;

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static int minCameraCover1(Node root) {
        Info data = process1(root);
        return (int) Math.min(data.uncovered + 1,
                Math.min(data.coveredNoCamera, data.coveredHasCamera));
    }

    // 潜台词：x是头节点，x下方的点都被覆盖的情况下
    public static class Info {
        public long uncovered; // x没有被覆盖，x为头的树至少需要几个相机
        public long coveredNoCamera; // x被相机覆盖，但是x没相机，x为头的树至少需要几个相机
        public long coveredHasCamera; // x被相机覆盖了，并且x上放了相机，x为头的树至少需要几个相机

        public Info(long un, long no, long has) {
            uncovered = un;
            coveredNoCamera = no;
            coveredHasCamera = has;
        }
    }

    // 所有可能性都穷尽了
    public static Info process1(Node X) {
        if (X == null) { // base case
            return new Info(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        }


        Info left = process1(X.left);
        Info right = process1(X.right);
        // x  uncovered  x自己不被覆盖，x下方所有节点，都被覆盖
        //  左孩子： 左孩子没被覆盖，左孩子以下的点都被覆盖
        //          左孩子被覆盖但没相机，左孩子以下的点都被覆盖
        //          左孩子被覆盖也有相机，左孩子以下的点都被覆盖
        long uncovered = left.coveredNoCamera + right.coveredNoCamera;


        // x下方的点都被covered，x也被cover，但x上没相机
        long coveredNoCamera = Math.min(
                // 1)
                left.coveredHasCamera + right.coveredHasCamera,

                Math.min(
                        // 2)
                        left.coveredHasCamera + right.coveredNoCamera,

                        // 3)
                        left.coveredNoCamera + right.coveredHasCamera)
        );


        // x下方的点都被covered，x也被cover，且x上有相机
        long coveredHasCamera = Math.min(

                left.uncovered,
                Math.min(
                        left.coveredNoCamera,
                        left.coveredHasCamera)
        )


                +
                Math.min(
                        right.uncovered,
                        Math.min(
                                right.coveredNoCamera,
                                right.coveredHasCamera))


                + 1;


        return new Info(uncovered, coveredNoCamera, coveredHasCamera);
    }

    public static int minCameraCover2(Node root) {
        Data data = process2(root);
        return data.cameras + (data.status == Status.UNCOVERED ? 1 : 0);
    }

    // 以x为头，x下方的节点都是被covered，x自己的状况，分三种
    public static enum Status {
        UNCOVERED, COVERED_NO_CAMERA, COVERED_HAS_CAMERA
    }

    // 以x为头，x下方的节点都是被covered，得到的最优解中：
    // x是什么状态，在这种状态下，需要至少几个相机
    public static class Data {
        public Status status;
        public int cameras;

        public Data(Status status, int cameras) {
            this.status = status;
            this.cameras = cameras;
        }
    }

    public static Data process2(Node X) {
        if (X == null) {
            return new Data(Status.COVERED_NO_CAMERA, 0);
        }
        Data left = process2(X.left);
        Data right = process2(X.right);
        int cameras = left.cameras + right.cameras;
        if (left.status == Status.UNCOVERED || right.status == Status.UNCOVERED) {
            return new Data(Status.COVERED_HAS_CAMERA, cameras + 1);
        }


        // 左右孩子，不存在没被覆盖的情况
        if (left.status == Status.COVERED_HAS_CAMERA
                ||
                right.status == Status.COVERED_HAS_CAMERA) {
            return new Data(Status.COVERED_NO_CAMERA, cameras);
        }
        // 左右孩子，不存在没被覆盖的情况，也都没有相机
        return new Data(Status.UNCOVERED, cameras);
    }

    static class Record {
        public long minAll;
        public long noCovered;
        public long coveredNoCamera;
        public long coveredHasCamera;

        public Record(long minAll, long noCovered, long coveredNoCamera, long coveredHasCamera) {
            this.minAll = minAll;
            this.noCovered = noCovered;
            this.coveredNoCamera = coveredNoCamera;
            this.coveredHasCamera = coveredHasCamera;
        }
    }


    public static Record precess3(Node head) {
        if (head == null) return new Record(0,MAX,0,MAX);
        Record r1 = precess3(head.left);
        Record r2 = precess3(head.right);
        long noCovered=r1.coveredNoCamera+r2.coveredNoCamera;
        long coveredNoCamera=Math.min(r1.coveredHasCamera+r2.coveredHasCamera,
                Math.min(
                        r1.coveredHasCamera+r2.coveredNoCamera,
                        r1.coveredNoCamera+r2.coveredHasCamera
                )
        );
        long coveredHasCamera=1+
                Math.min(r1.coveredHasCamera,Math.min(r1.coveredNoCamera,r1.noCovered)) +
                Math.min(r2.coveredHasCamera,Math.min(r2.coveredNoCamera,r2.noCovered))
               ;
        long minAll=Math.min(1+noCovered,Math.min(coveredNoCamera,coveredHasCamera));
        return new Record(minAll,noCovered,coveredNoCamera,coveredHasCamera);
    }

    public static int minCameraCover3(Node root) {
        return (int)precess3(root).minAll;
    }

    public static Node generateRandomTree(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }


    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }


    public static void main(String[] args) {
        int times = 1000;
        int maxVal = 100;
        int maxLevel = 8;
        boolean success = true;
        for (int i = 0; i < times; i++) {
            Node node = generateRandomTree(maxLevel, maxVal);
            if (minCameraCover2(node) != minCameraCover3(node)) {
                success = false;
                System.out.println(minCameraCover1(node));
                System.out.println(minCameraCover3(node));
                break;
            }
        }
        System.out.println(success ? "ok" : "no");
    }
}
