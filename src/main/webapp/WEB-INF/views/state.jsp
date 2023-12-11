<%@ page import="jdk.nashorn.internal.parser.JSONParser" %>
<%@ page import="jdk.nashorn.internal.ir.debug.JSONWriter" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.example.ultraspringmvc.model.Statistique" %>
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
  Statistique[] statistiques = (Statistique[]) request.getAttribute("vente-globale");
  Statistique[] statistiquesPoints = (Statistique[]) request.getAttribute("vente-points-vente");
  System.out.println(gson.toJson(statistiques));
  System.out.println("\n");
  System.out.println(gson.toJson(statistiquesPoints));
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>Statistique-vente</title>
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
  <p><a href="<%=request.getContextPath()%>/pdf/pdf?url=pdf/vente?mois=<%=mois%>&&annee=<%=annee%>">pdf</a> </p>
  <div class="main">
    <div class="table-main">
      <h1>Statistique global vente par mois</h1>
    </div>
    <div class="table-main">
      <div class="canvas">
        <canvas id="myChart"></canvas>
      </div>
    </div>
    <div class="table-main">
      <h1>Statistique vente par mois par points de Vente</h1>
    </div>
    <div class="table-main">
      <table>
        <tr>
          <th>points de vente</th>
          <th>mois</th>
        </tr>
        <%for(Statistique statistique: statistiquesPoints){%>
        <tr></tr>
        <%{%>
      </table>
    </div>
  </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
  active("state-vente");
</script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
  const ctx = document.getElementById('myChart');
  const state = <%=gson.toJson(statistiques)%>;
          new Chart(ctx, {
            type: 'bar',
            data: {
              labels: state.map(row=>row.mois),
              datasets: [{
                label: 'total global vente par mois',
                data: state.map(row=>row.nombre),
                borderWidth: 1
              }]
            },
            options: {
              scales: {
                y: {
                  beginAtZero: true
                }
              }
            }
          });
</script>

</body>
</html>
