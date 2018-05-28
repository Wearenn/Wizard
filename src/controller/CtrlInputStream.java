package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the second page Input_stream.fxml
 * package: application.xml
 */

public class CtrlInputStream implements Initializable {

    @FXML
    private RadioButton RbPreRecorded, RbTCPConnection, RbStandardInput;
    @FXML
    private Button BtnBrowserL, BtnBrowserI;
    @FXML
    private Label LblBrowserL, LblBrowserI;
    @FXML
    private TextField TfPort;
    @FXML
    private Button BtnContinue;

    public Button getBtnContinue() {
        return BtnContinue;
    }

    public TextField getTfPort() {
        return TfPort;
    }

    //private Desktop desktop = Desktop.getDesktop();
    private String Filename = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BtnBrowserI.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                final FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select a log file");
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                //fileChooser.getExtensionFilters().add(filter);
                Node node = (Node) event.getSource();
                File file = fileChooser.showOpenDialog(node.getScene().getWindow());
                if (file != null) {
                    openFileI(file);
                }
            }
        });
        BtnBrowserL.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                final FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select a log file");
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                //fileChooser.getExtensionFilters().add(filter);
                Node node = (Node) event.getSource();
                File file = fileChooser.showOpenDialog(node.getScene().getWindow());
                if (file != null) {
                    openFileL(file);
                }
                //fileChooser();
            }
        });
    }

    /**
     * We save in file "Choices.txt" the id of the user choice
     * Here the user have three choices :
     * Select Pre-recorded log (id = PreRecorded) or Standard input (id = StandardInput) or TCP connection (id = TCPConnection)
     *
     * @throws IOException
     */
    public void writeData() throws IOException {
        //write new informations
        FileWriter fichier = new FileWriter(new File("./src/txt/Choices.txt"), true);
        fichier.write("\n");

        try {
            if (RbPreRecorded.isSelected() && !LblBrowserL.getText().equals("No file selected")){
                fichier.write ("pre-recorded input");
                fichier.write("\n" + Filename);
            } else if (RbStandardInput.isSelected() && !LblBrowserI.getText().equals("No file selected")){
                fichier.write ("standard input");
                fichier.write("\n" + Filename);
            } else if (RbTCPConnection.isSelected()){
                fichier.write ("TCP Connection input");
                fichier.write("\n" + TfPort.getText());
            }
        } catch (IOException e) {
            e.getMessage();
        }

        fichier.close();
    }

    /*@FXML protected void fileChooser(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
    }*/

    private void openFileI(File file) {
        LblBrowserI.setText(file.getName());
        Filename = file.getName();
    }

    private void openFileL(File file) {
        LblBrowserL.setText(file.getName());
        Filename = file.getName();
    }

    /**
     * Verify if a radio Button is selected and verify if all conditions are respected
     */
    public boolean isSelected(){
        boolean isSelected = false;
        if ((RbPreRecorded.isSelected() && !LblBrowserL.getText().equals("No file selected"))
                || (RbStandardInput.isSelected() && !LblBrowserI.getText().equals("No file selected"))
                || (RbTCPConnection.isSelected() && isAnInt(getTfPort().getText()))){
            isSelected = true;
        } else if (RbTCPConnection.isSelected() && !isAnInt(getTfPort().getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("You must type an integer !");
            alert.showAndWait();
        } else if ((RbPreRecorded.isSelected() && LblBrowserL.getText().equals("No file selected"))
                || (RbStandardInput.isSelected() && LblBrowserI.getText().equals("No file selected"))){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("You must select a log file");
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

    public boolean isAnInt(String string){
        if (string.matches("[0-9]*") && !string.equals(""))  return true;
        return false;
    }
}
