package BasicLearning.Class06_Graph;

import java.util.HashMap;
import java.util.HashSet;

public class Gragh {
    public HashMap<Integer,Node> nodes;
    public HashSet<Edge> edges;

    public Gragh() {
        this.nodes = new HashMap<>();
        this.edges = new HashSet<>();
    }
}
