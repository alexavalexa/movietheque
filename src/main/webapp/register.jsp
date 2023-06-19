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
	<h2>Register</h2>
    <form action="/register" method="post">
        <p>First Name: </p><input type="text" name="f_name"/>
		<p>Last Name: </p><input type="text" name="l_name"/>
		<p>Email: </p><input type="email" name="email"/>
		<p>Username: </p><input type="text" name="username"/>
		<p>Password: </p><input type="password" name="password"/>
        <br>
        <c:if test="${not empty exception}">
            <c:out value="${exception}"/>
        </c:if>
		<br> <input type="submit" name="Register" value="Register"/>
    </form>
    <form action="/toLogin" method="post">
        <input type="submit" name="toLogin" value="Login"/>
    </form>
</body>
</html>