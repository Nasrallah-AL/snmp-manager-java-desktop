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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SNMPGroupsController implements Initializable {

    @FXML
    private Button BacktoMainPage;

    @FXML
    private Button GetSNMPTable;

    @FXML
    private TableColumn<?, ?> ID;

    @FXML
    private TableColumn<?, ?> Name;

    @FXML
    private TableColumn<?, ?> Value;

    @FXML
    private TableView<NetworkInfo> tableView;

    @FXML
    void BacktoMainPage(ActionEvent event) {
        Main.changeScene("main", "Home");

    }

    @FXML
    void GetSNMPTable(ActionEvent event) {
        try {
            ArrayList<String> oidObjectName = new ArrayList<>(Arrays.asList(
                    "snmpInPkts",
                    "snmpOutPkts",
                    "snmpInBadVersions",
                    "snmpInBadCommunityNames",
                    "snmpInBadCommunityUses",
                    "snmpInASNParseErrs",
                    "SKIP",
                    "snmpInTooBigs",
                    "snmpInNoSuchNames",
                    "snmpInBadValues",
                    "snmpInReadOnlys",
                    "snmpInGenErrs",
                    "snmpInTotalReqVars",
                    "snmpInTotalSetVars",
                    "snmpInGetRequests",
                    "snmpInGetNexts",
                    "snmpInSetRequests",
                    "snmpInGetResponses",
                    "snmpInTraps",
                    "snmpOutTooBigs",
                    "snmpOutNoSuchNames",
                    "snmpOutBadValues",
                    "SKIP",
                    "snmpOutGenErrs",
                    "snmpOutGetRequests",
                    "snmpOutGetNexts",
                    "snmpOutSetRequests",
                    "snmpOutGetResponses",
                    "snmpOutTraps",
                    "snmpEnableAuthenTraps"
            ));

            String urlStr = "http://localhost/servers/server.php?page=snmpStatistics";

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
            JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("get_method");




            ObservableList<SNMPGroupsController.NetworkInfo> data = FXCollections.observableArrayList();

            for (java.util.Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                int key = Integer.parseInt(entry.getKey());
                JsonElement value = entry.getValue();
                data.add(new SNMPGroupsController.NetworkInfo(entry.getKey(),oidObjectName.get(key-1), value.getAsString()));

            }


            tableView.setItems(data);


        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Value.setCellValueFactory(new PropertyValueFactory<>("value"));

    }

    public class NetworkInfo {
        private final String ID;
        private final String name;
        private final String value;


        public NetworkInfo(String ID, String name, String value) {
            this.ID = ID;
            this.name = name;
            this.value = value;
        }

        public String getID() {
            return ID;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

}