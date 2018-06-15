package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    public RadioButton getRbSmaller() {
        return RbSmaller;
    }

    public RadioButton getRbLarger() {
        return RbLarger;
    }

    public TextField getTfThreshold() {
        return TfThreshold;
    }

    /**
     * We save in file "Choices.txt" the id of the user choice
     *
     * @throws IOException
     */
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
        if ((RbLarger.isSelected() || RbSmaller.isSelected()) && isAFloat(getTfThreshold().getText())){
            return true;
        }
        return false;
    }

    /**
     * Check if the value typed by the user is a float
     * if false, display a warning
     */
    public void Check(){
        if (!isAFloat(getTfThreshold().getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("You must type an integer !");
            alert.showAndWait();
        }
    }

    /**
     * Check if the value is a float
     *
     * @param string string
     * @return true or false
     */
    public boolean isAFloat(String string){
        return string.matches("^([+-]?\\d*\\.?\\d*)$") && !string.equals("");
    }
}
