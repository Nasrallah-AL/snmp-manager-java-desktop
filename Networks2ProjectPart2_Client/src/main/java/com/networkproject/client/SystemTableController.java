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

public class SystemTableController implements Initializable {

    @FXML
    private Button BacktoMainPage;

    @FXML
    private Button EditsystemData;

    @FXML
    private Button Getsystemdata;

    @FXML
    private TableColumn<?, ?> SystemID;

    @FXML
    private TableColumn<?, ?> SystemDescription;

    @FXML
    private TableColumn<?, ?> SystemUpTime;

    @FXML
    private TableColumn<?, ?> SystemName;

    @FXML
    private TableColumn<?, ?> SystemContact;

    @FXML
    private TableColumn<?, ?> SystemLocation;


    @FXML
    private TableView<NetworkInfo> tableView;

    @FXML
    void BacktoMainPage(ActionEvent event) {
        Main.changeScene("main", "Home");
        ;
    }

    @FXML
    void EditSystemData(ActionEvent event) {
        Main.changeScene("edit-system-table", "System Table Configuration");

    }

    @FXML
    void GetsystemData(ActionEvent event) {
        try {

            String urlStr = "http://localhost/servers/server.php?page=systemGroup";

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




            ObservableList<SystemTableController.NetworkInfo> data = FXCollections.observableArrayList();

            String SystemID = jsonObject.get("System ID").getAsString();
            String SystemDescription = jsonObject.get("System Description").getAsString();
            String SystemUpTime = jsonObject.get("System UpTime").getAsString();
            String SystemName = jsonObject.get("System Name").getAsString();
            String SystemContact = jsonObject.get("System Contact").getAsString();
            String SystemLocation = jsonObject.get("System Location").getAsString();


            data.add(new SystemTableController.NetworkInfo(SystemID, SystemDescription, SystemUpTime, SystemName, SystemContact, SystemLocation));



            tableView.setItems(data);


        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SystemID.setCellValueFactory(new PropertyValueFactory<>("SystemID"));
        SystemDescription.setCellValueFactory(new PropertyValueFactory<>("SystemDescription"));
        SystemUpTime.setCellValueFactory(new PropertyValueFactory<>("SystemUpTime"));
        SystemName.setCellValueFactory(new PropertyValueFactory<>("SystemName"));
        SystemContact.setCellValueFactory(new PropertyValueFactory<>("SystemContact"));
        SystemLocation.setCellValueFactory(new PropertyValueFactory<>("SystemLocation"));
    }

    public class NetworkInfo {
        private final String SystemID;
        private final String SystemDescription;
        private final String SystemUpTime;
        private final String SystemName;
        private final String SystemContact;
        private final String SystemLocation;

        public NetworkInfo(String systemID, String systemDescription, String systemUpTime, String systemName, String systemContact, String systemLocation) {
            SystemID = systemID;
            SystemDescription = systemDescription;
            SystemUpTime = systemUpTime;
            SystemName = systemName;
            SystemContact = systemContact;
            SystemLocation = systemLocation;
        }

        public String getSystemID() {
            return SystemID;
        }

        public String getSystemDescription() {
            return SystemDescription;
        }

        public String getSystemUpTime() {
            return SystemUpTime;
        }

        public String getSystemName() {
            return SystemName;
        }

        public String getSystemContact() {
            return SystemContact;
        }

        public String getSystemLocation() {
            return SystemLocation;
        }
    }

}
