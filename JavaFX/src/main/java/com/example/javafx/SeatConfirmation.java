//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.javafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class SeatConfirmation {
    @FXML
    public TextArea displaytext;
    String inputData2 = "0098866";

    public SeatConfirmation() {
    }

    public void seat(String inputData) {
        String url = "jdbc:mysql://127.0.0.1:3306/sda_project";
        String username = "root";
        String password = "8686";
        String inputData2 = "0098866";
        this.displaytext.setText("Thank You " + inputData2 + " For Using Our App your seat in busid " + inputData + " has been confirmed");
        String sqlQuery1 = "UPDATE bus SET total_Seats = total_Seats - 1 WHERE BusID = ?";
        String sqlQuery2 = "UPDATE reservations INNER JOIN wallet ON reservations.stdID = wallet.std_ID SET reservations.routeBusID = ?, reservations.price = 10, reservations.paymentcleared = 1, wallet.balance = wallet.balance - 10 WHERE reservations.stdID = ? AND wallet.std_ID = ?";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            try {
                PreparedStatement statement1 = connection.prepareStatement(sqlQuery1);

                try {
                    PreparedStatement statement2 = connection.prepareStatement(sqlQuery2);

                    try {
                        connection.setAutoCommit(false);
                        statement1.setString(1, inputData);
                        statement1.executeUpdate();
                        statement2.setString(1, inputData);
                        statement2.setString(2, inputData2);
                        statement2.setString(3, inputData2);
                        statement2.executeUpdate();
                        connection.commit();
                    } catch (Throwable var16) {
                        if (statement2 != null) {
                            try {
                                statement2.close();
                            } catch (Throwable var15) {
                                var16.addSuppressed(var15);
                            }
                        }

                        throw var16;
                    }

                    if (statement2 != null) {
                        statement2.close();
                    }
                } catch (Throwable var17) {
                    if (statement1 != null) {
                        try {
                            statement1.close();
                        } catch (Throwable var14) {
                            var17.addSuppressed(var14);
                        }
                    }

                    throw var17;
                }

                if (statement1 != null) {
                    statement1.close();
                }
            } catch (Throwable var18) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Throwable var13) {
                        var18.addSuppressed(var13);
                    }
                }

                throw var18;
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var19) {
            var19.printStackTrace();
        }

    }
}
