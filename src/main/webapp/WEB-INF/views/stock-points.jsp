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
  PointsVente pointsVente = (PointsVente)request.getAttribute("points-vente");
  StockPoints[] stockPoints = (StockPoints[]) request.getAttribute("stock");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>stock-<%=pointsVente.getNom()%></title>
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
      <h1>stock-<%=pointsVente.getNom()%></h1>
    </div>
    <div class="table-main">
      <table>
        <tr>
          <th>laptop</th>
          <th>quantite </th>
          <th>prix</th>
        </tr>
        <%for(StockPoints stockPoints1:stockPoints){%>
        <tr>
            <td><%=stockPoints1.getReceptionTransfere().getTransfere().getStockMagasin().getLaptop().affiche()%></td>
            <td><%=stockPoints1.getQuantite()%></td>
            <td><%=stockPoints1.getReceptionTransfere().getTransfere().getStockMagasin().getLaptop().getPrixVente()%></td>
            <td><a href="<%=request.getContextPath()%>/user/stock/vente?id=<%=stockPoints1.getId()%>">vendre</a> </td>
        </tr>
        <%}%>
      </table>
    </div>
  </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
  active("stock-points");
  <%if(request.getAttribute("reussi") !=null){%>
  alert("<%=request.getAttribute("reussi")%>")
  <%} %>
</script>
</body>
</html>

