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
        //Edmonds Karp
        int totalFlow = 0;



        while (hasAugmentingPath(s, t)){
            int availableFlow = 2147483646;
            for (int v = t; v > s; v --){
                availableFlow = Math.min(availableFlow, vertices[v].successor.get(0).capacity);
                //update residual graph by
                // // subtract available flow at vertex v in direction of s to t
                // // add available flow at vertex v in direction of t to s
                totalFlow += availableFlow;

            }
        }


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

        //add s to queue...
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        while (!queue.isEmpty() && vertices[t].parent == -1){
            int v = queue.remove();
            for (GraphNode.EdgeInfo successor : vertices[v].successor){
                int w = successor.to; //could need to be from

                // TODO: if there is residual capacity from v to w
                // and not already part of the augmenting path
                // and not s

                if (successor.capacity>0 && !queue.contains(w) && w != s){
                    vertices[w].parent = v;
                    queue.add(w);
                }
            }
        }

        return (vertices[t].parent != -1);
    }

    /**
     * Algorithm to find the min-cut edges in a network
     * Display of which edges to cut
     */
    public void findMinCut(int s) {
        // TODO:
        //Based on the max flow algorithm, compute the final residual graph



        //Find the set of vertices that are reachable from the source vertex
        //  // in the residual graph; the set R includes s.  Call those vertices R.



        //All edges from a vertex in R to a vertex not in R are the minimum cut edges

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
