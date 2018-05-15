package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main application
 */

public class CtrlWizard implements Initializable {

    @FXML
    private Button BtnBack, BtnNext;
    @FXML
    private AnchorPane ApDetails;
    @FXML
    private Label LblPattern,LblInput,LblWindows,LblTrend,LblDistance,LblThreshold,LblNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LblNumber.setText("1");
        BtnBack.setDisable(true);
        LblPattern.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        BtnNext.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switchNextPage();
            }
        });
        BtnBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switchBachPage();
            }
        });
    }

    public void switchNextPage(){
        String name = null;

        /*********Affichage du Resultat********/
        if (BtnNext.getText().equals("Valider")){
            name = "../application/xml/Resume.fxml";
            BtnNext.setVisible(false);
            BtnBack.setVisible(false);
            LblNumber.setVisible(false);
        }

        /**********Gestion des fenetres*********/
        if (LblNumber.getText().equals("1")) {
            name = "../application/xml/Input_stream.fxml";
            LblNumber.setText("2");
            BtnBack.setDisable(false);
            LblPattern.setBorder(null);
            LblInput.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        } else if (LblNumber.getText().equals("2")){
            name = "../application/xml/Windows.fxml";
            LblNumber.setText("3");
            LblInput.setBorder(null);
            LblWindows.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        } else if (LblNumber.getText().equals("3")){
            name = "../application/xml/Trend.fxml";
            LblNumber.setText("4");
            LblWindows.setBorder(null);
            LblTrend.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        } else if (LblNumber.getText().equals("4")){
            name = "../application/xml/Distance.fxml";
            LblNumber.setText("5");
            LblTrend.setBorder(null);
            LblDistance.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        } else if (LblNumber.getText().equals("5")){
            name = "../application/xml/Threshold.fxml";
            LblNumber.setText("6");
            BtnNext.setText("Valider");
            LblDistance.setBorder(null);
            LblThreshold.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        }

        try {
            ApDetails.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource(name)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void switchBachPage(){
        String name = null;

        if (LblNumber.getText().equals("2")) {
            name = "../application/xml/Pattern.fxml";
            LblNumber.setText("1");
            BtnBack.setDisable(true);
            LblInput.setBorder(null);
            LblPattern.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        } else if (LblNumber.getText().equals("3")){
            name = "../application/xml/Input_stream.fxml";
            LblNumber.setText("2");
            LblWindows.setBorder(null);
            LblInput.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        } else if (LblNumber.getText().equals("4")){
            name = "../application/xml/Windows.fxml";
            LblNumber.setText("3");
            LblTrend.setBorder(null);
            LblWindows.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        } else if (LblNumber.getText().equals("5")){
            name = "../application/xml/Trend.fxml";
            LblNumber.setText("4");
            LblDistance.setBorder(null);
            LblTrend.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        } else if (LblNumber.getText().equals("6")){
            name = "../application/xml/Distance.fxml";
            LblNumber.setText("5");
            BtnNext.setText("Next >");
            LblThreshold.setBorder(null);
            LblDistance.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        }

        try {
            ApDetails.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource(name)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}