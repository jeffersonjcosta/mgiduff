package br.com.graphs.servlets;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewGraphServlet", urlPatterns = {"/viewGraph"})
public class ViewGraphServlet extends HttpServlet {

    private final String DIR_DATASETS = "datasets_ijis";
    private final String DIR_GEXF_VISUALIZATION = "gexfView";
    private final String DIR_GRAPHVIZ_VISUALIZATION = "graphVizView";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dirApp = request.getServletContext().getRealPath("");
        String dirDatasetFull = dirApp + File.separator + DIR_DATASETS;

        String gexf = null;
        String dot = null;
        
        String idGraph = "";
        String typeOfGraph = "";
        String gephi = null;
        String graphViz = null;

        idGraph = request.getParameter("idGraph");
        typeOfGraph = request.getParameter("type");
        gephi = request.getParameter("gephi");
        graphViz = request.getParameter("graphViz");
                 
        File dataset = null;
        FileReader fileReader = null;
        Scanner scanner = null;
        String filePrefix = null;
         
        if ("flowchart".equals(typeOfGraph)) {
            dataset = new File(dirDatasetFull + File.separator + "datasetFlowchart.txt");
            filePrefix = "graphFlowchart";
        } else if ("historical".equals(typeOfGraph)) {
            dataset = new File(dirDatasetFull + File.separator + "datasetHistorical.txt");
            filePrefix = "graphHistorical";
        } else if ("longestPathFlowchart".equals(typeOfGraph)) {
            dataset = new File(dirDatasetFull + File.separator + "datasetPathsFlowchart.txt");
            filePrefix = "lpFlowchart";
        } else if ("longestPathHistorical".equals(typeOfGraph)) {
            dataset = new File(dirDatasetFull + File.separator + "datasetPathsHistorical.txt");
            filePrefix = "lpHistorical";
        } else if ("heaviestsEdges".equals(typeOfGraph)) {
            dataset = new File(dirDatasetFull + File.separator + "datasetHeaviestsEdges.txt");
        } else if ("meanGraph".equals(typeOfGraph)) {
            dataset = new File(dirDatasetFull + File.separator + "datasetMeanGraph.txt");
            filePrefix = "meanGraph";
        } else if ("longestPathMeanGraph".equals(typeOfGraph)) {
            dataset = new File(dirDatasetFull + File.separator + "datasetPathsMeanGraph.txt");
            filePrefix = "lpMeanGraph";
        } else if ("medianGraph".equals(typeOfGraph)) {
            dataset = new File(dirDatasetFull + File.separator + "datasetMedianGraph.txt");
            filePrefix = "medianGraph";
        } else if ("longestPathMedianGraph".equals(typeOfGraph)) {
            dataset = new File(dirDatasetFull + File.separator + "datasetPathsMedianGraph.txt");
            filePrefix = "lpMedianGraph";
        } else if ("maximumGraph".equals(typeOfGraph)) {
            dataset = new File(dirDatasetFull + File.separator + "datasetMaximumGraph.txt");
            filePrefix = "maximumGraph";
        } else if ("longestPathMaximumGraph".equals(typeOfGraph)) {
            dataset = new File(dirDatasetFull + File.separator + "datasetPathsMaximumGraph .txt");
            filePrefix = "lpMaximumGraph";
        } 
        
        fileReader = new FileReader(dataset);
        scanner = new Scanner(fileReader);
                
        if (!typeOfGraph.contains("longest")) {                   
            Map<String, ArrayList<String>> idGraph_Vertices = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGraph_Edges = new HashMap<String, ArrayList<String>>();
            Map<String, HashSet<String>> idGraph_Periods = new HashMap<String, HashSet<String>>();
            Map<String, HashSet<String>> period_Subjects = new HashMap<String, HashSet<String>>();

            String idGraphInDataset = null;
            String idSubject = null;
            String period = null;
            String edge = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.substring(0, 1).toUpperCase().equals("G")) {
                    idGraphInDataset = line.substring(line.indexOf('|') + 1);

                    if (!idGraph_Vertices.containsKey(idGraphInDataset)) {
                        idGraph_Vertices.put(idGraphInDataset, new ArrayList<String>());
                        idGraph_Periods.put(idGraphInDataset, new HashSet<String>());
                    }

                    if (!idGraph_Edges.containsKey(idGraphInDataset)) {
                        idGraph_Edges.put(idGraphInDataset, new ArrayList<String>());
                    }
                } else if (line.substring(0, 1).toUpperCase().equals("V")) {
                    String[] fields = line.split(Pattern.quote("|"));

                    idSubject = fields[2];
                    period = fields[3];

                    idGraph_Vertices.get(idGraphInDataset).add(idSubject);
                    idGraph_Periods.get(idGraphInDataset).add(period);

                    if (idGraph.equals(idGraphInDataset)) {
                        if (!period_Subjects.containsKey(period)) {
                            period_Subjects.put(period, new HashSet<String>());
                        }

                        period_Subjects.get(period).add(idSubject);
                    }
                } else if (line.substring(0, 1).toUpperCase().equals("E")) {

                    String[] fields = line.split(Pattern.quote("|"));

                    String vertexSource = fields[2];
                    String vertexDestination = fields[4];
                    String weightEdge = fields[5];

                    edge = vertexSource + "|" + vertexDestination + "|" + weightEdge;
                    idGraph_Edges.get(idGraphInDataset).add(edge);
                }
            }

            if ("true".equals(gephi)) {
                if (idGraph_Vertices.containsKey(idGraph)) {
                    gexf = this.createGexf(idGraph, filePrefix, idGraph_Vertices, idGraph_Edges, idGraph_Periods, period_Subjects);
                    response.sendRedirect(getServletContext().getContextPath() + File.separator + DIR_GEXF_VISUALIZATION + "/index.jsp#files/" + gexf);
                } else {                    
                    request.setAttribute("idGraph", idGraph);                                        
                    getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } else if ("true".equals(graphViz)) {
                dot = this.createDotGraphViz(idGraph, filePrefix, idGraph_Vertices, idGraph_Edges);
                response.sendRedirect(getServletContext().getContextPath() + File.separator + DIR_GRAPHVIZ_VISUALIZATION + "/index.jsp?dotGraphViz=" + dot);
            }       
        } else {
            Map<String, ArrayList<String>> idGraph_VerticesOfTheLongestPath = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGraph_EdgesOfTheLongestPath = new HashMap<String, ArrayList<String>>();

            String idGraphPath = null;
            String verticeLabel = null;
            String edge = null;
                     
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.substring(0, 1).toUpperCase().equals("C")) {
                    String[] fields = line.split(Pattern.quote("|"));
                    
                    idGraphPath = fields[1];                     
                                        
                    if ("longestPathHistorical".equals(typeOfGraph)) {
                        idGraphPath = fields[1] + "|" + fields[2];
                    }                                        

                    if (!idGraph_VerticesOfTheLongestPath.containsKey(idGraphPath)) {
                        idGraph_VerticesOfTheLongestPath.put(idGraphPath, new ArrayList<String>());
                    }

                    if (!idGraph_EdgesOfTheLongestPath.containsKey(idGraphPath)) {
                        idGraph_EdgesOfTheLongestPath.put(idGraphPath, new ArrayList<String>());
                    }                                        
                } else if (line.substring(0, 1).toUpperCase().equals("V")) {
                    String[] fields = line.split(Pattern.quote("|"));
                    verticeLabel = fields[2];
                    idGraph_VerticesOfTheLongestPath.get(idGraphPath).add(verticeLabel);
                } else if (line.substring(0, 1).toUpperCase().equals("E")) {

                    String[] fields = line.split(Pattern.quote("|"));

                    String sourceVertice = fields[2];
                    String destinationVertex = fields[4];
                    String weight = fields[5];

                    edge = sourceVertice + "|" + destinationVertex + "|" + weight;
                    idGraph_EdgesOfTheLongestPath.get(idGraphPath).add(edge);
                }
            }
                                    
            if ("true".equals(gephi)) {
                gexf = this.createGexfForPaths(idGraph, filePrefix, idGraph_VerticesOfTheLongestPath, idGraph_EdgesOfTheLongestPath);
                response.sendRedirect(getServletContext().getContextPath() + File.separator + DIR_GEXF_VISUALIZATION + "/index.jsp#files/" + gexf);
            } else if ("true".equals(graphViz)) {
                dot = this.createDotGraphViz(idGraph, filePrefix, idGraph_VerticesOfTheLongestPath, idGraph_EdgesOfTheLongestPath);
                response.sendRedirect(getServletContext().getContextPath() + File.separator + DIR_GRAPHVIZ_VISUALIZATION + "/index.jsp?dotGraphViz=" + dot);
            }              
        }                
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private String createGexf(String idGraph, String filePrefix, Map<String, ArrayList<String>> idGraph_Vertices, Map<String, ArrayList<String>> idGraph_Edges, Map<String, HashSet<String>> idGraph_Periods, Map<String, HashSet<String>> period_Subjects) throws IOException {
        
        String dirApp = getServletContext().getRealPath("");
        String dirFilesGexfFull = dirApp + File.separator + DIR_GEXF_VISUALIZATION + File.separator + "files";

        File fileDir = new File(dirFilesGexfFull);
        if (!fileDir.exists()) {
            fileDir.mkdir();
            fileDir.setWritable(true);
            fileDir.setReadable(true);
        }
        
        String idGraphWithoutPipe = idGraph.replace("|", "");
        String idGraphWithoutDots = idGraphWithoutPipe.replace(".", "");               

        String gexfNome = filePrefix + idGraphWithoutDots + ".gexf";

        String fullPathWithFileName = dirFilesGexfFull + File.separator + gexfNome;

        FileWriter fileOfVisualization = new FileWriter(fullPathWithFileName);
        PrintWriter gexfPrint = new PrintWriter(fileOfVisualization, true);

        gexfPrint.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        gexfPrint.println("<gexf xmlns=\"http://www.gexf.net/1.1draft\" version=\"1.1\" xmlns:viz=\"http://www.gexf.net/1.1draft/viz\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.1draft http://www.gexf.net/1.1draft/gexf.xsd\">");
        gexfPrint.println("<meta>");
        gexfPrint.println("<description>Curriculum: " + idGraph + "</description>");
        gexfPrint.println("</meta>");
        gexfPrint.println("<graph defaultedgetype=\"directed\" timeformat=\"double\" mode=\"dynamic\">");

        String sy;
        int y;
        int aux = 9;
        int r;
        int g;
        int b;
        Random generator = new Random();

        gexfPrint.println("<nodes>");

        Iterator iter1 = idGraph_Periods.get(idGraph).iterator();
        while (iter1.hasNext()) {
            String period = (String) iter1.next();

            Iterator iter2 = period_Subjects.get(period).iterator();

            y = -30;
            r = generator.nextInt(255);
            g = generator.nextInt(230);
            b = generator.nextInt(200);

            while (iter2.hasNext()) {
                String idDisc = (String) iter2.next();

                y += aux;

                gexfPrint.println("<node id=\"" + idDisc + "\" label=\"" + idDisc + "\">");
                gexfPrint.println("<attvalues></attvalues>");
                gexfPrint.println("<viz:size value=\"2\"></viz:size>");

                String sx = period + "9";
                sy = Integer.toString(y);

                if (idDisc.equals("Admission")) {
                    sx = "-10";
                    sy = "-5";
                    r = 0;
                    g = 0;
                    b = 0;
                }
                if (idDisc.equals("Conclusion")) {
                    int xOfTheLastVertice = (idGraph_Vertices.get(idGraph).size() * 2) + 30;
                    sx = Integer.toString(xOfTheLastVertice);
                    sy = "-5";
                    r = 0;
                    g = 0;
                    b = 0;
                }

                gexfPrint.println("<viz:position x=\"" + sx + "\" y=\"" + sy + "\"></viz:position>");//Posição na tela
                gexfPrint.println("<viz:color r=\"" + r + "\" g=\"" + g + "\" b=\"" + b + "\"></viz:color>"); //Color
                gexfPrint.println("</node>");
            }
        }

        gexfPrint.println("</nodes>");

        gexfPrint.println("<edges>");

        for (int x = 0; x < idGraph_Edges.get(idGraph).size(); x++) {
            String edge = idGraph_Edges.get(idGraph).get(x);

            String[] fields = edge.split(Pattern.quote("|"));                        
            
            gexfPrint.println("<edge id=\"" + x + "\" source=\"" + fields[0] + "\" target=\"" + fields[1] + "\" label=\"" + fields[2] + "\">");
            gexfPrint.println("<attvalues></attvalues>");
            gexfPrint.println("</edge>");
        }

        gexfPrint.println("</edges>");

        gexfPrint.println("</graph>");
        gexfPrint.println("</gexf>");

        return gexfNome;
    }
    
    private String createGexfForPaths(String idGraph, String filePrefix, Map<String, ArrayList<String>> idGraph_VerticesOfTheLongestPath, Map<String, ArrayList<String>> idGraph_EdgesOfTheLongestPath) throws IOException {
        String dirApp = getServletContext().getRealPath("");
        String dirFilesGexfFull = dirApp + File.separator + DIR_GEXF_VISUALIZATION + File.separator + "files";

        File fileDir = new File(dirFilesGexfFull);
        if (!fileDir.exists()) {
            fileDir.mkdir();
            fileDir.setWritable(true);
            fileDir.setReadable(true);
        }

        String idGraphWithoutPipe = idGraph.replace("|", "");
        String idGraphWithoutDots = idGraphWithoutPipe.replace(".", "");

        String gexfNome = filePrefix + idGraphWithoutDots + ".gexf";

        String fullPathWithFileName = dirFilesGexfFull + File.separator + gexfNome;

        FileWriter fileOfVisualization = new FileWriter(fullPathWithFileName);
        PrintWriter gexfPrint = new PrintWriter(fileOfVisualization, true);
    
        gexfPrint.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        gexfPrint.println("<gexf xmlns=\"http://www.gexf.net/1.1draft\" version=\"1.1\" xmlns:viz=\"http://www.gexf.net/1.1draft/viz\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.1draft http://www.gexf.net/1.1draft/gexf.xsd\">");
        gexfPrint.println("<meta>");
        gexfPrint.println("<description>Longest Path Curriculum: " + idGraph + "</description>");
        gexfPrint.println("</meta>");
        gexfPrint.println("<graph defaultedgetype=\"directed\" timeformat=\"double\" mode=\"dynamic\">");

        gexfPrint.println("<nodes>");

        Iterator iter1 = idGraph_VerticesOfTheLongestPath.get(idGraph).iterator();
        int x = 0;
        int y = 0;
        while (iter1.hasNext()) {
            String vertice = (String) iter1.next();

            gexfPrint.println("<node id=\"" + vertice + "\" label=\"" + vertice + "\">");
            gexfPrint.println("<attvalues></attvalues>");
            gexfPrint.println("<viz:size value=\"2\"></viz:size>");

            gexfPrint.println("<viz:position x=\"" + (x += 15) + "\" y=\"" + y + "\"></viz:position>");//Posição na tela
            gexfPrint.println("<viz:color r=\"70\" g=\"110\" b=\"160\"></viz:color>"); //Color
            gexfPrint.println("</node>");
        }

        gexfPrint.println("</nodes>");

        gexfPrint.println("<edges>");

        for (int w = 0; w < idGraph_EdgesOfTheLongestPath.get(idGraph).size(); w++) {
            String edge = idGraph_EdgesOfTheLongestPath.get(idGraph).get(w);

            String[] fields = edge.split(Pattern.quote("|"));                      

            gexfPrint.println("<edge id=\"" + w + "\" source=\"" + fields[0] + "\" target=\"" + fields[1] + "\" label=\"" + fields[2] + "\">");
            gexfPrint.println("<attvalues></attvalues>");
            gexfPrint.println("</edge>");
        }

        gexfPrint.println("</edges>");

        gexfPrint.println("</graph>");
        gexfPrint.println("</gexf>");

        return gexfNome;
    }
    
    private String createDotGraphViz(String idGraph, String filePrefix, Map<String, ArrayList<String>> idGraph_Vertices, Map<String, ArrayList<String>> idGraph_Edges) throws IOException {
        String dirApp = getServletContext().getRealPath("");
        String dirFilesGraphVizFull = dirApp + File.separator + DIR_GRAPHVIZ_VISUALIZATION + File.separator + "data";

        File fileDir = new File(dirFilesGraphVizFull);
        if (!fileDir.exists()) {
            fileDir.mkdir();
            fileDir.setWritable(true);
            fileDir.setReadable(true);
        }

        String idGraphWithoutPipe = idGraph.replace("|", "");
        String idGraphWithoutDots = idGraphWithoutPipe.replace(".", "");

        String fileDOTName = filePrefix + idGraphWithoutDots + "dot.gv.txt";

        String fullPathWithFileName = dirFilesGraphVizFull + File.separator + fileDOTName;

        FileWriter fileOfVisualization = new FileWriter(fullPathWithFileName);
        PrintWriter graphVizPrint = new PrintWriter(fileOfVisualization, true);
        
        graphVizPrint.println("digraph " + idGraphWithoutDots);
        graphVizPrint.println("{");
        graphVizPrint.print("node[shape=circle fontSize=11]; ");
        
        Iterator iter1 = idGraph_Vertices.get(idGraph).iterator();
        while (iter1.hasNext()) {
            String vertice = (String) iter1.next();
            
            graphVizPrint.print(vertice + " ");
        }
 
        graphVizPrint.println();        
        graphVizPrint.println("edge[fontSize=11]");
        
        Iterator iter2 = idGraph_Edges.get(idGraph).iterator();
        while (iter2.hasNext()) {
            String edge = (String) iter2.next();
                        
            String[] fields = edge.split(Pattern.quote("|"));
            
            graphVizPrint.println("\"" + fields[0] + "\"" + " -> \"" + fields[1] + "\"[label=\"" + fields[2] + "\"];");
        }
        
        graphVizPrint.println("}");  
        
        return fileDOTName;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }      
}
