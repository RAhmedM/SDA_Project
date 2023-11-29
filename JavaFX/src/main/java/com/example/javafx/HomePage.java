package com.example.javafx;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;


public class HomePage {

    @FXML
    private Button adminButton;
    @FXML
    private Button StudentButton;

    @FXML
    private void adminButtonClicked(ActionEvent event) throws Exception {
        Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        homeStage.close();

        Stage primaryStage = new Stage();
        main Main = new main();
        main.showAdminLoginPage(primaryStage);
    }
    @FXML
    private void studentButtonClicked(ActionEvent event) throws Exception {
        Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        homeStage.close();

        Stage primaryStage = new Stage();
        main Main = new main();
        Main.showLoginForm(primaryStage);
    }
}
