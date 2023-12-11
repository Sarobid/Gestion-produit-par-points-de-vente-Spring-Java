<%@ page import="jdk.nashorn.internal.parser.JSONParser" %>
<%@ page import="jdk.nashorn.internal.ir.debug.JSONWriter" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.example.ultraspringmvc.model.Statistique" %>
<%@ page import="com.example.ultraspringmvc.model.Benefice" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.time.Month" %>
<%@ page import="com.example.ultraspringmvc.model.Vente" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Oracle
  Date: 14/04/2023
  Time: 06:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Vente[] ventes = (Vente[]) request.getAttribute("details");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>Statistique-Benefice</title>
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

  <div class="main" id="main">
    <div class="table-main">
      <h1>details </h1>
    </div>
    <div class="table-main">
      <table>
        <tr>
          <th>date vente</th>
          <th>Point de vente</th>
          <th>Laptop</th>
          <th>quantite</th>
          <th>vente</th>
        </tr>
        <%for(Vente vente:ventes){%>
          <tr>
            <td><%=vente.getDates()%></td>
            <td><%=vente.getStockPoints().getReceptionTransfere().getTransfere().getPointsVente().getNom()%></td>
            <td><%=vente.getStockPoints().getReceptionTransfere().getTransfere().getStockMagasin().getLaptop().affiche()%></td>
            <td><%=vente.getQuantite()%></td>
            <td><%=NumberFormat.getInstance(java.util.Locale.FRENCH).format(vente.getQuantite()*vente.getStockPoints().getReceptionTransfere().getTransfere().getStockMagasin().getLaptop().getPrixVente())%></td>
          </tr>
        <%}%>

      </table>
    </div>
  </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
  active("benefice-vente");
</script>
</body>
</html>
