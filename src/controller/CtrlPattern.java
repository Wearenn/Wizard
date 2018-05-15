package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import data.DAO.DaoPattern;

import java.net.URL;
import java.util.ResourceBundle;

public class CtrlPattern implements Initializable {
    @FXML
    private RadioButton RbSelfCorrelated, RbPatternBased;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DaoPattern.getInstance();

        if (RbSelfCorrelated.isSelected()){

        } else if (RbPatternBased.isSelected()){

        } else {
            System.out.println("You must make a choice !");
        }
    }
}
