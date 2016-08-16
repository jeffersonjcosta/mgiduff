package br.com.grafos.servlets;

import br.com.grafos.actions.CriarDatasetAction;
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

@WebServlet(name = "GrafosServlet", urlPatterns = {"/grafos"})
public class GrafosServlet extends HttpServlet {

    private final String DIR_DATASETS = "datasets";   

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String diretorioAplicacao = request.getServletContext().getRealPath("");
        String dirDeDatasetCompleto = diretorioAplicacao + File.separator + DIR_DATASETS;
        
        String listarIds = request.getParameter("listarIds");
        String menu = request.getParameter("menu");
        String itemMenu = request.getParameter("item");
        String subItemMenu = request.getParameter("subitem");
                                      
        if ("true".equals(listarIds)) {
            if ("gradeCurricular".equals(itemMenu) || "gradeCurricular".equals(subItemMenu) || "arestasMaisPesadas".equals(itemMenu) || "grafoMedio".equals(itemMenu) || "grafoMediano".equals(itemMenu) || "grafoMaximo".equals(itemMenu) || "frequentes".equals(subItemMenu)) {
                File datasetGradeCurricular = new File(dirDeDatasetCompleto + File.separator + "datasetGradeCurricular.txt");
                FileReader fileReaderDis = new FileReader(datasetGradeCurricular);
                Scanner scannerDis = new Scanner(fileReaderDis);
                ArrayList<String> idsCurriculos = new ArrayList<String>();

                while (scannerDis.hasNextLine()) {
                    String linha = scannerDis.nextLine();
                    String[] campos = linha.split("|");

                    String idCurriculo = linha.substring(linha.indexOf('|') + 1);

                    if (campos[0].equals("g")) {
                        idsCurriculos.add(idCurriculo);
                    }
                }

                Collections.sort(idsCurriculos);
                request.setAttribute("idsCurriculos", idsCurriculos);
                request.setAttribute("idsCurriculosSize", idsCurriculos.size());   
                
                if ("caminhosMaisLongos".equals(itemMenu)) {
                    CriarDatasetAction criarDatasetAction = new CriarDatasetAction();
                    criarDatasetAction.criarDatasetCaminhosMaisLongos(request.getServletContext().getRealPath(""), "gradeCurricular");
                }

                if ("arestasMaisPesadas".equals(itemMenu)) {
                    CriarDatasetAction criarDatasetAction = new CriarDatasetAction();
                    criarDatasetAction.criarDatasetArestasMaisPesadas(request.getServletContext().getRealPath(""), subItemMenu, request.getParameter("soma"));
                }
                
                if ("grafoMedio".equals(itemMenu)) {
                    CriarDatasetAction criarDatasetAction = new CriarDatasetAction();
                    criarDatasetAction.criarDatasetGrafoMedio(request.getServletContext().getRealPath(""));
                }
                
                if ("grafoMediano".equals(itemMenu)) {
                    CriarDatasetAction criarDatasetAction = new CriarDatasetAction();
                    criarDatasetAction.criarDatasetGrafoMediano(request.getServletContext().getRealPath(""));
                }
                
                if ("grafoMaximo".equals(itemMenu)) {
                    CriarDatasetAction criarDatasetAction = new CriarDatasetAction();
                    criarDatasetAction.criarDatasetGrafoMaximo(request.getServletContext().getRealPath(""));
                }
            } else if ("historicos".equals(itemMenu) || "historicos".equals(subItemMenu)) {
                File datasetHistoricos = new File(dirDeDatasetCompleto + File.separator + "datasetHistoricos.txt");
                FileReader fileReaderHis = new FileReader(datasetHistoricos);
                Scanner scannerHis = new Scanner(fileReaderHis);
                ArrayList<String> idsAlunos = new ArrayList<String>();

                while (scannerHis.hasNextLine()) {
                    String linha = scannerHis.nextLine();
                    String[] campos = linha.split("|");

                    String idAluno = linha.substring(linha.indexOf('|') + 1);

                    if (campos[0].equals("g")) {
                        idsAlunos.add(idAluno);
                    }
                }

                Collections.sort(idsAlunos);
                request.setAttribute("idsAlunos", idsAlunos);
                request.setAttribute("idsAlunosSize", idsAlunos.size());                                
                
                if ("historicos".equals(subItemMenu)) {
                    CriarDatasetAction criarDatasetAction = new CriarDatasetAction();
                    criarDatasetAction.criarDatasetCaminhosMaisLongos(request.getServletContext().getRealPath(""), "historicos");
                }                
            } else if ("grafoMedio".equals(subItemMenu)) {
                File datasetGrafoMedio = new File(dirDeDatasetCompleto + File.separator + "datasetGrafoMedio.txt");
                FileReader fileReader = new FileReader(datasetGrafoMedio);
                Scanner scanner = new Scanner(fileReader);
                ArrayList<String> idsCurriculos = new ArrayList<String>();

                while (scanner.hasNextLine()) {
                    String linha = scanner.nextLine();
                    String[] campos = linha.split("|");

                    String idAluno = linha.substring(linha.indexOf('|') + 1);

                    if (campos[0].equals("g")) {
                        idsCurriculos.add(idAluno);
                    }
                }

                Collections.sort(idsCurriculos);
                request.setAttribute("idsCurriculos", idsCurriculos);
                request.setAttribute("idsCurriculosSize", idsCurriculos.size());                                
                
                if ("caminhosMaisLongos".equals(itemMenu)) {
                    CriarDatasetAction criarDatasetAction = new CriarDatasetAction();
                    criarDatasetAction.criarDatasetCaminhosMaisLongos(request.getServletContext().getRealPath(""), "grafoMedio");
                }                
            } else if ("grafoMediano".equals(subItemMenu)) {
                File datasetGrafoMediano = new File(dirDeDatasetCompleto + File.separator + "datasetGrafoMediano.txt");
                FileReader fileReader = new FileReader(datasetGrafoMediano);
                Scanner scanner = new Scanner(fileReader);
                ArrayList<String> idsCurriculos = new ArrayList<String>();

                while (scanner.hasNextLine()) {
                    String linha = scanner.nextLine();
                    String[] campos = linha.split("|");

                    String idAluno = linha.substring(linha.indexOf('|') + 1);

                    if (campos[0].equals("g")) {
                        idsCurriculos.add(idAluno);
                    }
                }

                Collections.sort(idsCurriculos);
                request.setAttribute("idsCurriculos", idsCurriculos);
                request.setAttribute("idsCurriculosSize", idsCurriculos.size());                                
                
                if ("caminhosMaisLongos".equals(itemMenu)) {
                    CriarDatasetAction criarDatasetAction = new CriarDatasetAction();
                    criarDatasetAction.criarDatasetCaminhosMaisLongos(request.getServletContext().getRealPath(""), "grafoMediano");
                }                
            } else if ("grafoMaximo".equals(subItemMenu)) {
                File datasetGrafoMaximo = new File(dirDeDatasetCompleto + File.separator + "datasetGrafoMaximo.txt");
                FileReader fileReader = new FileReader(datasetGrafoMaximo);
                Scanner scanner = new Scanner(fileReader);
                ArrayList<String> idsCurriculos = new ArrayList<String>();

                while (scanner.hasNextLine()) {
                    String linha = scanner.nextLine();
                    String[] campos = linha.split("|");

                    String idAluno = linha.substring(linha.indexOf('|') + 1);

                    if (campos[0].equals("g")) {
                        idsCurriculos.add(idAluno);
                    }
                }

                Collections.sort(idsCurriculos);
                request.setAttribute("idsCurriculos", idsCurriculos);
                request.setAttribute("idsCurriculosSize", idsCurriculos.size());                                
                
                if ("caminhosMaisLongos".equals(itemMenu)) {
                    CriarDatasetAction criarDatasetAction = new CriarDatasetAction();
                    criarDatasetAction.criarDatasetCaminhosMaisLongos(request.getServletContext().getRealPath(""), "grafoMaximo");
                }                
            }  
            
            if ("arestasMaisPesadas".equals(itemMenu)) {
                request.setAttribute("tipoDeDataset", subItemMenu);
                request.setAttribute("tipoDeSoma", request.getParameter("soma"));
                if ("cml".equals(subItemMenu)) {                    
                    getServletContext().getRequestDispatcher("/" + menu + "/" + itemMenu + "/" + "usandoCML.jsp").forward(request, response);
                } else {                
                    getServletContext().getRequestDispatcher("/" + menu + "/" + itemMenu + "/" + "usandoHistoricos.jsp").forward(request, response);
                }                
            } else if ("caminhosMaisLongos".equals(itemMenu)) {
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
