package com.networkproject.client;

import javafx.fxml.FXML;

public class MainPageController {

    @FXML
    public void onSystemGroupsBtn() {
        Main.changeScene("system-table", "System Group");

    }

    @FXML
    public void onTCPTableBtn() {
        Main.changeScene("tcp-table", "TCP Table");

    }

    @FXML
    public void onARPTableBtn() {
        Main.changeScene("arp-table", "ARP Table");

    }

    @FXML
    public void onSNMPStatisticsBtn() {
        Main.changeScene("snmp-groups", "SNMP Statistics");

    }

    @FXML
    public void onLogoutBtn() {
        Main.changeScene("login-form", "Login");
    }
}
