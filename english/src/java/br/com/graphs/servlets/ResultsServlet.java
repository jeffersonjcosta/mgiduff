package br.com.graphs.servlets;

import br.com.graphs.algorithms.Backtracking;
import br.com.graphs.util.SortMap;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ResultadosServlet", urlPatterns = {"/results"})
public class ResultsServlet extends HttpServlet {

    private final String DIR_DATASETS = "datasets_ijis";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dirApp = request.getServletContext().getRealPath("");
        String dirDatasetFull = dirApp + File.separator + DIR_DATASETS;

        String idGraph = "";
        String resultType = "";
        String datasetType = null;

        idGraph = request.getParameter("idGraph");
        resultType = request.getParameter("type");
        datasetType = request.getParameter("dataset");

        File dataset = null;
        FileReader fileReader = null;
        Scanner scanner = null;

        if ("heaviestEdgesRank".equals(resultType)) {
            dataset = new File(dirDatasetFull + File.separator + "datasetHeaviestsEdges.txt");

            fileReader = new FileReader(dataset);
            scanner = new Scanner(fileReader);

            Map<String, ArrayList<String>> idGraph_Vertices = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGraph_Edges = new HashMap<String, ArrayList<String>>();

            String idGraphInDataset = null;
            String edge = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.substring(0, 1).toUpperCase().equals("G")) {
                    idGraphInDataset = line.substring(line.indexOf('|') + 1);

                    if (!idGraph_Vertices.containsKey(idGraphInDataset)) {
                        idGraph_Vertices.put(idGraphInDataset, new ArrayList<String>());
                    }

                    if (!idGraph_Edges.containsKey(idGraphInDataset)) {
                        idGraph_Edges.put(idGraphInDataset, new ArrayList<String>());
                    }
                } else if (line.substring(0, 1).toUpperCase().equals("E")) {

                    String[] fields = line.split(Pattern.quote("|"));

                    String vertexSource = fields[2];
                    String vertexDestination = fields[4];
                    String weightEdge = fields[5];

                    edge = vertexSource + "," + vertexDestination + "," + weightEdge;
                    idGraph_Edges.get(idGraphInDataset).add(edge);
                }
            }

            Map<String, Integer> disorderedMap = new HashMap<String, Integer>();

            Iterator i = idGraph_Edges.get(idGraph).iterator();
            while (i.hasNext()) {
                String edge2 = (String) i.next();

                String[] fields = edge2.split(",");

                String vertexSource = fields[0];
                String vertexDestination = fields[1];
                int weight = Integer.parseInt(fields[2]);

                disorderedMap.put(vertexSource + " - " + vertexDestination, weight);
            }

            Map<String, Integer> orderedMap = SortMap.sortByComparator(disorderedMap);

            request.setAttribute("orderedMap", orderedMap);
            request.setAttribute("idGraph", idGraph);
            request.setAttribute("sumType", request.getParameter("sum"));
            request.setAttribute("datasetType", datasetType);
            getServletContext().getRequestDispatcher("/results/heaviestEdges/ranking.jsp").forward(request, response);
        } else if ("rankingLP".equals(resultType)) {
            FileReader fileReaderFlowchart = new FileReader(dirApp + File.separator + DIR_DATASETS + File.separator + "datasetFlowchart.txt");
            Scanner scannerFlowchart = new Scanner(fileReaderFlowchart);

            Map<String, HashMap<Integer, String>> idGraph_idVertice_LabelVertice = new HashMap<String, HashMap<Integer, String>>();
            String idGraphDataset = null;
            while (scannerFlowchart.hasNextLine()) {
                String line = scannerFlowchart.nextLine();

                if (line.substring(0, 1).toUpperCase().equals("G")) {
                    idGraphDataset = line.substring(line.indexOf('|') + 1);

                    if (!idGraph_idVertice_LabelVertice.containsKey(idGraphDataset)) {
                        idGraph_idVertice_LabelVertice.put(idGraphDataset, new HashMap<Integer, String>());
                    }
                } else if (line.substring(0, 1).toUpperCase().equals("V")) {                    
                    String[] fields = line.split(Pattern.quote("|"));
                    int idVertice = Integer.parseInt(fields[1]);
                    String label = fields[2];
                    
                    if (!idGraph_idVertice_LabelVertice.get(idGraphDataset).containsKey(idVertice)) {
                        idGraph_idVertice_LabelVertice.get(idGraphDataset).put(idVertice, label);
                    }
                }
            }

            fileReader = new FileReader(dirApp + File.separator + DIR_DATASETS + File.separator + "datasetPathsHistorical.txt");
            scanner = new Scanner(fileReader);

            Map<String, ArrayList<String>> idGraph_longestsPaths = new HashMap<String, ArrayList<String>>();

            String idCurriculum = null;
            String lpStudent = null;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.substring(0, 1).toUpperCase().equals("C")) {
                    String[] fields = line.split(Pattern.quote("|"));

                    idCurriculum = fields[2];
                    lpStudent = fields[3];

                    if (!idGraph_longestsPaths.containsKey(idCurriculum)) {
                        idGraph_longestsPaths.put(idCurriculum, new ArrayList<String>());
                    }

                    idGraph_longestsPaths.get(idCurriculum).add(lpStudent);
                }
            }

            Map<String, ArrayList<String>> idGraph_AllPaths = new HashMap<String, ArrayList<String>>();
            Backtracking bck = new Backtracking();
            idGraph_AllPaths.put(idGraph, bck.allPathsWithBCK(dirApp, idGraph, "id"));

            Map<String, Integer> paths_amount = new HashMap<String, Integer>();

            Iterator it = idGraph_AllPaths.get(idGraph).iterator();
            while (it.hasNext()) {
                String pathCurriculum = (String) it.next();

                paths_amount.put(pathCurriculum, Collections.frequency(idGraph_longestsPaths.get(idGraph), pathCurriculum));
            }
                                             
            Map<String, Integer> pathsWithVerticesLabel_amount = new HashMap<String, Integer>();
            Set<String> paths = paths_amount.keySet();
            int aux = 0;
            for (String path : paths) {
                String[] ids = path.split("-");
                String pathWithLabel = "";
                for (int i = 0; i < ids.length; i++) {
                    pathWithLabel += idGraph_idVertice_LabelVertice.get(idGraph).get(Integer.parseInt(ids[i]));
                    if (!ids[i].equals("1")) {
                        pathWithLabel += " - ";
                    }
                }
                
                pathsWithVerticesLabel_amount.put(pathWithLabel, paths_amount.get(path));
                
                aux++;
            }
            
            Map<String, Integer> orderedMap = SortMap.sortByComparator(pathsWithVerticesLabel_amount);                         
           
            request.setAttribute("idGraph", idGraph);
            request.setAttribute("rankPaths", orderedMap);
            getServletContext().getRequestDispatcher("/results/longestsPaths/pathsRank.jsp").forward(request, response);
        } else if ("allPaths".equals(resultType)) {
            Map<String, ArrayList<String>> idGraph_AllPaths = new HashMap<String, ArrayList<String>>();

            Backtracking bck = new Backtracking();

            idGraph_AllPaths.put(idGraph, bck.allPathsWithBCK(dirApp, idGraph, "label"));

            request.setAttribute("idGraph", idGraph);
            request.setAttribute("paths", idGraph_AllPaths.get(idGraph));
            getServletContext().getRequestDispatcher("/results/longestsPaths/allPaths.jsp").forward(request, response);
        }
    }
}
