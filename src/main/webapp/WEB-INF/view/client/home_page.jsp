<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="constants" var="rb"/>
<%@ taglib prefix="ctg" uri="customtags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Home</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="topnav" id="myTopnav">
    <a class="active" href="/main?query=client.home.page"><fmt:message key="client.header.home" bundle="${rb}"/></a>
    <a href="/main?query=search.meals"><fmt:message key="client.header.menu" bundle="${rb}"/></a>
    <a href="/main?query=client.current.order"><fmt:message key="client.header.order" bundle="${rb}"/></a>
    <a href="/main?query=client.checks.page"><fmt:message key="client.header.checks" bundle="${rb}"/></a>
    <a href="/main?query=exit"><fmt:message key="client.header.exit" bundle="${rb}"/></a>
</div>

<div class="information-box">
    <h2><fmt:message key="client.home.information" bundle="${rb}"/></h2>
    <p2><fmt:message key="client.home.name" bundle="${rb}"/> ${sessionScope.user.name} </p2>
    <br>
    <p2><fmt:message key="client.home.birthday" bundle="${rb}"/> ${sessionScope.user.birthDate} </p2>
</div>

<c:if test="${not empty requestScope.message}">
    <div class="information-box" style="text-align: center">
        <p><fmt:message key="${requestScope.message}" bundle="${rb}"/></p>
    </div>
</c:if>

<div class="information-box" <c:if test="${empty requestScope.orderMeals}"> style="visibility: hidden"
</c:if> >
    <table>
        <tr>
            <th><fmt:message key="meal.name" bundle="${rb}"/></th>
            <th><fmt:message key="meal.category" bundle="${rb}"/></th>
            <th><fmt:message key="meal.amount" bundle="${rb}"/></th>
            <th><fmt:message key="meal.price" bundle="${rb}"/></th>
        </tr>
        <c:forEach var="meal" items="${requestScope.orderMeals}">
            <tr>
                <td><c:out value="${meal.name}"/></td>
                <td><c:out value="${meal.category.name}"/></td>
                <td><c:out value="${meal.amount}"/></td>
                <td><ctg:price price="${meal.price}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="information-box"
        <c:if test="${empty requestScope.ordersHistory}">
            style="visibility: hidden;"
        </c:if>
>
    <h2><fmt:message key="client.home.history" bundle="${rb}"/></h2>

    <c:forEach var="order" items="${requestScope.ordersHistory}">
        <div class="information-box">
            <fmt:message key="order.time" bundle="${rb}"/>
            <c:out value=": ${order.ordered}"/>
            <br>
            <fmt:message key="order.done" bundle="${rb}"/>
            <c:choose>
                <c:when test="${order.accepted == 0}">
                    <fmt:message key="order.status.inprocess" bundle="${rb}"/>
                </c:when>
                <c:when test="${order.accepted == 1}">
                    <fmt:message key="order.status.accepted" bundle="${rb}"/>
                </c:when>
                <c:when test="${order.accepted == -1}">
                    <fmt:message key="order.status.declined" bundle="${rb}"/>
                </c:when>
            </c:choose>
            <br>
            <form action="/main" method="post">
                <input type="hidden" name="order.id" value="${order.id}">
                <input type="hidden" name="query" value="get.order.items">
                <button class="button-green">
                    <fmt:message key="button.show" bundle="${rb}"/>
                </button>
            </form>
            <c:if test="${order.accepted == 0}">
                <form action="/main" method="post">
                    <input type="hidden" name="query" value="client.decline.order">
                    <input hidden name="order.id" value="${order.id}">
                    <button class="button-red">
                        <fmt:message key="order.decline" bundle="${rb}"/>
                    </button>
                </form>
            </c:if>

        </div>
    </c:forEach>
</div>
</body>
</html>
