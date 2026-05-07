import java.io.*;

public class CSVReader {

    /**
     * Reads edges from CSV and loads them into the graph.
     * @param filename path to the CSV file
     * @param graph    Graph object to populate
     */
    public static void loadFromCSV(String filename, Graph graph) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                // Skip header row
                if (firstLine) { firstLine = false; continue; }

                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                try {
                    int src  = Integer.parseInt(parts[0].trim());
                    int dest = Integer.parseInt(parts[1].trim());
                    int dist = Integer.parseInt(parts[2].trim());

                    if (src >= 0 && src < graph.getSize() &&
                        dest >= 0 && dest < graph.getSize() && dist > 0) {
                        graph.addEdge(src, dest, dist);
                    }
                } catch (NumberFormatException e) {
                    
                }
            }
        }
    }
}
