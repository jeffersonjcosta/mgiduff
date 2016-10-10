package br.com.graphs.actions;

import br.com.graphs.algorithms.BellmanFord;
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

public final class CreateDatasetAction {

    private final String DIR_DATASETS = "datasets_ijis";
    private final String DATASET_FLOWCHART_NAME = "datasetFlowchart";
    private final String DATASET_HISTORICAL_NAME = "datasetHistorical";
    private final String DATASET_MEAN_GRAPH_NAME = "datasetMeanGraph";
    private final String DATASET_MEDIAN_GRAPH_NAME = "datasetMedianGraph";
    private final String DATASET_MAXIMUM_GRAPH_NAME = "datasetMaximumGraph";
    private final String DATASET_PATHS_FLOWCHART_NAME = "datasetPathsFlowchart";
    private final String DATASET_PATHS_HISTORICAL_NAME = "datasetPathsHistorical";
    private final String DATASET_PATHS_MEAN_GRAPH_NAME = "datasetPathsMeanGraph";
    private final String DATASET_PATHS_MEDIAN_GRAPH_NAME = "datasetPathsMedianGraph";
    private final String DATASET_PATHS_MAXIMUM_GRAPH_NAME = "datasetPathsMaximumGraph ";
    private final String DATASET_HEAVIEST_EDGES_NAME = "datasetHeaviestsEdges";

    public void createDatasetFlowchart(File disciplinesAndPrerequisites, String dirApp) throws FileNotFoundException, IOException {

        FileWriter datasetFlowchart = this.createFileForDataset(dirApp, DATASET_FLOWCHART_NAME, "txt");
        PrintWriter datasetFlowchartPrint = new PrintWriter(datasetFlowchart, true);
        FileReader fileReaderDis = new FileReader(disciplinesAndPrerequisites.getAbsolutePath());
        Scanner scannerDis = new Scanner(fileReaderDis);

        Map<String, ArrayList<String>> idCurriculum_Disciplines = new HashMap<String, ArrayList<String>>();
        Map<String, ArrayList<String>> idCurriculum_Prerequisites = new HashMap<String, ArrayList<String>>();
        Map<String, HashSet<String>> idCurriculum_Periods = new HashMap<String, HashSet<String>>();
        Map<String, TreeMap<Integer, HashSet<String>>> idCurriculum_Period_Disciplines = new HashMap<String, TreeMap<Integer, HashSet<String>>>();
        Map<String, HashMap<String, HashSet<String>>> idCurriculum_Discipline_Prerequisites = new HashMap<String, HashMap<String, HashSet<String>>>();
        Map<String, Integer> idSubject_NumericalCode = new HashMap<String, Integer>();
        Map<String, HashSet<String>> idCurriculum_DisciplinesWithoutPrerequisites = new HashMap<String, HashSet<String>>();
        Map<String, HashMap<Integer, String>> idCurriculum_IdVertice_VerticeLabel = new HashMap<String, HashMap<Integer, String>>();

        String weightEdges = "1";

        String idCurr = null;
        String idDisc = null;
        String period = null;
        String idPrerequisite = null;
        String periodPrerequisite = null;

        while (scannerDis.hasNextLine()) {
            String line = scannerDis.nextLine();

            String[] fields = line.split(";");

            idCurr = fields[0];
            idDisc = fields[1];
            period = fields[2];
            idPrerequisite = fields[5];
            periodPrerequisite = fields[6];

            if ("-".equals(periodPrerequisite)) {
                periodPrerequisite = "0";
            }

            if (!idCurriculum_Disciplines.containsKey(idCurr)) {
                idCurriculum_Disciplines.put(idCurr, new ArrayList<String>());
            }

            if (!idCurriculum_Prerequisites.containsKey(idCurr)) {
                idCurriculum_Prerequisites.put(idCurr, new ArrayList<String>());
            }

            if (!idCurriculum_Periods.containsKey(idCurr)) {
                idCurriculum_Periods.put(idCurr, new HashSet<String>());
            }

            if (!idCurriculum_Period_Disciplines.containsKey(idCurr)) {
                idCurriculum_Period_Disciplines.put(idCurr, new TreeMap<Integer, HashSet<String>>());
            }

            if (!idCurriculum_Discipline_Prerequisites.containsKey(idCurr)) {
                idCurriculum_Discipline_Prerequisites.put(idCurr, new HashMap<String, HashSet<String>>());
            }

            if (!idCurriculum_Period_Disciplines.get(idCurr).containsKey(Integer.parseInt(period))) {
                idCurriculum_Period_Disciplines.get(idCurr).put(Integer.parseInt(period), new HashSet<String>());
            }

            if (!idCurriculum_Period_Disciplines.get(idCurr).containsKey(Integer.parseInt(periodPrerequisite))) {
                idCurriculum_Period_Disciplines.get(idCurr).put(Integer.parseInt(periodPrerequisite), new HashSet<String>());
            }

            if (!idCurriculum_Discipline_Prerequisites.get(idCurr).containsKey(idDisc)) {
                idCurriculum_Discipline_Prerequisites.get(idCurr).put(idDisc, new HashSet<String>());
            }

            if (!idCurriculum_DisciplinesWithoutPrerequisites.containsKey(idCurr)) {
                idCurriculum_DisciplinesWithoutPrerequisites.put(idCurr, new HashSet<String>());
            }

            if (!idCurriculum_IdVertice_VerticeLabel.containsKey(idCurr)) {
                idCurriculum_IdVertice_VerticeLabel.put(idCurr, new HashMap<Integer, String>());
            }

            idCurriculum_Disciplines.get(idCurr).add(idDisc);
            idCurriculum_Prerequisites.get(idCurr).add(idPrerequisite);
            idCurriculum_Periods.get(idCurr).add(period);
            idCurriculum_Periods.get(idCurr).add(periodPrerequisite);
            idCurriculum_Periods.get(idCurr).add("0");

            if (!idCurriculum_Period_Disciplines.get(idCurr).containsKey(0)) {
                idCurriculum_Period_Disciplines.get(idCurr).put(0, new HashSet<String>());
            }

            idCurriculum_Period_Disciplines.get(idCurr).get(Integer.parseInt(period)).add(idDisc);

            idCurriculum_Period_Disciplines.get(idCurr).get(0).add("Admission");
            idCurriculum_Period_Disciplines.get(idCurr).get(0).add("Conclusion");

            idCurriculum_Discipline_Prerequisites.get(idCurr).get(idDisc).add(idPrerequisite);

            if (!idCurriculum_Period_Disciplines.get(idCurr).get(Integer.parseInt(periodPrerequisite)).contains(idPrerequisite)) {
                idCurriculum_Period_Disciplines.get(idCurr).get(Integer.parseInt(periodPrerequisite)).add(idPrerequisite);
                idCurriculum_DisciplinesWithoutPrerequisites.get(idCurr).add(idPrerequisite);
            }
        }

        Set<String> idsOfCurriculums = idCurriculum_Prerequisites.keySet();
        for (String idCurriculum : idsOfCurriculums) {
            if (idCurriculum != null) {
                datasetFlowchartPrint.println("g|" + idCurriculum);

                Iterator n = idCurriculum_Periods.get(idCurriculum).iterator();
                int x = 0;
                while (n.hasNext()) {
                    String per = (String) n.next();

                    int p = Integer.parseInt(per);

                    Iterator it = idCurriculum_Period_Disciplines.get(idCurriculum).get(p).iterator();
                    while (it.hasNext()) {
                        String idSubject = (String) it.next();
                        datasetFlowchartPrint.println("v|" + x + "|" + idSubject + "|" + p);
                        idSubject_NumericalCode.put(idSubject, x);

                        if (!idCurriculum_IdVertice_VerticeLabel.get(idCurriculum).containsKey(x)) {
                            idCurriculum_IdVertice_VerticeLabel.get(idCurriculum).put(x, idSubject);
                        }

                        x++;
                    }
                }

                Iterator w = idCurriculum_Periods.get(idCurriculum).iterator();
                while (w.hasNext()) {
                    String per = (String) w.next();

                    int p = Integer.parseInt(per);

                    Iterator it2 = idCurriculum_Period_Disciplines.get(idCurriculum).get(p).iterator();
                    while (it2.hasNext()) {
                        String idSubject = (String) it2.next();

                        if (idCurriculum_Discipline_Prerequisites.get(idCurriculum).containsKey(idSubject)) {
                            Iterator it3 = idCurriculum_Discipline_Prerequisites.get(idCurriculum).get(idSubject).iterator();
                            while (it3.hasNext()) {
                                String idPreReq = (String) it3.next();
                                datasetFlowchartPrint.println("e|" + idSubject_NumericalCode.get(idPreReq) + "|" + idPreReq + "|" + idSubject_NumericalCode.get(idSubject) + "|" + idSubject + "|" + weightEdges);
                            }

                            if (!idCurriculum_Prerequisites.get(idCurriculum).contains(idSubject) && idCurriculum_Period_Disciplines.get(idCurriculum).get(p).contains(idSubject)) {
                                if (!"Conclusion".equals(idSubject)) {
                                    datasetFlowchartPrint.println("e|" + idSubject_NumericalCode.get(idSubject) + "|" + idSubject + "|" + idSubject_NumericalCode.get("Conclusion") + "|Conclusion|" + weightEdges);
                                }
                            }
                        }
                    }
                }

                Iterator i3 = idCurriculum_DisciplinesWithoutPrerequisites.get(idCurriculum).iterator();
                while (i3.hasNext()) {
                    String idDiscSemPreRequisito = (String) i3.next();
                    if (idCurriculum_Discipline_Prerequisites.get(idCurriculum).get(idDiscSemPreRequisito) == null) {
                        datasetFlowchartPrint.println("e|" + idSubject_NumericalCode.get("Admission") + "|Admission|" + idSubject_NumericalCode.get(idDiscSemPreRequisito) + "|" + idDiscSemPreRequisito + "|" + weightEdges);
                    }
                }
            }
        }

        datasetFlowchartPrint.flush();

    }

    public void createDatasetHistorical(File equivalences, File accompaniments, File historical, String dirApp) throws FileNotFoundException, IOException {
        FileReader fileReaderEquivalences = new FileReader(equivalences.getAbsolutePath());
        FileReader fileReaderAccompaniments = new FileReader(accompaniments.getAbsolutePath());
        FileReader fileReaderHistorical = new FileReader(historical.getAbsolutePath());
        FileReader fileReaderDatasetFlowchart = new FileReader(dirApp + File.separator + "datasets_ijis" + File.separator + "datasetFlowchart.txt");

        Scanner scannerEquivalences = new Scanner(fileReaderEquivalences);
        Scanner scannerAccompaniments = new Scanner(fileReaderAccompaniments);
        Scanner scannerHistorical = new Scanner(fileReaderHistorical);
        Scanner scannerDatasetFlowchart = new Scanner(fileReaderDatasetFlowchart);

        Map<String, ArrayList<String>> idCurriculum_Vertices = new HashMap<String, ArrayList<String>>();
        Map<String, HashSet<String>> idCurriculum_Edges = new HashMap<String, HashSet<String>>();

        String idCurr = null;
        while (scannerDatasetFlowchart.hasNextLine()) {
            String line = scannerDatasetFlowchart.nextLine();

            if (line.substring(0, 1).toUpperCase().equals("G")) {
                idCurr = line.substring(line.indexOf('|') + 1);

                if (!idCurriculum_Vertices.containsKey(idCurr)) {
                    idCurriculum_Vertices.put(idCurr, new ArrayList<String>());
                }
                if (!idCurriculum_Edges.containsKey(idCurr)) {
                    idCurriculum_Edges.put(idCurr, new HashSet<String>());
                }
            } else if (line.substring(0, 1).toUpperCase().equals("V")) {
                idCurriculum_Vertices.get(idCurr).add(line);
            } else if (line.substring(0, 1).toUpperCase().equals("E")) {
                idCurriculum_Edges.get(idCurr).add(line);
            }
        }

        Map<String, HashSet<String>> idStudent_Curriculums = new HashMap<String, HashSet<String>>();
        Map<String, ArrayList<String>> idStudent_Status = new HashMap<String, ArrayList<String>>();
        Map<String, ArrayList<String>> mapAidForCountTheCurriculumThatMostRepeat = new HashMap<String, ArrayList<String>>();

        String idStudentAccompaniment = null;
        String status = null;
        String curriculum1 = null;

        while (scannerAccompaniments.hasNextLine()) {
            String line = scannerAccompaniments.nextLine();
            String[] fields = line.split(";");
            
            idStudentAccompaniment = fields[0];
            status = fields[1];
            curriculum1 = fields[4];

            status = status.replace("\"", "");
            curriculum1 = curriculum1.replace("\"", "");

            if (!idStudent_Curriculums.containsKey(idStudentAccompaniment)) {
                idStudent_Curriculums.put(idStudentAccompaniment, new HashSet<String>());
            }

            if (!idStudent_Status.containsKey(idStudentAccompaniment)) {
                idStudent_Status.put(idStudentAccompaniment, new ArrayList<String>());
            }

            if (!mapAidForCountTheCurriculumThatMostRepeat.containsKey(idStudentAccompaniment)) {
                mapAidForCountTheCurriculumThatMostRepeat.put(idStudentAccompaniment, new ArrayList<String>());
            }

            if (!curriculum1.isEmpty()) {
                mapAidForCountTheCurriculumThatMostRepeat.get(idStudentAccompaniment).add(curriculum1);
            }

            idStudent_Status.get(idStudentAccompaniment).add(status);
        }

        Map<String, HashSet<String>> idStudent_NoRepeatCoursesTaken = new HashMap<String, HashSet<String>>();
        Map<String, HashSet<String>> idStudent_NoRepeatCoursesTakenJustForIterator = new HashMap<String, HashSet<String>>();
        Map<String, ArrayList<String>> idStudent_SubjectsTakenWithRepetition = new HashMap<String, ArrayList<String>>();

        String idStudentHistorical = null;
        String idSubjectStudent = null;
        String disciplineSituation = null;
        while (scannerHistorical.hasNextLine()) {
            String line = scannerHistorical.nextLine();

            String[] fields = line.split(";");
                      
            idStudentHistorical = fields[0];
            idSubjectStudent = fields[2];
            disciplineSituation = fields[6];

            if (!idStudent_NoRepeatCoursesTaken.containsKey(idStudentHistorical)) {
                idStudent_NoRepeatCoursesTaken.put(idStudentHistorical, new HashSet<String>());
            }

            if (!idStudent_NoRepeatCoursesTakenJustForIterator.containsKey(idStudentHistorical)) {
                idStudent_NoRepeatCoursesTakenJustForIterator.put(idStudentHistorical, new HashSet<String>());
            }

            if (!idStudent_SubjectsTakenWithRepetition.containsKey(idStudentHistorical)) {
                idStudent_SubjectsTakenWithRepetition.put(idStudentHistorical, new ArrayList<String>());
            }

            idSubjectStudent = idSubjectStudent.replace("\"", "");

            //C = disciplines locked by the student were not routed, but are registered to control locking limit
            //D = considered approved (the student attended the course at another institution and could dispense at UFF)
            //S = subjects that the student signed up but the teacher did not release a statement. Considered disapproval for registration cancellation purposes for 4 reproaches, etc., but not for the CR
            if (!"\"C\"".equals(disciplineSituation)) {
                idStudent_NoRepeatCoursesTaken.get(idStudentHistorical).add(idSubjectStudent);
                idStudent_NoRepeatCoursesTakenJustForIterator.get(idStudentHistorical).add(idSubjectStudent);
                idStudent_SubjectsTakenWithRepetition.get(idStudentHistorical).add(idSubjectStudent);
            }
        }

        Map<String, String> idEquivalence_Discipline = new HashMap<String, String>();

        String idDiscCurr;
        String idDiscEquivalent;
        while (scannerEquivalences.hasNext()) {
            String line = scannerEquivalences.nextLine();
            String[] fields = line.split(";");

            idDiscCurr = fields[1];
            idDiscEquivalent = fields[2];

            idEquivalence_Discipline.put(idDiscEquivalent, idDiscCurr);
        }

        FileWriter datasetHistorical = this.createFileForDataset(dirApp, DATASET_HISTORICAL_NAME, "txt");
        PrintWriter datasetHistoricalPrint = new PrintWriter(datasetHistorical, true);
        String idCurriculumStudent = null;
        Set<String> idsOfStudents = idStudent_Curriculums.keySet();
        for (String idStudent : idsOfStudents) {
            if (idStudent != null) {
                if (idStudent_Status.get(idStudent).contains("Formado")) {
                    if (idStudent_NoRepeatCoursesTaken.containsKey(idStudent)) {
                        idStudent_NoRepeatCoursesTaken.get(idStudent).add("Admission");
                        idStudent_NoRepeatCoursesTaken.get(idStudent).add("Conclusion");

                        idStudent_SubjectsTakenWithRepetition.get(idStudent).add("Admission");
                        idStudent_SubjectsTakenWithRepetition.get(idStudent).add("Conclusion");
                    }

                    if (idStudent_NoRepeatCoursesTakenJustForIterator.containsKey(idStudent)) {
                        Iterator i = idStudent_NoRepeatCoursesTakenJustForIterator.get(idStudent).iterator();
                        while (i.hasNext()) {
                            String idDiscStudent = (String) i.next();
                            if (idEquivalence_Discipline.containsKey(idDiscStudent)) {
                                idStudent_NoRepeatCoursesTaken.get(idStudent).add(idEquivalence_Discipline.get(idDiscStudent));
                                idStudent_SubjectsTakenWithRepetition.get(idStudent).add(idEquivalence_Discipline.get(idDiscStudent));
                            }
                        }
                    }

                    idStudent_Curriculums.get(idStudent).add(this.getValueMoreRepeatsInMap(mapAidForCountTheCurriculumThatMostRepeat, idStudent));

                    Iterator iter1 = idStudent_Curriculums.get(idStudent).iterator();
                    while (iter1.hasNext()) {
                        idCurriculumStudent = (String) iter1.next();

                        if (idCurriculum_Edges.containsKey(idCurriculumStudent)) {

                            datasetHistoricalPrint.println("g|" + idStudent + "|" + idCurriculumStudent);

                            Iterator iter2 = idCurriculum_Vertices.get(idCurriculumStudent).iterator();
                            while (iter2.hasNext()) {
                                String lineVertice = (String) iter2.next();

                                datasetHistoricalPrint.println(lineVertice);
                            }
                            Iterator iter3 = idCurriculum_Edges.get(idCurriculumStudent).iterator();
                            while (iter3.hasNext()) {
                                String lineEdge = (String) iter3.next();

                                String[] fields = lineEdge.split(Pattern.quote("|"));
                                String idVerticeSource = fields[1];
                                String prerequisiteCode = fields[2];
                                String idVerticeTarget = fields[3];
                                String disciplineCode = fields[4];
                                String weightEdge = fields[5];

                                weightEdge = Integer.toString(Collections.frequency(idStudent_SubjectsTakenWithRepetition.get(idStudent), prerequisiteCode));

                                if (!idStudent_NoRepeatCoursesTaken.get(idStudent).contains(prerequisiteCode)) {
                                    weightEdge = "0";
                                }

                                datasetHistoricalPrint.println("e|" + idVerticeSource + "|" + prerequisiteCode + "|" + idVerticeTarget + "|" + disciplineCode + "|" + weightEdge);
                            }
                            datasetHistoricalPrint.flush();
                        }
                    }
                }
            }
        }
    }

    public void createDatasetLongestsPaths(String dirApp, String type) throws FileNotFoundException, IOException {
        FileWriter dataset = null;
        PrintWriter datasetPrint = null;
        FileReader fileReader = null;
        Scanner scanner = null;

        if ("flowchart".equals(type)) {
            dataset = this.createFileForDataset(dirApp, DATASET_PATHS_FLOWCHART_NAME, "txt");
            datasetPrint = new PrintWriter(dataset, true);
            fileReader = new FileReader(dirApp + "/" + DIR_DATASETS + "/" + DATASET_FLOWCHART_NAME + ".txt");
            scanner = new Scanner(fileReader);
        } else if ("historical".equals(type)) {
            dataset = this.createFileForDataset(dirApp, DATASET_PATHS_HISTORICAL_NAME, "txt");
            datasetPrint = new PrintWriter(dataset, true);
            fileReader = new FileReader(dirApp + "/" + DIR_DATASETS + "/" + DATASET_HISTORICAL_NAME + ".txt");
            scanner = new Scanner(fileReader);
        } else if ("meanGraph".equals(type)) {
            dataset = this.createFileForDataset(dirApp, DATASET_PATHS_MEAN_GRAPH_NAME, "txt");
            datasetPrint = new PrintWriter(dataset, true);
            fileReader = new FileReader(dirApp + "/" + DIR_DATASETS + "/" + DATASET_MEAN_GRAPH_NAME + ".txt");
            scanner = new Scanner(fileReader);
        } else if ("medianGraph".equals(type)) {
            dataset = this.createFileForDataset(dirApp, DATASET_PATHS_MEDIAN_GRAPH_NAME, "txt");
            datasetPrint = new PrintWriter(dataset, true);
            fileReader = new FileReader(dirApp + "/" + DIR_DATASETS + "/" + DATASET_MEDIAN_GRAPH_NAME + ".txt");
            scanner = new Scanner(fileReader);
        } else if ("maximumGraph".equals(type)) {
            dataset = this.createFileForDataset(dirApp, DATASET_PATHS_MAXIMUM_GRAPH_NAME, "txt");
            datasetPrint = new PrintWriter(dataset, true);
            fileReader = new FileReader(dirApp + "/" + DIR_DATASETS + "/" + DATASET_MAXIMUM_GRAPH_NAME + ".txt");
            scanner = new Scanner(fileReader);
        }

        Map<String, HashMap<Integer, String>> idGraph_idVertice_LabelVertice = new HashMap<String, HashMap<Integer, String>>();
        Map<String, HashMap<String, String>> idGraph_Edge_Weight = new HashMap<String, HashMap<String, String>>();
        String idGraphAux = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.substring(0, 1).toUpperCase().equals("G")) {
                idGraphAux = line.substring(line.indexOf('|') + 1);

                if (!idGraph_idVertice_LabelVertice.containsKey(idGraphAux)) {
                    idGraph_idVertice_LabelVertice.put(idGraphAux, new HashMap<Integer, String>());
                }

                if (!idGraph_Edge_Weight.containsKey(idGraphAux)) {
                    idGraph_Edge_Weight.put(idGraphAux, new HashMap<String, String>());
                }
            } else if (line.substring(0, 1).toUpperCase().equals("V")) {
                String[] fields = line.split(Pattern.quote("|"));

                String idVertice = fields[1];
                String verticeLabel = fields[2];

                int idVer = Integer.parseInt(idVertice);

                if (!idGraph_idVertice_LabelVertice.get(idGraphAux).containsKey(idVer)) {
                    idGraph_idVertice_LabelVertice.get(idGraphAux).put(idVer, verticeLabel);
                }
            } else if (line.substring(0, 1).toUpperCase().equals("E")) {
                String[] fields = line.split(Pattern.quote("|"));

                String idVerticeSource = fields[1];
                //String verticeLabelOrigem = fields[2];
                String idVerticeTarget = fields[3];
                //String verticeLabelDestino = fields[4];
                String weightEdge = fields[5];

                String edgeWithIds = idVerticeSource + "," + idVerticeTarget;

                if (!idGraph_Edge_Weight.get(idGraphAux).containsKey(edgeWithIds)) {
                    idGraph_Edge_Weight.get(idGraphAux).put(edgeWithIds, weightEdge);
                }
            }
        }

        Map<String, ArrayList<Integer>> idGraph_Path = new HashMap<String, ArrayList<Integer>>();      
        BellmanFord b = new BellmanFord();
        idGraph_Path = b.findLongestPathWithBellmanFord(dirApp, type);

        Set<String> idsGraphs = idGraph_Path.keySet();
        for (String idG : idsGraphs) {

            int pathSize = idGraph_Path.get(idG).size();

            datasetPrint.print("c|" + idG + "|");
            for (int t = (pathSize - 1); t >= 0; t--) {
                datasetPrint.print(idGraph_Path.get(idG).get(t));
                if (idGraph_Path.get(idG).get(t) != 1) {
                    datasetPrint.print("-");
                }
            }

            datasetPrint.println();

            for (int t = (pathSize - 1); t >= 0; t--) {
                datasetPrint.println("v|" + idGraph_Path.get(idG).get(t) + "|" + idGraph_idVertice_LabelVertice.get(idG).get(idGraph_Path.get(idG).get(t)));
            }

            for (int t = (pathSize - 1); t >= 0; t--) {
                if (t < pathSize - 1) {
                    datasetPrint.println(
                            "e|"
                            + idGraph_Path.get(idG).get(t + 1)
                            + "|"
                            + idGraph_idVertice_LabelVertice.get(idG).get(idGraph_Path.get(idG).get(t + 1))
                            + "|"
                            + idGraph_Path.get(idG).get(t)
                            + "|"
                            + idGraph_idVertice_LabelVertice.get(idG).get(idGraph_Path.get(idG).get(t))
                            + "|"
                            + idGraph_Edge_Weight.get(idG).get(idGraph_Path.get(idG).get(t + 1) + "," + idGraph_Path.get(idG).get(t))
                    );
                }
            }
        }
    }

    public void createDataSetMeanGraph(String dirApp) throws FileNotFoundException, IOException {
        FileReader fileReaderDatasetFlowchart = new FileReader(dirApp + File.separator + DIR_DATASETS + File.separator + DATASET_FLOWCHART_NAME + ".txt");
        Scanner scannerDatasetFlowchart = new Scanner(fileReaderDatasetFlowchart);

        Map<String, ArrayList<String>> idCurriculum_Vertices = new HashMap<String, ArrayList<String>>();
        Map<String, HashMap<String, String>> idCurriculum_Vertice_Label = new HashMap<String, HashMap<String, String>>();
        Map<String, HashMap<String, Integer>> idCurriculum_Edge_Weight = new HashMap<String, HashMap<String, Integer>>();
        Map<String, HashSet<String>> idCurriculum_Edges = new HashMap<String, HashSet<String>>();

        String idCurriculum = null;
        while (scannerDatasetFlowchart.hasNextLine()) {
            String line = scannerDatasetFlowchart.nextLine();

            if (line.substring(0, 1).toUpperCase().equals("G")) {
                idCurriculum = line.substring(line.indexOf('|') + 1);

                if (!idCurriculum_Vertices.containsKey(idCurriculum)) {
                    idCurriculum_Vertices.put(idCurriculum, new ArrayList<String>());
                }

                if (!idCurriculum_Vertice_Label.containsKey(idCurriculum)) {
                    idCurriculum_Vertice_Label.put(idCurriculum, new HashMap<String, String>());
                }

                if (!idCurriculum_Edge_Weight.containsKey(idCurriculum)) {
                    idCurriculum_Edge_Weight.put(idCurriculum, new HashMap<String, Integer>());
                }

                if (!idCurriculum_Edges.containsKey(idCurriculum)) {
                    idCurriculum_Edges.put(idCurriculum, new HashSet<String>());
                }
            } else if (line.substring(0, 1).toUpperCase().equals("V")) {
                String[] fields = line.split(Pattern.quote("|"));

                String idVertice = fields[1];
                String verticeLabel = fields[2];

                if (!idCurriculum_Vertice_Label.get(idCurriculum).containsKey(idVertice)) {
                    idCurriculum_Vertice_Label.get(idCurriculum).put(idVertice, verticeLabel);
                }

                idCurriculum_Vertices.get(idCurriculum).add(line);
            } else if (line.substring(0, 1).toUpperCase().equals("E")) {
                String[] fields = line.split(Pattern.quote("|"));

                String idVerticeSource = fields[1];
                String idVerticeTarget = fields[3];

                int weightEdge = 0;//Seto todas as edges com weight 0 (zero) para depois ir incrementando de acordo com cada histórico

                String edgeWithIds = idVerticeSource + "," + idVerticeTarget;

                if (!idCurriculum_Edge_Weight.get(idCurriculum).containsKey(edgeWithIds)) {
                    idCurriculum_Edge_Weight.get(idCurriculum).put(edgeWithIds, weightEdge);
                }

                idCurriculum_Edges.get(idCurriculum).add(edgeWithIds);
            }
        }

        FileReader fileReaderDatasetHistorical = new FileReader(dirApp + File.separator + DIR_DATASETS + File.separator + DATASET_HISTORICAL_NAME + ".txt");
        Scanner scannerDatasetHistorical = new Scanner(fileReaderDatasetHistorical);

        Map<String, ArrayList<String>> idCurriculumStudent_Edge = new HashMap<String, ArrayList<String>>();

        String idCurriculumStudent = null;
        while (scannerDatasetHistorical.hasNextLine()) {
            String line = scannerDatasetHistorical.nextLine();

            if (line.substring(0, 1).toUpperCase().equals("G")) {
                String[] fields = line.split(Pattern.quote("|"));

                idCurriculumStudent = fields[2];

                if (!idCurriculumStudent_Edge.containsKey(idCurriculumStudent)) {
                    idCurriculumStudent_Edge.put(idCurriculumStudent, new ArrayList<String>());
                }
            } else if (line.substring(0, 1).toUpperCase().equals("E")) {
                String[] fields = line.split(Pattern.quote("|"));

                String idVerticeSource = fields[1];
                String idVerticeTarget = fields[3];
                int weightEdgeStudent = Integer.parseInt(fields[5]);

                String edgeWithIds = idVerticeSource + "," + idVerticeTarget;

                if (weightEdgeStudent > 0) {
                    idCurriculumStudent_Edge.get(idCurriculumStudent).add(edgeWithIds);
                }

                if (idCurriculum_Edge_Weight.get(idCurriculumStudent).containsKey(edgeWithIds)) {
                    int weightEdgeNew = weightEdgeStudent + idCurriculum_Edge_Weight.get(idCurriculumStudent).get(edgeWithIds);
                    idCurriculum_Edge_Weight.get(idCurriculumStudent).put(edgeWithIds, weightEdgeNew);
                }
            }
        }

        FileWriter dataset = this.createFileForDataset(dirApp, DATASET_MEAN_GRAPH_NAME, "txt");;
        PrintWriter datasetPrint = new PrintWriter(dataset, true);

        Set<String> idsCurriculums = idCurriculum_Edges.keySet();
        for (String idCurr : idsCurriculums) {
            if (idCurriculumStudent_Edge.containsKey(idCurr)) {
                datasetPrint.println("g|" + idCurr);

                Iterator i = idCurriculum_Vertices.get(idCurr).iterator();
                while (i.hasNext()) {
                    String lineVertice = (String) i.next();
                    datasetPrint.println(lineVertice);
                }

                Iterator i2 = idCurriculum_Edges.get(idCurr).iterator();
                while (i2.hasNext()) {
                    String edge = (String) i2.next();

                    double numberOfStudentsWhoAttendedEdge = Collections.frequency(idCurriculumStudent_Edge.get(idCurr), edge);
                    double totalWeightEdge = idCurriculum_Edge_Weight.get(idCurr).get(edge);

                    double averageEdge = 0;
                    if (numberOfStudentsWhoAttendedEdge > 0) {
                        averageEdge = totalWeightEdge / numberOfStudentsWhoAttendedEdge;
                    }

                    String[] fields = edge.split(Pattern.quote(","));

                    String idVerticeSource = fields[0];
                    String idVerticeTarget = fields[1];

                    datasetPrint.println("e|" + idVerticeSource + "|" + idCurriculum_Vertice_Label.get(idCurr).get(idVerticeSource) + "|" + idVerticeTarget + "|" + idCurriculum_Vertice_Label.get(idCurr).get(idVerticeTarget) + "|" + String.format("%.2f", averageEdge));
                }
            }
        }
    }

    public void createDataSetMedianGraph(String dirApp) throws FileNotFoundException, IOException {
        FileReader fileReaderDatasetFlowchart = new FileReader(dirApp + File.separator + DIR_DATASETS + File.separator + DATASET_FLOWCHART_NAME + ".txt");
        Scanner scannerDatasetFlowchart = new Scanner(fileReaderDatasetFlowchart);

        Map<String, ArrayList<String>> idCurriculum_Vertices = new HashMap<String, ArrayList<String>>();
        Map<String, HashMap<String, String>> idCurriculum_Vertice_Label = new HashMap<String, HashMap<String, String>>();
        Map<String, HashMap<String, ArrayList<Integer>>> idCurriculum_Edge_Weights = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
        Map<String, HashSet<String>> idCurriculum_Edges = new HashMap<String, HashSet<String>>();

        String idCurriculum = null;
        while (scannerDatasetFlowchart.hasNextLine()) {
            String line = scannerDatasetFlowchart.nextLine();

            if (line.substring(0, 1).toUpperCase().equals("G")) {
                idCurriculum = line.substring(line.indexOf('|') + 1);

                if (!idCurriculum_Vertices.containsKey(idCurriculum)) {
                    idCurriculum_Vertices.put(idCurriculum, new ArrayList<String>());
                }

                if (!idCurriculum_Vertice_Label.containsKey(idCurriculum)) {
                    idCurriculum_Vertice_Label.put(idCurriculum, new HashMap<String, String>());
                }

                if (!idCurriculum_Edge_Weights.containsKey(idCurriculum)) {
                    idCurriculum_Edge_Weights.put(idCurriculum, new HashMap<String, ArrayList<Integer>>());
                }

                if (!idCurriculum_Edges.containsKey(idCurriculum)) {
                    idCurriculum_Edges.put(idCurriculum, new HashSet<String>());
                }
            } else if (line.substring(0, 1).toUpperCase().equals("V")) {
                String[] fields = line.split(Pattern.quote("|"));

                String idVertice = fields[1];
                String verticeLabel = fields[2];

                if (!idCurriculum_Vertice_Label.get(idCurriculum).containsKey(idVertice)) {
                    idCurriculum_Vertice_Label.get(idCurriculum).put(idVertice, verticeLabel);
                }

                idCurriculum_Vertices.get(idCurriculum).add(line);
            } else if (line.substring(0, 1).toUpperCase().equals("E")) {
                String[] fields = line.split(Pattern.quote("|"));

                String idVerticeSource = fields[1];
                String idVerticeTarget = fields[3];

                String edgeWithIds = idVerticeSource + "," + idVerticeTarget;

                if (!idCurriculum_Edge_Weights.get(idCurriculum).containsKey(edgeWithIds)) {
                    idCurriculum_Edge_Weights.get(idCurriculum).put(edgeWithIds, new ArrayList<Integer>());
                }

                idCurriculum_Edges.get(idCurriculum).add(edgeWithIds);
            }
        }

        FileReader fileReaderDatasetHistorical = new FileReader(dirApp + File.separator + DIR_DATASETS + File.separator + DATASET_HISTORICAL_NAME + ".txt");
        Scanner scannerDatasetHistorical = new Scanner(fileReaderDatasetHistorical);

        Map<String, ArrayList<String>> idCurriculumStudent_Edge = new HashMap<String, ArrayList<String>>();

        String idCurriculumStudent = null;
        while (scannerDatasetHistorical.hasNextLine()) {
            String line = scannerDatasetHistorical.nextLine();

            if (line.substring(0, 1).toUpperCase().equals("G")) {
                String[] fields = line.split(Pattern.quote("|"));

                idCurriculumStudent = fields[2];

                if (!idCurriculumStudent_Edge.containsKey(idCurriculumStudent)) {
                    idCurriculumStudent_Edge.put(idCurriculumStudent, new ArrayList<String>());
                }
            } else if (line.substring(0, 1).toUpperCase().equals("E")) {
                String[] fields = line.split(Pattern.quote("|"));

                String idVerticeSource = fields[1];
                String idVerticeTarget = fields[3];
                int weightEdgeStudent = Integer.parseInt(fields[5]);

                String edgeWithIds = idVerticeSource + "," + idVerticeTarget;

                //if (weightEdgeStudent > 0) {
                idCurriculumStudent_Edge.get(idCurriculumStudent).add(edgeWithIds);
                idCurriculum_Edge_Weights.get(idCurriculumStudent).get(edgeWithIds).add(weightEdgeStudent);
                //}               
            }
        }

        FileWriter dataset = this.createFileForDataset(dirApp, DATASET_MEDIAN_GRAPH_NAME, "txt");;
        PrintWriter datasetPrint = new PrintWriter(dataset, true);

        Set<String> idsCurriculums = idCurriculum_Edges.keySet();
        for (String idCurr : idsCurriculums) {
            if (idCurriculumStudent_Edge.containsKey(idCurr)) {
                datasetPrint.println("g|" + idCurr);

                Iterator i = idCurriculum_Vertices.get(idCurr).iterator();
                while (i.hasNext()) {
                    String lineVertice = (String) i.next();
                    datasetPrint.println(lineVertice);
                }

                Iterator i2 = idCurriculum_Edges.get(idCurr).iterator();
                while (i2.hasNext()) {
                    String edge = (String) i2.next();

                    Collections.sort(idCurriculum_Edge_Weights.get(idCurr).get(edge));

                    int n = idCurriculum_Edge_Weights.get(idCurr).get(edge).size();

                    int medianValue = 0;
                    if (n % 2 == 0) {//PAR
                        int centralPositionValue1 = n / 2;
                        int centralPositionValue2 = centralPositionValue1 + 1;

                        medianValue = (idCurriculum_Edge_Weights.get(idCurr).get(edge).get(centralPositionValue1) + idCurriculum_Edge_Weights.get(idCurr).get(edge).get(centralPositionValue2)) / 2;
                    } else {//IMPAR
                        int positionDaMediana = (n + 1) / 2;

                        medianValue = idCurriculum_Edge_Weights.get(idCurr).get(edge).get(positionDaMediana);
                    }

                    String[] fields = edge.split(Pattern.quote(","));

                    String idVerticeSource = fields[0];
                    String idVerticeTarget = fields[1];

                    datasetPrint.println("e|" + idVerticeSource + "|" + idCurriculum_Vertice_Label.get(idCurr).get(idVerticeSource) + "|" + idVerticeTarget + "|" + idCurriculum_Vertice_Label.get(idCurr).get(idVerticeTarget) + "|" + medianValue);
                }
            }
        }
    }

    public void createDataSetMaximumGraph(String dirApp) throws FileNotFoundException, IOException {
        FileReader fileReaderDatasetFlowchart = new FileReader(dirApp + File.separator + DIR_DATASETS + File.separator + DATASET_FLOWCHART_NAME + ".txt");
        Scanner scannerDatasetFlowchart = new Scanner(fileReaderDatasetFlowchart);

        Map<String, ArrayList<String>> idCurriculum_Vertices = new HashMap<String, ArrayList<String>>();
        Map<String, HashMap<String, String>> idCurriculum_Vertice_Label = new HashMap<String, HashMap<String, String>>();
        Map<String, HashMap<String, ArrayList<Integer>>> idCurriculum_Edge_Weights = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
        Map<String, HashSet<String>> idCurriculum_Edges = new HashMap<String, HashSet<String>>();

        String idCurriculum = null;
        while (scannerDatasetFlowchart.hasNextLine()) {
            String line = scannerDatasetFlowchart.nextLine();

            if (line.substring(0, 1).toUpperCase().equals("G")) {
                idCurriculum = line.substring(line.indexOf('|') + 1);

                if (!idCurriculum_Vertices.containsKey(idCurriculum)) {
                    idCurriculum_Vertices.put(idCurriculum, new ArrayList<String>());
                }

                if (!idCurriculum_Vertice_Label.containsKey(idCurriculum)) {
                    idCurriculum_Vertice_Label.put(idCurriculum, new HashMap<String, String>());
                }

                if (!idCurriculum_Edge_Weights.containsKey(idCurriculum)) {
                    idCurriculum_Edge_Weights.put(idCurriculum, new HashMap<String, ArrayList<Integer>>());
                }

                if (!idCurriculum_Edges.containsKey(idCurriculum)) {
                    idCurriculum_Edges.put(idCurriculum, new HashSet<String>());
                }
            } else if (line.substring(0, 1).toUpperCase().equals("V")) {
                String[] fields = line.split(Pattern.quote("|"));

                String idVertice = fields[1];
                String verticeLabel = fields[2];

                if (!idCurriculum_Vertice_Label.get(idCurriculum).containsKey(idVertice)) {
                    idCurriculum_Vertice_Label.get(idCurriculum).put(idVertice, verticeLabel);
                }

                idCurriculum_Vertices.get(idCurriculum).add(line);
            } else if (line.substring(0, 1).toUpperCase().equals("E")) {
                String[] fields = line.split(Pattern.quote("|"));

                String idVerticeSource = fields[1];
                String idVerticeTarget = fields[3];

                String edgeWithIds = idVerticeSource + "," + idVerticeTarget;

                if (!idCurriculum_Edge_Weights.get(idCurriculum).containsKey(edgeWithIds)) {
                    idCurriculum_Edge_Weights.get(idCurriculum).put(edgeWithIds, new ArrayList<Integer>());
                }

                idCurriculum_Edges.get(idCurriculum).add(edgeWithIds);
            }
        }

        FileReader fileReaderDatasetHistorical = new FileReader(dirApp + File.separator + DIR_DATASETS + File.separator + DATASET_HISTORICAL_NAME + ".txt");
        Scanner scannerDatasetHistorical = new Scanner(fileReaderDatasetHistorical);

        Map<String, ArrayList<String>> idCurriculumStudent_Edge = new HashMap<String, ArrayList<String>>();

        String idCurriculumStudent = null;
        while (scannerDatasetHistorical.hasNextLine()) {
            String line = scannerDatasetHistorical.nextLine();

            if (line.substring(0, 1).toUpperCase().equals("G")) {
                String[] fields = line.split(Pattern.quote("|"));

                idCurriculumStudent = fields[2];

                if (!idCurriculumStudent_Edge.containsKey(idCurriculumStudent)) {
                    idCurriculumStudent_Edge.put(idCurriculumStudent, new ArrayList<String>());
                }
            } else if (line.substring(0, 1).toUpperCase().equals("E")) {
                String[] fields = line.split(Pattern.quote("|"));

                String idVerticeSource = fields[1];
                String idVerticeTarget = fields[3];
                int weightEdgeStudent = Integer.parseInt(fields[5]);

                String edgeWithIds = idVerticeSource + "," + idVerticeTarget;

                //if (weightEdgeStudent > 0) {
                idCurriculumStudent_Edge.get(idCurriculumStudent).add(edgeWithIds);
                idCurriculum_Edge_Weights.get(idCurriculumStudent).get(edgeWithIds).add(weightEdgeStudent);
                //}               
            }
        }

        FileWriter dataset = this.createFileForDataset(dirApp, DATASET_MAXIMUM_GRAPH_NAME, "txt");;
        PrintWriter datasetPrint = new PrintWriter(dataset, true);

        Set<String> idsCurriculums = idCurriculum_Edges.keySet();
        for (String idCurr : idsCurriculums) {
            if (idCurriculumStudent_Edge.containsKey(idCurr)) {
                datasetPrint.println("g|" + idCurr);

                Iterator i = idCurriculum_Vertices.get(idCurr).iterator();
                while (i.hasNext()) {
                    String lineVertice = (String) i.next();
                    datasetPrint.println(lineVertice);
                }

                Iterator i2 = idCurriculum_Edges.get(idCurr).iterator();
                while (i2.hasNext()) {
                    String edge = (String) i2.next();
                    
                    String[] fields = edge.split(Pattern.quote(","));

                    String idVerticeSource = fields[0];
                    String idVerticeTarget = fields[1];

                    datasetPrint.println("e|" + idVerticeSource + "|" + idCurriculum_Vertice_Label.get(idCurr).get(idVerticeSource) + "|" + idVerticeTarget + "|" + idCurriculum_Vertice_Label.get(idCurr).get(idVerticeTarget) + "|" + Collections.max(idCurriculum_Edge_Weights.get(idCurr).get(edge)));
                }
            }
        }
    }

    public void createDatasetHeaviestEdges(String dirApp, String datasetType, String sumType) throws FileNotFoundException, IOException {
        FileReader fileReaderFlowchart = new FileReader(dirApp + "/" + DIR_DATASETS + "/" + DATASET_FLOWCHART_NAME + ".txt");
        Scanner scannerFlowchart = new Scanner(fileReaderFlowchart);

        Map<String, ArrayList<String>> idCurriculum_Vertices = new HashMap<String, ArrayList<String>>();
        Map<String, HashMap<String, String>> idCurriculum_Vertice_Label = new HashMap<String, HashMap<String, String>>();
        Map<String, HashMap<String, Integer>> idCurriculum_Edge_Weight = new HashMap<String, HashMap<String, Integer>>();
        Map<String, HashSet<String>> idCurriculum_Edge = new HashMap<String, HashSet<String>>();

        String idCurriculum = null;
        while (scannerFlowchart.hasNextLine()) {
            String line = scannerFlowchart.nextLine();

            if (line.substring(0, 1).toUpperCase().equals("G")) {
                idCurriculum = line.substring(line.indexOf('|') + 1);

                if (!idCurriculum_Vertices.containsKey(idCurriculum)) {
                    idCurriculum_Vertices.put(idCurriculum, new ArrayList<String>());
                }

                if (!idCurriculum_Vertice_Label.containsKey(idCurriculum)) {
                    idCurriculum_Vertice_Label.put(idCurriculum, new HashMap<String, String>());
                }

                if (!idCurriculum_Edge_Weight.containsKey(idCurriculum)) {
                    idCurriculum_Edge_Weight.put(idCurriculum, new HashMap<String, Integer>());
                }

                if (!idCurriculum_Edge.containsKey(idCurriculum)) {
                    idCurriculum_Edge.put(idCurriculum, new HashSet<String>());
                }
            } else if (line.substring(0, 1).toUpperCase().equals("V")) {
                String[] fields = line.split(Pattern.quote("|"));

                String idVertice = fields[1];
                String verticeLabel = fields[2];

                if (!idCurriculum_Vertice_Label.get(idCurriculum).containsKey(idVertice)) {
                    idCurriculum_Vertice_Label.get(idCurriculum).put(idVertice, verticeLabel);
                }

                idCurriculum_Vertices.get(idCurriculum).add(line);
            } else if (line.substring(0, 1).toUpperCase().equals("E")) {
                String[] fields = line.split(Pattern.quote("|"));

                String idVerticeSource = fields[1];
                String idVerticeTarget = fields[3];

                int weightEdge = 0;

                String edgeWithIds = idVerticeSource + "," + idVerticeTarget;

                if (!idCurriculum_Edge_Weight.get(idCurriculum).containsKey(edgeWithIds)) {
                    idCurriculum_Edge_Weight.get(idCurriculum).put(edgeWithIds, weightEdge);
                }

                idCurriculum_Edge.get(idCurriculum).add(edgeWithIds);
            }
        }

        if ("lp".equals(datasetType)) {
            FileReader fileReaderPathsHistorical = new FileReader(dirApp + "/" + DIR_DATASETS + "/" + DATASET_PATHS_HISTORICAL_NAME + ".txt");
            Scanner scannerPathsHistorical = new Scanner(fileReaderPathsHistorical);

            String idCurriculumStudent = null;
            int weightEdgeNew = 0;
            int weightEdgeFlowcahrt = 0;
            while (scannerPathsHistorical.hasNextLine()) {
                String line = scannerPathsHistorical.nextLine();

                if (line.substring(0, 1).toUpperCase().equals("C")) {
                    String[] fields = line.split(Pattern.quote("|"));

                    idCurriculumStudent = fields[2];
                } else if (line.substring(0, 1).toUpperCase().equals("E")) {
                    String[] fields = line.split(Pattern.quote("|"));

                    String idVerticeSource = fields[1];
                    String idVerticeTarget = fields[3];
                    int weightEdgeStudent = Integer.parseInt(fields[5]);

                    String edgeWithIds = idVerticeSource + "," + idVerticeTarget;

                    if (idCurriculum_Edge_Weight.containsKey(idCurriculumStudent)) {
                        if (idCurriculum_Edge_Weight.get(idCurriculumStudent).containsKey(edgeWithIds)) {
                            weightEdgeFlowcahrt = idCurriculum_Edge_Weight.get(idCurriculumStudent).get(edgeWithIds);

                            if ("weights".equals(sumType)) {
                                weightEdgeNew = weightEdgeFlowcahrt + weightEdgeStudent;
                            } else {
                                weightEdgeNew = weightEdgeFlowcahrt + 1;
                            }

                            idCurriculum_Edge_Weight.get(idCurriculumStudent).put(edgeWithIds, weightEdgeNew);
                        }
                    }
                }
            }
        } else {
            FileReader fileReaderHistorical = new FileReader(dirApp + "/" + DIR_DATASETS + "/" + DATASET_HISTORICAL_NAME + ".txt");
            Scanner scannerHistorical = new Scanner(fileReaderHistorical);

            String idCurriculumStudent = null;
            while (scannerHistorical.hasNextLine()) {
                String line = scannerHistorical.nextLine();

                if (line.substring(0, 1).toUpperCase().equals("G")) {
                    String[] fields = line.split(Pattern.quote("|"));

                    idCurriculumStudent = fields[2];
                } else if (line.substring(0, 1).toUpperCase().equals("E")) {
                    String[] fields = line.split(Pattern.quote("|"));

                    String idVerticeSource = fields[1];
                    String idVerticeTarget = fields[3];
                    int weightEdgeStudent = Integer.parseInt(fields[5]);

                    String edgeWithIds = idVerticeSource + "," + idVerticeTarget;

                    if (idCurriculum_Edge_Weight.containsKey(idCurriculumStudent)) {
                        if (idCurriculum_Edge_Weight.get(idCurriculumStudent).containsKey(edgeWithIds)) {
                            int weightEdgeFlowcahrt = idCurriculum_Edge_Weight.get(idCurriculumStudent).get(edgeWithIds);

                            int weightEdgeNew = 0;
                            if ("weights".equals(sumType)) {
                                weightEdgeNew = weightEdgeFlowcahrt + weightEdgeStudent;
                            } else {
                                if (weightEdgeStudent > 0) {
                                    weightEdgeNew = weightEdgeFlowcahrt + 1;
                                }
                            }

                            idCurriculum_Edge_Weight.get(idCurriculumStudent).put(edgeWithIds, weightEdgeNew);
                        }
                    }
                }
            }
        }

        FileWriter dataset = this.createFileForDataset(dirApp, DATASET_HEAVIEST_EDGES_NAME, "txt");
        PrintWriter datasetPrint = new PrintWriter(dataset, true);

        Set<String> idsCurr = idCurriculum_Edge_Weight.keySet();
        for (String idCurr : idsCurr) {
            datasetPrint.println("g|" + idCurr);

            Iterator i = idCurriculum_Vertices.get(idCurr).iterator();
            while (i.hasNext()) {
                String lineVertice = (String) i.next();
                datasetPrint.println(lineVertice);
            }

            Iterator i2 = idCurriculum_Edge.get(idCurr).iterator();
            while (i2.hasNext()) {
                String edge = (String) i2.next();

                String[] fields = edge.split(Pattern.quote(","));

                String idVerticeSource = fields[0];
                String idVerticeTarget = fields[1];

                datasetPrint.println("e|" + idVerticeSource + "|" + idCurriculum_Vertice_Label.get(idCurr).get(idVerticeSource) + "|" + idVerticeTarget + "|" + idCurriculum_Vertice_Label.get(idCurr).get(idVerticeTarget) + "|" + idCurriculum_Edge_Weight.get(idCurr).get(edge));
            }
        }
    }

    private FileWriter createFileForDataset(String directory, String filneName, String ext) throws IOException {
        String dirDatasetFull = directory + File.separator + DIR_DATASETS;

        File f = new File(dirDatasetFull);
        if (!f.exists()) {
            f.mkdir();
            f.setWritable(true);
            f.setReadable(true);
        }

        FileWriter dataSetFile = new FileWriter(f + File.separator + filneName + "." + ext);

        return dataSetFile;
    }

    public String getValueMoreRepeatsInMap(Map<String, ArrayList<String>> map, String key) {

        ArrayList<String> array = map.get(key);

        Set<String> arrayAux = new HashSet<>(array);

        String itemMoreFrequently = "";

        int moreFrequently = 0;

        for (String item : arrayAux) {

      
            int currentFrequency = Collections.frequency(array, item);

      
            if (currentFrequency > moreFrequently) {
      
                moreFrequently = currentFrequency;
                itemMoreFrequently = item;
            }
        }

        return itemMoreFrequently;
    }
}
