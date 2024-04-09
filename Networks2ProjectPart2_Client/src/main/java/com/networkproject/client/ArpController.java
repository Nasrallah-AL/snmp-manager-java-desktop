package com.networkproject.client;

import com.google.gson.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class ArpController implements Initializable {

    @FXML
    private Button BacktoMainPage;

    @FXML
    private Button GetARPTable;

    @FXML
    private TableColumn<NetworkInfo, String> Interface;

    @FXML
    private TableColumn<NetworkInfo, String> Network;

    @FXML
    private TableColumn<NetworkInfo, String> Physical;

    @FXML
    private TableColumn<NetworkInfo, String> Type;

    @FXML
    private TableView<NetworkInfo> tableView;

    @FXML
    void BacktoMainPage(ActionEvent event) {
        Main.changeScene("main", "Home");

    }

    @FXML
    void GetARPTable(ActionEvent event) {
        try {

            String urlStr = "http://localhost/servers/server.php?page=arpTable";

            URL myURL = new URL(urlStr);
            HttpURLConnection myConn = (HttpURLConnection) myURL.openConnection();

            // Set up the connection properties
            myConn.setRequestMethod("GET");
            myConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            myConn.setDoOutput(true);
            myConn.setDoInput(true);
            myConn.setUseCaches(false);

            // Connect to the server
            myConn.connect();

            // Read the response
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(myConn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(response.toString());
            JsonObject jsonObject = jsonElement.getAsJsonObject();

//            jsonObject.asMap().forEach((s, jsonElement1) -> {
//                System.out.println(s);
//            });


            ObservableList<NetworkInfo> data = FXCollections.observableArrayList();

            JsonArray interfaceArray = jsonObject.get("ipNetToMediaIfIndex").getAsJsonArray();
            JsonArray networkArray = jsonObject.get("ipNetToMediaPhysAddress").getAsJsonArray();
            JsonArray physicalArray = jsonObject.get("ipNetToMediaNetAddress").getAsJsonArray();
            JsonArray typeArray = jsonObject.get("ipNetToMediaType").getAsJsonArray();

            for (int i = 0; i < interfaceArray.size(); i++) {
                data.add(new NetworkInfo(interfaceArray.get(i).getAsString(),networkArray.get(i).getAsString(), physicalArray.get(i).getAsString(), typeArray.get(i).getAsString()));

            }

            tableView.setItems(data);


        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Interface.setCellValueFactory(new PropertyValueFactory<>("interfaceValue"));
        Network.setCellValueFactory(new PropertyValueFactory<>("networkValue"));
        Physical.setCellValueFactory(new PropertyValueFactory<>("physicalValue"));
        Type.setCellValueFactory(new PropertyValueFactory<>("typeValue"));

    }
    public class NetworkInfo {
        private final String interfaceValue;
        private final String networkValue;
        private final String physicalValue;
        private final String typeValue;

        public NetworkInfo(String interfaceValue, String networkValue, String physicalValue, String typeValue) {
            this.interfaceValue = interfaceValue;
            this.networkValue = networkValue;
            this.physicalValue = physicalValue;
            this.typeValue = typeValue;
        }

        // Getter methods
        public String getInterfaceValue() {
            return interfaceValue;
        }

        public String getNetworkValue() {
            return networkValue;
        }

        public String getPhysicalValue() {
            return physicalValue;
        }

        public String getTypeValue() {
            return typeValue;
        }
    }
}


