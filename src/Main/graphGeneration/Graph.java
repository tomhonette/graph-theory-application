package Main.graphGeneration;

import Main.Utils.Edge;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Graph {
    //The number of vertices in the graph
    private final int verticesCount;
    public static int vCount = 0;
    //The number of edges in the graph
    private final int edgesCount;
    //The relations between the vertices
    private final int[][] edges;
    //The chromatic number
    public static int chromaticNumber = 0;
    static public String graphType;

    /**
     * Constructor of Graph object
     *
     * @param verticesCount the number of vertices in the graph
     * @param edgesCount the number of edges in the graph
     */
    public Graph(int verticesCount, int edgesCount) {
        this.verticesCount = verticesCount;
        this.edgesCount = edgesCount;
        this.edges = createRandomEdges(verticesCount, edgesCount);
        this.chromaticNumber = CNumber();
        vCount = this.verticesCount;
    }

    /**
     * Getter method
     *
     * @return the number of vertices in the graph
     */
    public int getVerticesCount() {
        return verticesCount;
    }

    /**
     * Getter method
     *
     * @return the number of vertices in the graph
     */
    public int getEdgesCount() {
        return edgesCount;
    }

    /**
     * Getter method
     *
     * @return the relations between the vertices
     */
    public int[][] getEdges() {
        return edges;
    }

    /**
     * Creates random relations between the vertices
     *
     * @param vertices the number of vertices in the graph
     * @param edges the number of edges in the graph
     * @return the relations between the vertices
     */
    private int[][] createRandomEdges(int vertices, int edges) {
        System.out.println("Creating");
        System.out.println(vertices);
        System.out.println(edges);
        Random random = new Random();
        int[][] relations = new int[edges][2];
        boolean[] existingVertices = new boolean[vertices];
        do{
            for (int i = 0; i < vertices; i++) { //resetting the array
                existingVertices[i] = false;
            }
            //generating edges
            for (int i = 0; i < edges; i++) {
                int source, dest;
                source = i%vertices;
                if( i < vertices) {
                    do{
                        dest = random.nextInt(vertices);
                    }while (existingVertices[dest] || dest == source); //avoid self-loops
                    existingVertices[dest] = true; //marking this vertex as used
                }else {
                    do{
                        dest = random.nextInt(vertices);
                    }while (dest == source); //avoid self-loops
                }

                relations[i][0] = source;
                relations[i][1] = dest;
            }
        }while(!checkGraph(relations, vertices)); //ensure the graph is connected
        System.out.println("Created");
        return relations;
    }

    /**
     * Checks if the graph is connected by verifying that all vertices can be reached
     * @param relations the edges of the graph
     * @param vertices  the number of vertices in the graph
     * @return true if the graph is connected, false otherwise
     */
    private boolean checkGraph(int[][] relations, int vertices)
    {
        boolean[] visited = new boolean[vertices]; //track whether each vertex is visited

        for (int i = 0; i < vertices; i++) { //marking vertices as unvisited
            visited[i] = false;
        }
        checkGraph2(relations, vertices, visited, 0);

        //check if all vertices were visited
        for (int i = 0; i < vertices; i++) {
            if(!visited[i]){
                return false;
            }
        }
        return true;
    }

    /**
     * Goes through all the edges of the vertex and marks vertices as visited
     * @param relations the edges of the graph
     * @param vertices  the number of vertices in the graph
     * @param visited   an array to track visited vertices
     * @param vertex    the current vertex being visited
     */
    private void checkGraph2(int[][] relations, int vertices, boolean[] visited, int vertex)
    {
        visited[vertex] = true;

        //going through all edges to find connected vertices
        for (int i = 0; i < relations.length; i++) {
            if(relations[i][0] == vertex || relations[i][1] == vertex ){
                //visit the destination vertex
                if(relations[i][0] == vertex){
                    if(!visited[relations[i][1]]){
                        checkGraph2(relations, vertices, visited, relations[i][1]);
                    }
                    //visit the source vertex
                }else{
                    if(!visited[relations[i][0]]){
                        checkGraph2(relations, vertices, visited, relations[i][0]);
                    }
                }
            }
        }
    }



    /**
     * gets the chromatic number
     *
     * @return the chromatic number
     */
    public int CNumber(){

        graphType = "";

        int[] edgesValidity = new int[edgesCount + 1];

        if(isCyclic(edges, edgesValidity)){
            graphType = "Complete cycle";
            if(verticesCount % 2 == 1){
                return 3;
            }
            else{
                return 2;
            }
        }


        if (edgesCount == verticesCount - 1) {
            boolean[] visited = new boolean[verticesCount + 1];
            if (!(hasCycle(edges, 1, -1, visited))) {
                for (int i = 1; i <= verticesCount; i++) {
                    if (visited[i]) {
                        graphType = "tree";
                        return 2;
                    }
                }
            }
        }

        // number of edges should be equal to that
        int expectedEdges = verticesCount * (verticesCount - 1) / 2;
        if (edgesCount == expectedEdges) {
            // checking the edges between two vertices
            boolean[][] adjacencyMatrix = new boolean[verticesCount + 1][verticesCount + 1];
            for (int[] edge : edges) {
                adjacencyMatrix[edge[0]][edge[1]] = true;
                adjacencyMatrix[edge[1]][edge[0]] = true;
            }
            // vertices are connected
            for (int i = 1; i <= verticesCount; i++) {
                for (int j = 1; j <= verticesCount; j++) {
                    if (i != j && adjacencyMatrix[i][j]) {
                        graphType = "Complete";
                        return verticesCount;
                    }
                }
            }
        }

        int[][] relations= new int [edgesCount][2];
        for(int i=0; i<relations.length; i++){
            relations[i][0]=edges[i][0];
            relations[i][1]=edges[i][1];
        }
        boolean bipartite=true;

        int[] colors2 = new int[verticesCount + 1]; // Array to store colors, 0 = uncolored
        Arrays.fill(colors2, 0);       // Initialize all vertices as uncolored

        Queue<Integer> queue = new LinkedList<>();

        for (int start = 1; start <= verticesCount; start++) {
            if (colors2[start] != 0) continue; // Skip if already visited
            colors2[start] = 1; // Assign first color
            queue.add(start);

            while (!queue.isEmpty()) {
                int node = queue.poll();
                for (int[] edge : relations) {
                    if (edge[0] == node || edge[1] == node) {
                        int neighbor = (edge[0] == node) ? edge[1] : edge[0];
                        if (colors2[neighbor] == 0) { // If uncolored, color with opposite color
                            colors2[neighbor] = -colors2[node];
                            queue.add(neighbor);
                        } else if (colors2[neighbor] == colors2[node]) {
                            bipartite=false;
                        }
                    }
                }
            }
        }

        if(bipartite){
            graphType = "Bipartite";
            return 2;
        }



        //BRUTE FORCE ALGORITHM if there's no special graph detected
        int[] vertexColors = new int[verticesCount];
        int[] availableColors = new int[verticesCount];

        for (int i = 1; i < verticesCount; i++) {
            for (int j = 0; j < verticesCount; j++) {
                availableColors[j] = 1; // 1 means available, 0 means unavailable
            }

            //We check for every relation and their neighbours
            for (int j = 0; j < edgesCount; j++) {
                if (edges[j][0] == i) {
                    int neighborColor = vertexColors[edges[j][1]];
                    if (neighborColor > 0) {
                        availableColors[neighborColor - 1] = 0;
                    }
                } else if (edges[j][1] == i) {
                    int neighborColor = vertexColors[edges[j][0]];
                    if (neighborColor > 0) {
                        availableColors[neighborColor - 1] = 0;
                    }
                }
            }

            int color;
            for (color = 1 ; color <= verticesCount; color++) {
                if (availableColors[color - 1] == 1) {
                    vertexColors[i] = color;
                    break;
                }
            }

            if (color > chromaticNumber) {
                chromaticNumber = color;
            }
        }
        return chromaticNumber;
    }


    private static boolean hasCycle(int[][] edges, int current, int parent, boolean[] visited) {
        visited[current] = true;
        for (int[] edge : edges) {
            if (edge[0] == current || edge[1] == current) {
                int neighbor = (edge[0] == current) ? edge[1] : edge[0];
                if (!visited[neighbor]) {
                    if (hasCycle(edges, neighbor, current, visited)) {
                        return true;
                    }
                } else if (neighbor != parent) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCyclic(int[][] connections, int[] edgesValidity){
        for(int i = 0; i < connections.length; i++){
            int u = connections[i][0];
            int v = connections[i][1];

            edgesValidity[u]++;
            edgesValidity[v]++;
        }


        for (int i = 0; i < edgesValidity.length; i++) {
            if(edgesValidity[i] != 2){
                return false;
            }
        }
        return true;
    }
}
