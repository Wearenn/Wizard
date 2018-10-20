package controller;

import BeepBeep.*;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.functions.*;
import ca.uqac.lif.cep.util.Numbers;
import com.sun.istack.internal.NotNull;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.ml.distance.ManhattanDistance;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for Resume Activity
 */

public class CtrlResume implements Initializable {

    @FXML private Label LblResume, LblAlert;
    @FXML private Button BtnSave, BtnRun;

    private ArrayList<String> StringData; //String to write the resume
    private File file;

    private TrendDistance<Number,Number,Number> trendDistance;  //object to save the new trend distance generated
    private SelfCorrelatedTrendDistance<Number,Number,Number> selfCorrelatedTrendDistance;  //object to save the new self-correlated trend distance generated

    private Number Pattern = null;
    private int m = -1;
    private int n = -1;
    private Processor Beta = null;
    private Function Delta = null;
    private Number d = null;
    private BinaryFunction<Number,Number,Boolean> comp = null;

    //variables to print in Network.txt file
    private String BetaPrint;
    private String DeltaPrint;
    private String compPrint;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FileReader fileReader = new FileReader("./src/txt/Choices.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String sCurrentLine;

            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                System.out.println(sCurrentLine);
            }

            System.out.println("\n");
            getDataBack();
        } catch (IOException e){
            e.printStackTrace();
        }

        BtnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                write();
            }
        });

        BtnRun.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    constructObject();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Write a summary of the user choices
        LblResume.setText("Over a stream coming from the " + underline(StringData.get(0)) + ",\n" +
                underline(StringData.get(1)) + "\n" +
                "and compare the " + underline(StringData.get(4)) + "\n" +
                "between " + underline(StringData.get(2)) + "\n" +
                underline(StringData.get(3)) + " that precedes it.");
        LblAlert.setText("Raise an alert whenever\n" +
                "the " + underline(StringData.get(5)) + " between them\n" +
                underline(StringData.get(6)) + ".");
    }

    /**
     * function to recover Choices.txt data in a table StringData
     */
    private void getDataBack() throws IOException {
        StringData = new ArrayList<String>();

        FileReader fileReader = new FileReader("./src/txt/Choices.txt");
        BufferedReader br = new BufferedReader(fileReader);
        String sCurrentLine;

        while ((sCurrentLine = br.readLine()) != null) {
            if (sCurrentLine.contains("input")) {
                StringData.add(sCurrentLine);
                System.out.println(sCurrentLine);
            } else if (sCurrentLine.contains("from port") || sCurrentLine.contains("extract a file in ") || sCurrentLine.contains("extract new data")) {
                StringData.add(sCurrentLine);
                System.out.println(sCurrentLine);
            } else if (sCurrentLine.contains("average of the stream over the entire window") || sCurrentLine.contains("first statistical moments over the entire window") || sCurrentLine.contains("number of distinct values observed in the window") ||
                    sCurrentLine.contains("distribution of values observed in the window") || sCurrentLine.contains("sum of all values over the window")) {
                StringData.add(sCurrentLine);
                System.out.println(sCurrentLine);
            } else if (sCurrentLine.contains(".jpg") || sCurrentLine.matches("[0-9]*")) {
                StringData.add(sCurrentLine);
                System.out.println(sCurrentLine);
            /*} else if (sCurrentLine.contains("hr")) {
                StringData.add(sCurrentLine);
                System.out.println(sCurrentLine);*/
            } else if (sCurrentLine.contains("events")) {
                StringData.add(sCurrentLine);
                System.out.println(sCurrentLine);
            } else if (sCurrentLine.contains("distance") || sCurrentLine.equals("Scalar difference") || sCurrentLine.equals("Ratio")) {
                StringData.add(sCurrentLine);
                System.out.println(sCurrentLine);
            } else if (sCurrentLine.contains("the value of")) {
                StringData.add(sCurrentLine);
                System.out.println(sCurrentLine);
            }
        }
    }

    /**
     * Contruct the new Objet and generate it
     */
    private void constructObject() throws IOException {
        System.out.println("\n--------------------------------------------");
        UtilityMethods.printGreeting();
        System.out.println("--------------------------------------------\n");
        System.out.println("DESCRIPTION DE L'OBJET CREE :");

        FileReader fileReader = new FileReader("./src/txt/Choices.txt");
        BufferedReader br = new BufferedReader(fileReader);
        String s;

        while ((s = br.readLine()) != null) {
            if (s.contains("Static")){
                String[] string = s.split(" ");
                Pattern = Integer.parseInt(string[1]);
                System.out.println("RESULT : Pattern = " + Pattern);
            }

            if (s.contains("the last")){
                String[] string = s.split(" ");
                n = Integer.parseInt(string[2]);
                System.out.println("RESULT : n = " + n);
            } else if (s.contains("and the")){
                String[] string = s.split(" ");
                m = Integer.parseInt(string[2]);
                System.out.println("RESULT : m = " + m);
            }

            if (s.equals("Manhattan distance")){
                Delta = new PointDistance(new ManhattanDistance());
                DeltaPrint = "new PointDistance(new ManhattanDistance())";
                System.out.println("RESULT : Delta = new PointDistance(new ManhattanDistance())");
            } else if (s.equals("Euclidean distance")){
                Delta = new PointDistance(new EuclideanDistance());
                DeltaPrint = "new PointDistance(new EuclideanDistance())";
                System.out.println("RESULT : Delta = new PointDistance(new EuclideanDistance())");
            } else if (s.equals("Scalar difference")){
                Delta = Numbers.subtraction;
                DeltaPrint = "Numbers.subtraction";
                System.out.println("RESULT : Delta = Scalar difference");
            } else if (s.equals("Ratio")){
                Delta = Numbers.division;
                DeltaPrint = "Numbers.division";
                System.out.println("RESULT : Delta = Ratio");
            }

            if (s.equals("average of the stream over the entire window")){
                Beta = new AverageFork();
                BetaPrint = "new AverageFork()";
                System.out.println("RESULT : Beta = new AverageFork()");
            } else if (s.contains(" first statistical moments over the entire window")){
                String[] string = s.split(" ");
                int nb = (int) Float.parseFloat(string[0]);
                Beta = new RunningMoments(nb);
                BetaPrint = "new RunningMoments(" + nb + ")";
                System.out.println("RESULT : Beta = new RunningMoments(" + nb + ")");
            } else if (s.equals("number of distinct values observed in the window")) {
                Beta = new SymbolDistribution();
                BetaPrint = "new SymbolDistribution()";
                System.out.println("RESULT : Beta = new SymbolDistribution()");
            } else if (s.equals("distribution of values observed in the window")) {
                Beta = new SymbolDistribution();
                BetaPrint = "new SymbolDistribution()";
                System.out.println("RESULT : Beta = new SymbolDistribution()");
            } else if (s.equals("sum of all values over the window")){
                Beta = new CumulativeSum();
                BetaPrint = "new CumulativeSum()";
                System.out.println("RESULT : Beta = new CumulativeSum()");
            }

            if (s.contains("are below the value of ")){
                comp = Numbers.isLessThan;
                compPrint = "Numbers.isLessThan";
                String[] string = s.split(" ");
                d = Float.parseFloat(string[5]);
                System.out.println("RESULT : comp = Numbers.isLessThan");
                System.out.println("RESULT : d = " + d);
            } else if (s.contains("exceeds the value of ")){
                comp = Numbers.isGreaterThan;
                compPrint = "Numbers.isGreaterThan";
                String[] string = s.split(" ");
                d = Float.parseFloat(string[4]);
                System.out.println("RESULT : comp = Numbers.isGreaterThan");
                System.out.println("RESULT : d = " + d);
            }
        }

        try {
            FileReader fileReader2 = new FileReader("./src/txt/Choices.txt");
            BufferedReader br2 = new BufferedReader(fileReader2);
            String s1 = br2.readLine();
            if (s1.equals("SelfCorrelated")) {
                if (m != -1 && n != -1 && Beta != null && Delta != null && d != null && comp != null){
                    selfCorrelatedTrendDistance = new SelfCorrelatedTrendDistance<Number,Number,Number>(m,n,Beta,Delta,d,comp);
                    BtnSave.setDisable(false);
                    System.out.println("FINAL RESULT : Self Correlated trend distance");
                } else {
                    System.out.println("Une des valeurs dans SelfCorrelated est null");
                    if (n == -1) {
                        System.out.println("C'est n");
                    } else if (m == -1){
                        System.out.println("C'est m");
                    } else if (Beta == null){
                        System.out.println("C'est Beta");
                    } else if (Delta == null){
                        System.out.println("C'est Delta");
                    } else if (d == null){
                        System.out.println("C'est d");
                    } else if (comp == null){
                        System.out.println("C'est comp");
                    }
                }
            } else if (s1.contains("Static")){
                if (Pattern != null && n != -1 && Beta != null && Delta != null && d != null && comp != null) {
                    trendDistance = new TrendDistance<Number, Number, Number>(Pattern, n, Beta, Delta, d, comp);
                    BtnSave.setDisable(false);
                    System.out.println("FINAL RESULT : trend distance");
                } else {
                    System.out.println("Une des valeurs dans static est null");
                    if (Pattern == null){
                        System.out.println("C'est Pattern");
                    } else if (n == -1){
                        System.out.println("C'est n");
                    } else if (Beta == null){
                        System.out.println("C'est Beta");
                    } else if (Delta == null){
                        System.out.println("C'est Delta");
                    } else if (d == null){
                        System.out.println("C'est d");
                    } else if (comp == null){
                        System.out.println("C'est comp");
                    }
                }
            } else {
                System.out.println("Erreur dans la création du processeur");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Save the program in the Text File "Network.txt"
     */
    private void write(){
        String s1 = null;
        //create the file if not created
        try {
            file = new File("./src/txt/Network.txt");
            file.createNewFile();

            FileReader fileReader2 = new FileReader("./src/txt/Choices.txt");
            BufferedReader br2 = new BufferedReader(fileReader2);
            br2.readLine();
            s1 = br2.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (s1.equals("pre-recorded input")){
            System.out.println("PreRecordedLog()");
            PreRecordedLog();
        } else if (s1.equals("standard input")){
            System.out.println("StandardInput()");
            StandardInput();
        } else if (s1.equals("TCP Connection input")){
            System.out.println("TCPConnection()");
            TCPConnection();
        } else {
            System.out.println("Input type not found");
            System.exit(1);
        }
    }

    /**
     * Function to write text in the file Network.txt
     * if Pré-recorded Log is selected
     */
    private void PreRecordedLog(){
        try {
            //write new informations
            FileWriter fichier = new FileWriter(file);

            FileReader fileReader2 = new FileReader("./src/txt/Choices.txt");
            BufferedReader br2 = new BufferedReader(fileReader2);
            String s1 = br2.readLine(); br2.readLine();
            String s2 = br2.readLine();
            String[] split = s2.split(" ");
            String FileName = split[4];

            if (s1.equals("SelfCorrelated")) {
                /*-------------Header---------------*/
                if (DeltaPrint.equals("Numbers.subtraction") || DeltaPrint.equals("Numbers.division")) {
                    fichier.write("String file = \"../txt/" + FileName + "\";\n" +
                            "ArrayList<Number> arrayList = new ArrayList<Number>();\n" +
                            "new ReadFile(file,arrayList);\n" +
                            "\n" +
                            "QueueSource source = new QueueSource();\n" +
                            "for (Number element : arrayList) {\n" +
                            "    source.setEvents(element);\n" +
                            "}\n\n");
                } else if (DeltaPrint.equals("ManhattanDistance") || DeltaPrint.equals("EuclideanDistance")) {
                    fichier.write("String file = \"../txt/" + FileName + "\";\n" +
                            "ArrayList<Number> arrayList = new ArrayList<Number>();\n" +
                            "new ReadFile(file,arrayList);\n" +
                            "\n" +
                            "QueueSource source = new QueueSource();\n" +
                            "for (Number element : arrayList) {\n" +
                            "    DoublePoint dp = DoublePointCast.getDoublePoint(element);\n" +
                            "    source.setEvents(dp);\n" +
                            "}\n\n");
                }

                /*------------Body-------------*/
                fichier.write("GroupProcessor groupProcessor = new GroupProcessor(1,1);\n" +
                        "{\n" +
                        "   Fork fork = new Fork(2);\n" +
                        "   groupProcessor.associateInput(INPUT, fork, INPUT);\n" +
                        "   Trim trim = new Trim(" + m + ");\n" +
                        "   Connector.connect(fork, TOP, trim, INPUT);\n" +
                        "   Window win1 = new Window(" + BetaPrint + ".duplicate(), " + n + ");\n" +
                        "   Connector.connect(trim, win1);\n" +
                        "   Window win2 = new Window(" + BetaPrint + ".duplicate(), " + m + ");\n" +
                        "   Connector.connect(fork, BOTTOM, win2, INPUT);\n" +
                        "   ApplyFunction distance = new ApplyFunction(" + DeltaPrint + ");\n" +
                        "   Connector.connect(win1, OUTPUT, distance, TOP);\n" +
                        "   Connector.connect(win2, OUTPUT, distance, BOTTOM);\n" +
                        "   ApplyFunction too_far = new ApplyFunction(new FunctionTree(" + compPrint + ",\n" +
                        "       new StreamVariable(0),\n" +
                        "       new Constant(" + d + ")\n" +
                        "       ));\n" +
                        "   Connector.connect(distance, too_far);\n" +
                        "   groupProcessor.associateOutput(OUTPUT, too_far, OUTPUT);\n" +
                        "   groupProcessor.addProcessors(fork, trim, win1, win2, distance, too_far);\n" +
                        "}\n\n" +
                        "Connector.connect(source,groupProcessor);\n" +
                        "Pullable p = groupProcessor.getPullableOutput();\n" +
                        "for (int i = 0; i < arrayList.size(); i++) {\n" +
                        "   System.out.println(p.pull());\n" +
                        "}");


            } else if (s1.contains("Static")) {
                /*-------------Header---------------*/
                if (DeltaPrint.equals("Numbers.subtraction") || DeltaPrint.equals("Numbers.division")) {
                    fichier.write("String file = \"../txt/" + FileName + "\";\n" +
                            "ArrayList<Number> arrayList = new ArrayList<Number>();\n" +
                            "new ReadFile(file,arrayList);\n" +
                            "\n" +
                            "QueueSource source = new QueueSource();\n" +
                            "for (Number element : arrayList) {\n" +
                            "    source.setEvents(element);\n" +
                            "}\n\n");
                } else if (DeltaPrint.equals("ManhattanDistance") || DeltaPrint.equals("EuclideanDistance")) {
                    fichier.write("String file = \"../txt/" + FileName + "\";\n" +
                            "ArrayList<Number> arrayList = new ArrayList<Number>();\n" +
                            "new ReadFile(file,arrayList);\n" +
                            "\n" +
                            "QueueSource source = new QueueSource();\n" +
                            "for (Number element : arrayList) {\n" +
                            "    DoublePoint dp = DoublePointCast.getDoublePoint(element);\n" +
                            "    source.setEvents(dp);\n" +
                            "}\n\n");
                }

                /*------------Body-------------*/
                fichier.write("GroupProcessor groupProcessor = new GroupProcessor(1,1);\n" +
                        "{\n" +
                        "   Window wp = new Window(" + BetaPrint + ", " + n + ");\n" +
                        "   groupProcessor.associateInput(INPUT, window, INPUT);\n" +
                        "   ApplyFunction distance = new ApplyFunction(new FunctionTree(" + DeltaPrint + ",\n" +
                        "       new Constant(" + Pattern + "),\n" +
                        "       new StreamVariable(0)\n" +
                        "       ));\n" +
                        "   Connector.connect(window, distance);\n" +
                        "   ApplyFunction too_far = new ApplyFunction(new FunctionTree(" + compPrint + ",\n" +
                        "       new StreamVariable(0),\n" +
                        "       new Constant(" + d + ")\n" +
                        "       ));\n" +
                        "   Connector.connect(distance, too_far);\n" +
                        "   groupProcessor.associateOutput(OUTPUT, too_far, OUTPUT);\n" +
                        "   groupProcessor.addProcessors(window, distance, too_far);\n" +
                        "}\n\n" +
                        "Connector.connect(source,groupProcessor);\n" +
                        "Pullable p = groupProcessor.getPullableOutput();\n" +
                        "for (int i = 0; i < arrayList.size(); i++) {\n" +
                        "   System.out.println(p.pull());\n" +
                        "}");
            }
            fichier.close();
            fileReader2.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("File saved");
            alert.setContentText("New file Network.txt saved");
            alert.showAndWait();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Function to write text in the file Network.txt
     * if standard input is selected
     */
    private void StandardInput(){
        try{
            //write new informations
            FileWriter fichier = new FileWriter(file);

            FileReader fileReader2 = new FileReader("./src/txt/Choices.txt");
            BufferedReader br2 = new BufferedReader(fileReader2);
            String s = br2.readLine();
            if (s.equals("SelfCorrelated")) {
                /*-------------Header---------------*/
                if (DeltaPrint.equals("Numbers.subtraction") || DeltaPrint.equals("Numbers.division")) {
                    fichier.write("ReadStringStream reader = new ReadStringStream(System.in);\n" +
                            "StringToInteger stringToInteger = new StringToInteger();\n" +
                            "ApplyFunction source = new ApplyFunction(stringToInteger);\n" +
                            "connect(reader,source);\n" +
                            "reader.setIsFile(false);\n\n");
                }else if (DeltaPrint.equals("ManhattanDistance") || DeltaPrint.equals("EuclideanDistance")) {
                    fichier.write("ReadStringStream reader = new ReadStringStream(System.in);\n" +
                            "StringToInteger stringToInteger = new StringToInteger();\n" +
                            "ApplyFunction source = new ApplyFunction(stringToInteger);\n" +
                            "connect(reader,source);\n" +
                            "reader.setIsFile(false);\n\n");
                }

                /*------------Body-------------*/
                fichier.write("GroupProcessor groupProcessor = new GroupProcessor(1,1);\n" +
                        "{\n" +
                        "   Fork fork = new Fork(2);\n" +
                        "   groupProcessor.associateInput(INPUT, fork, INPUT);\n" +
                        "   Trim trim = new Trim(" + m + ");\n" +
                        "   Connector.connect(fork, TOP, trim, INPUT);\n" +
                        "   Window win1 = new Window(" + BetaPrint + ".duplicate(), " + n + ");\n" +
                        "   Connector.connect(trim, win1);\n" +
                        "   Window win2 = new Window(" + BetaPrint + ".duplicate(), " + m + ");\n" +
                        "   Connector.connect(fork, BOTTOM, win2, INPUT);\n" +
                        "   ApplyFunction distance = new ApplyFunction(" + DeltaPrint + ");\n" +
                        "   Connector.connect(win1, OUTPUT, distance, TOP);\n" +
                        "   Connector.connect(win2, OUTPUT, distance, BOTTOM);\n" +
                        "   ApplyFunction too_far = new ApplyFunction(new FunctionTree(" + compPrint + ",\n" +
                        "       new StreamVariable(0),\n" +
                        "       new Constant(" + d + ")\n" +
                        "       ));\n" +
                        "   Connector.connect(distance, too_far);\n" +
                        "   groupProcessor.associateOutput(OUTPUT, too_far, OUTPUT);\n" +
                        "   groupProcessor.addProcessors(fork, trim, win1, win2, distance, too_far);\n" +
                        "}\n\n" +
                        "Connector.connect(source,groupProcessor);\n" +
                        "Pullable p = groupProcessor.getPullableOutput();\n" +
                        "for (Object o: p) {\n" +
                        "    System.out.println(o);\n" +
                        "}");

            } else if (s.contains("Static")) {
                /*-------------Header---------------*/
                if (DeltaPrint.equals("Numbers.subtraction") || DeltaPrint.equals("Numbers.division")) {
                    fichier.write("ReadStringStream reader = new ReadStringStream(System.in);\n" +
                            "StringToInteger stringToInteger = new StringToInteger();\n" +
                            "ApplyFunction source = new ApplyFunction(stringToInteger);\n" +
                            "connect(reader,source);\n" +
                            "reader.setIsFile(false);\n\n");
                }else if (DeltaPrint.equals("ManhattanDistance") || DeltaPrint.equals("EuclideanDistance")) {
                    fichier.write("ReadStringStream reader = new ReadStringStream(System.in);\n" +
                            "StringToInteger stringToInteger = new StringToInteger();\n" +
                            "ApplyFunction source = new ApplyFunction(stringToInteger);\n" +
                            "connect(reader,source);\n" +
                            "reader.setIsFile(false);\n\n");
                }

                /*------------Body-------------*/
                fichier.write("GroupProcessor groupProcessor = new GroupProcessor(1,1);\n" +
                        "{\n" +
                        "   Window wp = new Window(beta, " + n + ");\n" +
                        "   groupProcessor.associateInput(INPUT, window, INPUT);\n" +
                        "   ApplyFunction distance = new ApplyFunction(new FunctionTree(" + DeltaPrint + ",\n" +
                        "       new Constant(" + Pattern + "),\n" +
                        "       new StreamVariable(0)\n" +
                        "       ));\n" +
                        "   Connector.connect(window, distance);\n" +
                        "   ApplyFunction too_far = new ApplyFunction(new FunctionTree(" + compPrint + ",\n" +
                        "       new StreamVariable(0),\n" +
                        "       new Constant(" + d + ")\n" +
                        "       ));\n" +
                        "   Connector.connect(distance, too_far);\n" +
                        "   groupProcessor.associateOutput(OUTPUT, too_far, OUTPUT);\n" +
                        "   groupProcessor.addProcessors(window, distance, too_far);\n" +
                        "}\n\n" +
                        "Connector.connect(source,groupProcessor);\n" +
                        "Pullable p = groupProcessor.getPullableOutput();\n" +
                        "for (Object o: p) {\n" +
                        "    System.out.println(o);\n" +
                        "}");
            }
            fichier.close();
            fileReader2.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("File saved");
            alert.setContentText("New file Network.txt saved");
            alert.showAndWait();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Function to write text in the file Network.txt
     * if TCP Connection is selected
     */
    //TODO
    private void TCPConnection(){
        try{
            //write new informations
            FileWriter fichier = new FileWriter(file);

            FileReader fileReader2 = new FileReader("./src/txt/Choices.txt");
            BufferedReader br2 = new BufferedReader(fileReader2);
            String s = br2.readLine();
            if (s.equals("SelfCorrelated")) {
                /*-------------Header---------------*/
                if (DeltaPrint.equals("Numbers.subtraction") || DeltaPrint.equals("Numbers.division")) {
                    //TODO
                    fichier.write("");
                }else if (DeltaPrint.equals("ManhattanDistance") || DeltaPrint.equals("EuclideanDistance")) {
                    //TODO
                    fichier.write("");
                }

                /*------------Body-------------*/
                fichier.write("GroupProcessor groupProcessor = new GroupProcessor(1,1);\n" +
                        "{\n" +
                        "   Fork fork = new Fork(2);\n" +
                        "   groupProcessor.associateInput(INPUT, fork, INPUT);\n" +
                        "   Trim trim = new Trim(" + m + ");\n" +
                        "   Connector.connect(fork, TOP, trim, INPUT);\n" +
                        "   Window win1 = new Window(" + BetaPrint + ".duplicate(), " + n + ");\n" +
                        "   Connector.connect(trim, win1);\n" +
                        "   Window win2 = new Window(" + BetaPrint + ".duplicate(), " + m + ");\n" +
                        "   Connector.connect(fork, BOTTOM, win2, INPUT);\n" +
                        "   ApplyFunction distance = new ApplyFunction(" + DeltaPrint + ");\n" +
                        "   Connector.connect(win1, OUTPUT, distance, TOP);\n" +
                        "   Connector.connect(win2, OUTPUT, distance, BOTTOM);\n" +
                        "   ApplyFunction too_far = new ApplyFunction(new FunctionTree(" + compPrint + ",\n" +
                        "       new StreamVariable(0),\n" +
                        "       new Constant(" + d + ")\n" +
                        "       ));\n" +
                        "   Connector.connect(distance, too_far);\n" +
                        "   groupProcessor.associateOutput(OUTPUT, too_far, OUTPUT);\n" +
                        "   groupProcessor.addProcessors(fork, trim, win1, win2, distance, too_far);\n" +
                        "}\n\n" +
                        "Connector.connect(source,groupProcessor);\n" +
                        "Pullable p = groupProcessor.getPullableOutput();\n" +
                        "for (Object o: p) {\n" +
                        "    System.out.println(o);\n" +
                        "}");

            } else if (s.contains("Static")) {
                /*-------------Header---------------*/
                if (DeltaPrint.equals("Numbers.subtraction") || DeltaPrint.equals("Numbers.division")) {
                    //TODO
                    fichier.write("");
                }else if (DeltaPrint.equals("ManhattanDistance") || DeltaPrint.equals("EuclideanDistance")) {
                    //TODO
                    fichier.write("");
                }

                /*------------Body-------------*/
                fichier.write("GroupProcessor groupProcessor = new GroupProcessor(1,1);\n" +
                        "{\n" +
                        "   Window wp = new Window(beta, " + n + ");\n" +
                        "   groupProcessor.associateInput(INPUT, window, INPUT);\n" +
                        "   ApplyFunction distance = new ApplyFunction(new FunctionTree(" + DeltaPrint + ",\n" +
                        "       new Constant(" + Pattern + "),\n" +
                        "       new StreamVariable(0)\n" +
                        "       ));\n" +
                        "   Connector.connect(window, distance);\n" +
                        "   ApplyFunction too_far = new ApplyFunction(new FunctionTree(" + compPrint + ",\n" +
                        "       new StreamVariable(0),\n" +
                        "       new Constant(" + d + ")\n" +
                        "       ));\n" +
                        "   Connector.connect(distance, too_far);\n" +
                        "   groupProcessor.associateOutput(OUTPUT, too_far, OUTPUT);\n" +
                        "   groupProcessor.addProcessors(window, distance, too_far);\n" +
                        "}\n\n" +
                        "Connector.connect(source,groupProcessor);\n" +
                        "Pullable p = groupProcessor.getPullableOutput();\n" +
                        "for (Object o: p) {\n" +
                        "    System.out.println(o);\n" +
                        "}");
            }
            fichier.close();
            fileReader2.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("File saved");
            alert.setContentText("New file Network.txt saved");
            alert.showAndWait();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Function to underline a text
     *
     * @param args string to underline
     * @return underlined
     */
    private StringBuilder underline(@NotNull String args){
        StringBuilder underlined = new StringBuilder();
        for (char c : args.toCharArray()) {
            underlined.append(c).append('\u0332');
        }
        return underlined;
    }
}
