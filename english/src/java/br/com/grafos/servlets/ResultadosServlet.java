package br.com.grafos.servlets;

import br.com.grafos.algoritmos.Backtracking;
import br.com.grafos.util.SortMap;
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

@WebServlet(name = "ResultadosServlet", urlPatterns = {"/resultados"})
public class ResultadosServlet extends HttpServlet {

    private final String DIR_DATASETS = "datasets";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String diretorioAplicacao = request.getServletContext().getRealPath("");
        String dirDeDatasetCompleto = diretorioAplicacao + File.separator + DIR_DATASETS;

        String idGrafo = "";
        String tipoDeResultado = "";
        String tipoDataset = null;

        idGrafo = request.getParameter("idGrafo");
        tipoDeResultado = request.getParameter("tipo");
        tipoDataset = request.getParameter("dataset");

        File dataset = null;
        FileReader fileReader = null;
        Scanner scanner = null;

        if ("rankingArestasMaisPesadas".equals(tipoDeResultado)) {
            dataset = new File(dirDeDatasetCompleto + File.separator + "datasetArestasMaisPesadas.txt");

            fileReader = new FileReader(dataset);
            scanner = new Scanner(fileReader);

            Map<String, ArrayList<String>> idGrafo_Vertices = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGrafo_Arestas = new HashMap<String, ArrayList<String>>();

            String idGrafoNoDataset = null;
            String edge = null;

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();

                if (linha.substring(0, 1).toUpperCase().equals("G")) {
                    idGrafoNoDataset = linha.substring(linha.indexOf('|') + 1);

                    if (!idGrafo_Vertices.containsKey(idGrafoNoDataset)) {
                        idGrafo_Vertices.put(idGrafoNoDataset, new ArrayList<String>());
                    }

                    if (!idGrafo_Arestas.containsKey(idGrafoNoDataset)) {
                        idGrafo_Arestas.put(idGrafoNoDataset, new ArrayList<String>());
                    }
                } else if (linha.substring(0, 1).toUpperCase().equals("E")) {

                    String[] campos = linha.split(Pattern.quote("|"));

                    String verticeOrigem = campos[2];
                    String verticeDestino = campos[4];
                    String pesoAresta = campos[5];

                    edge = verticeOrigem + "," + verticeDestino + "," + pesoAresta;
                    idGrafo_Arestas.get(idGrafoNoDataset).add(edge);
                }
            }

            Map<String, Integer> mapDesordenado = new HashMap<String, Integer>();

            Iterator i = idGrafo_Arestas.get(idGrafo).iterator();
            while (i.hasNext()) {
                String aresta = (String) i.next();

                String[] campos = aresta.split(",");

                String verticeOrigem = campos[0];
                String verticeDestino = campos[1];
                int peso = Integer.parseInt(campos[2]);

                mapDesordenado.put(verticeOrigem + " - " + verticeDestino, peso);
            }

            Map<String, Integer> mapOrdenado = SortMap.sortByComparator(mapDesordenado);

            request.setAttribute("mapOrdenado", mapOrdenado);
            request.setAttribute("idGrafo", idGrafo);
            request.setAttribute("tipoDeSoma", request.getParameter("soma"));
            request.setAttribute("tipoDeDataset", tipoDataset);
            getServletContext().getRequestDispatcher("/resultados/arestasMaisPesadas/ranking.jsp").forward(request, response);
        } else if ("rankingCML".equals(tipoDeResultado)) {
            FileReader fileReaderGradeCurricular = new FileReader(diretorioAplicacao + File.separator + DIR_DATASETS + File.separator + "datasetGradeCurricular.txt");
            Scanner scannerGradeCurricular = new Scanner(fileReaderGradeCurricular);

            Map<String, HashMap<Integer, String>> idGrafo_idVertice_RotuloVertice = new HashMap<String, HashMap<Integer, String>>();
            String idGrafoDataset = null;
            while (scannerGradeCurricular.hasNextLine()) {
                String linha = scannerGradeCurricular.nextLine();

                if (linha.substring(0, 1).toUpperCase().equals("G")) {
                    idGrafoDataset = linha.substring(linha.indexOf('|') + 1);

                    if (!idGrafo_idVertice_RotuloVertice.containsKey(idGrafoDataset)) {
                        idGrafo_idVertice_RotuloVertice.put(idGrafoDataset, new HashMap<Integer, String>());
                    }
                } else if (linha.substring(0, 1).toUpperCase().equals("V")) {                    
                    String[] campos = linha.split(Pattern.quote("|"));
                    int idVertice = Integer.parseInt(campos[1]);
                    String rotulo = campos[2];
                    
                    if (!idGrafo_idVertice_RotuloVertice.get(idGrafoDataset).containsKey(idVertice)) {
                        idGrafo_idVertice_RotuloVertice.get(idGrafoDataset).put(idVertice, rotulo);
                    }
                }
            }

            fileReader = new FileReader(diretorioAplicacao + File.separator + DIR_DATASETS + File.separator + "datasetCaminhosHistoricos.txt");
            scanner = new Scanner(fileReader);

            Map<String, ArrayList<String>> idGrafo_CaminhosMaisLongos = new HashMap<String, ArrayList<String>>();

            String idCurriculo = null;
            String cmlAluno = null;
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();

                if (linha.substring(0, 1).toUpperCase().equals("C")) {
                    String[] campos = linha.split(Pattern.quote("|"));

                    idCurriculo = campos[2];
                    cmlAluno = campos[3];

                    if (!idGrafo_CaminhosMaisLongos.containsKey(idCurriculo)) {
                        idGrafo_CaminhosMaisLongos.put(idCurriculo, new ArrayList<String>());
                    }

                    idGrafo_CaminhosMaisLongos.get(idCurriculo).add(cmlAluno);
                }
            }

            Map<String, ArrayList<String>> idGrafo_TodosCaminhos = new HashMap<String, ArrayList<String>>();
            Backtracking bck = new Backtracking();
            idGrafo_TodosCaminhos.put(idGrafo, bck.todosOsCaminhosComBCK(diretorioAplicacao, idGrafo, "id"));

            Map<String, Integer> caminhos_qtds = new HashMap<String, Integer>();

            Iterator it = idGrafo_TodosCaminhos.get(idGrafo).iterator();
            while (it.hasNext()) {
                String caminhoCurriculo = (String) it.next();

                caminhos_qtds.put(caminhoCurriculo, Collections.frequency(idGrafo_CaminhosMaisLongos.get(idGrafo), caminhoCurriculo));
            }
                                             
            Map<String, Integer> caminhosComRotulosDosVertices_qtds = new HashMap<String, Integer>();
            Set<String> caminhos = caminhos_qtds.keySet();
            int aux = 0;
            for (String caminho : caminhos) {
                String[] ids = caminho.split("-");
                String caminhoComRotulo = "";
                for (int i = 0; i < ids.length; i++) {
                    caminhoComRotulo += idGrafo_idVertice_RotuloVertice.get(idGrafo).get(Integer.parseInt(ids[i]));
                    if (!ids[i].equals("1")) {
                        caminhoComRotulo += " - ";
                    }
                }
                
                caminhosComRotulosDosVertices_qtds.put(caminhoComRotulo, caminhos_qtds.get(caminho));
                
                aux++;
            }
            
            Map<String, Integer> mapOrdenado = SortMap.sortByComparator(caminhosComRotulosDosVertices_qtds);                         
           
            request.setAttribute("idGrafo", idGrafo);
            request.setAttribute("rankingCaminhos", mapOrdenado);
            getServletContext().getRequestDispatcher("/resultados/caminhosMaisLongos/rankingCaminhos.jsp").forward(request, response);
        } else if ("todosCaminhos".equals(tipoDeResultado)) {
            Map<String, ArrayList<String>> idGrafo_TodosCaminhos = new HashMap<String, ArrayList<String>>();

            Backtracking bck = new Backtracking();

            idGrafo_TodosCaminhos.put(idGrafo, bck.todosOsCaminhosComBCK(diretorioAplicacao, idGrafo, "rotulo"));

            request.setAttribute("idGrafo", idGrafo);
            request.setAttribute("caminhos", idGrafo_TodosCaminhos.get(idGrafo));
            getServletContext().getRequestDispatcher("/resultados/caminhosMaisLongos/todosCaminhos.jsp").forward(request, response);
        }
    }
}
