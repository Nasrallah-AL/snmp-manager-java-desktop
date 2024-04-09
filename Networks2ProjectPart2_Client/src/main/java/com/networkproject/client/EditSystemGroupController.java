package com.networkproject.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class EditSystemGroupController {

    @FXML
    private TextField Systemcontact;

    @FXML
    private TextField Systemlocation;

    @FXML
    private TextField Systemname;

    @FXML
    private Button cancel;

    @FXML
    private Button submit;


    @FXML
    void cancel(ActionEvent event) {
        Main.changeScene("system-table", "System Group");
    }

    @FXML
    void submit(ActionEvent event) {
        try {

            String sysName = Systemname.getText();
            String sysContact = Systemcontact.getText();
            String sysLocation = Systemlocation.getText();


            String urlStr = "http://localhost:80/servers/server.php";

            URL myURL = new URL(urlStr);
            HttpURLConnection myConn = (HttpURLConnection) myURL.openConnection();

            // Set up the connection properties
            myConn.setRequestMethod("POST");
            myConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            myConn.setDoOutput(true);
            myConn.setDoInput(true);
            myConn.setUseCaches(false);

            String postData = "{";

            if (!sysContact.isEmpty()) {
                postData += "\"System Contact\": \"" + sysContact + "\",";
            }

            if (!sysName.isEmpty()) {
                postData += "\"System Name\": \"" + sysName + "\",";
            }

            if (!sysLocation.isEmpty()) {
                postData += "\"System Location\": \"" + sysLocation + "\",";
            }


            if (postData.endsWith(",")) {
                postData = postData.substring(0, postData.length() - 1);
            }

            postData += "}";
            System.out.println(postData);



            OutputStream outputStream = myConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            writer.write(postData);
            writer.flush();
            writer.close();

            // Connect to the server
            myConn.connect();


            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(myConn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }


            Main.changeScene("system-table", "System Group");




        } catch (Exception e) {
            e.printStackTrace();


        }
    }

}