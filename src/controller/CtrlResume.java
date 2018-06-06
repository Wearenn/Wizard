package controller;

import BeepBeep.*;
import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.functions.*;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Trim;
import ca.uqac.lif.cep.tmf.Window;
import ca.uqac.lif.cep.util.Numbers;
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

import static ca.uqac.lif.cep.Connector.*;

/**
 * Controller for Resume Activity
 */

public class CtrlResume implements Initializable {

    @FXML private Label LblResume, LblAlert;
    @FXML private Button BtnSave, BtnRun;

    private ArrayList<String> StringData; //String to write the resume

    private TrendDistance<Number,Number,Number> trendDistance;
    private SelfCorrelatedTrendDistance<Number,Number,Number> selfCorrelatedTrendDistance;

    private Number Pattern = null;
    //private int m = 0;
    private int n = -1;
    private Processor Beta = null;
    private Function Delta = null;
    private Number d = null;
    private BinaryFunction<Number,Number,Boolean> comp = null;

    //variables pour l'affichage
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

        LblResume.setText("Over a stream coming from the " + underline(StringData.get(0)) + ",\n" +
                underline(StringData.get(1)) + "\n" +
                "and compare the " + underline(StringData.get(3)) + "\n" +
                //"between the last " + underline(StringData.get(2)) + "\n" +
                //"and the " + underline(StringData.get(3)) + " that precedes it.");
                "with the " + underline(StringData.get(2)) + " that precedes it.");
        LblAlert.setText("Raise an alert whenever\n" +
                "the " + underline(StringData.get(4)) + " between them\n" +
                underline(StringData.get(5)) + ".");

        //enregistrement des données dans un fichier .java
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
            } else if (s.equals("SelfCorrelated")){
                //m = n donc pas besoin de la recuperer ici
            }

            if (s.contains("group of ")){
                String[] string = s.split(" ");
                n = Integer.parseInt(string[2]);
                System.out.println("RESULT : n = m = " + n);
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
                //Beta =
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
                if (n != -1 && Beta != null && Delta != null && d != null && comp != null){
                    selfCorrelatedTrendDistance = new SelfCorrelatedTrendDistance<Number,Number,Number>(n,n,Beta,Delta,d,comp);
                    BtnSave.setDisable(false);
                    System.out.println("FINAL RESULT : Self Correlated trend distance");
                } else {
                    System.out.println("Une des valeurs dans SelfCorrelated est null");
                    if (n == 0){
                        System.out.println("C'est n");
                    } else if (Beta == null){
                        System.out.println("C'est Beta");
                    } else if (Delta == null){
                        System.out.println("C'est Delta");
                    } else if (d == null){
                        System.out.println("C'est d");
                    } else {
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
                    } else if (n == 0){
                        System.out.println("C'est n");
                    } else if (Beta == null){
                        System.out.println("C'est Beta");
                    } else if (Delta == null){
                        System.out.println("C'est Delta");
                    } else if (d == null){
                        System.out.println("C'est d");
                    } else {
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
     * fonction qui recupère les données du fichier pour les mettres dans un tableau trié par fenetre
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
            } else if (sCurrentLine.contains("from port") || sCurrentLine.contains("extract a fiel called") || sCurrentLine.contains("extract new data")) {
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
            } else if (sCurrentLine.contains("group of ")){
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
     * Save the program in a Text File
     */
    private void write(){
        try {
            //create the file if not created
            File file = new File("./src/txt/Network.txt");
            file.createNewFile();

            //write new informations
            FileWriter fichier = new FileWriter(file);

            FileReader fileReader2 = new FileReader("./src/txt/Choices.txt");
            BufferedReader br2 = new BufferedReader(fileReader2);
            String s1 = br2.readLine();

            if (s1.equals("SelfCorrelated")) {
                fichier.write("Fork fork = new Fork(2);\n" +
                        "associateInput(INPUT, fork, INPUT);\n" +
                        "Trim trim = new Trim(" + n + ");\n" +
                        "Connector.connect(fork, TOP, trim, INPUT);\n" +
                        "Window win1 = new Window(" + BetaPrint + ".duplicate(), " + n + ");\n" +
                        "Connector.connect(trim, win1);\n" +
                        "Window win2 = new Window(" + BetaPrint + ".duplicate(), " + n + ");\n" +
                        "Connector.connect(fork, BOTTOM, win2, INPUT);\n" +
                        "ApplyFunction distance = new ApplyFunction(" + DeltaPrint + ");\n" +
                        "Connector.connect(win1, OUTPUT, distance, TOP);\n" +
                        "Connector.connect(win2, OUTPUT, distance, BOTTOM);\n" +
                        "ApplyFunction too_far = new ApplyFunction(new FunctionTree(" + compPrint + ",\n" +
                        "    new StreamVariable(0),\n" +
                        "    new Constant(" + d + ")\n" +
                        "    ));\n" +
                        "Connector.connect(distance, too_far);\n" +
                        "associateOutput(OUTPUT, too_far, OUTPUT);\n" +
                        "addProcessors(fork, trim, win1, win2, distance, too_far);");
            } else if (s1.contains("Static")){
                fichier.write("Window wp = new Window(beta, " + n + ");\n" +
                        "associateInput(INPUT, window, INPUT);\n" +
                        "ApplyFunction distance = new ApplyFunction(new FunctionTree(" + DeltaPrint + ",\n" +
                        "    new Constant(" + Pattern + "),\n" +
                        "    new StreamVariable(0)\n" +
                        "    ));\n" +
                        "Connector.connect(window, distance);\n" +
                        "ApplyFunction too_far = new ApplyFunction(new FunctionTree(" + compPrint + ",\n" +
                        "    new StreamVariable(0),\n" +
                        "    new Constant(" + d + ")\n" +
                        "    ));\n" +
                        "Connector.connect(distance, too_far);\n" +
                        "associateOutput(OUTPUT, too_far, OUTPUT);\n" +
                        "addProcessors(window, distance, too_far);");
            }
            fichier.close();
            fileReader2.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("File saved");
            alert.setContentText("New file Network.txt saved");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder underline(String args){
        StringBuilder underlined = new StringBuilder();
        for (char c : args.toCharArray()) {
            underlined.append(c).append('\u0332');
        }
        return underlined;
    }

    public static void main(String[] args) {
        GroupProcessor p = new GroupProcessor(1,1);
        {
            Fork fork = new Fork(2);
            p.associateInput(INPUT, fork, INPUT);
            Trim trim = new Trim(3);
            Connector.connect(fork, TOP, trim, INPUT);
            Window win1 = new Window(new AverageFork().duplicate(), 3);
            Connector.connect(trim, win1);
            Window win2 = new Window(new AverageFork().duplicate(), 3);
            Connector.connect(fork, BOTTOM, win2, INPUT);
            ApplyFunction distance = new ApplyFunction(Numbers.subtraction);
            Connector.connect(win1, OUTPUT, distance, TOP);
            Connector.connect(win2, OUTPUT, distance, BOTTOM);
            ApplyFunction too_far = new ApplyFunction(new FunctionTree(Numbers.isLessThan,
                    new StreamVariable(0),
                    new Constant(0.5)
            ));
            Connector.connect(distance, too_far);
            p.associateOutput(OUTPUT, too_far, OUTPUT);
            p.addProcessors(fork, trim, win1, win2, distance, too_far);
        }
    }
}
