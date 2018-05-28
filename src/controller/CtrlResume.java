package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for Resume Activity
 */

public class CtrlResume implements Initializable {

    @FXML
    private Label LblResume, LblAlert;
    @FXML
    private Button BtnSave;

    private ArrayList<String> StringData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FileReader fileReader = new FileReader("./src/txt/Choices.txt");
            BufferedReader br = new BufferedReader(fileReader);
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
            }

            getDataBack();
        } catch (IOException e){
            e.printStackTrace();
        }

        LblResume.setText("Over a stream coming from the " + StringData.get(0) + ",\n" +
                           "extract a fiel called " + StringData.get(1) + ".\n" +
                           "and compare the " + "\n" +
                           "between the last " + StringData.get(2) + "\n" +
                           "and the " + StringData.get(3) + " that precedes it.");
        LblAlert.setText("Raise an alert whenever\n" +
                         "the " + StringData.get(4)+ " between them\n" +
                         StringData.get(5) + ".");

        //enregistrement des données dans un fichier .java
        BtnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                write();
            }
        });
    }

    /**
     * Write the data in a Text File
     */
    private void write(){
        try {
            //create the file if not created
            File file = new File("./src/txt/Network.txt");
            file.createNewFile();

            //write new informations
            FileWriter fichier = new FileWriter(file);
            fichier.write ("Write new data here !");
            fichier.close();
        } catch (IOException e) {
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
            } else if (sCurrentLine.contains(".jpg")){
                StringData.add(sCurrentLine);
                System.out.println(sCurrentLine);
            } else if (sCurrentLine.contains("hr")) {
                StringData.add(sCurrentLine);
                System.out.println(sCurrentLine);
            } else if (sCurrentLine.contains("distance")) {
                StringData.add(sCurrentLine);
                System.out.println(sCurrentLine);
            } else if (sCurrentLine.contains("the value of")) {
                StringData.add(sCurrentLine);
                System.out.println(sCurrentLine);
            }
        }
    }
}
