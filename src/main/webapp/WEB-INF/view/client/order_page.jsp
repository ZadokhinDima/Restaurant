<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="constants" var="rb"/>
<%@ taglib prefix="ctg" uri="customtags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Your order</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="topnav" id="myTopnav">
        <a href="/main?query=client.home.page"><fmt:message key="client.header.home" bundle="${rb}"/></a>
        <a href="/main?query=search.meals"><fmt:message key="client.header.menu" bundle="${rb}"/></a>
        <a class="active" href="#order"><fmt:message key="client.header.order" bundle="${rb}"/></a>
        <a href="/main?query=client.checks.page"><fmt:message key="client.header.checks" bundle="${rb}"/></a>
        <a href="/main?query=exit"><fmt:message key="client.header.exit" bundle="${rb}"/></a>
    </div>
    <c:if test="${not empty requestScope.message}">
        <div class="information-box" style="text-align: center">
            <p><fmt:message key="${requestScope.message}" bundle="${rb}"/></p>
        </div>
    </c:if>
    <c:forEach items="${sessionScope.currentOrder}" var="meal">
        <div class="information-box">
            <form action="/main" method="post">
                <input type="hidden" name="query" value="remove.meal.from.order">
                <input type="hidden" name="meal" value="${meal.id}">
                <h3><fmt:message key="meal.name" bundle="${rb}"/> : ${meal.name}</h3>
                <p>
                    <fmt:message key="meal.category" bundle="${rb}"/> : ${meal.category.name}
                </p>
                <p><fmt:message key="meal.amount" bundle="${rb}"/> : ${meal.amount}</p>
                <p><fmt:message key="meal.price" bundle="${rb}"/> : <ctg:price price="${meal.price}"/> <br>
                    <fmt:message key="meal.weight" bundle="${rb}"/> <ctg:weight weight="${meal.weight}"/> </p>
                <input type="number" min="1" max="${meal.amount}" name="amount" value="${meal.amount}" style="height: 35px;">
                <button class="button-red"><fmt:message key="meal.delete" bundle="${rb}" /></button>
            </form>
        </div>
    </c:forEach>
    <div class="information-box" <c:if test="${empty sessionScope.currentOrder}">
        style="visibility: hidden"
    </c:if>>
    <form action="/main" >
        <input type="hidden" name="query" value="create.order">
        <button class="button-green"><fmt:message key="order.create" bundle="${rb}"/></button>
    </form>
    </div>
</body>
</html>
