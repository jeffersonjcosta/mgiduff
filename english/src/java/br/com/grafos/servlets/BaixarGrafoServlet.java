package br.com.grafos.servlets;

import br.com.grafos.util.FileUtil;
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

@WebServlet(name = "BaixarGrafoServlet", urlPatterns = {"/baixarGrafo"})
public class BaixarGrafoServlet extends HttpServlet {

    private final String DIR_DATASETS = "datasets";   
    private final String DIR_TEMP = "temp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String diretorioAplicacao = request.getServletContext().getRealPath("");
        String dirDeDatasetCompleto = diretorioAplicacao + File.separator + DIR_DATASETS;

        String idGrafo = "";
        String tipoDeGrafo = "";
        String downloadGexf = "";
        String downloadDot = "";
        String downloadTxt = "";

        idGrafo = request.getParameter("idGrafo");
        tipoDeGrafo = request.getParameter("tipo");

        downloadGexf = request.getParameter("gexf");
        downloadDot = request.getParameter("dot");
        downloadTxt = request.getParameter("txt");

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
            prefixoDoArquivo = "arestasMaisPesadas";
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
            Map<String, ArrayList<String>> idGrafo_LinhasCompletasDosVertices = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGrafo_LinhasCompletasDasArestas = new HashMap<String, ArrayList<String>>();
            Map<String, HashSet<String>> idGrafo_Periodos = new HashMap<String, HashSet<String>>();
            Map<String, HashSet<String>> periodo_Disciplinas = new HashMap<String, HashSet<String>>();

            String idGrafoNoDataset = null;
            String idDisciplina = null;
            String periodo = null;
            String aresta = null;

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();

                if (linha.substring(0, 1).toUpperCase().equals("G")) {
                    idGrafoNoDataset = linha.substring(linha.indexOf('|') + 1);

                    if (!idGrafo_Vertices.containsKey(idGrafoNoDataset)) {
                        idGrafo_Vertices.put(idGrafoNoDataset, new ArrayList<String>());
                        idGrafo_Periodos.put(idGrafoNoDataset, new HashSet<String>());
                        idGrafo_LinhasCompletasDosVertices.put(idGrafoNoDataset, new ArrayList<String>());
                    }

                    if (!idGrafo_Arestas.containsKey(idGrafoNoDataset)) {
                        idGrafo_Arestas.put(idGrafoNoDataset, new ArrayList<String>());
                        idGrafo_LinhasCompletasDasArestas.put(idGrafoNoDataset, new ArrayList<String>());
                    }
                } else if (linha.substring(0, 1).toUpperCase().equals("V")) {
                    idGrafo_LinhasCompletasDosVertices.get(idGrafoNoDataset).add(linha);

                    String[] fields = linha.split(Pattern.quote("|"));

                    idDisciplina = fields[2];
                    periodo = fields[3];

                    idGrafo_Vertices.get(idGrafoNoDataset).add(idDisciplina);
                    idGrafo_Periodos.get(idGrafoNoDataset).add(periodo);

                    if (idGrafo.equals(idGrafoNoDataset)) {
                        if (!periodo_Disciplinas.containsKey(periodo)) {
                            periodo_Disciplinas.put(periodo, new HashSet<String>());
                        }

                        periodo_Disciplinas.get(periodo).add(idDisciplina);
                    }
                } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                    idGrafo_LinhasCompletasDasArestas.get(idGrafoNoDataset).add(linha);

                    String[] fields = linha.split(Pattern.quote("|"));

                    String verticeOrigem = fields[2];
                    String verticeDestino = fields[4];
                    String pesoAresta = fields[5];

                    aresta = verticeOrigem + "|" + verticeDestino + "|" + pesoAresta;
                    idGrafo_Arestas.get(idGrafoNoDataset).add(aresta);
                }
            }

            String arquivoCriadoNome = null;
            if ("true".equals(downloadGexf)) {
                arquivoCriadoNome = this.criarArquivo(idGrafo, prefixoDoArquivo, idGrafo_Vertices, idGrafo_Arestas, idGrafo_Periodos, periodo_Disciplinas, "gexf", tipoDeGrafo);
            } else if ("true".equals(downloadDot)) {
                arquivoCriadoNome = this.criarArquivo(idGrafo, prefixoDoArquivo, idGrafo_Vertices, idGrafo_Arestas, idGrafo_Periodos, periodo_Disciplinas, "dot", tipoDeGrafo);
            } else if ("true".equals(downloadTxt)) {
                arquivoCriadoNome = this.criarArquivo(idGrafo, prefixoDoArquivo, idGrafo_LinhasCompletasDosVertices, idGrafo_LinhasCompletasDasArestas, idGrafo_Periodos, periodo_Disciplinas, "txt", tipoDeGrafo);
            }

            File arquivoCriado = new File(getServletContext().getRealPath("") + File.separator + DIR_TEMP + File.separator + arquivoCriadoNome);         
            FileUtil.download(arquivoCriadoNome, FileUtil.read(arquivoCriado), response);
                       
        }
        else {//Se o tipo de grafo for um caminho, não terei todos os atributos dos vértices e das arestas, por isso devo gerar um GEXF de forma diferente para op download                                  
            Map<String, ArrayList<String>> idGrafo_Vertices = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGrafo_Arestas = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGrafo_LinhasCompletasDosVertices = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGrafo_LinhasCompletasDasArestas = new HashMap<String, ArrayList<String>>();            

            String idGrafoNoDataset = null;
            String idDisciplina = null;            
            String aresta = null;
            
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();              

                if (linha.substring(0, 1).toUpperCase().equals("C")) {                    
                    String[] campos = linha.split(Pattern.quote("|"));                    
                    
                    idGrafoNoDataset = campos[1];   
                    
                    if ("caminhoMaisLongoHistoricos".equals(tipoDeGrafo)) {
                        idGrafoNoDataset = campos[1] + "|" + campos[2];
                    }
                    
                    if (!idGrafo_Vertices.containsKey(idGrafoNoDataset)) {
                        idGrafo_Vertices.put(idGrafoNoDataset, new ArrayList<String>());                        
                        idGrafo_LinhasCompletasDosVertices.put(idGrafoNoDataset, new ArrayList<String>());
                    }

                    if (!idGrafo_Arestas.containsKey(idGrafoNoDataset)) {
                        idGrafo_Arestas.put(idGrafoNoDataset, new ArrayList<String>());
                        idGrafo_LinhasCompletasDasArestas.put(idGrafoNoDataset, new ArrayList<String>());
                    }                                        
                } else if (linha.substring(0, 1).toUpperCase().equals("V")) {
                    idGrafo_LinhasCompletasDosVertices.get(idGrafoNoDataset).add(linha);
                    String[] fields = linha.split(Pattern.quote("|"));
                    idDisciplina = fields[2];                
                    idGrafo_Vertices.get(idGrafoNoDataset).add(idDisciplina);                                       
                } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                    idGrafo_LinhasCompletasDasArestas.get(idGrafoNoDataset).add(linha);

                    String[] fields = linha.split(Pattern.quote("|"));

                    String verticeOrigem = fields[2];
                    String verticeDestino = fields[4]; 
                    String pesoAresta = fields[5];

                    aresta = verticeOrigem + "|" + verticeDestino + "|" + pesoAresta;
                    idGrafo_Arestas.get(idGrafoNoDataset).add(aresta);                   
                }
            }
            
            String arquivoCriadoNome = null;          
            if ("true".equals(downloadGexf)) {
                arquivoCriadoNome = this.criarArquivo(idGrafo, prefixoDoArquivo, idGrafo_Vertices, idGrafo_Arestas, null, null, "gexf", tipoDeGrafo);
            } else if ("true".equals(downloadDot)) {
                arquivoCriadoNome = this.criarArquivo(idGrafo, prefixoDoArquivo, idGrafo_Vertices, idGrafo_Arestas, null, null, "dot", tipoDeGrafo);
            } else if ("true".equals(downloadTxt)) {
                arquivoCriadoNome = this.criarArquivo(idGrafo, prefixoDoArquivo, idGrafo_LinhasCompletasDosVertices, idGrafo_LinhasCompletasDasArestas, null, null, "txt", tipoDeGrafo);
            }

            File arquivoCriado = new File(getServletContext().getRealPath("") + File.separator + DIR_TEMP + File.separator + arquivoCriadoNome);
            FileUtil.download(arquivoCriadoNome, FileUtil.read(arquivoCriado), response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private String criarArquivo(String idGrafo, String prefixoDoArquivo, Map<String, ArrayList<String>> idGrafo_Vertices, Map<String, ArrayList<String>> idGrafo_Arestas, Map<String, HashSet<String>> idGrafo_Periodos, Map<String, HashSet<String>> periodo_Disciplinas, String tipoDeArquivo, String tipoDeGrafo) throws IOException {

        String diretorioAplicacao = getServletContext().getRealPath("");
        String dirTempCompleto = diretorioAplicacao + File.separator + DIR_TEMP;

        File fileDir = new File(dirTempCompleto);
        if (!fileDir.exists()) {
            fileDir.mkdir();
            fileDir.setWritable(true);
            fileDir.setReadable(true);
        }

        String idGrafoSemPipe = idGrafo.replace("|", "");
        String idGrafoSemPontos = idGrafoSemPipe.replace(".", "");

        String arquivo = prefixoDoArquivo + idGrafoSemPontos + "." + tipoDeArquivo;
        if ("dot".equals(tipoDeArquivo)) {
            arquivo = prefixoDoArquivo + idGrafoSemPontos + "dot.gv.txt";
        }         

        String caminhoCompletoComNomeDoArquivo = dirTempCompleto + File.separator + arquivo;

        FileWriter arquivoParaEscrita = new FileWriter(caminhoCompletoComNomeDoArquivo);
        PrintWriter gexfPrint = new PrintWriter(arquivoParaEscrita, true);
        
        if ("gexf".equals(tipoDeArquivo)) {
            if ("gradeCurricular".equals(tipoDeGrafo) || "historicos".equals(tipoDeGrafo) || "arestasMaisPesadas".equals(tipoDeGrafo) || "grafoMedio".equals(tipoDeGrafo) || "grafoMediano".equals(tipoDeGrafo) || "grafoMaximo".equals(tipoDeGrafo)) {                
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
                            int xDoUltimoVertice = (idGrafo_Vertices.get(idGrafo).size() * 2) + 35;
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
            } else {
                gexfPrint.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                gexfPrint.println("<gexf xmlns=\"http://www.gexf.net/1.1draft\" version=\"1.1\" xmlns:viz=\"http://www.gexf.net/1.1draft/viz\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.1draft http://www.gexf.net/1.1draft/gexf.xsd\">");
                gexfPrint.println("<meta>");
                gexfPrint.println("<description>Caminho Mais Longo Currículo: " + idGrafo + "</description>");
                gexfPrint.println("</meta>");
                gexfPrint.println("<graph defaultedgetype=\"directed\" timeformat=\"double\" mode=\"dynamic\">");

                gexfPrint.println("<nodes>");

                Iterator iter1 = idGrafo_Vertices.get(idGrafo).iterator();
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

                for (int w = 0; w < idGrafo_Arestas.get(idGrafo).size(); w++) {
                    String aresta = idGrafo_Arestas.get(idGrafo).get(w);

                    String[] campos = aresta.split(Pattern.quote("|"));

                    gexfPrint.println("<edge id=\"" + w + "\" source=\"" + campos[0] + "\" target=\"" + campos[1] + "\" label=\"" + campos[2] + "\">");
                    gexfPrint.println("<attvalues></attvalues>");
                    gexfPrint.println("</edge>");
                }

                gexfPrint.println("</edges>");

                gexfPrint.println("</graph>");
                gexfPrint.println("</gexf>");
            }
        } else if ("dot".equals(tipoDeArquivo)) {
            PrintWriter graphVizPrint = new PrintWriter(arquivoParaEscrita, true);
                        
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
        } else if ("txt".equals(tipoDeArquivo)) {
            PrintWriter txtPrint = new PrintWriter(arquivoParaEscrita, true);
            
            if (tipoDeGrafo.contains("caminho")) {
                txtPrint.println("c|" + idGrafo);
            } else {
                txtPrint.println("g|" + idGrafo);
            }

            Iterator i = idGrafo_Vertices.get(idGrafo).iterator();
            while (i.hasNext()) {
                String linhaVertice = (String) i.next();

                txtPrint.println(linhaVertice);
            }

            Iterator i2 = idGrafo_Arestas.get(idGrafo).iterator();
            while (i2.hasNext()) {
                String linhaAresta = (String) i2.next();

                txtPrint.println(linhaAresta);
            }
        }

        return arquivo;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
