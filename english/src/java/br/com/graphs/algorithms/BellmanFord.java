package br.com.graphs.algorithms;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;

public class BellmanFord {

    public static Double INF = Double.MAX_VALUE;
    private Stack<Integer> stack;   
    public static int marked[];
    public static int position;
    public static int topological_order[];

    public BellmanFord() {
        stack = new Stack<Integer>();
    }

    public static void topologicalOrderWithDFS(ArrayList<Integer>[] adjacencyList, int v) {

        BellmanFord.marked[v] = 1;

        for (int i = 0; i < adjacencyList[v].size(); i++) {
            if (BellmanFord.marked[adjacencyList[v].get(i)] == 0) {
                BellmanFord.topologicalOrderWithDFS(adjacencyList, adjacencyList[v].get(i));
            }
        }

        BellmanFord.topological_order[position] = v;
        BellmanFord.position--;
    }   

    public Map<String, ArrayList<Integer>> findLongestPathWithBellmanFord(String dirApp, String datasetType) throws FileNotFoundException {
        int numberOfNodes, source;
        Map<String, ArrayList<Integer>> idGraph_Path = new HashMap<String, ArrayList<Integer>>();

        FileReader fileReader = null;
        Scanner scanner = null;
        try {

            if ("flowchart".equals(datasetType)) {
                fileReader = new FileReader(dirApp + "/datasets_ijis/datasetFlowchart.txt");
                scanner = new Scanner(fileReader);
            } else if ("historical".equals(datasetType)) {
                fileReader = new FileReader(dirApp + "/datasets_ijis/datasetHistorical.txt");
                scanner = new Scanner(fileReader);
            } else if ("meanGraph".equals(datasetType)) {
                fileReader = new FileReader(dirApp + "/datasets_ijis/datasetMeanGraph.txt");
                scanner = new Scanner(fileReader);
            } else if ("medianGraph".equals(datasetType)) {
                fileReader = new FileReader(dirApp + "/datasets_ijis/datasetMedianGraph.txt");
                scanner = new Scanner(fileReader);
            } else if ("maximumGraph".equals(datasetType)) {
                fileReader = new FileReader(dirApp + "/datasets_ijis/datasetMaximumGraph.txt");
                scanner = new Scanner(fileReader);
            }

            Map<String, ArrayList<String>> idGraph_Vertices = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGraph_Edges = new HashMap<String, ArrayList<String>>();
            Map<String, HashMap<Integer, HashSet<Integer>>> idGraph_idVertice_Neighbors = new HashMap<String, HashMap<Integer, HashSet<Integer>>>();
            Map<String, HashMap<Integer, ArrayList<String>>> idGraph_idVertice_Edges = new HashMap<String, HashMap<Integer, ArrayList<String>>>();
            Map<String, HashMap<String, Double>> idGraph_Edge_Weight = new HashMap<String, HashMap<String, Double>>();
            Map<String, HashMap<Integer, String>> idGraph_idVertice_LabelVertice = new HashMap<String, HashMap<Integer, String>>();

            String idGraph = null;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.substring(0, 1).toUpperCase().equals("G")) {
                    idGraph = line.substring(line.indexOf('|') + 1);

                    if (!idGraph_Vertices.containsKey(idGraph)) {
                        idGraph_Vertices.put(idGraph, new ArrayList<String>());
                    }

                    if (!idGraph_Edges.containsKey(idGraph)) {
                        idGraph_Edges.put(idGraph, new ArrayList<String>());
                    }

                    if (!idGraph_idVertice_Neighbors.containsKey(idGraph)) {
                        idGraph_idVertice_Neighbors.put(idGraph, new HashMap<Integer, HashSet<Integer>>());
                    }

                    if (!idGraph_idVertice_Edges.containsKey(idGraph)) {
                        idGraph_idVertice_Edges.put(idGraph, new HashMap<Integer, ArrayList<String>>());
                    }

                    if (!idGraph_Edge_Weight.containsKey(idGraph)) {
                        idGraph_Edge_Weight.put(idGraph, new HashMap<String, Double>());
                    }

                    if (!idGraph_idVertice_LabelVertice.containsKey(idGraph)) {
                        idGraph_idVertice_LabelVertice.put(idGraph, new HashMap<Integer, String>());
                    }
                } else if (line.substring(0, 1).toUpperCase().equals("V")) {
                    String lineVertice = line;
                    idGraph_Vertices.get(idGraph).add(lineVertice);

                    String[] fields = lineVertice.split(Pattern.quote("|"));
                    int idVertice = Integer.parseInt(fields[1]);
                    String label = fields[2];

                    if (!idGraph_idVertice_Neighbors.get(idGraph).containsKey(idVertice)) {
                        idGraph_idVertice_Neighbors.get(idGraph).put(idVertice, new HashSet<Integer>());
                    }

                    if (!idGraph_idVertice_Edges.get(idGraph).containsKey(idVertice)) {
                        idGraph_idVertice_Edges.get(idGraph).put(idVertice, new ArrayList<String>());
                    }

                    if (!idGraph_idVertice_LabelVertice.get(idGraph).containsKey(idVertice)) {
                        idGraph_idVertice_LabelVertice.get(idGraph).put(idVertice, label);
                    }
                } else if (line.substring(0, 1).toUpperCase().equals("E")) {
                    String lineEdge = line;
                    idGraph_Edges.get(idGraph).add(lineEdge);

                    String[] fields = lineEdge.split(Pattern.quote("|"));
                    int idSource = Integer.parseInt(fields[1]);
                    int idTarget = Integer.parseInt(fields[3]);
                    double weight = Double.parseDouble(fields[5].replace(",", "."));

                    idGraph_idVertice_Neighbors.get(idGraph).get(idSource).add(idTarget);

                    String edge = fields[1] + "," + fields[3];

                    idGraph_idVertice_Edges.get(idGraph).get(idSource).add(edge);

                    weight = weight * (-1);

                    if (!idGraph_Edge_Weight.get(idGraph).containsKey(edge)) {
                        idGraph_Edge_Weight.get(idGraph).put(edge, weight);
                    }
                }
            }

            Set<String> idsGraphs = idGraph_Edges.keySet();
            for (String id : idsGraphs) {
                numberOfNodes = idGraph_idVertice_Neighbors.get(id).size();

                ArrayList<Integer>[] adjacencyList = (ArrayList<Integer>[]) new ArrayList[numberOfNodes];
                for (int i = 0; i < numberOfNodes; i++) {
                    adjacencyList[i] = new ArrayList<Integer>();
                }

                //int adjacency_matrix[][] = new int[numberOfNodes][numberOfNodes];
                for (int w = 0; w < numberOfNodes; w++) {
                    Iterator it = idGraph_idVertice_Neighbors.get(id).get(w).iterator();

                    while (it.hasNext()) {
                        int idNeighbor = (int) it.next();
                        adjacencyList[w].add(idNeighbor);
                        //adjacency_matrix[w][idNeighbor] = 1;
                    }
                }

                Double[] distance = new Double[numberOfNodes];
                int[] precursor = new int[numberOfNodes];

                Arrays.fill(distance, INF);
                Arrays.fill(precursor, -1);
                distance[0] = 0.0;
                source = 0;
                BellmanFord.marked = new int[numberOfNodes];
                BellmanFord.topological_order = new int[numberOfNodes];
                BellmanFord.position = numberOfNodes - 1;
                Arrays.fill(BellmanFord.marked, 0);

                BellmanFord bf = new BellmanFord();
                bf.topologicalOrderWithDFS(adjacencyList, source);
                //BELLMAN-FORD - Papadimitriou's book
                for (int i = 0; i < numberOfNodes; i++) { //for each vertice in topological order
                    int u = BellmanFord.topological_order[i];
                    
                    for (int y = 0; y < adjacencyList[u].size(); y++) {
                        int v = adjacencyList[u].get(y);
                        String edge = u + "," + v;

                        if (distance[v] > (distance[u] + idGraph_Edge_Weight.get(id).get(edge))) {
                            distance[v] = (distance[u] + idGraph_Edge_Weight.get(id).get(edge));
                            precursor[v] = u;
                        }
                    }                    
                }

                if (!idGraph_Path.containsKey(id)) {
                    idGraph_Path.put(id, new ArrayList<Integer>());
                }

                int x = 1; //target | the conclusion node is always one
                while (x != -1) {
                    idGraph_Path.get(id).add(x);
                    x = precursor[x];
                }
            }
        } catch (InputMismatchException inputMismatch) {
            System.out.println("Wrong Input format");
        }

        return idGraph_Path;
    }
}
