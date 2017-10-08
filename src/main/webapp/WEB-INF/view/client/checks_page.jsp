<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:setBundle basename="constants" var="rb" />
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><fmt:message key="client.header.checks" bundle="${rb}"/></title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="topnav" id="myTopnav">
    <a href="/restaurant/client/home_page" ><fmt:message key="client.header.home" bundle="${rb}"/></a>
    <a href="/restaurant/client/menu"><fmt:message key="client.header.menu" bundle="${rb}"/></a>
    <a href="/restaurant/client/order"><fmt:message key="client.header.order" bundle="${rb}"/></a>
    <a class="active" href="#checks"><fmt:message key="client.header.checks" bundle="${rb}"/></a>
    <a href="/restaurant/exit"><fmt:message key="client.header.exit" bundle="${rb}"/></a>
</div>

<c:if test="${not empty requestScope.message}">
    <div class="information-box" style="text-align: center">
        <p><fmt:message key="${requestScope.message}" bundle="${rb}"/></p>
    </div>
</c:if>

<c:forEach var="check" items="${requestScope.userChecks}" >
    <div class="information-box">
        <h1><fmt:message key="check.order" bundle="${rb}"/> ${check.order.ordered}</h1>
        <p><fmt:message key="check.price" bundle="${rb}"/> <ctg:price price="${check.price}"/></p>
        <p><fmt:message key="check.admin" bundle="${rb}"/> ${check.admin.name} </p>
        <form action="/restaurant/client/show_order" method="post" class="button-forms">
            <input type="hidden" name="order.id" value="${check.order.id}">
            <input type="hidden" name="query" value="get.order.items">
            <button class="button-green">
                <fmt:message key="button.show" bundle="${rb}"/>
            </button>
        </form>
        <c:if test="${empty check.paid}">
            <form action="/restaurant/client/pay" method="post" class="button-forms">
                <input type="hidden" name="query" value="pay.check">
                <input hidden name="check.id" value="${check.id}">
                <button class="button-green">
                    <fmt:message key="check.pay" bundle="${rb}"/>
                </button>
            </form>
        </c:if>
    </div>
</c:forEach>


</body>
</html>
