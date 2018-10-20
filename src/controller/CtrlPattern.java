package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the first page Pattern.fxml
 * package: application.xml
 */

public class CtrlPattern implements Initializable, Data {

    @FXML
    public RadioButton RbSelfCorrelated, RbPatternBased;
    @FXML
    private TextField TfValue;
    @FXML
    private Label LblSelf, LblPattern;

    public TextField getTfValue() {
        return TfValue;
    }

    public RadioButton getRbSelfCorrelated() {
        return RbSelfCorrelated;
    }

    public RadioButton getRbPatternBased() {
        return RbPatternBased;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LblSelf.setText("Evaluates the similarity of a trend computed on the presentwith \nthe same trend computed over a past window.");
        LblPattern.setText("Evaluates the similarity of a trend computed on the presentwith \na reference trend provided externally.");
    }

    /**
     * We save in file "Choices.txt" the id of the user choice
     * Here the user have two choices :
     * Select Self-Correlated trend distance (id = SelfCorrelated) or Pattern-Based trend distance (id = PatternBased)
     *
     * @throws IOException
     */
    @Override
    public void writeData() throws IOException {
        //create the file if not created
        File file = new File("./src/txt/Choices.txt");
        file.createNewFile();

        //write new informations
        FileWriter fichier = new FileWriter(file);

        try {
            if (RbSelfCorrelated.isSelected()) {
                fichier.write ("SelfCorrelated");
            } else if (RbPatternBased.isSelected()) {
                fichier.write ("Static " + TfValue.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        fichier.close();
    }

    /**
     * Verify if a radio Button is selected and verify if all conditions are respected
     */
    public boolean isSelected(){
        if (RbSelfCorrelated.isSelected() || (RbPatternBased.isSelected() && isAnInt(getTfValue().getText()))){
            return true;
        }
        return false;
    }

    /**
     * Check if the value typed by the user is an int
     * if false, display a warning
     */
    public void Check(){
        if (!isAnInt(getTfValue().getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("You must type an integer !");
            alert.showAndWait();
        }
    }

    /**
     * Check if the value is an int
     *
     * @param string
     * @return
     */
    private boolean isAnInt(String string){
        return string.matches("[0-9]*") && !string.equals("");
    }
}
