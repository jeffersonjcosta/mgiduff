package br.com.graphs.algorithms;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

public class Backtracking {

    public static Double INF = Double.MAX_VALUE;
    public static Stack<Integer> stack;       
    public static int marked[];
    public static int position;
    public static int topological_order[];   
    public static ArrayList<String> paths;       
    public static ArrayList<String> pathsWithLabels;

    public Backtracking() {
        Backtracking.stack = new Stack<Integer>();              
    }

    public static void bck(ArrayList<Integer>[] adjacencyList, int v) {

        stack.push(v);
        Backtracking.marked[v] = 1;

        if (v == 1) {                 
            Backtracking.paths.add(stack.toString().replaceAll("\\[|\\]", "").replaceAll(",", "-").replaceAll(" ", ""));                           
        } else {
            for (int i = 0; i < adjacencyList[v].size(); i++) {
                if (Backtracking.marked[adjacencyList[v].get(i)] == 0) {
                    Backtracking.bck(adjacencyList, adjacencyList[v].get(i));
                }
            }
        }
       
        Backtracking.marked[v] = 0;
        stack.pop();
    }

    public ArrayList<String> allPathsWithBCK(String dirApp, String idGraph, String exhibitionType) throws FileNotFoundException, IOException {      
        FileReader fileReader = null;
        Scanner scanner = null;
        try {
            fileReader = new FileReader(dirApp + "/datasets_ijis/datasetFlowchart.txt");
            scanner = new Scanner(fileReader);

            Map<String, ArrayList<String>> idGraph_Vertices = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGraph_Edges = new HashMap<String, ArrayList<String>>();
            Map<String, HashMap<Integer, HashSet<Integer>>> idGraph_idVertice_Neighbors = new HashMap<String, HashMap<Integer, HashSet<Integer>>>();
            Map<String, HashMap<Integer, ArrayList<String>>> idGraph_idVertice_Edges = new HashMap<String, HashMap<Integer, ArrayList<String>>>();
            Map<String, HashMap<String, Double>> idGraph_Edge_Weight = new HashMap<String, HashMap<String, Double>>();
            Map<String, HashMap<Integer, String>> idGraph_idVertice_LabelVertice = new HashMap<String, HashMap<Integer, String>>();

            String idGraphDataset = null;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.substring(0, 1).toUpperCase().equals("G")) {
                    idGraphDataset = line.substring(line.indexOf('|') + 1);

                    if (!idGraph_Vertices.containsKey(idGraphDataset)) {
                        idGraph_Vertices.put(idGraphDataset, new ArrayList<String>());
                    }

                    if (!idGraph_Edges.containsKey(idGraphDataset)) {
                        idGraph_Edges.put(idGraphDataset, new ArrayList<String>());
                    }

                    if (!idGraph_idVertice_Neighbors.containsKey(idGraphDataset)) {
                        idGraph_idVertice_Neighbors.put(idGraphDataset, new HashMap<Integer, HashSet<Integer>>());
                    }

                    if (!idGraph_idVertice_Edges.containsKey(idGraphDataset)) {
                        idGraph_idVertice_Edges.put(idGraphDataset, new HashMap<Integer, ArrayList<String>>());
                    }

                    if (!idGraph_Edge_Weight.containsKey(idGraphDataset)) {
                        idGraph_Edge_Weight.put(idGraphDataset, new HashMap<String, Double>());
                    }

                    if (!idGraph_idVertice_LabelVertice.containsKey(idGraphDataset)) {
                        idGraph_idVertice_LabelVertice.put(idGraphDataset, new HashMap<Integer, String>());
                    }
                } else if (line.substring(0, 1).toUpperCase().equals("V")) {
                    String lineVertice = line;
                    idGraph_Vertices.get(idGraphDataset).add(lineVertice);

                    String[] fields = lineVertice.split(Pattern.quote("|"));
                    int idVertice = Integer.parseInt(fields[1]);
                    String label = fields[2];

                    if (!idGraph_idVertice_Neighbors.get(idGraphDataset).containsKey(idVertice)) {
                        idGraph_idVertice_Neighbors.get(idGraphDataset).put(idVertice, new HashSet<Integer>());
                    }

                    if (!idGraph_idVertice_Edges.get(idGraphDataset).containsKey(idVertice)) {
                        idGraph_idVertice_Edges.get(idGraphDataset).put(idVertice, new ArrayList<String>());
                    }

                    if (!idGraph_idVertice_LabelVertice.get(idGraphDataset).containsKey(idVertice)) {
                        idGraph_idVertice_LabelVertice.get(idGraphDataset).put(idVertice, label);
                    }
                } else if (line.substring(0, 1).toUpperCase().equals("E")) {
                    String lineEdge = line;
                    idGraph_Edges.get(idGraphDataset).add(lineEdge);

                    String[] fields = lineEdge.split(Pattern.quote("|"));
                    int idSource = Integer.parseInt(fields[1]);
                    int idTarget = Integer.parseInt(fields[3]);
                    double weight = Double.parseDouble(fields[5].replace(",", "."));

                    idGraph_idVertice_Neighbors.get(idGraphDataset).get(idSource).add(idTarget);

                    String edge = fields[1] + "," + fields[3];

                    idGraph_idVertice_Edges.get(idGraphDataset).get(idSource).add(edge);

                    weight = weight * (-1);

                    if (!idGraph_Edge_Weight.get(idGraphDataset).containsKey(edge)) {
                        idGraph_Edge_Weight.get(idGraphDataset).put(edge, weight);
                    }
                }
            }

            int numberOfNodes, source = 0;

            numberOfNodes = idGraph_idVertice_Neighbors.get(idGraph).size();

            ArrayList<Integer>[] adjacencyList = (ArrayList<Integer>[]) new ArrayList[numberOfNodes];
            for (int i = 0; i < numberOfNodes; i++) {
                adjacencyList[i] = new ArrayList<Integer>();
            }

            for (int w = 0; w < numberOfNodes; w++) {
                Iterator it = idGraph_idVertice_Neighbors.get(idGraph).get(w).iterator();

                while (it.hasNext()) {
                    int idNeighbor = (int) it.next();
                    adjacencyList[w].add(idNeighbor);
                }
            }

            Backtracking.marked = new int[numberOfNodes];           
            Backtracking.paths = new ArrayList<String>();
           
            Arrays.fill(Backtracking.marked, 0);         

            Backtracking b = new Backtracking();
            b.bck(adjacencyList, source);

            Backtracking.pathsWithLabels = new ArrayList<String>();            
            Iterator it = Backtracking.paths.iterator();
            while (it.hasNext()) {
                String path = (String) it.next();
                                
                String[] ids = path.split("-");
                String pathWithLabel = "";
                for (int i = 0; i < ids.length; i++) {                    
                    pathWithLabel += idGraph_idVertice_LabelVertice.get(idGraph).get(Integer.parseInt(ids[i]));
                    if (!ids[i].equals("1")) {
                        pathWithLabel += " - ";
                    }                                        
                }
                
                pathsWithLabels.add(pathWithLabel);
            }
        } catch (InputMismatchException inputMismatch) {
            System.out.println("Wrong Input format");
        }

        if ("label".equals(exhibitionType)) {
            return Backtracking.pathsWithLabels;
        } else {
            return Backtracking.paths;
        }
    }
}
