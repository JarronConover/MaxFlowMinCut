import java.util.*;

public class Graph {
    private final GraphNode[] vertices;  // Adjacency list for graph.
    private final String name;  //The file from which the graph was created.

    public Graph(String name, int vertexCount) {
        this.name = name;

        vertices = new GraphNode[vertexCount];
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            vertices[vertex] = new GraphNode(vertex);
        }
    }
    public boolean addEdge(int source, int destination, int capacity) {
        // A little bit of validation
        if (source < 0 || source >= vertices.length) return false;
        if (destination < 0 || destination >= vertices.length) return false;

        // This adds the actual requested edge, along with its capacity
        vertices[source].addEdge(source, destination, capacity);

        // TODO: This is what you have to describe in the required README.TXT file
        //       that you submit as part of this assignment.
        vertices[destination].addEdge(destination, source, 0);

        return true;
    }

    /**
     * Algorithm to find max-flow in a network
     * Display of the augmenting paths and edges
     * total flow displayed
     */
    public int findMaxFlow(int s, int t, boolean report) {
        System.out.println("-- Max Flow: " + this.name + " --");

        int totalFlow = 0;
        ArrayList<ArrayList<Integer>> usedEdges = new ArrayList<>();

        while (hasAugmentingPath(s, t)){
            int availableFlow = Integer.MAX_VALUE;

            ArrayList<Integer> augmentedPath = new ArrayList<>();
            augmentedPath.add(t);
            int v = t;

            while (v != s) {
                int w = vertices[v].parent;
                augmentedPath.add(w);
                availableFlow = Math.min(getEdge(w, v).capacity, availableFlow);
                v = w;
            }

            for (int i = 0, j = 1; j < augmentedPath.size(); i++, j++) {
                v = augmentedPath.get(i);
                int w = augmentedPath.get(j);

                getEdge(w, v).capacity -= availableFlow;
                getEdge(v, w).capacity += availableFlow;
              }

            totalFlow += availableFlow;

            //Display each augmenting path and add paths to usedEdges
            StringBuilder flow = new StringBuilder("Flow " + availableFlow + ": ");
            Collections.reverse(augmentedPath);
            usedEdges.add(augmentedPath);
            for (int vertex : augmentedPath){
                flow.append(vertex).append(" ");
            }
            System.out.println(flow);
        }
        printGraph(usedEdges);
        return totalFlow;
    }

    /**
     * Algorithm to find an augmenting path in a network
     */
    private boolean hasAugmentingPath(int s, int t) {
        for (GraphNode vertex : vertices){
            vertex.parent= -1;
            vertex.visited = false;
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        while (!queue.isEmpty() && vertices[t].parent == -1){
            int v = queue.remove();
            vertices[v].visited = true;
            for (GraphNode.EdgeInfo edge : vertices[v].successor){
                int w = edge.to;
                if (edge.capacity > 0 && !vertices[w].visited && w != s){
                    vertices[w].parent = v;
                    queue.add(w);
                }
            }
        }
        return (vertices[t].parent != -1);
    }

    /**
     * Algorithm to find the min-cut edges in a network
     */
    public void findMinCut(int s) {
        System.out.println("\n-- Min Cut: " + this.name + " --");

        ArrayList<Integer> R = new ArrayList<>();
        R.add(s);

        //Add all vertices that are reachable from s to ArrayList R
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        while (!queue.isEmpty()){
            int v = queue.remove();
            for (GraphNode.EdgeInfo edge : vertices[v].successor){
                int w = edge.to;
                if (edge.capacity > 0 && queue.contains(w)){
                    queue.add(w);
                    R.add(w);
                }
            }
        }

        //Find all edges from a vertex in R to a vertex not in R and add to minCutEdges
        ArrayList<GraphNode.EdgeInfo> minCutEdges = new ArrayList<>();
        for (int vertex : R){
            for (GraphNode.EdgeInfo edge : vertices[vertex].successor){
                if (!R.contains(edge.to)){
                    minCutEdges.add(edge);
                }
            }
        }
        for (GraphNode.EdgeInfo edge : minCutEdges){
            System.out.println("Min Cut Edge: (" + edge.from + ", " + edge.to + ")");
        }
        System.out.println();
    }

    /**
     * Algorithm to find an edge given a from and to vertices
     */
    private GraphNode.EdgeInfo getEdge (int from, int to){
        GraphNode.EdgeInfo edge = null;
        for (GraphNode.EdgeInfo element : vertices[from].successor) {
            if (element.to == to) {
                edge = element;
            }
        }
        return edge;
    }

    /**
     * Algorithm to print the graph given the paths used
     */
    private void printGraph(ArrayList<ArrayList<Integer>> usedGraph){
        String graph = "";
        ArrayList<GraphNode.EdgeInfo> list = new ArrayList<>();
        for (ArrayList<Integer> path : usedGraph){
            for (int i = 0, j = 1; j < path.size(); i++, j++){
                GraphNode.EdgeInfo edge = getEdge(path.get(j), path.get(i));
                if (!list.contains(edge)){
                    graph += "\nEdge(" + edge.to + ", " + edge.from + ") transports " + edge.capacity + " items";
                    list.add(edge);
                }
            }
        }
        System.out.println(graph);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("The Graph " + name + " \n");
        for (var vertex : vertices) {
            sb.append((vertex.toString()));
        }
        return sb.toString();
    }

    /**
     * Algorithm to create residual 2D graph as a matrix
     */
    private LinkedList<LinkedList<Integer>> residualGraph(){
        LinkedList<LinkedList<Integer>> residual2D = new LinkedList<>();
        for (GraphNode vertex : vertices){
            LinkedList<Integer> list = new LinkedList<>();

            for (int i = 0; i < vertices.length; i++){
                list.add(0);
                for (GraphNode.EdgeInfo edge : vertex.successor){
                    if (edge.to == i){
                        list.set(i, edge.capacity);
                    }
                }
            }
            residual2D.add(list);
        }
        return residual2D;
    }
}
