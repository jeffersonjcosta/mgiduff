package br.com.grafos.algoritmos;

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
    public static int marcado[];
    public static int posicao;
    public static int ordem_topologica[];   
    public static ArrayList<String> caminhos;       
    public static ArrayList<String> caminhosComRotulo;

    public Backtracking() {
        Backtracking.stack = new Stack<Integer>();              
    }

    public static void bck(ArrayList<Integer>[] listaDeAdjacencia, int v) {

        stack.push(v);
        Backtracking.marcado[v] = 1;

        if (v == 1) {                 
            Backtracking.caminhos.add(stack.toString().replaceAll("\\[|\\]", "").replaceAll(",", "-").replaceAll(" ", ""));                           
        } else {
            for (int i = 0; i < listaDeAdjacencia[v].size(); i++) {
                if (Backtracking.marcado[listaDeAdjacencia[v].get(i)] == 0) {
                    Backtracking.bck(listaDeAdjacencia, listaDeAdjacencia[v].get(i));
                }
            }
        }
       
        Backtracking.marcado[v] = 0;
        stack.pop();
    }

    public ArrayList<String> todosOsCaminhosComBCK(String diretorioAplicacao, String idGrafo, String tipoDeExibicao) throws FileNotFoundException, IOException {      
        FileReader fileReader = null;
        Scanner scanner = null;
        try {
            fileReader = new FileReader(diretorioAplicacao + "/datasets/datasetGradeCurricular.txt");
            scanner = new Scanner(fileReader);

            Map<String, ArrayList<String>> idGrafo_Vertices = new HashMap<String, ArrayList<String>>();
            Map<String, ArrayList<String>> idGrafo_Arestas = new HashMap<String, ArrayList<String>>();
            Map<String, HashMap<Integer, HashSet<Integer>>> idGrafo_idVertice_Vizinhos = new HashMap<String, HashMap<Integer, HashSet<Integer>>>();
            Map<String, HashMap<Integer, ArrayList<String>>> idGrafo_idVertice_Arestas = new HashMap<String, HashMap<Integer, ArrayList<String>>>();
            Map<String, HashMap<String, Double>> idGrafo_Aresta_Peso = new HashMap<String, HashMap<String, Double>>();
            Map<String, HashMap<Integer, String>> idGrafo_idVertice_RotuloVertice = new HashMap<String, HashMap<Integer, String>>();

            String idGrafoDataset = null;
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();

                if (linha.substring(0, 1).toUpperCase().equals("G")) {
                    idGrafoDataset = linha.substring(linha.indexOf('|') + 1);

                    if (!idGrafo_Vertices.containsKey(idGrafoDataset)) {
                        idGrafo_Vertices.put(idGrafoDataset, new ArrayList<String>());
                    }

                    if (!idGrafo_Arestas.containsKey(idGrafoDataset)) {
                        idGrafo_Arestas.put(idGrafoDataset, new ArrayList<String>());
                    }

                    if (!idGrafo_idVertice_Vizinhos.containsKey(idGrafoDataset)) {
                        idGrafo_idVertice_Vizinhos.put(idGrafoDataset, new HashMap<Integer, HashSet<Integer>>());
                    }

                    if (!idGrafo_idVertice_Arestas.containsKey(idGrafoDataset)) {
                        idGrafo_idVertice_Arestas.put(idGrafoDataset, new HashMap<Integer, ArrayList<String>>());
                    }

                    if (!idGrafo_Aresta_Peso.containsKey(idGrafoDataset)) {
                        idGrafo_Aresta_Peso.put(idGrafoDataset, new HashMap<String, Double>());
                    }

                    if (!idGrafo_idVertice_RotuloVertice.containsKey(idGrafoDataset)) {
                        idGrafo_idVertice_RotuloVertice.put(idGrafoDataset, new HashMap<Integer, String>());
                    }
                } else if (linha.substring(0, 1).toUpperCase().equals("V")) {
                    String linhaVertice = linha;
                    idGrafo_Vertices.get(idGrafoDataset).add(linhaVertice);

                    String[] campos = linhaVertice.split(Pattern.quote("|"));
                    int idVertice = Integer.parseInt(campos[1]);
                    String rotulo = campos[2];

                    if (!idGrafo_idVertice_Vizinhos.get(idGrafoDataset).containsKey(idVertice)) {
                        idGrafo_idVertice_Vizinhos.get(idGrafoDataset).put(idVertice, new HashSet<Integer>());
                    }

                    if (!idGrafo_idVertice_Arestas.get(idGrafoDataset).containsKey(idVertice)) {
                        idGrafo_idVertice_Arestas.get(idGrafoDataset).put(idVertice, new ArrayList<String>());
                    }

                    if (!idGrafo_idVertice_RotuloVertice.get(idGrafoDataset).containsKey(idVertice)) {
                        idGrafo_idVertice_RotuloVertice.get(idGrafoDataset).put(idVertice, rotulo);
                    }
                } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                    String linhaAresta = linha;
                    idGrafo_Arestas.get(idGrafoDataset).add(linhaAresta);

                    String[] campos = linhaAresta.split(Pattern.quote("|"));
                    int idSource = Integer.parseInt(campos[1]);
                    int idTarget = Integer.parseInt(campos[3]);
                    double peso = Double.parseDouble(campos[5].replace(",", "."));

                    idGrafo_idVertice_Vizinhos.get(idGrafoDataset).get(idSource).add(idTarget);

                    String aresta = campos[1] + "," + campos[3];

                    idGrafo_idVertice_Arestas.get(idGrafoDataset).get(idSource).add(aresta);

                    peso = peso * (-1);

                    if (!idGrafo_Aresta_Peso.get(idGrafoDataset).containsKey(aresta)) {
                        idGrafo_Aresta_Peso.get(idGrafoDataset).put(aresta, peso);
                    }
                }
            }

            int numeroDeNos, origem = 0;

            numeroDeNos = idGrafo_idVertice_Vizinhos.get(idGrafo).size();

            ArrayList<Integer>[] listaDeAdjacencia = (ArrayList<Integer>[]) new ArrayList[numeroDeNos];
            for (int i = 0; i < numeroDeNos; i++) {
                listaDeAdjacencia[i] = new ArrayList<Integer>();
            }

            for (int w = 0; w < numeroDeNos; w++) {
                Iterator it = idGrafo_idVertice_Vizinhos.get(idGrafo).get(w).iterator();

                while (it.hasNext()) {
                    int idVizinho = (int) it.next();
                    listaDeAdjacencia[w].add(idVizinho);
                }
            }

            Backtracking.marcado = new int[numeroDeNos];           
            Backtracking.caminhos = new ArrayList<String>();
           
            Arrays.fill(Backtracking.marcado, 0);         

            Backtracking b = new Backtracking();
            b.bck(listaDeAdjacencia, origem);

            Backtracking.caminhosComRotulo = new ArrayList<String>();            
            Iterator it = Backtracking.caminhos.iterator();
            while (it.hasNext()) {
                String caminho = (String) it.next();
                                
                String[] ids = caminho.split("-");
                String caminhoComRotulo = "";
                for (int i = 0; i < ids.length; i++) {                    
                    caminhoComRotulo += idGrafo_idVertice_RotuloVertice.get(idGrafo).get(Integer.parseInt(ids[i]));
                    if (!ids[i].equals("1")) {
                        caminhoComRotulo += " - ";
                    }                                        
                }
                
                caminhosComRotulo.add(caminhoComRotulo);
            }
        } catch (InputMismatchException inputMismatch) {
            System.out.println("Wrong Input format");
        }

        if ("rotulo".equals(tipoDeExibicao)) {
            return Backtracking.caminhosComRotulo;
        } else {
            return Backtracking.caminhos;
        }
    }
}
