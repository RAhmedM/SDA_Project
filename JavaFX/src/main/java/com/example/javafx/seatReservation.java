//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.javafx;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class seatReservation {
    @FXML
    public Button submit_date_time_destination;
    @FXML
    private TextField inputboxdestination;
    @FXML
    private TextField inputboxtime;

    public seatReservation() {
    }

    public void setSubmit_date_time_destination_onAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("displayroutes.fxml"));
            Parent root = (Parent)loader.load();
            displayroutescontroller nextController = (displayroutescontroller)loader.getController();
            String inputData1 = this.inputboxdestination.getText();
            String inputData2 = this.inputboxtime.getText();
            nextController.initialize(inputData1, inputData2);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage currentStage = (Stage)this.submit_date_time_destination.getScene().getWindow();
            currentStage.close();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

    }

}
