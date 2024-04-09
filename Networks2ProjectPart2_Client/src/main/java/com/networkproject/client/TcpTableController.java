package com.networkproject.client;


import com.google.gson.*;
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

public class TcpTableController implements Initializable {

    @FXML
    private Button BacktoMainPage;

    @FXML
    private Button GetTCPTable;

    @FXML
    private TableColumn<NetworkInfo, String> tcpConnState;

    @FXML
    private TableColumn<NetworkInfo, String> tcpConnLocalAddress;

    @FXML
    private TableColumn<NetworkInfo, String> tcpConnLocalPort;

    @FXML
    private TableColumn<NetworkInfo, String> tcpConnRemAddress;

    @FXML
    private TableColumn<NetworkInfo, String> tcpConnRemPort;



    @FXML
    private TableView<NetworkInfo> tableView;

    @FXML
    void BacktoMainPage(ActionEvent event) {
        Main.changeScene("main", "Home");

    }

    @FXML
    void GetTCPTable(ActionEvent event) {
        try {

            String urlStr = "http://localhost/servers/server.php?page=tcpTable";

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




            ObservableList<TcpTableController.NetworkInfo> data = FXCollections.observableArrayList();

            JsonArray tcpConnStateArray = jsonObject.get("tcpConnState").getAsJsonArray();
            JsonArray tcpConnLocalAddressArray = jsonObject.get("tcpConnLocalAddress").getAsJsonArray();
            JsonArray tcpConnLocalPortArray = jsonObject.get("tcpConnLocalPort").getAsJsonArray();
            JsonArray tcpConnRemAddressArray = jsonObject.get("tcpConnRemAddress").getAsJsonArray();
            JsonArray tcpConnRemPortArray = jsonObject.get("tcpConnRemPort").getAsJsonArray();

            for (int i = 0; i < tcpConnStateArray.size(); i++) {
                data.add(new TcpTableController.NetworkInfo(tcpConnStateArray.get(i).getAsString(),tcpConnLocalAddressArray.get(i).getAsString(), tcpConnLocalPortArray.get(i).getAsString(), tcpConnRemAddressArray.get(i).getAsString(), tcpConnRemPortArray.get(i).getAsString()));

            }

            tableView.setItems(data);


        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tcpConnState.setCellValueFactory(new PropertyValueFactory<>("tcpConnState"));
        tcpConnLocalAddress.setCellValueFactory(new PropertyValueFactory<>("tcpConnLocalAddress"));
        tcpConnLocalPort.setCellValueFactory(new PropertyValueFactory<>("tcpConnLocalPort"));
        tcpConnRemAddress.setCellValueFactory(new PropertyValueFactory<>("tcpConnRemAddress"));
        tcpConnRemPort.setCellValueFactory(new PropertyValueFactory<>("tcpConnRemPort"));

    }

    public class NetworkInfo {
        private final String tcpConnState;
        private final String tcpConnLocalAddress;
        private final String tcpConnLocalPort;
        private final String tcpConnRemAddress;
        private final String tcpConnRemPort;

        public NetworkInfo(String tcpConnState, String tcpConnLocalAddress, String tcpConnLocalPort, String tcpConnRemAddress, String tcpConnRemPort) {
            this.tcpConnState = tcpConnState;
            this.tcpConnLocalAddress = tcpConnLocalAddress;
            this.tcpConnLocalPort = tcpConnLocalPort;
            this.tcpConnRemAddress = tcpConnRemAddress;
            this.tcpConnRemPort = tcpConnRemPort;
        }

        // Getter methods
        public String getTcpConnState() {
            return tcpConnState;
        }

        public String getTcpConnLocalAddress() {
            return tcpConnLocalAddress;
        }

        public String getTcpConnLocalPort() {
            return tcpConnLocalPort;
        }

        public String getTcpConnRemAddress() {
            return tcpConnRemAddress;
        }

        public String getTcpConnRemPort() {
            return tcpConnRemPort;
        }
    }

}