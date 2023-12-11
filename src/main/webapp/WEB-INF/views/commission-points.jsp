<%@ page import="jdk.nashorn.internal.parser.JSONParser" %>
<%@ page import="jdk.nashorn.internal.ir.debug.JSONWriter" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.example.ultraspringmvc.model.Statistique" %>
<%@ page import="com.example.ultraspringmvc.model.PointsVente" %>
<%@ page import="java.time.Month" %>
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
  Gson gson = new Gson();
  PointsVente[] pointsVentes =  (PointsVente[]) request.getAttribute("points-vente");
  Month[] months = Month.values();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>Commission-points-vente</title>
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
      <h1>Commission points de Vente</h1>
    </div>
    <div class="table-main" id="form-table">
      <div class="table-main">
        <div class="recherche">
          <form action="<%=request.getContextPath()%>/admin/points/commission" class="form" method="get" >
            <div class="form-group">
              <label for="">annee</label>
              <input type="number" name="annee" value="0" placeholder="" id="" class="class-form"/>
            </div>
            <div class="form-group">
              <label for="">marque</label>
              <select name="models.id" id="" class="class-form">
                <%
                  int i = 0;
                  for(i = 0 ; i < months.length ; i++){%>
                  <option value="<%=i+1%>"><%=months[i].name()%></option>
                  <%} %>
              </select>
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
          <th>Points de vente</th>
          <th>montant</th>
          <th>commission</th>
        </tr>
        <%for(PointsVente pointsVente:pointsVentes){%>
        <tr>
          <td><%=pointsVente.getNom()%></td>
          <td><%=pointsVente.getMontant()%></td>
          <td><%=pointsVente.getCommission()%> %</td>
        </tr>
        <%}%>
      </table>
    </div>
  </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
  active("commission-points");
</script>
</body>
</html>
