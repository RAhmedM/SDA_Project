package com.example.javafx;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application{

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showHomePage(this.primaryStage);
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

    public static void showHomePage(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(main.class.getResource("homePage.fxml"));
        primaryStage.setTitle("Home Page");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void showAdminLoginPage(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(main.class.getResource("adminLogin.fxml"));
        primaryStage.setTitle("Admin Login Page");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    public static void showAdminHomePage(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(main.class.getResource("adminHomePage.fxml"));
        primaryStage.setTitle("Admin Home Page");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void show_rfidValidationStudent(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(main.class.getResource("rfidValidation_Student.fxml"));
        primaryStage.setTitle("RFID Validation Page");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void show_rfidValidationAdmin(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(main.class.getResource("adminRFID_Validation.fxml"));
        primaryStage.setTitle("RFID Validation Page");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
    public static void show_ManageRoutes(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(main.class.getResource("manageRoutes.fxml"));
        primaryStage.setTitle("Manage Routes");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void show_AddRoutes(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(main.class.getResource("addRoute.fxml"));
        primaryStage.setTitle("add Routes");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void show_DeleteRoutes(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(main.class.getResource("deleteRoute.fxml"));
        primaryStage.setTitle("Delete Routes");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
    public static void show_DRouteMain(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(main.class.getResource("DrouteMainpage.fxml"));
        primaryStage.setTitle("Display Routes");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String []args) {launch((args));}
}
