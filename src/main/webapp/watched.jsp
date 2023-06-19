<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Movies</title>
<link href="css/show.css" rel="stylesheet" type="text/css">
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
    <table>
        <tr>
            <th class="idTH">Id</th>
            <th class="propertiesTH">Title</th> 
            <th class="propertiesTH">Director</th>
            <th class="propertiesTH">Year</th>
            <th class="propertiesTH">Genre</th>
        </tr>
        <c:forEach items="${movies}" var="m">
            <tr>
                <td class="idTD">${m.getId()}</td>
                <td class="propertiesTD">${m.getTitle()}</td> 
                <td class="propertiesTD">${m.getDirector()}</td> 
                <td class="propertiesTD">${m.getYear()}</td> 
                <td class="propertiesTD">${m.getGenre()}</td> 
                <td name="remove">
                    <form action="/removeFromWatched" method="post">
                        <input type="hidden" name="id" value="${m.getId()}">
                        <input type="submit" name="remove" value="Remove from watched">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${not empty exception}">
		<c:out value="${exception}"/>
	</c:if>
</body>
</html>