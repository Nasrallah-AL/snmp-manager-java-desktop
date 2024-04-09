package com.networkproject.auth;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    private void login(String name, String password, HttpServletResponse response) throws ServletException, IOException {

        String responseString = "NO";

        try {

            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("users.json");
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONObject userData = new JSONObject(tokener);


            if (userData.has(name)) {
                String storedPassword = userData.getString(name);
                if (password.equals(storedPassword)) {
                    responseString = "OK";
                }
            }

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");


        PrintWriter out = response.getWriter();
        out.println(responseString);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reqName = request.getParameter("id");
        String reqPassword = request.getParameter("password");

        login(reqName, reqPassword, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream reqInputStream = request.getInputStream();
        JSONTokener reqTokener = new JSONTokener(reqInputStream);
        JSONObject reqData = new JSONObject(reqTokener);

        String reqName = reqData.getString("name");
        String reqPassword = reqData.getString("password");

        login(reqName, reqPassword, response);

    }
}