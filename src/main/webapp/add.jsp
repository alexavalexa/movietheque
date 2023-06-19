<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Movietheque</title>
<link href="css/add.css" rel="stylesheet" type="text/css">
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
	<form action="/insert" method="post">
		<p>Title: </p><input type="text" name="movieTitle"/>
		<p>Director: </p><input type="text" name="movieDirector"/>
		<p>Year: </p><input type="number" name="movieYear"/>
		<p>Genre: </p><input type="text" name="movieGenre"/>
		<br><br>
		<input type="radio" id="watched" name="watched" value="watched">
		<label for="title">watched</label>
		<input type="radio" id="to-watch" name="watched" value="to watch">
		<label for="title">to watch</label>
		<br>
		<input type="submit" value="Add"/>
		<input type="hidden" name="user_id" value="${user_id}"/>
	</form>
	<c:if test="${not empty exception}">
		<c:out value="${exception}"/>
	</c:if>
</body>
</html>