package Dijkstra;
import java.util.*;

class WeightedGraph {
    private Map<String, List<Edge>> adj;

    public WeightedGraph() {
        this.adj = new HashMap<>();
    }

    public void addVertex(String v) {
        adj.putIfAbsent(v, new ArrayList<>());
    }

    public void addEdge(String u, String v, int weight) {
        addVertex(u);
        addVertex(v);

        adj.get(u).add(new Edge(v, weight));
        adj.get(v).add(new Edge(u, weight)); // tirar se quiser direcionado
    }

    public Map<String, Integer> dijkstra(String start) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> parent = new HashMap<>();

        for (String v : adj.keySet()) {
            dist.put(v, Integer.MAX_VALUE);
            parent.put(v, null);
        }
        dist.put(start, 0);

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.distance > dist.get(current.vertex)) continue;

            for (Edge edge : adj.get(current.vertex)) {
                int newDist = current.distance + edge.weight;

                if (newDist < dist.get(edge.to)) {
                    dist.put(edge.to, newDist);
                    parent.put(edge.to, current.vertex);
                    pq.add(new Node(edge.to, newDist));
                }
            }
        }

        return dist;
    }

    public List<String> shortestPath(String start, String end, Map<String, String> parent) {
        List<String> path = new ArrayList<>();
        String curr = end;

        while (curr != null) {
            path.add(curr);
            curr = parent.get(curr);
        }
        Collections.reverse(path);
        return path;
    }
}

class Edge {
    String to;
    int weight;

    Edge(String to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}

class Node {
    String vertex;
    int distance;

    Node(String v, int d) {
        this.vertex = v;
        this.distance = d;
    }
}

class Main {
    public static void main(String[] args) {
        WeightedGraph g = new WeightedGraph();

        g.addEdge("A", "B", 4);
        g.addEdge("A", "C", 2);
        g.addEdge("C", "B", 1);
        g.addEdge("B", "D", 5);
        g.addEdge("C", "D", 8);
        g.addEdge("D", "E", 6);

        Map<String, Integer> dist = g.dijkstra("A");

        System.out.println("Distâncias mínimas:");
        for (String v : dist.keySet()) {
            System.out.println("A -> " + v + ": " + dist.get(v));
        }
    }
}
