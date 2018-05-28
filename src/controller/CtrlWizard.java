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

    private CtrlPattern ctrlPattern;
    private CtrlInputStream ctrlInputStream;
    private CtrlWindows ctrlWindows;
    private CtrlTrend ctrlTrend;
    private CtrlDistance ctrlDistance;
    private CtrlThreshold ctrlThreshold;
    private CtrlResume ctrlResume;

    @FXML
    private Button BtnBack, BtnNext;
    @FXML
    public AnchorPane ApDetails, ApCorp;
    @FXML
    private Label LblPattern,LblInput,LblWindows,LblTrend,LblDistance,LblThreshold,LblNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/xml/Pattern.fxml"));
            ApDetails.getChildren().setAll((AnchorPane)loader.load());
            ctrlPattern = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LblNumber.setText("1");
        BtnBack.setDisable(true);
        LblPattern.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        BtnNext.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    switchNextPage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        BtnBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switchBackPage();
            }
        });
    }

    /**
     * Switch for Next Button
     */
    private void switchNextPage() throws IOException {
        FXMLLoader loader;
        String name;

        if (LblNumber.getText().equals("1")) {
            //storing new data according to user choices
            ctrlPattern.writeData();

            //load the new xml and the new controller
            name = "../application/xml/Input_stream.fxml";
            loader = new FXMLLoader(getClass().getResource(name));
            ApDetails.getChildren().setAll((AnchorPane)loader.load());
            ctrlInputStream = loader.getController();

            //GUI updates
            LblNumber.setText("2");
            BtnBack.setDisable(false);
            LblPattern.setBorder(null);
            LblInput.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        } else if (LblNumber.getText().equals("2")) {
            //storing new data according to user choices
            ctrlInputStream.writeData();

            //load the new xml and the new controller
            name = "../application/xml/Windows.fxml";
            loader = new FXMLLoader(getClass().getResource(name));
            ApDetails.getChildren().setAll((AnchorPane)loader.load());
            ctrlWindows = loader.getController();

            //GUI updates
            LblNumber.setText("3");
            LblInput.setBorder(null);
            LblWindows.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        } else if (LblNumber.getText().equals("3")) {
            //storing new data according to user choices
            ctrlWindows.writeData();

            //load the new xml and the new controller
            name = "../application/xml/Trend.fxml";
            loader = new FXMLLoader(getClass().getResource(name));
            ApDetails.getChildren().setAll((AnchorPane)loader.load());
            ctrlTrend = loader.getController();

            //GUI updates
            LblNumber.setText("4");
            LblWindows.setBorder(null);
            LblTrend.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        } else if (LblNumber.getText().equals("4")) {
            //storing new data according to user choices
            ctrlTrend.writeData();

            //load the new xml and the new controller
            name = "../application/xml/Distance.fxml";
            loader = new FXMLLoader(getClass().getResource(name));
            ApDetails.getChildren().setAll((AnchorPane)loader.load());
            ctrlDistance = loader.getController();

            //GUI updates
            LblNumber.setText("5");
            LblTrend.setBorder(null);
            LblDistance.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        } else if (LblNumber.getText().equals("5")) {
            //storing new data according to user choices
            ctrlDistance.writeData();

            //load the new xml and the new controller
            name = "../application/xml/Threshold.fxml";
            loader = new FXMLLoader(getClass().getResource(name));
            ApDetails.getChildren().setAll((AnchorPane)loader.load());
            ctrlThreshold = loader.getController();

            //GUI updates
            LblNumber.setText("6");
            LblDistance.setBorder(null);
            LblThreshold.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        } else if (LblNumber.getText().equals("6")) {
            //storing new data according to user choices
            ctrlThreshold.writeData();

            //load the new xml and the new controller
            name = "../application/xml/Resume.fxml";
            loader = new FXMLLoader(getClass().getResource(name));
            ApDetails.getChildren().setAll((AnchorPane)loader.load());
            ctrlResume = loader.getController();

            //GUI updates
            BtnNext.setText("Start");
        }
    }

    /**
     * Switch for Back Button
     */
    private void switchBackPage(){
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