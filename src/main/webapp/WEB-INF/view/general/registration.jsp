<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <title>Register</title>
    <base href="${pageContext.request.contextPath}">
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="login-page">
<div>
        <form class="form" action="/main">
            <input type="hidden" name="query" value="register.user">
            <input placeholder="Name" name = "name" >
            <input placeholder="Email address" name = "email"/>
            <input type="password" placeholder="Password" name = "password"/>
            <input type="password" placeholder="Repeat password" name = "repeatedPass">
            <input placeholder="Date of birth" onfocus="(this.type='date')" name = "birthDay">
            <button>create</button>
            <p class="message">Already registered? <a href="/main?query=index">Sign In</a></p>
        </form>
</div>
</body>
</html>
