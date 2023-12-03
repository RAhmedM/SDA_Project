package com.example.javafx;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


import java.io.IOException;

public class SempassM extends Application {

    @FXML
    public Button submit_date_time_destination;
    //@FXML
    @FXML
    private TextField inputboxdestination;
    //@FXML
    @FXML
    private TextField inputboxtime;


    public void setSubmit_date_time_destination_onAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Sempass2.fxml"));
            Parent root = loader.load();

            // Access the controller associated with the next FXML
            Sempass2 nextController = loader.getController();

            // Retrieve input data from in1 and in2


            String inputData1 = inputboxdestination.getText(); // Replace in1 with your actual FXID
            String inputData2 = inputboxtime.getText(); // Replace in2 with your actual FXID
            //String inputData1="";
            //String inputData2="";
            // Pass the input data to the next controller
            nextController.initialize(inputData1, inputData2);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Optionally, close the current window
            Stage currentStage = (Stage) submit_date_time_destination.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SempassM.class.getResource("Sempass.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
