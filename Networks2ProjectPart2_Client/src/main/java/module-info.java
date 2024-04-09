module com.networkproject.client{
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.google.gson;

    opens com.networkproject.client to javafx.fxml;
    exports com.networkproject.client;
}