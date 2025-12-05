package AlgoritmoGenerico;
import java.util.*;

public class Graph {
    private Map<String, List<String>> adj;
    private boolean directed;

    public Graph(boolean directed) {
        this.directed = directed;
        this.adj = new HashMap<>();
    }

    public void addVertex(String v) {
        adj.putIfAbsent(v, new ArrayList<>());
    }

    public void addEdge(String u, String v) {
        addVertex(u);
        addVertex(v);

        adj.get(u).add(v);

        if (!directed) {
            adj.get(v).add(u);
        }
    }

    public void removeEdge(String u, String v) {
        if (adj.containsKey(u)) adj.get(u).remove(v);
        if (!directed && adj.containsKey(v)) adj.get(v).remove(u);
    }

    public void removeVertex(String v) {
        adj.remove(v);
        for (String key : adj.keySet()) {
            adj.get(key).remove(v);
        }
    }

    public List<String> bfs(String start) {
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(start);

        while (!queue.isEmpty()) {
            String u = queue.poll();
            if (!visited.contains(u)) {
                visited.add(u);
                result.add(u);
                queue.addAll(adj.get(u));
            }
        }
        return result;
    }

    public List<String> dfs(String start) {
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        dfsRecursive(start, visited, result);
        return result;
    }

    private void dfsRecursive(String u, Set<String> visited, List<String> result) {
        if (!visited.contains(u)) {
            visited.add(u);
            result.add(u);
            for (String v : adj.get(u)) {
                dfsRecursive(v, visited, result);
            }
        }
    }

    public void show() {
        for (String v : adj.keySet()) {
            System.out.println(v + " -> " + adj.get(v));
        }
    }
}

class Main {
    public static void main(String[] args) {
        Graph g = new Graph(false);

        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "D");
        g.addEdge("C", "D");

        g.show();

        System.out.println("BFS: " + g.bfs("A"));
        System.out.println("DFS: " + g.dfs("A"));
    }
}
