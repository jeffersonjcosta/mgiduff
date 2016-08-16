package br.com.grafos.algoritmos;

import static br.com.grafos.algoritmos.Backtracking.posicao;
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
    public static int marcado[];
    public static int posicao;
    public static int ordem_topologica[];

    public BellmanFord() {
        stack = new Stack<Integer>();
    }

    public static void ordemTopologicaComDFS(ArrayList<Integer>[] listaDeAdjacencia, int v) {

        BellmanFord.marcado[v] = 1;

        for (int i = 0; i < listaDeAdjacencia[v].size(); i++) {
            if (BellmanFord.marcado[listaDeAdjacencia[v].get(i)] == 0) {
                BellmanFord.ordemTopologicaComDFS(listaDeAdjacencia, listaDeAdjacencia[v].get(i));
            }
        }

        BellmanFord.ordem_topologica[posicao] = v;
        BellmanFord.posicao--;
    }   

    public Map<String, ArrayList<Integer>> encontrarCaminhoMaisLongoComBellmanFord(String diretorioAplicacao, String tipoDeDataset) throws FileNotFoundException {
        int numeroDeNos, origem;
        Map<String, ArrayList<Integer>> idGrafo_Caminho = new HashMap<String, ArrayList<Integer>>();

        FileReader fileReader = null;
        Scanner scanner = null;
        try {

            if ("gradeCurricular".equals(tipoDeDataset)) {
                fileReader = new FileReader(diretorioAplicacao + "/datasets/datasetGradeCurricular.txt");
                scanner = new Scanner(fileReader);
            } else if ("historicos".equals(tipoDeDataset)) {
                fileReader = new FileReader(diretorioAplicacao + "/datasets/datasetHistoricos.txt");
                scanner = new Scanner(fileReader);
            } else if ("grafoMedio".equals(tipoDeDataset)) {
                fileReader = new FileReader(diretorioAplicacao + "/datasets/datasetGrafoMedio.txt");
                scanner = new Scanner(fileReader);
            } else if ("grafoMediano".equals(tipoDeDataset)) {
                fileReader = new FileReader(diretorioAplicacao + "/datasets/datasetGrafoMediano.txt");
                scanner = new Scanner(fileReader);
            } else if ("grafoMaximo".equals(tipoDeDataset)) {
                fileReader = new FileReader(diretorioAplicacao + "/datasets/datasetGrafoMaximo.txt");
                scanner = new Scanner(fileReader);
            }

            Map<String, ArrayList<String>> idGrafo_Vertices = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGrafo_Arestas = new HashMap<String, ArrayList<String>>();
            Map<String, HashMap<Integer, HashSet<Integer>>> idGrafo_idVertice_Vizinhos = new HashMap<String, HashMap<Integer, HashSet<Integer>>>();
            Map<String, HashMap<Integer, ArrayList<String>>> idGrafo_idVertice_Arestas = new HashMap<String, HashMap<Integer, ArrayList<String>>>();
            Map<String, HashMap<String, Double>> idGrafo_Aresta_Peso = new HashMap<String, HashMap<String, Double>>();
            Map<String, HashMap<Integer, String>> idGrafo_idVertice_RotuloVertice = new HashMap<String, HashMap<Integer, String>>();

            String idGrafo = null;
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();

                if (linha.substring(0, 1).toUpperCase().equals("G")) {
                    idGrafo = linha.substring(linha.indexOf('|') + 1);

                    if (!idGrafo_Vertices.containsKey(idGrafo)) {
                        idGrafo_Vertices.put(idGrafo, new ArrayList<String>());
                    }

                    if (!idGrafo_Arestas.containsKey(idGrafo)) {
                        idGrafo_Arestas.put(idGrafo, new ArrayList<String>());
                    }

                    if (!idGrafo_idVertice_Vizinhos.containsKey(idGrafo)) {
                        idGrafo_idVertice_Vizinhos.put(idGrafo, new HashMap<Integer, HashSet<Integer>>());
                    }

                    if (!idGrafo_idVertice_Arestas.containsKey(idGrafo)) {
                        idGrafo_idVertice_Arestas.put(idGrafo, new HashMap<Integer, ArrayList<String>>());
                    }

                    if (!idGrafo_Aresta_Peso.containsKey(idGrafo)) {
                        idGrafo_Aresta_Peso.put(idGrafo, new HashMap<String, Double>());
                    }

                    if (!idGrafo_idVertice_RotuloVertice.containsKey(idGrafo)) {
                        idGrafo_idVertice_RotuloVertice.put(idGrafo, new HashMap<Integer, String>());
                    }
                } else if (linha.substring(0, 1).toUpperCase().equals("V")) {
                    String linhaVertice = linha;
                    idGrafo_Vertices.get(idGrafo).add(linhaVertice);

                    String[] campos = linhaVertice.split(Pattern.quote("|"));
                    int idVertice = Integer.parseInt(campos[1]);
                    String rotulo = campos[2];

                    if (!idGrafo_idVertice_Vizinhos.get(idGrafo).containsKey(idVertice)) {
                        idGrafo_idVertice_Vizinhos.get(idGrafo).put(idVertice, new HashSet<Integer>());
                    }

                    if (!idGrafo_idVertice_Arestas.get(idGrafo).containsKey(idVertice)) {
                        idGrafo_idVertice_Arestas.get(idGrafo).put(idVertice, new ArrayList<String>());
                    }

                    if (!idGrafo_idVertice_RotuloVertice.get(idGrafo).containsKey(idVertice)) {
                        idGrafo_idVertice_RotuloVertice.get(idGrafo).put(idVertice, rotulo);
                    }
                } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                    String linhaAresta = linha;
                    idGrafo_Arestas.get(idGrafo).add(linhaAresta);

                    String[] campos = linhaAresta.split(Pattern.quote("|"));
                    int idSource = Integer.parseInt(campos[1]);
                    int idTarget = Integer.parseInt(campos[3]);
                    double peso = Double.parseDouble(campos[5].replace(",", "."));

                    idGrafo_idVertice_Vizinhos.get(idGrafo).get(idSource).add(idTarget);

                    String aresta = campos[1] + "," + campos[3];

                    idGrafo_idVertice_Arestas.get(idGrafo).get(idSource).add(aresta);

                    peso = peso * (-1);

                    if (!idGrafo_Aresta_Peso.get(idGrafo).containsKey(aresta)) {
                        idGrafo_Aresta_Peso.get(idGrafo).put(aresta, peso);
                    }
                }
            }

            Set<String> idsGrafos = idGrafo_Arestas.keySet();
            for (String id : idsGrafos) {
                numeroDeNos = idGrafo_idVertice_Vizinhos.get(id).size();

                ArrayList<Integer>[] listaDeAdjacencia = (ArrayList<Integer>[]) new ArrayList[numeroDeNos];
                for (int i = 0; i < numeroDeNos; i++) {
                    listaDeAdjacencia[i] = new ArrayList<Integer>();
                }

                //int adjacency_matrix[][] = new int[numeroDeNos][numeroDeNos];
                for (int w = 0; w < numeroDeNos; w++) {
                    Iterator it = idGrafo_idVertice_Vizinhos.get(id).get(w).iterator();

                    while (it.hasNext()) {
                        int idVizinho = (int) it.next();
                        listaDeAdjacencia[w].add(idVizinho);
                        //adjacency_matrix[w][idVizinho] = 1;
                    }
                }

                Double[] distancia = new Double[numeroDeNos];
                int[] predecessor = new int[numeroDeNos];

                Arrays.fill(distancia, INF);
                Arrays.fill(predecessor, -1);
                distancia[0] = 0.0;
                origem = 0;
                BellmanFord.marcado = new int[numeroDeNos];
                BellmanFord.ordem_topologica = new int[numeroDeNos];
                BellmanFord.posicao = numeroDeNos - 1;
                Arrays.fill(BellmanFord.marcado, 0);

                BellmanFord bf = new BellmanFord();
                bf.ordemTopologicaComDFS(listaDeAdjacencia, origem);
                //BELLMAN-FORD - Livro do Papadimitriou
                for (int i = 0; i < numeroDeNos; i++) { //para cada vértice na ordem topológica                    
                    int u = BellmanFord.ordem_topologica[i];
                    
                    for (int y = 0; y < listaDeAdjacencia[u].size(); y++) {
                        int v = listaDeAdjacencia[u].get(y);
                        String aresta = u + "," + v;

                        if (distancia[v] > (distancia[u] + idGrafo_Aresta_Peso.get(id).get(aresta))) {
                            distancia[v] = (distancia[u] + idGrafo_Aresta_Peso.get(id).get(aresta));
                            predecessor[v] = u;
                        }
                    }                    
                }

                if (!idGrafo_Caminho.containsKey(id)) {
                    idGrafo_Caminho.put(id, new ArrayList<Integer>());
                }

                int x = 1; //destino | o nó conclusão é sempre 1                
                while (x != -1) {
                    idGrafo_Caminho.get(id).add(x);
                    x = predecessor[x];
                }
            }
        } catch (InputMismatchException inputMismatch) {
            System.out.println("Wrong Input format");
        }

        return idGrafo_Caminho;
    }
}
