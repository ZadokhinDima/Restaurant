<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="constants" var="rb"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Login page</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="login-page">
<div class="box">
    <div class="form" id="main">
        <form class="login-form" action="/restaurant/login" method="post">
            <input pattern="[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*"
                   title="<fmt:message key="errors.email" bundle="${rb}"/>"
                   placeholder="<fmt:message key="login.email" bundle="${rb}"/>" name="email"/>
            <input type="password" placeholder="<fmt:message key="login.password" bundle="${rb}"/>" name="password"/>

            <c:if test="${not empty requestScope.errorMessage}">
                <p style="color:red"><fmt:message key="${requestScope.errorMessage}" bundle="${rb}"/></p>
            </c:if>

            <button class="my-button"><fmt:message key="login.login" bundle="${rb}"/></button>
            <p class="message"><fmt:message key="login.notregistered" bundle="${rb}"/>
                <a href="/restaurant/registration"><fmt:message key="login.register" bundle="${rb}"/></a></p>
        </form>
    </div>

</div>
<div class="language-box">
    <form class="language-form" action="/restaurant/change_locale">
        <input type="hidden" name="page" value="/sign_in.jsp"/>
        <input id="ua" type="submit" name="language" value="UA"/>
        <input id="en" type="submit" name="language" value="EN"/>
    </form>
</div>
</body>