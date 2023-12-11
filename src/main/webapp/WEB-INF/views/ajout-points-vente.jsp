<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ page import="org.springframework.security.access.method.P" %>
<%@ page import="com.example.ultraspringmvc.model.*" %><%--
  Created by IntelliJ IDEA.
  User: Oracle
  Date: 17/05/2023
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    PointsVente[] pointsVentes = (PointsVente[]) request.getAttribute("points");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
    <title>points-vente</title>
</head>
<style>
    .error{
        color: white;
        font-size:10px;
        background: red;
        height: 30%;
    }
</style>
<body>
<div class="header">
    <div class="header-div">
        <div class="icon-menu">
            <img class="icon" src="<c:url value="/resources/image/sliders-h-solid.svg"/>" alt=""/>
        </div>
        <div id="header-text"></div>
    </div>
</div>
<div class="content">
    <div class="menu" id="menu"></div>

    <div class="main">
        <div class="table-main">
            <h1>Nouveau Points de Vente</h1>
        </div>
        <div class="table-main" id="form-table">
            <div class="form-table">
                <form action="<%=request.getContextPath()%>/admin/points/addPoints" class="formulaire" method="post" modelAttribute="pointsVente">
                    <div class="form-group">
                        <label for="">Nom</label>
                        <input type="text" name="nom" placeholder="" id="" class="class-form"/>
                        <%if(request.getAttribute("error-nom")!=null){%>
                        <div class="error"><%=request.getAttribute("error-nom")%></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <input type="submit" value="valider" />
                    </div>
                </form>
            </div>
        </div>

        <div class="table-main">
            <h1>liste Points de Vente</h1>
        </div>
        <div class="table-main">
            <table>
                <tr>
                    <th>Nom</th>
                </tr>
                <%for(PointsVente pointsVente:pointsVentes){%>
                <tr>
                    <form method="post" action="<%=request.getContextPath()%>/admin/points/modifier" modelAttribute="pointsVente">
                        <input type="hidden" name="id" value="<%=pointsVente.getId()%>"/>
                        <td><input type="text" name="nom" value="<%=pointsVente.getNom()%>"/></td>
                        <td><button type="submit">modifier</button> </td>
                    </form>
                    <td><a href="<%=request.getContextPath()%>/admin/points/delete?id=<%=pointsVente.getId()%>"><button type="button">supprimer</button> </a> </td>
                </tr>
                <%}%>
            </table>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
    active("points-vente");
    <%if(request.getAttribute("reussi") !=null){%>
    alert("<%=request.getAttribute("reussi")%>")
    <%} %>
</script>
</body>
</html>

