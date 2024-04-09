package com.networkproject.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("edit-system-table.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 450);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        Main.stage = stage;
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeScene(String sceneName, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(sceneName+".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(Main.class.getResource("/styles/main.css").toExternalForm());
            stage.setScene(scene);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}