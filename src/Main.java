import java.io.*;
import java.util.Scanner;
import java.awt.Desktop;
import java.net.URI;

public class Main {

    static final int    CITIES      = 100;
    static final String CSV_PATH    = "data/roads.csv";
    static final String OUTPUT_PATH = "output/results.txt";
    static final int    LINE_WIDTH  = 62;

    static Scanner globalScanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        // Open the visual simulation in the browser
        openVisualization();

        printDouble(LINE_WIDTH);
        printCentered("GPS Navigation System (Dijkstra)", LINE_WIDTH);
        printDouble(LINE_WIDTH);
        System.out.println();

        System.out.print("  Enter Source City      (0 - 99): ");
        int source = readInt(globalScanner, 0, 99);
        System.out.print("  Enter Destination City (0 - 99): ");
        int dest   = readInt(globalScanner, 0, 99);
        System.out.println();

        Graph graph = new Graph(CITIES);
        File csvFile = new File(CSV_PATH);

        if (!csvFile.exists()) {
            System.out.println("  [ERROR] roads.csv not found at: " + csvFile.getAbsolutePath());
            return;
        }

        CSVReader.loadFromCSV(CSV_PATH, graph);

        printDash(LINE_WIDTH);
        Dijkstra dijk = new Dijkstra(CITIES, true);
        dijk.run(graph, source);
        System.out.println();

        printDash(LINE_WIDTH);
        System.out.println("  Shortest Path Result");
        printDash(LINE_WIDTH);

        int shortestDist = dijk.getDistance(dest);
        int[] path       = dijk.getPath(dest);

        System.out.printf("  Source City      : %d%n", source);
        System.out.printf("  Destination City : %d%n", dest);
        printDash(LINE_WIDTH);

        if (shortestDist >= Integer.MAX_VALUE / 2) {
            System.out.println("  No path found between the two cities.");
        } else {
            System.out.printf("  Shortest Distance: %d km%n%n", shortestDist);
            System.out.print ("  Path: ");
            for (int i = 0; i < path.length; i++) {
                System.out.print(path[i]);
                if (i < path.length - 1) System.out.print(" -> ");
            }
            System.out.println();
        }

        System.out.println();
        printDash(LINE_WIDTH);
        System.out.println("  Shortest Distances from City " + source + " (5 samples)");
        printDash(LINE_WIDTH);

        int[] samples = {10, 25, 50, 75, 99};
        for (int s : samples) {
            if (s == source) continue;
            int d = dijk.getDistance(s);
            if (d >= Integer.MAX_VALUE / 2)
                System.out.printf("  City %2d : unreachable%n", s);
            else
                System.out.printf("  City %2d : %d km%n", s, d);
        }

        System.out.println();
        printDouble(LINE_WIDTH);
        printCentered("Navigation Calculation Completed", LINE_WIDTH);
        printDouble(LINE_WIDTH);

        writeResults(source, dest, shortestDist, path, graph);
        System.out.println("\n  [output saved] Results saved to " + OUTPUT_PATH);

        System.out.println();
        printDash(LINE_WIDTH);
        System.out.print("  Find another path? (yes/no): ");

        String response = "";
        if (globalScanner.hasNextLine()) {
            response = globalScanner.nextLine().trim().toLowerCase();
        }

        if (response.equals("yes") || response.equals("y")) {
            main(args);
        } else {
            System.out.println("  Exiting. Goodbye!");
            globalScanner.close();
        }
    }

    static void openVisualization() {
        try {
            File html = new File("visualization.html").getCanonicalFile();
            if (!html.exists()) {
                System.out.println("  [INFO] visualization.html not found, skipping browser launch.");
                return;
            }
            URI uri = html.toURI();
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
            } else {
                // Fallback for Windows
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + html.getAbsolutePath());
            }
            System.out.println("  [INFO] Visualization opened: " + html.getName());
        } catch (Exception e) {
            System.out.println("  [INFO] Could not open browser: " + e.getMessage());
        }
    }

    static int readInt(Scanner sc, int min, int max) {
        int val = -1;
        while (val < min || val > max) {
            try {
                val = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                val = -1;
            }
            if (val < min || val > max)
                System.out.printf("  Please enter a number between %d and %d: ", min, max);
        }
        return val;
    }

    static void printDouble(int len) {
        for (int i = 0; i < len; i++) System.out.print('=');
        System.out.println();
    }

    static void printDash(int len) {
        for (int i = 0; i < len; i++) System.out.print('-');
        System.out.println();
    }

    static void printCentered(String text, int width) {
        int pad = (width - text.length()) / 2;
        for (int i = 0; i < pad; i++) System.out.print(' ');
        System.out.println(text);
    }

    static void writeResults(int src, int dest, int dist, int[] path, Graph graph) throws IOException {
        File f = new File(OUTPUT_PATH);
        f.getParentFile().mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(f, true))) {
            pw.println("GPS Navigation System - Results");
            pw.println("================================");
            pw.printf("Source City      : %d%n", src);
            pw.printf("Destination City : %d%n", dest);
            if (dist >= Integer.MAX_VALUE / 2) {
                pw.println("Result           : No path found");
            } else {
                pw.printf("Shortest Distance: %d km%n", dist);
                pw.print("Path             : ");
                for (int i = 0; i < path.length; i++) {
                    pw.print(path[i]);
                    if (i < path.length - 1) pw.print(" -> ");
                }
                pw.println();
            }
            pw.println();
            pw.println("Total Cities : " + graph.getSize());
            pw.println("Total Roads  : " + graph.getEdgeCount());
            pw.println();
        }
    }
}
