<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Movietheque</title>
    <link href="css/register.css" rel="stylesheet" type="text/css">
    <link href="css/add.css" rel="stylesheet" type="text/css">
    <link href="css/home.css" rel="stylesheet" type="text/css">
</head>
<body>
	<h1>Movietheque</h1>
	<h2>Login</h2>
    <form action="/login" method="post">
		<p>Username: </p><input type="text" name="username"/>
		<p>Password: </p><input type="password" name="password"/>
        <br>
        <c:if test="${not empty exception}">
            <c:out value="${exception}"/>
        </c:if>
		<br> <input type="submit" name="Login" value="Login"/>
    </form>
    <form action="/toRegister" method="post">
        <input type="submit" name="toRegister" value="Register"/>
    </form>
</body>
</html>