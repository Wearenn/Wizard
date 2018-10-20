package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the third page Window.fxml
 * package: application.xml
 */

public class CtrlWindows implements Initializable, Data {

    @FXML
    private ComboBox<Integer> HoursM, HoursN, MinutesM, MinutesN, SecondsM, SecondsN;
    @FXML
    private TextField TfWindowMValue, TfWindowNValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialize ComboBox Past
        HoursM.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24);
        HoursM.setValue(1);
        MinutesM.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60);
        MinutesM.setValue(0);
        SecondsM.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60);
        SecondsM.setValue(0);

        //initialize ComboBox Present
        HoursN.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24);
        HoursN.setValue(2);
        MinutesN.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60);
        MinutesN.setValue(0);
        SecondsN.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60);
        SecondsN.setValue(0);
    }

    /**
     * We save in file "Choices.txt" the id of the user choice
     *
     * @throws IOException
     */
    @Override
    public void writeData() throws IOException {
        //write new informations
        FileWriter fichier = new FileWriter(new File("./src/txt/Choices.txt"), true);

        fichier.write("\n");

        /*code fonctionnel mais non implémenté
        fichier.write(HoursM.getValue() + "hr " + MinutesM.getValue() + "min " + SecondsM.getValue() + "sec" + "\n" + HoursN.getValue() + "hr " + MinutesN.getValue() + "min " + SecondsN.getValue() + "sec");
        */
        fichier.write("the last " + TfWindowNValue.getText() + " events");
        fichier.write("\n");
        fichier.write("and the " + TfWindowMValue.getText() + " events");
        fichier.close();
    }
}
