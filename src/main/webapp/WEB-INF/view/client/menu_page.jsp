<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="constants" var="rb"/>
<%@ taglib prefix="ctg" uri="customtags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><fmt:message key="client.header.menu" bundle="${rb}"/></title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="topnav" id="myTopnav">
    <a href="/main?query=client.home.page"><fmt:message key="client.header.home" bundle="${rb}"/></a>
    <a class="active" href="#menu"><fmt:message key="client.header.menu" bundle="${rb}"/></a>
    <a href="/main?query=client.current.order"><fmt:message key="client.header.order" bundle="${rb}"/></a>
    <a href="/main?query=client.checks.page"><fmt:message key="client.header.checks" bundle="${rb}"/></a>
    <a href="/main?query=exit"><fmt:message key="client.header.exit" bundle="${rb}"/></a>
</div>



<div>
    <form action="/main">
        <input type="hidden" value="search.meals" name="query">
        <div class="styled-select">
            <fmt:message key="menu.select.category" bundle="${rb}"/>
            <select name="category" >
                <option value="" disabled selected>${sessionScope.currentCategory.name}</option>
                <c:forEach items="${requestScope.categories}" var="category">
                    <option value="${category.id}"> ${category.name} </option>
                </c:forEach>
            </select>
            <button class="button-green"><fmt:message key="menu.search" bundle="${rb}"/></button>
        </div>
    </form>
</div>
<c:if test="${not empty requestScope.message}">
    <div class="information-box" style="text-align: center">
        <p><fmt:message key="${requestScope.message}" bundle="${rb}"/></p>
    </div>
</c:if>
<c:forEach items="${requestScope.meals}" var="meal">
    <div class="information-box">
        <form action="/main" method="post">
            <input type="hidden" name="query" value="add.meal.to.order">
            <input type="hidden" name="meal" value="${meal.id}">
            <h3><fmt:message key="meal.name" bundle="${rb}"/> : ${meal.name}</h3>
            <p><fmt:message key="meal.price" bundle="${rb}"/> : <ctg:price price="${meal.price}"/> <br>
                <fmt:message key="meal.weight" bundle="${rb}"/> <ctg:weight weight="${meal.weight}"/> </p>
            <input type="number" min="1" max="20" name="amount" value="1"  style="height: 35px;">
            <button class="button-green"><fmt:message key="meal.order" bundle="${rb}" /></button>
        </form>
    </div>
</c:forEach>
</body>
</html>
