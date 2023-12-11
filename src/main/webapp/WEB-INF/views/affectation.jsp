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
  Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
  PointsVente[] pointsVentes = (PointsVente[]) request.getAttribute("points-vente");
  Utilisateur[] affectation = (Utilisateur[]) request.getAttribute("affectation");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<c:url value="/resources/css/acceuil.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/css/formulaire.css"/>">
  <title>affectation-<%=utilisateur.getNom()%></title>
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
      <h1><%=utilisateur.getNom()%></h1>
    </div>
    <%if(affectation.length > 0){%>
    <div class="table-main" id="form-table">
      <div class="form-table">
        <form action="<%=request.getContextPath()%>/admin/utilisateur/desaffectation" class="formulaire" method="Post" modelAttribute="utilisateur">
          <input type="hidden" name="id" value="<%=utilisateur.getId()%>"/>
          <input type="hidden" name="id_points_vente.id" value="<%=affectation[0].getId_points_vente().getId()%>"/>
          <div class="form-group">
            <label for="">Points-Vente</label>
            <input type="text" name="id_points_vente.nom" placeholder="" value="<%=affectation[0].getId_points_vente().getNom()%>" id="" class="class-form" disabled/>
          </div>
          <div class="form-group">
            <input type="submit" value="desaffectation" />
          </div>
        </form>
      </div>
    </div>
    <% }else{ %>
    <div class="table-main" id="form-table">
      <div class="form-table">
        <form action="<%=request.getContextPath()%>/admin/utilisateur/affectation/add" class="formulaire" method="Post" modelAttribute="utilisateur">
          <input type="hidden" name="id" value="<%=utilisateur.getId()%>"/>
          <div class="form-group">
            <label for="">Points Vente</label>
            <select name="id_points_vente.id" id="" class="class-form">
              <%for(PointsVente pointsVente:pointsVentes){%>
              <option value="<%=pointsVente.getId()%>"><%=pointsVente.getNom()%></option>
              <%} %>
            </select>
          </div>
          <div class="form-group">
            <input type="submit" value="affectation" />
          </div>
        </form>
      </div>
    </div>
    <% } %>
  </div>
</div>
<script src="<c:url value="/resources/js/acceuil.js"/>"></script>
<script>
  active("liste-utilisateur");
  <%if(request.getAttribute("reussi") !=null){%>
  alert("<%=request.getAttribute("reussi")%>")
  <%} %>
</script>
</body>
</html>

