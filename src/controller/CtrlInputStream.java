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

    public TextField getTfPort() {
        return TfPort;
    }

    public RadioButton getRbPreRecorded() {
        return RbPreRecorded;
    }

    public RadioButton getRbTCPConnection() {
        return RbTCPConnection;
    }

    public RadioButton getRbStandardInput() {
        return RbStandardInput;
    }

    public Label getLblBrowserL() {
        return LblBrowserL;
    }

    public Label getLblBrowserI() {
        return LblBrowserI;
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
                fichier.write("\nextract a fiel called " + Filename);
            } else if (RbStandardInput.isSelected() && !LblBrowserI.getText().equals("No file selected")){
                fichier.write ("standard input");
                fichier.write("\nextract a fiel called " + Filename);
            } else if (RbTCPConnection.isSelected()){
                fichier.write ("TCP Connection input");
                fichier.write("\nextract data from port n°" + TfPort.getText());
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
        if ((RbPreRecorded.isSelected() && !LblBrowserL.getText().equals("No file selected"))
                || (RbStandardInput.isSelected() && !LblBrowserI.getText().equals("No file selected"))
                || (RbTCPConnection.isSelected() && isAnInt(getTfPort().getText()))){
            return true;
        }
        return false;
    }

    public void Check(){
        if (!isAnInt(getTfPort().getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("You must type an integer !");
            alert.showAndWait();
        }
    }

    public boolean isAnInt(String string){
        return string.matches("[0-9]*") && !string.equals("");
    }
}
