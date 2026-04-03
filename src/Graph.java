
public class Graph {

    private final int[][] adjMatrix;  
    private final int size;           
    private int edgeCount;      

    
    public Graph(int vertices) {
        this.size = vertices;
        this.adjMatrix = new int[size][size];
        this.edgeCount = 0;
       
    }

    
    public void addEdge(int src, int dest, int distance) {
        if (adjMatrix[src][dest] == 0) {
            edgeCount++; 
        }
        adjMatrix[src][dest] = distance;
        adjMatrix[dest][src] = distance; 
    }

    
    public int getWeight(int u, int v) {
        return adjMatrix[u][v];
    }

    public int getSize()      { return size; }
    public int getEdgeCount() { return edgeCount; }
    public int[][] getMatrix(){ return adjMatrix; }

   
    public void printSampleMatrix(int limit) {
        int n = Math.min(limit, size);
        System.out.print("         ");
        for (int i = 0; i < n; i++) System.out.printf("%5d", i);
        System.out.println();
        System.out.println("         " + "-----".repeat(n));

        for (int i = 0; i < n; i++) {
            System.out.printf("%5d  | ", i);
            for (int j = 0; j < n; j++) {
                System.out.printf("%5d", adjMatrix[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Print all edges for the first `limit` cities (edge list view).
     */
    public void printEdges(int limit) {
        int n = Math.min(limit, size);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (adjMatrix[i][j] > 0) {
                    System.out.printf("  %2d -> %2d  =  %d%n", i, j, adjMatrix[i][j]);
                }
            }
        }
    }
}
