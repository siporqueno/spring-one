package com.porejemplo.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegController {
    @FXML
    public TextField loginField;
    @FXML
    public TextField passField;
    @FXML
    public TextField nickField;
    @FXML
    public Button btnReg;
    @FXML
    public Button btnClose;

    Controller controller;

    public void clickClose(ActionEvent actionEvent) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void clickReg(ActionEvent actionEvent) {
        try {
            controller.out.writeUTF("/reg " + loginField.getText() + " " + passField.getText() + " " + nickField.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
