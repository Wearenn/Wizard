package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    public Button BtnBack, BtnNext;
    @FXML
    private AnchorPane ApDetails;
    @FXML
    private Label LblPattern,LblInput,LblWindows,LblTrend,LblDistance,LblThreshold,LblNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //loading and implementing of the first page Pattern.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/xml/Pattern.fxml"));
            ApDetails.getChildren().setAll((AnchorPane)loader.load());
            ctrlPattern = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        /*BtnBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    switchBackPage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/

        checkWindowPatternConditions();

        LblNumber.setText("1");
        BtnBack.setVisible(false);
        BtnNext.setDisable(true);
        LblPattern.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
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

            BtnNext.setDisable(true);
            checkWindowInputStreamConditions();

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

            BtnNext.setDisable(true);
            checkWindowTrendConditions();

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

            BtnNext.setDisable(true);
            checkWindowDistanceConditions();

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

            BtnNext.setDisable(true);
            checkWindowThresholdConditions();

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
            BtnNext.setVisible(false);
            BtnBack.setVisible(false);
            LblNumber.setVisible(false);
        }
    }

    /**
     * Check if conditions to move next window are respected
     */
    private void checkWindowPatternConditions(){
        ctrlPattern.getRbSelfCorrelated().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlPattern.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlPattern.getRbPatternBased().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlPattern.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlPattern.getTfValue().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (oldPropertyValue) {
                    if (ctrlPattern.isSelected()){
                        BtnNext.setDisable(false);
                    } else {
                        BtnNext.setDisable(true);
                    }
                }
            }
        });
    }

    /**
     * Check if conditions to move next window are respected
     */
    private void checkWindowInputStreamConditions(){
        ctrlInputStream.getRbPreRecorded().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlInputStream.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlInputStream.getRbStandardInput().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlInputStream.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlInputStream.getRbTCPConnection().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlInputStream.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlInputStream.getLblBrowserL().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (ctrlInputStream.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlInputStream.getTfPort().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (oldPropertyValue) {
                    if (ctrlInputStream.isSelected()){
                        BtnNext.setDisable(false);
                    } else {
                        BtnNext.setDisable(true);
                    }
                }
            }
        });
    }

    /**
     * Check if conditions to move next window are respected
     */
    private void checkWindowTrendConditions(){
        ctrlTrend.getRbCumulativeSum().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlTrend.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlTrend.getRbDestination().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlTrend.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlTrend.getRbDirectSize().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlTrend.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlTrend.getRbDistinctOcc().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlTrend.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlTrend.getRbOther().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlTrend.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlTrend.getRbRunningA().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlTrend.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlTrend.getRbSize().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlTrend.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlTrend.getRbSource().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlTrend.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlTrend.getRbValueDis().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlTrend.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlTrend.getRbVectorOfM().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlTrend.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlTrend.getTxtOther().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (ctrlTrend.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });
    }

    /**
     * Check if conditions to move next window are respected
     */
    private void checkWindowDistanceConditions(){
        ctrlDistance.getRbManhattan().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlDistance.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlDistance.getRbEuclidean().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlDistance.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlDistance.getRbScalar().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlDistance.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlDistance.getRbRatio().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlDistance.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });
    }

    /**
     * Check if conditions to move next window are respected
     */
    private void checkWindowThresholdConditions(){
        ctrlThreshold.getRbLarger().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlThreshold.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlThreshold.getRbSmaller().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ctrlThreshold.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });

        ctrlThreshold.getTfThreshold().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (ctrlThreshold.isSelected()){
                    BtnNext.setDisable(false);
                } else {
                    BtnNext.setDisable(true);
                }
            }
        });
    }
}

/**
 * Switch for Back Button
 */
//Problem when replacing the latest data (how to suppress the last 2 lines in the text file ?)
    /*private void switchBackPage() throws IOException {
        FXMLLoader loader;
        String name;

        if (LblNumber.getText().equals("2")) {
            //load the old xml and the old controller
            name = "../application/xml/Pattern.fxml";
            loader = new FXMLLoader(getClass().getResource(name));
            ApDetails.getChildren().setAll((AnchorPane)loader.load());
            ctrlPattern = loader.getController();

            checkWindowPatternConditions();

            //GUI updates
            LblNumber.setText("1");
            BtnNext.setDisable(true);
            BtnBack.setDisable(true);
            LblInput.setBorder(null);
            LblPattern.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        } else if (LblNumber.getText().equals("3")){
            //load the old xml and the old controller
            name = "../application/xml/Input_stream.fxml";
            loader = new FXMLLoader(getClass().getResource(name));
            ApDetails.getChildren().setAll((AnchorPane)loader.load());
            ctrlInputStream = loader.getController();

            checkWindowInputStreamConditions();

            //GUI updates
            LblNumber.setText("2");
            BtnNext.setDisable(true);
            LblWindows.setBorder(null);
            LblInput.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        } else if (LblNumber.getText().equals("4")){
            //load the old xml and the old controller
            name = "../application/xml/Windows.fxml";
            loader = new FXMLLoader(getClass().getResource(name));
            ApDetails.getChildren().setAll((AnchorPane)loader.load());
            ctrlWindows = loader.getController();

            //GUI updates
            LblNumber.setText("3");
            BtnNext.setDisable(true);
            LblTrend.setBorder(null);
            LblWindows.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        } else if (LblNumber.getText().equals("5")){
            //load the old xml and the old controller
            name = "../application/xml/Trend.fxml";
            loader = new FXMLLoader(getClass().getResource(name));
            ApDetails.getChildren().setAll((AnchorPane)loader.load());
            ctrlTrend = loader.getController();

            checkWindowTrendConditions();

            //GUI updates
            LblNumber.setText("4");
            BtnNext.setDisable(true);
            LblDistance.setBorder(null);
            LblTrend.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        } else if (LblNumber.getText().equals("6")){
            //load the old xml and the old controller
            name = "../application/xml/Distance.fxml";
            loader = new FXMLLoader(getClass().getResource(name));
            ApDetails.getChildren().setAll((AnchorPane)loader.load());
            ctrlDistance = loader.getController();

            checkWindowDistanceConditions();

            //GUI updates
            LblNumber.setText("5");
            BtnNext.setDisable(true);
            BtnNext.setText("Next >");
            LblThreshold.setBorder(null);
            LblDistance.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        }
    }*/