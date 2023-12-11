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
  Vente[] ventes = (Vente[]) request.getAttribute("vente");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>liste-vente-<%=pointsVente.getNom()%></title>
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
      <h1>Liste vente <%=pointsVente.getNom()%></h1>
    </div>
    <div class="table-main" id="form-table">
      <div class="table-main">
        <div class="recherche">
          <form action="<%=request.getContextPath()%>/user/stock/vente/liste" class="form" method="get" >
            <div class="form-group">
              <label for="">Reference</label>
              <input type="text" name="reference" placeholder="" id="" class="class-form"/>
            </div>
            <div class="form-group">
              <label for="">Prix Minimum</label>
              <input type="number" name="min" value="0" placeholder="" id="" class="class-form"/>
            </div>
            <div class="form-group">
              <label for="">Prix Maximum</label>
              <input type="number" name="max" value="0" placeholder="" id="" class="class-form"/>
            </div>
            <div class="form-group">
              <input type="submit" value="recherche">
            </div>
          </form>
        </div>
      </div>
    </div>
    <div class="table-main">
      <table>
        <tr>
          <th>date vente</th>
          <th>laptop</th>
          <th>quantite</th>
          <th>prix de vente</th>
        </tr>
        <%for(Vente vente:ventes){%>
        <tr>
          <td><%=vente.getDates()%></td>
          <td><%=vente.getStockPoints().getReceptionTransfere().getTransfere().getStockMagasin().getLaptop().affiche()%></td>
          <td><%=vente.getQuantite()%></td>
          <td><%=vente.getPrix()%></td>
          <td><a href="<%=request.getContextPath()%>/user/stock/supprimer?id=<%=vente.getId()%>"><button type="button">supprimer</button> </a> </td>
        </tr>
        <%}%>
      </table>
    </div>
  </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
  active("liste-vente");
  <%if(request.getAttribute("reussi") !=null){%>
  alert("<%=request.getAttribute("reussi")%>")
  <%} %>
</script>
</body>
</html>

