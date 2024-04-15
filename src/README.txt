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

Initial hunch: describe that it creates a start and end point


