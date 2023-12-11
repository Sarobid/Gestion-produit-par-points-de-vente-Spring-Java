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
  ReceptionTransfere[] receptionTransferes = (ReceptionTransfere[]) request.getAttribute("stock");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>perte-reception-<%=pointsVente.getNom()%></title>
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
      <h1>Perte et Reception <%=pointsVente.getNom()%></h1>
    </div>
    <div class="table-main">
      <table>
        <tr>
          <th>date</th>
          <th>laptop</th>
          <th>quantite </th>
          <th>etat</th>
        </tr>
        <%for(ReceptionTransfere receptionTransfere:receptionTransferes){%>
        <tr>
          <form action="<%=request.getContextPath()%>/user/perte/modification" method="post" modelAttribute="reception">
            <input type="hidden" name="id" value="<%=receptionTransfere.getId()%>"/>
            <input type="hidden" name="etat.id" value="<%=receptionTransfere.getEtat().getId()%>"/>
            <td><input type="date" name="date" value="<%=receptionTransfere.getDate()%>"/></td>
            <td><%=receptionTransfere.getTransfere().getStockMagasin().getLaptop().affiche()%></td>
            <td><input type="number" name="quantite" value="<%=receptionTransfere.getQuantite()%>"/></td>
            <td><%=receptionTransfere.getEtat().getNom()%></td>
            <td><button type="submit">modifier</button> </td>
          </form>
            <td><a href="<%=request.getContextPath()%>/user/perte/supprimer?id=<%=receptionTransfere.getId()%>"><button type="button" class="supprimer">supprimer</button></a></td>
          </tr>
        <%}%>
      </table>
    </div>
  </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
  active("reception-transfere");
  <%if(request.getAttribute("reussi") !=null){%>
  alert("<%=request.getAttribute("reussi")%>")
  <%} %>
</script>
</body>
</html>

