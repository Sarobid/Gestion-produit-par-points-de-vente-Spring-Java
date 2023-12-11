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
    Etat[] etats = (Etat[])request.getAttribute("etat");
    Renvoye[] renvoyes = (Renvoye[]) request.getAttribute("renvoye");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
    <title>reception-renvoye</title>
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
            <h1>Reception-renvoye</h1>
        </div>
        <div class="table-main">
            <table>
                <tr>
                    <th>date renvoye</th>
                    <th>laptop</th>
                    <th>quantite renvoye</th>
                    <th>date reception</th>
                    <th>quantite</th>
                    <th>etat</th>
                </tr>
                <%for(Renvoye renvoye:renvoyes){%>
                <tr>
                    <form action="<%=request.getContextPath()%>/admin/renvoye/add" method="post" modelAttribute="reception">
                        <input type="hidden" name="renvoye.id" value="<%=renvoye.getId()%>"/>
                        <td><%=renvoye.getDate().toString()%></td>
                        <td><%=renvoye.getReceptionTransfere().getTransfere().getStockMagasin().getLaptop().affiche()%></td>
                        <td><%=renvoye.getQuantite()%></td>
                        <td><input type="date" name="date" required/></td>
                        <td><input type="number" name="quantite" required/></td>
                        <td>
                            <select name="etat.id">
                                <%for(Etat etat:etats){%>
                                <option value="<%=etat.getId()%>"><%=etat.getNom()%></option>
                                <%}%>
                            </select>
                        </td>
                        <td><button type="submit">valider</button></td>
                        <td><a href="<%=request.getContextPath()%>/admin/renvoye/annuler?id=<%=renvoye.getId()%>"><button type="button" class="supprimer">annuler</button> </a> </td>
                    </form>
                </tr>
                <%}%>
            </table>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
    active("reception-renvoye");
    <%if(request.getAttribute("reussi") !=null){%>
    alert("<%=request.getAttribute("reussi")%>")
    <%} %>
</script>
</body>
</html>

