module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires com.fazecast.jSerialComm;

    opens com.example.javafx to javafx.fxml;
    exports com.example.javafx;
}