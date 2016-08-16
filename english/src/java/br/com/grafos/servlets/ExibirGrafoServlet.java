package br.com.grafos.servlets;

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

@WebServlet(name = "ExibirGrafoServlet", urlPatterns = {"/exibirGrafo"})
public class ExibirGrafoServlet extends HttpServlet {

    private final String DIR_DATASETS = "datasets";
    private final String DIR_GEXF_VISUALIZACAO = "gexfView";
    private final String DIR_GRAPHVIZ_VISUALIZACAO = "graphVizView";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String diretorioAplicacao = request.getServletContext().getRealPath("");
        String dirDeDatasetCompleto = diretorioAplicacao + File.separator + DIR_DATASETS;

        String gexf = null;
        String dot = null;
        
        String idGrafo = "";
        String tipoDeGrafo = "";
        String gephi = null;
        String graphViz = null;

        idGrafo = request.getParameter("idGrafo");
        tipoDeGrafo = request.getParameter("tipo");
        gephi = request.getParameter("gephi");
        graphViz = request.getParameter("graphViz");
                 
        File dataset = null;
        FileReader fileReader = null;
        Scanner scanner = null;
        String prefixoDoArquivo = null;
         
        if ("gradeCurricular".equals(tipoDeGrafo)) {
            dataset = new File(dirDeDatasetCompleto + File.separator + "datasetGradeCurricular.txt");
            prefixoDoArquivo = "grafoGradeCurricular";
        } else if ("historicos".equals(tipoDeGrafo)) {
            dataset = new File(dirDeDatasetCompleto + File.separator + "datasetHistoricos.txt");
            prefixoDoArquivo = "grafoHistorico";
        } else if ("caminhoMaisLongoGradeCurricular".equals(tipoDeGrafo)) {
            dataset = new File(dirDeDatasetCompleto + File.separator + "datasetCaminhosGradeCurricular.txt");
            prefixoDoArquivo = "cmlGradeCurricular";
        } else if ("caminhoMaisLongoHistoricos".equals(tipoDeGrafo)) {
            dataset = new File(dirDeDatasetCompleto + File.separator + "datasetCaminhosHistoricos.txt");
            prefixoDoArquivo = "cmlHistorico";
        } else if ("arestasMaisPesadas".equals(tipoDeGrafo)) {
            dataset = new File(dirDeDatasetCompleto + File.separator + "datasetArestasMaisPesadas.txt");
        } else if ("grafoMedio".equals(tipoDeGrafo)) {
            dataset = new File(dirDeDatasetCompleto + File.separator + "datasetGrafoMedio.txt");
            prefixoDoArquivo = "grafoMedio";
        } else if ("caminhoMaisLongoGrafoMedio".equals(tipoDeGrafo)) {
            dataset = new File(dirDeDatasetCompleto + File.separator + "datasetCaminhosGrafoMedio.txt");
            prefixoDoArquivo = "cmlGrafoMedio";
        } else if ("grafoMediano".equals(tipoDeGrafo)) {
            dataset = new File(dirDeDatasetCompleto + File.separator + "datasetGrafoMediano.txt");
            prefixoDoArquivo = "grafoMediano";
        } else if ("caminhoMaisLongoGrafoMediano".equals(tipoDeGrafo)) {
            dataset = new File(dirDeDatasetCompleto + File.separator + "datasetCaminhosGrafoMediano.txt");
            prefixoDoArquivo = "cmlGrafoMediano";
        } else if ("grafoMaximo".equals(tipoDeGrafo)) {
            dataset = new File(dirDeDatasetCompleto + File.separator + "datasetGrafoMaximo.txt");
            prefixoDoArquivo = "grafoMaximo";
        } else if ("caminhoMaisLongoGrafoMaximo".equals(tipoDeGrafo)) {
            dataset = new File(dirDeDatasetCompleto + File.separator + "datasetCaminhosGrafoMaximo.txt");
            prefixoDoArquivo = "cmlGrafoMaximo";
        } 
        
        fileReader = new FileReader(dataset);
        scanner = new Scanner(fileReader);
                
        if (!tipoDeGrafo.contains("caminho")) {                   
            Map<String, ArrayList<String>> idGrafo_Vertices = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGrafo_Arestas = new HashMap<String, ArrayList<String>>();
            Map<String, HashSet<String>> idGrafo_Periodos = new HashMap<String, HashSet<String>>();
            Map<String, HashSet<String>> periodo_Disciplinas = new HashMap<String, HashSet<String>>();

            String idGrafoNoDataset = null;
            String idDisciplina = null;
            String periodo = null;
            String edge = null;

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();

                if (linha.substring(0, 1).toUpperCase().equals("G")) {
                    idGrafoNoDataset = linha.substring(linha.indexOf('|') + 1);

                    if (!idGrafo_Vertices.containsKey(idGrafoNoDataset)) {
                        idGrafo_Vertices.put(idGrafoNoDataset, new ArrayList<String>());
                        idGrafo_Periodos.put(idGrafoNoDataset, new HashSet<String>());
                    }

                    if (!idGrafo_Arestas.containsKey(idGrafoNoDataset)) {
                        idGrafo_Arestas.put(idGrafoNoDataset, new ArrayList<String>());
                    }
                } else if (linha.substring(0, 1).toUpperCase().equals("V")) {
                    String[] campos = linha.split(Pattern.quote("|"));

                    idDisciplina = campos[2];
                    periodo = campos[3];

                    idGrafo_Vertices.get(idGrafoNoDataset).add(idDisciplina);
                    idGrafo_Periodos.get(idGrafoNoDataset).add(periodo);

                    if (idGrafo.equals(idGrafoNoDataset)) {
                        if (!periodo_Disciplinas.containsKey(periodo)) {
                            periodo_Disciplinas.put(periodo, new HashSet<String>());
                        }

                        periodo_Disciplinas.get(periodo).add(idDisciplina);
                    }
                } else if (linha.substring(0, 1).toUpperCase().equals("E")) {

                    String[] campos = linha.split(Pattern.quote("|"));

                    String verticeOrigem = campos[2];
                    String verticeDestino = campos[4];
                    String pesoAresta = campos[5];

                    edge = verticeOrigem + "|" + verticeDestino + "|" + pesoAresta;
                    idGrafo_Arestas.get(idGrafoNoDataset).add(edge);
                }
            }

            if ("true".equals(gephi)) {
                if (idGrafo_Vertices.containsKey(idGrafo)) {
                    gexf = this.criarGexf(idGrafo, prefixoDoArquivo, idGrafo_Vertices, idGrafo_Arestas, idGrafo_Periodos, periodo_Disciplinas);
                    response.sendRedirect(getServletContext().getContextPath() + File.separator + DIR_GEXF_VISUALIZACAO + "/index.jsp#files/" + gexf);
                } else {                    
                    request.setAttribute("idGrafo", idGrafo);                                        
                    getServletContext().getRequestDispatcher("/erro.jsp").forward(request, response);
                }
            } else if ("true".equals(graphViz)) {
                dot = this.criarDotGraphViz(idGrafo, prefixoDoArquivo, idGrafo_Vertices, idGrafo_Arestas);
                response.sendRedirect(getServletContext().getContextPath() + File.separator + DIR_GRAPHVIZ_VISUALIZACAO + "/index.jsp?dotGraphViz=" + dot);
            }       
        } else {//Se o tipo de grafo for um caminho, não terei todos os atributos dos vértices e das arestas, por isso devo gerar um GEXF de forma diferente para esse tipo de grafo                       
            Map<String, ArrayList<String>> idGrafo_VerticesDoCaminhoMaisLongo = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGrafo_ArestasDoCaminhoMaisLongo = new HashMap<String, ArrayList<String>>();

            String idGrafoCaminho = null;
            String rotuloVertice = null;
            String aresta = null;
                     
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();

                if (linha.substring(0, 1).toUpperCase().equals("C")) {
                    String[] campos = linha.split(Pattern.quote("|"));
                    
                    idGrafoCaminho = campos[1];                     
                                        
                    if ("caminhoMaisLongoHistoricos".equals(tipoDeGrafo)) {
                        idGrafoCaminho = campos[1] + "|" + campos[2];
                    }                                        

                    if (!idGrafo_VerticesDoCaminhoMaisLongo.containsKey(idGrafoCaminho)) {
                        idGrafo_VerticesDoCaminhoMaisLongo.put(idGrafoCaminho, new ArrayList<String>());
                    }

                    if (!idGrafo_ArestasDoCaminhoMaisLongo.containsKey(idGrafoCaminho)) {
                        idGrafo_ArestasDoCaminhoMaisLongo.put(idGrafoCaminho, new ArrayList<String>());
                    }                                        
                } else if (linha.substring(0, 1).toUpperCase().equals("V")) {
                    String[] campos = linha.split(Pattern.quote("|"));
                    rotuloVertice = campos[2];
                    idGrafo_VerticesDoCaminhoMaisLongo.get(idGrafoCaminho).add(rotuloVertice);
                } else if (linha.substring(0, 1).toUpperCase().equals("E")) {

                    String[] campos = linha.split(Pattern.quote("|"));

                    String verticeDeOrigem = campos[2];
                    String verticeDeDestino = campos[4];
                    String peso = campos[5];

                    aresta = verticeDeOrigem + "|" + verticeDeDestino + "|" + peso;
                    idGrafo_ArestasDoCaminhoMaisLongo.get(idGrafoCaminho).add(aresta);
                }
            }
                                    
            if ("true".equals(gephi)) {
                gexf = this.criarGexfParaCaminhos(idGrafo, prefixoDoArquivo, idGrafo_VerticesDoCaminhoMaisLongo, idGrafo_ArestasDoCaminhoMaisLongo);
                response.sendRedirect(getServletContext().getContextPath() + File.separator + DIR_GEXF_VISUALIZACAO + "/index.jsp#files/" + gexf);
            } else if ("true".equals(graphViz)) {
                dot = this.criarDotGraphViz(idGrafo, prefixoDoArquivo, idGrafo_VerticesDoCaminhoMaisLongo, idGrafo_ArestasDoCaminhoMaisLongo);
                response.sendRedirect(getServletContext().getContextPath() + File.separator + DIR_GRAPHVIZ_VISUALIZACAO + "/index.jsp?dotGraphViz=" + dot);
            }              
        }                
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private String criarGexf(String idGrafo, String prefixoDoArquivo, Map<String, ArrayList<String>> idGrafo_Vertices, Map<String, ArrayList<String>> idGrafo_Arestas, Map<String, HashSet<String>> idGrafo_Periodos, Map<String, HashSet<String>> periodo_Disciplinas) throws IOException {
        
        String diretorioAplicacao = getServletContext().getRealPath("");
        String dirArquivosGexfCompleto = diretorioAplicacao + File.separator + DIR_GEXF_VISUALIZACAO + File.separator + "files";

        File fileDir = new File(dirArquivosGexfCompleto);
        if (!fileDir.exists()) {
            fileDir.mkdir();
            fileDir.setWritable(true);
            fileDir.setReadable(true);
        }
        
        String idGrafoSemPipe = idGrafo.replace("|", "");
        String idGrafoSemPontos = idGrafoSemPipe.replace(".", "");               

        String gexfNome = prefixoDoArquivo + idGrafoSemPontos + ".gexf";

        String caminhoCompletoComNomeDoArquivo = dirArquivosGexfCompleto + File.separator + gexfNome;

        FileWriter arquivoDeVisualizacao = new FileWriter(caminhoCompletoComNomeDoArquivo);
        PrintWriter gexfPrint = new PrintWriter(arquivoDeVisualizacao, true);

        gexfPrint.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        gexfPrint.println("<gexf xmlns=\"http://www.gexf.net/1.1draft\" version=\"1.1\" xmlns:viz=\"http://www.gexf.net/1.1draft/viz\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.1draft http://www.gexf.net/1.1draft/gexf.xsd\">");
        gexfPrint.println("<meta>");
        gexfPrint.println("<description>Currículo: " + idGrafo + "</description>");
        gexfPrint.println("</meta>");
        gexfPrint.println("<graph defaultedgetype=\"directed\" timeformat=\"double\" mode=\"dynamic\">");

        String sy;
        int y;
        int aux = 9;
        int r;
        int g;
        int b;
        Random gerador = new Random();

        gexfPrint.println("<nodes>");

        Iterator iter1 = idGrafo_Periodos.get(idGrafo).iterator();
        while (iter1.hasNext()) {
            String periodo = (String) iter1.next();

            Iterator iter2 = periodo_Disciplinas.get(periodo).iterator();

            y = -30;
            r = gerador.nextInt(255);
            g = gerador.nextInt(230);
            b = gerador.nextInt(200);

            while (iter2.hasNext()) {
                String idDisc = (String) iter2.next();

                y += aux;

                gexfPrint.println("<node id=\"" + idDisc + "\" label=\"" + idDisc + "\">");
                gexfPrint.println("<attvalues></attvalues>");
                gexfPrint.println("<viz:size value=\"2\"></viz:size>");

                String sx = periodo + "9";
                sy = Integer.toString(y);

                if (idDisc.equals("Ingresso")) {
                    sx = "-10";
                    sy = "-5";
                    r = 0;
                    g = 0;
                    b = 0;
                }
                if (idDisc.equals("Conclusao")) {
                    int xDoUltimoVertice = (idGrafo_Vertices.get(idGrafo).size() * 2) + 30;
                    sx = Integer.toString(xDoUltimoVertice);
                    sy = "-5";
                    r = 0;
                    g = 0;
                    b = 0;
                }

                gexfPrint.println("<viz:position x=\"" + sx + "\" y=\"" + sy + "\"></viz:position>");//Posição na tela
                gexfPrint.println("<viz:color r=\"" + r + "\" g=\"" + g + "\" b=\"" + b + "\"></viz:color>"); //Cor
                gexfPrint.println("</node>");
            }
        }

        gexfPrint.println("</nodes>");

        gexfPrint.println("<edges>");

        for (int x = 0; x < idGrafo_Arestas.get(idGrafo).size(); x++) {
            String aresta = idGrafo_Arestas.get(idGrafo).get(x);

            String[] campos = aresta.split(Pattern.quote("|"));                        
            
            gexfPrint.println("<edge id=\"" + x + "\" source=\"" + campos[0] + "\" target=\"" + campos[1] + "\" label=\"" + campos[2] + "\">");
            gexfPrint.println("<attvalues></attvalues>");
            gexfPrint.println("</edge>");
        }

        gexfPrint.println("</edges>");

        gexfPrint.println("</graph>");
        gexfPrint.println("</gexf>");

        return gexfNome;
    }
    
    private String criarGexfParaCaminhos(String idGrafo, String prefixoDoArquivo, Map<String, ArrayList<String>> idGrafo_VerticesDoCaminhoMaisLongo, Map<String, ArrayList<String>> idGrafo_ArestasDoCaminhoMaisLongo) throws IOException {
        String diretorioAplicacao = getServletContext().getRealPath("");
        String dirArquivosGexfCompleto = diretorioAplicacao + File.separator + DIR_GEXF_VISUALIZACAO + File.separator + "files";

        File fileDir = new File(dirArquivosGexfCompleto);
        if (!fileDir.exists()) {
            fileDir.mkdir();
            fileDir.setWritable(true);
            fileDir.setReadable(true);
        }

        String idGrafoSemPipe = idGrafo.replace("|", "");
        String idGrafoSemPontos = idGrafoSemPipe.replace(".", "");

        String gexfNome = prefixoDoArquivo + idGrafoSemPontos + ".gexf";

        String caminhoCompletoComNomeDoArquivo = dirArquivosGexfCompleto + File.separator + gexfNome;

        FileWriter arquivoDeVisualizacao = new FileWriter(caminhoCompletoComNomeDoArquivo);
        PrintWriter gexfPrint = new PrintWriter(arquivoDeVisualizacao, true);
    
        gexfPrint.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        gexfPrint.println("<gexf xmlns=\"http://www.gexf.net/1.1draft\" version=\"1.1\" xmlns:viz=\"http://www.gexf.net/1.1draft/viz\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.1draft http://www.gexf.net/1.1draft/gexf.xsd\">");
        gexfPrint.println("<meta>");
        gexfPrint.println("<description>Caminho Mais Longo Currículo: " + idGrafo + "</description>");
        gexfPrint.println("</meta>");
        gexfPrint.println("<graph defaultedgetype=\"directed\" timeformat=\"double\" mode=\"dynamic\">");

        gexfPrint.println("<nodes>");

        Iterator iter1 = idGrafo_VerticesDoCaminhoMaisLongo.get(idGrafo).iterator();
        int x = 0;
        int y = 0;
        while (iter1.hasNext()) {
            String vertice = (String) iter1.next();

            gexfPrint.println("<node id=\"" + vertice + "\" label=\"" + vertice + "\">");
            gexfPrint.println("<attvalues></attvalues>");
            gexfPrint.println("<viz:size value=\"2\"></viz:size>");

            gexfPrint.println("<viz:position x=\"" + (x += 15) + "\" y=\"" + y + "\"></viz:position>");//Posição na tela
            gexfPrint.println("<viz:color r=\"70\" g=\"110\" b=\"160\"></viz:color>"); //Cor
            gexfPrint.println("</node>");
        }

        gexfPrint.println("</nodes>");

        gexfPrint.println("<edges>");

        for (int w = 0; w < idGrafo_ArestasDoCaminhoMaisLongo.get(idGrafo).size(); w++) {
            String aresta = idGrafo_ArestasDoCaminhoMaisLongo.get(idGrafo).get(w);

            String[] campos = aresta.split(Pattern.quote("|"));                      

            gexfPrint.println("<edge id=\"" + w + "\" source=\"" + campos[0] + "\" target=\"" + campos[1] + "\" label=\"" + campos[2] + "\">");
            gexfPrint.println("<attvalues></attvalues>");
            gexfPrint.println("</edge>");
        }

        gexfPrint.println("</edges>");

        gexfPrint.println("</graph>");
        gexfPrint.println("</gexf>");

        return gexfNome;
    }
    
    private String criarDotGraphViz(String idGrafo, String prefixoDoArquivo, Map<String, ArrayList<String>> idGrafo_Vertices, Map<String, ArrayList<String>> idGrafo_Arestas) throws IOException {
        String diretorioAplicacao = getServletContext().getRealPath("");
        String dirArquivosGraphVizCompleto = diretorioAplicacao + File.separator + DIR_GRAPHVIZ_VISUALIZACAO + File.separator + "data";

        File fileDir = new File(dirArquivosGraphVizCompleto);
        if (!fileDir.exists()) {
            fileDir.mkdir();
            fileDir.setWritable(true);
            fileDir.setReadable(true);
        }

        String idGrafoSemPipe = idGrafo.replace("|", "");
        String idGrafoSemPontos = idGrafoSemPipe.replace(".", "");

        String arquivoDOTNome = prefixoDoArquivo + idGrafoSemPontos + "dot.gv.txt";

        String caminhoCompletoComNomeDoArquivo = dirArquivosGraphVizCompleto + File.separator + arquivoDOTNome;

        FileWriter arquivoDeVisualizacao = new FileWriter(caminhoCompletoComNomeDoArquivo);
        PrintWriter graphVizPrint = new PrintWriter(arquivoDeVisualizacao, true);
        
        graphVizPrint.println("digraph " + idGrafoSemPontos);
        graphVizPrint.println("{");
        graphVizPrint.print("node[shape=circle fontSize=11]; ");
        
        Iterator iter1 = idGrafo_Vertices.get(idGrafo).iterator();
        while (iter1.hasNext()) {
            String vertice = (String) iter1.next();
            
            graphVizPrint.print(vertice + " ");
        }
 
        graphVizPrint.println();        
        graphVizPrint.println("edge[fontSize=11]");
        
        Iterator iter2 = idGrafo_Arestas.get(idGrafo).iterator();
        while (iter2.hasNext()) {
            String aresta = (String) iter2.next();
                        
            String[] campos = aresta.split(Pattern.quote("|"));
            
            graphVizPrint.println("\"" + campos[0] + "\"" + " -> \"" + campos[1] + "\"[label=\"" + campos[2] + "\"];");
        }
        
        graphVizPrint.println("}");  
        
        return arquivoDOTNome;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }      
}
