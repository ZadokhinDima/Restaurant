<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:setBundle basename="constants" var="rb" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Login page</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="login-page">
<div class="box">
    <div class="form" id="main">
        <form class="login-form" action="/main" method="post">
            <input type="hidden" name="query" value="login"/>
            <input pattern="[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*"
                   title="<fmt:message key="errors.email" bundle="${rb}"/>" placeholder="<fmt:message key="login.email" bundle="${rb}"/>" name="email"/>
            <input type="password" placeholder="<fmt:message key="login.password" bundle="${rb}"/>" name="password"/>
            <p style="color:red"> ${errorMessage}</p>
            <button class="my-button"><fmt:message key="login.login" bundle="${rb}"/></button>
            <p class="message"><fmt:message key="login.notregistered" bundle="${rb}"/>
                <a href="/main?query=registration"><fmt:message key="login.register" bundle="${rb}"/></a></p>
        </form>
    </div>

</div>
<div class="language-box">
    <form class="language-form" action="/main">
        <input type="hidden" name="query" value="set.locale"/>
        <input type="hidden" name="page" value="index.jsp"/>
        <input id = "ua" type="submit" name="language" value="UA"/>
        <input id = "en" type="submit" name="language" value="EN"/>
    </form>
</div>
</body>

</html>