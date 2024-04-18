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
        // TODO:
        System.out.println("-- Max Flow: " + this.name + " --");

        //Edmonds Karp
        int totalFlow = 0;

        GraphNode.EdgeInfo forwardEdge = null;
        GraphNode.EdgeInfo backwardEdge = null;

        while (hasAugmentingPath(s, t)){
            int availableFlow = 2147483646;

            ArrayList<Integer> augmentedPath = new ArrayList<>();

            for (int v = t; v > s; v --){

                //Error Handling:
                //If v = s or v has no parent solve for w

                int w = vertices[v].parent;

                for (GraphNode.EdgeInfo vertex : vertices[v].successor){
                    if (vertex.to == w){
                        backwardEdge = vertex;
                    }
                }

                for (GraphNode.EdgeInfo vertex : vertices[w].successor){
                    if (vertex.to == v){
                        forwardEdge = vertex;
                    }
                }

                assert forwardEdge != null;
                assert backwardEdge != null;

                int residual = forwardEdge.capacity;

                availableFlow = Math.min(availableFlow, residual);

                backwardEdge.capacity += availableFlow;
                forwardEdge.capacity -= availableFlow;

                //create an array of points of the augmented path
                augmentedPath.add(v);
            }

            totalFlow += availableFlow;

            //Display each augmenting path
            Collections.reverse(augmentedPath);
            StringBuilder flow = new StringBuilder("Flow " + availableFlow + ": ");

            for (int vertex : augmentedPath){
                flow.append(vertex).append(" ");
            }

            System.out.println(flow + "\n");
        }

        //Print out edges and amount transported preferably with a single function
        printGraph();

        return totalFlow;
    }






    /**
     * Algorithm to find an augmenting path in a network
     */
    private boolean hasAugmentingPath(int s, int t) {
        // TODO:
        //breadth-first Search IDK where it goes with Max Flow or here
        for (GraphNode vertex : vertices){
            vertex.parent= -1;
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        while (!queue.isEmpty() && vertices[t].parent == -1){
            int v = queue.remove();
            for (GraphNode.EdgeInfo successor : vertices[v].successor){
                int w = successor.to; //could need to be from

                // TODO: if there is residual capacity from v to w

                if (successor.capacity > 0 && !vertices[w].visited && w != s){
                    vertices[w].parent = v;
                    vertices[w].visited = true;
                    queue.add(w);
                }
            }
        }

        return (vertices[t].parent != -1);
    }

    /**
     * Algorithm to print graph
     */
    private void printGraph(){
        for (GraphNode vertex : vertices){
            for (GraphNode.EdgeInfo edge : vertex.successor){
                if (edge.capacity > 0){
                    System.out.println("Edge(" + edge.from + ", " + edge.to + ") transports " + edge.capacity + " items");
                }
            }
        }
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

        //Find all edges from a vertex in R to a vertex not in are and add to minCutEdges
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

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("The Graph " + name + " \n");
        for (var vertex : vertices) {
            sb.append((vertex.toString()));
        }
        return sb.toString();
    }
}
