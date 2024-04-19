Describe line 26 in the Graph.java file.
Describe what this line of code is doing and most importantly,
why this line of code is necessary in order for
the algorithm to work correctly.  I want to know that you
understand the purpose of this code.  I want something other than,
"without this line the algorithm doesn't find the correct paths",
tell me why this line of code helps with the correctness of the
algorithm.  Be as specific as you can.

    public boolean addEdge(int source, int destination, int capacity) {
        // A little bit of validation
        if (source < 0 || source >= vertices.length) return false;
        if (destination < 0 || destination >= vertices.length) return false;

        // This adds the actual requested edge, along with its capacity
        vertices[source].addEdge(source, destination, capacity);

        // TODO: This is what you have to describe in the required README.TXT file that you submit as part of this assignment.
        vertices[destination].addEdge(destination, source, 0);

        return true;
    }

Response:

The purpose of the line 26 of the Graph.java file is to create a residual backwards flow edge for each and every edge
added to the graph. This line of code creates an edge from the destination of the new edge to the source of the new edge
with a capacity of 0 as there is no possible flow at this edge until there is residual flow added to it from flow used
by the original (forward) edge of source to destination.

Line 26 is necessary because our Edmonds Karp algorithm can make *mistakes*. In the scenario that we use a path that is
later discovered to be suboptimal we must have the residual backwards flow edge created by line 26 to push or redirect
flow back threw the graph to create a more optimal route.

This line of code helps with the correctness of the graph by allowing for changes to the graph once paths have already
been determined. Thus, we achieve the most optimal path regardless of what path we choose to start with.