
public class Dijkstra {

    private final int[]     dist;      
    private final int[]     parent;    
    private final boolean[] visited;  
    private final int       size;
    private final boolean   verbose;   

    public Dijkstra(int vertices, boolean verbose) {
        this.size    = vertices;
        this.verbose = verbose;
        dist    = new int[size];
        visited = new boolean[size];
        parent  = new int[size];
    }
    public void run(Graph graph, int source) {
        final int INF = Integer.MAX_VALUE / 2;

        
        for (int i = 0; i < size; i++) {
            dist[i]    = INF;
            visited[i] = false;
            parent[i]  = -1;
        }
        dist[source] = 0;

        if (verbose) {
            printSeparator('-', 60);
            System.out.println("  Dijkstra Algorithm Steps");
            printSeparator('-', 60);
        }

        
        for (int step = 0; step < size; step++) {

           
            int u = minDistance();
            if (u == -1 || dist[u] == INF) break; 
            visited[u] = true;

            if (verbose && step < 7) {
                System.out.printf("%nStep %d%n", step + 1);
                System.out.printf("  Visited City : %d%n", u);
                System.out.print ("  Visited Cities : {");
                boolean first = true;
                for (int i = 0; i < size; i++) {
                    if (visited[i]) {
                        if (!first) System.out.print(", ");
                        System.out.print(i);
                        first = false;
                    }
                }
                System.out.println("}");
            }

          
            StringBuilder relaxLine = new StringBuilder("  Relax        : ");
            boolean anyRelax = false;

            for (int v = 0; v < size; v++) {
                int w = graph.getWeight(u, v);
                if (w > 0 && !visited[v] && dist[u] + w < dist[v]) {
                    dist[v]   = dist[u] + w;
                    parent[v] = u;

                    if (verbose && step < 7) {
                        relaxLine.append(String.format("%d->%d=%d   ", u, v, dist[v]));
                        anyRelax = true;
                    }
                }
            }

            if (verbose && step < 7) {
                if (anyRelax) System.out.println(relaxLine);

                
                System.out.print("  dist[]       : [ ");
                for (int i = 0; i < Math.min(7, size); i++) {
                    if (dist[i] == INF) System.out.print("INF ");
                    else System.out.printf("%d ", dist[i]);
                }
                System.out.println("... ]");
            }
        }
    }

    private int minDistance() {
        int min = Integer.MAX_VALUE, idx = -1;
        for (int v = 0; v < size; v++) {
            if (!visited[v] && dist[v] < min) {
                min = dist[v];
                idx = v;
            }
        }
        return idx;
    }


    public int[] getPath(int dest) {
        // Count path length
        int len = 0;
        int cur = dest;
        while (cur != -1) { len++; cur = parent[cur]; }

        int[] path = new int[len];
        cur = dest;
        for (int i = len - 1; i >= 0; i--) {
            path[i] = cur;
            cur = parent[cur];
        }
        return path;
    }

    public int   getDistance(int v) { return dist[v]; }
    public int[] getDist()          { return dist; }

    private void printSeparator(char ch, int len) {
        for (int i = 0; i < len; i++) System.out.print(ch);
        System.out.println();
    }
}
