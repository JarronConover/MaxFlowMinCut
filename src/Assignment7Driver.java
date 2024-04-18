import java.io.File;
import java.util.Scanner;

public class Assignment7Driver {
    public static void main(String[] args) {
        Graph g1 = buildGraph("demands1.txt");
        int flow1 = g1.findMaxFlow(0, 5, true);
        System.out.printf("Total Flow: %d\n", flow1);
        g1.findMinCut(0);

//        Graph g2 = buildGraph("demands2.txt");
//        int flow2 = g2.findMaxFlow(0, 8, true);
//        System.out.printf("Total Flow: %d\n", flow2);
//        g2.findMinCut(0);
//
//        Graph g3 = buildGraph("demands3.txt");
//        int flow3 = g3.findMaxFlow(0, 8, true);
//        System.out.printf("Total Flow: %d\n", flow3);
//        g3.findMinCut(0);
//
//        Graph g4 = buildGraph("demands4.txt");
//        int flow4 = g4.findMaxFlow(0, 7, true);
//        System.out.printf("Total Flow: %d\n", flow4);
//        g4.findMinCut(0);
//
//        Graph g5 = buildGraph("demands5.txt");
//        int flow5 = g5.findMaxFlow(0, 8, true);
//        System.out.printf("Total Flow: %d\n", flow5);
//        g5.findMinCut(0);

        Graph g6 = buildGraph("demands6.txt");
        int flow6 = g6.findMaxFlow(0, 7, true);
        System.out.printf("Total Flow: %d\n", flow6);
        g6.findMinCut(0);
    }

    public static Graph buildGraph(String filename) {
        try {
            Scanner input = new Scanner(new File(filename));
            int vertexCount = input.nextInt();
            Graph g = new Graph(filename, vertexCount);

            while (input.hasNextInt()) {
                int v1 = input.nextInt();
                int v2 = input.nextInt();
                int capacity = input.nextInt();
                if (!g.addEdge(v1, v2, capacity)) {
                    throw new Exception();
                }
            }
            input.close();
            return g;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Should throw an exception, but, well, deal with it.
        return null;
    }
}

/**
 * Compute max flow and min cut for starting and ending vertices
 * Graph and GraphNode use adjacency list (may need to add to Graph NOT GraphNode)
 * Finish Graph; You may modify code or add methods

 * Must use Edmonds Karp algorithm to determine the max flow
    * as describe in the assignment
 * Bread First Search for computing of the augmenting path
    * as described in the assignment
 * Min Cut as described in the assignment

 * Demands.txt are as follows:
    * first line: (6) Number of vertices
    * Rest of lines: (0 1 3) vertex numbers of edge and then edge weight
    * Flow goes from first vertex to second vertex
 * Code should handle vertices in any order

 *Output:
    *  -- Max Flow: txtFileName.txt --
     * Display each augmenting path
        * Flow (amount of flow along that augmenting path): vertex numbers of the path
        * Edmonds Karp finds shortest paths first so aug paths only grow in size
     * Display all edges that carry flow  (items)
        * Edge(x, y) transports z items (z being amount of flow on edge)
        * Total Flow: Total
        * An augmenting path can "push" flow backwards. *see notes

    *  -- Min Cut: txtFileName.txt --
        *  Min Cut Edge: (x, y) (the cut edges needing to be cut)

 *  May want to add a residual 2D array to Graph.java to track flow forward and backward
 *  Write a method to display the residual graph to help you debug your code
 *  Java's LinkedList class supports the Queue interface; this is much better than using an ArrayList as a queue.
    *  You can create it like this: Queue<Integer> q = new LinkedList<>();
 */