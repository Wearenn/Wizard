package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import data.DAO.DaoPattern;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Resume Activity
 */

public class CtrlResume implements Initializable {

    @FXML
    private Label LblResume, LblAlert;
    @FXML
    private Button BtnSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LblResume.setText("");
        LblAlert.setText("");

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
            File file = new File("Network.txt");
            file.createNewFile();

            //group new informations
            DaoPattern.getInstance();

            //write new informations
            FileWriter fichier = new FileWriter("Network.txt");
            fichier.write ("Write new data here !");
            fichier.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
