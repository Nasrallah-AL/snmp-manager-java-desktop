package com.networkproject.client;

import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

    private boolean isVerified1;
    private boolean isVerified2;



    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField passTextField;

    @FXML
    private Label status1;

    @FXML
    private Label status2;

    @FXML
    private Label loginStatus;



    @FXML
    protected void onVerify1BtnClicked() {
        try {

            String userName = nameTextField.getText();
            String userId = idTextField.getText();
            String password = passTextField.getText();


            String urlStr = "http://localhost:8080/tomcatserver/LoginServlet";

            URL myURL = new URL(urlStr);
            HttpURLConnection myConn = (HttpURLConnection) myURL.openConnection();

            // Set up the connection properties
            myConn.setRequestMethod("POST");
            myConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            myConn.setDoOutput(true);
            myConn.setDoInput(true);
            myConn.setUseCaches(false);

            String postData = "{\"name\": \"" + userName + "\", \"password\": \"" + password + "\"}";
            OutputStream outputStream = myConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(postData);
            writer.flush();
            writer.close();

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

            // Set The Response Text to be OK or NO
            status1.setText(response.toString());
            isVerified1 = response.toString().equals("OK");


        } catch (Exception e) {
            e.printStackTrace();
            status1.setText("FAILED");


        }
    }

    @FXML
    protected void onVerify2BtnClicked() {
        try {

            String userName = nameTextField.getText();
            String userId = idTextField.getText();
            String password = passTextField.getText();


            String urlStr = "http://localhost:8080/tomcatserver/auth.jsp";

            URL myURL = new URL(urlStr);
            HttpURLConnection myConn = (HttpURLConnection) myURL.openConnection();

            // Set up the connection properties
            myConn.setRequestMethod("POST");
            myConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            myConn.setDoOutput(true);
            myConn.setDoInput(true);
            myConn.setUseCaches(false);

            String postData = "{\"id\": \"" + userId + "\", \"password\": \"" + password + "\"}";
            OutputStream outputStream = myConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(postData);
            writer.flush();
            writer.close();

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

            // Set The Response Text to be OK or NO
            status2.setText(response.toString());
            isVerified2 = response.toString().equals("OK");



        } catch (Exception e) {
            e.printStackTrace();
            status2.setText("FAILED");


        }
    }

    @FXML
    protected void onLoginBtnClicked() {
        if(isVerified1 && isVerified2) {
            loginStatus.setText("Login Success");
            Main.changeScene("main", "Home");
        }else {
            loginStatus.setText("Please verify on both server");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}