package br.com.grafos.actions;

import br.com.grafos.algoritmos.BellmanFord;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

public final class CriarDatasetAction {

    private final String DIR_DATASETS = "datasets";
    private final String DATASET_GRADE_CURRICULAR_NOME = "datasetGradeCurricular";
    private final String DATASET_HISTORICOS_NOME = "datasetHistoricos";
    private final String DATASET_GRAFO_MEDIO_NOME = "datasetGrafoMedio";
    private final String DATASET_GRAFO_MEDIANO_NOME = "datasetGrafoMediano";
    private final String DATASET_GRAFO_MAXIMO_NOME = "datasetGrafoMaximo";
    private final String DATASET_CAMINHOS_GRADE_CURRICULAR_NOME = "datasetCaminhosGradeCurricular";
    private final String DATASET_CAMINHOS_HISTORICOS_NOME = "datasetCaminhosHistoricos";
    private final String DATASET_CAMINHOS_GRAFO_MEDIO_NOME = "datasetCaminhosGrafoMedio";
    private final String DATASET_CAMINHOS_GRAFO_MEDIANO_NOME = "datasetCaminhosGrafoMediano";
    private final String DATASET_CAMINHOS_GRAFO_MAXIMO_NOME = "datasetCaminhosGrafoMaximo";
    private final String DATASET_ARESTAS_MAIS_CARAS_NOME = "datasetArestasMaisPesadas";

    public void criarDatasetGradeCurricular(File disciplinasEPreRequisitos, String diretorioAplicacao) throws FileNotFoundException, IOException {

        FileWriter datasetGradeCurricular = this.criarArquivoParaDataset(diretorioAplicacao, DATASET_GRADE_CURRICULAR_NOME, "txt");
        PrintWriter datasetGradeCurricularPrint = new PrintWriter(datasetGradeCurricular, true);
        FileReader fileReaderDis = new FileReader(disciplinasEPreRequisitos.getAbsolutePath());
        Scanner scannerDis = new Scanner(fileReaderDis);

        Map<String, ArrayList<String>> idCurriculo_Disciplinas = new HashMap<String, ArrayList<String>>();
        Map<String, ArrayList<String>> idCurriculo_PreRequisitos = new HashMap<String, ArrayList<String>>();
        Map<String, HashSet<String>> idCurriculo_Periodos = new HashMap<String, HashSet<String>>();
        Map<String, TreeMap<Integer, HashSet<String>>> idCurriculo_Periodo_Disciplinas = new HashMap<String, TreeMap<Integer, HashSet<String>>>();
        Map<String, HashMap<String, HashSet<String>>> idCurriculo_Disciplina_PreRequisitos = new HashMap<String, HashMap<String, HashSet<String>>>();
        Map<String, Integer> idDisciplina_CodNumerico = new HashMap<String, Integer>();
        Map<String, HashSet<String>> idCurriculo_DisciplinasSemPreRequisitos = new HashMap<String, HashSet<String>>();
        Map<String, HashMap<Integer, String>> idCurriculo_IdVertice_RotuloVertice = new HashMap<String, HashMap<Integer, String>>();

        String pesoArestas = "1";

        String idCurr = null;
        String idDisc = null;
        String periodo = null;
        String idPreRequisito = null;
        String periodoPreRequisito = null;

        while (scannerDis.hasNextLine()) {
            String line = scannerDis.nextLine();

            String[] fields = line.split(";");

            idCurr = fields[0];
            idDisc = fields[1];
            periodo = fields[2];
            idPreRequisito = fields[5];
            periodoPreRequisito = fields[6];

            if ("-".equals(periodoPreRequisito)) {
                periodoPreRequisito = "0";
            }

            if (!idCurriculo_Disciplinas.containsKey(idCurr)) {
                idCurriculo_Disciplinas.put(idCurr, new ArrayList<String>());
            }

            if (!idCurriculo_PreRequisitos.containsKey(idCurr)) {
                idCurriculo_PreRequisitos.put(idCurr, new ArrayList<String>());
            }

            if (!idCurriculo_Periodos.containsKey(idCurr)) {
                idCurriculo_Periodos.put(idCurr, new HashSet<String>());
            }

            if (!idCurriculo_Periodo_Disciplinas.containsKey(idCurr)) {
                idCurriculo_Periodo_Disciplinas.put(idCurr, new TreeMap<Integer, HashSet<String>>());
            }

            if (!idCurriculo_Disciplina_PreRequisitos.containsKey(idCurr)) {
                idCurriculo_Disciplina_PreRequisitos.put(idCurr, new HashMap<String, HashSet<String>>());
            }

            if (!idCurriculo_Periodo_Disciplinas.get(idCurr).containsKey(Integer.parseInt(periodo))) {
                idCurriculo_Periodo_Disciplinas.get(idCurr).put(Integer.parseInt(periodo), new HashSet<String>());
            }

            if (!idCurriculo_Periodo_Disciplinas.get(idCurr).containsKey(Integer.parseInt(periodoPreRequisito))) {
                idCurriculo_Periodo_Disciplinas.get(idCurr).put(Integer.parseInt(periodoPreRequisito), new HashSet<String>());
            }

            if (!idCurriculo_Disciplina_PreRequisitos.get(idCurr).containsKey(idDisc)) {
                idCurriculo_Disciplina_PreRequisitos.get(idCurr).put(idDisc, new HashSet<String>());
            }

            if (!idCurriculo_DisciplinasSemPreRequisitos.containsKey(idCurr)) {
                idCurriculo_DisciplinasSemPreRequisitos.put(idCurr, new HashSet<String>());
            }

            if (!idCurriculo_IdVertice_RotuloVertice.containsKey(idCurr)) {
                idCurriculo_IdVertice_RotuloVertice.put(idCurr, new HashMap<Integer, String>());
            }

            idCurriculo_Disciplinas.get(idCurr).add(idDisc);
            idCurriculo_PreRequisitos.get(idCurr).add(idPreRequisito);
            idCurriculo_Periodos.get(idCurr).add(periodo);
            idCurriculo_Periodos.get(idCurr).add(periodoPreRequisito);
            idCurriculo_Periodos.get(idCurr).add("0");

            if (!idCurriculo_Periodo_Disciplinas.get(idCurr).containsKey(0)) {
                idCurriculo_Periodo_Disciplinas.get(idCurr).put(0, new HashSet<String>());
            }

            idCurriculo_Periodo_Disciplinas.get(idCurr).get(Integer.parseInt(periodo)).add(idDisc);

            idCurriculo_Periodo_Disciplinas.get(idCurr).get(0).add("Ingresso");
            idCurriculo_Periodo_Disciplinas.get(idCurr).get(0).add("Conclusao");

            idCurriculo_Disciplina_PreRequisitos.get(idCurr).get(idDisc).add(idPreRequisito);

            if (!idCurriculo_Periodo_Disciplinas.get(idCurr).get(Integer.parseInt(periodoPreRequisito)).contains(idPreRequisito)) {
                idCurriculo_Periodo_Disciplinas.get(idCurr).get(Integer.parseInt(periodoPreRequisito)).add(idPreRequisito);
                idCurriculo_DisciplinasSemPreRequisitos.get(idCurr).add(idPreRequisito);
            }
        }

        Set<String> idsDosCurriculos = idCurriculo_PreRequisitos.keySet();
        for (String idCurriculo : idsDosCurriculos) {
            if (idCurriculo != null) {
                datasetGradeCurricularPrint.println("g|" + idCurriculo);

                Iterator n = idCurriculo_Periodos.get(idCurriculo).iterator();
                int x = 0;
                while (n.hasNext()) {
                    String per = (String) n.next();

                    int p = Integer.parseInt(per);

                    Iterator it = idCurriculo_Periodo_Disciplinas.get(idCurriculo).get(p).iterator();
                    while (it.hasNext()) {
                        String idDisciplina = (String) it.next();
                        datasetGradeCurricularPrint.println("v|" + x + "|" + idDisciplina + "|" + p);
                        idDisciplina_CodNumerico.put(idDisciplina, x);

                        if (!idCurriculo_IdVertice_RotuloVertice.get(idCurriculo).containsKey(x)) {
                            idCurriculo_IdVertice_RotuloVertice.get(idCurriculo).put(x, idDisciplina);
                        }

                        x++;
                    }
                }

                Iterator w = idCurriculo_Periodos.get(idCurriculo).iterator();
                while (w.hasNext()) {
                    String per = (String) w.next();

                    int p = Integer.parseInt(per);

                    Iterator it2 = idCurriculo_Periodo_Disciplinas.get(idCurriculo).get(p).iterator();
                    while (it2.hasNext()) {
                        String idDisciplina = (String) it2.next();

                        if (idCurriculo_Disciplina_PreRequisitos.get(idCurriculo).containsKey(idDisciplina)) {
                            Iterator it3 = idCurriculo_Disciplina_PreRequisitos.get(idCurriculo).get(idDisciplina).iterator();
                            while (it3.hasNext()) {
                                String idPreReq = (String) it3.next();
                                datasetGradeCurricularPrint.println("e|" + idDisciplina_CodNumerico.get(idPreReq) + "|" + idPreReq + "|" + idDisciplina_CodNumerico.get(idDisciplina) + "|" + idDisciplina + "|" + pesoArestas);
                            }

                            if (!idCurriculo_PreRequisitos.get(idCurriculo).contains(idDisciplina) && idCurriculo_Periodo_Disciplinas.get(idCurriculo).get(p).contains(idDisciplina)) {
                                if (!"Conclusao".equals(idDisciplina)) {
                                    datasetGradeCurricularPrint.println("e|" + idDisciplina_CodNumerico.get(idDisciplina) + "|" + idDisciplina + "|" + idDisciplina_CodNumerico.get("Conclusao") + "|Conclusao|" + pesoArestas);
                                }
                            }
                        }
                    }
                }

                Iterator i3 = idCurriculo_DisciplinasSemPreRequisitos.get(idCurriculo).iterator();
                while (i3.hasNext()) {
                    String idDiscSemPreRequisito = (String) i3.next();
                    if (idCurriculo_Disciplina_PreRequisitos.get(idCurriculo).get(idDiscSemPreRequisito) == null) {
                        datasetGradeCurricularPrint.println("e|" + idDisciplina_CodNumerico.get("Ingresso") + "|Ingresso|" + idDisciplina_CodNumerico.get(idDiscSemPreRequisito) + "|" + idDiscSemPreRequisito + "|" + pesoArestas);
                    }
                }
            }
        }

        datasetGradeCurricularPrint.flush();

    }

    public void criarDatasetHistoricos(File equivalencias, File acompanhamentos, File historicos, String diretorioAplicacao) throws FileNotFoundException, IOException {
        FileReader fileReaderEquivalencias = new FileReader(equivalencias.getAbsolutePath());
        FileReader fileReaderAcompanhamentos = new FileReader(acompanhamentos.getAbsolutePath());
        FileReader fileReaderHistoricos = new FileReader(historicos.getAbsolutePath());
        FileReader fileReaderDatasetGradeCurricular = new FileReader(diretorioAplicacao + File.separator + "datasets" + File.separator + "datasetGradeCurricular.txt");

        Scanner scannerEquivalencias = new Scanner(fileReaderEquivalencias);
        Scanner scannerAcompanhamentos = new Scanner(fileReaderAcompanhamentos);
        Scanner scannerHistoricos = new Scanner(fileReaderHistoricos);
        Scanner scannerDatasetGradeCurricular = new Scanner(fileReaderDatasetGradeCurricular);

        Map<String, ArrayList<String>> idCurriculo_Vertices = new HashMap<String, ArrayList<String>>();
        Map<String, HashSet<String>> idCurriculo_Arestas = new HashMap<String, HashSet<String>>();

        String idCurr = null;
        while (scannerDatasetGradeCurricular.hasNextLine()) {
            String linha = scannerDatasetGradeCurricular.nextLine();

            if (linha.substring(0, 1).toUpperCase().equals("G")) {
                idCurr = linha.substring(linha.indexOf('|') + 1);

                if (!idCurriculo_Vertices.containsKey(idCurr)) {
                    idCurriculo_Vertices.put(idCurr, new ArrayList<String>());
                }
                if (!idCurriculo_Arestas.containsKey(idCurr)) {
                    idCurriculo_Arestas.put(idCurr, new HashSet<String>());
                }
            } else if (linha.substring(0, 1).toUpperCase().equals("V")) {
                idCurriculo_Vertices.get(idCurr).add(linha);
            } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                idCurriculo_Arestas.get(idCurr).add(linha);
            }
        }

        Map<String, HashSet<String>> idAluno_Curriculos = new HashMap<String, HashSet<String>>();
        Map<String, ArrayList<String>> idAluno_Status = new HashMap<String, ArrayList<String>>();
        Map<String, ArrayList<String>> mapAuxiliarParaContagemDoCurriculoQueMaisRepete = new HashMap<String, ArrayList<String>>();

        String idAlunoAcompanhamento = null;
        String status = null;
        String curriculo1 = null;

        while (scannerAcompanhamentos.hasNextLine()) {
            String linha = scannerAcompanhamentos.nextLine();
            String[] campos = linha.split(";");
            
            idAlunoAcompanhamento = campos[0];
            status = campos[1];
            curriculo1 = campos[4];

            status = status.replace("\"", "");
            curriculo1 = curriculo1.replace("\"", "");

            if (!idAluno_Curriculos.containsKey(idAlunoAcompanhamento)) {
                idAluno_Curriculos.put(idAlunoAcompanhamento, new HashSet<String>());
            }

            if (!idAluno_Status.containsKey(idAlunoAcompanhamento)) {
                idAluno_Status.put(idAlunoAcompanhamento, new ArrayList<String>());
            }

            if (!mapAuxiliarParaContagemDoCurriculoQueMaisRepete.containsKey(idAlunoAcompanhamento)) {
                mapAuxiliarParaContagemDoCurriculoQueMaisRepete.put(idAlunoAcompanhamento, new ArrayList<String>());
            }

            if (!curriculo1.isEmpty()) {
                mapAuxiliarParaContagemDoCurriculoQueMaisRepete.get(idAlunoAcompanhamento).add(curriculo1);
            }

            idAluno_Status.get(idAlunoAcompanhamento).add(status);
        }

        Map<String, HashSet<String>> idAluno_DisciplinasCursadasSemRepeticao = new HashMap<String, HashSet<String>>();
        //Crio esse MAP apenas para fazer o while na hora de adicionar as equivalencias, pois se usasse o MAP idAluno_DisciplinasCursadasSemRepeticao não conseguiria fazer o add no MAP (não tem como percorrer o MAP e adicionar um valor ao memso tempo)
        Map<String, HashSet<String>> idAluno_DisciplinasCursadasSemRepeticaoApenasParaIterator = new HashMap<String, HashSet<String>>();
        Map<String, ArrayList<String>> idAluno_DisciplinasCursadasComRepeticao = new HashMap<String, ArrayList<String>>();

        String idAlunoHistorico = null;
        String idDisciplinaAluno = null;
        String situacaoDisciplina = null;
        while (scannerHistoricos.hasNextLine()) {
            String linha = scannerHistoricos.nextLine();

            String[] campos = linha.split(";");
                      
            idAlunoHistorico = campos[0];
            idDisciplinaAluno = campos[2];
            situacaoDisciplina = campos[6];

            if (!idAluno_DisciplinasCursadasSemRepeticao.containsKey(idAlunoHistorico)) {
                idAluno_DisciplinasCursadasSemRepeticao.put(idAlunoHistorico, new HashSet<String>());
            }

            if (!idAluno_DisciplinasCursadasSemRepeticaoApenasParaIterator.containsKey(idAlunoHistorico)) {
                idAluno_DisciplinasCursadasSemRepeticaoApenasParaIterator.put(idAlunoHistorico, new HashSet<String>());
            }

            if (!idAluno_DisciplinasCursadasComRepeticao.containsKey(idAlunoHistorico)) {
                idAluno_DisciplinasCursadasComRepeticao.put(idAlunoHistorico, new ArrayList<String>());
            }

            idDisciplinaAluno = idDisciplinaAluno.replace("\"", "");

            //C = disciplinas trancadas pelo aluno, não foram cursadas, mas são registradas pra controlar limite de trancamento
            //D = considerada aprovação (o aluno cursou a disciplina em outra instituição e conseguiu dispensar na UFF)
            //S = disciplinas que o aluno se inscreveu mas o professor não lançou nota. Considerada reprovação para fins de cancelamento de matrícula por 4 reprovações, etc., mas não para o CR                        
            if (!"\"C\"".equals(situacaoDisciplina)) {
                idAluno_DisciplinasCursadasSemRepeticao.get(idAlunoHistorico).add(idDisciplinaAluno);
                idAluno_DisciplinasCursadasSemRepeticaoApenasParaIterator.get(idAlunoHistorico).add(idDisciplinaAluno);
                idAluno_DisciplinasCursadasComRepeticao.get(idAlunoHistorico).add(idDisciplinaAluno);
            }
        }

        Map<String, String> idEquivalencia_Disciplina = new HashMap<String, String>();

        String idDiscCurr;
        String idDiscEquivalente;
        while (scannerEquivalencias.hasNext()) {
            String linha = scannerEquivalencias.nextLine();
            String[] campos = linha.split(";");

            idDiscCurr = campos[1];
            idDiscEquivalente = campos[2];

            idEquivalencia_Disciplina.put(idDiscEquivalente, idDiscCurr);
        }

        FileWriter datasetHistoricos = this.criarArquivoParaDataset(diretorioAplicacao, DATASET_HISTORICOS_NOME, "txt");
        PrintWriter datasetHistoricosPrint = new PrintWriter(datasetHistoricos, true);
        String idCurriculoAluno = null;
        Set<String> idsDosAlunos = idAluno_Curriculos.keySet();
        for (String idAluno : idsDosAlunos) {
            if (idAluno != null) {
                if (idAluno_Status.get(idAluno).contains("Formado")) {
                    if (idAluno_DisciplinasCursadasSemRepeticao.containsKey(idAluno)) {
                        idAluno_DisciplinasCursadasSemRepeticao.get(idAluno).add("Ingresso");
                        idAluno_DisciplinasCursadasSemRepeticao.get(idAluno).add("Conclusao");

                        idAluno_DisciplinasCursadasComRepeticao.get(idAluno).add("Ingresso");
                        idAluno_DisciplinasCursadasComRepeticao.get(idAluno).add("Conclusao");
                    }

                    if (idAluno_DisciplinasCursadasSemRepeticaoApenasParaIterator.containsKey(idAluno)) {
                        Iterator i = idAluno_DisciplinasCursadasSemRepeticaoApenasParaIterator.get(idAluno).iterator();
                        while (i.hasNext()) {
                            String idDiscAluno = (String) i.next();
                            if (idEquivalencia_Disciplina.containsKey(idDiscAluno)) {
                                idAluno_DisciplinasCursadasSemRepeticao.get(idAluno).add(idEquivalencia_Disciplina.get(idDiscAluno));
                                idAluno_DisciplinasCursadasComRepeticao.get(idAluno).add(idEquivalencia_Disciplina.get(idDiscAluno));
                            }
                        }
                    }

                    idAluno_Curriculos.get(idAluno).add(this.getValorQueMaisSeRepeteNoMap(mapAuxiliarParaContagemDoCurriculoQueMaisRepete, idAluno));

                    Iterator iter1 = idAluno_Curriculos.get(idAluno).iterator();
                    while (iter1.hasNext()) {
                        idCurriculoAluno = (String) iter1.next();

                        if (idCurriculo_Arestas.containsKey(idCurriculoAluno)) {

                            datasetHistoricosPrint.println("g|" + idAluno + "|" + idCurriculoAluno);

                            Iterator iter2 = idCurriculo_Vertices.get(idCurriculoAluno).iterator();
                            while (iter2.hasNext()) {
                                String linhaVertice = (String) iter2.next();

                                datasetHistoricosPrint.println(linhaVertice);
                            }
                            Iterator iter3 = idCurriculo_Arestas.get(idCurriculoAluno).iterator();
                            while (iter3.hasNext()) {
                                String linhaAresta = (String) iter3.next();

                                String[] campos = linhaAresta.split(Pattern.quote("|"));
                                String idVerticeOrigem = campos[1];
                                String codPreRequisito = campos[2];
                                String idVerticeDestino = campos[3];
                                String codDisciplina = campos[4];
                                String pesoAresta = campos[5];

                                pesoAresta = Integer.toString(Collections.frequency(idAluno_DisciplinasCursadasComRepeticao.get(idAluno), codPreRequisito));

                                if (!idAluno_DisciplinasCursadasSemRepeticao.get(idAluno).contains(codPreRequisito)) {
                                    pesoAresta = "0";
                                }

                                datasetHistoricosPrint.println("e|" + idVerticeOrigem + "|" + codPreRequisito + "|" + idVerticeDestino + "|" + codDisciplina + "|" + pesoAresta);
                            }
                            datasetHistoricosPrint.flush();
                        }
                    }
                }
            }
        }
    }

    public void criarDatasetCaminhosMaisLongos(String diretorioAplicacao, String tipo) throws FileNotFoundException, IOException {
        FileWriter dataset = null;
        PrintWriter datasetPrint = null;
        FileReader fileReader = null;
        Scanner scanner = null;

        if ("gradeCurricular".equals(tipo)) {
            dataset = this.criarArquivoParaDataset(diretorioAplicacao, DATASET_CAMINHOS_GRADE_CURRICULAR_NOME, "txt");
            datasetPrint = new PrintWriter(dataset, true);
            fileReader = new FileReader(diretorioAplicacao + "/" + DIR_DATASETS + "/" + DATASET_GRADE_CURRICULAR_NOME + ".txt");
            scanner = new Scanner(fileReader);
        } else if ("historicos".equals(tipo)) {
            dataset = this.criarArquivoParaDataset(diretorioAplicacao, DATASET_CAMINHOS_HISTORICOS_NOME, "txt");
            datasetPrint = new PrintWriter(dataset, true);
            fileReader = new FileReader(diretorioAplicacao + "/" + DIR_DATASETS + "/" + DATASET_HISTORICOS_NOME + ".txt");
            scanner = new Scanner(fileReader);
        } else if ("grafoMedio".equals(tipo)) {
            dataset = this.criarArquivoParaDataset(diretorioAplicacao, DATASET_CAMINHOS_GRAFO_MEDIO_NOME, "txt");
            datasetPrint = new PrintWriter(dataset, true);
            fileReader = new FileReader(diretorioAplicacao + "/" + DIR_DATASETS + "/" + DATASET_GRAFO_MEDIO_NOME + ".txt");
            scanner = new Scanner(fileReader);
        } else if ("grafoMediano".equals(tipo)) {
            dataset = this.criarArquivoParaDataset(diretorioAplicacao, DATASET_CAMINHOS_GRAFO_MEDIANO_NOME, "txt");
            datasetPrint = new PrintWriter(dataset, true);
            fileReader = new FileReader(diretorioAplicacao + "/" + DIR_DATASETS + "/" + DATASET_GRAFO_MEDIANO_NOME + ".txt");
            scanner = new Scanner(fileReader);
        } else if ("grafoMaximo".equals(tipo)) {
            dataset = this.criarArquivoParaDataset(diretorioAplicacao, DATASET_CAMINHOS_GRAFO_MAXIMO_NOME, "txt");
            datasetPrint = new PrintWriter(dataset, true);
            fileReader = new FileReader(diretorioAplicacao + "/" + DIR_DATASETS + "/" + DATASET_GRAFO_MAXIMO_NOME + ".txt");
            scanner = new Scanner(fileReader);
        }

        Map<String, HashMap<Integer, String>> idGrafo_idVertice_RotuloVertice = new HashMap<String, HashMap<Integer, String>>();
        Map<String, HashMap<String, String>> idGrafo_Aresta_Peso = new HashMap<String, HashMap<String, String>>();
        String idGraph = null;
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();

            if (linha.substring(0, 1).toUpperCase().equals("G")) {
                idGraph = linha.substring(linha.indexOf('|') + 1);

                if (!idGrafo_idVertice_RotuloVertice.containsKey(idGraph)) {
                    idGrafo_idVertice_RotuloVertice.put(idGraph, new HashMap<Integer, String>());
                }

                if (!idGrafo_Aresta_Peso.containsKey(idGraph)) {
                    idGrafo_Aresta_Peso.put(idGraph, new HashMap<String, String>());
                }
            } else if (linha.substring(0, 1).toUpperCase().equals("V")) {
                String[] campos = linha.split(Pattern.quote("|"));

                String idVertice = campos[1];
                String rotuloVertice = campos[2];

                int idVer = Integer.parseInt(idVertice);

                if (!idGrafo_idVertice_RotuloVertice.get(idGraph).containsKey(idVer)) {
                    idGrafo_idVertice_RotuloVertice.get(idGraph).put(idVer, rotuloVertice);
                }
            } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                String[] campos = linha.split(Pattern.quote("|"));

                String idVerticeOrigem = campos[1];
                //String rotuloVerticeOrigem = campos[2];
                String idVerticeDestino = campos[3];
                //String rotuloVerticeDestino = campos[4];
                String pesoAresta = campos[5];

                String arestaComIds = idVerticeOrigem + "," + idVerticeDestino;

                if (!idGrafo_Aresta_Peso.get(idGraph).containsKey(arestaComIds)) {
                    idGrafo_Aresta_Peso.get(idGraph).put(arestaComIds, pesoAresta);
                }
            }
        }

        Map<String, ArrayList<Integer>> idGrafo_Caminho = new HashMap<String, ArrayList<Integer>>();      
        BellmanFord b = new BellmanFord();
        idGrafo_Caminho = b.encontrarCaminhoMaisLongoComBellmanFord(diretorioAplicacao, tipo);

        Set<String> idsGrafos = idGrafo_Caminho.keySet();
        for (String idGrafo : idsGrafos) {

            int tamanhoCaminho = idGrafo_Caminho.get(idGrafo).size();

            datasetPrint.print("c|" + idGrafo + "|");
            for (int t = (tamanhoCaminho - 1); t >= 0; t--) {
                datasetPrint.print(idGrafo_Caminho.get(idGrafo).get(t));
                if (idGrafo_Caminho.get(idGrafo).get(t) != 1) {
                    datasetPrint.print("-");
                }
            }

            datasetPrint.println();

            for (int t = (tamanhoCaminho - 1); t >= 0; t--) {
                datasetPrint.println("v|" + idGrafo_Caminho.get(idGrafo).get(t) + "|" + idGrafo_idVertice_RotuloVertice.get(idGrafo).get(idGrafo_Caminho.get(idGrafo).get(t)));
            }

            for (int t = (tamanhoCaminho - 1); t >= 0; t--) {
                if (t < tamanhoCaminho - 1) {
                    datasetPrint.println(
                            "e|"
                            + idGrafo_Caminho.get(idGrafo).get(t + 1)
                            + "|"
                            + idGrafo_idVertice_RotuloVertice.get(idGrafo).get(idGrafo_Caminho.get(idGrafo).get(t + 1))
                            + "|"
                            + idGrafo_Caminho.get(idGrafo).get(t)
                            + "|"
                            + idGrafo_idVertice_RotuloVertice.get(idGrafo).get(idGrafo_Caminho.get(idGrafo).get(t))
                            + "|"
                            + idGrafo_Aresta_Peso.get(idGrafo).get(idGrafo_Caminho.get(idGrafo).get(t + 1) + "," + idGrafo_Caminho.get(idGrafo).get(t))
                    );
                }
            }
        }
    }

    public void criarDatasetGrafoMedio(String diretorioAplicacao) throws FileNotFoundException, IOException {
        FileReader fileReaderDatasetGradeCurricular = new FileReader(diretorioAplicacao + File.separator + DIR_DATASETS + File.separator + DATASET_GRADE_CURRICULAR_NOME + ".txt");
        Scanner scannerDatasetGradeCurricular = new Scanner(fileReaderDatasetGradeCurricular);

        Map<String, ArrayList<String>> idCurriculo_Vertices = new HashMap<String, ArrayList<String>>();
        Map<String, HashMap<String, String>> idCurriculo_Vertice_Rotulo = new HashMap<String, HashMap<String, String>>();
        Map<String, HashMap<String, Integer>> idCurriculo_Aresta_Peso = new HashMap<String, HashMap<String, Integer>>();
        Map<String, HashSet<String>> idCurriculo_Arestas = new HashMap<String, HashSet<String>>();

        String idCurriculo = null;
        while (scannerDatasetGradeCurricular.hasNextLine()) {
            String linha = scannerDatasetGradeCurricular.nextLine();

            if (linha.substring(0, 1).toUpperCase().equals("G")) {
                idCurriculo = linha.substring(linha.indexOf('|') + 1);

                if (!idCurriculo_Vertices.containsKey(idCurriculo)) {
                    idCurriculo_Vertices.put(idCurriculo, new ArrayList<String>());
                }

                if (!idCurriculo_Vertice_Rotulo.containsKey(idCurriculo)) {
                    idCurriculo_Vertice_Rotulo.put(idCurriculo, new HashMap<String, String>());
                }

                if (!idCurriculo_Aresta_Peso.containsKey(idCurriculo)) {
                    idCurriculo_Aresta_Peso.put(idCurriculo, new HashMap<String, Integer>());
                }

                if (!idCurriculo_Arestas.containsKey(idCurriculo)) {
                    idCurriculo_Arestas.put(idCurriculo, new HashSet<String>());
                }
            } else if (linha.substring(0, 1).toUpperCase().equals("V")) {
                String[] campos = linha.split(Pattern.quote("|"));

                String idVertice = campos[1];
                String rotuloVertice = campos[2];

                if (!idCurriculo_Vertice_Rotulo.get(idCurriculo).containsKey(idVertice)) {
                    idCurriculo_Vertice_Rotulo.get(idCurriculo).put(idVertice, rotuloVertice);
                }

                idCurriculo_Vertices.get(idCurriculo).add(linha);
            } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                String[] campos = linha.split(Pattern.quote("|"));

                String idVerticeOrigem = campos[1];
                String idVerticeDestino = campos[3];

                int pesoAresta = 0;//Seto todas as arestas com peso 0 (zero) para depois ir incrementando de acordo com cada histórico

                String arestaComIds = idVerticeOrigem + "," + idVerticeDestino;

                if (!idCurriculo_Aresta_Peso.get(idCurriculo).containsKey(arestaComIds)) {
                    idCurriculo_Aresta_Peso.get(idCurriculo).put(arestaComIds, pesoAresta);
                }

                idCurriculo_Arestas.get(idCurriculo).add(arestaComIds);
            }
        }

        FileReader fileReaderDatasetHistoricos = new FileReader(diretorioAplicacao + File.separator + DIR_DATASETS + File.separator + DATASET_HISTORICOS_NOME + ".txt");
        Scanner scannerDatasetHistoricos = new Scanner(fileReaderDatasetHistoricos);

        Map<String, ArrayList<String>> idCurriculoAluno_Aresta = new HashMap<String, ArrayList<String>>();

        String idCurriculoAluno = null;
        while (scannerDatasetHistoricos.hasNextLine()) {
            String linha = scannerDatasetHistoricos.nextLine();

            if (linha.substring(0, 1).toUpperCase().equals("G")) {
                String[] campos = linha.split(Pattern.quote("|"));

                idCurriculoAluno = campos[2];

                if (!idCurriculoAluno_Aresta.containsKey(idCurriculoAluno)) {
                    idCurriculoAluno_Aresta.put(idCurriculoAluno, new ArrayList<String>());
                }
            } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                String[] campos = linha.split(Pattern.quote("|"));

                String idVerticeOrigem = campos[1];
                String idVerticeDestino = campos[3];
                int pesoArestaAluno = Integer.parseInt(campos[5]);

                String arestaComIds = idVerticeOrigem + "," + idVerticeDestino;

                if (pesoArestaAluno > 0) {
                    idCurriculoAluno_Aresta.get(idCurriculoAluno).add(arestaComIds);
                }

                if (idCurriculo_Aresta_Peso.get(idCurriculoAluno).containsKey(arestaComIds)) {
                    int pesoArestaNovo = pesoArestaAluno + idCurriculo_Aresta_Peso.get(idCurriculoAluno).get(arestaComIds);
                    idCurriculo_Aresta_Peso.get(idCurriculoAluno).put(arestaComIds, pesoArestaNovo);
                }
            }
        }

        FileWriter dataset = this.criarArquivoParaDataset(diretorioAplicacao, DATASET_GRAFO_MEDIO_NOME, "txt");;
        PrintWriter datasetPrint = new PrintWriter(dataset, true);

        Set<String> idsCurriculos = idCurriculo_Arestas.keySet();
        for (String idCurr : idsCurriculos) {
            if (idCurriculoAluno_Aresta.containsKey(idCurr)) {
                datasetPrint.println("g|" + idCurr);

                Iterator i = idCurriculo_Vertices.get(idCurr).iterator();
                while (i.hasNext()) {
                    String linhaVertice = (String) i.next();
                    datasetPrint.println(linhaVertice);
                }

                Iterator i2 = idCurriculo_Arestas.get(idCurr).iterator();
                while (i2.hasNext()) {
                    String aresta = (String) i2.next();

                    double qtdAlunosQueCursaramAresta = Collections.frequency(idCurriculoAluno_Aresta.get(idCurr), aresta);
                    double pesoTotalAresta = idCurriculo_Aresta_Peso.get(idCurr).get(aresta);

                    double mediaAresta = 0;
                    if (qtdAlunosQueCursaramAresta > 0) {
                        mediaAresta = pesoTotalAresta / qtdAlunosQueCursaramAresta;
                    }

                    String[] campos = aresta.split(Pattern.quote(","));

                    String idVerticeOrigem = campos[0];
                    String idVerticeDestino = campos[1];

                    datasetPrint.println("e|" + idVerticeOrigem + "|" + idCurriculo_Vertice_Rotulo.get(idCurr).get(idVerticeOrigem) + "|" + idVerticeDestino + "|" + idCurriculo_Vertice_Rotulo.get(idCurr).get(idVerticeDestino) + "|" + String.format("%.2f", mediaAresta));
                }
            }
        }
    }

    public void criarDatasetGrafoMediano(String diretorioAplicacao) throws FileNotFoundException, IOException {
        FileReader fileReaderDatasetGradeCurricular = new FileReader(diretorioAplicacao + File.separator + DIR_DATASETS + File.separator + DATASET_GRADE_CURRICULAR_NOME + ".txt");
        Scanner scannerDatasetGradeCurricular = new Scanner(fileReaderDatasetGradeCurricular);

        Map<String, ArrayList<String>> idCurriculo_Vertices = new HashMap<String, ArrayList<String>>();
        Map<String, HashMap<String, String>> idCurriculo_Vertice_Rotulo = new HashMap<String, HashMap<String, String>>();
        Map<String, HashMap<String, ArrayList<Integer>>> idCurriculo_Aresta_Pesos = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
        Map<String, HashSet<String>> idCurriculo_Arestas = new HashMap<String, HashSet<String>>();

        String idCurriculo = null;
        while (scannerDatasetGradeCurricular.hasNextLine()) {
            String linha = scannerDatasetGradeCurricular.nextLine();

            if (linha.substring(0, 1).toUpperCase().equals("G")) {
                idCurriculo = linha.substring(linha.indexOf('|') + 1);

                if (!idCurriculo_Vertices.containsKey(idCurriculo)) {
                    idCurriculo_Vertices.put(idCurriculo, new ArrayList<String>());
                }

                if (!idCurriculo_Vertice_Rotulo.containsKey(idCurriculo)) {
                    idCurriculo_Vertice_Rotulo.put(idCurriculo, new HashMap<String, String>());
                }

                if (!idCurriculo_Aresta_Pesos.containsKey(idCurriculo)) {
                    idCurriculo_Aresta_Pesos.put(idCurriculo, new HashMap<String, ArrayList<Integer>>());
                }

                if (!idCurriculo_Arestas.containsKey(idCurriculo)) {
                    idCurriculo_Arestas.put(idCurriculo, new HashSet<String>());
                }
            } else if (linha.substring(0, 1).toUpperCase().equals("V")) {
                String[] campos = linha.split(Pattern.quote("|"));

                String idVertice = campos[1];
                String rotuloVertice = campos[2];

                if (!idCurriculo_Vertice_Rotulo.get(idCurriculo).containsKey(idVertice)) {
                    idCurriculo_Vertice_Rotulo.get(idCurriculo).put(idVertice, rotuloVertice);
                }

                idCurriculo_Vertices.get(idCurriculo).add(linha);
            } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                String[] campos = linha.split(Pattern.quote("|"));

                String idVerticeOrigem = campos[1];
                String idVerticeDestino = campos[3];

                String arestaComIds = idVerticeOrigem + "," + idVerticeDestino;

                if (!idCurriculo_Aresta_Pesos.get(idCurriculo).containsKey(arestaComIds)) {
                    idCurriculo_Aresta_Pesos.get(idCurriculo).put(arestaComIds, new ArrayList<Integer>());
                }

                idCurriculo_Arestas.get(idCurriculo).add(arestaComIds);
            }
        }

        FileReader fileReaderDatasetHistoricos = new FileReader(diretorioAplicacao + File.separator + DIR_DATASETS + File.separator + DATASET_HISTORICOS_NOME + ".txt");
        Scanner scannerDatasetHistoricos = new Scanner(fileReaderDatasetHistoricos);

        Map<String, ArrayList<String>> idCurriculoAluno_Aresta = new HashMap<String, ArrayList<String>>();

        String idCurriculoAluno = null;
        while (scannerDatasetHistoricos.hasNextLine()) {
            String linha = scannerDatasetHistoricos.nextLine();

            if (linha.substring(0, 1).toUpperCase().equals("G")) {
                String[] campos = linha.split(Pattern.quote("|"));

                idCurriculoAluno = campos[2];

                if (!idCurriculoAluno_Aresta.containsKey(idCurriculoAluno)) {
                    idCurriculoAluno_Aresta.put(idCurriculoAluno, new ArrayList<String>());
                }
            } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                String[] campos = linha.split(Pattern.quote("|"));

                String idVerticeOrigem = campos[1];
                String idVerticeDestino = campos[3];
                int pesoArestaAluno = Integer.parseInt(campos[5]);

                String arestaComIds = idVerticeOrigem + "," + idVerticeDestino;

                //if (pesoArestaAluno > 0) {
                idCurriculoAluno_Aresta.get(idCurriculoAluno).add(arestaComIds);
                idCurriculo_Aresta_Pesos.get(idCurriculoAluno).get(arestaComIds).add(pesoArestaAluno);
                //}               
            }
        }

        FileWriter dataset = this.criarArquivoParaDataset(diretorioAplicacao, DATASET_GRAFO_MEDIANO_NOME, "txt");;
        PrintWriter datasetPrint = new PrintWriter(dataset, true);

        Set<String> idsCurriculos = idCurriculo_Arestas.keySet();
        for (String idCurr : idsCurriculos) {
            if (idCurriculoAluno_Aresta.containsKey(idCurr)) {
                datasetPrint.println("g|" + idCurr);

                Iterator i = idCurriculo_Vertices.get(idCurr).iterator();
                while (i.hasNext()) {
                    String linhaVertice = (String) i.next();
                    datasetPrint.println(linhaVertice);
                }

                Iterator i2 = idCurriculo_Arestas.get(idCurr).iterator();
                while (i2.hasNext()) {
                    String aresta = (String) i2.next();

                    Collections.sort(idCurriculo_Aresta_Pesos.get(idCurr).get(aresta));

                    int n = idCurriculo_Aresta_Pesos.get(idCurr).get(aresta).size();

                    int mediana = 0;
                    if (n % 2 == 0) {//PAR
                        int posicaoValorCentral1 = n / 2;
                        int posicaoValorCentral2 = posicaoValorCentral1 + 1;

                        mediana = (idCurriculo_Aresta_Pesos.get(idCurr).get(aresta).get(posicaoValorCentral1) + idCurriculo_Aresta_Pesos.get(idCurr).get(aresta).get(posicaoValorCentral2)) / 2;
                    } else {//IMPAR
                        int posicaoDaMediana = (n + 1) / 2;

                        mediana = idCurriculo_Aresta_Pesos.get(idCurr).get(aresta).get(posicaoDaMediana);
                    }

                    String[] campos = aresta.split(Pattern.quote(","));

                    String idVerticeOrigem = campos[0];
                    String idVerticeDestino = campos[1];

                    datasetPrint.println("e|" + idVerticeOrigem + "|" + idCurriculo_Vertice_Rotulo.get(idCurr).get(idVerticeOrigem) + "|" + idVerticeDestino + "|" + idCurriculo_Vertice_Rotulo.get(idCurr).get(idVerticeDestino) + "|" + mediana);
                }
            }
        }
    }

    public void criarDatasetGrafoMaximo(String diretorioAplicacao) throws FileNotFoundException, IOException {
        FileReader fileReaderDatasetGradeCurricular = new FileReader(diretorioAplicacao + File.separator + DIR_DATASETS + File.separator + DATASET_GRADE_CURRICULAR_NOME + ".txt");
        Scanner scannerDatasetGradeCurricular = new Scanner(fileReaderDatasetGradeCurricular);

        Map<String, ArrayList<String>> idCurriculo_Vertices = new HashMap<String, ArrayList<String>>();
        Map<String, HashMap<String, String>> idCurriculo_Vertice_Rotulo = new HashMap<String, HashMap<String, String>>();
        Map<String, HashMap<String, ArrayList<Integer>>> idCurriculo_Aresta_Pesos = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
        Map<String, HashSet<String>> idCurriculo_Arestas = new HashMap<String, HashSet<String>>();

        String idCurriculo = null;
        while (scannerDatasetGradeCurricular.hasNextLine()) {
            String linha = scannerDatasetGradeCurricular.nextLine();

            if (linha.substring(0, 1).toUpperCase().equals("G")) {
                idCurriculo = linha.substring(linha.indexOf('|') + 1);

                if (!idCurriculo_Vertices.containsKey(idCurriculo)) {
                    idCurriculo_Vertices.put(idCurriculo, new ArrayList<String>());
                }

                if (!idCurriculo_Vertice_Rotulo.containsKey(idCurriculo)) {
                    idCurriculo_Vertice_Rotulo.put(idCurriculo, new HashMap<String, String>());
                }

                if (!idCurriculo_Aresta_Pesos.containsKey(idCurriculo)) {
                    idCurriculo_Aresta_Pesos.put(idCurriculo, new HashMap<String, ArrayList<Integer>>());
                }

                if (!idCurriculo_Arestas.containsKey(idCurriculo)) {
                    idCurriculo_Arestas.put(idCurriculo, new HashSet<String>());
                }
            } else if (linha.substring(0, 1).toUpperCase().equals("V")) {
                String[] campos = linha.split(Pattern.quote("|"));

                String idVertice = campos[1];
                String rotuloVertice = campos[2];

                if (!idCurriculo_Vertice_Rotulo.get(idCurriculo).containsKey(idVertice)) {
                    idCurriculo_Vertice_Rotulo.get(idCurriculo).put(idVertice, rotuloVertice);
                }

                idCurriculo_Vertices.get(idCurriculo).add(linha);
            } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                String[] campos = linha.split(Pattern.quote("|"));

                String idVerticeOrigem = campos[1];
                String idVerticeDestino = campos[3];

                String arestaComIds = idVerticeOrigem + "," + idVerticeDestino;

                if (!idCurriculo_Aresta_Pesos.get(idCurriculo).containsKey(arestaComIds)) {
                    idCurriculo_Aresta_Pesos.get(idCurriculo).put(arestaComIds, new ArrayList<Integer>());
                }

                idCurriculo_Arestas.get(idCurriculo).add(arestaComIds);
            }
        }

        FileReader fileReaderDatasetHistoricos = new FileReader(diretorioAplicacao + File.separator + DIR_DATASETS + File.separator + DATASET_HISTORICOS_NOME + ".txt");
        Scanner scannerDatasetHistoricos = new Scanner(fileReaderDatasetHistoricos);

        Map<String, ArrayList<String>> idCurriculoAluno_Aresta = new HashMap<String, ArrayList<String>>();

        String idCurriculoAluno = null;
        while (scannerDatasetHistoricos.hasNextLine()) {
            String linha = scannerDatasetHistoricos.nextLine();

            if (linha.substring(0, 1).toUpperCase().equals("G")) {
                String[] campos = linha.split(Pattern.quote("|"));

                idCurriculoAluno = campos[2];

                if (!idCurriculoAluno_Aresta.containsKey(idCurriculoAluno)) {
                    idCurriculoAluno_Aresta.put(idCurriculoAluno, new ArrayList<String>());
                }
            } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                String[] campos = linha.split(Pattern.quote("|"));

                String idVerticeOrigem = campos[1];
                String idVerticeDestino = campos[3];
                int pesoArestaAluno = Integer.parseInt(campos[5]);

                String arestaComIds = idVerticeOrigem + "," + idVerticeDestino;

                //if (pesoArestaAluno > 0) {
                idCurriculoAluno_Aresta.get(idCurriculoAluno).add(arestaComIds);
                idCurriculo_Aresta_Pesos.get(idCurriculoAluno).get(arestaComIds).add(pesoArestaAluno);
                //}               
            }
        }

        FileWriter dataset = this.criarArquivoParaDataset(diretorioAplicacao, DATASET_GRAFO_MAXIMO_NOME, "txt");;
        PrintWriter datasetPrint = new PrintWriter(dataset, true);

        Set<String> idsCurriculos = idCurriculo_Arestas.keySet();
        for (String idCurr : idsCurriculos) {
            if (idCurriculoAluno_Aresta.containsKey(idCurr)) {
                datasetPrint.println("g|" + idCurr);

                Iterator i = idCurriculo_Vertices.get(idCurr).iterator();
                while (i.hasNext()) {
                    String linhaVertice = (String) i.next();
                    datasetPrint.println(linhaVertice);
                }

                Iterator i2 = idCurriculo_Arestas.get(idCurr).iterator();
                while (i2.hasNext()) {
                    String aresta = (String) i2.next();
                    
                    String[] campos = aresta.split(Pattern.quote(","));

                    String idVerticeOrigem = campos[0];
                    String idVerticeDestino = campos[1];

                    datasetPrint.println("e|" + idVerticeOrigem + "|" + idCurriculo_Vertice_Rotulo.get(idCurr).get(idVerticeOrigem) + "|" + idVerticeDestino + "|" + idCurriculo_Vertice_Rotulo.get(idCurr).get(idVerticeDestino) + "|" + Collections.max(idCurriculo_Aresta_Pesos.get(idCurr).get(aresta)));
                }
            }
        }
    }

    public void criarDatasetArestasMaisPesadas(String diretorioAplicacao, String tipoDeDataset, String tipoDeSoma) throws FileNotFoundException, IOException {
        FileReader fileReaderGradeCurricular = new FileReader(diretorioAplicacao + "/" + DIR_DATASETS + "/" + DATASET_GRADE_CURRICULAR_NOME + ".txt");
        Scanner scannerGradeCurrcilar = new Scanner(fileReaderGradeCurricular);

        Map<String, ArrayList<String>> idCurriculo_Vertices = new HashMap<String, ArrayList<String>>();
        Map<String, HashMap<String, String>> idCurriculo_Vertice_Rotulo = new HashMap<String, HashMap<String, String>>();
        Map<String, HashMap<String, Integer>> idCurriculo_Aresta_Peso = new HashMap<String, HashMap<String, Integer>>();
        Map<String, HashSet<String>> idCurriculo_Aresta = new HashMap<String, HashSet<String>>();

        String idCurriculo = null;
        while (scannerGradeCurrcilar.hasNextLine()) {
            String linha = scannerGradeCurrcilar.nextLine();

            if (linha.substring(0, 1).toUpperCase().equals("G")) {
                idCurriculo = linha.substring(linha.indexOf('|') + 1);

                if (!idCurriculo_Vertices.containsKey(idCurriculo)) {
                    idCurriculo_Vertices.put(idCurriculo, new ArrayList<String>());
                }

                if (!idCurriculo_Vertice_Rotulo.containsKey(idCurriculo)) {
                    idCurriculo_Vertice_Rotulo.put(idCurriculo, new HashMap<String, String>());
                }

                if (!idCurriculo_Aresta_Peso.containsKey(idCurriculo)) {
                    idCurriculo_Aresta_Peso.put(idCurriculo, new HashMap<String, Integer>());
                }

                if (!idCurriculo_Aresta.containsKey(idCurriculo)) {
                    idCurriculo_Aresta.put(idCurriculo, new HashSet<String>());
                }
            } else if (linha.substring(0, 1).toUpperCase().equals("V")) {
                String[] campos = linha.split(Pattern.quote("|"));

                String idVertice = campos[1];
                String rotuloVertice = campos[2];

                if (!idCurriculo_Vertice_Rotulo.get(idCurriculo).containsKey(idVertice)) {
                    idCurriculo_Vertice_Rotulo.get(idCurriculo).put(idVertice, rotuloVertice);
                }

                idCurriculo_Vertices.get(idCurriculo).add(linha);
            } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                String[] campos = linha.split(Pattern.quote("|"));

                String idVerticeOrigem = campos[1];
                String idVerticeDestino = campos[3];

                int pesoAresta = 0;//Seto todas as arestas com peso 0 (zero) para depois ir incrementando de acordo com cada histórico

                String arestaComIds = idVerticeOrigem + "," + idVerticeDestino;

                if (!idCurriculo_Aresta_Peso.get(idCurriculo).containsKey(arestaComIds)) {
                    idCurriculo_Aresta_Peso.get(idCurriculo).put(arestaComIds, pesoAresta);
                }

                idCurriculo_Aresta.get(idCurriculo).add(arestaComIds);
            }
        }

        if ("cml".equals(tipoDeDataset)) {
            FileReader fileReaderCaminhosHistoricos = new FileReader(diretorioAplicacao + "/" + DIR_DATASETS + "/" + DATASET_CAMINHOS_HISTORICOS_NOME + ".txt");
            Scanner scannerCaminhosHistoricos = new Scanner(fileReaderCaminhosHistoricos);

            String idCurriculoAluno = null;
            int pesoArestaNovo = 0;
            int pesoArestaGradeCurricular = 0;
            while (scannerCaminhosHistoricos.hasNextLine()) {
                String linha = scannerCaminhosHistoricos.nextLine();

                if (linha.substring(0, 1).toUpperCase().equals("C")) {
                    String[] campos = linha.split(Pattern.quote("|"));

                    idCurriculoAluno = campos[2];
                } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                    String[] campos = linha.split(Pattern.quote("|"));

                    String idVerticeOrigem = campos[1];
                    String idVerticeDestino = campos[3];
                    int pesoArestaAluno = Integer.parseInt(campos[5]);

                    String arestaComIds = idVerticeOrigem + "," + idVerticeDestino;

                    if (idCurriculo_Aresta_Peso.containsKey(idCurriculoAluno)) {
                        if (idCurriculo_Aresta_Peso.get(idCurriculoAluno).containsKey(arestaComIds)) {
                            pesoArestaGradeCurricular = idCurriculo_Aresta_Peso.get(idCurriculoAluno).get(arestaComIds);

                            if ("pesos".equals(tipoDeSoma)) {
                                pesoArestaNovo = pesoArestaGradeCurricular + pesoArestaAluno;
                            } else {
                                pesoArestaNovo = pesoArestaGradeCurricular + 1;
                            }

                            idCurriculo_Aresta_Peso.get(idCurriculoAluno).put(arestaComIds, pesoArestaNovo);
                        }
                    }
                }
            }
        } else {
            FileReader fileReaderHistoricos = new FileReader(diretorioAplicacao + "/" + DIR_DATASETS + "/" + DATASET_HISTORICOS_NOME + ".txt");
            Scanner scannerHistoricos = new Scanner(fileReaderHistoricos);

            String idCurriculoAluno = null;
            while (scannerHistoricos.hasNextLine()) {
                String linha = scannerHistoricos.nextLine();

                if (linha.substring(0, 1).toUpperCase().equals("G")) {
                    String[] campos = linha.split(Pattern.quote("|"));

                    idCurriculoAluno = campos[2];
                } else if (linha.substring(0, 1).toUpperCase().equals("E")) {
                    String[] campos = linha.split(Pattern.quote("|"));

                    String idVerticeOrigem = campos[1];
                    String idVerticeDestino = campos[3];
                    int pesoArestaAluno = Integer.parseInt(campos[5]);

                    String arestaComIds = idVerticeOrigem + "," + idVerticeDestino;

                    if (idCurriculo_Aresta_Peso.containsKey(idCurriculoAluno)) {
                        if (idCurriculo_Aresta_Peso.get(idCurriculoAluno).containsKey(arestaComIds)) {
                            int pesoArestaGradeCurricular = idCurriculo_Aresta_Peso.get(idCurriculoAluno).get(arestaComIds);

                            int pesoArestaNovo = 0;
                            if ("pesos".equals(tipoDeSoma)) {
                                pesoArestaNovo = pesoArestaGradeCurricular + pesoArestaAluno;
                            } else {
                                if (pesoArestaAluno > 0) {
                                    pesoArestaNovo = pesoArestaGradeCurricular + 1;
                                }
                            }

                            idCurriculo_Aresta_Peso.get(idCurriculoAluno).put(arestaComIds, pesoArestaNovo);
                        }
                    }
                }
            }
        }

        FileWriter dataset = this.criarArquivoParaDataset(diretorioAplicacao, DATASET_ARESTAS_MAIS_CARAS_NOME, "txt");
        PrintWriter datasetPrint = new PrintWriter(dataset, true);

        Set<String> idsCurr = idCurriculo_Aresta_Peso.keySet();
        for (String idCurr : idsCurr) {
            datasetPrint.println("g|" + idCurr);

            Iterator i = idCurriculo_Vertices.get(idCurr).iterator();
            while (i.hasNext()) {
                String linhaVertice = (String) i.next();
                datasetPrint.println(linhaVertice);
            }

            Iterator i2 = idCurriculo_Aresta.get(idCurr).iterator();
            while (i2.hasNext()) {
                String aresta = (String) i2.next();

                String[] campos = aresta.split(Pattern.quote(","));

                String idVerticeOrigem = campos[0];
                String idVerticeDestino = campos[1];

                datasetPrint.println("e|" + idVerticeOrigem + "|" + idCurriculo_Vertice_Rotulo.get(idCurr).get(idVerticeOrigem) + "|" + idVerticeDestino + "|" + idCurriculo_Vertice_Rotulo.get(idCurr).get(idVerticeDestino) + "|" + idCurriculo_Aresta_Peso.get(idCurr).get(aresta));
            }
        }
    }

    private FileWriter criarArquivoParaDataset(String diretorio, String nomeDoArquivo, String ext) throws IOException {
        String dirDeDatasetCompleto = diretorio + File.separator + DIR_DATASETS;

        File f = new File(dirDeDatasetCompleto);
        if (!f.exists()) {
            f.mkdir();
            f.setWritable(true);
            f.setReadable(true);
        }

        FileWriter dataSetFile = new FileWriter(f + File.separator + nomeDoArquivo + "." + ext);

        return dataSetFile;
    }

    public String getValorQueMaisSeRepeteNoMap(Map<String, ArrayList<String>> mapa, String key) {

        //PEGO O ARRAY DE ACORDO COM A KEY INFORMADA
        ArrayList<String> array = mapa.get(key);

        //CRIO UM ARRAY AUXILIAR COM OS MESMOS ITENS DO ARRAY DE CIMA, MAS SEM REPETICAO 
        Set<String> arrayAux = new HashSet<>(array);

        //VARIAVEL PARA GUARDAR O ITEM DE MAIOR FREQUENCIA
        String itemMaiorFrequencia = "";

        //VARIAVEL PARA CONTROLAR A MAIOR FREQUENCIA CORRENTE         
        int maiorFrequencia = 0;

        //FOR NO ARRAY QUE TEM OS ITENS NAO REPETIDOS        
        for (String item : arrayAux) {

            //PEGO A FREQEUNCIA DOS ITEMS, PASSANDO O ARRAY COM ITEMS REPETIDOS
            int frequenciaCorrente = Collections.frequency(array, item);

            //VERIFICO SE A FREQUENCIA CORRENTE É MAIOR QUE A FREQUENCIA ANTERIOR
            if (frequenciaCorrente > maiorFrequencia) {
                /*
                 * SE SIM, ENTAO FACO UMA SUBSTITUICAO ATÉ ENCONTRAR O ITEM COM A MAIOR FREQUENCIA
                 */
                maiorFrequencia = frequenciaCorrente;
                itemMaiorFrequencia = item;
            }
        }

        return itemMaiorFrequencia;
    }
}
