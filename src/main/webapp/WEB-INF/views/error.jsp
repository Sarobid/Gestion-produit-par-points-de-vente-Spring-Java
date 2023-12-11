<%--
  Created by IntelliJ IDEA.
  User: Oracle
  Date: 20/05/2023
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = (String) request.getAttribute("error");
%>
<html>
<head>
    <title>erreur 400</title>
</head>
<style>
    /* Réinitialisation des styles */
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    /* Styles pour le conteneur d'erreur */
    .error-container {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        height: 100vh;
        font-family: Arial, sans-serif;
        text-align: center;
        background-color: #f1f1f1;
    }

    /* Styles pour le titre de l'erreur */
    .error-container h1 {
        font-size: 4rem;
        margin-bottom: 1rem;
        color: #333;
    }
    /* Styles pour le message d'erreur */
    .error-container p {
        font-size: 1.5rem;
        margin-bottom: 2rem;
        color: #777;
    }

    /* Styles pour le lien de retour */
    .error-container a {
        display: inline-block;
        padding: 0.75rem 1.5rem;
        font-size: 1.2rem;
        font-weight: bold;
        text-decoration: none;
        color: #fff;
        background-color: #333;
        border-radius: 4px;
        transition: background-color 0.3s ease;
    }
    .error-container a:hover {
        background-color: #555;
    }
</style>
<body>
    <div class="error-container">
        <h1>Erreur 400</h1>
        <p><%=error%></p>
    </div>
</body>
</html>
