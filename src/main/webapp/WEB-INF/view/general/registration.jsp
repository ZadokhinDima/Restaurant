<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:setBundle basename="constants" var="rb" />
<html>
<head>
    <title>Registration</title>
    <base href="${pageContext.request.contextPath}">
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="login-page">
<div>
        <form class="form" action="/main">
            <input type="hidden" name="query" value="register.user">
            <input pattern="([A-Z][a-z]{1,13} ?){1,5}" title="<fmt:message key="error.name" bundle="${rb}" />" placeholder="<fmt:message key="registration.name" bundle="${rb}"/>" name = "name" >
            <input pattern="[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*" title="<fmt:message key="errors.email" bundle="${rb}" />" placeholder="<fmt:message key="registration.email" bundle="${rb}"/>" name = "email"/>
            <input type="password" pattern=".{6,}" title="<fmt:message key="error.password" bundle="${rb}"/>"
                   placeholder="<fmt:message key="registration.password" bundle="${rb}"/>" name = "password"/>
            <input type="password" pattern=".{6,}" title="<fmt:message key="error.password" bundle="${rb}"/>"
                   placeholder="<fmt:message key="registration.password.val" bundle="${rb}"/>" name = "repeatedPass">
            <input placeholder="<fmt:message key="registration.birthday" bundle="${rb}"/>" onfocus="(this.type='date')" name = "birthDay">
            <c:forEach items="${requestScope.registration_errors}" var="error">
                <p style="color: red"> <fmt:message key="${error}" bundle="${rb}"/> </p> <br>
            </c:forEach>
            <button class="my-button"><fmt:message key="registration.register" bundle="${rb}"/></button>
            <p class="message"><fmt:message key="registration.have.account" bundle="${rb}"/>
                <a href="/main?query=index"><fmt:message key="registration.signin" bundle="${rb}"/></a></p>
        </form>
</div>
<div class="language-box">
    <form class="language-form" action="/main" method="post">
        <input type="hidden" name="query" value="set.locale">
        <input type="hidden" name="page" value="/WEB-INF/view/general/registration.jsp">
        <input id = "ua" type="submit" name="language" value="UA">
        <input id = "en" type="submit" name="language" value="EN">
    </form>
</div>
</body>
</html>
