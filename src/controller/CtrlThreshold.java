package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CtrlThreshold {

    @FXML
    private RadioButton RbSmaller, RbLarger;
    @FXML
    private TextField TfThreshold;
    @FXML
    private Button BtnContinue;

    public Button getBtnContinue(){
        return BtnContinue;
    }

    public TextField getTfThreshold() {
        return TfThreshold;
    }

    public void writeData() throws IOException {
        //write new informations
        FileWriter fichier = new FileWriter(new File("./src/txt/Choices.txt"),true);
        fichier.write("\n");

        try {
            if (RbLarger.isSelected()) {
                fichier.write ("exceeds the value of " + TfThreshold.getText());
            } else if (RbSmaller.isSelected()) {
                fichier.write ("are below the value of " + TfThreshold.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        fichier.close();
    }

    /**
     * Verify if a radio Button is selected
     */
    public boolean isSelected(){
        boolean isSelected = false;
        if ((RbLarger.isSelected() || RbSmaller.isSelected()) && isAFloat(getTfThreshold().getText())){
            isSelected = true;
        } else if (!isAFloat(getTfThreshold().getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("You must type a float !");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("You must make a choice before going far or fill all fields!");
            alert.showAndWait();
        }
        return isSelected;
    }

    public boolean isAFloat(String string){
        if (string.matches("^([+-]?\\d*\\.?\\d*)$") && !string.equals(""))  return true;
        return false;
    }
}
