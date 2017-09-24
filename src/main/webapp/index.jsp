<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Login page</title>
    <base href="${pageContext.request.contextPath}">
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="login-page">
<div>
    <div class="form" id = "main">

        <form class="login-form" action="/main">
            <input type="hidden" name="query" value="login">
            <input placeholder="Email" name="email"/>
            <input type="password" placeholder="Password" name = "password"/>
            <button>login</button>
            <p class="message">Not registered? <a href="/main?query=registration" >Create an account</a></p>
        </form>
    </div>
</div>
</body>

</html>