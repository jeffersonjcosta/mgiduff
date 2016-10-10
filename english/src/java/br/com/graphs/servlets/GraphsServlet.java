package br.com.graphs.servlets;

import br.com.graphs.actions.CreateDatasetAction;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GraphsServlet", urlPatterns = {"/graphs"})
public class GraphsServlet extends HttpServlet {

    private final String DIR_DATASETS = "datasets_ijis";   

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String dirApplication = request.getServletContext().getRealPath("");
        String dirDataSetFull = dirApplication + File.separator + DIR_DATASETS;
        
        String listIds = request.getParameter("listIds");
        String menu = request.getParameter("menu");
        String itemMenu = request.getParameter("item");
        String subItemMenu = request.getParameter("subitem");
                                      
        if ("true".equals(listIds)) {
            if ("flowchart".equals(itemMenu) || "flowchart".equals(subItemMenu) || "heaviestsEdges".equals(itemMenu) || "meanGraph".equals(itemMenu) || "medianGraph".equals(itemMenu) || "maximumGraph".equals(itemMenu) || "frequents".equals(subItemMenu)) {
                File datasetFlowchart = new File(dirDataSetFull + File.separator + "datasetFlowchart.txt");
                FileReader fileReaderDis = new FileReader(datasetFlowchart);
                Scanner scannerDis = new Scanner(fileReaderDis);
                ArrayList<String> idsCurriculum = new ArrayList<String>();

                while (scannerDis.hasNextLine()) {
                    String row = scannerDis.nextLine();
                    String[] fields = row.split("|");

                    String idCurriculum = row.substring(row.indexOf('|') + 1);

                    if (fields[0].equals("g")) {
                        idsCurriculum.add(idCurriculum);
                    }
                }

                Collections.sort(idsCurriculum);
                request.setAttribute("idsCurriculum", idsCurriculum);
                request.setAttribute("idsCurriculumSize", idsCurriculum.size());   
                
                if ("longestsPaths".equals(itemMenu)) {
                    CreateDatasetAction createDatasetAction = new CreateDatasetAction();
                    createDatasetAction.createDatasetLongestsPaths(request.getServletContext().getRealPath(""), "flowchart");
                }

                if ("heaviestsEdges".equals(itemMenu)) {
                    CreateDatasetAction createDatasetAction = new CreateDatasetAction();
                    createDatasetAction.createDatasetHeaviestEdges(request.getServletContext().getRealPath(""), subItemMenu, request.getParameter("sum"));
                }
                
                if ("meanGraph".equals(itemMenu)) {
                    CreateDatasetAction createDatasetAction = new CreateDatasetAction();
                    createDatasetAction.createDataSetMeanGraph(request.getServletContext().getRealPath(""));
                }
                
                if ("medianGraph".equals(itemMenu)) {
                    CreateDatasetAction createDatasetAction = new CreateDatasetAction();
                    createDatasetAction.createDataSetMedianGraph(request.getServletContext().getRealPath(""));
                }
                
                if ("maximumGraph".equals(itemMenu)) {
                    CreateDatasetAction createDatasetAction = new CreateDatasetAction();
                    createDatasetAction.createDataSetMaximumGraph(request.getServletContext().getRealPath(""));
                }
            } else if ("historical".equals(itemMenu) || "historical".equals(subItemMenu)) {
                File datasetHistorical = new File(dirDataSetFull + File.separator + "datasetHistorical.txt");
                FileReader fileReaderHis = new FileReader(datasetHistorical);
                Scanner scannerHis = new Scanner(fileReaderHis);
                ArrayList<String> idsStudents = new ArrayList<String>();

                while (scannerHis.hasNextLine()) {
                    String row = scannerHis.nextLine();
                    String[] fields = row.split("|");

                    String idStudent = row.substring(row.indexOf('|') + 1);

                    if (fields[0].equals("g")) {
                        idsStudents.add(idStudent);
                    }
                }

                Collections.sort(idsStudents);
                request.setAttribute("idsStudents", idsStudents);
                request.setAttribute("idsStudentsSize", idsStudents.size());                                
                
                if ("historical".equals(subItemMenu)) {
                    CreateDatasetAction createDatasetAction = new CreateDatasetAction();
                    createDatasetAction.createDatasetLongestsPaths(request.getServletContext().getRealPath(""), "historical");
                }                
            } else if ("meanGraph".equals(subItemMenu)) {
                File datasetMeanGraph = new File(dirDataSetFull + File.separator + "datasetMeanGraph.txt");
                FileReader fileReader = new FileReader(datasetMeanGraph);
                Scanner scanner = new Scanner(fileReader);
                ArrayList<String> idsCurriculum = new ArrayList<String>();

                while (scanner.hasNextLine()) {
                    String row = scanner.nextLine();
                    String[] fields = row.split("|");

                    String idStudent = row.substring(row.indexOf('|') + 1);

                    if (fields[0].equals("g")) {
                        idsCurriculum.add(idStudent);
                    }
                }

                Collections.sort(idsCurriculum);
                request.setAttribute("idsCurriculum", idsCurriculum);
                request.setAttribute("idsCurriculumSize", idsCurriculum.size());                                
                
                if ("longestsPaths".equals(itemMenu)) {
                    CreateDatasetAction createDatasetAction = new CreateDatasetAction();
                    createDatasetAction.createDatasetLongestsPaths(request.getServletContext().getRealPath(""), "meanGraph");
                }                
            } else if ("medianGraph".equals(subItemMenu)) {
                File datasetMedianGraph = new File(dirDataSetFull + File.separator + "datasetMedianGraph.txt");
                FileReader fileReader = new FileReader(datasetMedianGraph);
                Scanner scanner = new Scanner(fileReader);
                ArrayList<String> idsCurriculum = new ArrayList<String>();

                while (scanner.hasNextLine()) {
                    String row = scanner.nextLine();
                    String[] fields = row.split("|");

                    String idStudent = row.substring(row.indexOf('|') + 1);

                    if (fields[0].equals("g")) {
                        idsCurriculum.add(idStudent);
                    }
                }

                Collections.sort(idsCurriculum);
                request.setAttribute("idsCurriculum", idsCurriculum);
                request.setAttribute("idsCurriculumSize", idsCurriculum.size());                                
                
                if ("longestsPaths".equals(itemMenu)) {
                    CreateDatasetAction createDatasetAction = new CreateDatasetAction();
                    createDatasetAction.createDatasetLongestsPaths(request.getServletContext().getRealPath(""), "medianGraph");
                }                
            } else if ("maximumGraph".equals(subItemMenu)) {
                File datasetMaximumGraph = new File(dirDataSetFull + File.separator + "datasetMaximumGraph.txt");
                FileReader fileReader = new FileReader(datasetMaximumGraph);
                Scanner scanner = new Scanner(fileReader);
                ArrayList<String> idsCurriculum = new ArrayList<String>();

                while (scanner.hasNextLine()) {
                    String row = scanner.nextLine();
                    String[] fields = row.split("|");

                    String idStudent = row.substring(row.indexOf('|') + 1);

                    if (fields[0].equals("g")) {
                        idsCurriculum.add(idStudent);
                    }
                }

                Collections.sort(idsCurriculum);
                request.setAttribute("idsCurriculum", idsCurriculum);
                request.setAttribute("idsCurriculumSize", idsCurriculum.size());                                
                
                if ("longestsPaths".equals(itemMenu)) {
                    CreateDatasetAction createDatasetAction = new CreateDatasetAction();
                    createDatasetAction.createDatasetLongestsPaths(request.getServletContext().getRealPath(""), "maximumGraph");
                }                
            }  
            
            if ("heaviestsEdges".equals(itemMenu)) {
                request.setAttribute("datasetType", subItemMenu);
                request.setAttribute("sumType", request.getParameter("sum"));
                if ("lp".equals(subItemMenu)) {                    
                    getServletContext().getRequestDispatcher("/" + menu + "/" + itemMenu + "/" + "usingLP.jsp").forward(request, response);
                } else {                
                    getServletContext().getRequestDispatcher("/" + menu + "/" + itemMenu + "/" + "usingHistorical.jsp").forward(request, response);
                }                
            } else if ("longestsPaths".equals(itemMenu)) {
                getServletContext().getRequestDispatcher("/" + menu + "/" + itemMenu + "/" + subItemMenu + ".jsp").forward(request, response);
            }  else {
                 getServletContext().getRequestDispatcher("/" + menu + "/" + itemMenu + ".jsp").forward(request, response);
            }          
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
