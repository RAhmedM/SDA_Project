package com.example.javafx;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class main extends Application{

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showLoginForm(this.primaryStage);
    }
    public void showLoginForm(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 520, 400));
        primaryStage.show();
    }


    public static void showRegistrationForm(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(main.class.getResource("Register.fxml"));
        primaryStage.setTitle("Register");
        primaryStage.setScene(new Scene(root, 655, 430));
        primaryStage.show();
    }

    public static void showStudentHomePage(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(main.class.getResource("StudentHomePage.fxml"));
        primaryStage.setTitle("Student Home Page");
        primaryStage.setScene(new Scene(root, 655, 430));
        primaryStage.show();
    }

    public static void main(String []args) {launch((args));}
}
