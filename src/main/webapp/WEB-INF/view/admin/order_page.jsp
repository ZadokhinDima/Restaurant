<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="constants" var="rb"/>
<%@ taglib prefix="ctg" uri="customtags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><fmt:message key="admin.header.order" bundle="${rb}"/></title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="topnav" id="myTopnav">
    <a href="/restaurant/admin/home_page"><fmt:message key="admin.header.active.orders" bundle="${rb}"/></a>
    <a href="/restaurant/exit"><fmt:message key="client.header.exit" bundle="${rb}"/></a>
</div>

<c:if test="${not empty requestScope.message}">
    <div class="information-box" style="text-align: center">
        <p><fmt:message key="${requestScope.message}" bundle="${rb}"/></p>
    </div>
</c:if>

<div class="information-box">
    <h2><fmt:message key="admin.client.information" bundle="${rb}"/></h2>
    <p2><fmt:message key="client.home.name" bundle="${rb}"/> ${sessionScope.currentOrder.client.name} </p2>
    <br>
    <p2><fmt:message key="client.home.birthday" bundle="${rb}"/> ${sessionScope.currentOrder.client.birthDate} </p2>
</div>

<c:forEach items="${sessionScope.currentOrder.meals}" var="meal">
    <div class="information-box">
            <h3><fmt:message key="meal.name" bundle="${rb}"/> : ${meal.name}</h3>
            <p>
                <fmt:message key="meal.category" bundle="${rb}"/> : ${meal.category.name}
            </p>
            <p><fmt:message key="meal.amount" bundle="${rb}"/> : ${meal.amount}</p>
            <p><fmt:message key="meal.price" bundle="${rb}"/> : <ctg:price price="${meal.price}"/> <br>
                <fmt:message key="meal.weight" bundle="${rb}"/> <ctg:weight weight="${meal.weight}"/> </p>
    </div>
</c:forEach>
<div class="information-box">
    <form action="/restaurant/admin/accept_order">
        <button class="button-green"><fmt:message key="order.accept" bundle="${rb}"/></button>
    </form>
    <form action="/restaurant/admin/decline_order">
        <button class="button-red"><fmt:message key="order.decline" bundle="${rb}" /></button>
    </form>
</div>
</body>
</html>
