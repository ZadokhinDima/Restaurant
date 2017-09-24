<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 23.09.2017
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
    email : <%= request.getAttribute("email") %>
    <br/>
    password : <%= request.getAttribute("password")%>

</body>
</html>
