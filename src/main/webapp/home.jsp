<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Movietheque</title>
    <link href="css/home.css" rel="stylesheet" type="text/css">
</head>
<body>
	<h1>Movietheque</h1>
	<nav class="main-menu">
        <ul>
            <li><a href="home.jsp">Home</a></li>
            <li><a href="add.jsp">Add</a></li>
            <li><a href="search.jsp">Search</a></li>
            <li>
                <form action="/to-watch" method="post">
                    <input type="submit" value="To Watch">
                </form>
            </li>
            <li>
                <form action="/watched" method="post">
                    <input type="submit" value="Watched">
                </form>
            </li>
            <li>
                <form action="/favorites" method="post">
                    <input type="submit" value="Favorites">
                </form>
            </li>
            <li>
                <form action="/show" method="post">
                    <input type="submit" value="Show all">
                </form>
            </li>
            <li name="logout"><a href="login.jsp">Log out</a></li>
        </ul>
    </nav>
    <br>
    <img src="images/home_background.jpg">
</body>
</html>