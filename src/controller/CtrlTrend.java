package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CtrlTrend {

    @FXML
    private RadioButton RbDirectSize, RbSize, RbOther, RbSource, RbDestination;
    @FXML
    private RadioButton RbRunningA, RbVectorOfM, RbDistinctOcc, RbValueDis, RbCumulativeSum;
    @FXML
    private TextField TxtOther;
    @FXML
    private Slider SliderVector;

    public RadioButton getRbDirectSize() {
        return RbDirectSize;
    }

    public RadioButton getRbSize() {
        return RbSize;
    }

    public RadioButton getRbOther() {
        return RbOther;
    }

    public RadioButton getRbSource() {
        return RbSource;
    }

    public RadioButton getRbDestination() {
        return RbDestination;
    }

    public RadioButton getRbRunningA() {
        return RbRunningA;
    }

    public RadioButton getRbVectorOfM() {
        return RbVectorOfM;
    }

    public RadioButton getRbDistinctOcc() {
        return RbDistinctOcc;
    }

    public RadioButton getRbValueDis() {
        return RbValueDis;
    }

    public RadioButton getRbCumulativeSum() {
        return RbCumulativeSum;
    }

    public TextField getTxtOther() {
        return TxtOther;
    }

    public void writeData() throws IOException {
        //write new informations
        FileWriter fichier = new FileWriter(new File("./src/txt/Choices.txt"),true);
        fichier.write("\n");

        try {
            if (RbDirectSize.isSelected()){
                fichier.write ("Direct Value");
            } else if (RbSize.isSelected()){
                fichier.write ("Size");
            } else if (RbOther.isSelected() && !TxtOther.getText().equals("")){
                fichier.write ("Other " + TxtOther.getText());
            } else if (RbSource.isSelected()){
                fichier.write ("Source");
            } else if (RbDestination.isSelected()){
                fichier.write ("Destination");
            }

            fichier.write("\n");

            if (RbRunningA.isSelected()){
                fichier.write ("average of the stream over the entire window");
            } else if (RbVectorOfM.isSelected()){
                fichier.write ((int) SliderVector.getValue() + " first statistical moments over the entire window");
            } else if (RbDistinctOcc.isSelected()){
                fichier.write ("number of distinct values observed in the window");
            } else if (RbValueDis.isSelected()){
                fichier.write ("distribution of values observed in the window");
            } else if (RbCumulativeSum.isSelected()){
                fichier.write ("sum of all values over the window");
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
        if ((RbDirectSize.isSelected() || RbSize.isSelected() || (RbOther.isSelected() && !TxtOther.getText().equals(""))
                || RbSource.isSelected() || RbDestination.isSelected()) && (RbRunningA.isSelected() || RbVectorOfM.isSelected()
                || RbDistinctOcc.isSelected() || RbValueDis.isSelected() || RbCumulativeSum.isSelected())){
            return true;
        }
        return false;
    }

    /**
     * Check if the value typed by the user is an int
     * if false, display a warning
     */
    public void Check(){
        if (!isAnInt(getTxtOther().getText())){
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
