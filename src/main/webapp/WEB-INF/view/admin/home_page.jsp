<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="constants" var="rb"/>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><fmt:message key="admin.header.active.orders" bundle="${rb}"/></title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="topnav" id="myTopnav">
    <a class="active" href="#"><fmt:message key="admin.header.active.orders" bundle="${rb}"/></a>
    <a href="/restaurant/exit"><fmt:message key="client.header.exit" bundle="${rb}"/></a>
</div>

<c:if test="${not empty requestScope.message}">
    <div class="information-box" style="text-align: center">
        <p><fmt:message key="${requestScope.message}" bundle="${rb}"/></p>
    </div>
</c:if>


<c:forEach var="order" items="${requestScope.activeOrders}">
    <div class="information-box">
        <fmt:message key="order.time" bundle="${rb}"/>
        <c:out value=": ${order.ordered}"/>
        <br>
        <br>
        <br>
        <form action="/restaurant/admin/order" method="post">
            <input type="hidden" name="order.id" value="${order.id}">
            <button class="button-green">
                <fmt:message key="admin.goto.order" bundle="${rb}"/>
            </button>
        </form>
    </div>
</c:forEach>

</body>
</html>
