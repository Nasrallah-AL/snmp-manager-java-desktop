<%@ page import="java.io.*, org.json.* "%>
<%
    String reqID = "";
    String reqPassword = "";


    if (request.getMethod().equalsIgnoreCase("GET")) {
        reqID = request.getParameter("id");
        reqPassword = request.getParameter("password");
    } else {
        InputStream reqInputStream = request.getInputStream();
        JSONTokener reqTokener = new JSONTokener(reqInputStream);
        JSONObject reqData = new JSONObject(reqTokener);

        reqID = reqData.getString("id");
        reqPassword = reqData.getString("password");

        reqInputStream.close();
    }




    String responseString = "NO";


    try {

        FileReader reader = new FileReader(application.getRealPath("/") + "usersJSP.json");
        JSONTokener tokener = new JSONTokener(reader);
        JSONObject userData = new JSONObject(tokener);


        if (userData.has(reqID)) {
            String storedPassword = (String) userData.get(reqID);
            if (reqPassword.equals(storedPassword)) {
                responseString = "OK";
            }
        }

        reader.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<%= responseString %>