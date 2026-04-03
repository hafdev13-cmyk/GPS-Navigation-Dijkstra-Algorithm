
# GPS Navigation System

## Overview

A high-performance Java implementation of a shortest-path routing engine utilizing Dijkstra's algorithm for optimal pathfinding across a complex road network. The system processes 100 geographically distributed nodes connected by 501 weighted edges, delivering efficient single-source shortest path computations with comprehensive execution tracing and persistent result logging.

## Core Features

- Scalable Graph Architecture: 100 nodes with 501 bidirectional weighted edges
- Dijkstra's Algorithm Implementation: O(V²) single-source shortest path computation
- Interactive Query Interface: Real-time pathfinding with multi-query session support
- Execution Tracing: Step-by-step algorithm visualization with relaxation details
- Persistent Logging: Append-based result archival with comprehensive metadata
- Robust Error Handling: Graceful management of unreachable destinations and invalid inputs
- CSV Data Integration: Automated graph construction from structured road network data

## Architecture

### Project Structure

```
├── src/
│   ├── Main.java              # Application entry point and CLI orchestration
│   ├── Graph.java             # Adjacency matrix-based graph representation
│   ├── Dijkstra.java          # Single-source shortest path algorithm
│   └── CSVReader.java         # CSV parsing and data ingestion
├── data/
│   └── roads.csv              # Road network dataset (501 edges)
├── output/
│   └── results.txt            # Query results log (append mode)
├── out/                       # Compiled bytecode (.class files)
└── README.md                  # This documentation
```

### Component Responsibilities

| Component | Purpose |
|-----------|---------|
| Main.java | CLI interface, user input validation, session management |
| Graph.java| Adjacency matrix construction, edge management, graph queries |
| Dijkstra.java| Shortest path computation, distance tracking, path reconstruction |
| CSVReader.java | CSV parsing, data validation, graph initialization |

## Getting Started

### Prerequisites

- Java 8 or higher
- CSV file containing road network data

### Compilation

```bash
javac -d out src/CSVReader.java src/Graph.java src/Dijkstra.java src/Main.java
```

### Execution

```bash
java -cp out Main
```

## Usage Guide

### Interactive Session

```
Enter Source City      (0 - 99): 5
Enter Destination City (0 - 99): 25
Shortest Distance: 37 km
Path: 5 -> 15 -> 25
```

### Query Workflow

1. Provide source node identifier (0-99)
2. Provide destination node identifier (0-99)
3. System computes and displays optimal path with cumulative distance
4. Option to execute additional queries or terminate session

## Data Specification

### Input Format

`data/roads.csv` structure:
```csv
source,destination,distance
0,1,10
0,2,15
1,3,20
...
```

### Output Format

`output/results.txt` contains:
- Query timestamp and parameters
- Computed shortest distance (km)
- Complete path sequence
- Graph statistics and execution metrics

### Console Output

- Adjacency matrix sample (first 7 nodes)
- Algorithm execution trace (first 10 iterations with visited set and relaxation operations)
- Final shortest path and distance
- Distance vector from source node

## Algorithm Specification

### Dijkstra's Algorithm

**Time Complexity: O(V²) where V = 100 nodes  
**Space Complexity: O(V²) for adjacency matrix storage  
**Graph Properties: Undirected, weighted, connected  
**Edge Count: 501 unique bidirectional roads

### Implementation Details

- Distance initialization: ∞ for all nodes except source (0)
- Relaxation: Updates distances when shorter paths are discovered
- Termination: All nodes visited or unreachable destinations confirmed
- Path reconstruction: Backtracking through predecessor array

## Performance Characteristics

| Metric | Value |
|--------|-------|
| Nodes | 100 |
| Edges | 501 |
| Avg. Degree | ~10 |
| Time per Query | O(V²) ≈ 10,000 operations |
| Memory Footprint | ~80 KB (adjacency matrix) |

## Error Handling

- Invalid node identifiers: Validation with range checking (0-99)
- Unreachable destinations: Graceful notification with infinite distance indicator
- Malformed CSV data: Exception handling with informative error messages
- File I/O errors: Fallback mechanisms for result logging

## Design Patterns

- ingleton Pattern Graph instance management
- Strategy Pattern: Algorithm encapsulation in Dijkstra class
- Builder Pattern: Graph construction from CSV data
- observer Pattern: Result logging and event tracking

## Future Enhancements

- A* algorithm implementation for heuristic-based optimization
- Bidirectional Dijkstra for improved performance
- Priority queue (min-heap) implementation for O(E log V) complexity
- Multi-threaded query processing
- REST API interface for distributed access
- Visualization module for path rendering

## Technical Notes

- All distances are bidirectional (undirected graph representation)
- Results append to log file (non-destructive writes)
- Algorithm execution includes detailed step-by-step tracing
- System handles disconnected graph components appropriately
