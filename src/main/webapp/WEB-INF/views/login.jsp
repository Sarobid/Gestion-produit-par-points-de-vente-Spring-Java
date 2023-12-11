<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Oracle
  Date: 16/05/2023
  Time: 08:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = (String) request.getParameter("error");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/login.css"/>">
    <title>Login</title>
</head>
<body>
<div class="container">
    <h1>Connexion</h1>
    <%if(error != null){%>
        <p style="color: white;background-color: red;border: solid 1px white;">Verifier votre email et votre mot de passe</p>
    <%}%>
    <form action="<%=request.getContextPath()%>/login" method="post">
        <div class="input-field">
            <input type="text" id="email" name="username" required>
            <label for="email">Email</label>
            <span class="underline"></span>
        </div>
        <div class="input-field">
            <input type="password" id="password" name="password" required>
            <label for="password">Mot de passe</label>
            <span class="underline"></span>
        </div>
        <div class="button">
            <button type="submit">Se connecter</button>
        </div>
    </form>
</div>

</body>
</html>